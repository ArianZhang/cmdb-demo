/**
 * 
 */
package com.xbrother.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@MappedSuperclass
public class BaseEntity extends UUIDEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected String rowStatus;
	protected String creator;
	protected Date createTime;
	protected String updator;
	protected Date updateTime;

	@Column(name = "row_status",columnDefinition = "VARCHAR(10) NOT NULL DEFAULT '1'")
	public String getRowStatus() {
		return this.rowStatus;
	}

	public void setRowStatus(String status) {
		this.rowStatus = status;
	}

	@Column(name = "creator", columnDefinition = "VARCHAR(32) NOT NULL DEFAULT 0")
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String createby) {
		this.creator = createby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time",columnDefinition = "TIMESTAMP NOT NULL DEFAULT '2013-01-01 00:00:00'")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createtime) {
		this.createTime = createtime;
	}

	@Column(name = "updator", columnDefinition = "VARCHAR(32) NOT NULL DEFAULT 0")
	public String getUpdator() {
		return this.updator;
	}

	public void setUpdator(String updateby) {
		this.updator = updateby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updatetime) {
		this.updateTime = updatetime;
	}

}
