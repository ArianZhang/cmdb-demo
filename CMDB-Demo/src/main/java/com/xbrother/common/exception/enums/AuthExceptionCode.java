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
 * @date 2013-7-27
 * @version 1.0
 */
public enum AuthExceptionCode implements ExceptionCode {
	//账户不存在
	ACCOUNT_NON_EXISTENT("01"),
	//密码不正确
	ACCOUNT_ERROR_PASSWORD("02"),
	//非法入侵
	UNLAWFUL_ACCESS("03"),
	//用户名已经存在
	USERNAME_HAS_EXIST("04"),
	//不能删除超级用户
	CANNOT_DELETE_ADMIN("05"),
	;
	private final ExceptionCodeTemplate template;
	AuthExceptionCode(String errorCode){
		template = new ExceptionCodeTemplate(this, "RIGHTS-"+errorCode);
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
