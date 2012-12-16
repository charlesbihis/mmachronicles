package veronica.worker;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import veronica.feed.vo.Feed;
import veronica.story.StoryManager;
import veronica.story.vo.Story;
import veronica.util.BigTableDao;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class DeleteFeed extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(DeleteFeed.class.getName());
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String feedKey = req.getParameter("feed-key");
		
		if (feedKey != null) {
			PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
			Key key = KeyFactory.createKey(Feed.class.getSimpleName(), Long.parseLong(feedKey));
			Feed feed = persistenceManager.getObjectById(Feed.class, key);
			List<Story> stories = StoryManager.getStoriesByFeed(feedKey);
			
			log.info("Deleting " + stories.size() + " stories for feed '" + feed.getTitle() + "'");
			PersistenceManager storyPersistenceManager;
			for (Story story : stories) {
				storyPersistenceManager = JDOHelper.getPersistenceManager(story);
				storyPersistenceManager.deletePersistent(story);
			}  // for loop
			
			log.info("Deleting feed '" + feed.getTitle() + "'");
			persistenceManager.deletePersistent(feed);
			
			resp.sendRedirect("/admin/feeds/");
		}  // if statement
    }  // doPost
}  // AddFeedServlet
