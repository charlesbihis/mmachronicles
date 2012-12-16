package veronica.story.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Story implements Serializable {
	private static final long serialVersionUID = 1L;

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
	private String sourceFeedKey;
	
	// optional
	@Persistent
	private String author;
	
	@Persistent
	private List<String> authors;
	
	@Persistent
	private List<String> categories;
	
	@Persistent
	private Date publishedDate;
	
	@Persistent
	private Date updatedDate;
	
	@Persistent
	private Integer clicks;
	
	@Persistent
	private Integer popularClicks;
	
	@Persistent
	private Integer shares;
	
	@Persistent
	private Blob image;
	
	@Persistent
	private Blob thumbnail;

    public static class Builder {
    	// required
    	private String title;
    	private String url;
    	private String teaser;
    	private String sourceFeedKey;
    	
    	// optional
    	private String author = "";
    	private List<String> authors = new ArrayList<String>();
    	private List<String> categories = new ArrayList<String>();
    	private Date publishedDate = null;
    	private Date updatedDate = null;
    	private Integer clicks = 0;
    	private Integer popularClicks = 0;
    	private Integer shares = 0;
    	private Blob image = null;
    	private Blob thumbnail = null;
    	
    	public Builder title(String title) {
    		this.title = title;
    		return this;
    	}  // title
    	
    	public Builder url(String url) {
    		this.url = url;
    		return this;
    	}  // url
    	
    	public Builder teaser(String teaser) {
    		this.teaser = teaser;
    		return this;
    	}  // teaser
    	
    	public Builder sourceFeedKey(String sourceFeedKey) {
    		this.sourceFeedKey = sourceFeedKey;
    		return this;
    	}  // sourceFeedKey
    	
    	public Builder author(String author) {
    		this.author = author;
    		return this;
    	}  // author
    	
    	public Builder authors(List<String> authors) {
    		this.authors = authors;
    		return this;
    	}  // authors
    	
    	public Builder categories(List<String> categories) {
    		this.categories = categories;
    		return this;
    	}  // categories
    	
    	public Builder publishedDate(Date publishedDate) {
    		this.publishedDate = publishedDate;
    		return this;
    	}  // publishedDate
    	
    	public Builder updatedDate(Date updatedDate) {
    		this.updatedDate = updatedDate;
    		return this;
    	}  // updatedDate
    	
    	public Builder image(Blob image) {
    		this.image = image;
    		return this;
    	}  // image
    	
    	public Builder thumbnail(Blob thumbnail) {
    		this.thumbnail = thumbnail;
    		return this;
    	}  // thumbnail
    	
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
		this.authors = builder.authors;
		this.categories = builder.categories;
		this.publishedDate = builder.publishedDate;
		this.updatedDate = builder.updatedDate;
		this.clicks = builder.clicks;
		this.popularClicks = builder.popularClicks;
		this.shares = builder.shares;
		this.image = builder.image;
		this.thumbnail = builder.thumbnail;
		
		// Generate unique key based off of a hash of the title, and URL.  This
		// is used to determine if a duplicate story has already been saved.
		long id = this.title.toLowerCase().concat(this.url.toLowerCase()).hashCode();
		this.key = KeyFactory.createKey(Story.class.getSimpleName(), id);
	}  // Story

	public Key getKey() {
	    return key;
	}  // getKey
	
	public String getTitle() {
	    return title;
	}  // getTitle
	
	public void setTitle(String title) {
		this.title = title;
	}  // setTitle
	
	public String getUrl() {
		return url;
	}  // getUrl
	
	public void setUrl(String url) {
		this.url = url;
	}  //setUrl
	
	public String getAuthor() {
	    return author;
	}  // getAuthor
	
	public void setAuthor(String author) {
		this.author = author;
	}  // setAuthor
	
	public List<String> getAuthors() {
	    return authors;
	}  // getAuthors
	
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}  // setAuthors
	
	public List<String> getCategories() {
		return categories;
	}  // getCategories
	
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}  // setCategories
	
	public String getTeaser() {
		return teaser;
	}  // getTeaser
	
	public void setTeaser(String teaser) {
		this.teaser = teaser;
	}  // setTeaser
	
	public String getSourceFeedKey() {
		return sourceFeedKey;
	}  // getSourceFeedKey
	
	public Date getPublishedDate() {
		return publishedDate;
	}  // getPublishedDate
	
	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}  // setPublishedDate
	
	public Date getUpdatedDate() {
		return updatedDate;
	}  // getUpdatedDate
	
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}  // setUpdatedDate
	
	public Integer getClicks() {
		return clicks;
	}  // getClicks
	
	public Integer getPopularClicks() {
		return popularClicks;
	}  // getPopularClicks
	
	public Integer getShares() {
		return shares;
	}  // getShares
	
	public Blob getImage() {
		return image;
	}  // getImage
	
	public Blob getThumbnail() {
		return thumbnail;
	}  // getThumbnail
	
	public void addClick() {
		clicks++;
	}  // addClick
	
	public void addPopularClick() {
		popularClicks++;
	}  // addPopularClick
	
	public void addShare() {
		shares++;
	}  // addShare
}  // class declaration