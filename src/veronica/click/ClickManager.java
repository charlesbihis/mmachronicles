package veronica.click;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import veronica.click.vo.Click;
import veronica.click.vo.Click.Source;
import veronica.click.vo.Click.Type;
import veronica.feed.vo.Feed;
import veronica.story.vo.Story;
import veronica.util.BigTableDao;

import com.google.appengine.api.datastore.Key;

public class ClickManager {
	private static final Logger log = Logger.getLogger(ClickManager.class.getName());
	
	public static void click(Type type, Source source, Key feedKey, Key storyKey) {
		PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
		
		try {
			Feed feed = persistenceManager.getObjectById(Feed.class, feedKey);
			Story story = persistenceManager.getObjectById(Story.class, storyKey);
			
			switch (type) {
			case FEED:
				feed.addClick();
				break;
			case STORY:
				feed.addClick();
				story.addClick();
				break;
			case SHARE:
				feed.addShare();
				story.addShare();
				break;
			case SITE:
				feed.addClick();
				break;
			case POPULAR:
				feed.addPopularlick();
				story.addPopularClick();
				break;
			default:
				throw new Error("Invalid Type passed to ClickManager");
			}  // switch statement
			
			// Create click and store it
			Click click = new Click.Builder(type, source, feedKey, storyKey, new Date()).build();
			persistenceManager.makePersistent(click);
			
			log.fine("Click - type:" + type.toString() + "    source:" + source.toString() + "    key:" + click.getKey().getId());
		} catch (Exception e) {
			log.severe("Error storing click - " + e.getMessage());
			e.printStackTrace();
		}  finally {
			persistenceManager.close();
		}  // try-catch-finally statement
	}  // click
	
	@SuppressWarnings("unchecked")
	public static List<Click> getClicks(int secondsSince) {
		PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
		Query query = persistenceManager.newQuery(Click.class);
		query.setFilter("date > minDate");
		query.setOrdering("date desc");
		query.declareParameters("Date minDate");
		query.declareImports("import java.util.Date");
		List<Click> clicks = (List<Click>)query.execute((new Date(new Date().getTime() - (secondsSince * 1000))));
		
		return clicks;
	}  // getClicks
	
	@SuppressWarnings("unchecked")
	public static int getClickCount(int secondsSince) {
		PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
		Query query = persistenceManager.newQuery(Click.class);
		query.setFilter("date > minDate");
		query.setOrdering("date desc");
		query.declareParameters("Date minDate");
		query.declareImports("import java.util.Date");
		List<Click> clicks = (List<Click>)query.execute((new Date(new Date().getTime() - (secondsSince * 1000))));
		
		return clicks.size();
	}  // getClickCount
}  // class declaration
