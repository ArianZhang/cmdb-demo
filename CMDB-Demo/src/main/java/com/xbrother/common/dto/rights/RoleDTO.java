package com.xbrother.common.dto.rights;

import java.util.ArrayList;
import java.util.List;

import com.xbrother.common.dao.IBaseDao;
import com.xbrother.common.dto.SuperDTO;
import com.xbrother.common.entity.rights.Role;

public class RoleDTO extends SuperDTO {

	private String roleName;
	private String description;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static RoleDTO convert(Role role) {
		RoleDTO dto = new RoleDTO();
		dto.setId(role.getId());
		dto.setRoleName(role.getRoleName());
		dto.setDescription(role.getDescription());
		return dto;
	}

	public static List<RoleDTO> converts(ArrayList<Role> roles) {
		List<RoleDTO> dtos = new ArrayList<RoleDTO>();
		for (Role role : roles) {
			dtos.add(convert(role));
		}
		return dtos;
	}

	public static Role reverse(RoleDTO dto, IBaseDao baseDao) {
		Role role = new Role();
		role.setId(dto.getId());
		role.setRoleName(dto.getRoleName());
		role.setDescription(dto.getDescription());
		role = baseDao.saveOrUpdate(role);
		return role;
	}

}
