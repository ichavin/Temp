package com.chavin.po;

import java.io.Serializable;

/**
 * 方案
 * 
 * @author chavin
 */
public class SchemeMeterial extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Scheme scheme;
	private Meterial meterial;
	private Unit unit;
	private double amount;

	public SchemeMeterial() {
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Scheme getScheme() {
		return scheme;
	}

	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	public Meterial getMeterial() {
		return meterial;
	}

	public void setMeterial(Meterial meterial) {
		this.meterial = meterial;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "SchemeMeterial [id=" + id + ", scheme=" + scheme
				+ ", meterial=" + meterial + ", unit=" + unit + ", amount="
				+ amount + "]";
	}
	
	

}