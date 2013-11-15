/**
 * All rights reserved by DigitNexus Technology INC.
 */
package com.xbrother.common.exception;

import java.text.MessageFormat;

import com.xbrother.common.utils.LocaleUtils;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-27
 * @version 1.0
 */
public interface ExceptionCode {
	
	/**
	 * 
	 * @return
	 */
	String getErrorCode();

	/**
	 * 
	 * @param args
	 * @return
	 */
	String getErrorCause(Object... args);

	/**
	 * 
	 * this template for each enumeration that implements ExceptionCode
	 * interface.
	 * 
	 * @author Arian Zhang
	 * @email arian_zhang@foxmail.com
	 * @date 2013-7-27
	 * @version 1.0
	 */
	class ExceptionCodeTemplate implements ExceptionCode {

		private final String errorCode;
		private final ExceptionCode codeEnum;

		public ExceptionCodeTemplate(ExceptionCode codeEnum, String errorCode) {
			this.codeEnum = codeEnum;
			this.errorCode = errorCode;
		}

		public String getResourceProperty() {
			return LocaleUtils.getResourceProperty(codeEnum.getClass(), codeEnum.toString());
		}

		@Override
		public String getErrorCause(Object... args) {
			if (args == null || args.length == 0) {
				return getResourceProperty();
			}
			return MessageFormat.format(getResourceProperty(), args);
		}

		@Override
		public String getErrorCode() {
			return errorCode;
		}

	}
}
