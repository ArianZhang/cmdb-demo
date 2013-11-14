package com.xbrother.common.rs.rights;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.xbrother.common.dto.rights.RoleDTO;
import com.xbrother.common.entity.rights.Role;
import com.xbrother.common.rs.SuperRs;
import com.xbrother.common.rs.constants.PathConst;

@Component
@Path(RoleRs.PATH)
public class RoleRs extends SuperRs<RoleDTO, Role> {
	public final static String PATH = PathConst.RIGHTS_MGT + "/role";

	@Override
	@Path("/syn")
	@GET
	public Response getData(@HeaderParam("lastUpdate") String lastUpdate) {
		return ok();
	}

}
