/**
 * 
 */
package com.xbrother.common.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-20
 * @version 1.0
 */
//@WebFilter(urlPatterns = { "/*" }, asyncSupported = true, description = "Encode UTF-8")
public class EncodingFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(EncodingFilter.class);

	private String charset = "UTF-8";

	@SuppressWarnings("unused")
	private final static Logger log = LoggerFactory.getLogger(EncodingFilter.class);

	public void init(FilterConfig config) throws ServletException {
		String paramCharset = config.getInitParameter("charset");
		if (paramCharset != null && paramCharset.length() > 0) {
			this.charset = paramCharset;
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		if (LOGGER.isDebugEnabled()) {
			HttpServletRequest servletRequest = (HttpServletRequest) request;
			String url = servletRequest.getRequestURI();
			// String contextPath = servletRequest.getContextPath();
			// url = url.substring(url.indexOf(contextPath) +
			// contextPath.length());
			LOGGER.debug("request url is " + url);
		}
		request.setCharacterEncoding(charset);
		response.setCharacterEncoding(charset);
		chain.doFilter(request, response);
	}

	public void destroy() {
	}
}