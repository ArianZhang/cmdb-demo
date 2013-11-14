/**
 * 
 */
package com.xbrother.common.rs.rights;

import java.util.List;

import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xbrother.common.aop.annotation.Validate;
import com.xbrother.common.context.CurrentContext;
import com.xbrother.common.context.UserInfo;
import com.xbrother.common.dto.ui.PaginationRequestDTO;
import com.xbrother.common.entity.rights.User;
import com.xbrother.common.exception.BizsException;
import com.xbrother.common.exception.enums.AuthExceptionCode;
import com.xbrother.common.query.Condition;
import com.xbrother.common.rs.SuperRs;
import com.xbrother.common.rs.constants.PathConst;
import com.xbrother.common.service.IRightsService;
import com.xbrother.common.utils.CodeUtils;

/**
 * user management resource
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-20
 * @version 1.0
 */
@Path(UserRs.PATH)
@Component
public class UserRs extends SuperRs<UserInfo, User> {
	public static final String PATH = PathConst.RIGHTS_MGT + "/user";

	@Autowired
	IRightsService rightsService;

	@Autowired
	Validator validator;

	@Path("/login")
	@POST
	@Validate(classOfDTO = UserInfo.class)
	public Response login(UserInfo userInfo) {
		CurrentContext.setCurrentUser(rightsService.validateUser(userInfo));
		return ok();
	}

	@Path("/logout")
	@POST
	public Response logout() {
		rightsService.cleanAllRightsMenus(CurrentContext.getCurrentUserId());
		CurrentContext.invalidSession();
		return ok();
	}

	@Override
	@Path("/save")
	@POST
	public Response save(UserInfo dto) {
		if(!rightsService.checkUserNameExist(dto)){
			dto.setPassword(CodeUtils.md5Encode(dto.getPassword()));
			return ok(rightsService.saveOrUpdateUserDTO(dto));
		}else{
			throw new BizsException(AuthExceptionCode.USERNAME_HAS_EXIST);
		}
		
	}

	@Override
	@Path("/update")
	@PUT
	public Response update(UserInfo dto) {
		dto.setPassword(CodeUtils.md5Encode(dto.getPassword()));
		return ok(rightsService.saveOrUpdateUserDTO(dto));
	}

	@Override
	@Path("/syn")
	@GET
	public Response getData(@HeaderParam("lastUpdate") String lastUpdate) {
		return ok();
	}

	@Override
	@Path("/getAll")
	@GET
	public Response getAll() {
		return super.getAll();
	}

	@Override
	@Path("/datagridlist")
	@POST
	public Response dataGridList(PaginationRequestDTO dto) {
		dto.getConditions().add(Condition.HIDDEN_SUPER);
		return super.dataGridList(dto);
	}

	@Override
	@Path("/delete")
	@PUT
	public Response delete(List<Integer> ids) {
		if(ids.contains(1) || ids.contains(0)){
			throw new BizsException(AuthExceptionCode.CANNOT_DELETE_ADMIN);
		}
		return super.delete(ids);
	}

}
