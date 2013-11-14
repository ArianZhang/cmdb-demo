package com.xbrother.common.dto.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xbrother.common.dto.SuperDTO;
import com.xbrother.common.entity.rights.Menu;
import com.xbrother.common.utils.JsonUtils;

/**
 * 用于WebUI显示用的menu tree
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-23
 * @version 1.0
 */
public class MenuDTO extends SuperDTO{
	private final static Logger LOGGER = LoggerFactory.getLogger(MenuDTO.class);
	String text;
	String url;
	List<MenuDTO> children;


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<MenuDTO> getChildren() {
		if (children == null) {
			children = new ArrayList<MenuDTO>();
		}
		return children;
	}

	public void setChildren(List<MenuDTO> children) {
		this.children = children;
	}

	public static MenuDTO convert(Menu menu) {
		MenuDTO dto = new MenuDTO();
		dto.setId(menu.getId());
		dto.setText(menu.getName());
		dto.setUrl(menu.getUrl());
		return dto;
	}

	/**
	 * notice: call this method must in an opend session.
	 * 
	 * @date 2013-7-28
	 * @param menus
	 * @param parentId
	 * @return
	 */
	public static List<MenuDTO> convert(List<Menu> menus, Integer parentId) {
		List<MenuDTO> dtos = new ArrayList<MenuDTO>();
		MenuDTO dto;
		for (Menu menu : menus) {
			if ((parentId == null && menu.getParent() == null)
					|| (parentId != null && menu.getParent() != null && parentId.equals(menu.getParent().getId()))) {
				dto = convert(menu);
				if (menu.getChildren() == null || menu.getChildren().size() == 0) {
					dtos.add(dto);
				} else {
					List<MenuDTO> children = convert(menus, menu.getId());
					if (children.size() > 0) {
						dto.setChildren(children);
						dtos.add(dto);
					}
				}
			}
		}
		return dtos;
	}
}
