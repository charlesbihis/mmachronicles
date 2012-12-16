<%@	page contentType="text/html;charset=UTF-8"
		 language="java"
		 import="java.io.FileInputStream"
		 import="java.io.IOException"
		 import="java.util.logging.Logger"
		 import="java.util.Properties"
%>

<%
	final Logger log = Logger.getLogger("veronica.web");
	Properties properties = new Properties();
	
	try {
		properties.load(new FileInputStream("veronica.properties"));
	} catch (IOException e) {
		log.severe("Error loading properties file - " + e.getMessage());
		e.printStackTrace();
	}  // try-catch statement
	
	// fetch feeds
	List<Feed> feeds = FeedManager.getFeeds();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@page import="java.util.List"%>
<%@page import="veronica.feed.vo.Feed"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="veronica.story.vo.Story"%>
<%@page import="veronica.story.StoryManager"%>
<%@page import="veronica.feed.FeedManager"%>
<%@page import="java.util.TimeZone"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><%= properties.getProperty("site.title") + " - " + properties.getProperty("site.slogan") %></title>
	<link media="all" type="text/css" rel="stylesheet" href="/css/all.css" />
	<script type="text/javascript" src="/js/main.js"></script>
	<!--[if lt IE 7]><link rel="stylesheet" type="text/css" href="css/lt7.css" media="screen"/><![endif]-->
</head>
<body>
	<div id="wrapper">
		<!-- logo -->
		<h1 class="logo"><a href="/"><%= properties.getProperty("site.title") %> - Feeds</a></h1>
		<!-- main area -->
		<div id="main">
			<div id="twocolumns">
				<!-- content -->
				<div class="about">
					<h2>Feeds</h2>
					<form action="/worker/add-feed" method="post">
						<table>
							<tr>
								<td align="right">Site URL:</td>
								<td align="right"><input type="text" name="url" /></td>
							</tr>
							<tr>
								<td align="right">Site Name:</td>
								<td align="right"><input type="text" name="title" /></td>
							</tr>
							<tr>
								<td align="right">Feed URL:</td>
								<td align="right"><input type="text" name="feed-url" /></td>
							</tr>
							<tr>
								<td align="right">Favicon URL:</td>
								<td align="right"><input type="text" name="favicon" /></td>
							</tr>
							<tr>
								<td align="right">Poll Rate (in seconds):</td>
								<td align="right"><input type="text" name="poll-rate" /></td>
							</tr>
							<tr>
								<td align="right"></td>
								<td align="right"><input type="submit" value="Go!" /></td>
							</tr>
						</table>
					</form>
					
					<br />
					
					<table cellspacing="0" border="1" width="100%">
						<tr>
							<th>Feed (stream)</th>
							<th>Stories (popular)</th>
							<th>Clicks (this week)</th>
							<th>Shares (this week)</th>
							<th>Last Updated</th>
							<th>Update</th>
							<th>Delete!</th>
						</tr>
						<%
						if (!feeds.isEmpty()) {
							int storyCount;
							for (Feed feed : feeds) {
								//storyCount = StoryManager.getStoryCountByFeed(feed.getKey().getId() + "");
						%>
						<tr>
							<td><% out.println("<a href='/admin/feeds/feed.jsp?fid=" + feed.getKey().getId() + "'>" + feed.getTitle() + "</a> (" + "<a href='" + feed.getFeedUrl() + "'>feed</a>)"); %></td>
							<td><%= "should be storyCount" %></td>
							<td><% out.println(feed.getClicks() + " (?)"); %></td>
							<td><% out.println(feed.getShares() + " (?)"); %></td>
							<td><% DateFormat dateFormat = new SimpleDateFormat("h:mma MMM d, yyyy"); dateFormat.setTimeZone(TimeZone.getTimeZone("PST")); out.print(dateFormat.format(feed.getLastPoll())); %></td>
							<td align="center"><form action="/worker/fetch-stories" method="post"><input type="hidden" value="<%= feed.getKey().getId() %>" name="feed-key" /><input type="submit" value="Update" /></form></td>
							<td align="center"><form action="/worker/delete-feed" method="post"><input type="hidden" value="<%= feed.getKey().getId() %>" name="feed-key" /><input type="submit" value="Delete!" /></form></td>
						</tr>
						<%
							}  // for loop
						}  // if-else statement
						%>
					</table> 
				</div>
			</div>
			<div class="main-b">&nbsp;</div>
		</div>
		<jsp:include page="/inc/header.tpl" />
		<jsp:include page="/inc/footer.jsp" />
	</div>
</body>
</html>
