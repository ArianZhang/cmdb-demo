/**
 * 
 */
package com.xbrother.common.web.listener;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextListener;

import com.xbrother.common.constants.CacheSpace;
import com.xbrother.common.constants.SessionKey;
import com.xbrother.common.context.CurrentContext;
import com.xbrother.common.context.UserInfo;

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
		CurrentContext.initialize((HttpServletRequest) requestEvent.getServletRequest());
	}

	@Override
	public void requestDestroyed(ServletRequestEvent requestEvent) {
		super.requestDestroyed(requestEvent);
		CurrentContext.release();
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
		UserInfo user = (UserInfo) sessionEvent.getSession().getAttribute(SessionKey.CURRENT_USER_KEY);
		if (user != null) {
			ContextLoader.getCurrentWebApplicationContext().getBean(CacheManager.class).getCache(CacheSpace.MENUDTO)
					.evict(user.getId());
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("\n================session destroyed. current session count:" + count
					+ "====================\n");
		}
	}

}
