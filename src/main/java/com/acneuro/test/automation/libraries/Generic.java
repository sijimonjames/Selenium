package com.acneuro.test.automation.libraries;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.Reporter;

public class Generic {

	// generic Library contains the re-usable methods which can use for other
	// projects as well.
	// method to return number of rows specified in the excel sheet

	public static int getRowCount(String excel_path, String sheetName) {
		try {
			FileInputStream fis = new FileInputStream(excel_path);
			Workbook wb = WorkbookFactory.create(fis);
			return wb.getSheet(sheetName).getLastRowNum();
		} catch (Exception e) {
			return -1;
		}
	}

	// Method to Read a cell value
	public static String getCellValue(String excel_path, String sheet_name, int row, int column) {
		try {
			// String workingDir = System.getProperty("user.dir");
			// System.out.println("Current working directory : " + workingDir);
			FileInputStream fis = new FileInputStream(excel_path);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet s = wb.getSheet(sheet_name);
			return s.getRow(row).getCell(column).getStringCellValue();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void updateCellValue(String excel_path, String sheet_name, int row, int column, String cellValue) {
		try {
			FileInputStream fis = new FileInputStream(excel_path);
			Workbook wb = WorkbookFactory.create(fis);
			org.apache.poi.ss.usermodel.Sheet s = wb.getSheet(sheet_name);
			Row row1 = s.getRow(row);
			Cell cell = row1.createCell(column);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(cellValue);
			FileOutputStream fos = new FileOutputStream(excel_path);
			wb.write(fos);
			fos.close();
			System.out.println("Excel sheet is updated");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String urlSelection(String countryCodeOrWorkerURL) {
		String configFile = Constant.DEFAULT_TESTDATA_LOCATION + "/" + Constant.configFileName;
		String url = "";
		if (countryCodeOrWorkerURL.equalsIgnoreCase("UK")) {
			url = Generic.getCellValue(configFile, "Browser_selection", 1, 1);
		}
		if (countryCodeOrWorkerURL.equalsIgnoreCase("FR_English")) {
			url = Generic.getCellValue(configFile, "Browser_selection", 3, 1);
		}
		if (countryCodeOrWorkerURL.equalsIgnoreCase("FR_French")) {
			url = Generic.getCellValue(configFile, "Browser_selection", 5, 1);
		}
		System.out.println("URL: " + url);
		Reporter.log(countryCodeOrWorkerURL + " URL: " + url, true);
		return url;
	}
}