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
    private Key feedKey;
	
	@Persistent
    private Key storyKey;
	
	@Persistent
    private Date clickDate;
	
	public static class Builder {
		// required
		private Key feedKey;
		private Key storyKey;
		private Date clickDate;
		
		public Builder(Key feedKey, Key storyKey, Date clickDate) {
			this.feedKey = feedKey;
			this.storyKey = storyKey;
			this.clickDate = clickDate;
		}  // Builder
		
		public Click build() {
			return new Click(this);
		}  // build
	}  // inner class declaration
	
	private Click(Builder builder) {
		this.feedKey = builder.feedKey;
		this.storyKey = builder.storyKey;
		this.clickDate = builder.clickDate;
	}  // Feed

	public Key getKey() {
		return key;
	}

	public Key getFeedKey() {
		return feedKey;
	}

	public Key getStoryKey() {
		return storyKey;
	}

	public Date getClickDate() {
		return clickDate;
	}
}
