package com.chavin.service.impl;

import org.springframework.stereotype.Service;

import com.chavin.dao.UserMapper;
import com.chavin.po.User;
import com.chavin.service.UserService;
import com.core.exception.CustomException;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService{

	/**
	 * 登录
	 */
	@Override
	public User login(String userName, String password) throws CustomException{
		User user = null;
		try {
			User entity = new User();
			entity.setLoginName(userName);
			user = mapper.findByEntity(entity);
		} catch (Exception e) {
			throw new CustomException("DB_QUERY_EXCEPTION");
		}
		if(user == null){
			throw new CustomException("NO_USER");
		}else{
			if(!user.getPassword().equals(password)){
				throw new CustomException("PWD_ERROR");
			}
		}
		return user;
	}

}
