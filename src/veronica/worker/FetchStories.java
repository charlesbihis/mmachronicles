package veronica.worker;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import veronica.feed.FeedManager;
import veronica.feed.vo.Feed;
import veronica.util.BigTableDao;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class FetchStories extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(FetchStories.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("Fetching stories for all feeds.");
		List<Feed> feeds = FeedManager.getFeeds();
		
		for (Feed feed : feeds) {
			FeedManager.fetchStories(feed);
		}  // for loop
	}  // doGet
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String feedKey = req.getParameter("feed-key");
		
		PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
		Key key = KeyFactory.createKey(Feed.class.getSimpleName(), Long.parseLong(feedKey));
		Feed feed = persistenceManager.getObjectById(Feed.class, key);
		
		log.info("Fetching stories for feed '" + feed.getTitle() + "'");
		FeedManager.fetchStories(feed);
		
		resp.sendRedirect("/admin/feeds/");
	}  // doPost
}  // AddFeedServlet
