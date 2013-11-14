/**
 * 
 */
package com.xbrother.common.entity.appstore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ajx_app_version", catalog = DatabaseConst.CATALOG)
public class AppVersion extends BaseEntity {

	String versionNo;
	String name;
	String news;
	String downloadUrl;
	String updateLevel;
	AppSpace appSpace;

	@Column(name = "version_no", length = 36)
	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	@Column(name = "name", length = DatabaseConst.NAME_LEGNTH)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "news", length = DatabaseConst.CONTENT_LENGTH)
	public String getNews() {
		return news;
	}

	public void setNews(String news) {
		this.news = news;
	}

	@Column(name = "download_url")
	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	@Column(name = "update_level", length = DatabaseConst.TYPE_CODE_LENGTH)
	public String getUpdateLevel() {
		return updateLevel;
	}

	public void setUpdateLevel(String updateLevel) {
		this.updateLevel = updateLevel;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "app_space_code", nullable = true,referencedColumnName="code")
	public AppSpace getAppSpace() {
		return appSpace;
	}

	public void setAppSpace(AppSpace appSpace) {
		this.appSpace = appSpace;
	}

}
