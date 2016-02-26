package com.chavin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chavin.constant.CustomConstant;
import com.chavin.po.Custom;
import com.chavin.po.Meterial;
import com.chavin.po.Scheme;
import com.chavin.po.SchemeMeterial;
import com.chavin.po.TransferObj;
import com.chavin.service.SchemeMeterialService;
import com.chavin.service.SchemeService;
import com.chavin.vo.Select;
import com.google.common.collect.Collections2;

@RequestMapping(value = "/scheme")
@Controller
public class SchemeAction implements CustomConstant {

	@Resource
	private SchemeService schemeService;
	
	@Resource
	private SchemeMeterialService schemeMeterialService;

	/**
	 * 产品方案
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/scheme")
	public String meterialManage(HttpServletRequest request, HttpServletResponse response) {
		return "commodity_manage/scheme";
	}
	
	@RequestMapping(value="/getData")
	@ResponseBody
	public List<Scheme> getData(HttpServletRequest request ,HttpServletResponse response ,String name , Integer[] meterial_id, Integer deleted){
		List<Scheme> schemes = new ArrayList<Scheme>();
		Scheme scheme = new Scheme();
		scheme.setName(name);
		scheme.setDeleted(deleted);
		if(meterial_id != null){
			List<Integer> meterialIds = Arrays.asList(meterial_id);
			scheme.setForeignIds(meterialIds);
		}
		try {
			schemes = schemeService.findListByEntity(scheme);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schemes;
	}

	@RequestMapping(value="/getSchemeMeterialData")
	@ResponseBody
	public List<SchemeMeterial> getSchemeMeterialData(Integer scheme_id){
		List<SchemeMeterial> schemeMeterials = null;
		try {
			SchemeMeterial schemeMeterial = new SchemeMeterial();
			if(scheme_id != null){
				Scheme scheme = new Scheme();
				scheme.setId(scheme_id);
				schemeMeterial.setScheme(scheme);
			}
			schemeMeterials = schemeMeterialService.findListByEntity(schemeMeterial);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schemeMeterials;
	}
	
	@RequestMapping(value="/insertScheme")
	@ResponseBody
	public TransferObj insertScheme(String schemeName, String schemeMeterialStr){
		TransferObj transferObj = new TransferObj();
		transferObj.setSuccess(false);
		if(StringUtils.isBlank(schemeName)){
			transferObj.setCode("null");
			transferObj.setDetailInfo("方案名称不能为空!");
			return transferObj;
		}
		if(StringUtils.isBlank(schemeMeterialStr)){
			return transferObj;
		}
		JSONArray jsonArray = JSONArray.fromObject(schemeMeterialStr);
		List<SchemeMeterial> schemeMeterialList = JSONArray.toList(jsonArray, SchemeMeterial.class);
		Scheme scheme = new Scheme();
		scheme.setName(schemeName);
		scheme.setDeleted(0);
		try {
			scheme = schemeService.findByEntity(scheme);
			if(scheme != null){
				transferObj.setCode("exist");
				transferObj.setDetailInfo("此方案名称已存在");
				return transferObj;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
			transferObj.setDetailInfo("系统 查询错误");
			return transferObj;
		}
		
		try {
			schemeService.insert(scheme);
		} catch (Exception e1) {
			e1.printStackTrace();
			transferObj.setCode("create_exception");
			transferObj.setDetailInfo("新建方案时发生异常");
			return transferObj;
		}
		for(int i = 0 ; i < schemeMeterialList.size() ; i ++){
			schemeMeterialList.get(i).setScheme(scheme);
		}
		try {
			if(schemeMeterialList.size() > 0){
				schemeMeterialService.batchInsert(schemeMeterialList);
				transferObj.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			transferObj.setCode("batch_exception");
			transferObj.setDetailInfo("批量插入发生异常");
			return transferObj;
		}
		transferObj.setSuccess(true);
		return transferObj;
	}
	
	@RequestMapping(value="/updateSchemeName")
	@ResponseBody
	public TransferObj updateSchemeName(Integer currentId, String newName){
		TransferObj transferObj = new TransferObj();
		transferObj.setSuccess(false);
		if(StringUtils.isBlank(newName)){
			transferObj.setDetailInfo("用户名不能为空");
			return transferObj;
		}
		Scheme scheme = new Scheme();
		scheme.setName(newName);
		try {
			scheme = schemeService.findByEntity(scheme);
			if(scheme != null){
				transferObj.setDetailInfo("该名称已存在！");
				return transferObj;
			}
		} catch (Exception e) {
			e.printStackTrace();
			transferObj.setDetailInfo("查询异常！");
			return transferObj;
		}
		Scheme entity = new Scheme();
		entity.setName(newName);
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("idParamter", currentId);
			schemeService.updateByEntity(map, entity);
		} catch (Exception e) {
			e.printStackTrace();
			transferObj.setDetailInfo("修改异常！");
			return transferObj;
		}
		transferObj.setSuccess(true);
		transferObj.setDetailInfo("修改成功");
		return transferObj;
	}
	
	@RequestMapping(value="/deleteScheme")
	@ResponseBody
	public TransferObj deleteScheme(Integer currentId){
		TransferObj transferObj = new TransferObj();
		transferObj.setSuccess(false);
		if(currentId == null){
			transferObj.setDetailInfo("获取方案失败");
			return transferObj;
		}
		Scheme entity = new Scheme();
		entity.setId(currentId);
		try {
			schemeService.deleteByEntity(entity);
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
		Scheme entity = new Scheme();
		entity.setDeleted(0);
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("idParamter", currentId);
			schemeService.updateByEntity(map, entity);
			transferObj.setSuccess(true);
			transferObj.setDetailInfo("撤销删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			transferObj.setDetailInfo("数据库操作异常");
			return transferObj;
		}
		return transferObj;
	}
	
	@RequestMapping(value = "/operScheme")
	@ResponseBody
	public TransferObj operScheme(Integer schemeId, String schemeMeterialStr){
		TransferObj transferObj = new TransferObj();
		transferObj.setSuccess(false);
		transferObj.setDetailInfo("操作失败");
		if(StringUtils.isBlank(schemeMeterialStr) || schemeId == null){
			transferObj.setDetailInfo("获取修改信息失败");
			return transferObj;
		}
		JSONArray jsonArray = JSONArray.fromObject(schemeMeterialStr);
		List<SchemeMeterial> schemeMeterialList = JSONArray.toList(jsonArray, SchemeMeterial.class);
		/**
		 * 1、先根据schemeId删除中间表scheme_meterial中的数据
		 * 2、根据schemMeterialList进行数据插入
		 */		
		try {
			schemeMeterialService.operSchemeMerterial(schemeId, schemeMeterialList);
		} catch (Exception e) {
			e.printStackTrace();
			transferObj.setCode("batch_exception");
			transferObj.setDetailInfo("数据库异常");
			return transferObj;
		}
		transferObj.setSuccess(true);
		return transferObj;
	}
	
	/**
	 * 获取方案下拉列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getSchemeSelectList")
	@ResponseBody
	public List<Select> getSchemeSelectList(HttpServletRequest request ,HttpServletResponse response){
		Scheme scheme = new Scheme();
		scheme.setDeleted(0);
		List<Scheme> schemeList = null;
		try {
			schemeList = schemeService.findListByEntity(scheme);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Select> list = new ArrayList<Select>();
		for(int i = 0 ; i < schemeList.size() ; i ++){
			Select select = new Select();
			select.setValue(schemeList.get(i).getId() + "");
			select.setText(schemeList.get(i).getName());
			list.add(select);
		}
		return list;
	}
	
	
	
}
