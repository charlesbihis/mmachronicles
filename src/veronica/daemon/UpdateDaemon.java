package veronica.daemon;

import static com.google.appengine.api.labs.taskqueue.TaskOptions.Builder.url;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import veronica.feed.FeedManager;
import veronica.feed.vo.Feed;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions.Method;

public class UpdateDaemon extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(UpdateDaemon.class.getName());
	
	private final int sleepInterval = 1000;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		FeedManager feedManager = FeedManager.getInstance();
		
		log.info("Initializing...");
		
		log.info("Populating feed queue...");
		List<Feed> feeds = FeedManager.getFeeds();
		for (Feed feed : feeds) {
			feedManager.enqueue(feed);
		}  // for loop
		
		System.out.println("ADDING TO QUEUE");
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(url("/worker/testServlet").method(Method.GET).param("something", "this is hello world!"));
		System.out.println("DONE");
		
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}  // try-catch statement
//		
//		System.out.println("ADDING TO QUEUE");
//		queue = QueueFactory.getDefaultQueue();
//		queue.add(url("/worker/testServlet").method(Method.GET).param("something", "this is hello world!"));
		System.out.println("DONE");
		
//		startDaemon(feedManager);
//		
//		Runnable updateDaemonJob = new UpdateDaemonJob(feedManager);
//		Thread updateDaemonThread = new Thread(updateDaemonJob);
//		updateDaemonThread.start();
	}
	
//	private void startDaemon(FeedManager feedManager) {
//		Feed feed;
//		
//		while(true) {
//			feed = feedManager.poll();
//			
//			if (feed != null) {
//				Runnable runnableUpdateDaemonThread = new UpdateDaemonThread(feedManager, feed);
//				Thread updateDaemonThread = new Thread(runnableUpdateDaemonThread);
//				updateDaemonThread.start();
//			} else {
//				log.info("Idle.  Waiting for feed to re-enter the queue.  Checking again in " + sleepInterval / 1000 + " second(s)");
//				try {
//					Thread.sleep(sleepInterval);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}  // try-catch statement
//			} // if-else statement
//		}  // while loop
//	}  // startDaemon
}  // class declaration
