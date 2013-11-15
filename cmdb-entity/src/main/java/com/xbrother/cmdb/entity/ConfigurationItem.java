package com.xbrother.cmdb.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.xbrother.common.entity.BaseEntity;

@Table(name = "cms_ci")
@Entity
public class ConfigurationItem extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String templateUid;// : 1,
	private String computerRoomUid;// " : "7b4a187524874288806fe60fdb8ddb7a",
	private String locationUid;// : "946c8f390bcc4778bd578cd08d7112aa11",
	private String typeId;// " : "010101",
	private String name;// " : "艾默生 UPS 200",
	private String manufacturerUid;// " : 1,
	private String serviceProviderUid;// " : 1,
	private String modelCode;// " : "UH3R_0200",
	private String purpose;// " : "持续供电，稳压作用",
	private String seriesNo;// " : "UH3R020001110111"
	private Set<ConfigurationItemAttr> attributes;/*
												 * : [{ "attrType" : "1",
												 * "sortNo" : 1 "name" :
												 * "powerRate", "displayName" :
												 * "额定功率", "attrValue" : "2KVA",
												 * "valueType" : 1, "requried" :
												 * 0 }],
												 */
	private Set<ConfigurationItemSpot> monitorSpots;/*
													 * " : [{ "id" : 1, "name" :
													 * "交流输入相电压A", "normalValue"
													 * : "", "exceptionValue" :
													 * "", "normalMaxValue" :
													 * "215", "normalMinValue" :
													 * "255", "valueType" : 2,
													 * "thresholdSegments" : [{
													 * "priorNo" : 1,
													 * "arithmeticSign" : ">=",
													 * "thresholdValue" : "280",
													 * "incidentDesc" :
													 * "UPS指标{0}，达到{4}V，超过{1}，事件等级：{3}，请速处理！"
													 * }] }]
													 */

	@Column(name = "template_uid", length = 32)
	public String getTemplateUid() {
		return templateUid;
	}

	public void setTemplateUid(String templateUid) {
		this.templateUid = templateUid;
	}

	@Column(name = "computer_room_uid", length = 32)
	public String getComputerRoomUid() {
		return computerRoomUid;
	}

	public void setComputerRoomUid(String computerRoomUid) {
		this.computerRoomUid = computerRoomUid;
	}

	@Column(name = "location_uid", length = 32)
	public String getLocationUid() {
		return locationUid;
	}

	public void setLocationUid(String locationUid) {
		this.locationUid = locationUid;
	}

	@Column(name = "type_id")
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "manufacturer_uid")
	public String getManufacturerUid() {
		return manufacturerUid;
	}

	public void setManufacturerUid(String manufacturerUid) {
		this.manufacturerUid = manufacturerUid;
	}

	@Column(name = "service_provider_uid")
	public String getServiceProviderUid() {
		return serviceProviderUid;
	}

	public void setServiceProviderUid(String serviceProviderUid) {
		this.serviceProviderUid = serviceProviderUid;
	}

	@Column(name = "model_code")
	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	@Column(name = "purpose")
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	@Column(name = "series_no")
	public String getSeriesNo() {
		return seriesNo;
	}

	public void setSeriesNo(String seriesNo) {
		this.seriesNo = seriesNo;
	}

	@OneToMany(mappedBy = "ci", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public Set<ConfigurationItemAttr> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<ConfigurationItemAttr> attributes) {
		this.attributes = attributes;
	}

	@OneToMany(mappedBy = "ci", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public Set<ConfigurationItemSpot> getMonitorSpots() {
		return monitorSpots;
	}

	public void setMonitorSpots(Set<ConfigurationItemSpot> monitorSpots) {
		this.monitorSpots = monitorSpots;
	}
	
	public static void main(String[] args) {
		System.out.println("7b4a187524874288806fe60fdb8ddb7a".length());
	}

}
