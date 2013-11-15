package com.xbrother.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * 
 * @author Arian Zhang
 * @email hzhang@digitnexus.com
 * @Date 2013-7-22 上午11:41:11
 * @since v1.0.0
 */
public class BizsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(BizsException.class);

	private Integer status = 500;
	
	public BizsException(ExceptionCode code, Object... args) {
		super(code.getErrorCause(args));
	}
	
	public BizsException(Throwable cause, ExceptionCode code, Object... args) {
		super(code.getErrorCause(args), cause);
	}
	
	public BizsException(Integer status, ExceptionCode code, Object... args) {
		super(code.getErrorCause(args));
		this.status = status;
	}

	public BizsException(Integer status, Throwable cause, ExceptionCode code, Object... args) {
		super(code.getErrorCause(args), cause);
		this.status = status;
	}
	
	public BizsException(String msg) {
		super(msg);
	}

	public BizsException(Throwable cause, String msg) {
		super(msg, cause);
	}
	
	public BizsException(Integer status, String msg) {
		super(msg);
		this.status = status;
	}

	public BizsException(Integer status, Throwable cause, String msg) {
		super(msg, cause);
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

}
