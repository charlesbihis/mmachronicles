<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.io.IOException"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Properties" %>
<%@page import="veronica.story.StoryManager"%>
<%@page import="veronica.story.vo.Story"%>

<%
	Properties properties = new Properties();
	
	try {
		properties.load(new FileInputStream("veronica.properties"));
	} catch (IOException e) {
		// do something
	}
	
	int currentPage;
	String currentPageString = request.getParameter("page");
	
	if (currentPageString != null) {
		currentPage = Integer.parseInt(currentPageString);
	} else {
		currentPage = 1;
	}
%>


<%@page import="javax.jdo.PersistenceManager"%>
<%@page import="veronica.util.BigTableDao"%>
<%@page import="veronica.feed.vo.Feed"%><html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title><%= properties.getProperty("site.title") + " - " + properties.getProperty("site.slogan") %></title>
</head>

<body>

	<h1><%= properties.getProperty("site.title") %></h1>
	<%
		PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
		List<Story> stories = StoryManager.getStories(currentPage);
		
		if (stories.isEmpty()) {
			out.println("There are no stories in the bucket.");
		} else {
			for (Story story : stories) {
				String query = "SELECT FROM " + Feed.class.getName() + " WHERE key == " + story.getSourceFeedKey().getId();
				Feed feed = ((List<Feed>)persistenceManager.newQuery(query).execute()).get(0);
				
				out.println("<a href='" + feed.getUrl() + "'>" + feed.getTitle() + "</a> - <a href='" + story.getUrl() + "'>" + story.getTitle() + "</a><br />");
				out.println(story.getTeaser() + "<br /><br />");
			}  // for loop
		}  // if-else statement
	%>
	
	<p>PAGINATION</p>
	
	<a href="/index.jsp?page=<%= currentPage - 1 %>">Prev</a>&nbsp;&nbsp;&nbsp;
	<a href="/index.jsp?page=<%= currentPage - 1 %>"><%= currentPage - 1 %></a>&nbsp;&nbsp;&nbsp;
	<%= currentPage %>&nbsp;&nbsp;&nbsp;
	<a href="/index.jsp?page=<%= currentPage + 1 %>"><%= currentPage + 1 %></a>&nbsp;&nbsp;&nbsp;
	<a href="/index.jsp?page=<%= currentPage + 1 %>">Next</a>
	
</body>
	
</html>
