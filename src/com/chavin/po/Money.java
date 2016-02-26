package com.chavin.po;

import java.io.Serializable;

/**
 * 收支情况表
 * 
 * @author chavin
 */
public class Money extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private Double consumeIncome;

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

	public Double getConsumeIncome() {
		return consumeIncome;
	}

	public void setConsumeIncome(Double consumeIncome) {
		this.consumeIncome = consumeIncome;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Money [id=" + id + ", name=" + name + ", consumeIncome="
				+ consumeIncome + ", deleted=" + deleted + "]";
	}

}