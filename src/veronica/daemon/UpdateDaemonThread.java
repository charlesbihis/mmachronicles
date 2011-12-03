package veronica.daemon;

import java.util.List;
import java.util.logging.Logger;

import veronica.feed.FeedManager;
import veronica.feed.vo.Feed;



public class UpdateDaemonThread implements Runnable {
	private static final Logger log = Logger.getLogger(UpdateDaemonThread.class.getName());
	
	private FeedManager feedManager;
	private Feed feed;
	
	public UpdateDaemonThread(FeedManager feedManager, Feed feed) {
		this.feedManager = feedManager;
		this.feed = feed;
	}  // UpdateDaemonThread

	@Override
	public void run() {
		// perform the update
		log.info("Retrieving stories for '" + feed.getTitle() + "...");
		
		List<Feed> feeds = FeedManager.getFeeds();
//		FeedManager.fetchStories(feed);
		
		// re-insert into the queue
		try {
//			Thread.sleep(feed.getPollRate() * 1000);
			if (feed.getTitle().equals("MMA Junkie")) {
				Thread.sleep(5000);		// sleep for 5 seconds
			} else if (feed.getTitle().equals("MMA Fighting")) {
				Thread.sleep(10000);	// sleep for 10 seconds
			}
			
			feedManager.enqueue(feed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}  // try-catch statement
	}  // run
}  // class declaration
