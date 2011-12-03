package veronica.story.vo;

import java.util.Date;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.images.Image;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Story {
	// required
    @PrimaryKey
    @Persistent
    private Key key;

    @Persistent
    private String title;
    
    @Persistent
    private String url;
    
    @Persistent
    private String teaser;
    
    @Persistent
    private Key sourceFeedKey;

    // optional
    @Persistent
    private String author;

    @Persistent
    private Date publishedDate;
    
    @Persistent
    private Date updatedDate;
    
    @Persistent
    private Integer clicks;
    
    // TODO Figure out how to store images
    //@Persistent
    private Image image;
    
    public static class Builder {
    	// required
    	private String title;
    	private String url;
    	private String teaser;
    	private Key sourceFeedKey;
    	
    	// optional
    	private String author = "";
    	private Date publishedDate = null;
    	private Date updatedDate = null;
    	private Integer clicks = 0;
    	private Image image = null;
    	
    	public Builder(String title, String url, String teaser, Key sourceFeedKey) {
    		this.title = title;
    		this.url = url;
    		this.teaser = teaser;
    		this.sourceFeedKey = sourceFeedKey;
    	}  // Builder
    	
    	public Builder author(String author) {
    		this.author = author;
    		return this;
    	}  // author
    	
    	public Builder publishedDate(Date publishedDate) {
    		this.publishedDate = publishedDate;
    		return this;
    	}  // publishedDate
    	
    	public Builder updatedDate(Date updatedDate) {
    		this.updatedDate = updatedDate;
    		return this;
    	}  // updatedDate
    	
    	public Builder image(Image image) {
    		this.image = image;
    		return this;
    	}  // image
    	
    	public Story build() {
    		return new Story(this);
		}  // build
	}  // inner class declaration

	private Story(Builder builder) {
		this.title = builder.title;
		this.url = builder.url;
		this.teaser = builder.teaser;
		this.sourceFeedKey = builder.sourceFeedKey;
		this.author = builder.author;
		this.publishedDate = builder.publishedDate;
		this.updatedDate = builder.updatedDate;
		this.clicks = builder.clicks;
		this.image = builder.image;
		
		// Generate unique key based off of a hash of the title, url, and
		// teaser.  This is used to determine if a duplicate story has already
		// been saved.
		long id = this.title.concat(this.url).concat(this.teaser).hashCode();
		this.key = KeyFactory.createKey(Story.class.getSimpleName(), id);
	}  // Story

    public Key getKey() {
        return key;
    }  // getKey

    public String getTitle() {
        return title;
    }  // getTitle
    
    public String getUrl() {
    	return url;
    }  // getUrl
    
    public String getAuthor() {
        return author;
    }  // getAuthor

    public String getTeaser() {
    	return teaser;
    }  // getTeaser
    
    public Key getSourceFeedKey() {
    	return sourceFeedKey;
    }  // getSourceFeedKey

    public Date getPublishedDate() {
    	return publishedDate;
    }  // getPublishedDate

    public Date getUpdatedDate() {
    	return updatedDate;
    }  // getUpdatedDate
    
    public Integer getClicks() {
    	return clicks;
    }  // getClicks
    
    public Image getImage() {
    	return image;
    }  // getImage
    
    public void addClick() {
    	clicks++;
    }  // addClick
}  // class declaration