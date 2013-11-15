package com.xbrother.cmdb.rs;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.xbrother.cmdb.entity.ConfigurationItem;
import com.xbrother.cmdb.entity.LastUpdateMark;
import com.xbrother.cmdb.entity.utils.EntityRelationUtils;
import com.xbrother.common.constants.HeaderKey;
import com.xbrother.common.query.Condition;
import com.xbrother.common.rs.AbstractRs;
import com.xbrother.common.service.IBaseService;
import com.xbrother.common.utils.CollectionUtils;
import com.xbrother.common.utils.GUIDUtils;
import com.xbrother.common.utils.JsonUtils;

@Component
@Path("/basicdatamgt/incrementalupdate")
public class IncrementalUpdateRs extends AbstractRs {

	private static Logger LOGGER = LoggerFactory.getLogger(IncrementalUpdateRs.class);

	protected String URL = "http://localhost:8080/cmdb-web/rest";
	protected ClientConfig clientConfig;
	protected Client client;
	protected WebResource resource;
	protected Cookie cookie;

	@Autowired
	protected IBaseService baseService;

	public IncrementalUpdateRs() {
		clientConfig = new DefaultClientConfig();
		client = Client.create(clientConfig);
		client.setFollowRedirects(true);
		resource = client.resource(URL);
		// for (NewCookie cookie : login()) {
		// AbstractRsTest.cookie = cookie;
		// }
	}

	
	private void incrementalUpdateCIs() {
		List<Condition> conditions = new ArrayList<Condition>();
		Condition condition = new Condition("mark", "=", "ci");
		conditions.add(condition);
		List<LastUpdateMark> marks = baseService.query(conditions, LastUpdateMark.class);
		ClientResponse response = null;
		if (CollectionUtils.isEmpty(marks)) {
			response = resource.path("/basicdatamgt/ci/syn").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		} else {
			response = resource.path("/basicdatamgt/ci/syn").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
					.header(HeaderKey.LASTUPDATE, marks.get(0).getLastUpdate()).get(ClientResponse.class);
		}
		if(response.getStatus() == 200 || response.getStatus() == 206){
			List<ConfigurationItem> cis = JsonUtils.fromJson(response.getEntity(String.class),new TypeToken<List<ConfigurationItem>>(){}.getType());
			for(ConfigurationItem ci : cis){
				EntityRelationUtils.initialCIRelation(ci);
				baseService.saveOrUpdate(ci);
			}
		}
		Long newMarkLastUpdate = new Long(response.getHeaders().getFirst(HeaderKey.LASTUPDATE));
		if(CollectionUtils.isEmpty(marks)){
			LastUpdateMark lastUpdateMark = new LastUpdateMark();
			lastUpdateMark.setLastUpdate(newMarkLastUpdate);
			lastUpdateMark.setMark("ci");
			lastUpdateMark.setUid(GUIDUtils.generate());
			baseService.addNew(lastUpdateMark);
		}else{
			marks.get(0).setLastUpdate(newMarkLastUpdate);
			baseService.update(marks.get(0));
		}
		if(response.getStatus() == 206){
			incrementalUpdateCIs();
		}
	}

	@GET
	public Response incrementalUpdate() {
		incrementalUpdateCIs();
		return ok();
	}
}
