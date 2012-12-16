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

<!-- sidebar -->

<div id="sidebar">
	<!-- links -->
	<div class="box">
		<h3>Links</h3>
		<ul>
			<li>
				<div class="holder">
					<em class="date"><a href="<%= properties.getProperty("about.link") %>"><%= properties.getProperty("about.linktext") %></a></em>
				</div>
			</li>
			<li>
				<div class="holder">
					<em class="date"><a href="<%= properties.getProperty("faq.link") %>"><%= properties.getProperty("faq.linktext") %></a></em>
				</div>
			</li>
			<li>
				<div class="holder">
					<em class="date"><a href="<%= properties.getProperty("contact.link") %>"><%= properties.getProperty("contact.linktext") %></a></em>
				</div>
			</li>
		</ul>
	</div>
</div>