package veronica.feed;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import javax.jdo.PersistenceManager;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.xml.sax.InputSource;

import veronica.feed.vo.Feed;
import veronica.story.vo.Story;
import veronica.story.vo.Story.Builder;
import veronica.util.BigTableDao;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndPerson;
import com.sun.syndication.io.SyndFeedInput;

public class FeedManager {
	private static final Logger log = Logger.getLogger(FeedManager.class.getName());
	private static final String CACHED_FEED_PREFIX = "cachedFeed";
	private static final int MAX_TEASER_SIZE = 400;
	private static final int MAX_IMAGE_WIDTH = 300;
	private static final int MAX_IMAGE_HEIGHT = 400;
	private static final int MAX_THUMBNAIL_WIDTH = 36;
	private static final int MAX_THUMBNAIL_HEIGHT = 35;
	
	@SuppressWarnings("unchecked")
	public static List<Feed> getFeeds() {
		PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
		String query = "SELECT FROM " + Feed.class.getName();
		
		return (List<Feed>) persistenceManager.newQuery(query).execute();
	}  // getFeeds
	
	public static Feed getFeed(String feedKey) {
		Feed feed = new Feed.Builder().build();
		Cache cache;
		
		// check to see if the feed is in the cache
		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());

			if (cache.containsKey(CACHED_FEED_PREFIX + feedKey)) {
				feed = (Feed)cache.get(CACHED_FEED_PREFIX + feedKey);
			} else {
				PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
				feed = persistenceManager.getObjectById(Feed.class, Long.parseLong(feedKey));
				
				log.fine("Storing feed with key = " + feedKey + " in the cache.");
				cache.put(CACHED_FEED_PREFIX + feedKey, feed);
			}  // if-else statement
		} catch (CacheException e) {
			log.severe("Error getting stories from cache or datastore - " + e.getMessage());
			e.printStackTrace();
		}  // try-catch statement
		
		return feed;
	}  // getFeed
	
	@SuppressWarnings("unchecked")
	public static void fetchStories(Feed feed) {
		PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
		
		try {
			URL feedUrl = new URL(feed.getFeedUrl());
            SyndFeedInput input = new SyndFeedInput();
            HttpURLConnection conn = (HttpURLConnection) feedUrl.openConnection();
    		conn.setRequestMethod("GET");
    		conn.connect();
    		
    		SyndFeed syndFeed;
    		if (conn.getHeaderField("Content-Encoding") != null && conn.getHeaderField("Content-Encoding").equals("gzip")) {
    			InputSource is = new InputSource(new GZIPInputStream(conn.getInputStream()));
    	        syndFeed = input.build(is);
    		} else {
    			InputSource is = new InputSource(conn.getInputStream());
    	        syndFeed = input.build(is);
    		}  // if-else statement
    		
			List<SyndEntry> stories = syndFeed.getEntries();

			for (SyndEntry story : stories) {
				
				// only try and publish stories with a date later than the last-poll-date of our feed
				if ((story.getPublishedDate() != null && story.getPublishedDate().compareTo(feed.getLastPoll()) > 0) || 
					(story.getUpdatedDate() != null && story.getUpdatedDate().compareTo(feed.getLastPoll()) > 0)) {

					Date pubDateToUse = null;
					if (story.getPublishedDate() != null && story.getPublishedDate().compareTo(feed.getLastPoll()) > 0) {
						pubDateToUse = story.getPublishedDate();
					} else if (story.getUpdatedDate() != null && story.getUpdatedDate().compareTo(feed.getLastPoll()) > 0) {
						pubDateToUse = story.getUpdatedDate();
					}  // if-else statement
					
					// create basic object
					Builder storyBuilder = new Story.Builder();
					storyBuilder.title(story.getTitle())
								.url(story.getLink())
								.sourceFeedKey(feed.getKey().getId() + "")
								.author(story.getAuthor())
								.publishedDate(pubDateToUse)
								.updatedDate(story.getUpdatedDate())
								.image(makeImage(story));
					
					// fill in more complex fields
					storyBuilder.teaser(makeTeaser(story));
					storyBuilder.authors(makeAuthors(story.getAuthors()));
					storyBuilder.categories(makeCategories(story.getCategories()));
					Blob image = makeImage(story);
					storyBuilder.image(image);
					storyBuilder.thumbnail(makeThumbnail(image));
					
					// build story
					Story storyObj = storyBuilder.build();
					
					// check to see if this is simply an updated story or a new story
					String query = "SELECT FROM " + Story.class.getName() + " WHERE key == " + storyObj.getKey().getId();
					List<Story> storedStories = (List<Story>)persistenceManager.newQuery(query).execute();
					
					DateFormat dateFormat = new SimpleDateFormat("h:mma d MMM, yyyy");
					dateFormat.setTimeZone(TimeZone.getTimeZone("PST"));
					
					if (storedStories.size() <= 0) {
						log.info("New story: " + feed.getTitle() + " - '" + story.getTitle() + "'");
						persistenceManager.makePersistent(storyObj);
					} else {
						Story storedStory = storedStories.get(0);
						log.info("Updated story: " + feed.getTitle() + " - '" + story.getTitle() + "'");
						storedStory.setTitle(storyObj.getTitle());
						storedStory.setUrl(storyObj.getUrl());
						storedStory.setTeaser(storyObj.getTeaser());
						storedStory.setAuthor(storyObj.getAuthor());
						storedStory.setAuthors(storyObj.getAuthors());
						storedStory.setCategories(storyObj.getCategories());
						storedStory.setPublishedDate(pubDateToUse);
						storedStory.setUpdatedDate(storyObj.getUpdatedDate());
					}  // if-else statement
					
				}  // if statement

				// update the last-poll-date of the feed
				Feed storedFeed = persistenceManager.getObjectById(Feed.class, feed.getKey());
				storedFeed.setLastPoll(new Date());
			}  // for loop

		} catch (Exception e) {
			log.severe("Error fetching stories for feed " + feed.getTitle() + " - " + e.getMessage());
			e.printStackTrace();
		} finally {
			persistenceManager.close();
		}  // try-catch-finally statement
	}  // fetchStories
	
	private static String makeTeaser(SyndEntry story) {
		StringBean stringBean = new StringBean();
		Parser parser;
		String teaser = "";
		
		try {
			// Evaluate the description first, because if the story object has a description, it should already have it's tags stripped.
			if (story.getDescription() != null && story.getDescription().getValue().length() > 0) {
				parser = new Parser("<br />" + story.getDescription().getValue());
				parser.visitAllNodesWith(stringBean);
			} else if (story.getContents() != null && story.getContents().size() > 0 && story.getContents().get(0) != null && ((SyndContent) story.getContents().get(0)).getValue().length() > 0){
				parser = new Parser("<br />" + ((SyndContent) story.getContents().get(0)).getValue());
				parser.visitAllNodesWith(stringBean);
			} else {
				return "[No story]";
			}  // if-else statement
			
			String storyText = stringBean.getStrings();
			stringBean.setLinks(false);
			parser.reset();
			parser.visitAllNodesWith(stringBean);
			storyText = stringBean.getStrings();
			
			if (storyText == null || storyText.length() <= 0) {
				teaser = "[No story]";
			} else {
				teaser = storyText.substring(0, Math.min(storyText.length(), MAX_TEASER_SIZE)) + "...";
			}  // if-else statement
		} catch (Exception e) {
			log.severe("Error creating teaser for story + '" + story.getTitle() + "' - " + e.getMessage());
			e.printStackTrace();
		}  // try-catch statement
		
		return teaser;
	}  // makeTeaser
	
	private static List<String> makeCategories(List<SyndCategory> storyCategories) {
		List<String> categories = new ArrayList<String>();
		
		for (SyndCategory category : storyCategories) {
			categories.add(category.getName());
		}  // for loop
		
		return categories;
	}  // makeCategories
	
	private static List<String> makeAuthors(List<SyndPerson> storyAuthors) {
		List<String> authors = new ArrayList<String>();
		
		for (SyndPerson author : storyAuthors) {
			authors.add(author.getName());
		}  // for loop
		
		return authors;
	}  // makeAuthors
	
	private static Blob makeImage(SyndEntry story) {
		// extract a URL, if one exists
		List<String> imgUrls = extractUrls(story);
		
		// quit if we couldn't grab any image URLs
		if (imgUrls == null) {
			return null;
		}  // if statement
		
		for (int i = 0; i < imgUrls.size(); i++) {
			
			if (!imgUrls.get(i).equals("") && imgUrls.get(i).length() > 0) {
				// grab image from URL endpoint
				byte[] imageByteArray = grabImageFromUrl(imgUrls.get(i));

				if (imageByteArray == null || imageByteArray.length <= 0) {
					continue;
				}  // if statement
				
				// modify the image to fit into the story-block
				try {
					Blob modifiedImage = modifyImage(imageByteArray);
					if (modifiedImage != null) {
						return new Blob(modifyImage(imageByteArray).getBytes());
					}  // if statement
				} catch (Exception e) {
					log.severe("Error modifying image from location '" + imgUrls.get(i) + "' - " + e.getMessage());
					e.printStackTrace();
				}
			}  // if statement
			
		}  // for loop
		
		return null;
	}  // makeImage
	
	private static Blob makeThumbnail(Blob blobImage) {
		if (blobImage == null) {
			return null;
		}  // if statement
		
		// modify thumbnail to thumbnail spec that we decide
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		Image image = ImagesServiceFactory.makeImage(blobImage.getBytes());
		
		// apply resize only if the original image size is too big
		if (image.getWidth() > MAX_THUMBNAIL_WIDTH || image.getHeight() > MAX_THUMBNAIL_HEIGHT) {
			Transform resize = ImagesServiceFactory.makeResize(MAX_THUMBNAIL_WIDTH, MAX_THUMBNAIL_HEIGHT);
			image = imagesService.applyTransform(resize, image);
		}  // if statement

		return new Blob(image.getImageData());
	}  // makeThumbnail
	
	private static List<String> extractUrls(SyndEntry story) {
		Parser parser = null;
		NodeFilter nodeFilter;
		NodeList nodeList;
		List<String> imgUrls = new ArrayList<String>();
		
		nodeFilter = new NodeClassFilter(ImageTag.class);
		
		// find <img /> tag and extract 'src' URL, if one exists
		try {
			// Evaluate contents first because we *want* the HTML tags so that we can strip out the <img /> tags
			if (story.getContents() != null && story.getContents().size() > 0 && story.getContents().get(0) != null && ((SyndContent) story.getContents().get(0)).getValue().length() > 0){
				parser = new Parser("<br />" + ((SyndContent) story.getContents().get(0)).getValue());
			} else if (story.getDescription() != null && story.getDescription().getValue().length() > 0) {
				parser = new Parser("<br />" + story.getDescription().getValue());
			} else {
				return null;
			}  // if-else statement
			
			// add URLs to list
			nodeList = parser.extractAllNodesThatMatch(nodeFilter);
			for (int i = 0; i < nodeList.size(); i++) {
				
				// Paying special attention to MMA Fighting (www.mmafighting.com).  Need to start with
				// second picture instead, since first picture is used as the author's avatar.
				if (story.getLink().contains("mmafighting.com") && i == 0) {
					continue;
				}  // if statement
				
				// Also special attention to BJPenn.com (www.bjpenn.com).  Need to start with
				// second picture as well, since first picture is used as the AddThis share image.
				if (story.getLink().contains("bjpenn.com") && i == 0) {
					continue;
				}  // if statement
				
				ImageTag img = (ImageTag)nodeList.elementAt(i);
				
				// Also paying special attention to MMA Convert (www.mmaconvert.com).  Need to remove
				// images inserted via FeedBurner, particularly the "E-mail This" image inserted when
				// using FeedFlare.
				if (img.getImageURL().contains("feeds.feedburner.com/~ff/mmaconvert")) {
					continue;
				}  // if statement
				
				imgUrls.add(img.getImageURL());
				
			}  // if statement
			
		} catch (ParserException e) {
			log.severe("Error extracting image URLs from story '" + story.getTitle() + "' - " + e.getMessage());
			e.printStackTrace();
		}  // try-catch statement
		
		return imgUrls;
	}  // extractUrls
	
	private static byte[] grabImageFromUrl(String urlString) {
		ByteArrayOutputStream bais = new ByteArrayOutputStream();
		
		try {
			URL url = new URL(urlString);
			URLConnection urlConnection = url.openConnection();
			InputStream inputStream = null;
			
			try {
				inputStream = urlConnection.getInputStream();
				byte[] byteChunk = new byte[4096];	// or whatever size to read in at a time
				
				int n;
				while ((n = inputStream.read(byteChunk)) > 0) {
					bais.write(byteChunk, 0, n);
				}  // while loop
			} catch (IOException e) {
				log.severe("Failed while reading bytes from " + url.toExternalForm() + ": " + e.getMessage());
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}  // if statement
			}  // try-catch statement
			
		} catch (Exception e) {
			log.severe("Error retrieving image from " + urlString + " - " + e.getMessage());
			e.printStackTrace();
		}  // try-catch statement
		
		return bais.toByteArray();
	}  // grabImageFromUrl
	
	private static Blob modifyImage(byte[] imageByteArray) throws IllegalArgumentException {
		// modify image to image spec that we decide
        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        Image image = ImagesServiceFactory.makeImage(imageByteArray);
        
		// if this is one of those fucking load-detection images, fuck it
		if (image.getWidth() <= 1 || image.getHeight() <= 1) {
			return null;
		}  // if statement
        
        // apply resize only if the original image size is too big
        if (image.getWidth() > MAX_IMAGE_WIDTH || image.getHeight() > MAX_IMAGE_HEIGHT) {
        	Transform resize = ImagesServiceFactory.makeResize(MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT);
        	image = imagesService.applyTransform(resize, image);
        }  // if statement
        
        return new Blob(image.getImageData());
	}  // modifyImage
}  // class declaration
