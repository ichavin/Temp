package com.chavin.po;

import java.io.Serializable;

/**
 * 价格波动表
 * 
 * @author chavin
 */
public class PriceFluctuation extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer meterialId;

	private Double price;

	private Integer deleted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMeterialId() {
		return meterialId;
	}

	public void setMeterialId(Integer meterialId) {
		this.meterialId = meterialId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "PriceFluctuation [id=" + id + ", meterialId=" + meterialId
				+ ", price=" + price + ", deleted=" + deleted + "]";
	}
}