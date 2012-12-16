<%@	page contentType="text/html;charset=UTF-8"
		 language="java"
		 import="com.google.appengine.api.datastore.Key"
		 import="java.io.IOException"
		 import="java.io.FileInputStream"
		 import="java.net.URL"
		 import="java.net.URLEncoder"
		 import="java.util.Collections"
		 import="java.util.List"
		 import="java.util.logging.Logger"
		 import="java.util.Properties"
		 import="javax.jdo.PersistenceManager"
		 import="veronica.click.vo.Click"
		 import="veronica.click.vo.Click.Type"
		 import="veronica.feed.FeedManager"
		 import="veronica.feed.vo.Feed"
		 import="veronica.story.StoryManager"
		 import="veronica.story.vo.Story"
		 import="veronica.util.BigTableDao"
		 import="veronica.util.RelativeDate"
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
	
	String feedKey = request.getParameter("fid");
	
	int currentPage;
	String currentPageString = request.getParameter("page");
	
	if (currentPageString != null) {
		currentPage = Integer.parseInt(currentPageString);
	} else {
		currentPage = 1;
	}  // if-else statement
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><%= properties.getProperty("site.title") + " - " + properties.getProperty("site.slogan") %></title>
	<link media="all" type="text/css" rel="stylesheet" href="/css/all.css" />
	<link rel="alternate" type="application/rss+xml" title="<%= properties.getProperty("rss.title") %>" href="<%= properties.getProperty("rss.link") %>" />
	<script type="text/javascript" src="/js/main.js"></script>
	<!--[if lt IE 7]><link rel="stylesheet" type="text/css" href="css/lt7.css" media="screen"/><![endif]-->
