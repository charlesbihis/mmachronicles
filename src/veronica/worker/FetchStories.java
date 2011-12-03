package veronica.worker;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import veronica.feed.FeedManager;
import veronica.feed.vo.Feed;

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
}  // AddFeedServlet
