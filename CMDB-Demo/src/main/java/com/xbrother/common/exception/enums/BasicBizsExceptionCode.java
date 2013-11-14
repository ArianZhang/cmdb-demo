/**
 * 
 */
package com.xbrother.common.exception.enums;

import com.xbrother.common.exception.ExceptionCode;
import com.xbrother.common.exception.ExceptionCode.ExceptionCodeTemplate;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-29
 * @version 1.0
 */
public enum BasicBizsExceptionCode implements ExceptionCode{
	BIZ_OPERATION_ERROR("01"),
	DELETE_ENTITY_ERROR("02"),
	RECOVER_ENTITY_ERROR("03"),
	;
	private final ExceptionCodeTemplate template;
	BasicBizsExceptionCode(String errorCode){
		template = new ExceptionCodeTemplate(this, "BASIC-"+errorCode);
	}

	@Override
	public String getErrorCode() {
		return template.getErrorCode();
	}

	@Override
	public String getErrorCause(Object... args) {
		return template.getErrorCause(args);
	}

	public static void main(String[] args) {
		for(AuthExceptionCode code : AuthExceptionCode.values()){
			System.out.println(code.getErrorCause());
		}
	}
}
