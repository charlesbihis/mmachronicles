package veronica.story;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;
import veronica.feed.FeedManager;
import veronica.story.vo.Story;
import veronica.util.BigTableDao;

import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;

public class StoryManager {
	private static final Logger log = Logger.getLogger(FeedManager.class.getName());
	private static final String CACHED_PAGE_PREFIX = "cachedPage";
	private static final String CACHED_POPULAR_STORIES = "cachedPopularStories";
	private static final int MAX_STORIES_PER_PAGE = 15;
	private static final int MAX_POPULAR_STORIES = 7;
	
	public static List<Story> getStories() {
		return getStories(1);
	}  // getStories
	
	@SuppressWarnings("unchecked")
	public static List<Story> getStories(int page) {
		List<Story> stories = new ArrayList<Story>();
		Cache cache;
		
		// set the cache to expire every 10 minutes (600 seconds in 10 minutes)
		Map props = new HashMap();
        props.put(GCacheFactory.EXPIRATION_DELTA, 600);
        
		// check to see if the page to load is still in the cache
		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(props);

			if (cache.containsKey(CACHED_PAGE_PREFIX + page)) {
				stories = (List<Story>)cache.get(CACHED_PAGE_PREFIX + page);
			} else {
				log.fine("Storing page " + page + " in the cache.");
				PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
				String query = "SELECT FROM " + Story.class.getName() + " ORDER BY publishedDate DESC range " + ((page - 1) * MAX_STORIES_PER_PAGE) + ", " + (((page - 1) * MAX_STORIES_PER_PAGE) + MAX_STORIES_PER_PAGE);
				stories = (List<Story>)persistenceManager.newQuery(query).execute();
				
				// store this page back in the cache since it wasn't already in there
				List<Story> cachablePage = new ArrayList<Story>();
				cachablePage.addAll(stories);
				cache.put(CACHED_PAGE_PREFIX + page, cachablePage);
			}  // if-else statement
		} catch (CacheException e) {
			log.severe("Error getting stories from cache or datastore - " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			log.severe("Unknown error fetching/storing stories from/to cache - " + e.getMessage());
			e.printStackTrace();
		}  // try-catch statement
		
		return stories;
	}  // getStories
	
	public static List<Story> getStoriesByFeed(String feedKey) {
		return getStoriesByFeed(feedKey, 1);
	}  // getStoriesByFeed
	
	@SuppressWarnings("unchecked")
	public static List<Story> getStoriesByFeed(String feedKey, int page) {
		List<Story> stories = new ArrayList<Story>();
		
		PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
		String query = "SELECT FROM " + Story.class.getName() + " WHERE sourceFeedKey == '" + feedKey + "' ORDER BY publishedDate DESC range " + ((page - 1) * MAX_STORIES_PER_PAGE) + ", " + (((page - 1) * MAX_STORIES_PER_PAGE) + MAX_STORIES_PER_PAGE);
		stories = (List<Story>)persistenceManager.newQuery(query).execute();
		
		return stories;
	}  // getStoriesByFeed
	
	@SuppressWarnings("unchecked")
	public static int getStoryCountByFeed(String feedKey) {
		List<Story> stories = new ArrayList<Story>();
		
		PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
		String query = "SELECT FROM " + Story.class.getName() + " WHERE sourceFeedKey == '" + feedKey + "'";
		stories = (List<Story>)persistenceManager.newQuery(query).execute();
		
		return stories.size();
	}  // getStoryCountByFeed
	
	@SuppressWarnings("unchecked")
	public static List<Story> getPopularStories() {
		List<Story> popularStories = new ArrayList<Story>();
		Cache cache;
		
		// set the cache to expire every 5 minutes
		Map props = new HashMap();
        props.put(GCacheFactory.EXPIRATION_DELTA, 300);
        
		// check to see if the popular stories are still in the cache
		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(props);

			if (cache.containsKey(CACHED_POPULAR_STORIES)) {
				popularStories = (List<Story>)cache.get(CACHED_POPULAR_STORIES);
			} else {
				PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
				Query query = persistenceManager.newQuery(Story.class);
				query.setFilter("publishedDate > minDate");
				query.setOrdering("publishedDate desc");
				query.declareParameters("Date minDate");
				query.declareImports("import java.util.Date");
				List<Story> recentStories = (List<Story>)query.execute((new Date(new Date().getTime() - 259200000)));	// 3 days

				popularStories = new ArrayList<Story>(MAX_POPULAR_STORIES);
				Story story;
				Boolean added = false;
				
				for (int i = 0; i < recentStories.size(); i++) {
					story = recentStories.get(i);
					added = false;
					
					for (int j = 0; j < MAX_POPULAR_STORIES; j++) {
						if (popularStories.size() <= j) {
							popularStories.add(j, story);
							added = true;
						} else if (popularStories.get(j) == null) {
							popularStories.remove(j);
							popularStories.add(j, story);
							added = true;
						} else if (popularStories.get(j) != null && popularStories.get(j).getClicks() < story.getClicks()) {
							popularStories.add(j, story);
							popularStories.remove(popularStories.size() - 1);
							added = true;
						}  // if-else statement
						
						if (added) {
							break;
						}  // if statement
					}  // for loop
					
					if (added) {
						continue;
					}  // if statement
				}  // for loop
				
				// store this page back in the cache since it wasn't already in there
				log.fine("Storing popular stories in the cache.");
				List<Story> cachablePopularStories = new ArrayList<Story>();
				cachablePopularStories.addAll(popularStories);
				cache.put(CACHED_POPULAR_STORIES, cachablePopularStories);
			}  // if-else statement
		} catch (CacheException e) {
			log.severe("Error getting popular stories from cache or datastore - " + e.getMessage());
			e.printStackTrace();
		}  // try-catch statement
		
		return popularStories;
	}  // getPopularStories
	
//	@SuppressWarnings("unchecked")
//	public static List<Story> getStoriesByFeed(String feedKey) {
//		PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
//
//		String query = "SELECT FROM " + Story.class.getName() + " WHERE sourceFeedKey == '" + feedKey + "'";
//		List<Story> stories = (List<Story>)persistenceManager.newQuery(query).execute();
//		
//		return stories;
//	}  // getStoriesByFeed
}  // StoryManager
