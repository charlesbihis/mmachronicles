package veronica.feed.vo;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Feed implements Serializable {
	private static final long serialVersionUID = 1L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private String url;
	
	@Persistent
    private String title;
	
	@Persistent
    private String feedUrl;
	
	@Persistent
    private String favicon;
	
	@Persistent
    private Integer pollRate;
	
	@Persistent
    private Date lastPoll;
	
	@Persistent
    private Integer clicks;
	
	@Persistent
    private Integer popularClicks;
	
	@Persistent
    private Integer shares;
	
	public static class Builder {
		// required
		private String url;
		private String title;
		private String feedUrl;
		
		// optional
		private String favicon = "";
		private Integer pollRate = 3600;		// defaults to 1 hour
		private Integer clicks = 0;
		private Integer popularClicks = 0;
		private Integer shares = 0;
		
		public Builder url(String url) {
			this.url = url;
			return this;
		}  // url
		
		public Builder title(String title) {
			this.title = title;
			return this;
		}  // title
		
		public Builder feedUrl(String feedUrl) {
			this.feedUrl = feedUrl;
			return this;
		}  // feedUrl
		
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
		this.feedUrl = builder.feedUrl;
		this.favicon = builder.favicon;
		this.pollRate = builder.pollRate;
		this.lastPoll = new Date(new Date().getTime() - 604800000);	// set last poll to be last week by default
		this.clicks = builder.clicks;
		this.popularClicks = builder.popularClicks;
		this.shares = builder.shares;
	}  // Feed
	
	public Key getKey() {
		return key;
	}  // getKey

	public String getUrl() {
		return url;
	}  // getUrl
	
	public String getTitle() {
		return title;
	}  // getTitle
	
	public String getFeedUrl() {
		return feedUrl;
	}  // getFeedUrl

	public String getFavicon() {
		return favicon;
	}  // getFavicon

	public Integer getPollRate() {
		return pollRate;
	}  // getPollRate

	public Date getLastPoll() {
		return lastPoll;
	}  // getLastPoll

	public void setLastPoll(Date lastPoll) {
		this.lastPoll = lastPoll;
	}  // setLastPoll
	
	public Integer getClicks() {
		return clicks;
	}  // getClicks
	
	public Integer getPopularClicks() {
		return popularClicks;
	}  // getPopularClicks
	
	public Integer getShares() {
		return shares;
	}  // getShares
	
	public void addClick() {
		clicks++;
	}  // addClick
	
	public void addPopularlick() {
		popularClicks++;
	}  // addPopularlick
	
	public void addShare() {
		shares++;
	}  // addShare
}  // class declaration
