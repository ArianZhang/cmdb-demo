/**
 * 
 */
package com.xbrother.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-25
 * @version 1.0
 */
public class PropertiesUtils {

	private final static Logger LOGGER = LoggerFactory.getLogger(LocaleUtils.class);

	public static Properties getProperties(String resourcePath) {
		Properties props = new Properties();
		List<InputStream> ins = null;
		try {
			ins = ResourceUtils.getResourcesAsStreamsSortByLong2Near(resourcePath);
			for (InputStream in : ins) {
				props.load(in);
			}
		} catch (IOException e) {
			LOGGER.warn("load c3p0.properties occur error!", e);
		} finally {
			for (InputStream in : ins) {
				StreamUtils.closeInputStream(in);
			}
		}
		return props;
	}
	
	public static Map<String,String> convert(Properties props){
		Map<String,String> map = new HashMap<String,String>();
		for(Object key: props.keySet()){
			map.put((String) key, props.getProperty((String) key));
		}
		return map;
	}
}
