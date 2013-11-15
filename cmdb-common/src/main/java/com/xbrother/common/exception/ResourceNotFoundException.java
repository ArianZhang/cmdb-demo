package com.xbrother.common.exception;

/**
 * Custom exception that could hold the id of the resource not found.
 */
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6221814072902653349L;

	public ResourceNotFoundException(long id) {
		super("Resource "+id+" not found.");
	}
}
