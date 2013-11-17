package com.xbrother.cmdb.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xbrother.cmdb.rsclient.IncrementalUpdateTask;
import com.xbrother.common.rs.AbstractRs;

@Component
@Path("/basicdatamgt/incrementalupdate")
public class IncrementalUpdateRs extends AbstractRs {

	@Autowired
	IncrementalUpdateTask task;
	
	@GET
	public Response incrementalUpdate() {
		task.run();
		return ok();
	}
}
