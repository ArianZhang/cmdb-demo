package com.xbrother.common.entity.rights;

// Generated 2013-7-22 15:04:22 by Hibernate Tools 4.0.0

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.xbrother.common.constants.DatabaseConst;
import com.xbrother.common.entity.BaseEntity;

/**
 * Action generated by hbm2java
 */
@Entity
@Table(name = "ajx_action", catalog = DatabaseConst.CATALOG)
public class Action extends BaseEntity{

	private Action parent;
	private Menu menu;
	private Permission permission;
	private String name;
	private String actionCode;
	private String url;
	private String requestMethod; // GET POST PUT DELETE
	private int leaf;
	private Set<Action> children;


	@ManyToOne
	@JoinColumn(name = "menu_id", nullable = false)
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@Column(name = "name", nullable = false, length = 128)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "action_code", length = 64)
	public String getActionCode() {
		return this.actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	@Column(name = "url", length = 128)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "request_method", length = 10)
	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	@Column(name = "leaf", nullable = false)
	public int getLeaf() {
		return leaf;
	}

	public void setLeaf(int leaf) {
		this.leaf = leaf;
	}

	@ManyToOne
	@JoinColumn(name = "permission_id")
	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	@ManyToOne
	@JoinColumn(name = "parent_id")
	public Action getParent() {
		return parent;
	}

	public void setParent(Action parent) {
		this.parent = parent;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
	public Set<Action> getChildren() {
		return children;
	}

	public void setChildren(Set<Action> children) {
		this.children = children;
	}

}
