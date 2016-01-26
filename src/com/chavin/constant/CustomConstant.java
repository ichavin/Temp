package com.chavin.constant;

import java.text.SimpleDateFormat;

public interface CustomConstant {

	/**
	 * session中保存的用户信息
	 */
	public static final String USER = "USER";
	
	/**
	 * 加盐
	 */
	public static final String COOKIESTR = "TEMP_COOKIE_STR";
	
	/**
	 * cookie的名称
	 */
	public static final String COOKIE_NAME = "COOKIE_NAME";
	
	/**
	 * 简短日期
	 */
	public static final SimpleDateFormat datesdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 全日期
	 */
	public static final SimpleDateFormat timesdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
}
