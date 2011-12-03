package veronica.daemon;

import java.util.logging.Logger;

import veronica.feed.FeedManager;
import veronica.feed.vo.Feed;

public class UpdateDaemonJob implements Runnable {
	private static final Logger log = Logger.getLogger(UpdateDaemonJob.class.getName());
	
	private final int sleepInterval = 1000;
	
	private FeedManager feedManager;
	
	public UpdateDaemonJob(FeedManager feedManager) {
		this.feedManager = feedManager;
	}  // UpdateDaemonJob

	@Override
	public void run() {
		while(true) {
			Feed feed = feedManager.poll();
			
			if (feed != null) {
				Runnable threadJob = new UpdateDaemonThread(feedManager, feed);
				Thread updateDaemonThread = new Thread(threadJob);
				updateDaemonThread.start();
			} else {
//				log.info("Idle.  Waiting for feed to re-enter the queue.  Checking again in " + sleepInterval / 1000 + " second(s)");
				try {
					Thread.sleep(sleepInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}  // try-catch statement
			} // if-else statement
		}  // while loop
	}  // run
}  // class declaration
