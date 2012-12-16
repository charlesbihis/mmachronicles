package veronica.worker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;
import veronica.story.vo.Story;
import veronica.util.BigTableDao;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;

public class ImageWarehouse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ImageWarehouse.class.getName());
	private static final String CACHED_IMAGE_PREFIX = "cachedImage";
	private static final String CACHED_THUMBNAIL_PREFIX = "cachedThumbnail";

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String storyKey = req.getParameter("id");
		String imageType = req.getParameter("type");
		Image image = null;
		Cache cache;
		
		// check to see if the image is in the cache
		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());

			if (imageType != null && imageType.equals("thumb")) {
				if (cache.containsKey(CACHED_THUMBNAIL_PREFIX + storyKey)) {
					image = ImagesServiceFactory.makeImage(((Blob)cache.get(CACHED_THUMBNAIL_PREFIX + storyKey)).getBytes());
				} else {
					log.fine("Storing thumbnail from story with key = " + storyKey + " in the cache.");
					PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
					String query = "SELECT FROM " + Story.class.getName() + " WHERE key == " + storyKey;
					List<Story> stories = (List<Story>)persistenceManager.newQuery(query).execute();
					
					// if there is no story corresponding to the given key, quit
					if (stories.size() <= 0) {
						return;
					}  // if statement
					
					// if there is no image associated with this story, quit
					Story story = stories.get(0);
					if (story.getThumbnail() == null) {
						return ;
					}  // if statement
					
					// get image and then store it in the cache since it wasn't already in there
					image = ImagesServiceFactory.makeImage(story.getThumbnail().getBytes());
					cache.put(CACHED_THUMBNAIL_PREFIX + storyKey, story.getThumbnail());
				}  // if-else statement
			} else {
				if (cache.containsKey(CACHED_IMAGE_PREFIX + storyKey)) {
					image = ImagesServiceFactory.makeImage(((Blob)cache.get(CACHED_IMAGE_PREFIX + storyKey)).getBytes());
				} else {
					log.fine("Storing image from story with key = " + storyKey + " in the cache.");
					PersistenceManager persistenceManager = BigTableDao.get().getPersistenceManager();
					String query = "SELECT FROM " + Story.class.getName() + " WHERE key == " + storyKey;
					List<Story> stories = (List<Story>)persistenceManager.newQuery(query).execute();
					
					// if there is no story corresponding to the given key, quit
					if (stories.size() <= 0) {
						return;
					}  // if statement
					
					// if there is no image associated with this story, quit
					Story story = stories.get(0);
					if (story.getImage() == null) {
						return ;
					}  // if statement
					
					// get image and then store it in the cache since it wasn't already in there
					image = ImagesServiceFactory.makeImage(story.getImage().getBytes());
					cache.put(CACHED_IMAGE_PREFIX + storyKey, story.getImage());
				}  // if-else statement
			}
		} catch (CacheException e) {
			log.severe("Error getting stories from cache or datastore - " + e.getMessage());
			e.printStackTrace();
		}  // try-catch statement
		
        ByteArrayInputStream bais = new ByteArrayInputStream(image.getImageData());
		resp.setContentType(URLConnection.guessContentTypeFromStream(bais));
		resp.getOutputStream().write(image.getImageData());
	}  // doGet
}  // class declaration
