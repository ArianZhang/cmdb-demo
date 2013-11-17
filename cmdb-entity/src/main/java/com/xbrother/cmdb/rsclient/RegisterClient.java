package com.xbrother.cmdb.rsclient;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.xbrother.cmdb.entity.ConfigurationItem;
import com.xbrother.common.utils.JsonUtils;
import com.xbrother.common.utils.PropertiesUtils;

@Component
public class RegisterClient {
	private static Logger LOGGER = LoggerFactory.getLogger(IncrementalUpdateTask.class);

	protected String URL = "http://localhost:8080/cmdb-web/rest";
	{
		URL = PropertiesUtils.getProperties("/config.properties").getProperty("cmdb-web-address");
	}
	protected ClientConfig clientConfig;
	protected Client client;
	protected WebResource resource;
	protected Cookie cookie;
	
	public RegisterClient(){
		clientConfig = new DefaultClientConfig();
		client = Client.create(clientConfig);
		client.setFollowRedirects(true);
		resource = client.resource(URL);
	}

	public ClientResponse registerCI(ConfigurationItem ci, Integer actionType) {
		ClientResponse response = resource.path("/basicdatamgt/ci/register").accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).header("uid", ci.getUid()).header("actionType", actionType)
				.post(ClientResponse.class, JsonUtils.toJson(ci));
		return response;
	}

}
