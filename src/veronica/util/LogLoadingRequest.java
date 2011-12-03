package veronica.util;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LogLoadingRequest implements ServletContextListener {
	private static final Logger log = Logger.getLogger(LogLoadingRequest.class.getName());

	public void contextInitialized(ServletContextEvent sce) {
		log.info("Loading request occuring.");
	}  // contextInitialized

	public void contextDestroyed(ServletContextEvent sce) {
		log.info("Loading request complete.");
	}  // contextDestroyed
}  // class declaration
