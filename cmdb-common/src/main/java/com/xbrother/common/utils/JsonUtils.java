package com.xbrother.common.utils;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON utilization : Translates JAVA object to JSON formatted String or
 * translates JSON formatted string to JAVA object.
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-20
 * @version 1.0
 */
public class JsonUtils {

	private final static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	private static class JsonTranslater {

//		private static String DATA_FORMATTER = "yyyy-MM-dd HH:mm:ss.SSSZ";
		private static String DATA_FORMATTER = "yyyy-MM-dd HH:mm:ss";
		private static final Gson gson;
		private static final Gson gson4Debug;
		static {
			gson = new GsonBuilder().setPrettyPrinting().setDateFormat(DATA_FORMATTER)
					.excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC).create();
			gson4Debug = new GsonBuilder().setPrettyPrinting().setDateFormat(DATA_FORMATTER)
					.excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC).create();
		}
	}

	/**
	 * translate java object to JSON String.
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		if (logger.isDebugEnabled()) {
			logger.debug("\n" + obj.getClass() + " to Json : \n" + getGson4Debug().toJson(obj));
		}
		return getGson().toJson(obj);
	}

	/**
	 * Translate java object from JSON String and casted by given class type. <br/>
	 * Get the class type like following:
	 * <p>
	 * Type genericType = new TypeToken<Collection<Foo>>(){}.getType();
	 * 
	 * @see com.google.gson.reflect.TypeToken
	 * @param json
	 * @param genericType
	 * @return
	 */
	public static <T> T fromJson(String json, Type genericType) {
		return getGson().fromJson(json, genericType);
	}

	/**
	 * Translate java object from JSON String and casted by given class.
	 * 
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> classOfT) {
		return getGson().fromJson(json, classOfT);
	}

	/**
	 * get a Gson instance that configurated by
	 * {@linkplain GsonBuilder#setPrettyPrinting()} and with
	 * 
	 * <pre>
	 * <code>setDateFormat(DATA_FORMATTER).excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)</code>
	 * </pre>
	 * 
	 * @see Gson
	 * @return
	 */
	public static Gson getGson4Debug() {
		return JsonTranslater.gson4Debug;
	}

	/**
	 * 
	 * get a Gson instance that configurated by {@link GsonBuilder} with
	 * 
	 * <pre>
	 * <code>setDateFormat(DATA_FORMATTER).excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)</code>
	 * </pre>
	 * 
	 * @return
	 */
	public static Gson getGson() {
		return JsonTranslater.gson;
	}
}
