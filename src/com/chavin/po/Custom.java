package com.chavin.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 供货商及客户
 * 
 * @author chavin
 */
public class Custom extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String phone;
	private String company;
	private String address;
	private String telNumb;
	private String mark;
	private Integer isgys; // 1为 供货商 0为客户
	private Integer iscompany; // 1为公司 0为个人
	private Date createTime;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company == null ? null : company.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getTelNumb() {
		return telNumb;
	}

	public void setTelNumb(String telNumb) {
		this.telNumb = telNumb == null ? null : telNumb.trim();
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark == null ? null : mark.trim();
	}

	public Integer getIsgys() {
		return isgys;
	}

	public void setIsgys(Integer isgys) {
		this.isgys = isgys;
	}

	public Integer getIscompany() {
		return iscompany;
	}

	public void setIscompany(Integer iscompany) {
		this.iscompany = iscompany;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Custom [id=" + id + ", name=" + name + ", phone=" + phone
				+ ", company=" + company + ", address=" + address
				+ ", telNumb=" + telNumb + ", mark=" + mark + ", isgys="
				+ isgys + ", iscompany=" + iscompany + ", createTime="
				+ createTime + ", deleted=" + deleted + "]";
	}

}