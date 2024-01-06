package com.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.common.CommonProperties;

public class FileUtility {
	Iterator<List> iter;
	Iterator <String> strIter;
	Calendar cal;
	Date d;
	File fPath, fileName;
	public FileUtility() throws IOException {
		//createDirectoryAndFile();
	}
	
	private void createDirectoryAndFile() throws IOException {
		String currDir;
		this.fPath =  new File(CommonProperties.filePath);
		SimpleDateFormat sdfDT = new SimpleDateFormat("ddMMMYYY");
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMYYY - HHmmss");
		currDir = sdfDT.format(Calendar.getInstance().getTime());
		if(!fPath.exists()) {
			fPath.mkdir();
		}
		fPath = new File(CommonProperties.filePath+""+currDir+"\\");
		if(!fPath.exists()) {
			fPath.mkdir();
		}
		
		this.fileName = new File(CommonProperties.filePath+""+currDir+"\\PNR Input Messages - GDS PNR - "+CommonProperties.gdsPNR+".txt");
		if(CommonProperties.fileNameSet==false) {	
			if(!this.fileName.exists()) {
				this.fileName.createNewFile();
				CommonProperties.fileNameSet=true;
			}
		}
	}
	public void writeTTYToFile(String msgType, List<List> ttyMesgList) throws IOException {		
		if(ttyMesgList!=null && !ttyMesgList.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMMYYY - HHmmss");
			this.createDirectoryAndFile();
			iter = ttyMesgList.iterator();
			FileWriter filWriter = new FileWriter(this.fileName, true);
			d = new Date();
			cal = Calendar.getInstance();
			filWriter.write("***************************************************************************************************************************************\n");
			filWriter.write("************************       "+msgType+" - Created Time - "+sdf.format(d)+"       ***************************\n");
			filWriter.write("***************************************************************************************************************************************\n\n");
			while(iter.hasNext()) {
				strIter = iter.next().iterator();
				while(strIter.hasNext()) {
					filWriter.append(strIter.next().toString()+"\n");
				}
				filWriter.write("\n\n");
			}	
			
			filWriter.flush();
			filWriter.close();
		}
		
	}
	public void writeEdifactToFile(String msgType, HashMap<String, HashMap<String, List<String>>> edifactMesgMap) throws IOException {
		HashMap<String, List<String>> edifactIndMap;
		List<String> edifactMesgList;
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMYYY - HHmmss");
		this.createDirectoryAndFile();
		if(edifactMesgMap!=null && edifactMesgMap.size()>0) {
			for(Map.Entry<String, HashMap<String, List<String>>> mapEntry: edifactMesgMap.entrySet())
			{
				edifactIndMap = mapEntry.getValue();
				if(edifactIndMap!=null && edifactIndMap.size()>0) {
					FileWriter filWriter = new FileWriter(this.fileName, true);
					d = new Date();
					cal = Calendar.getInstance();
					filWriter.write("***************************************************************************************************************************************\n");
					filWriter.write("***********************          "+msgType+" - Created Time - "+sdf.format(d)+"          ****************************\n");
					filWriter.write("***************************************************************************************************************************************\n\n");
					for(Map.Entry<String, List<String>> imEntry: edifactIndMap.entrySet()) {
						edifactMesgList = imEntry.getValue();
						if(edifactMesgList!=null && !edifactMesgList.isEmpty()) {
							strIter = edifactMesgList.iterator();
							while(strIter.hasNext()) {
								filWriter.write(strIter.next().toString()+"\n");						
							}
						}
						filWriter.write("\n\n");	
					}
					
					filWriter.flush();
					filWriter.close();
				}
			}
		
		}
	}
	public void writePNRToFile(String msgType, String pnrResponse) throws IOException {
		String befStr="*****************************", aftStr="********************************";
		int msgLength;
		if(pnrResponse!=null && pnrResponse.length()>0) {
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMMYYY - HHmmss");
			this.createDirectoryAndFile();
			
			//iter = ttyMesgList.iterator();
			FileWriter filWriter = new FileWriter(this.fileName, true);
			d = new Date();
			cal = Calendar.getInstance();
			if(msgType.trim().length()>24) {
				msgLength = msgType.trim().length();
				while (msgLength>24) {
					befStr=befStr.substring(1);
					aftStr = aftStr.substring(1);
					msgLength= msgLength-2;
				}
			}
			filWriter.write("***************************************************************************************************************************************\n");
			filWriter.write(befStr+"       "+msgType+" - Created Time - "+sdf.format(d)+"       "+aftStr+"\n");
			filWriter.write("***************************************************************************************************************************************\n\n");
			/*while(iter.hasNext()) {
				strIter = iter.next().iterator();
				while(strIter.hasNext()) {
					filWriter.append(strIter.next().toString()+"\n");
				}
				filWriter.write("\n\n");
			}*/	
			filWriter.write("\n");
			filWriter.write(pnrResponse);
			filWriter.write("\n\n");
			filWriter.flush();
			filWriter.close();
		}
		
	}
	
}
