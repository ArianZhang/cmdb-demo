/**
 * 
 */
package com.xbrother.common.report;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xbrother.common.utils.LocaleUtils;

/**
 * @author Jonah.Cui
 * @Date 2013-8-9
 */
public abstract class ExcelReport {
	private final static Logger LOGGER = LoggerFactory.getLogger(ExcelReport.class);

	// protected Workbook wb;
	// protected Sheet sheet;
	protected Map<String, CellStyle> styles;
	protected SheetInfo sheetInfo;

	/**
	 * 生成xls
	 */
	public Workbook createHSSFWorkbook(Class<?> clazz, List<?> datas) {
		LOGGER.debug("execute createHSSFWorkbook ...");
		Workbook wb = new HSSFWorkbook();
		String path = getPath(clazz);
		sheetInfo = new SheetInfo(path);
		createStyles(wb);
		if (sheetInfo != null) {
			createStyles(wb);
			Sheet sheet = createSheet(wb, sheetInfo);
			createNormalTitle(sheet, sheetInfo.title);
			createNormalHeader(sheet, sheetInfo.titles);
			cteateNormalContent(sheet, sheetInfo.fields, datas);
		}
		return wb;
	}

	protected abstract String getPath(Class<?> clazz);

	/**
	 * 生成xlsx
	 */
	public Workbook createXSSFWorkbook(Class<?> clazz, List<Object> datas) {
		LOGGER.debug("execute createXSSFWorkbook ...");
		Workbook wb = new XSSFWorkbook();
		String path = getPath(clazz);
		SheetInfo sheetInfo = new SheetInfo(path);
		if (sheetInfo != null) {
			createStyles(wb);
			Sheet sheet = createSheet(wb, sheetInfo);
			createNormalTitle(sheet, sheetInfo.title);
			createNormalHeader(sheet, sheetInfo.titles);
			cteateNormalContent(sheet, sheetInfo.fields, datas);
		}
		return wb;
	}

	private Sheet createSheet(Workbook wb, SheetInfo sheetInfo) {
		Sheet sheet = wb.createSheet(sheetInfo.sheetName);
		return sheet;
	}

	/**
	 * 创建报表标题行
	 * 
	 * @param titleString
	 */
	protected abstract void createNormalTitle(Sheet sheet, String title);

	/**
	 * 创建报表头行
	 * 
	 * @param titles
	 */
	protected abstract void createNormalHeader(Sheet sheet, String[] titles);

	/**
	 * 创建报表数据内容行
	 * 
	 * @param sheet
	 * @param datas
	 */
	protected abstract void cteateNormalContent(Sheet sheet, String[] fields, List<?> datas);

	
	public abstract OutputStream writeStream(Workbook wb, OutputStream os);
	/**
	 * create a library of cell styles
	 */
	protected Map<String, CellStyle> createStyles(Workbook wb) {
		styles = new HashMap<String, CellStyle>();
		DataFormat df = wb.createDataFormat();

		CellStyle style;
		Font titleFont = wb.createFont();
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(titleFont);
		styles.put("title", style);

		Font headerFont = wb.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(headerFont);
		style.setDataFormat(df.getFormat("d-mmm"));
		styles.put("header", style);

		Font font1 = wb.createFont();
		font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setFont(font1);
		styles.put("data", style);

		return styles;
	}

	protected CellStyle createBorderedStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return style;
	}
	
	public String getFileName() {
		return sheetInfo.getFileName();
	}
}
