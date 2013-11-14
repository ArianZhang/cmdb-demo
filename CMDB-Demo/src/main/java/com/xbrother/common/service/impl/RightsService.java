package com.xbrother.common.service.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbrother.common.constants.CacheSpace;
import com.xbrother.common.constants.UserConst;
import com.xbrother.common.context.UserInfo;
import com.xbrother.common.dto.ui.MenuDTO;
import com.xbrother.common.entity.enums.Status;
import com.xbrother.common.entity.rights.Menu;
import com.xbrother.common.entity.rights.Role;
import com.xbrother.common.entity.rights.RolePermission;
import com.xbrother.common.entity.rights.User;
import com.xbrother.common.entity.rights.UserRole;
import com.xbrother.common.exception.BizsException;
import com.xbrother.common.exception.enums.AuthExceptionCode;
import com.xbrother.common.service.IRightsService;
import com.xbrother.common.utils.CodeUtils;

@Service
@Transactional
public class RightsService extends BaseService implements IRightsService {

	@Override
	//@Cacheable(value = { CacheSpace.MENUDTO })
	public List<MenuDTO> loadAllRightsMenus(Integer userId) {
		if (userId == UserConst.SUPER_ADMIN.getId()) {
			List<Menu> allMenus = baseDao.find("from " + Menu.class.getName() + " menu where menu.status='"
					+ Status.valid.value() + "' order by menu.id");
			return MenuDTO.convert(allMenus, null);
		}
		List<Menu> allMenus = baseDao.find("from " + Menu.class.getName() + " m where m.status='"
				+ Status.valid.value()
				+ "' and (m.permission.id is null or m.permission.id in ( select rp.permission.id from "
				+ RolePermission.class.getName() + " rp where rp.role.id in( select r.id from " + Role.class.getName()
				+ " r where r.id in (select ur.role.id from " + UserRole.class.getName() + " ur where ur.user.id = '"
				+ userId + "'))))" + " order by m.id");
		// User user = baseDao.load(User.class, userId);
		// Set<UserRole> userRoles = user.getUserRoles();
		// Set<RolePermission> rolePermissions;
		// Set<Menu> menus;
		// Set<Menu> result = new HashSet<Menu>();
		// for (UserRole userRole : userRoles) {
		// rolePermissions = userRole.getRole().getRolePermissions();
		// for (RolePermission rolePermission : rolePermissions) {
		// menus = rolePermission.getPermission().getMenus();
		// result.addAll(menus);
		// }
		// }
		// List<Menu> noPermissionMenus = baseDao.find("from " +
		// Menu.class.getName() + " m where m.permission is null");
		// result.addAll(noPermissionMenus);
		// result.addAll(allMenu);
		return MenuDTO.convert(allMenus, null);
	}

	@CacheEvict(value = { CacheSpace.MENUDTO })
	public void cleanAllRightsMenus(Integer userId) {

	}

	@Override
	public UserInfo validateUser(UserInfo user) {
		User userInDb = (User) baseDao.findUnique("from " + User.class.getName() + " u where u.username = ? and u.status = ?",
				user.getUsername(), Status.valid.value());
		if (userInDb == null) {
			throw new BizsException(AuthExceptionCode.ACCOUNT_NON_EXISTENT);
		}
		if (!CodeUtils.isPasswordValid(userInDb.getPassword(), user.getPassword())) {
			throw new BizsException(AuthExceptionCode.ACCOUNT_ERROR_PASSWORD);
		}
		return UserInfo.convert(userInDb);
	}
	
	@Override
	public boolean checkUserNameExist(UserInfo user) {
		User userInDb = (User) baseDao.findUnique("from " + User.class.getName() + " u where u.username = ? and u.status = ?",
				user.getUsername(), Status.valid.value);
		if (userInDb != null) {
			return true;
		}
		return false;
	}

	@Override
	public UserInfo saveOrUpdateUserDTO(UserInfo userInfo) {
		User user = UserInfo.reverse(userInfo, baseDao);
		return UserInfo.convert(user);
	}
}
