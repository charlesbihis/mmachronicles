<%@	page contentType="text/html;charset=UTF-8"
		 language="java"
		 import="java.io.FileInputStream"
		 import="java.io.IOException"
		 import="java.util.List"
		 import="java.util.logging.Logger"
		 import="java.util.Properties"
		 import="veronica.click.ClickManager"
		 import="veronica.feed.FeedManager"
		 import="veronica.feed.vo.Feed"
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

<%@page import="veronica.click.vo.Click"%>
<%@page import="veronica.click.vo.Click.Type"%><html xmlns="http://www.w3.org/1999/xhtml">
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
		<h1 class="logo"><a href="/"><%= properties.getProperty("site.title") %> - Clicks</a></h1>
		<!-- main area -->
		<div id="main">
			<div id="twocolumns">
				<!-- content -->
				<div class="about">
					<h2>Clicks</h2>
					<p>
					<%
					List<Click> todayClicks = ClickManager.getClicks(86400);
					
					int todayTotalClicks = todayClicks.size();
					int todayStoryClicks = 0;
					int todayFeedClicks = 0;
					int todayShareClicks = 0;
					int todaySiteClicks = 0;
					int todayPopularClicks = 0;
					
					int todayStoryTitleClicks = 0;
					int todayStoryImageClicks = 0;
					int todayStoryReadMoreClicks = 0;
					
					int todayPopularTextClicks = 0;
					int todayPopularThumbnailClicks = 0;
					
					int todayShareEmailClicks = 0;
					int todayShareTwitterClicks = 0;
					int todayShareFacebookClicks = 0;
					int todayShareMySpaceClicks = 0;
					int todayShareDeliciousClicks = 0;
					int todayShareDiggClicks = 0;
					int todayShareGoogleReaderClicks = 0;
					
					for (Click click : todayClicks) {
						switch (click.getType()) {
						case STORY:
							todayStoryClicks++;
							if (click.getSource().compareTo(Click.Source.STORY_TITLE) == 0) {
								todayStoryTitleClicks++;
							} else if (click.getSource().compareTo(Click.Source.STORY_IMAGE) == 0) {
								todayStoryImageClicks++;
							} else if (click.getSource().compareTo(Click.Source.STORY_READ_MORE) == 0) {
								todayStoryReadMoreClicks++;
							} // if-else statement
							break;
						case FEED:
							todayFeedClicks++;
							break;
						case SHARE:
							todayShareClicks++;
							if (click.getSource().compareTo(Click.Source.SHARE_BAR_EMAIL) == 0) {
								todayShareEmailClicks++;
							} else if (click.getSource().compareTo(Click.Source.SHARE_BAR_TWITTER) == 0) {
								todayShareTwitterClicks++;
							} else if (click.getSource().compareTo(Click.Source.SHARE_BAR_FACEBOOK) == 0) {
								todayShareFacebookClicks++;
							} else if (click.getSource().compareTo(Click.Source.SHARE_BAR_MYSPACE) == 0) {
								todayShareMySpaceClicks++;
							} else if (click.getSource().compareTo(Click.Source.SHARE_BAR_DELICIOUS) == 0) {
								todayShareDeliciousClicks++;
							} else if (click.getSource().compareTo(Click.Source.SHARE_BAR_DIGG) == 0) {
								todayShareDiggClicks++;
							} else if (click.getSource().compareTo(Click.Source.SHARE_BAR_GOOGLE_READER) == 0) {
								todayShareGoogleReaderClicks++;
							}  // if-else statement
							break;
						case SITE:
							todaySiteClicks++;
							break;
						case POPULAR:
							todayPopularClicks++;
							if (click.getSource().compareTo(Click.Source.POPULAR_TEXT) == 0) {
								todayPopularTextClicks++;
							} else if (click.getSource().compareTo(Click.Source.POPULAR_THUMBNAIL) == 0) {
								todayPopularThumbnailClicks++;
							}  // if-else statement
							break;
						default:
							throw new Error("Invalid Type passed to /admin/clicks/index.jsp");
						}  // switch statement
					}  // for loop
					%>
						Clicks today: <%= todayTotalClicks %>
						<ul>
							<li>Story Clicks: <%= todayStoryClicks %></li>
							<li>Feed Clicks: <%= todayFeedClicks %></li>
							<li>Share Clicks: <%= todayShareClicks %></li>
							<li>Site Clicks: <%= todaySiteClicks %></li>
							<li>Popular Clicks: <%= todayPopularClicks %></li>
						</ul>
						<ul>
							<li>Story Click Breakdown:</li>
							<ul>
								<li>via Title: <%= todayStoryTitleClicks %></li>
								<li>via Image: <%= todayStoryImageClicks %></li>
								<li>via ReadMore: <%= todayStoryReadMoreClicks %></li>
							</ul>
						</ul>
						<ul>
							<li>Popular Click Breakdown:</li>
							<ul>
								<li>via Text: <%= todayPopularTextClicks %></li>
								<li>via Thumbnail: <%= todayPopularThumbnailClicks %></li>
							</ul>
						</ul>
						<ul>
							<li>Share Click Breakdown:</li>
							<ul>
								<li>via E-mail: <%= todayShareEmailClicks %></li>
								<li>via Twitter: <%= todayShareTwitterClicks %></li>
								<li>via Facebook: <%= todayShareFacebookClicks %></li>
								<li>via MySpace: <%= todayShareMySpaceClicks %></li>
								<li>via Delicious: <%= todayShareDeliciousClicks %></li>
								<li>via Digg: <%= todayShareDiggClicks %></li>
								<li>via Google Reader: <%= todayShareGoogleReaderClicks %></li>
							</ul>
						</ul>
					</p>
					<p>
					<%
					List<Click> weekClicks = ClickManager.getClicks(604800);
					
					int weekTotalClicks = weekClicks.size();
					int weekStoryClicks = 0;
					int weekFeedClicks = 0;
					int weekShareClicks = 0;
					int weekSiteClicks = 0;
					int weekPopularClicks = 0;
					
					int weekStoryTitleClicks = 0;
					int weekStoryImageClicks = 0;
					int weekStoryReadMoreClicks = 0;
					
					int weekPopularTextClicks = 0;
					int weekPopularThumbnailClicks = 0;
					
					int weekShareEmailClicks = 0;
					int weekShareTwitterClicks = 0;
					int weekShareFacebookClicks = 0;
					int weekShareMySpaceClicks = 0;
					int weekShareDeliciousClicks = 0;
					int weekShareDiggClicks = 0;
					int weekShareGoogleReaderClicks = 0;
					
					for (Click click : weekClicks) {
						switch (click.getType()) {
						case STORY:
							weekStoryClicks++;
							if (click.getSource().compareTo(Click.Source.STORY_TITLE) == 0) {
								weekStoryTitleClicks++;
							} else if (click.getSource().compareTo(Click.Source.STORY_IMAGE) == 0) {
								weekStoryImageClicks++;
							} else if (click.getSource().compareTo(Click.Source.STORY_READ_MORE) == 0) {
								weekStoryReadMoreClicks++;
							} // if-else statement
							break;
						case FEED:
							weekFeedClicks++;
							break;
						case SHARE:
							weekShareClicks++;
							if (click.getSource().compareTo(Click.Source.SHARE_BAR_EMAIL) == 0) {
								weekShareEmailClicks++;
							} else if (click.getSource().compareTo(Click.Source.SHARE_BAR_TWITTER) == 0) {
								weekShareTwitterClicks++;
							} else if (click.getSource().compareTo(Click.Source.SHARE_BAR_FACEBOOK) == 0) {
								weekShareFacebookClicks++;
							} else if (click.getSource().compareTo(Click.Source.SHARE_BAR_MYSPACE) == 0) {
								weekShareMySpaceClicks++;
							} else if (click.getSource().compareTo(Click.Source.SHARE_BAR_DELICIOUS) == 0) {
								weekShareDeliciousClicks++;
							} else if (click.getSource().compareTo(Click.Source.SHARE_BAR_DIGG) == 0) {
								weekShareDiggClicks++;
							} else if (click.getSource().compareTo(Click.Source.SHARE_BAR_GOOGLE_READER) == 0) {
								weekShareGoogleReaderClicks++;
							}  // if-else statement
							break;
						case SITE:
							weekSiteClicks++;
							break;
						case POPULAR:
							weekPopularClicks++;
							if (click.getSource().compareTo(Click.Source.POPULAR_TEXT) == 0) {
								weekPopularTextClicks++;
							} else if (click.getSource().compareTo(Click.Source.POPULAR_THUMBNAIL) == 0) {
								weekPopularThumbnailClicks++;
							}  // if-else statement
							break;
						default:
							throw new Error("Invalid Type passed to /admin/clicks/index.jsp");
						}  // switch statement
					}  // for loop
					%>
						Clicks past 7 days: <%= weekTotalClicks %>
						<ul>
							<li>Story Clicks: <%= weekStoryClicks %></li>
							<li>Feed Clicks: <%= weekFeedClicks %></li>
							<li>Share Clicks: <%= weekShareClicks %></li>
							<li>Site Clicks: <%= weekSiteClicks %></li>
							<li>Popular Clicks: <%= weekPopularClicks %></li>
						</ul>
						<ul>
							<li>Story Click Breakdown:</li>
							<ul>
								<li>via Title: <%= weekStoryTitleClicks %></li>
								<li>via Image: <%= weekStoryImageClicks %></li>
								<li>via ReadMore: <%= weekStoryReadMoreClicks %></li>
							</ul>
						</ul>
						<ul>
							<li>Popular Click Breakdown:</li>
							<ul>
								<li>via Text: <%= weekPopularTextClicks %></li>
								<li>via Thumbnail: <%= weekPopularThumbnailClicks %></li>
							</ul>
						</ul>
						<ul>
							<li>Share Click Breakdown:</li>
							<ul>
								<li>via E-mail: <%= weekShareEmailClicks %></li>
								<li>via Twitter: <%= weekShareTwitterClicks %></li>
								<li>via Facebook: <%= weekShareFacebookClicks %></li>
								<li>via MySpace: <%= weekShareMySpaceClicks %></li>
								<li>via Delicious: <%= weekShareDeliciousClicks %></li>
								<li>via Digg: <%= weekShareDiggClicks %></li>
								<li>via Google Reader: <%= weekShareGoogleReaderClicks %></li>
							</ul>
						</ul>
					</p>
				</div>
			</div>
			<div class="main-b">&nbsp;</div>
		</div>
		<jsp:include page="/inc/header.tpl" />
		<jsp:include page="/inc/footer.jsp" />
	</div>
</body>
</html>
