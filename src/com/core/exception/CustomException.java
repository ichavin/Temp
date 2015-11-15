package com.core.exception;

/**
 * 自定义异常
 * 
 * CustomException
 * 时间：2015年10月29日-上午9:22:21 
 * @version 1.0.0
 *
 */
public class CustomException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public CustomException(){
		
	}
	
	public CustomException(String msg){
		super(msg);
	}
	
	public CustomException(Throwable cause){
		super(cause);
	}
	
	public CustomException(String message, Throwable cause){
		super(message, cause);
	}

}
