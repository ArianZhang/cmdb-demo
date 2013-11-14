/**
 * 
 */
package com.xbrother.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;



/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-25
 * @version 1.0
 */
public class CollectionUtils extends org.springframework.util.CollectionUtils {
	/**
	 * convert to List<T> from Enumeration<T>
	 * 
	 * @param enumeration
	 * @return
	 */
	public static <T> List<T> convert(Enumeration<T> enumeration) {
		if (enumeration == null) {
			return Collections.emptyList();
		}
		ArrayList<T> result = new ArrayList<T>();
		while (enumeration.hasMoreElements()) {
			result.add(enumeration.nextElement());
		}
		return result;
	}
}
