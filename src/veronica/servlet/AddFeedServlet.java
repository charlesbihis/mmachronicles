package veronica.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import veronica.BigTableDao;
import veronica.feed.vo.Feed;

public class AddFeedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(AddFeedServlet.class.getName());
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String feedUrl = req.getParameter("feed-url");
		String feedTitle = req.getParameter("feed-title");
		String feedFavicon = req.getParameter("feed-favicon");
		Integer feedPollRate = Integer.parseInt(req.getParameter("feed-poll-rate"));
		
		log.info("Persisting Feed: '" + feedTitle + " - " + feedUrl);
		Feed feed = new Feed.Builder(feedUrl, feedTitle)
						.favicon(feedFavicon)
						.pollRate(feedPollRate).build();
		PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
		
		try {
			persistenceManager.makePersistent(feed);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			persistenceManager.close();
		}  // try-catch-finally statement
		
		resp.sendRedirect("/admin/feeds/");
    }  // doPost
}  // AddFeedServlet
