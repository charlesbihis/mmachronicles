<%@page contentType="text/html;charset=UTF-8" language="java" %>


<%@page import="javax.jdo.PersistenceManager"%>
<%@page import="veronica.BigTableDao"%>
<%@page import="veronica.story.vo.Story"%>
<%@page import="java.util.List"%><html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>Testing</title>
</head>

<body>
	
	<%
	PersistenceManager pm = BigTableDao.get().getPersistenceManager();
	String query = "SELECT FROM " + Story.class.getName() + " WHERE key == -2139209695";
	
	List<Story> stories = (List<Story>) pm.newQuery(query).execute();
	
	for (Story story : stories) {
		out.println("*** " + story.getTitle() + "<br />");
	}
	%>
</body>

</html>