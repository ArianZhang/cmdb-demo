package com.xbrother.asset.web.listener;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.xbrother.cmdb.mqclient.SubscribeCIsTopic;
import com.xbrother.cmdb.rsclient.IncrementalUpdateTask;

public class AssertWebContextLoaderListener extends ContextLoaderListener implements ServletContextListener {
	 /**
     * @see ContextLoaderListener#ContextLoaderListener()
     */
    public AssertWebContextLoaderListener() {
        super();
    }
       
    /**
     * @see ContextLoaderListener#ContextLoaderListener(WebApplicationContext)
     */
    public AssertWebContextLoaderListener(WebApplicationContext context) {
        super(context);
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {
    	super.contextInitialized(event);
    	SubscribeCIsTopic subscribeCIsTopic = getCurrentWebApplicationContext().getBean(SubscribeCIsTopic.class);
    	IncrementalUpdateTask task = getCurrentWebApplicationContext().getBean(IncrementalUpdateTask.class);
    	new Thread(task).start();
    	new Thread(subscribeCIsTopic).start();
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event) {
    	super.contextDestroyed(event);
    }
}
