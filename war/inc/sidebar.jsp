<%@	page contentType="text/html;charset=UTF-8"
		 language="java"
		 import="java.text.DateFormat"
		 import="java.text.SimpleDateFormat"
		 import="java.util.List"
		 import="veronica.click.vo.Click"
		 import="veronica.feed.FeedManager"
		 import="veronica.feed.vo.Feed"
		 import="veronica.story.StoryManager"
		 import="veronica.story.vo.Story"
		 import="java.util.TimeZone"
%>

<!-- sidebar -->
<div id="sidebar">
	<!-- ad -->
	<jsp:include page="/inc/ads/sidebar-rectangle1.tpl" />
	<!-- popular stories -->
	<div class="box">
		<h3>Popular Stories</h3>
		<ul>
			<%
			List<Story> popularStories = StoryManager.getPopularStories();
			
			if (popularStories.isEmpty()) {
				out.println("There are no popular stories at this time.");
			} else {
				// for each story, get the feed info from the associated feed
				Story story;
				for (int i = 0; i < popularStories.size(); i++) {
					story = popularStories.get(i);
					Feed feed = FeedManager.getFeed(story.getSourceFeedKey());
					
					// If we've deleted the source-feed, don't display this item. Perhaps we'd eventually like to do
					// something different, but for now, let's just keep it simple and skip this story.
					if (feed != null) {
			%>
			<li <% if (i % 2 == 1) out.println("class=\"even\""); %>>
				<%
				if (story.getThumbnail() != null) {
					out.println("<div class=\"image\"><a href=\"" + story.getUrl() + "\" onMouseDown=\"this.href='/redirect?url=" + story.getUrl() + "&type=" + Click.Type.POPULAR + "&src=" + Click.Source.POPULAR_THUMBNAIL + "&fkey=" + feed.getKey().getId() + "&skey=" + story.getKey().getId() + "'\"><img src=\"/image?id=" + story.getKey().getId() + "&type=thumb\" alt=\"Image for story '" + story.getTitle() + "'\" /></a></div>");						
				}
				%>
				<div class="holder">
					<em class="date">
					<%
					DateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");
					dateFormat.setTimeZone(TimeZone.getTimeZone("PST"));
					out.print(dateFormat.format(story.getPublishedDate()));
					%></em>
					<p><% out.println("<a href=\"" + story.getUrl() + "\" onMouseDown=\"this.href='/redirect?url=" + story.getUrl() + "&type=" + Click.Type.POPULAR + "&src=" + Click.Source.POPULAR_TEXT + "&fkey=" + feed.getKey().getId() + "&skey=" + story.getKey().getId() + "'\">"); %><%= story.getTitle() %><%= "</a>" %></p>
				</div>
			</li>
			<%			
					}  // if statement
					
				}  // for loop
			}  // if-else statement
			%>
		</ul>
	</div>
	<!-- ads -->
	<jsp:include page="/inc/ads/sidebar-rectangle2.tpl" />
	<jsp:include page="/inc/ads/sidebar-skyscraper.tpl" />
</div>