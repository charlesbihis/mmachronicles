<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Properties"%>
<%@page import="java.util.List"%>
<%@page import="veronica.feed.vo.Feed"%>
<%@page import="veronica.util.BigTableDao"%>
<%@page import="javax.jdo.PersistenceManager"%>

<%
	Properties properties = new Properties();
	
	try {
		properties.load(new FileInputStream("veronica.properties"));
	} catch (IOException e) {
		// do something
	}

	PersistenceManager pm = BigTableDao.get().getPersistenceManager();
	String query = "SELECT FROM " + Feed.class.getName();
	List<Feed> feeds = (List<Feed>) pm.newQuery(query).execute();
	
	String result = feeds.size() + "";
	
	if (feeds.isEmpty()) {
		 result = "NO FEEDS TO DELETE";
	} else {
		try {
			for (Feed feed : feeds) {
				pm.deletePersistent(feed);
			}
	    } finally {
	    	pm.close();
	    }
	}
%>

<html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title><%= properties.getProperty("site.title") + " - Delete Feeds" %></title>
</head>

<body>
	<p>Deleting feeds...<%= result %></p>
</body>

</html>