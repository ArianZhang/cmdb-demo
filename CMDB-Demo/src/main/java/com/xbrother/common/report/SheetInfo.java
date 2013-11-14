/**
 * 
 */
package com.xbrother.common.report;

import com.xbrother.common.utils.LocaleUtils;

/**
 * @author Jonah.Cui
 * @Date 2013-8-9
 */
public class SheetInfo {
	String fileName;
	String sheetName;
	String title;
	String[] titles;
	String[] fields;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getTitles() {
		return titles;
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	SheetInfo() {

	}

	SheetInfo(String path) {
		fileName = LocaleUtils.getResourceProperty(path, "fileName");
		sheetName = LocaleUtils.getResourceProperty(path, "sheetName");
		title = LocaleUtils.getResourceProperty(path, "title");
		titles = LocaleUtils.getResourceProperty(path, "titles").split(",");
		fields = LocaleUtils.getResourceProperty(path, "fields").split(",");
	}
}
