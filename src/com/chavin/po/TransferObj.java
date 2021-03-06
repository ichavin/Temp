package com.chavin.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据传递的传输对象
 * 
 * TransferObj 时间：2015年10月29日-上午10:53:19
 * 
 * @version 1.0.0
 *
 */
public class TransferObj implements Serializable {

	private static final long serialVersionUID = 1L;
	private String code;
	private Boolean success;
	private String detailInfo;
	private Object object;

	public TransferObj() {
		super();
	}

	public TransferObj(Boolean success) {
		super();
		this.success = success;
	}

	public TransferObj(Boolean success, String detailInfo) {
		super();
		this.success = success;
		this.detailInfo = detailInfo;
	}

	public TransferObj(String code, String detailInfo, Object object) {
		super();
		this.code = code;
		this.detailInfo = detailInfo;
		this.object = object;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "TransferObj [code=" + code + ", success=" + success
				+ ", detailInfo=" + detailInfo + ", object=" + object + "]";
	}

}
