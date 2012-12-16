package veronica.worker;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.sun.syndication.feed.atom.Feed;

import veronica.click.ClickManager;
import veronica.click.vo.Click.Source;
import veronica.click.vo.Click.Type;
import veronica.story.vo.Story;

public class Redirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private static final Logger log = Logger.getLogger(Redirect.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getParameter("url");
		Type type = Type.valueOf(req.getParameter("type"));
		Source source = Source.valueOf(req.getParameter("src"));
		String feedKeyValue = req.getParameter("fkey");
		String storyKeyValue = req.getParameter("skey");
		
		// create the keys
		Key feedKey = KeyFactory.createKey(null, Feed.class.getSimpleName(), Integer.parseInt(feedKeyValue));
		Key storyKey = null;
		if (storyKeyValue != null) {
			 storyKey = KeyFactory.createKey(null, Story.class.getSimpleName(), Integer.parseInt(storyKeyValue));
		}  // if statement
		
		// record the click
		ClickManager.click(type, source, feedKey, storyKey);
		
		// send redirect
		resp.sendRedirect(url);
	}  // doGet
}  // class declaration
