package com.xbrother.common.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class LocaleUtils {

	private final static Logger LOG = LoggerFactory.getLogger(LocaleUtils.class);

	public static Locale getLocale() {
		return Locale.CHINA;
	}

	/**
	 * Get property value from resource bundle.
	 * <p>
	 * If current passed class's path's properties file appeared the matched
	 * key-value pairs, return the matched value. If hasn't, then recurrence to
	 * the passed class's super class's path's properties files to find the
	 * key-value pairs. If hasn't too, then recurrence too until there is no
	 * super class, then return the key for it's value.
	 * 
	 * @author arian zhang
	 * @date 2013-07-10 13:00
	 * @param clazz
	 * @param key
	 * @return
	 */
	public static String getResourcePropertyFromClassSuccession(Class<?> clazz, String key) {
		String value = getResourceProperty(clazz, key);
		if (value != null && !value.equals(key)) {
			return value;
		}
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null) {
			return getResourcePropertyFromClassSuccession(superClass, key);
		} else {
			return key;
		}
	}

	/**
	 * get property by key & default locale
	 * 
	 * @param key
	 * @return
	 */
	public static String getResourceProperty(Class<?> clazz, String key) {
		String path = clazz.getName().replace('.', '/');
		return getResourcePropertyInUTF8(path, key);
	}

	public static String getResourceProperty(Class<?> clazz, String key, Object... args) {
		String path = clazz.getName().replace('.', '/');

		String message = getResourcePropertyInUTF8(path, key);

		if (!StringUtils.isEmpty(message) && args != null && args.length > 0) {
			message = MessageFormat.format(message, args);
		}

		return message;
	}

	/**
	 * get properties by given path & key & locale
	 * 
	 * @param key
	 * @return the value of the key. return the key directly if the bundle of
	 *         the key not found
	 */
	public static String getResourceProperty(String path, String key) {
		return getResourcePropertyInUTF8(path, key);
	}

	private static String getResourcePropertyInUTF8(String path, String key) {
		Locale locale = Locale.CHINA;
		PropertyResourceBundle bundle = getResourceBoundle(path, locale);
		String result = key;
		if (bundle != null) {
			try {
				result = bundle.getString(key);
			} catch (MissingResourceException e) {
				LOG.warn("No key(" + key + ") in ResourceBundle : " + path + " locale : " + locale);
			}
		} else {
			LOG.warn("ReourceBoundle not found by path : " + path + " locale :" + locale);
		}
		return result;
	}

	public static PropertyResourceBundle getResourceBoundle(Class<?> clazz, Locale locale) {
		String path = clazz.getName().replace('.', '/');
		return getResourceBoundle(path, locale);
	}

	/**
	 * @param path
	 * @param locale
	 * @return
	 */
	private static PropertyResourceBundle getResourceBoundle(String path, Locale locale) {
		return ((PropertyResourceBundle) ResourceBundle.getBundle(path, locale, Thread.currentThread()
				.getContextClassLoader()));
	}
	
	public static String getPath(Class<?> clazz){
		String path = clazz.getName().replace('.', '/');
		return path;
	}
}