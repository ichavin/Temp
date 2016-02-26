package com.chavin.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单表
 * 
 * @author chavin
 */
public class Order extends BaseBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Commodity commodity;
	private Custom custom;
	private Double univalence;
	private Integer singleGroupNum;
	private Integer groupNum;
	private Integer amount;
	private String remark;
	private String specimenImg;
	private Double totlePrice;
	private Date createTime;
	private Date tradeTime;
	private Integer deleted;
	private Integer isTradeSuccess;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	public Custom getCustom() {
		return custom;
	}

	public void setCustom(Custom custom) {
		this.custom = custom;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Integer getIsTradeSuccess() {
		return isTradeSuccess;
	}

	public void setIsTradeSuccess(Integer isTradeSuccess) {
		this.isTradeSuccess = isTradeSuccess;
	}

}