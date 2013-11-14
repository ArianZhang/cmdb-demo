/**
 * 
 */
package com.xbrother.common.rs;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.xbrother.common.utils.JsonUtils;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-29
 * @version 1.0
 */
public abstract class AbstractRsTest {
	private static Logger LOGGER = LoggerFactory.getLogger(AbstractRsTest.class);

	// protected static String URL = "http://1.azglxx.sinaapp.com/rest";
	protected static String URL = "http://localhost:8080/rst-web/rest";
	protected static ClientConfig clientConfig;
	protected static Client client;
	protected static WebResource resource;
	protected static Cookie cookie;

	@BeforeClass
	public static void beforeTestClass() throws IOException {
		clientConfig = new DefaultClientConfig();
		client = Client.create(clientConfig);
		client.setFollowRedirects(true);
		resource = client.resource(URL);
		for (NewCookie cookie : login()) {
			AbstractRsTest.cookie = cookie;
		}
	}

	protected static List<NewCookie> login() {
		/*HashMap<String, String> userInfo = new HashMap<String, String>();
		userInfo.put("username", "superadmin");
		userInfo.put("password", "888888");
		ClientResponse response = resource.path(UserRs.PATH).path("/login").accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, JsonUtils.toJson(userInfo));
		assertOk(response, LOGGER);
		return response.getCookies();
*/	
		return Collections.emptyList();
	}
	
	protected static void printResponseEntity(ClientResponse cr, final Logger LOGGER){
		LOGGER.info("reponse  is " + cr.getEntity(String.class));
	}

	protected static void assertOk(ClientResponse cr, final Logger LOGGER) {
		LOGGER.info("reponse status is " + cr.getStatus());
		// LOGGER.info("reponse  is " + cr.getEntity(String.class));
		assertEquals("reponse status is not OK", 200, cr.getStatus());
	}

	protected abstract WebResource pathResource();

	protected Builder cookieBuilder(String path, MediaType accept, MediaType type) {
		return pathResource().path(path).cookie(cookie).accept(accept).type(type);
	}

	protected Builder cookieBuilder() {
		return pathResource().cookie(cookie).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
	}

	protected Builder cookieBuilder(String path) {
		return pathResource().path(path).cookie(cookie).accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON);
	}

	protected Builder cookieBuilder(String path, Map<String, String> queryMap) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if (queryMap != null) {
			for (String key : queryMap.keySet()) {
				params.add(key, queryMap.get(key));
			}
		}
		return pathResource().path(path).queryParams(params).cookie(cookie).accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON);
	}

	protected Builder synRsBuilder(String path) {
		return pathResource().path(path).cookie(cookie).accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).header("lastUpdate", "");
	}

	protected Builder synRsBuilder(String path, Long lastUpdate) {
		return pathResource().path(path).cookie(cookie).accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).header("lastUpdate", lastUpdate);
	}

	protected Builder headerBuilder(String path, String headKey, String headValue) {
		return pathResource().path(path).cookie(cookie).header(headKey, headValue).accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON);
	}

}
