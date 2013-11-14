/**
 * 
 */
package com.xbrother.common.entity.appstore;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.xbrother.common.constants.DatabaseConst;
import com.xbrother.common.entity.BaseEntity;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013年9月23日
 * @version 1.0
 */
@Entity
@Table(name = "ajx_app_space", catalog = DatabaseConst.CATALOG)
public class AppSpace extends BaseEntity {

	String code;
	String name;
	String description;
	transient Set<AppVersion> apps;

	@Column(name = "code", length = 36, unique = true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", length = DatabaseConst.NAME_LEGNTH)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = DatabaseConst.CONTENT_LENGTH)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "appSpace")
	public Set<AppVersion> getApps() {
		return apps;
	}

	public void setApps(Set<AppVersion> apps) {
		this.apps = apps;
	}

}
