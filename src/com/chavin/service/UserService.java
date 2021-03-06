package com.chavin.service;

import com.chavin.po.TransferObj;
import com.chavin.po.User;
import com.core.exception.CustomException;

public interface UserService extends BaseService<User>{

	/**
	 * 登录
	 * @param userName
	 * @param password
	 * @return CustomCode<br/>
	 * @exception 
	 */
	public TransferObj login(String userName, String password) throws CustomException;
	
	
}
