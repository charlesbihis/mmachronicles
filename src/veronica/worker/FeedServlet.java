package veronica.worker;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import veronica.story.StoryManager;
import veronica.story.vo.Story;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

public class FeedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(FeedServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("Generating RSS feed.");
		
		// Set up the feed
		SyndFeed syndFeed = new SyndFeedImpl();
		syndFeed.setFeedType("rss_2.0");
		syndFeed.setEncoding("windows-1252");
		syndFeed.setTitle("MMA Chronicles RSS Feed");
		syndFeed.setLink("http://www.mmachronicles.com/rss");
		syndFeed.setDescription("An undying eye on the sport of mixed-martial-arts.");
		
		// Grab the stories
		List<Story> stories = StoryManager.getStories();
		
		// Populate the feed
		List<SyndEntry> entries = new ArrayList<SyndEntry>();
		SyndEntry entry;
		SyndContent description;
		for (Story story : stories) {
			entry = new SyndEntryImpl();
			
			entry.setTitle(story.getTitle());
			entry.setLink(story.getUrl());
			entry.setPublishedDate(story.getPublishedDate());
			entry.setUpdatedDate(story.getUpdatedDate());
			description = new SyndContentImpl();
			description.setType("text/plain");
			description.setValue(story.getTeaser());
			entry.setDescription(description);
			
			entries.add(entry);
		}  // for loop
		syndFeed.setEntries(entries);
		
		// Output the feed
		resp.setContentType("application/rss+xml");
		SyndFeedOutput output = new SyndFeedOutput();
		Writer writer = new OutputStreamWriter(resp.getOutputStream());
		
		try {
			output.output(syndFeed, writer);
		} catch (FeedException e) {
			log.severe("Error outputting stream - " + e.getMessage());
			e.printStackTrace();
		}  // try-catch statement
	}  // doGet
}  // AddFeedServlet
