/**
 * 
 */
package com.xbrother.common.aop;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xbrother.common.aop.annotation.Validate;
import com.xbrother.common.dto.SuperDTO;
import com.xbrother.common.exception.ValidationException;
import com.xbrother.common.rs.SuperRs;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-28
 * @version 1.0
 */
@Aspect
@Component
public class ValidateRsRequest {

	@Autowired
	private Validator validator;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Before("@annotation(validate) && target(rs)")
	public void beforeValidate(JoinPoint joinPoint, Validate validate, SuperRs rs) {
//		Class<? extends SuperDTO> dtoClass = validate.classOfDTO().equals(SuperDTO.class) ? rs.getDTOClass() : validate
//				.classOfDTO();
		int argumentIndex = getSuperDTOArgumentIndex(joinPoint, validate.classOfDTO());
		if (argumentIndex == -1) {
			return;
		}
		SuperDTO dto = validate.classOfDTO().cast(joinPoint.getArgs()[argumentIndex]);
		Set<ConstraintViolation<SuperDTO>> validations = validator.validate(dto);
		if (validations != null && validations.size() > 0) {
			throw new ValidationException(validations);
		}
	}

	private int getSuperDTOArgumentIndex(JoinPoint joinPoint, Class<? extends SuperDTO> classOfDTO) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Class<?>[] parameterTypes = methodSignature.getParameterTypes();
		if (parameterTypes != null && parameterTypes.length > 0) {
			for (int i = 0; i < parameterTypes.length; i++) {
				if (classOfDTO.equals(parameterTypes[i])) {
					return i;
				}
			}
		}
		return -1;
	}
}
