package com.chavin.po;

import java.io.Serializable;

/**
 * 单位表
 * 
 * @author chavin
 */
public class Unit extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private Integer deleted;

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

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Unit [id=" + id + ", name=" + name + ", deleted=" + deleted
				+ "]";
	}

}