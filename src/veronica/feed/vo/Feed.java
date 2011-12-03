package veronica.feed.vo;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Feed {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
    private String url;
	
	@Persistent
    private String title;
	
	@Persistent
    private String favicon;
	
	@Persistent
    private Integer pollRate;
	
	@Persistent
    private Date lastPoll;
	
	@Persistent
    private Integer clicks;
	
	public static class Builder {
		// required
		private String url;
		private String title;
		
		// optional
		private String favicon = "";
		private Integer pollRate = 3600;		// defaults to 1 hour
		private Integer clicks = 0;
		
		public Builder(String url, String title) {
			this.url = url;
			this.title = title;
		}  // Builder
		
		public Builder favicon(String favicon) {
			this.favicon = favicon;
			return this;
		}  // favicon
		
		public Builder pollRate(int pollRate) {
			this.pollRate = pollRate;
			return this;
		}  // pollRate
		
		public Feed build() {
			return new Feed(this);
		}  // build
	}  // inner class declaration
	
	private Feed(Builder builder) {
		this.url = builder.url;
		this.title = builder.title;
		this.favicon = builder.favicon;
		this.pollRate = builder.pollRate;
		this.lastPoll = new Date(new Date().getTime() - 604800000);	// set last poll to be last week by default
		this.clicks = builder.clicks;
	}  // Feed
	
	public Key getKey() {
		return key;
	}

	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public String getFavicon() {
		return favicon;
	}

	public Integer getPollRate() {
		return pollRate;
	}

	public Date getLastPoll() {
		return lastPoll;
	}

	public void setLastPoll(Date lastPoll) {
		this.lastPoll = lastPoll;
	}
	
	public Integer getClicks() {
		return clicks;
	}
}
