<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Properties"%>
<%@page import="java.util.List"%>
<%@page import="veronica.feed.vo.Feed"%>
<%@page import="veronica.feed.FeedManager"%>

<%
	Properties properties = new Properties();
	
	try {
		properties.load(new FileInputStream("veronica.properties"));
	} catch (IOException e) {
		// do something
	}

	List<Feed> feeds = FeedManager.getFeeds();
	
	for (Feed feed : feeds) {
		FeedManager.fetchStories(feed);
	}
%>

<html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title><%= properties.getProperty("site.title") + " - Fetch Stories" %></title>
</head>

<body>
	<p>Fetching stories</p>
</body>

</html>