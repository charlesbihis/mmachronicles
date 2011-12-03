package veronica.feed;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import veronica.feed.vo.Feed;
import veronica.story.vo.Story;
import veronica.util.BigTableDao;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class FeedManager {
	private static final Logger log = Logger.getLogger(FeedManager.class.getName());
//	private static final String CACHED_FEEDS_KEY = "cachedFeeds";
	
	@SuppressWarnings("unchecked")
	public static List<Feed> getFeeds() {
//		List<Feed> feeds = new ArrayList<Feed>();
//		Cache cache;
//
//		// check to see if the feeds are still in the cache
//		try {
//			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
//			cache = cacheFactory.createCache(Collections.emptyMap());
//
//			if (cache.containsKey(CACHED_FEEDS_KEY)) {
//				System.out.println("CACHE HIT!  FEEDS PULLED FROM CACHE!");
//				return (List<Feed>)cache.get(CACHED_FEEDS_KEY);
//			} else {
//				System.out.println("cache miss...re-inserting feeds into the cache");
//				PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
//				String query = "SELECT FROM " + Feed.class.getName();
//				
//				feeds = (List<Feed>) persistenceManager.newQuery(query).execute();
//				cache.put(CACHED_FEEDS_KEY, feeds);
//			}  // if-else statement
//		} catch (CacheException e) {
//			e.printStackTrace();
//		}  // try-catch statement
		
		PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
		String query = "SELECT FROM " + Feed.class.getName();
		
		return (List<Feed>) persistenceManager.newQuery(query).execute();
	}  // getFeeds
	
	@SuppressWarnings("unchecked")
	public static void fetchStories(Feed feed) {
		PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
		
		try {
			URL feedUrl = new URL(feed.getUrl());
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed syndFeed = input.build(new XmlReader(feedUrl));
			List<SyndEntry> stories = syndFeed.getEntries();

			for (SyndEntry story : stories) {
				Story storyObj = new Story.Builder(story.getTitle(), story.getLink(), makeTeaser(story), feed.getKey())
									.author(story.getAuthor())
									.publishedDate(story.getPublishedDate())
									.updatedDate(story.getUpdatedDate()).build();
				
				// only try and publish stories with a date later than the last-poll-date of our feed
				if (storyObj.getPublishedDate().compareTo(feed.getLastPoll()) > 0) {
					log.info("New story: " + feed.getTitle() + " - '" + story.getTitle() + "'");
					persistenceManager.makePersistent(storyObj);
				}  // if statement

				// update the last-poll-date of the feed
				Feed storedFeed = persistenceManager.getObjectById(Feed.class, feed.getKey());
				storedFeed.setLastPoll(new Date());
			}  // for loop
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			persistenceManager.close();
		}  // try-catch-finally statement
	}  // fetchStories
	
	// TODO Make better teaser-generator
	private static String makeTeaser(SyndEntry story) {
		return story.getDescription().getValue().replaceAll("\\<.*?>","").substring(0, 200) + "...";
	}  // makeTeaser
}  // class declaration
