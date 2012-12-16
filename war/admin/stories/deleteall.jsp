<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Properties"%>
<%@page import="java.util.List"%>
<%@page import="veronica.feed.vo.Feed"%>
<%@page import="veronica.util.BigTableDao"%>
<%@page import="veronica.story.vo.Story"%>
<%@page import="javax.jdo.PersistenceManager"%>

<%
	Properties properties = new Properties();
	
	try {
		properties.load(new FileInputStream("veronica.properties"));
	} catch (IOException e) {
		// do something
	}

	PersistenceManager pm = BigTableDao.get().getPersistenceManager();
	String query = "SELECT FROM " + Story.class.getName();
	List<Story> stories = (List<Story>) pm.newQuery(query).execute();
	
	String result = stories.size() + "";
	
	if (stories.isEmpty()) {
		 result = "NO STORIES TO DELETE";
	} else {
		try {
			for (Story story : stories) {
				pm.deletePersistent(story);
			}
	    } finally {
	    	pm.close();
	    }
	}
%>

<html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title><%= properties.getProperty("site.title") + " - Delete Stories" %></title>
</head>

<body>
	<p>Deleting stories...<%= result %></p>
</body>

</html>