package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static XSSFCell cell;
	public static XSSFRow row;
	public static String[][] getExcelData(String fileName, String SheetName) {
		String[][] arrayExcelData = null;
		try {
			int colCount, rowCount, i = 0, j = 0, count = 0;
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + fileName);
			XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
			XSSFSheet mySheet = myWorkBook.getSheet(SheetName);
			XSSFRow row = null;
			row = mySheet.getRow(0);
			colCount = row.getLastCellNum();
			rowCount = mySheet.getLastRowNum() + 1;
			Iterator<Row> iterator = mySheet.iterator();
			arrayExcelData = new String[rowCount - 1][colCount];
			SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yy");
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				if (count == 0) {
					count++;
				} else {
					j = 0;
					while (cellIterator.hasNext()) {
						Cell currentCell = cellIterator.next();
						if (currentCell.getCellTypeEnum() == CellType.STRING) {
							arrayExcelData[i][j] = currentCell.getStringCellValue();
						} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
							if (currentCell.getCellStyle().getDataFormatString().equalsIgnoreCase("General")) {
								arrayExcelData[i][j] = String.valueOf(currentCell.getNumericCellValue());
								arrayExcelData[i][j] = arrayExcelData[i][j].indexOf(".") < 0 ? arrayExcelData[i][j]
										: arrayExcelData[i][j].replaceAll("0*$", "").replaceAll("\\.$", "");
							} else {
								Date d = currentCell.getDateCellValue();
								arrayExcelData[i][j] = fmt.format(d);
							}
						}
						j++;
					}
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayExcelData;
	}
	
	/**
	 * 	For this method, In Excel make first row empty
	 * @param fileName
	 * @param SheetName
	 * @param rowData
	 * @return
	 */
	public static Map<String, String> getRowFromRowNumber(String fileName, String SheetName, String rowData) {
		String[][] excelData = ExcelUtils.getExcelData(fileName, SheetName);
		String[] column= excelData[0];
		int colSize = column.length;
		int size = excelData.length;
		Map<String, String> map = new HashMap<String, String>();
		for (int i=0;i<size;i++){
			if(excelData[i][0].equals(rowData)) {
				for(int j=0;j<colSize;j++) {
					map.put(excelData[0][j], excelData[i][j]); 
				}
				break;
			}
		}
		return map;
	}
	
	/**
	 * @param fileName
	 * @param sheetName
	 * @param RowNum
	 * @param ColNum
	 * @param Results
	 *            set @param Results value into excel cell
	 */
	public static void setCellData(String fileName, String sheetName, int RowNum, int ColNum, String Results) {
		File file = new File(fileName);
		try {
			int rows = getRowCount(fileName, sheetName);
			if (rows < RowNum)
				cell = sheet.createRow(RowNum).createCell(ColNum);
			else
				cell = sheet.getRow(RowNum).createCell(ColNum);
			cell.setCellValue(Results);
			FileOutputStream Outputstream = new FileOutputStream(file.getAbsolutePath());
			workbook.write(Outputstream);
			Outputstream.flush();
			Outputstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setCellDataWithFormate(String fileName, String sheetName, int RowNum, int ColNum,
			String Results) {
		File file = new File(fileName);
		try {
			int rows = getRowCount(fileName, sheetName);
			if (rows < RowNum)
				cell = sheet.createRow(RowNum).createCell(ColNum);
			else
				cell = sheet.getRow(RowNum).createCell(ColNum);
			XSSFCellStyle style = workbook.createCellStyle();
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setAlignment(HorizontalAlignment.LEFT);
			style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.TOP);
			if (Results.length() > 50) {
				sheet.setColumnWidth(ColNum, 10000);
				style.setWrapText(true);
			}
			// style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cell.setCellStyle(style);
			cell.setCellValue(Results);
			FileOutputStream Outputstream = new FileOutputStream(file.getAbsolutePath());
			workbook.write(Outputstream);
			Outputstream.flush();
			Outputstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param fileName
	 * @param sheetName
	 * @return no of rows in excel
	 */
	public static int getRowCount(String fileName, String sheetName) {
		try {
			setExcelFile(fileName, sheetName);
			int RowCount = sheet.getLastRowNum();
			return RowCount;
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * @param filepath
	 * @param sheetName
	 *            to set excel file for perform read/write operations
	 */
	public static void setExcelFile(String fileName, String sheetName) {
		File file = new File(System.getProperty("user.dir") + fileName);
		try {
			FileInputStream ExcelFile = new FileInputStream(file.getAbsolutePath());
			workbook = new XSSFWorkbook(ExcelFile);
			sheet = workbook.getSheet(sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
