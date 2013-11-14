/**
 * 
 */
package com.xbrother.common.rs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.spi.resource.Singleton;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-30
 * @version 1.0
 */
@Singleton
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public abstract class AbstractRs {
	protected static Response ok() {
		return ok(null);
	}

	protected static Response ok(Object obj) {
		return createResult(obj);
	}

	protected static Response createNotAcceptable() {
		return createResult(Status.NOT_ACCEPTABLE, null);
	}

	protected static Response createResult(Object obj) {
		return createResult(Status.OK, obj);
	}

	protected static Response createResult(String key, Object value) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("lastUpdate", System.nanoTime());
		result.put(key, value);
		return createResult(Status.OK, result);
	}

	protected static Response createResult(Status status, Object obj) {
		return createResult(status.getStatusCode(), obj);
	}

	protected static Response createResult(Status status, Object obj, Map<String, String> headers) {
		return createResult(status.getStatusCode(), obj, headers);
	}

	protected static Response createResult(int status, Object obj) {
		return createResult(status, obj, null);
	}

	protected static <T> Response createSynResult(List<T> objs) {
		Map<String, String> head = new HashMap<String, String>();
		head.put("lastUpdate", System.currentTimeMillis() + "");
		if (objs == null || objs.size() == 0) {
			return createResult(304, objs, head);
		}
		return createResult(200, objs, head);
	}

	protected static <T> Response createSynResult(Object obj) {
		Map<String, String> head = new HashMap<String, String>();
		head.put("lastUpdate", System.currentTimeMillis() + "");
		if (obj == null) {
			return createResult(304, obj, head);
		}
		return createResult(200, obj, head);
	}

	protected static Response createResult(int status, Object obj, Map<String, String> headers) {
		ResponseBuilder builder = Response.status(status).entity(obj);
		if (headers != null) {
			for (String key : headers.keySet()) {
				builder = builder.header(key, headers.get(key));
			}
		}
		return builder.build();
	}
}
