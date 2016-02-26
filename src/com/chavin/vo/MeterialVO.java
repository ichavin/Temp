package com.chavin.vo;

import java.io.Serializable;

import com.chavin.po.BaseBean;

public class MeterialVO extends BaseBean implements Serializable {

	private Integer id;
	private String name;
	private Integer deleted;
	private Integer unit_id;
	private String unit_name;
	private String unit_deleted;
	private Integer custom_id;
	private String custom_name;

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
		this.name = name;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Integer getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(Integer unit_id) {
		this.unit_id = unit_id;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	public String getUnit_deleted() {
		return unit_deleted;
	}

	public void setUnit_deleted(String unit_deleted) {
		this.unit_deleted = unit_deleted;
	}

	public Integer getCustom_id() {
		return custom_id;
	}

	public void setCustom_id(Integer custom_id) {
		this.custom_id = custom_id;
	}

	public String getCustom_name() {
		return custom_name;
	}

	public void setCustom_name(String custom_name) {
		this.custom_name = custom_name;
	}

}
