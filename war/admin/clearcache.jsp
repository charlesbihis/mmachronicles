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
%>


<%@page import="veronica.feed.FeedManager"%><html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title><%= properties.getProperty("site.title") + " - " + properties.getProperty("site.slogan") %></title>
</head>

<body>

	<%
	CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
	Cache cache = cacheFactory.createCache(Collections.emptyMap());
	
    log.info("Clearing " + cache.getCacheStatistics().getObjectCount() + " objects in cache");
    cache.clear(); 
	%>
	
</body>
	
</html>
