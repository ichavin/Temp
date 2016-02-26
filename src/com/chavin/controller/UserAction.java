package com.chavin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chavin.constant.CustomConstant;
import com.chavin.po.User;
import com.chavin.service.UserService;


@RequestMapping(value="/user")
@Controller
public class UserAction implements CustomConstant{
	
	@Resource
	private UserService userService;

	@RequestMapping(value="/userInfo")
	public String userInfo(HttpServletRequest request, HttpServletResponse response){
		User user = (User) request.getSession().getAttribute(USER);
		request.setAttribute("user", user);
		return "/user/userinfo";
	}
	
	/**
	 * 修改个人资料
	 */
	@RequestMapping(value="/updateUserInfo")
	@ResponseBody
	public String updateUserInfo(HttpServletRequest request, HttpServletResponse response, User user){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idParamter",user.getId());
		try {
			userService.updateByEntity(map, user);
			User entity = new User();
			entity.setId(user.getId());
			User newUser = userService.findByEntity(entity);
			if(newUser != null ){
				request.getSession().removeAttribute(USER);
				request.getSession().setAttribute(USER, newUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
}
