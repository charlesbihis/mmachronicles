package veronica;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import veronica.feed.FeedManager;
import veronica.feed.vo.Feed;

public class VeronicaEngine extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(VeronicaEngine.class.getName());
	
	@Override
	public void init() throws ServletException {
		
		log.info("Initializing Veronica Engine...");
		
		log.info("Fetching stories from feeds");
		List<Feed> feeds = FeedManager.getFeeds();
		for (Feed feed : feeds) {
			FeedManager.fetchStories(feed);
		}  // for loop
		
		log.info("Initialization complete");
		
	}  // init
}  // class declaration
