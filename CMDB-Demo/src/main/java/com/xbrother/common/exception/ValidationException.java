package com.xbrother.common.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 * An exception to hold the Set of ContstraintViolations and translate to a
 * message.
 */
public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = -8362895298532098190L;

	private Map<String, String> validationMessage = new HashMap<String, String>();

	public <T> ValidationException(Set<ConstraintViolation<T>> violations) {
		parseViolations(violations);
	}

	public Map<String, String> getValidationMessage() {
		return validationMessage;
	}

	// This is not a good translation. Needs work.
	private <T> void parseViolations(Set<ConstraintViolation<T>> violations) {
		for (ConstraintViolation<T> violation : violations) {
			validationMessage.put(violation.getPropertyPath().toString(),
					violation.getInvalidValue() + ":" + violation.getMessage());
		}
	}
}
