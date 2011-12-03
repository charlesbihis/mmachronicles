package veronica.story;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import veronica.BigTableDao;
import veronica.story.vo.Story;

public class StoryManager {
	private static final int STORIES_PER_PAGE = 15;
	private static final Logger log = Logger.getLogger(StoryManager.class.getName());
	
	public static List<Story> getStories() {
		return getStories(1);
	}  // getStories
	
	@SuppressWarnings("unchecked")
	public static List<Story> getStories(int page) {
		PersistenceManager pm = BigTableDao.get().getPersistenceManager();
		String query = "SELECT FROM " + Story.class.getName() + " ORDER BY publishedDate DESC range " + ((page - 1) * STORIES_PER_PAGE) + ", " + (((page - 1) * STORIES_PER_PAGE) + STORIES_PER_PAGE);

		return (List<Story>) pm.newQuery(query).execute();
	}  // getStories
}  // StoryManager
