/**
 * 
 */
package com.xbrother.common.rs.hander;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.xbrother.common.exception.BizsException;
import com.xbrother.common.exception.enums.UtilsExceptionCode;
import com.xbrother.common.utils.JsonUtils;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-20
 * @version 1.0
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Component
public class GsonMessageBodyHandler implements MessageBodyWriter<Object>, MessageBodyReader<Object> {
	private final static Logger logger = LoggerFactory.getLogger(GsonMessageBodyHandler.class);

	private static final String UTF_8 = "UTF-8";

	// 2013-05-05 13:00:00.000-0800
	// private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSSZ";
	private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private Gson gson;

	private Gson getGson() {
		if (gson == null) {
			final GsonBuilder gsonBuilder = new GsonBuilder();
			gson = gsonBuilder.setDateFormat(DATE_PATTERN)
					.excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC).create();
		}
		return gson;
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType, java.lang.annotation.Annotation[] annotations,
			MediaType mediaType) {
		return true;
	}

	@Override
	public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException,
			WebApplicationException {
		if (logger.isDebugEnabled()) {
			String debug = "\n+\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";
			debug += "\n go into readFrom method...";
			debug += "\n genericType : " + genericType;
			debug += "\n type : " + type;
			debug += "\n mediaType : " + mediaType;
			debug += "\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n+";
			logger.debug(debug);
		}
		JsonReader jsonReader = null;
		jsonReader = new JsonReader(new InputStreamReader(entityStream, UTF_8));
		try {
			Type jsonType;
			if (type.equals(genericType)) {
				jsonType = type;
			} else {
				jsonType = genericType;
			}
			Object obj = getGson().fromJson(jsonReader, jsonType);
			if(logger.isDebugEnabled()){
				logger.debug("request body is : " + getGson().toJson(obj, jsonType));
			}
			return obj;
		}catch(Exception e){
			throw new BizsException(e, UtilsExceptionCode.JSON_CONVERT_ERROR);
		}finally {
			if (jsonReader != null) {
				jsonReader.close();
			}
		}
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public long getSize(Object object, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(Object object, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException,
			WebApplicationException {
		if (logger.isDebugEnabled()) {
			String debug = "\n+\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";
			debug += "\n go into writeTo method...";
			debug += "\n genericType : " + genericType;
			debug += "\n type : " + type;
			debug += "\n mediaType : " + mediaType;
			debug += "\n object.getClass() = " + object.getClass();
			debug += "\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n+";
			logger.debug(debug);
		}
		try {
			Type jsonType;
			if (type.equals(genericType)) {
				jsonType = type;
			} else {
				jsonType = genericType;
			}
			String result = getGson().toJson(object, jsonType);
			if(logger.isDebugEnabled()){
				logger.debug("response body is : " + result);
			}
			entityStream.write(result.getBytes(UTF_8));
		} catch(Exception e){
			throw new BizsException(e, UtilsExceptionCode.JSON_CONVERT_ERROR);
		}finally {
			if (entityStream != null)
				entityStream.close();
		}
	}
}
