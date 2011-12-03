package veronica.feed;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import veronica.BigTableDao;
import veronica.feed.vo.Feed;
import veronica.story.vo.Story;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class FeedManager {
	private static final Logger log = Logger.getLogger(FeedManager.class.getName());
	private static FeedManager instance = null;
	
	private Queue<Feed> feedQueue;
	
	private FeedManager() {
		feedQueue = new LinkedList<Feed>();
	}
	
	public static FeedManager getInstance() {
		if (instance == null) {
			instance = new FeedManager();
		}  // if statement
		
		return instance;
	}  // getInstance
	
	@SuppressWarnings("unchecked")
	public static List<Feed> getFeeds() {
		PersistenceManager pm = BigTableDao.get().getPersistenceManager();
		String query = "SELECT FROM " + Feed.class.getName();
		
		return (List<Feed>) pm.newQuery(query).execute();
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

				log.info("  -persisting story '" + story.getTitle() + "'");
				persistenceManager.makePersistent(storyObj);
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
	}
	
	public boolean enqueue(Feed feed) {
		boolean result;
		
		synchronized(feedQueue) {
			result = feedQueue.offer(feed);
		}  // synchronized
		
		return result;
	}  // enqueue
	
	public Feed poll() {
		Feed feed;
		
		synchronized(feedQueue) {
			feed = feedQueue.poll();
		}  // synchronized
		
		return feed;
	}  // poll
}  // class declaration
