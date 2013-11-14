/**
 * 
 */
package com.xbrother.common.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-27
 * @version 1.0
 */
@MappedSuperclass
public abstract class UUIDEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	protected String uid;

	public UUIDEntity() {
	}

	public UUIDEntity(String uid) {
		super();
		this.uid = uid;
	}

	@Id
	@Column(name = "uid", unique = true, nullable = false)
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
