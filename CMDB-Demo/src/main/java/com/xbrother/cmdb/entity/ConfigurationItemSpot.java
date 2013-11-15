package com.xbrother.cmdb.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.xbrother.common.entity.BaseEntity;

@Table(name = "cms_ci_spot")
@Entity
public class ConfigurationItemSpot extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Integer id;// " : 1,
	private String name;// " : "交流输入相电压A",
	private String normalValue;// " : "",
	private String exceptionValue;// " : "",
	private String normalMaxValue;// " : "215",
	private String normalMinValue;// " : "255",
	private Integer valueType;// " : 2,
	private Set<ConfigurationItemSpotSegment> thresholdSegments;/*
																 * " : [{
																 * "priorNo" :
																 * 1,
																 * "arithmeticSign"
																 * : ">=",
																 * "thresholdValue"
																 * : "280",
																 * "incidentDesc"
																 * :
																 * "UPS指标{0}，达到{4}V，超过{1}，事件等级：{3}，请速处理！"
																 * }]
																 */
	private transient ConfigurationItem ci;

	@Column(unique = true)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "normal_value")
	public String getNormalValue() {
		return normalValue;
	}

	public void setNormalValue(String normalValue) {
		this.normalValue = normalValue;
	}

	@Column(name = "exception_value")
	public String getExceptionValue() {
		return exceptionValue;
	}

	public void setExceptionValue(String exceptionValue) {
		this.exceptionValue = exceptionValue;
	}

	@Column(name = "normal_max_value")
	public String getNormalMaxValue() {
		return normalMaxValue;
	}

	public void setNormalMaxValue(String normalMaxValue) {
		this.normalMaxValue = normalMaxValue;
	}

	@Column(name = "normal_min_value")
	public String getNormalMinValue() {
		return normalMinValue;
	}

	public void setNormalMinValue(String normalMinValue) {
		this.normalMinValue = normalMinValue;
	}

	@Column(name = "value_type")
	public Integer getValueType() {
		return valueType;
	}

	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}

	@OneToMany(mappedBy = "spot", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public Set<ConfigurationItemSpotSegment> getThresholdSegments() {
		return thresholdSegments;
	}

	public void setThresholdSegments(Set<ConfigurationItemSpotSegment> thresholdSegments) {
		this.thresholdSegments = thresholdSegments;
	}

	@ManyToOne
	@JoinColumn(name = "ci_uid")
	public ConfigurationItem getCi() {
		return ci;
	}

	public void setCi(ConfigurationItem ci) {
		this.ci = ci;
	}
}
