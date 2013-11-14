package com.xbrother.common.exception.utils;

import com.xbrother.common.exception.BizsException;

public class ExceptionUtils {

	public static BizsException findBizsException(Throwable e) {
		if (e instanceof BizsException) {
			return (BizsException) e;
		} else {
			Throwable e2 = e.getCause();
			if (e2 != null) {
				return findBizsException(e2);
			} else {
				return null;
			}
		}
	}
}
