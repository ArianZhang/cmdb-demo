/**
 * 
 */
package com.xbrother.common.web.listener;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextListener;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-20
 * @version 1.0
 */
// @WebListener
public class RequestAndSessionListener extends RequestContextListener implements HttpSessionListener {
	private final static Logger LOGGER = LoggerFactory.getLogger(RequestAndSessionListener.class);

	private AtomicInteger count = new AtomicInteger(0);

	@Override
	public void requestInitialized(ServletRequestEvent requestEvent) {
		super.requestInitialized(requestEvent);
	}

	@Override
	public void requestDestroyed(ServletRequestEvent requestEvent) {
		super.requestDestroyed(requestEvent);
	}

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		count.incrementAndGet();
		if (LOGGER.isDebugEnabled()) {
			HttpSession session = sessionEvent.getSession();
			LOGGER.debug("\n===========session created, id=" + session.getId() + ",max inactive interval is"
					+ session.getMaxInactiveInterval() + ",current session count : " + count + ".==========\n");
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		count.decrementAndGet();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("\n================session destroyed. current session count:" + count
					+ "====================\n");
		}
	}

}
