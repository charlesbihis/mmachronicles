package veronica.worker;

import java.io.IOException;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import veronica.feed.vo.Feed;
import veronica.util.BigTableDao;

public class AddFeed extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(AddFeed.class.getName());
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String url = req.getParameter("url");
		String feedUrl = req.getParameter("feed-url");
		String title = req.getParameter("title");
		String favicon = req.getParameter("favicon");
		Integer pollRate = Integer.parseInt(req.getParameter("poll-rate"));
		
		log.info("Persisting Feed: '" + title + " - " + feedUrl);
		Feed feed = new Feed.Builder()
						.url(url)
						.feedUrl(feedUrl)
						.title(title)
						.favicon(favicon)
						.pollRate(pollRate).build();
		PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
		
		try {
			persistenceManager.makePersistent(feed);
		} catch (Exception e) {
			log.severe("Error storing feed " + feed.getTitle() + " - " + e.getMessage());
			e.printStackTrace();
		} finally {
			persistenceManager.close();
		}  // try-catch-finally statement
		
		resp.sendRedirect("/admin/feeds/");
    }  // doPost
}  // AddFeedServlet
