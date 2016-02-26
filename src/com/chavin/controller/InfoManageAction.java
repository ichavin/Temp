package com.chavin.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.chavin.po.TransferObj;
import com.chavin.service.InfoManageService;
import com.chavin.vo.Select;
import com.core.util.CustomStringUtils;
import com.core.util.PoiExcelUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RequestMapping(value = "/infoManage")
@Controller
public class InfoManageAction implements CustomConstant{
	
	@Resource
	private InfoManageService infoManageService;
	
	
	/**
	 * 供应商
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/hzsInfo")
	public String hzsInfo(HttpServletRequest request, HttpServletResponse response){
		return "infoManage/hzsInfo";
	}
	
	/**
	 * 获取数据表格记录
	 * @param request
	 * @param response
	 * @param custom
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/getData")
	@ResponseBody
	public DataPageInfo<Custom> getData(HttpServletRequest request, HttpServletResponse response, Custom custom) throws ParseException{
		DataPageInfo<Custom> dataPageInfo = null;
		if(custom != null && StringUtils.isNotBlank(custom.getCreateTimestartTime())){
			custom.setCstartTime(timesdf.parse(custom.getCreateTimestartTime()));
		}
		if(custom != null && StringUtils.isNotBlank(custom.getCreateTimeendTime())){
			custom.setCendTime(timesdf.parse(custom.getCreateTimeendTime()));
		}
		try {
			PageHelper.startPage(custom.getPage(), custom.getRows());
			if(!StringUtils.isEmpty(custom.getSort()) && !StringUtils.isEmpty(custom.getOrder())){
				PageHelper.orderBy(CustomStringUtils.camelNameTolineName(custom.getSort()) + " " + custom.getOrder());
			}
			List<Custom> gysList = infoManageService.findListByEntity(custom);
			dataPageInfo = new DataPageInfo<Custom>(gysList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dataPageInfo == null)
			dataPageInfo = new DataPageInfo<Custom>();
		return dataPageInfo;
	}
	
	/**
	 * 操作数据库记录
	 * @param request
	 * @param response
	 * @param custom （接收参数）
	 * @param createTimeStr （创建时间字符串格式）
	 * @param deleteIds （要删除的ids）
	 * @return
	 */
	@RequestMapping(value = "/operData")
	@ResponseBody
	public TransferObj operData(HttpServletRequest request, HttpServletResponse response, Custom custom, String createTimeStr, Integer[] deleteIds){
		TransferObj transferObj ;
		if(StringUtils.isEmpty(custom.getOper())){
			return new TransferObj(false, "系统错误");
		}
		if(StringUtils.isNotBlank(createTimeStr)){
			try {
				custom.setCreateTime(datesdf.parse(createTimeStr));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			custom.setCreateTime(new Date());
		}
		if("update".equals(custom.getOper())){
			String id = request.getParameter("operId");
			if(StringUtils.isBlank(id)){
				return new TransferObj(false, "修改失败，未能正确获取当前修改的行");
			}
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("idParamter", Integer.parseInt(id));
			try {
				int a = infoManageService.updateByEntity(params,custom);
				if(a >= 0) 
					transferObj = new TransferObj(true, "修改成功");
				else
					transferObj = new TransferObj(true, "修改失败");
			} catch (Exception e) {
				e.printStackTrace();
				transferObj = new TransferObj(false, "修改失败，数据库执行修改发生异常");
			}
		}else if("insert".equals(custom.getOper())){
			try {
				int a = infoManageService.insert(custom);
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
				custom.setIds(Arrays.asList(deleteIds));
				try {
					int a = infoManageService.deleteByEntity(custom);
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
	 * @param custom
	 * @param chooseNum
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportExcel")
	public String exportExcel(HttpServletRequest request, HttpServletResponse response, Custom custom) throws IOException{
		List<Map<String,String>> rows = Lists.newArrayList();  //记录
		try {
			if(custom != null && StringUtils.isNotBlank(custom.getCreateTimestartTime())){
				custom.setCstartTime(timesdf.parse(custom.getCreateTimestartTime()));
			}
			if(custom != null && StringUtils.isNotBlank(custom.getCreateTimeendTime())){
				custom.setCendTime(timesdf.parse(custom.getCreateTimeendTime()));
			}
			List<Custom> gysList = infoManageService.findListByEntity(custom);
			int endSize = gysList.size();
			if(custom != null && custom.getStartNum() != null && custom.getStartNum() <= endSize){
				if(custom.getEndNum() == null){
					gysList = gysList.subList(custom.getStartNum(), endSize);
				}else if(custom.getEndNum() < custom.getStartNum()){
					gysList = new ArrayList<Custom>();
				}else if(custom.getEndNum() <= endSize){
					endSize = custom.getEndNum();
					gysList = gysList.subList(custom.getStartNum(), endSize);
				}else{
					gysList = gysList.subList(custom.getStartNum(), endSize);
				}
			}else{
				gysList = new ArrayList<Custom>();
			}
			Map<String,String> rowMap;
			Custom entity;
			for(int i = 0 ; i < gysList.size() ; i++){
				entity = gysList.get(i);
				rowMap = Maps.newHashMap();
				rowMap.put("name", entity.getName());
				rowMap.put("phone", entity.getPhone());
				rowMap.put("telNum", entity.getTelNumb());
				rowMap.put("company", entity.getCompany());
				rowMap.put("createTime", entity.getCreateTime() == null ? "" : timesdf.format(entity.getCreateTime()));
				rowMap.put("address", entity.getAddress());
				rowMap.put("mark", entity.getMark());
				rowMap.put("isgys", entity.getIsgys() == 1 ? "供应商" : "客户");
				rowMap.put("iscompany", entity.getIscompany() == 1 ? "单位" : "个人");
				rowMap.put("deleted", entity.getDeleted() == 1 ? "已删除" : "未删除");
				rows.add(rowMap);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		Map<String,Integer> columnMap = Maps.newLinkedHashMap();
		columnMap.put("name", 5000);
		columnMap.put("phone", 5000);
		columnMap.put("telNum", 5000);
		columnMap.put("company", 10000);
		columnMap.put("createTime", 5000);
		columnMap.put("address", 10000);
		columnMap.put("mark", 15000);
		columnMap.put("isgys", 5000);
		columnMap.put("iscompany", 5000);
		columnMap.put("deleted", 5000);
		
		Map<String,String> columnCHMap = Maps.newHashMap();
		columnCHMap.put("name", "姓名");
		columnCHMap.put("phone", "手机号码");
		columnCHMap.put("telNum", "电话号码");
		columnCHMap.put("company", "公司名称");
		columnCHMap.put("address", "公司地址");
		columnCHMap.put("mark", "备注");
		columnCHMap.put("isgys", "供应商角色");
		columnCHMap.put("iscompany", "性质类型");
		columnCHMap.put("deleted", "删除标志");
		columnCHMap.put("createTime", "创建时间");
		
		String title = "标题";
		if(custom.getIsgys() == 1){
			title = "供应商列表";
		}else if(custom.getIsgys() == 0){
			title = "客户列表";
		}else{
			title = "合作商列表";
		}
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
	

	@RequestMapping(value = "/uploadCustom")
	@ResponseBody
	public String uploadCustom(HttpServletRequest request, HttpServletResponse response, @RequestParam("importFile") CommonsMultipartFile[] files) throws IOException {
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
	
	@RequestMapping(value = "/getgysSelectList")
	@ResponseBody
	public List<Select> getgysSelectList(HttpServletRequest request ,HttpServletResponse response){
		Custom custom = new Custom();
		custom.setIsgys(1);
		custom.setDeleted(0);
		List<Custom> gysList = null;
		try {
			gysList = infoManageService.findListByEntity(custom);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Select> list = new ArrayList<Select>();
		for(int i = 0 ; i < gysList.size() ; i ++){
			Select select = new Select();
			select.setValue(gysList.get(i).getId() + "");
			select.setText(gysList.get(i).getName());
			list.add(select);
		}
		return list;
	}
	
	@RequestMapping(value = "/getkhSelectList")
	@ResponseBody
	public List<Select> getkhSelectList(HttpServletRequest request ,HttpServletResponse response){
		Custom custom = new Custom();
		custom.setIsgys(0);
		custom.setDeleted(0);
		List<Custom> gysList = null;
		try {
			gysList = infoManageService.findListByEntity(custom);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Select> list = new ArrayList<Select>();
		for(int i = 0 ; i < gysList.size() ; i ++){
			Select select = new Select();
			select.setValue(gysList.get(i).getId() + "");
			select.setText(gysList.get(i).getName());
			list.add(select);
		}
		return list;
	}
}
