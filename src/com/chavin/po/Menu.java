package com.chavin.po;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 * 
 * @author chavin
 */
public class Menu extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String iconClass;

	private String menuCode;

	private String name;

	private String parentMenuCode;

	private String parentMenuName;

	private String url;

	private Integer deleted;

	private List<Menu> subList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass == null ? null : iconClass.trim();
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode == null ? null : menuCode.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getParentMenuCode() {
		return parentMenuCode;
	}

	public void setParentMenuCode(String parentMenuCode) {
		this.parentMenuCode = parentMenuCode == null ? null : parentMenuCode
				.trim();
	}

	public String getParentMenuName() {
		return parentMenuName;
	}

	public void setParentMenuName(String parentMenuName) {
		this.parentMenuName = parentMenuName == null ? null : parentMenuName
				.trim();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public List<Menu> getSubList() {
		return subList;
	}

	public void setSubList(List<Menu> subList) {
		this.subList = subList;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", iconClass=" + iconClass + ", menuCode="
				+ menuCode + ", name=" + name + ", parentMenuCode="
				+ parentMenuCode + ", parentMenuName=" + parentMenuName
				+ ", url=" + url + ", deleted=" + deleted + ", subList="
				+ subList + "]";
	}

}