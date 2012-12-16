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

<!-- footer -->
<div id="footer">
	<ul>
		<li><a href="<%= properties.getProperty("about.link") %>"><%= properties.getProperty("about.linktext") %></a></li>
		<li><a href="<%= properties.getProperty("faq.link") %>"><%= properties.getProperty("faq.linktext") %></a></li>
		<li><a href="<%= properties.getProperty("rss.link") %>"><%= properties.getProperty("rss.linktext") %></a></li>
		<li><a href="<%= properties.getProperty("contact.link") %>"><%= properties.getProperty("contact.linktext") %></a></li>
	</ul>
	<ul class="copyright">
		<li><%= properties.getProperty("footer.copyright") %></li>
		<li><a href="<%= properties.getProperty("legal.termsofuse.link") %>"><%= properties.getProperty("legal.termsofuse.linktext") %></a></li>
		<li><a href="<%= properties.getProperty("legal.privacypolicy.link") %>"><%= properties.getProperty("legal.privacypolicy.linktext") %></a></li>
	</ul>
</div>