package com.xbrother.common.exception.enums;

import com.xbrother.common.exception.ExceptionCode;

public enum UtilsExceptionCode implements ExceptionCode {
	IO_EXCEPTION("01"),
	CLOSE_STREAM("02"),
	CONVERT_ERROR("03"),
	REFLECT_ERROR("04"),
	GETWEATHER_ERROR("05"),
	DATE_CONVERT_ERROR("06"),
	JSON_CONVERT_ERROR("07")
	;
	private final ExceptionCodeTemplate template;

	UtilsExceptionCode(String errorCode) {
		template = new ExceptionCodeTemplate(this, "UTILS-" + errorCode);
	}

	@Override
	public String getErrorCode() {
		return template.getErrorCode();
	}

	@Override
	public String getErrorCause(Object... args) {
		return template.getErrorCause(args);
	}

}
