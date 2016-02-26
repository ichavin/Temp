package com.chavin.vo;

import java.io.Serializable;
import java.util.Date;

import com.chavin.po.BaseBean;

public class OrderVO extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer customId;
	private String customName;
	private Integer commodityId;
	private String commodityName;
	private String remark;
	private Integer isTradeSuccess;
	private Integer deleted;
	private Double univalence;
	private Integer singleGroupNum;
	private Integer groupNum;
	private Integer amount;
	private String specimenImg;
	private Double totlePrice;
	private Date createTime;
	private Date tradeTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomId() {
		return customId;
	}

	public void setCustomId(Integer customId) {
		this.customId = customId;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsTradeSuccess() {
		return isTradeSuccess;
	}

	public void setIsTradeSuccess(Integer isTradeSuccess) {
		this.isTradeSuccess = isTradeSuccess;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Double getUnivalence() {
		return univalence;
	}

	public void setUnivalence(Double univalence) {
		this.univalence = univalence;
	}

	public Integer getSingleGroupNum() {
		return singleGroupNum;
	}

	public void setSingleGroupNum(Integer singleGroupNum) {
		this.singleGroupNum = singleGroupNum;
	}

	public Integer getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(Integer groupNum) {
		this.groupNum = groupNum;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getSpecimenImg() {
		return specimenImg;
	}

	public void setSpecimenImg(String specimenImg) {
		this.specimenImg = specimenImg;
	}

	public Double getTotlePrice() {
		return totlePrice;
	}

	public void setTotlePrice(Double totlePrice) {
		this.totlePrice = totlePrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

}
