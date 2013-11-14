package com.xbrother.common.service;

import java.util.List;

import com.xbrother.common.context.UserInfo;
import com.xbrother.common.dto.ui.MenuDTO;
import com.xbrother.common.entity.rights.User;


public interface IRightsService extends IBaseService {
	
	List<MenuDTO> loadAllRightsMenus(Integer userId);
	void cleanAllRightsMenus(Integer userId);
	UserInfo validateUser(UserInfo userInfo);
	UserInfo saveOrUpdateUserDTO(UserInfo userInfo);
	boolean checkUserNameExist(UserInfo user);
}
