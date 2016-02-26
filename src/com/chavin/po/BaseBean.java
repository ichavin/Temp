package com.chavin.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 基础JavaBean
 * 
 * @author chavin
 */
public class BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String key;
	private String oper;
	private Integer page = 1;
	private Integer rows = 10;
	private String sort;
	private String order;
	private Date cstartTime;
	private Date cendTime;
	private Date tstartTime;
	private Date tendTime;
	private String tradeTimestartTime;
	private String tradeTimeendTime;
	private String createTimestartTime;
	private String createTimeendTime;
	private Integer startNum;
	private Integer endNum;
	private List<Integer> ids;
	private List<String> keys;
	private List<Integer> foreignIds;
	private List<Object> objs;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public Integer getPage() {
		return page == null ? 1 : page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getStartNum() {
		return startNum == null ? 0 : startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public Integer getEndNum() {
		return endNum;
	}

	public void setEndNum(Integer endNum) {
		this.endNum = endNum;
	}

	public Integer getRows() {
		return rows == null ? 10 : rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

	public List<Integer> getForeignIds() {
		return foreignIds;
	}

	public void setForeignIds(List<Integer> foreignIds) {
		this.foreignIds = foreignIds;
	}

	public List<Object> getObjs() {
		return objs;
	}

	public void setObjs(List<Object> objs) {
		this.objs = objs;
	}

	public Date getCstartTime() {
		return cstartTime;
	}

	public void setCstartTime(Date cstartTime) {
		this.cstartTime = cstartTime;
	}

	public Date getCendTime() {
		return cendTime;
	}

	public void setCendTime(Date cendTime) {
		this.cendTime = cendTime;
	}

	public Date getTstartTime() {
		return tstartTime;
	}

	public void setTstartTime(Date tstartTime) {
		this.tstartTime = tstartTime;
	}

	public Date getTendTime() {
		return tendTime;
	}

	public void setTendTime(Date tendTime) {
		this.tendTime = tendTime;
	}

	public String getTradeTimestartTime() {
		return tradeTimestartTime;
	}

	public void setTradeTimestartTime(String tradeTimestartTime) {
		this.tradeTimestartTime = tradeTimestartTime;
	}

	public String getTradeTimeendTime() {
		return tradeTimeendTime;
	}

	public void setTradeTimeendTime(String tradeTimeendTime) {
		this.tradeTimeendTime = tradeTimeendTime;
	}

	public String getCreateTimestartTime() {
		return createTimestartTime;
	}

	public void setCreateTimestartTime(String createTimestartTime) {
		this.createTimestartTime = createTimestartTime;
	}

	public String getCreateTimeendTime() {
		return createTimeendTime;
	}

	public void setCreateTimeendTime(String createTimeendTime) {
		this.createTimeendTime = createTimeendTime;
	}

}
