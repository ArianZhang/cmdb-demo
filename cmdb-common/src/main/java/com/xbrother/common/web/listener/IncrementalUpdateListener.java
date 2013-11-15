package com.xbrother.common.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

/**
 * Application Lifecycle Listener implementation class IncrementalUpdateListener
 *
 */
public class IncrementalUpdateListener extends ContextLoaderListener implements ServletContextListener {
       
    /**
     * @see ContextLoaderListener#ContextLoaderListener()
     */
    public IncrementalUpdateListener() {
        super();
    }
       
    /**
     * @see ContextLoaderListener#ContextLoaderListener(WebApplicationContext)
     */
    public IncrementalUpdateListener(WebApplicationContext context) {
        super(context);
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {
    	super.contextInitialized(event);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event) {
    	super.contextDestroyed(event);
    }
	
}
