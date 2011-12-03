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
	
	@SuppressWarnings("unchecked")
	public static List<Feed> getFeeds() {
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
