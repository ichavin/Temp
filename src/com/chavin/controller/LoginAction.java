package com.chavin.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chavin.constant.CustomConstant;
import com.chavin.po.TransferObj;
import com.chavin.po.User;
import com.chavin.service.UserService;
import com.core.exception.CustomException;
import com.core.listener.Global;
import com.core.util.CreateValidateCodeUtil;
import com.core.util.EncryptionDecryption;

@Controller
@RequestMapping("/login/")
public class LoginAction implements CustomConstant{

	private static final Logger logger = Logger.getLogger(LoginAction.class);
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@RequestMapping(value = "/login")
	@ResponseBody
	public TransferObj login(HttpServletRequest request, HttpServletResponse response, String userName, String password, String validCode, boolean autoLogin){
		TransferObj transferObj = null;
		String code;
		if(StringUtils.isBlank(userName) || StringUtils.isBlank(password)){
			return new TransferObj("IS_NULL", Global.CUSTOM_CODE_MSG.get("IS_NULL"), null);
		}
		HttpSession session = request.getSession();
		Object object = session.getAttribute(userName);
		int count = 0;
		if(object != null){
			count = (int) object;
		}else{
			session.setAttribute(userName, 0);
		}
		if(count >= 3 && session.getAttribute("validCode") != null){
			String sessionValidCode = (String) session.getAttribute("validCode");
			if(!sessionValidCode.toLowerCase().equals(validCode.toLowerCase())){
				code = "VALID_CODE_ERROR";
				return new TransferObj(code, Global.CUSTOM_CODE_MSG.get(code), count);
			}
		}
		User user;
		try {
			user = userService.login(userName, EncryptionDecryption.getInstance().encrypt(password));
			//登录成功
			session.removeAttribute(userName);
			session.removeAttribute("validCode");
			session.setAttribute(CustomConstant.USER, user);
			if(autoLogin){
				String cookieEncryStr = EncryptionDecryption.getInstance().encrypt(user.getLoginName())
						+ EncryptionDecryption.getInstance().encrypt(user.getPassword())
						+ EncryptionDecryption.getInstance().encrypt(System.currentTimeMillis() + "");
				Cookie cookie = new Cookie(COOKIE_NAME, cookieEncryStr);
				response.addCookie(cookie);
			}
			//最大生效时间
			session.setMaxInactiveInterval(1000 * 30);
			code = "LOGIN_SUCCESS";
			count = 0;
		} catch (CustomException e) {
			code = e.getMessage();
			count++;
			session.setAttribute(userName, count);
		} catch (Exception e) {
			e.printStackTrace();
			code = "SYSTEM_ERROR";
		}
		transferObj = new TransferObj(code, Global.CUSTOM_CODE_MSG.get(code), count);
		return transferObj;
	}
	
	@RequestMapping(value = "/validCode")
	public void validCode(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// 设置响应的类型格式为图片格式
		response.setContentType("image/jpeg");
		//禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		HttpSession session = request.getSession();
		
		OutputStream outputStream = response.getOutputStream();
		String validCode = CreateValidateCodeUtil.generateVerifyCode(4);
		session.setAttribute("validCode", validCode);
		CreateValidateCodeUtil.outputImage(80, 30, outputStream, validCode);
		outputStream.close();
	}
	
	@RequestMapping(value = "/getLoginCount")
	@ResponseBody
	public int getLoginCount(HttpServletRequest request, HttpServletResponse response, String userName){
		HttpSession session = request.getSession();
		Object obj = session.getAttribute(userName);
		if(obj != null){
			return (int)obj;
		}
		return 0;
	}
}
