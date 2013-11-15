package com.xbrother.common.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

/**
 * Maps a ResourceNotFoundException to a 400 Response status.
 */
@Component
@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {

	@Override
	public Response toResponse(ResourceNotFoundException e) {
		return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
	}

}
