package com.xbrother.common.context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.xbrother.common.dao.IBaseDao;
import com.xbrother.common.dto.SuperDTO;
import com.xbrother.common.dto.rights.RoleDTO;
import com.xbrother.common.entity.rights.User;
import com.xbrother.common.entity.rights.UserRole;

/**
 * user information
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-20
 * @version 1.0
 */
public class UserInfo extends SuperDTO {

	@NotEmpty
	@Length(min = 4, max = 20)
	private String username;
	@NotEmpty
	@Length(min = 6, max = 16)
	private String password;

	private List<RoleDTO> roles;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}

	public static UserInfo convert(User user) {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(user.getId());
		userInfo.setUsername(user.getUsername());
		userInfo.setPassword(user.getPassword());
		userInfo.setStatus(user.getStatus());
		if (user.getUserRoles().size() > 0) {
			List<RoleDTO> roleDTOs = new ArrayList<RoleDTO>();
			for (UserRole userRole : user.getUserRoles()) {
				roleDTOs.add(RoleDTO.convert(userRole.getRole()));
			}
			userInfo.setRoles(roleDTOs);
		}
		return userInfo;
	}

	public static List<UserInfo> converts(ArrayList<User> users) {
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		for (User user : users) {
			userInfos.add(convert(user));
		}
		return userInfos;
	}

	public static User reverse(UserInfo userInfo, IBaseDao baseDao) {
		User user = new User();
		user.setId(userInfo.getId());
		user.setUsername(userInfo.getUsername());
		user.setPassword(userInfo.getPassword());
		if (user.getId() != null) {
			baseDao.delete("delete from " + UserRole.class.getName() + " ur where ur.user.id = '" + user.getId() + "'");
		}
		user = baseDao.saveOrUpdate(user);
		if (userInfo.getRoles() != null) {
			Set<UserRole> userRoles = new HashSet<UserRole>();
			UserRole userRole = null;
			for (RoleDTO roleDTO : userInfo.getRoles()) {
				userRole = new UserRole();
				userRole.setRole(RoleDTO.reverse(roleDTO,baseDao));
				userRole.setUser(user);
				userRole = baseDao.saveOrUpdate(userRole);
				userRoles.add(userRole);
			}
			user.setUserRoles(userRoles);
		}
		return user;
	}

}
