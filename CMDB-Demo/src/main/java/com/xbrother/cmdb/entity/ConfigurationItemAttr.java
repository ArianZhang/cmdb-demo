package com.xbrother.cmdb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.xbrother.common.entity.BaseEntity;

@Table(name = "cms_ci_attr")
@Entity
public class ConfigurationItemAttr extends BaseEntity {

	private Integer attrType;// " : "1",
	private Integer sortNo;// " : 1
	private String name;// " : "powerRate",
	private String displayName;// " : "额定功率",
	private String attrValue;// " : "2KVA",
	private Integer valueType;// " : 1,
	private Integer requried;// " : 0
	private ConfigurationItem ci;

	@Column(name = "attr_type")
	public Integer getAttrType() {
		return attrType;
	}

	public void setAttrType(Integer attrType) {
		this.attrType = attrType;
	}

	@Column(name = "sort_no")
	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "display_name")
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Column(name = "attr_value")
	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	@Column(name = "value_type")
	public Integer getValueType() {
		return valueType;
	}

	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}

	@Column(name = "requried")
	public Integer getRequried() {
		return requried;
	}

	public void setRequried(Integer requried) {
		this.requried = requried;
	}
	@ManyToOne
	@JoinColumn(name="ci_uid")
	public ConfigurationItem getCi() {
		return ci;
	}

	public void setCi(ConfigurationItem ci) {
		this.ci = ci;
	}

}
