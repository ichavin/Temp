package com.chavin.po;

import java.io.Serializable;

/**
 * 产品
 * 
 * @author chavin
 */
public class Commodity extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private Scheme scheme;

	private String imageUrl;

	private Integer deleted;

	private String numberId;

	private Double weight;

	private String size;

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

	public Scheme getScheme() {
		return scheme;
	}

	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl == null ? null : imageUrl.trim();
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getNumberId() {
		return numberId;
	}

	public void setNumberId(String numberId) {
		this.numberId = numberId;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

}