/**
 * 
 */
package com.xbrother.common.rs.appstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xbrother.common.dto.ui.PaginationRequestDTO;
import com.xbrother.common.entity.appstore.AppSpace;
import com.xbrother.common.entity.appstore.AppVersion;
import com.xbrother.common.entity.enums.Status;
import com.xbrother.common.entity.enums.UpdateLevel;
import com.xbrother.common.rs.AbstractRs;
import com.xbrother.common.service.appstore.IAppStoreService;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-9-23
 * @version 1.0
 */
@Path(AppStoreRs.PATH)
@Component
public class AppStoreRs extends AbstractRs {
	public final static String PATH = "/appstore";
	
	@Autowired
	IAppStoreService appStoreService;

	@Path("/space/datagridlist")
	@POST
	public Response appSpaceDataGridList(PaginationRequestDTO dto) {
		return ok(appStoreService.appSpaceDataGridList(dto));
	}

	@Path("/space/get")
	@GET
	public Response appSpaceGet(@QueryParam("id") Integer id) {
		return ok(appStoreService.getAppSpace(id));
	}

	@Path("/space/getAll")
	@GET
	public Response appSpaceGetAll() {
		return ok(appStoreService.getAllAppSpace());
	}

	@Path("/space/save")
	@POST
	public Response appSpaceSave(AppSpace appSpace) {
		return ok(appStoreService.saveOrUpdateAppSpace(appSpace));
	}

	@Path("/space/update")
	@PUT
	public Response appSpaceUpdate(AppSpace appSpace) {
		return ok(appStoreService.saveOrUpdateAppSpace(appSpace));
	}

	@Path("/space/delete")
	@PUT
	public Response appSpaceDelete(List<Integer> ids) {
		for(Integer id : ids){
			appStoreService.deleteAppSpace(id);
		}
		return ok();
	}

	@Path("/space/recover")
	@PUT
	public Response appSpaceRecover(List<Integer> ids) {
		for(Integer id : ids){
			appStoreService.recoverAppSpace(id);
		}
		return ok();
	}

	@Path("/version/datagridlist")
	@POST
	public Response appDataGridList(PaginationRequestDTO dto) {
		return ok(appStoreService.versionDataGridList(dto));
	}

	@Path("/version/get")
	@GET
	public Response versionGet(@QueryParam("id") Integer id) {
		return ok(appStoreService.getVersion(id));
	}

	@Path("/version/save")
	@POST
	public Response versionSave(AppVersion version) {
		return ok(appStoreService.saveOrUpdateVersion(version));
	}

	@Path("/version/update")
	@POST
	public Response versionUpdate(AppVersion version) {
		return ok(appStoreService.saveOrUpdateVersion(version));
	}

	@Path("/version/delete")
	@PUT
	public Response versionDelete(List<Integer> ids) {
		for(Integer id : ids){
			appStoreService.deleteAppVersion(id);
		}
		return ok();
	}

	@Path("/version/recover")
	@PUT
	public Response versionRecover(List<Integer> ids) {
		for(Integer id : ids){
			appStoreService.recoverAppVersion(id);
		}
		return ok();
	}

	@Path("/{spaceCode}/released/last")
	@GET
	public Response versionGet(@PathParam("spaceCode") String spaceCode) {
		return ok(appStoreService.getLastReleasedApp(spaceCode));
	}
	
	@GET
	@Path("/updatelevel")
	public Response getUpdateLevel(){
		List<Map<String, String>> statusList  = new ArrayList<Map<String,String>>();
		Map<String,String> statusMap;
		for(UpdateLevel status : UpdateLevel.values()){
			statusMap = new HashMap<String,String>();
			statusMap.put("value", status.value);
			statusMap.put("key", status.displayValue());
			statusList.add(statusMap);
		}
		return ok(statusList);
	}
}
