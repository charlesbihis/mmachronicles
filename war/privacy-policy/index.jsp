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
					<div class="about">
						<h2><%= properties.getProperty("legal.privacypolicy.linktext") %></h2>
						<p>MMA Chronicles is founded on the principles of helping people to discover news and stories relevant to the topic of Mixed Martial Arts. MMA Chronicles knows that you care about how your personal information is used and shared and we take your privacy very seriously. Please read the following to learn more about our privacy policy. By visiting the MMA Chronicles website, you are accepting the practices outlined in this Privacy Policy.</p>
						<p>This Privacy Policy covers MMA Chronicles's treatment of personal information that MMA Chronicles gathers when you are on the MMA Chronicles website and when you use MMA Chronicles services. This policy does not apply to the practices of third parties that MMA Chronicles does not own or control, or to individuals that MMA Chronicles does not employ or manage.</p>
						
						<strong>Information Collected by MMA Chronicles</strong>
						<p>We only collect information that is relevant to the purpose of our website, which is to enable users to discover and share the most relevant news and information with one another. This information allows us to provide you with a customized and efficient experience. We do not process this information in a way that is incompatible with this objective. We collect the following types of information from our users:</p>
						<ol>
							<li><strong>Information You Provide to Us:</strong><br />We receive and store any information you enter on our website or provide to us in any other way. You can of course choose not to provide us with any information you do not feel comfortable sharing.</li>
							<li><strong>Automatic Information:</strong><br />We receive and store certain types of information whenever you interact with us. MMA Chronicles and its authorized agents automatically receive and record certain "traffic data" on their server logs from your browser including click data, and share data. MMA Chronicles uses this data to help diagnose problems with its servers, analyze trends and administer the website. MMA Chronicles may collect and, on any page, display the total counts that page has been viewed.</li>
						</ol>
						
						<strong>Sharing Your Information</strong>
						<p>Because MMA Chronicles enables people to discover and share information with one another, information about the people who use MMA Chronicles is an integral part of the MMA Chronicles experience. Rest assured that we neither rent nor sell your personal information to anyone and that we will share your personal information only as described below.</p>
						<ul>
							<li>MMA Chronicles Personnel: MMA Chronicles personnel and authorized consultants and/or contractors may have access to user information if necessary in the normal course of MMA Chronicles business.</li>
							<li>Business Transfers: In some cases, we may choose to buy or sell assets. In these types of transactions, user information is typically one of the business assets that is transferred. Moreover, if MMA Chronicles, or substantially all of its assets, were acquired, user information would be one of the assets that is transferred.</li>
							<li>Protection of MMA Chronicles and Others: We may release personal information when we believe in good faith that release is necessary to comply with a law; to enforce or apply our Terms of Use and other policies; or to protect the rights, property, or safety of MMA Chronicles, our employees, our users, or others. This includes exchanging information with other companies and organizations for fraud protection and credit risk reduction.</li>
							<li>Syndication: MMA Chronicles allows for the RSS syndication of all of its public content within the MMA Chronicles website.</li>
							<li>With Your Consent: Except as noted above, we will contact you when your personal information is shared with third parties or used for a purpose incompatible with the purpose(s) for which it was originally collected, and you will be able to opt out to prevent the sharing of this information.</li>
						</ul>
						
						<p>MMA Chronicles may amend this Privacy Policy from time to time, at its sole discretion. Use of information we collect now is subject to the Privacy Policy in effect at the time such information is used. If we make changes to the Privacy Policy, we will notify you by posting an announcement on the MMA Chronicles website so you are always aware of what information we collect, how we use it, and under what circumstances if any, it is disclosed.</p>
					</div>
				</div>
				<jsp:include page="/inc/sidebar-legal.jsp" />
			</div>
			<div class="main-b">&nbsp;</div>
		</div>
		<jsp:include page="/inc/header.tpl" />
		<jsp:include page="/inc/footer.jsp" />
	</div>
</body>
</html>
