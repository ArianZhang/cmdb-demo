/**
 * 
 */
package com.xbrother.common.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-27
 * @version 1.0
 */
@Component
@Provider
public class BizsExceptionMapper implements ExceptionMapper<BizsException> {

	private final static Logger LOGGER = LoggerFactory.getLogger(BizsExceptionMapper.class);

	@Override
	public Response toResponse(BizsException e) {
		LOGGER.debug(e.getMessage(), e);
		return Response.status(500).entity(e.getMessage()).build();
	}

}
