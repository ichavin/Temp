package com.chavin.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.chavin.constant.CustomConstant;
import com.chavin.po.User;
import com.chavin.service.UserService;
import com.core.util.EncryptionDecryption;

public class LoginInterceptor implements HandlerInterceptor,CustomConstant {

	@Autowired
	private UserService userService;
	
	//进入 Handler方法之前执行
	//用于身份认证、身份授权
	//比如身份认证，如果认证通过表示当前用户没有登陆，需要此方法拦截不再向下执行
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		//获取请求的url
		String url = request.getRequestURI();
		HttpSession session  = request.getSession();
		
		if(url.indexOf("logout") >= 0){  //如果是注销
			//从session中取出用户身份信息
			User user = (User)session.getAttribute(USER);
			if(user != null)
				session.removeAttribute(USER);
			return true;
		}else if(url.indexOf("login") >= 0){             //访问登录页面或者进行登录
			
			if(url.indexOf("login/index") >= 0 || url.endsWith("/Temp/") ||  url.endsWith("/Temp")){   //访问登录页面
				//1、已经登录的用户再次访问登录页面
				User logined_user = (User)session.getAttribute(USER);
				if(logined_user != null){
					response.sendRedirect(request.getServletContext().getContextPath() + "/home/index");
					return false;
				}
				
				Cookie[] cookies = request.getCookies();
				//cookie没有用户信息
				if(cookies == null){
					return true;
				}
				//cookie有用户信息，则取出cookie中的值进行登录校验
				String userName = null, pwd = null, timeStr = null;
				String[] cookieStr = null;
				Cookie cookie = null;
				for(int i = 0 ; i < cookies.length ; i ++)
				{
					cookie = cookies[i];
					if(("COOKIE_NAME").equals(cookie.getName())){
						cookieStr = cookie.getValue().split(":");
						if(cookieStr != null && cookieStr.length >= 3){
							userName = cookieStr[0];
							pwd = cookieStr[1];
							timeStr = cookieStr[2];
							break;
						}
					}
				}
				if(userName != null && pwd != null && timeStr != null){
					Long setTime = Long.parseLong(EncryptionDecryption.getInstance().decrypt(timeStr));
					Long now = System.currentTimeMillis();
					if((now - setTime)/(1000 * 60 * 60 * 24) > 3){
						//TODO
						PrintWriter writer =  response.getWriter();
						writer.write("autoLoginTimeOut");   //自动登录超过指定时间
						writer.flush();
						writer.close();
						return false;
					}
					userName = EncryptionDecryption.getInstance().decrypt(userName);
					User entity = new User();
					entity.setLoginName(userName);
					User user = userService.findByEntity(entity);
					if(user == null){
						cookie.setMaxAge(0);
						response.addCookie(cookie);
						//TODO
						PrintWriter writer =  response.getWriter();
						writer.write("autoLoginInvalid");   //自动登录失败
						writer.flush();
						writer.close();
						return false;
					}
					String encryPwd = EncryptionDecryption.getInstance().encrypt(user.getPassword());
					if((user.getLoginName() + encryPwd).equals(userName + pwd)){
						session.setAttribute(USER, user);
						response.sendRedirect(request.getServletContext().getContextPath() + "/home/index");
						return false;
					}
					return true;
				}
				
			}
			return true;
		}else{                                     //如果不是登录页面
			//从session中取出用户身份信息
			User user = (User)session.getAttribute(USER);
			if(user != null){
				return true;
			}
			// ajax请求
			if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
				PrintWriter writer =  response.getWriter();
				writer.write("timeout");
			    writer.flush();
			    writer.close();
				return true;
			}else{
				response.sendRedirect(request.getServletContext().getContextPath() + "/login/index");
			}
			return false;
		}
		
	}
	
	//进入Handler方法之后，返回modelAndView之前执行
	//应用场景从modelAndView出发：将公用的模型数据(比如菜单导航)在这里传到视图，也可以在这里统一指定视图
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3) throws Exception {
		
	}
	
	//执行Handler完成执行此方法
	//应用场景：统一异常处理，统一日志处理
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3) throws Exception {
		
	}
	

}
