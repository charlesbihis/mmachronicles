package veronica.click.vo;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Click {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
    private Type type;
	
	@Persistent
    private Source source;
	
	@Persistent
    private Key feedKey;
	
	@Persistent
    private Key storyKey;
	
	@Persistent
    private Date date;
	
	public enum Type {
		STORY, FEED, SHARE, SITE, POPULAR
	}  // enum declaration
	
	public enum Source {
		STORY_TITLE, STORY_IMAGE, STORY_READ_MORE, FEED_LINK, SHARE_BAR_EMAIL, SHARE_BAR_TWITTER, SHARE_BAR_FACEBOOK,
		SHARE_BAR_MYSPACE, SHARE_BAR_DELICIOUS, SHARE_BAR_DIGG, SHARE_BAR_GOOGLE_READER, SHARE_BAR_RSS,
		POPULAR_THUMBNAIL, POPULAR_TEXT, WEBSITE_TITLE, FOCUS_STORY_TITLE, FOCUS_STORY_IMAGE, FOCUS_STORY_READ_MORE, FOCUS_WEBSITE_TITLE
	}  // enum declaration
	
	public static class Builder {
		// required
		private Key feedKey;
		private Key storyKey;
		private Type type;
		private Source source;
		private Date date;
		
		public Builder(Type type, Source source, Key feedKey, Key storyKey, Date date) {
			this.type = type;
			this.source = source;
			this.feedKey = feedKey;
			this.storyKey = storyKey;
			this.date = date;
		}  // Builder
		
		public Click build() {
			return new Click(this);
		}  // build
	}  // inner class declaration
	
	private Click(Builder builder) {
		this.feedKey = builder.feedKey;
		this.storyKey = builder.storyKey;
		this.type = builder.type;
		this.source = builder.source;
		this.date = builder.date;
	}  // Feed

	public Key getKey() {
		return key;
	}  // getKey

	public Type getType() {
		return type;
	}  // getType
	
	public Source getSource() {
		return source;
	}  // getSource
	
	public Key getFeedKey() {
		return feedKey;
	}  // getFeedKey

	public Key getStoryKey() {
		return storyKey;
	}  // getStoryKey
	
	public Date getClickDate() {
		return date;
	}  // getClickDate
}  // class declaration
