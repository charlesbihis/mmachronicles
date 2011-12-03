<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Properties"%>
<%@page import="veronica.feed.FeedManager"%>
<%@page import="veronica.feed.vo.Feed"%>

<%
	Properties properties = new Properties();
	
	try {
		properties.load(new FileInputStream("veronica.properties"));
	} catch (IOException e) {
		// do something
	}
%>

<html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title><%= properties.getProperty("site.title") + " - Administer Feeds" %></title>
</head>

<body>
	
	<h3>Add Feed:</h3>
	<form action="/worker/add-feed" method="post">
		URL:&nbsp;&nbsp;&nbsp;<input type="text" name="feed-url" />&nbsp;&nbsp;&nbsp;
		Title:&nbsp;&nbsp;&nbsp;<input type="text" name="feed-title" />&nbsp;&nbsp;&nbsp;
		Favicon URL:&nbsp;&nbsp;&nbsp;<input type="text" name="feed-favicon" />&nbsp;&nbsp;&nbsp;
		Poll Rate (in seconds):&nbsp;&nbsp;&nbsp;<input type="text" name="feed-poll-rate" />&nbsp;&nbsp;&nbsp;
		<input type="submit" value="Go!" />
	</form>
	
	<h3>Aggregated Feeds:</h3>
	<%
	List<Feed> feeds = FeedManager.getFeeds();
	
	if (feeds.isEmpty()) {
		out.println("You are not aggregating any feeds.");
	} else {
		for (Feed feed : feeds) {
			out.println(feed.getTitle() + " - <a href='" + feed.getUrl() + "'>" + feed.getUrl() + "</a><br />");
		}  // for loop
	}  // if-else statement
	%>
</body>

</html>