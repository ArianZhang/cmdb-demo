package com.xbrother.cmdb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.xbrother.common.entity.BaseEntity;

@Table(name = "cms_ci_spot_segement")
@Entity
public class ConfigurationItemSpotSegment extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Integer priorNo;// " : 1,
	private String arithmeticSign;// " : ">=",
	private String thresholdValue;// " : "280",
	private String incidentDesc;// " : "UPS指标{0}，达到{4}V，超过{1}，事件等级：{3}，请速处理！
	private ConfigurationItemSpot spot;
	@Column(name="prior_no")
	public Integer getPriorNo() {
		return priorNo;
	}

	public void setPriorNo(Integer priorNo) {
		this.priorNo = priorNo;
	}

	@Column(name="arithmetic_sign")
	public String getArithmeticSign() {
		return arithmeticSign;
	}

	public void setArithmeticSign(String arithmeticSign) {
		this.arithmeticSign = arithmeticSign;
	}
	
	@Column(name="threshold_value")
	public String getThresholdValue() {
		return thresholdValue;
	}

	public void setThresholdValue(String thresholdValue) {
		this.thresholdValue = thresholdValue;
	}

	@Column(name="incident_desc")
	public String getIncidentDesc() {
		return incidentDesc;
	}

	public void setIncidentDesc(String incidentDesc) {
		this.incidentDesc = incidentDesc;
	}
	
	@ManyToOne
	@JoinColumn(name="spot_uid")
	public ConfigurationItemSpot getSpot() {
		return spot;
	}

	public void setSpot(ConfigurationItemSpot spot) {
		this.spot = spot;
	}

}
