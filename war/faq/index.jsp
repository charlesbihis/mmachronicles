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
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
		<h1 class="logo"><a href="/"><%= properties.getProperty("site.title") %></a></h1>
		<!-- ad -->
		<jsp:include page="/inc/ads/banner.tpl" />
		<!-- main area -->
		<div id="main">
			<div id="twocolumns">
				<!-- content -->
				<div id="content">
					<div class="about">
						<h2><%= properties.getProperty("faq.header") %></h2>
						<ul>
							<li><a href="#how-does-it-work">How does MMA Chronicles work?</a></li>
							<li><a href="#add-us">Can I get my site added to MMA Chronicles?</a></li>
							<li><a href="#remove-us">Stories from my site are being listed here and I don't like it.  Can I get my site removed from MMA Chronicles?</a></li>
							<li><a href="#can-i-submit">Can I submit a single story to MMA Chronicles?</a></li>
							<li><a href="#same-topic">Sometimes I see multiple articles written about the exact same topic.  Sometimes they even use the same picture.  Why?</a></li>
							<li><a href="#from-the-future">Sometimes I see articles that say "Posted 25 minutes from now" or "Posted 2 hours from now".  Am I really reading posts from the future??</a></li>
							<li><a href="#theres-a-problem">How do I report a problem with the site?</a></li>
							<li><a href="#suggest-a-feature">Can I suggest features to add to the site?</a></li>
						</ul>
						
						<br />
						
						<h3><a name="how-does-it-work"></a>How does MMA Chronicles work?</h3>
						<p>MMA Chronicles is what's called a "News Aggregator".  We scour the web and pull in stories from all of the top MMA websites and blogs.  Whenever a new story is posted, MMA Chronicles gets updated and posts the story in our main feed	within minutes.  This allows users to have a single source where they can find out all of the latest news!</p>
						
						<h3><a name="add-us"></a>Can I get my site added to MMA Chronicles?</h3>
						<p>Yes, you can definitely add your site to our feed!  Just e-mail us at <a href="mailto:addus@mmachronicles.com">addus@mmachronicles.com</a>!  All you need is a valid RSS/Atom feed that we can pull your stories from.</p>
						<p>[Don't know what an RSS/Atom feed is?  Don't worry.  Unless you wrote your site from scratch, you probably already have one and just don't know it.  <a href="mailto:addus@mmachronicles.com">E-mail us</a> and we'll help you find it.]</p>
						
						<h3><a name="remove-us"></a>Stories from my site are being listed here and I don't like it.  Can I get my site removed from MMA Chronicles?</h3>
						<p>Yes, of course you can get your stories removed from our feed.  We're sorry to see you go, but if you don't like the service we're providing, just let us know at <a href="mailto:removeus@mmachronicles">removeus@mmachronicles.com</a> and we'll be sure to take your stories out of our site immediately.</p>
						
						<h3><a name="can-i-submit"></a>Can I submit a single story to MMA Chronicles?</h3>
						<p>Unfortunately, no, you can't submit single stories to our site.  It's not that we don't want you to!  But our site just doesn't work that way.  We aggregate stories using RSS and Atom feeds taken from websites and so we don't actually support the aggregation of single news items.  This might be something that we can add in the future, if people want it.  If you'd like us to add this feature to our site, <a href="mailto:feedback@mmachronicles.com">let us know</a>!  Otherwise, we won't know what to do in our spare time.
						
						<h3><a name="same-topic"></a>Sometimes I see multiple articles written about the exact same topic.  Sometimes they even use the same picture.  Why?</h3>
						<p>As we mentioned in "<a href="#how-does-it-work">How does MMA Chronicles work?</a>", our site actually aggregates the latest stories from many other MMA sites, so if there is a hot topic that everyone is writing about, we'll see it in our feed in the form of multiple topic posts.</p>
						
						<h3><a name="from-the-future"></a>Sometimes I see articles that say "Posted 25 minutes from now" or "Posted 2 hours from now".  Am I really reading posts from the future??</h3>
						<p>Ha, unfortunately not.  Since we aggregate stories from multiple feeds across the web, we sometimes are unable to correctly determine the proper timezone for the original article.  Because of this, sometimes our post-times are off by up to a few hours.  We make our best guess, but it's not always 100% right.  We'll get it eventually :)</p>
						
						<h3><a name="theres-a-problem"></a>How do I report a problem with the site?</h3>
						<p>If you find something wrong on the site, or you find a questionable article that snuck into the feed, e-mail us at <a href="feedback@mmachronicles.com">feedback@mmachronicles.com</a> and we'll address it right away!  Don't be shy ;)</p>
						
						<h3><a name="suggest-a-feature"></a>Can I suggest features to add to the site?</h3>
						<p>Yes, please do!  Without hearing from you, we won't know what to add to our site next!  Reach us at <a href="mailto:feedback@mmachronicles.com">feedback@mmachronicles.com</a> and tell your friends!
					</div>
				</div>
				<jsp:include page="/inc/sidebar-links.jsp" />
			</div>
			<div class="main-b">&nbsp;</div>
		</div>
		<jsp:include page="/inc/header.tpl" />
		<jsp:include page="/inc/footer.jsp" />
	</div>
</body>
</html>