</head>
<body>
	<!-- analytics -->
	<jsp:include page="/inc/analytics/analytics.tpl" />

	<div id="wrapper">
		<!-- logo -->
		<h1 class="logo"><a href="/"><%= properties.getProperty("site.title") %></a></h1>
		<!-- ad -->
		<jsp:include page="/inc/ads/banner.tpl" />
		<!-- main area -->
		<div id="main">
			<div id="twocolumns">
				<!-- content -->
				<div id="content">
					<!-- posts -->
					<div class="posts">
						<!-- post item -->
						<%
						List<Story> stories = StoryManager.getStoriesByFeed(feedKey, currentPage);
						
						if (stories.isEmpty()) {
							out.println("There are no stories to display.");
						} else {
							// for each story, get the feed info from the associated feed
							for (Story story : stories) {
								
								Feed feed = FeedManager.getFeed(story.getSourceFeedKey() + "");
								
								// If we've deleted the source-feed, don't display this item. Perhaps we'd eventually like to do
								// something different, but for now, let's just keep it simple and skip this story.
								if (feed != null) {
						%>
						<div class="post">			
							<h2><% out.println("<a href=\"" + story.getUrl() + "\" onMouseDown=\"this.href='/redirect?url=" + story.getUrl() + "&type=" + Click.Type.STORY + "&src=" + Click.Source.STORY_TITLE + "&fkey=" + feed.getKey().getId() + "&skey=" + story.getKey().getId() + "'\">"); %><%= story.getTitle() %><%= "</a>" %></h2>
							<div class="meta">
								<% out.println("<a href=\"" + feed.getUrl() + "\" onMouseDown=\"this.href='/redirect?url=" + feed.getUrl() + "&type=" + Click.Type.SITE + "&src=" + Click.Source.WEBSITE_TITLE + "&fkey=" + feed.getKey().getId() + "&skey=" + story.getKey().getId() + "'\" class=\"www\" style=\"background: url(" + ((feed.getFavicon() != null && feed.getFavicon().length() > 1) ? feed.getFavicon() : "../images/ico-man.gif") + ") no-repeat;\">"); %><%= new URL(feed.getUrl()).getHost() %><%= "</a>" %>
								<em class="date">
								<%
								out.print("Posted " + RelativeDate.getRelativeDate(story.getPublishedDate()));
								
								// print the story's author, if we know it
								if (story.getAuthors().size() == 1) {
									out.print(" by <font color='#464646'>");
									out.print(story.getAuthors().get(0));
									out.print("</font>");
								} else if (story.getAuthors().size() == 2) {
									out.print(" by <font color='#464646'>");
									out.print(story.getAuthors().get(0) + " and " + story.getAuthors().get(1));
									out.print("</font>");
								} else if (story.getAuthors().size() > 2) {
									out.print(" by <font color='#464646'>");
									for (int i = 0; i < story.getAuthors().size() - 1; i++) {
										out.print(story.getAuthors().get(i) + ", ");
									}  // for loop
									out.print(story.getAuthors().get(story.getAuthors().size()));
									out.print("</font>");
								} else if (story.getAuthor() != null && !story.getAuthor().equals("")) {
									out.print(" by <font color='#464646'>");
									out.print(story.getAuthor());
									out.print("</font>");
								}  // if-else statement
								%>
								</em>
							</div>
							<div class="holder">
								<%
								if (story.getImage() != null) {
									out.println("<div class=\"image\"><a href=\"" + story.getUrl() + "\" onMouseDown=\"this.href='/redirect?url=" + story.getUrl() + "&type=" + Click.Type.STORY + "&src=" + Click.Source.STORY_IMAGE + "&fkey=" + feed.getKey().getId() + "&skey=" + story.getKey().getId() + "'\"><img src=\"/image?id=" + story.getKey().getId() + "\" alt=\"Image for story '" + story.getTitle() + "'\" /></a></div>");						
								}  // if statement
								%>
								
								<p><%= story.getTeaser() %></p>
								<span class="more"><% out.println("<a href=\"" + story.getUrl() + "\" onMouseDown=\"this.href='/redirect?url=" + story.getUrl() + "&type=" + Click.Type.STORY + "&src=" + Click.Source.STORY_READ_MORE + "&fkey=" + feed.getKey().getId() + "&skey=" + story.getKey().getId() + "'\">"); %>Read More &raquo;<%= "</a>" %></span>
							</div>
							<!-- social -->
							<ul class="social">
								<li class="email"><% out.println("<a href=\"mailto:?subject=Article: " + story.getTitle() + "&body=I wanted to share this story with you...%0D%0A%0D%0A" + story.getTitle() + " - " + story.getUrl() + "%0D%0A%0D%0A%0D%0AShared from " + properties.getProperty("site.title") + "\" onMouseDown=\"this.href='/redirect?type=" + Click.Type.SHARE + "&src=" + Click.Source.SHARE_BAR_EMAIL + "&fkey=" + feed.getKey().getId() + "&skey=" + story.getKey().getId() + "&url=mailto:?subject=Article: " + URLEncoder.encode(story.getTitle() + "&body=I wanted to share this story with you...%0D%0A%0D%0A" + story.getTitle() + " - " + story.getUrl() + "%0D%0A%0D%0A%0D%0A-Shared from " + properties.getProperty("site.title") + " (" + properties.getProperty("site.url") + ")", "UTF-8") + "'\">"); %>E-mail<span>Friend</span><%= "</a>" %></li>
								<li class="twitter"><% out.println("<a href=\"http://twitter.com/home?status=" + URLEncoder.encode(story.getTitle().substring(0, (Math.min(140 - story.getUrl().length() - 3, story.getTitle().length() - 3) >= 0) ? Math.min(140 - story.getUrl().length() - 3, story.getTitle().length() - 3) : 0), "UTF-8") + "%3A+" + URLEncoder.encode(story.getUrl(), "UTF-8") + "\" onMouseDown=\"this.href='/redirect?url=http://twitter.com/home?status=" + story.getTitle().substring(0, (Math.min(140 - story.getUrl().length() - 3, story.getTitle().length() - 3) >= 0) ? Math.min(140 - story.getUrl().length() - 3, story.getTitle().length() - 3) : 0) + "%3A+" + URLEncoder.encode(story.getUrl(), "UTF-8") + "&type=" + Click.Type.SHARE + "&src=" + Click.Source.SHARE_BAR_TWITTER + "&fkey=" + feed.getKey().getId() + "&skey=" + story.getKey().getId() + "'\">"); %>Share on<span>Twitter</span><%= "</a>" %></li>
								<li class="facebook"><% out.println("<a href=\"http://www.facebook.com/sharer.php?t=" + URLEncoder.encode(story.getTitle(), "UTF-8") + "&u=" + URLEncoder.encode(story.getUrl(), "UTF-8") + "\" onMouseDown=\"this.href='/redirect?url=" + URLEncoder.encode("http://www.facebook.com/sharer.php?t=", "UTF-8") + URLEncoder.encode(story.getTitle(), "UTF-8") + URLEncoder.encode("&u=", "UTF-8") + URLEncoder.encode(story.getUrl(), "UTF-8") + "&type=" + Click.Type.SHARE + "&src=" + Click.Source.SHARE_BAR_FACEBOOK + "&fkey=" + feed.getKey().getId() + "&skey=" + story.getKey().getId() + "'\">"); %>Share on<span>Facebook</span><%= "</a>" %></li>
								<li class="myspace"><% out.println("<a href=\"http://www.myspace.com/Modules/PostTo/Pages/?c=" + URLEncoder.encode(story.getUrl(), "UTF-8") + "\" onMouseDown=\"this.href='/redirect?url=" + URLEncoder.encode("http://www.myspace.com/Modules/PostTo/Pages/?c=", "UTF-8") + URLEncoder.encode(story.getUrl(), "UTF-8") + "&type=" + Click.Type.SHARE + "&src=" + Click.Source.SHARE_BAR_MYSPACE + "&fkey=" + feed.getKey().getId() + "&skey=" + story.getKey().getId() + "'\">"); %>Share on<span>MySpace</span><%= "</a>" %></li>
								<li class="delicious"><% out.println("<a href=\"http://delicious.com/post?v=4;url=" + URLEncoder.encode(story.getUrl(), "UTF-8") + "\" onMouseDown=\"this.href='/redirect?url=" + URLEncoder.encode("http://delicious.com/post?v=4;url=", "UTF-8") + URLEncoder.encode(story.getUrl(), "UTF-8") + "&type=" + Click.Type.SHARE + "&src=" + Click.Source.SHARE_BAR_DELICIOUS + "&fkey=" + feed.getKey().getId() + "&skey=" + story.getKey().getId() + "'\">"); %>Share on<span>Delicious</span><%= "</a>" %></li>
								<li class="digg"><% out.println("<a href=\"http://digg.com/submit?url=" + URLEncoder.encode(story.getUrl(), "UTF-8") + "\" onMouseDown=\"this.href='/redirect?url=" + URLEncoder.encode("http://digg.com/submit?url=", "UTF-8") + URLEncoder.encode(story.getUrl(), "UTF-8") + "&type=" + Click.Type.SHARE + "&src=" + Click.Source.SHARE_BAR_DIGG + "&fkey=" + feed.getKey().getId() + "&skey=" + story.getKey().getId() + "'\">"); %>Share on<span>Digg</span><%= "</a>" %></li>
								<li class="google"><% out.println("<a href=\"http://www.google.com/reader/link?url=" + URLEncoder.encode(story.getUrl(), "UTF-8") + "&title=" + URLEncoder.encode(story.getTitle(), "UTF-8") + "\" onMouseDown=\"this.href='/redirect?url=" + URLEncoder.encode("http://www.google.com/reader/link?url=", "UTF-8") + URLEncoder.encode(story.getUrl(), "UTF-8") + URLEncoder.encode("&title=", "UTF-8") + URLEncoder.encode(story.getTitle(), "UTF-8") + story.getUrl() + "&type=" + Click.Type.SHARE + "&src=" + Click.Source.SHARE_BAR_GOOGLE_READER + "&fkey=" + feed.getKey().getId() + "&skey=" + story.getKey().getId() + "'\">"); %>Google<span>Reader</span><%= "</a>" %></li>
								<li class="rss"><% out.println("<a href=\"" + feed.getFeedUrl() + "\" onMouseDown=\"this.href='/redirect?url=" + feed.getFeedUrl() + "&type=" + Click.Type.FEED + "&src=" + Click.Source.SHARE_BAR_RSS + "&fkey=" + feed.getKey().getId() + "&skey=" + story.getKey().getId() + "'\">"); %>RSS<span>Feed</span><%= "</a>" %></li>
							</ul>	
						</div>		
						<%			
								}  // if statement
								
							}  // for loop
						}  // if-else statement
						%>
					</div>
					<!-- paging -->
					<div class="paging">
						<ul>
							<%
							if (currentPage == 1) {
							%>
							<li class="prev disable"><a href="#">&laquo; Prev</a></li>
							<li><strong>1</strong></li>
							<li><a href="/admin/feeds/feed.jsp?page=2&fid=<%= feedKey %>">2</a></li>
							<li><a href="/admin/feeds/feed.jsp?page=3&fid=<%= feedKey %>">3</a></li>
							<li><a href="/admin/feeds/feed.jsp?page=4&fid=<%= feedKey %>">4</a></li>
							<li><a href="/admin/feeds/feed.jsp?page=5&fid=<%= feedKey %>">5</a></li>
							<li class="next"><a href="/admin/feeds/feed.jsp?page=2&fid=<%= feedKey %>">Next &raquo;</a></li>
							<%
							} else if (currentPage == 2) {
							%>
							<li class="prev"><a href="/">&laquo; Prev</a></li>
							<li><a href="/admin/feeds/feed.jsp?fid=<%= feedKey %>">1</a></li>
							<li><strong>2</strong></li>
							<li><a href="/admin/feeds/feed.jsp?page=3&fid=<%= feedKey %>">3</a></li>
							<li><a href="/admin/feeds/feed.jsp?page=4&fid=<%= feedKey %>">4</a></li>
							<li><a href="/admin/feeds/feed.jsp?page=5&fid=<%= feedKey %>">5</a></li>
							<li class="next"><a href="/?page=3&fid=<%= feedKey %>">Next &raquo;</a></li>
							<%
							} else if (currentPage == 3) {
							%>
							<li class="prev"><a href="/?page=2&fid=<%= feedKey %>">&laquo; Prev</a></li>
							<li><a href="/admin/feeds/feed.jsp?page=1&fid=<%= feedKey %>">1</a></li>
							<li><a href="/admin/feeds/feed.jsp?page=2&fid=<%= feedKey %>">2</a></li>
							<li><strong>3</strong></li>
							<li><a href="/admin/feeds/feed.jsp?page=4&fid=<%= feedKey %>">4</a></li>
							<li><a href="/admin/feeds/feed.jsp?page=5&fid=<%= feedKey %>">5</a></li>
							<li class="next"><a href="/?page=4&fid=<%= feedKey %>">Next &raquo;</a></li>
							<%
							} else if (currentPage > 3) {
							%>
							<li class="prev"><a href="/?page=<%= currentPage - 1 %>&fid=<%= feedKey %>">&laquo; Prev</a></li>
							<li><a href="/admin/feeds/feed.jsp?page=<%= currentPage - 2 %>&fid=<%= feedKey %>"><%= currentPage - 2 %></a></li>
							<li><a href="/admin/feeds/feed.jsp?page=<%= currentPage - 1 %>&fid=<%= feedKey %>"><%= currentPage - 1 %></a></li>
							<li><strong><%= currentPage %></strong></li>
							<li><a href="/admin/feeds/feed.jsp?page=<%= currentPage + 1 %>&fid=<%= feedKey %>"><%= currentPage + 1 %></a></li>
							<li><a href="/admin/feeds/feed.jsp?page=<%= currentPage + 2 %>&fid=<%= feedKey %>"><%= currentPage + 2 %></a></li>
							<li class="next"><a href="/admin/feeds/feed.jsp?page=<%= currentPage + 1 %>&fid=<%= feedKey %>">Next &raquo;</a></li>
							<%
							}
							%>
							
						</ul>
					</div>
				</div>
				<jsp:include page="/inc/sidebar.jsp" />
			</div>
			<div class="main-b">&nbsp;</div>
		</div>
		<jsp:include page="/inc/header.tpl" />
		<jsp:include page="/inc/footer.jsp" />
	</div>
</body>
</html>
