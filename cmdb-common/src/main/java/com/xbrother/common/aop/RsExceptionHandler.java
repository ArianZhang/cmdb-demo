/**
 * 
 */
package com.xbrother.common.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.xbrother.common.exception.BizsException;
import com.xbrother.common.exception.ResourceNotFoundException;
import com.xbrother.common.exception.ValidationException;
import com.xbrother.common.exception.enums.BasicBizsExceptionCode;
import com.xbrother.common.exception.utils.ExceptionUtils;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-28
 * @version 1.0
 */
@Aspect
@Component
public class RsExceptionHandler {

	@AfterThrowing(pointcut = "execution(* com.xbrother.*.rs.*.*(..))", throwing = "t")
	public void handleRsExcpetion(Throwable t) {
		BizsException bizsException = ExceptionUtils.findBizsException(t);
		if (bizsException != null) {
			throw bizsException;
		}
		if (t instanceof ValidationException) {
			throw (ValidationException) t;
		} else if (t instanceof ResourceNotFoundException) {
			throw (ResourceNotFoundException) t;
		} else if (t instanceof BizsException) {
			throw (BizsException) t;
		} else {
			throw new BizsException(t, BasicBizsExceptionCode.BIZ_OPERATION_ERROR);
		}
	}

}
