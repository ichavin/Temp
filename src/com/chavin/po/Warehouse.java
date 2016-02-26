package com.chavin.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 仓库表
 * 
 * @author chavin
 */
public class Warehouse extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private Integer defectiveAmount;

	private Integer qualityAmount;

	private Integer totleAmount;

	private Date produceTime;

	private Integer type;

	private Unit unit;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getDefectiveAmount() {
		return defectiveAmount;
	}

	public void setDefectiveAmount(Integer defectiveAmount) {
		this.defectiveAmount = defectiveAmount;
	}

	public Integer getQualityAmount() {
		return qualityAmount;
	}

	public void setQualityAmount(Integer qualityAmount) {
		this.qualityAmount = qualityAmount;
	}

	public Integer getTotleAmount() {
		return totleAmount;
	}

	public void setTotleAmount(Integer totleAmount) {
		this.totleAmount = totleAmount;
	}

	public Date getProduceTime() {
		return produceTime;
	}

	public void setProduceTime(Date produceTime) {
		this.produceTime = produceTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}