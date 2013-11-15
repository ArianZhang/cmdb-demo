/**
 * 
 */
package com.xbrother.common.utils;

import java.util.UUID;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-9-27
 * @version 1.0
 */
public class GUIDUtils {

	public static String generate(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static void main(String[] args) {
		System.out.println(generate());
	}
}
