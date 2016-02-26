package com.chavin.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.chavin.constant.CustomConstant;
import com.chavin.po.Custom;
import com.chavin.po.DataPageInfo;
import com.chavin.po.User;
import com.chavin.po.TransferObj;
import com.chavin.po.Unit;
import com.chavin.service.UserService;
import com.chavin.vo.Select;
import com.core.util.CustomStringUtils;
import com.core.util.EncryptionDecryption;
import com.core.util.PoiExcelUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RequestMapping(value = "/userManage")
@Controller
public class UserManageAction implements CustomConstant{
	
	@Resource
	private UserService userService;
	
	/**
	 * 材料管理
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/userManage")
	public String userManage(HttpServletRequest request, HttpServletResponse response){
		return "user/userManage";
	}
	
	/**
	 * 获取数据表格记录
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/getData")
	@ResponseBody
	public DataPageInfo<User> getData(HttpServletRequest request, HttpServletResponse response,User user){
		DataPageInfo<User> dataPageInfo = null;
		try {
			PageHelper.startPage(user.getPage(), user.getRows());
			if(!StringUtils.isEmpty(user.getSort()) && !StringUtils.isEmpty(user.getOrder())){
				PageHelper.orderBy(CustomStringUtils.camelNameTolineName(user.getSort()) + " " + user.getOrder());
			}
			List<User> userList = userService.findListByEntity(user);
			dataPageInfo = new DataPageInfo<User>(userList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dataPageInfo == null)
			dataPageInfo = new DataPageInfo<User>();
		return dataPageInfo;
	}
	
	/**
	 * 操作数据库记录
	 * @param request
	 * @param response
	 * @param user （接收参数）
	 * @param createTimeStr （创建时间字符串格式）
	 * @param deleteIds （要删除的ids）
	 * @return
	 */
	@RequestMapping(value = "/operData")
	@ResponseBody
	public TransferObj operData(HttpServletRequest request, HttpServletResponse response, User user, Integer[] deleteIds, String birthdayStr){
		TransferObj transferObj ;
		if(StringUtils.isEmpty(user.getOper())){
			return new TransferObj(false, "系统错误");
		}
		if(StringUtils.isNoneBlank(birthdayStr)){
			try {
				user.setBirthday(timesdf.parse(birthdayStr));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		/**
		 * 验证登录名是否存在
		 */
		User entity = new User();
		if("update".equals(user.getOper()) || "insert".equals(user.getOper())){
			entity.setLoginName(user.getLoginName());
			try {
				entity = userService.findByEntity(entity);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if("update".equals(user.getOper())){
			String id = request.getParameter("operId");
			if(StringUtils.isBlank(id)){
				return new TransferObj(false, "修改失败，未能正确获取当前修改的行");
			}
			if(StringUtils.isNotBlank(user.getPassword())){
				try {
					String encryptPwd = EncryptionDecryption.getInstance().encrypt(user.getPassword());
					user.setPassword(encryptPwd);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				user.setPassword(null);
			}
			if(entity != null && (entity.getId() != Integer.parseInt(id))){
				return new TransferObj(false, "登录名已存在！");
			}
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("idParamter", Integer.parseInt(id));
			try {
				int a = userService.updateByEntity(params,user);
				if(a >= 0) 
					transferObj = new TransferObj(true, "修改成功");
				else
					transferObj = new TransferObj(true, "修改失败");
			} catch (Exception e) {
				e.printStackTrace();
				transferObj = new TransferObj(false, "修改失败，数据库执行修改发生异常");
			}
		}else if("insert".equals(user.getOper())){
			if(entity != null){
				return new TransferObj(false, "登录名已存在！");
			}
			String pwd = user.getPassword();
			if(StringUtils.isBlank(pwd)){
				pwd = "123456";
			}
			try {
				String encryptPwd = EncryptionDecryption.getInstance().encrypt(pwd);
				user.setPassword(encryptPwd);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				int a = userService.insert(user);
				if(a >= 0) 
					transferObj = new TransferObj(true, "新增成功");
				else
					transferObj = new TransferObj(true, "新增失败");
			} catch (Exception e) {
				e.printStackTrace();
				transferObj = new TransferObj(true, "新增失败，数据库执行插入发生异常");
			}
		}else{
			if(deleteIds != null && deleteIds.length > 0){
				user.setIds(Arrays.asList(deleteIds));
				try {
					int a = userService.deleteByEntity(user);
					if(a >= 0) 
						transferObj = new TransferObj(true, "删除成功");
					else
						transferObj = new TransferObj(true, "删除失败");
				} catch (Exception e) {
					e.printStackTrace();
					transferObj = new TransferObj(true, "删除失败，数据库执行删除发生异常");
				}
			}else{
				transferObj = new TransferObj(false, "获取删除的行失败");
			}
		}
		return transferObj;
	}
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @param user
	 * @param chooseNum
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportExcel")
	public String exportExcel(HttpServletRequest request, HttpServletResponse response, User user) throws IOException{
		List<Map<String,String>> rows = Lists.newArrayList();  //记录
		try {
			List<User> userList = userService.findListByEntity(user);
			int endSize = userList.size();
			if(user != null && user.getStartNum() != null && user.getStartNum() <= endSize){
				if(user.getEndNum() == null){
					userList = userList.subList(user.getStartNum(), endSize);
				}else if(user.getEndNum() < user.getStartNum()){
					userList = new ArrayList<User>();
				}else if(user.getEndNum() <= endSize){
					endSize = user.getEndNum();
					userList = userList.subList(user.getStartNum(), endSize);
				}else{
					userList = userList.subList(user.getStartNum(), endSize);
				}
			}else{
				userList = new ArrayList<User>();
			}
			Map<String,String> rowMap;
			User entity;
			for(int i = 0 ; i < userList.size() ; i++){
				entity = userList.get(i);
				rowMap = Maps.newHashMap();
				rowMap.put("loginName", entity.getLoginName());
				rowMap.put("realName", entity.getRealName());
				rowMap.put("sex", entity.getSex() == 0 ? "女" : "男");
				rowMap.put("email", entity.getEmail());
				rowMap.put("phone", entity.getPhone());
				rowMap.put("idCard", entity.getIdCard());
				rowMap.put("birthday", entity.getBirthday() == null ? "" : timesdf.format(entity.getBirthday()));
				rowMap.put("lastLoginTime", entity.getLastLoginTime() == null ? "" : timesdf.format(entity.getLastLoginTime()));
				rowMap.put("deleted", entity.getDeleted() == 0 ? "未删除":"已删除");
				rowMap.put("school", entity.getGraduateSchool());
				rows.add(rowMap);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		Map<String,Integer> columnMap = Maps.newLinkedHashMap();
		columnMap.put("loginName", 5000);
		columnMap.put("realName", 5000);
		columnMap.put("sex", 5000);
		columnMap.put("email", 5000);
		columnMap.put("phone", 5000);
		columnMap.put("idCard", 5000);
		columnMap.put("birthday", 5000);
		columnMap.put("lastLoginTime", 5000);
		columnMap.put("deleted", 5000);
		columnMap.put("school", 10000);
		
		Map<String,String> columnCHMap = Maps.newHashMap();
		columnCHMap.put("loginName", "登录名");
		columnCHMap.put("realName", "真实名");
		columnCHMap.put("sex", "性别");
		columnCHMap.put("email", "邮箱");
		columnCHMap.put("phone", "手机号码");
		columnCHMap.put("idCard", "身份证号码");
		columnCHMap.put("birthday", "生日");
		columnCHMap.put("lastLoginTime", "最后登录时间");
		columnCHMap.put("deleted", "删除标志");
		columnCHMap.put("school", "毕业学校");
		
		String title = "用户列表";
		Workbook workbook = null;
		workbook = PoiExcelUtil.getInstance().createxlsExcel(rows, columnMap, columnCHMap, title);
		if(workbook == null){
			response.setHeader("Content-type", "text/html;charset=UTF-8"); 
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("<script type=\"text/javascript\">alert(\"导出失败\");window.close();</script>");
			return null;
		}else{
			response.reset();
	        response.setContentType("application/vnd.ms-excel;charset=utf-8");
	        try {
				response.setHeader("Content-Disposition", "attachment;filename="+ new String((title + ".xls").getBytes(), "iso-8859-1"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	        ServletOutputStream out = response.getOutputStream();
	        //写入输出流
	        workbook.write(out);
	        out.close();
		}
		return null;
	}
	

	@RequestMapping(value = "/uploadUser")
	@ResponseBody
	public String uploadUser(HttpServletRequest request, HttpServletResponse response, @RequestParam("importFile") CommonsMultipartFile[] files) throws IOException {
		if(files == null || files.length <= 0){
			return "fail";
		}
		CommonsMultipartFile file = files[0];
		InputStream stream = file.getInputStream();  
		String fileName = file.getOriginalFilename(); 
		try {
			
			String fileNameFull = "D:\\testUpload\\" + fileName;
			OutputStream bos = new FileOutputStream(fileNameFull);
			byte[] b = new byte[1024];
			while ((stream.read(b)) != -1) {
				bos.write(b, 0, b.length);
			}
			bos.close();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
}
