<%@	page contentType="text/html;charset=UTF-8"
		 language="java"
		 import="com.google.appengine.api.datastore.Key"
		 import="java.io.IOException"
		 import="java.io.FileInputStream"
		 import="java.util.Collections"
		 import="java.util.List"
		 import="java.util.logging.Logger"
		 import="java.util.Properties"
		 import="javax.jdo.PersistenceManager"
		 import="net.sf.jsr107cache.Cache"
		 import="net.sf.jsr107cache.CacheException"
		 import="net.sf.jsr107cache.CacheFactory"
		 import="net.sf.jsr107cache.CacheManager"
		 import="veronica.feed.vo.Feed"
		 import="veronica.story.StoryManager"
		 import="veronica.story.vo.Story"
		 import="veronica.util.BigTableDao"
%>

<%
	final Logger log = Logger.getLogger("veronica.web");
	Properties properties = new Properties();
	
	try {
		properties.load(new FileInputStream("veronica.properties"));
	} catch (IOException e) {
		log.severe("Error loading properties file - " + e.getMessage());
	}  // try-catch statement
	
	int currentPage;
	String currentPageString = request.getParameter("page");
	
	if (currentPageString != null) {
		currentPage = Integer.parseInt(currentPageString);
	} else {
		currentPage = 1;
	}  // if-else statement
%>


<%@page import="veronica.feed.FeedManager"%><html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title><%= properties.getProperty("site.title") + " - " + properties.getProperty("site.slogan") %></title>
</head>

<body>

	<h1><%= properties.getProperty("site.title") %></h1>
	<%
		List<Story> stories = StoryManager.getPopularStories();
		
		if (stories.isEmpty()) {
			out.println("There are no stories in the bucket.");
		} else {
			// for each story, get the feed info from the associated feed
			for (Story story : stories) {
				
				Feed feed = FeedManager.getFeed(story.getSourceFeedKey() + "");
				log.severe("" + stories.size());
				System.out.println("" + stories.size());
				// If we've deleted the source-feed, don't display this item. Perhaps we'd eventually like to do
				// something different, but for now, let's just keep it simple and skip this story.
				if (feed != null) {
					
					if (story.getImage() != null) {
						out.println("<img src='/image?id=" + story.getKey().getId() + "' />");						
					}  // if statement
					
					out.println("<a href='" + feed.getUrl() + "'>" + feed.getTitle() + "</a> - <a href='" + story.getUrl() + "' onMouseDown='this.href=\"/redirect?url=" + story.getUrl() + "&fkv=" + feed.getKey().getId() + "&skv=" + story.getKey().getId() + "\"'>" + story.getTitle() + "</a> (" + story.getClicks() + " clicks)<br />");
					out.println(story.getTeaser() + "<br /><br />");
				}  // if statement
				
			}  // for loop
		}  // if-else statement
	%>
	
	<p>PAGINATION</p>
	
	<a href="/?page=<%= currentPage - 1 %>">Prev</a>&nbsp;&nbsp;&nbsp;
	<a href="/?page=<%= currentPage - 1 %>"><%= currentPage - 1 %></a>&nbsp;&nbsp;&nbsp;
	<%= currentPage %>&nbsp;&nbsp;&nbsp;
	<a href="/?page=<%= currentPage + 1 %>"><%= currentPage + 1 %></a>&nbsp;&nbsp;&nbsp;
	<a href="/?page=<%= currentPage + 1 %>">Next</a>
	
</body>
	
</html>
