package com.utilities;


import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import com.common.CommonProperties;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ExcelUtility {
	Iterator<List> iter;
	Iterator <String> strIter;
	Calendar cal;
	Date d;
	File fPath, fileName;
	XSSFWorkbook workbook;
	XSSFSheet workingSheet = null;
	private boolean fileCreated = false;
	
	
	public ExcelUtility() {
		fileCreated = false;
	}
   
    
    private void createDirectoryAndFile() throws IOException {
		String currDir;
		this.fPath =  new File(CommonProperties.autoTDFilePath);
		SimpleDateFormat sdfDT = new SimpleDateFormat("ddMMMYYY");
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMYYY - HHmmss");
		currDir = sdfDT.format(Calendar.getInstance().getTime());
		if(!fPath.exists()) {
			fPath.mkdir();
		}
		fPath = new File(CommonProperties.autoTDFilePath+""+currDir+"\\");
		if(!fPath.exists()) {
			fPath.mkdir();
		}
		
		
		if(fileCreated==false) {
			this.fileName = new File(CommonProperties.autoTDFilePath+""+currDir+"\\"+CommonProperties.autoFileDirectory+""+sdf.format(Calendar.getInstance().getTime())+".xlsx");
			if(!this.fileName.exists()) {
				workbook = new XSSFWorkbook();
	            FileOutputStream fileOut = new FileOutputStream(this.fileName);
	            workbook.write(fileOut);
	            fileCreated=true;
			}
		}
	}
    public void createScenarioHeaders(HashMap<String, String> tdSheetHeaderMap) throws IOException {
    	int wbSheetCount, rowCnt=0, columnCnt;
    	boolean SheetExists=false, headerExists=false;
    	String sheetName="";
    	try {
    		if(tdSheetHeaderMap!=null && tdSheetHeaderMap.size()>0) {    			
    			sheetName = tdSheetHeaderMap.get("sheetName");
    			
    			this.createDirectoryAndFile();
    			File fl = this.fileName;
    			if(fl.exists()){
    	            FileInputStream fileInput = new FileInputStream(this.fileName);
    	            workbook = new XSSFWorkbook(fileInput);
    	            wbSheetCount = workbook.getNumberOfSheets();
    	            for (int i=0; i< wbSheetCount; i++) {
    	                //System.out.println(workbook.getSheetName(i));
    	                //rowCnt = 0;
    	                if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
    	                    SheetExists = true;
    	                    workingSheet = workbook.getSheetAt(i);
    	                    Header hdr = workingSheet.getHeader();
    	                    rowCnt = workingSheet.getPhysicalNumberOfRows();
    	                    if (rowCnt > 0) {
    	                    	headerExists = this.verifyHeaderExists(workingSheet, tdSheetHeaderMap);
    	                    }else{
    	                        headerExists = false;    	                        
    	                    }
    	                }
    	            }
    	            if(!SheetExists){
    	                workingSheet = workbook.createSheet(sheetName);
    	                headerExists = false;    	            
    	            }
    	            if(!headerExists){
        	            this.createHeaders(workingSheet, tdSheetHeaderMap);
        	            rowCnt = 1;
        	        }
    	            FileOutputStream fileOut = new FileOutputStream(this.fileName);
    	            workbook.write(fileOut);
    			}
    		}
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    public File getFileName() {
		return fileName;
	}
	public void setFileName(File fileName) {
		this.fileName = fileName;
	}
	private boolean verifyHeaderExists(XSSFSheet workingSheet, HashMap<String, String> headerMap) {
    	boolean headerExists = false;
    	String columnName="", tblHdrValue="";
    	
    	try {    
    			while(true) {
		    		tblHdrValue = headerMap.get("headerOne");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(0).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerTwo");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(1).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerThree");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(2).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerFour");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(3).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}
		    		tblHdrValue = headerMap.get("headerFive");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(4).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerSix");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(5).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerSeven");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(6).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerEight");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(7).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerNine");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(8).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerTen");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(9).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerEleven");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(10).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerTwelve");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(11).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerThirteen");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(12).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerFourteen");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(13).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerFifteen");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(14).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerSixteen");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(15).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerSeventeen");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(16).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerEighteen");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(17).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerNineteen");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(18).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerTwenty");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(19).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerTwentyOne");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(20).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerTwentyTwo");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(21).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerTwentyThree");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(22).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerTwentyFour");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(23).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerTwentyFive");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(24).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerTwentySix");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(25).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerTwentySeven");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(26).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerTwentyEight");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(27).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerTwentyNine");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(28).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerThirty");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(29).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerThirtyOne");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(30).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerThirtyTwo");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(31).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerThirtyThree");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(32).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		tblHdrValue = headerMap.get("headerThirtyFour");
		    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
		    			columnName = workingSheet.getRow(0).getCell(33).getStringCellValue();
		    			if(tblHdrValue.trim().equalsIgnoreCase(columnName.trim())) {
		    				headerExists = true;
		    			}else {
		    				headerExists = false;
		    				break;
		    			}
		    		}else {
		    			break;
		    		}
		    		
		    		
		    		break;
    			}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return headerExists;
    }
    private void createHeaders(XSSFSheet workingSheet, HashMap<String, String> headerMap) {
    	boolean headerExists = false;
    	String tblHdrValue = "";
    	int cellNo = 0;
    	Cell cell;
    	try {
	    	Row rw = workingSheet.createRow(0);	    	
	    	while(true) {
	    		tblHdrValue = headerMap.get("headerOne");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			//this.setCellStyle(rw.getCell(cellNo));
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		tblHdrValue = headerMap.get("headerTwo");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			this.setCellStyle(rw.getCell(cellNo));
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		tblHdrValue = headerMap.get("headerThree");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		tblHdrValue = headerMap.get("headerFour");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		tblHdrValue = headerMap.get("headerFive");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		tblHdrValue = headerMap.get("headerSix");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		tblHdrValue = headerMap.get("headerSeven");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		tblHdrValue = headerMap.get("headerEight");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		tblHdrValue = headerMap.get("headerNine");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		tblHdrValue = headerMap.get("headerTen");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		tblHdrValue = headerMap.get("headerEleven");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		tblHdrValue = headerMap.get("headerTwelve");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		tblHdrValue = headerMap.get("headerThirteen");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}	
	    		
	    		tblHdrValue = headerMap.get("headerFourteen");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerFifteen");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerSixteen");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerSeventeen");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerEighteen");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerNineteen");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerTwenty");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerTwentyOne");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerTwentyTwo");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerTwentyThree");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerTwentyFour");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerTwentyFive");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerTwentySix");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerTwentySeven");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerTwentyEight");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerTwentyNine");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerThirty");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerThirtyOne");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerThirtyTwo");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerThirtyThree");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    		
	    		tblHdrValue = headerMap.get("headerThirtyFour");
	    		if(tblHdrValue!=null && !tblHdrValue.equalsIgnoreCase("NA")) {
	    			rw.createCell(cellNo).setCellValue(tblHdrValue);
	    			cellNo++;
	    		}else {
	    			break;
	    		}
	    			    		
	    		break;
	    	}
	    	
	    	//Setting cell backgroundcolour
	    	int i=0;
	    	while (i< cellNo) {
	    		this.setCellStyle(rw.getCell(i));
	    		workingSheet.autoSizeColumn(cellNo);
	    		i++;
	    	}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    public void addTestDataToExcel(TreeMap<String, HashMap<String, String>> scenariosTestData) {
    		String sheetName, scenarioName;
    		HashMap<String, String> individualScenTDMap;
    		int wbSheetCount, rowCnt=0, columnCnt;
        	boolean SheetExists=false, headerExists=false;
    	try {
    		if(this.fileCreated) {
				if(scenariosTestData!=null && !scenariosTestData.isEmpty()) {
					for (Map.Entry<String, HashMap<String, String>> entry : scenariosTestData.entrySet()) {
						individualScenTDMap = entry.getValue();
						sheetName = individualScenTDMap.get("sheetName");
						scenarioName = individualScenTDMap.get("scenario");
						File fl = this.fileName;    		    			
			    	    FileInputStream fileInput = new FileInputStream(this.fileName);
	    	            workbook = new XSSFWorkbook(fileInput);
	    	            wbSheetCount = workbook.getNumberOfSheets();
	    	            for (int i=0; i< wbSheetCount; i++) {	    	                
	    	                if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
	    	                    SheetExists = true;
	    	                    workingSheet = workbook.getSheetAt(i);	    	                    
	    	                    rowCnt = workingSheet.getPhysicalNumberOfRows();
	    	                    this.addInidvidualScenarioTD(workingSheet, individualScenTDMap, rowCnt);
	    	                    break;
	    	                }
	    	            }
	    	            this.autoSizeColumns();
	    	            FileOutputStream fileOut = new FileOutputStream(this.fileName);
			            workbook.write(fileOut);
	    			}
				}
    		}
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    
    }
    private void addInidvidualScenarioTD(XSSFSheet workingSheet, HashMap<String, String> individualScenTDMap, int roWCnt) {
    	int cellNo = 0;
    	try {
	    	if(individualScenTDMap!=null && !individualScenTDMap.isEmpty()) {
	    		Row hdRW = workingSheet.getRow(0);
	    		Cell hdrCl;
	    		Row rw = workingSheet.createRow(roWCnt);
	    		rw.createCell(cellNo).setCellValue(individualScenTDMap.get("scenario"));
	    		cellNo++;
	    		
	    		while(true) {
		    		if(!individualScenTDMap.get("valueOne").trim().equalsIgnoreCase("NA")) {	    				
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueOne")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueOne"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			if(!individualScenTDMap.get("valueTwo").trim().equalsIgnoreCase("NA")) {	    				
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueTwo")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueTwo"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			if(!individualScenTDMap.get("valueThree").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueThree")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueThree"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			if(!individualScenTDMap.get("valueFour").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueFour")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueFour"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			if(!individualScenTDMap.get("valueFive").trim().equalsIgnoreCase("NA")) {
		    			rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueFive"));
		    			//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueFive")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueFive"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			if(!individualScenTDMap.get("valueSix").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueSix")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueSix"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			if(!individualScenTDMap.get("valueSeven").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					System.out.print(individualScenTDMap.get("scenario"));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueSeven")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueSeven"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}	    			
	    			
	    			if(!individualScenTDMap.get("valueEight").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueEight")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueEight"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			if(!individualScenTDMap.get("valueNine").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueNine")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueNine"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			if(!individualScenTDMap.get("valueTen").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueTen")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueTen"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			if(!individualScenTDMap.get("valueEleven").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueEleven")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueEleven"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}	    			
	    			if(!individualScenTDMap.get("valueTwelve").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueTwelve")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueTwelve"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueThirteen").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueThirteen")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueThirteen"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueFourTeen").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueFourTeen")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueFourTeen"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			if(!individualScenTDMap.get("valueFifteen").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueFifteen")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueFifteen"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueSixteen").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueSixteen")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueSixteen"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueSeventeen").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueSeventeen")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueSeventeen"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueEighteen").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueEighteen")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueEighteen"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueNineteen").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueNineteen")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueNineteen"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueTwenty").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueTwenty")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueTwenty"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueTwentyone").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueTwentyone")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueTwentyone"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueTwentytwo").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueTwentytwo")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueTwentytwo"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueTwentythree").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueTwentythree")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueTwentythree"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueTwentyfour").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueTwentyfour")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueTwentyfour"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueTwentyfive").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueTwentyfive")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueTwentyfive"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueTwentysix").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueTwentysix")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueTwentysix"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueTwentyseven").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueTwentyseven")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueTwentyseven"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueTwentyeight").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueTwentyeight")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueTwentyeight"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueTwentynine").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueTwentynine")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueTwentynine"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueThirty").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueThirty")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueThirty"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueThirtyone").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueThirtyone")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueThirtyone"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueThirtytwo").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueThirtytwo")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueThirtytwo"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueThirtythree").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
		    			if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueThirtythree")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueThirtythree"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueThirtyFour").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit")|| hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueThirtyFour")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueThirtyFour"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    			
	    			if(!individualScenTDMap.get("valueThirtyFive").trim().equalsIgnoreCase("NA")) {
	    				//setting number format for datefield in excel
		    			hdrCl = hdRW.getCell(cellNo);
	    				if(hdrCl!=null && hdrCl.getStringCellValue().length()>0 && (hdrCl.getStringCellValue().equalsIgnoreCase("DepartureDateTime") || hdrCl.getStringCellValue().equalsIgnoreCase("TicketTimeLimit") || hdrCl.getStringCellValue().equalsIgnoreCase("ArrivalDateTime"))) {
	    					//this.setCellNumericFormat(rw.getCell(cellNo));
	    					rw.createCell(cellNo).setCellValue(Integer.parseInt(individualScenTDMap.get("valueThirtyFive")));
	    				}else {
	    					rw.createCell(cellNo).setCellValue(individualScenTDMap.get("valueThirtyFive"));
	    				}
			    		cellNo++;
		    		}else {
		    			break;
		    		}
	    				    			
	    			break;
	    		}
	    		
	    	}
    	
    	}catch(Exception e) {
    		System.out.println("Scenario: "+individualScenTDMap.get("scenario"));
    		e.printStackTrace();
    	}
    }
    public void setCellStyle(Cell cell) {
    	CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillBackgroundColor(IndexedColors.YELLOW.index);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());

        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cell.setCellStyle(cellStyle);

    }
    
    public void setCellNumericFormat(Cell cell) {
    	CellStyle style = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("Number"));
        cell.setCellStyle(style);
        

    }
    public void autoSizeColumns() {
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            XSSFSheet sheet = workbook.getSheetAt(i);
            if (sheet.getPhysicalNumberOfRows() > 0) {
                Row row = sheet.getRow(sheet.getFirstRowNum());
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    sheet.autoSizeColumn(columnIndex);
                }
            }
        }
    }
}

