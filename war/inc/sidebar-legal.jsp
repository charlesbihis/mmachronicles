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
		<ul>
			<li>
				<div class="holder">
					<em class="date"><a href="<%= properties.getProperty("legal.termsofuse.link") %>"><%= properties.getProperty("legal.termsofuse.linktext") %></a></em>
				</div>
			</li>
			<li>
				<div class="holder">
					<em class="date"><a href="<%= properties.getProperty("legal.privacypolicy.link") %>"><%= properties.getProperty("legal.privacypolicy.linktext") %></a></em>
				</div>
			</li>
		</ul>
	</div>
</div>