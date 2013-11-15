package com.xbrother.cmdb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbrother.common.entity.UUIDEntity;

@Entity
@Table(name = "cms_last_update_mark")
public class LastUpdateMark extends UUIDEntity {

	private static final long serialVersionUID = 1L;
	private String mark;
	private Long lastUpdate;

	@Column(name = "mark", unique = true)
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	@Column(name = "last_update")
	public Long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
