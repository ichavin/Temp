package com.chavin.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易记录
 * 
 * @author chavin
 */
public class Transaction extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private Integer unitId;

	private Integer amount;

	private Double univalence;

	private Double totalMoney;

	private Integer buyOrSell; // 0为买入 1为卖出

	private String userName;

	private String address;

	private Date time;

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

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Double getUnivalence() {
		return univalence;
	}

	public void setUnivalence(Double univalence) {
		this.univalence = univalence;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getBuyOrSell() {
		return buyOrSell;
	}

	public void setBuyOrSell(Integer buyOrSell) {
		this.buyOrSell = buyOrSell;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", name=" + name + ", unitId="
				+ unitId + ", amount=" + amount + ", univalence=" + univalence
				+ ", totalMoney=" + totalMoney + ", buyOrSell=" + buyOrSell
				+ ", userName=" + userName + ", address=" + address + ", time="
				+ time + ", deleted=" + deleted + "]";
	}

}