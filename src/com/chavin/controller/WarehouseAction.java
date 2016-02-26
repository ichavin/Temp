package com.chavin.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
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
import com.chavin.po.DataPageInfo;
import com.chavin.po.TransferObj;
import com.chavin.po.Unit;
import com.chavin.po.Warehouse;
import com.chavin.service.WarehouseService;
import com.core.util.CustomStringUtils;
import com.core.util.PoiExcelUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RequestMapping(value = "/warehouse")
@Controller
public class WarehouseAction implements CustomConstant{
	
	@Resource
	private WarehouseService wareHouseService;
	
	/**
	 * 材料管理
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/warehouse")
	public String warehouse(HttpServletRequest request, HttpServletResponse response){
		return "warehouseManage/warehouse";
	}
	
	/**
	 * 获取数据表格记录
	 * @param request
	 * @param response
	 * @param wareHouse
	 * @return
	 */
	@RequestMapping(value = "/getData")
	@ResponseBody
	public DataPageInfo<Warehouse> getData(HttpServletRequest request, HttpServletResponse response,Warehouse wareHouse){
		DataPageInfo<Warehouse> dataPageInfo = null;
		try {
			
			if(wareHouse != null && StringUtils.isNotBlank(wareHouse.getCreateTimestartTime())){
				wareHouse.setCstartTime(timesdf.parse(wareHouse.getCreateTimestartTime()));
			}
			if(wareHouse != null && StringUtils.isNotBlank(wareHouse.getCreateTimeendTime())){
				wareHouse.setCendTime(timesdf.parse(wareHouse.getCreateTimeendTime()));
			}
			
			PageHelper.startPage(wareHouse.getPage(), wareHouse.getRows());
			if(!StringUtils.isEmpty(wareHouse.getSort()) && !StringUtils.isEmpty(wareHouse.getOrder())){
				if(wareHouse.getSort().equals("unitName")){
					wareHouse.setSort("u.name");
				}
				PageHelper.orderBy(CustomStringUtils.camelNameTolineName(wareHouse.getSort()) + " " + wareHouse.getOrder());
			}
			List<Warehouse> wareHouseList = wareHouseService.findListByEntity(wareHouse);
			dataPageInfo = new DataPageInfo<Warehouse>(wareHouseList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dataPageInfo == null)
			dataPageInfo = new DataPageInfo<Warehouse>();
		return dataPageInfo;
	}
	
	/**
	 * 操作数据库记录
	 * @param request
	 * @param response
	 * @param wareHouse （接收参数）
	 * @param createTimeStr （创建时间字符串格式）
	 * @param deleteIds （要删除的ids）
	 * @return
	 */
	@RequestMapping(value = "/operData")
	@ResponseBody
	public TransferObj operData(HttpServletRequest request, HttpServletResponse response, Warehouse wareHouse, String produceTimeStr, Integer[] deleteIds, String unit_id){
		TransferObj transferObj ;
		if(StringUtils.isEmpty(wareHouse.getOper())){
			return new TransferObj(false, "系统错误");
		}
		if(StringUtils.isNotBlank(produceTimeStr)){
			try {
				wareHouse.setProduceTime(timesdf.parse(produceTimeStr));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(unit_id)){
			Unit unit = new Unit();
			unit.setId(Integer.parseInt(unit_id));
			wareHouse.setUnit(unit);
		}
		if("update".equals(wareHouse.getOper())){
			String id = request.getParameter("operId");
			if(StringUtils.isBlank(id)){
				return new TransferObj(false, "修改失败，未能正确获取当前修改的行");
			}
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("idParamter", Integer.parseInt(id));
			try {
				int a = wareHouseService.updateByEntity(params,wareHouse);
				if(a >= 0) 
					transferObj = new TransferObj(true, "修改成功");
				else
					transferObj = new TransferObj(true, "修改失败");
			} catch (Exception e) {
				e.printStackTrace();
				transferObj = new TransferObj(false, "修改失败，数据库执行修改发生异常");
			}
		}else if("insert".equals(wareHouse.getOper())){
			if(wareHouse.getProduceTime() == null){
				wareHouse.setProduceTime(new Date());
			}
			try {
				int a = wareHouseService.insert(wareHouse);
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
				wareHouse.setIds(Arrays.asList(deleteIds));
				try {
					int a = wareHouseService.deleteByEntity(wareHouse);
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
	 * @param wareHouse
	 * @param chooseNum
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportExcel")
	public String exportExcel(HttpServletRequest request, HttpServletResponse response, Warehouse wareHouse) throws IOException{
		List<Map<String,String>> rows = Lists.newArrayList();  //记录
		try {
			if(wareHouse != null && StringUtils.isNotBlank(wareHouse.getCreateTimestartTime())){
				wareHouse.setCstartTime(timesdf.parse(wareHouse.getCreateTimestartTime()));
			}
			if(wareHouse != null && StringUtils.isNotBlank(wareHouse.getCreateTimeendTime())){
				wareHouse.setCendTime(timesdf.parse(wareHouse.getCreateTimeendTime()));
			}
			List<Warehouse> wareHouseList = wareHouseService.findListByEntity(wareHouse);
			int endSize = wareHouseList.size();
			if(wareHouse != null && wareHouse.getStartNum() != null && wareHouse.getStartNum() <= endSize){
				if(wareHouse.getEndNum() == null){
					wareHouseList = wareHouseList.subList(wareHouse.getStartNum(), endSize);
				}else if(wareHouse.getEndNum() < wareHouse.getStartNum()){
					wareHouseList = new ArrayList<Warehouse>();
				}else if(wareHouse.getEndNum() <= endSize){
					endSize = wareHouse.getEndNum();
					wareHouseList = wareHouseList.subList(wareHouse.getStartNum(), endSize);
				}else{
					wareHouseList = wareHouseList.subList(wareHouse.getStartNum(), endSize);
				}
			}else{
				wareHouseList = new ArrayList<Warehouse>();
			}
			Map<String,String> rowMap;
			Warehouse entity;
			String type = "其它";
			for(int i = 0 ; i < wareHouseList.size() ; i++){
				entity = wareHouseList.get(i);
				rowMap = Maps.newHashMap();
				rowMap.put("name", entity.getName());
				rowMap.put("unitName", entity.getUnit().getName());
				if(entity.getType() == 0){
					type = "产品";
				}else if(entity.getType() == 1){
					type = "材料";
				}else{
					type = "其它";
				}
				rowMap.put("type", type);
				rowMap.put("defectiveAmount", entity.getDefectiveAmount() + "");
				rowMap.put("qualityAmount", entity.getQualityAmount() + "");
				rowMap.put("totleAmount", entity.getTotleAmount() + "");
				rowMap.put("produceTime", entity.getProduceTime() == null ? "":timesdf.format(entity.getProduceTime()));
				rows.add(rowMap);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		Map<String,Integer> columnMap = Maps.newLinkedHashMap();
		columnMap.put("name", 5000);
		columnMap.put("unitName", 5000);
		columnMap.put("type", 5000);
		columnMap.put("defectiveAmount", 5000);
		columnMap.put("qualityAmount", 5000);
		columnMap.put("totleAmount", 5000);
		columnMap.put("produceTime", 5000);
		
		Map<String,String> columnCHMap = Maps.newHashMap();
		columnCHMap.put("name", "名称");
		columnCHMap.put("unitName", "单位");
		columnCHMap.put("type", "类型");
		columnCHMap.put("defectiveAmount", "次品数量");
		columnCHMap.put("qualityAmount", "正品数量");
		columnCHMap.put("totleAmount", "总数量");
		columnCHMap.put("produceTime", "入仓时间");
		
		String title = "库存列表";
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
	

	@RequestMapping(value = "/uploadWarehouse")
	@ResponseBody
	public String uploadWarehouse(HttpServletRequest request, HttpServletResponse response, @RequestParam("importFile") CommonsMultipartFile[] files) throws IOException {
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
