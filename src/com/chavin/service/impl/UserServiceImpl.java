package com.chavin.service.impl;

import org.springframework.stereotype.Service;

import com.chavin.dao.UserMapper;
import com.chavin.po.TransferObj;
import com.chavin.po.User;
import com.chavin.service.UserService;
import com.core.exception.CustomException;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService{

	/**
	 * 登录
	 */
	@Override
	public TransferObj login(String userName, String password) throws CustomException{
		User user = null;
		TransferObj transferObj = new TransferObj();
		String code = "";
		try {
			User entity = new User();
			entity.setLoginName(userName);
			user = mapper.findByEntity(entity);
		} catch (Exception e) {
			throw new CustomException("DB_QUERY_EXCEPTION");
		}
		if(user == null){
			code = "NO_USER";
		}else{
			if(user != null && user.getPassword().equals(password)){
				code = "LOGIN_SUCCESS";
				transferObj.setObject(user);
			}else{
				code = "PWD_ERROR";
			}
		}
		transferObj.setCode(code);
		return transferObj;
	}

}
