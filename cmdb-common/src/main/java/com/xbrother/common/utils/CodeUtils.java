package com.xbrother.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * util class for decode and encode
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-27
 * @version 1.0
 */
@SuppressWarnings("restriction")
public class CodeUtils {
	private static final Logger log = LoggerFactory.getLogger(CodeUtils.class);

	private static final Md5PasswordEncoder MD5_PASSWORD_ENCODER = new Md5PasswordEncoder();

	public static String urlDecode(String msg, String encoding) throws UnsupportedEncodingException {
		if (msg == null || "".equals(msg.trim())) {
			return "";
		}
		return URLDecoder.decode(msg, encoding);
	}

	public static String urlEncode(String msg, String encoding) throws UnsupportedEncodingException {
		log.debug(System.getProperty("file.encoding"));
		if (msg == null || "".equals(msg.trim())) {
			return "";
		}
		return URLEncoder.encode(msg, encoding);
	}

	/**
	 * Use BASE64 to encode the bytes array
	 * 
	 * @param s
	 *            bytes array to be encoded
	 * @return base64 string
	 */
	public static String base64Encode(byte[] s) {
		if (s == null || s.length == 0) {
			return "";
		}

		return (new BASE64Encoder()).encode(s).replaceAll("\r", "").replaceAll("\n", "");
	}

	/**
	 * use BASE64 to decode the string
	 * 
	 * @param s
	 *            base64 String
	 * @return bytes array
	 */
	public static byte[] base64Decode(String s) {
		byte[] result = {};
		if (s == null || "".equals(s.trim())) {
			return result;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			result = decoder.decodeBuffer(s);
		} catch (Exception e) {
			log.error("error when decode this string：" + s, e);
		}
		return result;
	}

	/**
	 * use MD5 encode the string
	 * 
	 * @date 2013-7-27
	 * @param s
	 * @return
	 */
	public static String md5Encode(String rawPass) {
		return MD5_PASSWORD_ENCODER.encodePassword(rawPass, "azglxx");
	}

	/**
	 * validate password
	 * 
	 * @date 2013-7-27
	 * @param encPass
	 * @param rawPass
	 * @return
	 */
	public static boolean isPasswordValid(String encPass, String rawPass) {
		return MD5_PASSWORD_ENCODER.isPasswordValid(encPass, rawPass, "azglxx");
	}
	
	public static void main(String[] args) {
		System.out.println(md5Encode("888888"));
	}
}
