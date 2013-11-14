package com.xbrother.common.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Takes a ValidationException and maps that to a Response code.
 */
@Component
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

	private static final Logger logger = LoggerFactory.getLogger(ValidationExceptionMapper.class);

	@Override
	public Response toResponse(ValidationException e) {
		 logger.debug(e.getMessage(), e);
		return Response.status(Response.Status.BAD_REQUEST).entity(e.getValidationMessage()).build();
	}

}
