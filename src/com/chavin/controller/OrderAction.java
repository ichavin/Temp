package com.chavin.controller;

import java.io.File;
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
import com.chavin.po.Commodity;
import com.chavin.po.Custom;
import com.chavin.po.DataPageInfo;
import com.chavin.po.Meterial;
import com.chavin.po.Order;
import com.chavin.po.TransferObj;
import com.chavin.po.Unit;
import com.chavin.service.OrderService;
import com.chavin.vo.MeterialVO;
import com.chavin.vo.OrderVO;
import com.core.util.CustomStringUtils;
import com.core.util.PoiExcelUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RequestMapping(value = "/order")
@Controller
public class OrderAction implements CustomConstant{
	
	@Resource
	private OrderService orderService;
	
	/**
	 * 供应商
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/order")
	public String hzsInfo(HttpServletRequest request, HttpServletResponse response){
		return "purchaseOrderManage/order";
	}
	
	/**
	 * 获取数据表格记录
	 * @param request
	 * @param response
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/getData")
	@ResponseBody
	public DataPageInfo<Order> getData(HttpServletRequest request, HttpServletResponse response, OrderVO orderVo){
		DataPageInfo<Order> dataPageInfo = null;
		try {
			PageHelper.startPage(orderVo.getPage(), orderVo.getRows());
			Order order = new Order();
			if(orderVo !=null){
				order = ChangeVo(orderVo);
		    }
			
			if(!StringUtils.isEmpty(order.getSort()) && !StringUtils.isEmpty(order.getOrder())){
				if(order.getSort().equals("customName")){
					order.setSort("cm.name");
				}
				if(order.getSort().equals("commodityName")){
					order.setSort("cy.name");
				}
				PageHelper.orderBy(CustomStringUtils.camelNameTolineName(order.getSort()) + " " + order.getOrder());
			}
			List<Order> gysList = orderService.findListByEntity(order);
			dataPageInfo = new DataPageInfo<Order>(gysList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dataPageInfo == null)
			dataPageInfo = new DataPageInfo<Order>();
		return dataPageInfo;
	}
	
	/**
	 * 操作数据库记录
	 * @param request
	 * @param response
	 * @param order （接收参数）
	 * @param createTimeStr （创建时间字符串格式）
	 * @param deleteIds （要删除的ids）
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/operData")
	@ResponseBody
	public TransferObj operData(HttpServletRequest request, HttpServletResponse response, OrderVO orderVo, String createTimeStr, String tradeTimeStr, Integer[] deleteIds) throws ParseException{
		TransferObj transferObj ;
		if(StringUtils.isEmpty(orderVo.getOper())){
			return new TransferObj(false, "系统错误");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Order order = ChangeVo(orderVo);
		if(StringUtils.isNotBlank(createTimeStr)){
			try {
				order.setCreateTime(sdf.parse(createTimeStr));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			order.setCreateTime(new Date());
		}
		if(StringUtils.isNotBlank(tradeTimeStr)){
			try {
				order.setTradeTime(sdf.parse(tradeTimeStr));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		
		if("update".equals(orderVo.getOper())){
			String id = request.getParameter("operId");
			if(StringUtils.isBlank(id)){
				return new TransferObj(false, "修改失败，未能正确获取当前修改的行");
			}
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("idParamter", Integer.parseInt(id));
			try {
				int a = orderService.updateByEntity(params,order);
				if(a >= 0) 
					transferObj = new TransferObj(true, "修改成功");
				else
					transferObj = new TransferObj(true, "修改失败");
			} catch (Exception e) {
				e.printStackTrace();
				transferObj = new TransferObj(false, "修改失败，数据库执行修改发生异常");
			}
		}else if("insert".equals(orderVo.getOper())){
			try {
				int a = orderService.insert(order);
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
				order.setIds(Arrays.asList(deleteIds));
				try {
					int a = orderService.deleteByEntity(order);
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
	 * @param order
	 * @param chooseNum
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportExcel")
	public String exportExcel(HttpServletRequest request, HttpServletResponse response, OrderVO orderVo) throws IOException{
		List<Map<String,String>> rows = Lists.newArrayList();  //记录
		try {
			PageHelper.startPage(orderVo.getPage(), orderVo.getRows());
			Order order = new Order();
			if(orderVo !=null){
				order = ChangeVo(orderVo);
		    }
			
			if(!StringUtils.isEmpty(order.getSort()) && !StringUtils.isEmpty(order.getOrder())){
				if(order.getSort().equals("customName")){
					order.setSort("cm.name");
				}
				if(order.getSort().equals("commodityName")){
					order.setSort("cy.name");
				}
				PageHelper.orderBy(CustomStringUtils.camelNameTolineName(order.getSort()) + " " + order.getOrder());
			}
			if(order != null && order.getStartNum() != null && order.getEndNum() != null)
				PageHelper.startPage(order.getStartNum(), order.getEndNum());
			List<Order> orderList = orderService.findListByEntity(order);
			int endSize = orderList.size();
			if(order != null && order.getStartNum() != null && order.getStartNum() <= endSize){
				if(order.getEndNum() == null){
					orderList = orderList.subList(order.getStartNum(), endSize);
				}else if(order.getEndNum() < order.getStartNum()){
					orderList = new ArrayList<Order>();
				}else if(order.getEndNum() <= endSize){
					endSize = order.getEndNum();
					orderList = orderList.subList(order.getStartNum(), endSize);
				}else{
					orderList = orderList.subList(order.getStartNum(), endSize);
				}
			}else{
				orderList = new ArrayList<Order>();
			}
			Map<String,String> rowMap;
			Order entity;
			for(int i = 0 ; i < orderList.size() ; i++){
				entity = orderList.get(i);
				rowMap = Maps.newHashMap();
				rowMap.put("customName", entity.getCustom().getName());
				rowMap.put("commodityName", entity.getCommodity().getName());
				rowMap.put("univalence", entity.getUnivalence() + "");
				rowMap.put("singleGroupNum", entity.getSingleGroupNum() + "");
				rowMap.put("groupNum", entity.getGroupNum() + "");
				rowMap.put("amount", entity.getAmount() + "");
				rowMap.put("totlePrice", entity.getTotlePrice() + "");
				rowMap.put("createTime", entity.getCreateTime() == null ? "" : timesdf.format(entity.getCreateTime()));
				rowMap.put("tradeTime", entity.getTradeTime() == null ? "" : timesdf.format(entity.getTradeTime()));
				rowMap.put("isTradeSuccess", entity.getIsTradeSuccess() == 1 ? "交易成功" : "未交易成功");
				rowMap.put("deleted", entity.getDeleted() == 1 ? "已删除" : "未删除");
				rowMap.put("remark", entity.getRemark());
				rows.add(rowMap);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		Map<String,Integer> columnMap = Maps.newLinkedHashMap();
		columnMap.put("customName", 5000);
		columnMap.put("commodityName", 5000);
		columnMap.put("univalence", 3000);
		columnMap.put("singleGroupNum", 3000);
		columnMap.put("groupNum", 3000);
		columnMap.put("amount", 3000);
		columnMap.put("totlePrice", 3000);
		columnMap.put("createTime", 5000);
		columnMap.put("tradeTime", 5000);
		columnMap.put("isTradeSuccess", 5000);
		columnMap.put("deleted", 5000);
		columnMap.put("remark", 15000);
		
		Map<String,String> columnCHMap = Maps.newHashMap();
		columnCHMap.put("customName", "客户名称");
		columnCHMap.put("commodityName", "产品名称");
		columnCHMap.put("univalence","单价");
		columnCHMap.put("singleGroupNum","单组数量");
		columnCHMap.put("groupNum","组数");
		columnCHMap.put("amount","总数量");
		columnCHMap.put("totlePrice","总价格");
		columnCHMap.put("createTime", "创建时间");
		columnCHMap.put("tradeTime", "交易时间");
		columnCHMap.put("isTradeSuccess", "交易状态");
		columnCHMap.put("deleted", "删除标志");
		columnCHMap.put("remark", "备注信息");
		
		String title = "订单列表";
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
	

	@RequestMapping(value = "/uploadOrder")
	@ResponseBody
	public String uploadOrder(HttpServletRequest request, HttpServletResponse response, @RequestParam("importFile") CommonsMultipartFile[] files) throws IOException {
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
	private Order ChangeVo(OrderVO orderVo) throws ParseException{
		Order order = null;
		if(orderVo !=null){
			order = new Order();
			order.setId(orderVo.getId());
			order.setIsTradeSuccess(orderVo.getIsTradeSuccess());
			order.setDeleted(orderVo.getDeleted());
			order.setUnivalence(orderVo.getUnivalence());
			order.setGroupNum(orderVo.getGroupNum());
			order.setTotlePrice(orderVo.getTotlePrice());
			order.setSingleGroupNum(orderVo.getSingleGroupNum());
			order.setAmount(orderVo.getAmount());
			order.setSpecimenImg(orderVo.getSpecimenImg());
			order.setRemark(orderVo.getRemark());
			order.setOrder(orderVo.getOrder());
			order.setPage(orderVo.getPage());
			order.setRows(orderVo.getRows());
			order.setSort(orderVo.getSort());
			if(orderVo != null && orderVo.getStartNum() != null && orderVo.getEndNum() != null){
				order.setStartNum(orderVo.getStartNum());
				order.setEndNum(orderVo.getEndNum());
			}
			if(StringUtils.isNotBlank(orderVo.getCustomName()) || orderVo.getCustomId() != null){
				Custom custom = new Custom();
				custom.setId(orderVo.getCustomId());
				custom.setName(orderVo.getCustomName());
				order.setCustom(custom);
			}
			if(orderVo.getCommodityName() != null || orderVo.getCommodityId() != null){
				Commodity commodity = new Commodity();
				commodity.setId(orderVo.getCommodityId());
				commodity.setName(orderVo.getCommodityName());
				order.setCommodity(commodity);
			}
			if(StringUtils.isNotBlank(orderVo.getCreateTimestartTime()) && StringUtils.isNotBlank(orderVo.getCreateTimeendTime())){
				order.setCstartTime(timesdf.parse(orderVo.getCreateTimestartTime()));
				order.setCendTime(timesdf.parse(orderVo.getCreateTimeendTime()));
			}
			if(StringUtils.isNotBlank(orderVo.getTradeTimestartTime()) && StringUtils.isNotBlank(orderVo.getTradeTimeendTime())){
				order.setTstartTime(timesdf.parse(orderVo.getTradeTimestartTime()));
				order.setTendTime(timesdf.parse(orderVo.getTradeTimeendTime()));
			}
	   }
		return order;
	} 
	
	
	@RequestMapping(value="/InsertCommodityImg")
	@ResponseBody
	public TransferObj InsertCommodityImg(HttpServletRequest request, @RequestParam("importFile") CommonsMultipartFile file, Integer orderId){
		TransferObj transferObj = new TransferObj();
		transferObj.setSuccess(false);
		if(orderId == null || file == null){
			transferObj.setDetailInfo("获取参数错误");
			return transferObj;
		}
		Order entity = new Order();
		entity.setId(orderId);
		try {
			Order order = orderService.findByEntity(entity);
			if(order != null){
				String projectPath = request.getServletContext().getRealPath("/");
				String newName = order.getCommodity().getName()  + "样品图片" + System.currentTimeMillis() + ".jpg";
				InputStream inputStream = null;
				OutputStream bos = null;
				String img_url = File.separator + "resource" + File.separator + "userimg" + File.separator + "order" + File.separator + newName;
				String fileNameFull = projectPath + "resource" + File.separator + "userimg" + File.separator + "order" + File.separator + newName;
				try {
					inputStream = file.getInputStream();
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
				String specimenImgUrl = (order.getSpecimenImg() == null ? "" : order.getSpecimenImg()) + img_url + ";";
				entity = new Order();
				entity.setSpecimenImg(specimenImgUrl);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("idParamter", orderId);
				try{
					orderService.updateByEntity(map, entity);
					transferObj.setSuccess(true);
					transferObj.setDetailInfo("新增成功");
					transferObj.setObject(img_url);
				}catch (Exception e){
					transferObj.setDetailInfo("数据库更新出错");
					File file2 = new File(fileNameFull);
					if(file2.exists()){
						file2.delete();
					}
				}
			}else{
				transferObj.setDetailInfo("获取订单出错");
			}
		} catch (Exception e) {
			e.printStackTrace();
			transferObj.setDetailInfo("获取订单出错");
		}
		return transferObj;
	}
	
	@RequestMapping(value="/DeleteCommodityImg")
	@ResponseBody
	public TransferObj DeleteCommodityImg(HttpServletRequest request, HttpServletResponse response, Integer orderId, String commodityImgUrl){
		TransferObj transferObj = new TransferObj();
		transferObj.setSuccess(false);
		if(orderId == null || StringUtils.isBlank(commodityImgUrl)){
			transferObj.setDetailInfo("获取参数错误");
			return transferObj;
		}
		Order entity = new Order();
		entity.setId(orderId);
		try {
			Order order = orderService.findByEntity(entity);
			if(order != null){
				String specimenImgUrl = order.getSpecimenImg();		
				if(specimenImgUrl.contains(commodityImgUrl.trim() + ";")){
					specimenImgUrl = specimenImgUrl.replace(commodityImgUrl + ";", "");
				}
				order = new Order();
				order.setSpecimenImg(specimenImgUrl);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("idParamter", orderId);
				orderService.updateByEntity(map, order);
				String fileNameFull = request.getServletContext().getRealPath("/") + commodityImgUrl;
				File file = new File(fileNameFull);
				if(file.exists()){
					file.delete();
				}
				transferObj.setSuccess(true);
				transferObj.setDetailInfo("删除成功");
			}else{
				transferObj.setDetailInfo("获取订单出错");
			}
		} catch (Exception e) {
			e.printStackTrace();
			transferObj.setDetailInfo("获取订单出错");
		}
		return transferObj;
	}
	
}
