/**
 * 
 */
package com.xbrother.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.xbrother.common.exception.BizsException;
import com.xbrother.common.exception.enums.UtilsExceptionCode;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-30
 * @version 1.0
 */
public class DateFormatUtils {

	public static enum Pattern {
		DATE_PATTERN("yyyy-MM-dd"), DATETIME_PATTERN("yyyy-MM-dd HH:mm:ss"), DATE_PATTERN_ZH("yyyy年MM月dd日");
		public String value;

		Pattern(String pattern) {
			this.value = pattern;
		}
	}

	public static enum DateType {
		YEAR, MONTH, DAY, HOUR, MINUTE, SECOND
	}

	public static String DATE_PATTERN = "";

	public static String dateToString(Date date, Pattern pattern) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(pattern.value);
		return dateFormat.format(date);
	}

	public static String dateToString(Date date) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(Pattern.DATETIME_PATTERN.value);
		return dateFormat.format(date);
	}

	public static Date getDayTimeStart(String str) {
		Date date = stringToDate(str + " 00:00:00", Pattern.DATETIME_PATTERN);
		return date;
	}

	public static Date getDayTimeEnd(String str) {
		Date date = stringToDate(str + " 23:59:59", Pattern.DATETIME_PATTERN);
		return date;
	}

	public static Date stringToDate(String str, Pattern pattern) {
		if (str == null) {
			return new Date();
		}
		final SimpleDateFormat dateFormat = new SimpleDateFormat(pattern.value);
		Date date = null;
		try {
			date = dateFormat.parse(str);
		} catch (ParseException e) {
			throw new BizsException(e, UtilsExceptionCode.DATE_CONVERT_ERROR);
		}
		return date;
	}

	public static String getSpecifiedDayAfter(String specifiedDay, Pattern pattern) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat(pattern.value).parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat(pattern.value).format(c.getTime());
		return dayAfter;
	}

	public static Date getDateIncreasedBy(Date date, DateType dateType, int value) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		switch (dateType) {
		case YEAR:
			c.set(Calendar.YEAR, c.get(Calendar.YEAR) + value);
			break;
		case DAY:
			c.set(Calendar.DATE, c.get(Calendar.DATE) + value);
			break;
		case HOUR:
			c.set(Calendar.HOUR, c.get(Calendar.HOUR) + value);
			break;
		case MINUTE:
			c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + value);
			break;
		case MONTH:
			c.set(Calendar.MONTH, c.get(Calendar.MONTH) + value);
			break;
		case SECOND:
			c.set(Calendar.SECOND, c.get(Calendar.SECOND) + value);
			break;
		default:
			break;
		}
		return c.getTime();
	}

	public static void main(String[] args) {
		System.out.println(dateToString(new Date(), Pattern.DATE_PATTERN));
		System.out.println(dateToString(new Date()));
		System.out.println(getSpecifiedDayAfter("2013-08-09 15:41:00", Pattern.DATETIME_PATTERN));
	}
}
