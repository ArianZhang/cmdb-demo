/**
 * 
 */
package com.xbrother.common.rs.ui;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xbrother.common.context.CurrentContext;
import com.xbrother.common.dto.ui.MenuDTO;
import com.xbrother.common.entity.rights.Menu;
import com.xbrother.common.rs.SuperRs;
import com.xbrother.common.rs.constants.PathConst;
import com.xbrother.common.service.IRightsService;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-30
 * @version 1.0
 */
@Path(MenuRs.PATH)
@Component
public class MenuRs extends SuperRs<MenuDTO, Menu> {
	
	public final static String PATH = PathConst.WEBUI + "/menu";
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuRs.class);

	@Autowired
	IRightsService rightsService;

	@GET
	public Response getMenus() {
		List<MenuDTO> menus = rightsService.loadAllRightsMenus(CurrentContext.getCurrentUserId());
		return createResult(menus);
	}
	
	

}
