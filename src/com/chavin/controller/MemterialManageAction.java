package com.chavin.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
import com.chavin.po.Meterial;
import com.chavin.po.TransferObj;
import com.chavin.po.Unit;
import com.chavin.service.MeterialService;
import com.chavin.vo.MeterialVO;
import com.chavin.vo.Select;
import com.core.util.CustomStringUtils;
import com.core.util.PoiExcelUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RequestMapping(value = "/meterialManage")
@Controller
public class MemterialManageAction implements CustomConstant{
	
	@Resource
	private MeterialService meterialService;
	
	/**
	 * 材料管理
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/meterialManage")
	public String meterialManage(HttpServletRequest request, HttpServletResponse response){
		return "commodity_manage/meterialManage";
	}
	
	/**
	 * 获取数据表格记录
	 * @param request
	 * @param response
	 * @param meterial
	 * @return
	 */
	@RequestMapping(value = "/getData")
	@ResponseBody
	public DataPageInfo<Meterial> getData(HttpServletRequest request, HttpServletResponse response,MeterialVO meterialvo){
		DataPageInfo<Meterial> dataPageInfo = null;
		try {
			PageHelper.startPage(meterialvo.getPage(), meterialvo.getRows());
			if(!StringUtils.isEmpty(meterialvo.getSort()) && !StringUtils.isEmpty(meterialvo.getOrder())){
				PageHelper.orderBy(CustomStringUtils.camelNameTolineName(meterialvo.getSort()) + " " + meterialvo.getOrder());
			}
			Meterial meterial =  ChangeVo(meterialvo);
			List<Meterial> gysList = meterialService.findListByEntity(meterial);
			dataPageInfo = new DataPageInfo<Meterial>(gysList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dataPageInfo == null)
			dataPageInfo = new DataPageInfo<Meterial>();
		return dataPageInfo;
	}
	
	/**
	 * 操作数据库记录
	 * @param request
	 * @param response
	 * @param meterial （接收参数）
	 * @param createTimeStr （创建时间字符串格式）
	 * @param deleteIds （要删除的ids）
	 * @return
	 */
	@RequestMapping(value = "/operData")
	@ResponseBody
	public TransferObj operData(HttpServletRequest request, HttpServletResponse response, MeterialVO meterialvo, Integer[] deleteIds){
		TransferObj transferObj ;
		if(StringUtils.isEmpty(meterialvo.getOper())){
			return new TransferObj(false, "系统错误");
		}
		Meterial meterial =  ChangeVo(meterialvo);
		if("update".equals(meterialvo.getOper())){
			String id = request.getParameter("operId");
			if(StringUtils.isBlank(id)){
				return new TransferObj(false, "修改失败，未能正确获取当前修改的行");
			}
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("idParamter", Integer.parseInt(id));
			try {
				int a = meterialService.updateByEntity(params,meterial);
				if(a >= 0) 
					transferObj = new TransferObj(true, "修改成功");
				else
					transferObj = new TransferObj(true, "修改失败");
			} catch (Exception e) {
				e.printStackTrace();
				transferObj = new TransferObj(false, "修改失败，数据库执行修改发生异常");
			}
		}else if("insert".equals(meterialvo.getOper())){
			try {
				int a = meterialService.insert(meterial);
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
				meterial.setIds(Arrays.asList(deleteIds));
				try {
					int a = meterialService.deleteByEntity(meterial);
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
	 * @param meterial
	 * @param chooseNum
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportExcel")
	public String exportExcel(HttpServletRequest request, HttpServletResponse response, MeterialVO meterialvo) throws IOException{
		List<Map<String,String>> rows = Lists.newArrayList();  //记录
		Meterial meterial = ChangeVo(meterialvo);
		meterial.setEndNum(meterialvo.getEndNum());
		meterial.setStartNum(meterialvo.getStartNum());
		try {
			List<Meterial> gysList = meterialService.findListByEntity(meterial);
			int endSize = gysList.size();
			if(meterial != null && meterial.getStartNum() != null && meterial.getStartNum() <= endSize){
				if(meterial.getEndNum() == null){
					gysList = gysList.subList(meterial.getStartNum(), endSize);
				}else if(meterial.getEndNum() < meterial.getStartNum()){
					gysList = new ArrayList<Meterial>();
				}else if(meterial.getEndNum() <= endSize){
					endSize = meterial.getEndNum();
					gysList = gysList.subList(meterial.getStartNum(), endSize);
				}else{
					gysList = gysList.subList(meterial.getStartNum(), endSize);
				}
			}else{
				gysList = new ArrayList<Meterial>();
			}
			Map<String,String> rowMap;
			Meterial entity;
			for(int i = 0 ; i < gysList.size() ; i++){
				entity = gysList.get(i);
				rowMap = Maps.newHashMap();
				rowMap.put("name", entity.getName());
				rowMap.put("gys", entity.getCustom().getName());
				rowMap.put("unit", entity.getUnit().getName());
				rowMap.put("deleted", entity.getDeleted() == 0 ? "未删除":"已删除");
				rows.add(rowMap);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		Map<String,Integer> columnMap = Maps.newLinkedHashMap();
		columnMap.put("name", 5000);
		columnMap.put("gys", 5000);
		columnMap.put("unit", 5000);
		columnMap.put("deleted", 5000);
		
		Map<String,String> columnCHMap = Maps.newHashMap();
		columnCHMap.put("name", "材料名称");
		columnCHMap.put("gys", "供应商");
		columnCHMap.put("unit", "单位");
		columnCHMap.put("deleted", "删除标志");
		
		String title = "材料列表";
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
	

	@RequestMapping(value = "/uploadMeterial")
	@ResponseBody
	public String uploadMeterial(HttpServletRequest request, HttpServletResponse response, @RequestParam("importFile") CommonsMultipartFile[] files) throws IOException {
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
	
	//转换
	private Meterial ChangeVo(MeterialVO meterialvo){
		Meterial meterial = null;
		if(meterialvo != null){
			meterial = new Meterial();
			meterial.setId(meterialvo.getId());
			meterial.setName(meterialvo.getName());
			meterial.setDeleted(meterialvo.getDeleted());
			if(StringUtils.isNotBlank(meterialvo.getCustom_name()) || meterialvo.getCustom_id() != null){
				Custom custom = new Custom();
				custom.setId(meterialvo.getCustom_id());
				custom.setName(meterialvo.getCustom_name());
				meterial.setCustom(custom);
			}
			if(StringUtils.isNoneBlank(meterialvo.getUnit_name()) || meterialvo.getUnit_id() != null){
				Unit unit = new Unit();
				unit.setId(meterialvo.getUnit_id());
				unit.setName(meterialvo.getUnit_name());
				meterial.setUnit(unit);
			}
		}
		return meterial;
	}
	
	/**
	 * 获取材料下拉列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getMeterialSelectList")
	@ResponseBody
	public List<Select> getMeterialSelectList(HttpServletRequest request ,HttpServletResponse response){
		Meterial meterial = new Meterial();
		meterial.setDeleted(0);
		List<Meterial> meterialList = null;
		try {
			meterialList = meterialService.findListByEntity(meterial);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Select> list = new ArrayList<Select>();
		for(int i = 0 ; i < meterialList.size() ; i ++){
			Select select = new Select();
			select.setValue(meterialList.get(i).getId() + "");
			select.setText(meterialList.get(i).getName());
			list.add(select);
		}
		return list;
	}
	
	/**
	 * 获取材料单位信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getMeterialUnitList")
	@ResponseBody
	public Map<Integer,String> getMeterialUnitList(HttpServletRequest request ,HttpServletResponse response){
		Meterial meterial = new Meterial();
		List<Meterial> meterialList = null;
		try {
			meterialList = meterialService.findListByEntity(meterial);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<Integer,String> map = new HashMap<Integer, String>();
		for(int i = 0 ; i < meterialList.size() ; i ++){
			map.put(meterialList.get(i).getId(), meterialList.get(i).getUnit().getName());
		}
		return map;
	}
	
	
}
