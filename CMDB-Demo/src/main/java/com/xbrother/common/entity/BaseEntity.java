/**
 * 
 */
package com.xbrother.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author qhcui
 * 
 */
@MappedSuperclass
public class BaseEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected String status;
	protected Integer createby;
	protected Date createtime;
	protected Integer updateby;
	protected Date updatetime;

	@Column(name = "status",columnDefinition = "VARCHAR(10) NOT NULL DEFAULT '1'")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "createby", columnDefinition = "INTEGER NOT NULL DEFAULT 0")
	public Integer getCreateby() {
		return this.createby;
	}

	public void setCreateby(Integer createby) {
		this.createby = createby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime",columnDefinition = "TIMESTAMP NOT NULL DEFAULT '2013-01-01 00:00:00'")
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "updateby", columnDefinition = "INTEGER NOT NULL DEFAULT 0")
	public Integer getUpdateby() {
		return this.updateby;
	}

	public void setUpdateby(Integer updateby) {
		this.updateby = updateby;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatetime", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

}
