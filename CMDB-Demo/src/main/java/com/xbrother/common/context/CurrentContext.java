/**
 * 
 */
package com.xbrother.common.context;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.xbrother.common.constants.SessionKey;
import com.xbrother.common.constants.UserConst;

/**
 * request current context
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-20
 * @version 1.0
 */
public class CurrentContext {

	private static ThreadLocal<HttpServletRequest> requestThread = new ThreadLocal<HttpServletRequest>();

	/**
	 * put key-value to context
	 * 
	 * @param key
	 * @param value
	 * @param scope
	 */
	public static void put(String key, Object value, ContextScope scope) {
		switch (scope) {
		case GLOBAL_SESSION:
			requestThread.get().getServletContext().setAttribute(key, value);
		case SESSION:
			requestThread.get().getSession().setAttribute(key, value);
		case REQUEST:
			requestThread.get().setAttribute(key, value);
			break;
		}
	}

	/**
	 * get value by key from context.
	 * 
	 * @param key
	 * @param classOfT
	 * @return
	 */
	public static <T> T get(String key, Class<T> classOfT) {
		T t = classOfT.cast(requestThread.get().getAttribute(key));
		if (t == null) {
			t = classOfT.cast(requestThread.get().getSession().getAttribute(key));
			if (t == null) {
				t = classOfT.cast(requestThread.get().getServletContext().getAttribute(key));
			}
		}
		return t;
	}

	public static Integer getCurrentUserId() {
		if (requestThread.get() == null) {
			return UserConst.SUPER_ADMIN.getId();
		}
		return getCurrentUser().getId();
	}

	/**
	 * Get current user
	 * 
	 * @date 2013-7-20
	 * @return
	 */
	public static UserInfo getCurrentUser() {
		if (requestThread.get() == null) {
			return UserConst.SUPER_ADMIN;
		}
		UserInfo user = (UserInfo) requestThread.get().getAttribute(SessionKey.CURRENT_USER_KEY);
		if (user == null) {
			user = (UserInfo) requestThread.get().getSession().getAttribute(SessionKey.CURRENT_USER_KEY);
		}
		return user;
	}

	/*public static Integer getCurrentCustomerId() {
		if (requestThread.get() == null) {
			return UserConst.ANONYMOUS_CUSTOMER.getId();
		}
		return getCurrentCustomer().getId();
	}

	public static CustomerDTO getCurrentCustomer() {
		if (requestThread.get() == null) {
			return UserConst.ANONYMOUS_CUSTOMER;
		}
		CustomerDTO customer = (CustomerDTO) requestThread.get().getAttribute(SessionKey.CURRENT_CUSTOMER_KEY);
		if (customer == null) {
			customer = (CustomerDTO) requestThread.get().getSession().getAttribute(SessionKey.CURRENT_CUSTOMER_KEY);
		}
		return customer;
	}*/

	public static Locale getLocale() {
		if (requestThread.get() == null) {
			return Locale.CHINA;
		}
		String localeStr = requestThread.get().getHeader("locale");
		if (StringUtils.isEmpty(localeStr)) {
			return Locale.CHINA;
		}
		for (Locale locale : Locale.getAvailableLocales()) {
			if (localeStr.equals(locale.toString())) {
				return locale;
			}
		}
		return Locale.CHINA;
	}

	/**
	 * Set current user
	 * 
	 * @date 2013-7-20
	 * @param user
	 */
	public static void setCurrentUser(UserInfo user) {
		requestThread.get().setAttribute(SessionKey.CURRENT_USER_KEY, user);
		requestThread.get().getSession().setAttribute(SessionKey.CURRENT_USER_KEY, user);
	}

	/*public static void setCurrentCustomer(CustomerDTO customer) {
		requestThread.get().setAttribute(SessionKey.CURRENT_CUSTOMER_KEY, customer);
		requestThread.get().getSession().setAttribute(SessionKey.CURRENT_CUSTOMER_KEY, customer);
	}*/

	public static void initialize(HttpServletRequest request) {
		requestThread.set(request);
	}

	public static void release() {
		requestThread.set(null);
	}

	public static void invalidSession() {
		requestThread.get().getSession().invalidate();
	}

	public static HttpServletRequest getHttpServletRequest() {
		return requestThread.get();
	}
}
