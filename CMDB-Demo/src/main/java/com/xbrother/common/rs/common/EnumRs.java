/**
 * 
 */
package com.xbrother.common.rs.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xbrother.common.entity.enums.Status;
import com.xbrother.common.rs.AbstractRs;
import com.xbrother.common.rs.constants.PathConst;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-30
 * @version 1.0
 */
@Component
@Path(EnumRs.PATH)
public class EnumRs extends AbstractRs{

	public final static String PATH = PathConst.COMMON + "/enums";

	private final static Logger LOG = LoggerFactory.getLogger(EnumRs.class);
	
	@GET
	@Path("/status")
	public Response getStatus(){
		List<Map<String, String>> statusList  = new ArrayList<Map<String,String>>();
		Map<String,String> statusMap;
		for(Status status : Status.values()){
			statusMap = new HashMap<String,String>();
			statusMap.put("value", status.value);
			statusMap.put("key", status.displayValue());
			statusList.add(statusMap);
		}
		return ok(statusList);
	}
	
}
