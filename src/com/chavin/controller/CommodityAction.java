package com.chavin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.chavin.constant.CustomConstant;
import com.chavin.po.Commodity;
import com.chavin.po.Scheme;
import com.chavin.po.TransferObj;
import com.chavin.service.CommodityService;
import com.chavin.vo.Select;

@RequestMapping(value = "/commodity")
@Controller
public class CommodityAction implements CustomConstant {

	@Resource
	private CommodityService commodityService;

	/**
	 * 产品方案
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/commodity")
	public String meterialManage(HttpServletRequest request, HttpServletResponse response) {
		return "commodity_manage/commodity";
	}
	
	@RequestMapping(value="/getData")
	@ResponseBody
	public List<Commodity> getData(HttpServletRequest request ,HttpServletResponse response ,String name ,Integer[] scheme_id , Integer deleted){
		List<Commodity> commoditys = new ArrayList<Commodity>();
		Commodity commodity = new Commodity();
		commodity.setName(name);
		commodity.setDeleted(deleted);
		if(scheme_id != null){
			List<Integer> schemeIds = Arrays.asList(scheme_id);
			commodity.setForeignIds(schemeIds);
		}
		try {
			commoditys = commodityService.findListByEntity(commodity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commoditys;
	}

	@RequestMapping(value="/updateCommodity")
	@ResponseBody
	public TransferObj updateCommodity(Integer currentId, String newName,String updateNumberId,Double updateWeight, String updateSize){
		TransferObj transferObj = new TransferObj();
		transferObj.setSuccess(false);
		if(StringUtils.isBlank(newName)){
			transferObj.setCode("nameNotNull");
			transferObj.setDetailInfo("名称不能为空");
			return transferObj;
		}
		if(StringUtils.isBlank(updateNumberId)){
			transferObj.setCode("numberNotNull");
			transferObj.setDetailInfo("编号不能为空");
			return transferObj;
		}
		if(updateWeight == null){
			transferObj.setCode("weightNotNull");
			transferObj.setDetailInfo("重量不能为空");
			return transferObj;
		}
		if(StringUtils.isBlank(updateSize)){
			transferObj.setCode("sizeNotNull");
			transferObj.setDetailInfo("尺寸不能为空");
			return transferObj;
		}
		
		Commodity commodity = new Commodity();
		try {
			commodity.setName(newName);
			commodity = commodityService.findByEntity(commodity);
			if(commodity != null && commodity.getId() != currentId){
				transferObj.setCode("nameExist");
				transferObj.setDetailInfo("该名称已存在！");
				return transferObj;
			}
			commodity = new Commodity();
			commodity.setNumberId(updateNumberId);
			commodity = commodityService.findByEntity(commodity);
			if(commodity != null && commodity.getId() != currentId){
				transferObj.setCode("numberIdExist");
				transferObj.setDetailInfo("该编号已存在！");
				return transferObj;
			}
		} catch (Exception e) {
			e.printStackTrace();
			transferObj.setDetailInfo("查询异常！");
			return transferObj;
		}
		Commodity entity = new Commodity();
		entity.setName(newName);
		entity.setNumberId(updateNumberId);
		entity.setWeight(updateWeight);
		entity.setSize(updateSize);
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("idParamter", currentId);
			commodityService.updateByEntity(map, entity);
		} catch (Exception e) {
			e.printStackTrace();
			transferObj.setDetailInfo("修改异常！");
			return transferObj;
		}
		transferObj.setSuccess(true);
		transferObj.setDetailInfo("修改成功");
		return transferObj;
	}
	
	
	@RequestMapping(value="/deleteCommodity")
	@ResponseBody
	public TransferObj deleteCommodity(Integer currentId){
		TransferObj transferObj = new TransferObj();
		transferObj.setSuccess(false);
		if(currentId == null){
			transferObj.setDetailInfo("获取方案失败");
			return transferObj;
		}
		Commodity entity = new Commodity();
		entity.setId(currentId);
		try {
			commodityService.deleteByEntity(entity);
			transferObj.setSuccess(true);
			transferObj.setDetailInfo("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			transferObj.setDetailInfo("数据库操作异常");
			return transferObj;
		}
		return transferObj;
	}
	
	@RequestMapping(value="/revertDeleteScheme")
	@ResponseBody
	public TransferObj revertDeleteScheme(Integer currentId){
		TransferObj transferObj = new TransferObj();
		transferObj.setSuccess(false);
		if(currentId == null){
			transferObj.setDetailInfo("获取方案失败");
			return transferObj;
		}
		Commodity entity = new Commodity();
		entity.setDeleted(0);
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("idParamter", currentId);
			commodityService.updateByEntity(map, entity);
			transferObj.setSuccess(true);
			transferObj.setDetailInfo("撤销删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			transferObj.setDetailInfo("数据库操作异常");
			return transferObj;
		}
		return transferObj;
	}
	
	@RequestMapping(value = "/changeImage")
	@ResponseBody
	public TransferObj changeImage(HttpServletRequest request, HttpServletResponse response, @RequestParam("importFile") CommonsMultipartFile file, Integer commodityId){
		TransferObj transferObj = new TransferObj();
		transferObj.setSuccess(false);
		InputStream stream;
		String projectPath = request.getServletContext().getRealPath("/");
		Commodity commodity = new Commodity();
		Commodity entity = new Commodity();
		entity.setId(commodityId);
		try {
			entity = commodityService.findByEntity(entity);
		} catch (Exception e1) {
			e1.printStackTrace();
			transferObj.setDetailInfo("获取当前产品失败");
			return transferObj;
		}
		String commodityOldUrl;
		String currentImageUrl = entity.getImageUrl();
		String newName = entity.getName() + System.currentTimeMillis() + ".jpg";
		if(StringUtils.isBlank(currentImageUrl)){
			commodityOldUrl = null;
		}else if(currentImageUrl.indexOf("/") != -1){
			commodityOldUrl = currentImageUrl.substring(currentImageUrl.lastIndexOf("/") + 1);
		}else{
			commodityOldUrl = currentImageUrl.substring(currentImageUrl.lastIndexOf("\\") + 1);
		}
		commodity.setImageUrl(File.separator + "resource" + File.separator + "userimg" + File.separator + "commodity" + File.separator + newName);
		try {
			stream = file.getInputStream();
			String fileNameFull = projectPath + "resource" + File.separator + "userimg" + File.separator + "commodity" + File.separator + newName;
			OutputStream bos = new FileOutputStream(fileNameFull);
			byte[] b = new byte[1024];
			int t = 0;
			while ((t = stream.read(b)) != -1) {
				bos.write(b, 0, t);
			}
			bos.flush();
			bos.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
			transferObj.setDetailInfo("添加图片出错");
			return transferObj;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("idParamter", commodityId);
		try {
			commodityService.updateByEntity(map,commodity);
			if(StringUtils.isNotBlank(commodityOldUrl) && commodityOldUrl.indexOf("none.png") == -1){
				File oldImg = new File(projectPath + "resource" + File.separator + "userimg" + File.separator + "commodity" + File.separator + commodityOldUrl);
				if(oldImg.exists()){
					oldImg.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			transferObj.setDetailInfo("数据库更新语句出错");
			return transferObj;
		}
		transferObj.setSuccess(true);
		transferObj.setDetailInfo("修改成功");
		transferObj.setObject(commodity.getImageUrl());
		return transferObj;
	}
	
	
	@RequestMapping(value="/changeScheme")
	@ResponseBody
	public TransferObj changeScheme(Integer commodityId, Integer schemeId){
		TransferObj transferObj = new TransferObj();
		transferObj.setSuccess(false);
		
		Commodity commodity = new Commodity();
		Scheme scheme = new Scheme();
		scheme.setId(schemeId);
		commodity.setScheme(scheme);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("idParamter", commodityId);
		try {
			commodityService.updateByEntity(map, commodity);
		} catch (Exception e) {
			e.printStackTrace();
			transferObj.setDetailInfo("数据库更新语句出错");
			return transferObj;
		}
		transferObj.setSuccess(true);
		transferObj.setDetailInfo("修改成功");
		return transferObj;
	}
	
	@RequestMapping(value="/insertCommodity")
	@ResponseBody
	public TransferObj insertCommodity(HttpServletRequest request, HttpServletResponse response ,@RequestParam("importFile") CommonsMultipartFile file, String inserCommodityName,Integer insertSchemeId,String insertNumberId,Double insertWeight, String insertSize){
		//String projectPath = request.getServletContext().getRealPath("/");
		String projectPath = "D:" + File.separator + "zhanghongyan" + File.separator + "tempimgs" + File.separator;
		return common(projectPath,file,inserCommodityName,insertSchemeId,insertNumberId,insertWeight,insertSize); 
	}
	
	@RequestMapping(value = "/insertCommodityWithoutImg")
	@ResponseBody
	public TransferObj insertCommodityWithoutImg(HttpServletRequest request, HttpServletResponse response, String inserCommodityName,Integer insertSchemeId,String insertNumberId,Double insertWeight, String insertSize){
		//String projectPath = request.getServletContext().getRealPath("/");
		String projectPath = "D:" + File.separator + "zhanghongyan" + File.separator + "tempimgs" + File.separator;
		return common(projectPath,null,inserCommodityName,insertSchemeId,insertNumberId,insertWeight,insertSize); 
	}
	
	private TransferObj common(String projectPath,CommonsMultipartFile file, String inserCommodityName,Integer insertSchemeId,String insertNumberId,Double insertWeight, String insertSize){
		TransferObj transferObj = new TransferObj();
		transferObj.setSuccess(false);
		if(StringUtils.isBlank(inserCommodityName) || insertSchemeId == null){
			transferObj.setDetailInfo("获取新增信息失败");
			return transferObj;
		}
		Commodity entity = new Commodity();
		entity.setNumberId(insertNumberId);
		Commodity commodity;
		try {
			commodity = commodityService.findByEntity(entity);
			if(commodity != null){
				transferObj.setCode("numberId_exist");
				transferObj.setDetailInfo("编号已存在");
				return transferObj;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			transferObj.setDetailInfo("查询错误");
			return transferObj;
		}
		try {
			entity = new Commodity();
			entity.setName(inserCommodityName);
			commodity = commodityService.findByEntity(entity);
			if(commodity != null){
				transferObj.setCode("name_exist");
				transferObj.setDetailInfo("名称已存在");
				return transferObj;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			transferObj.setDetailInfo("查询错误");
			return transferObj;
		}
		commodity = new Commodity();
		if(file != null && !file.getOriginalFilename().equals("none.png")){
			String newName = inserCommodityName + System.currentTimeMillis() + ".jpg";
			InputStream inputStream = null;
			OutputStream bos = null;
			String img_url = File.separator + "resource" + File.separator + "userimg" + File.separator + "commodity" + File.separator + newName;
			try {
				inputStream = file.getInputStream();
				String fileNameFull = projectPath + "resource" + File.separator + "userimg" + File.separator + "commodity" + File.separator + newName;
				bos = new FileOutputStream(fileNameFull);
				byte[] b = new byte[1024];
				int t = 0;
				while ((t = inputStream.read(b)) != -1) {
					bos.write(b, 0, t);
				}
				bos.flush();
				bos.close();
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
				transferObj.setDetailInfo("添加图片出错");
				return transferObj;
			}
			commodity.setImageUrl(img_url);
		}
		commodity.setName(inserCommodityName);
		Scheme scheme = new Scheme();
		scheme.setId(insertSchemeId);
		commodity.setScheme(scheme);
		commodity.setNumberId(insertNumberId);
		commodity.setWeight(insertWeight);
		commodity.setSize(insertSize);
		try {
			commodityService.insert(commodity);
		} catch (Exception e) {
			e.printStackTrace();
			transferObj.setDetailInfo("数据库插入时出错");
			return transferObj;
		}
		transferObj.setSuccess(true);
		transferObj.setDetailInfo("修改成功");
		return transferObj;
	}
	
	/**
	 * 获取方案下拉列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getCommoditySelectList")
	@ResponseBody
	public List<Select> getCommoditySelectList(HttpServletRequest request ,HttpServletResponse response){
		Commodity commodity = new Commodity();
		commodity.setDeleted(0);
		List<Commodity> commodityList = null;
		try {
			commodityList = commodityService.findListByEntity(commodity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Select> list = new ArrayList<Select>();
		for(int i = 0 ; i < commodityList.size() ; i ++){
			Select select = new Select();
			select.setValue(commodityList.get(i).getId() + "");
			select.setText(commodityList.get(i).getName());
			list.add(select);
		}
		return list;
	}
}
