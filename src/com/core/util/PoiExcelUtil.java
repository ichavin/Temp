package com.core.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * excel工具类
 * 
 * @author chavin
 */
public class PoiExcelUtil {

	private static PoiExcelUtil poiExcelUtil = null;

	private PoiExcelUtil() {

	}

	public static PoiExcelUtil getInstance() {
		if (poiExcelUtil == null)
			poiExcelUtil = new PoiExcelUtil();
		return poiExcelUtil;
	}

	/**
	 * 创建excel
	 * @param rows 记录数
	 * @param columnMap 列（key:width）,需要使用能够带顺序的Map的实现
	 * @param columnCHMap 列中文名（key:CH_Name）
	 * @param title 标题
	 * @return workboob对象
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Workbook createxlsExcel(List<Map<String, String>> rows, Map<String, Integer> columnMap, Map<String, String> columnCHMap, String title) throws FileNotFoundException, IOException {
		// 数据可以没有，列一定要有
		if (MapUtils.isEmpty(columnMap)) {
			return null;
		}
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet();

		// 标题样式
		Font font = workbook.createFont();
		CellStyle titleStyle = workbook.createCellStyle();
		font.setFontHeightInPoints((short) 15);
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		titleStyle.setFont(font);
		titleStyle.setBorderLeft(CellStyle.BORDER_THIN);
		titleStyle.setBorderRight(CellStyle.BORDER_THIN);
		titleStyle.setBorderTop(CellStyle.BORDER_THIN);
		titleStyle.setBorderBottom(CellStyle.BORDER_THIN);
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);	

		// 列头样式
		Font fontColumn = workbook.createFont();
		CellStyle ColumnStyle = workbook.createCellStyle();
		fontColumn.setFontHeightInPoints((short) 10);
		fontColumn.setColor(IndexedColors.BLACK.getIndex());
		fontColumn.setBoldweight(Font.BOLDWEIGHT_BOLD);
		ColumnStyle.setFont(fontColumn);
		ColumnStyle.setBorderLeft(CellStyle.BORDER_THIN);
		ColumnStyle.setBorderRight(CellStyle.BORDER_THIN);
		ColumnStyle.setBorderTop(CellStyle.BORDER_THIN);
		ColumnStyle.setBorderBottom(CellStyle.BORDER_THIN);
		ColumnStyle.setAlignment(CellStyle.ALIGN_CENTER);
		ColumnStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中

		// 内容样式
		Font fontContent = workbook.createFont();
		CellStyle cellStyleContent = workbook.createCellStyle();
		fontContent.setFontHeightInPoints((short) 10);
		fontContent.setColor(IndexedColors.BLACK.getIndex());
		fontContent.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		cellStyleContent.setFont(fontContent);
		cellStyleContent.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleContent.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleContent.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleContent.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyleContent.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyleContent.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
		
		//如果需要的话，可以单独设置一些样式
		CellStyle redfontstyle = workbook.createCellStyle();
		redfontstyle.setLeftBorderColor(HSSFColor.BLACK.index);
		redfontstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		redfontstyle.setRightBorderColor(HSSFColor.BLACK.index);
		redfontstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		redfontstyle.setBottomBorderColor(HSSFColor.BLACK.index);
		redfontstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		redfontstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		redfontstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		redfontstyle.setFillForegroundColor(HSSFColor.CORAL.index);
		redfontstyle.setAlignment(CellStyle.ALIGN_CENTER);
		redfontstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
		
		Row row = null;
		Cell cell = null;
		String cell_value;  //值
		int cell_num;    //标识当前第几列
		for (int i = 0; i < rows.size() + 3; i++) {
			row = sheet.createRow(i); // Row 和 Cell 都是从0开始计数的，0-1行用在了标题显示，2行用在了列头显示
			cell_num = 0;
			row.setHeight((short) (25 * 13));
			for (String column : columnMap.keySet()) {
				cell = row.createCell(cell_num);
				if(i >= 3){
					cell_value = rows.get(i - 3).get(column);
					cell.setCellValue(cell_value);
					cell.setCellStyle(cellStyleContent);
				}else if(i == 2){
					row.setHeight((short) (25 * 15));
					sheet.setColumnWidth(cell_num, columnMap.get(column));
					cell = row.createCell(cell_num);
					cell.setCellValue(columnCHMap.get(column));
					cell.setCellStyle(ColumnStyle);
				}else{
					cell.setCellValue(title);
					cell.setCellStyle(titleStyle);
				}
				cell_num ++;
			}
		}
		
		//创建标题
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0,columnMap.size() - 1));
		
		return workbook;
	}
}
