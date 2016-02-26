package com.chavin.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 出入仓
 * 
 * @author chavin
 */
public class ProduceCellWarehouse extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String commodityName;

	private Integer isProduce;

	private Integer amount;

	private Date time;

	private String deleted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName == null ? null : commodityName
				.trim();
	}

	public Integer getIsProduce() {
		return isProduce;
	}

	public void setIsProduce(Integer isProduce) {
		this.isProduce = isProduce;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted == null ? null : deleted.trim();
	}

	@Override
	public String toString() {
		return "ProduceCellWarehouse [id=" + id + ", commodityName="
				+ commodityName + ", isProduce=" + isProduce + ", amount="
				+ amount + ", time=" + time + ", deleted=" + deleted + "]";
	}
}