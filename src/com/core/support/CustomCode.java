package com.core.support;

public enum CustomCode {
	
	loginSuccess(1000,"登录成功"),
	noUser(1001,"用户名不存在"),
	userDisabled(1002,"用户已被停用，请联系管理员"),
	pwdError(1020,"密码错误"),
	validCodeError(1030,"验证码错误"),
	
	systemException(1101, "系统异常"),
	dbException(1102, "数据库异常");

	private int code;
	private String msg;

	CustomCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static void main(String[] args) {
		
	}
}
