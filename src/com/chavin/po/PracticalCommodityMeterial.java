package com.chavin.po;

import java.io.Serializable;

/**
 * 商品材料实际使用情况表
 * 
 * @author chavin
 */
public class PracticalCommodityMeterial extends BaseBean implements
		Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String produceId;

	private Integer commodityId;

	private Integer meterialId;

	private Integer amount;

	private Integer deleted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProduceId() {
		return produceId;
	}

	public void setProduceId(String produceId) {
		this.produceId = produceId == null ? null : produceId.trim();
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public Integer getMeterialId() {
		return meterialId;
	}

	public void setMeterialId(Integer meterialId) {
		this.meterialId = meterialId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "PracticalCommodityMeterial [id=" + id + ", produceId="
				+ produceId + ", commodityId=" + commodityId + ", meterialId="
				+ meterialId + ", amount=" + amount + ", deleted=" + deleted
				+ "]";
	}
}