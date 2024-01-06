package com.core;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.openqa.selenium.WebDriver;

import com.common.CommonProperties;
import com.model.TableDataService;
import com.pages.HomePage;
import com.pages.TerminalEmulation;

public class FlightSeatCollector {
	WebDriver driver;
	private TableDataService tableDataService;
	private StringTokenizer stringTokenizer;
	Connection connection;		
	private HashMap<String, HashMap<String, String>> returnFlightDataMap, inputDtlsMapFrFltData;
	private TerminalEmulation terminalEmulation;
	HashMap<String, HashMap<String, String>> routeMap;
	
	public FlightSeatCollector(Connection connection) {
		tableDataService = new TableDataService(connection);
		
	}
	public List<String> getAvailableSeats(String fltNo, String orgination, String destination, String jrnyDte, String cos){
		List <String> seatList = new ArrayList();
		String seatCmd, response;
		try {
			LoginIShares loginIShares = new LoginIShares();
			driver = loginIShares.launchISHARESApplication(CommonProperties.envURL, true);
			loginIShares.logintoIShares(CommonProperties.logID, CommonProperties.passWord);
			HomePage homePage = new HomePage(driver);
			if(homePage.verifyHomePagedisplayed()) {
				homePage.selectTerminalEmulation();
				this.terminalEmulation = new TerminalEmulation(driver);
				terminalEmulation.runSingleLineEntry("LOGI CMRE");
				terminalEmulation.runSingleLineEntry("BSIB");
				seatCmd = "6:*"+fltNo+"/"+jrnyDte+orgination+"."+cos;
				terminalEmulation.runSingleLineEntry("6:CMV");
				terminalEmulation.runSingleLineEntry(seatCmd);
				response = terminalEmulation.getResponse();
				if(response.toUpperCase().contains("INVLD FLT NUMBER")) {
					seatList=null;
				}else {
					seatList = this.captureAvailableSeats(response, cos);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			driver.close();
		}
		
		return seatList;
	}
	
	public List<String> captureAvailableSeats(String response, String cos){
		List <String> seatList = new ArrayList();
		StringTokenizer strTknzr, recTknzr;
		String seatDisplayRecord, seatRowNum, seatCode, tempStr;
		int nextIndex, i=0, j=0;
		HashMap<String, String> seatColumn;
		try {
			if(terminalEmulation!=null) {
				strTknzr =  new StringTokenizer(response, "\n");
				seatColumn = new HashMap<String, String>();
				while (strTknzr.hasMoreTokens()) {
					seatDisplayRecord = strTknzr.nextToken().trim();
					if(seatDisplayRecord.startsWith(cos.toUpperCase()+"   ")) {
						nextIndex = (seatDisplayRecord.substring(2).indexOf(cos.toUpperCase()))+2;
						recTknzr = new StringTokenizer((seatDisplayRecord.substring(1, nextIndex-1))," ");
						while(recTknzr!=null && recTknzr.hasMoreTokens()) {
							seatColumn.put(String.valueOf(i+1), recTknzr.nextToken());
							i++;
						}
					}else if(seatDisplayRecord.startsWith("0") || seatDisplayRecord.startsWith("1") || seatDisplayRecord.startsWith("2") || seatDisplayRecord.startsWith("3")) {
						seatDisplayRecord = seatDisplayRecord.replace("W N", "");
						seatDisplayRecord = seatDisplayRecord.replace("E N", "");
						seatDisplayRecord = seatDisplayRecord.replace("N", "");
						seatRowNum = seatDisplayRecord.trim().substring(0, 2);
						if(seatRowNum.startsWith("0")) {
							seatRowNum.replace("0","");
						}
						recTknzr = new StringTokenizer((seatDisplayRecord.trim().substring(2, seatDisplayRecord.length()-2))," ");
						j=1;
						while(recTknzr!=null && recTknzr.hasMoreTokens()) {
							tempStr = recTknzr.nextToken();
							if(tempStr.equalsIgnoreCase("V") || tempStr.equalsIgnoreCase("E") || tempStr.equalsIgnoreCase("T") || tempStr.equalsIgnoreCase("@") || tempStr.equalsIgnoreCase("Q")) {
								seatCode=seatRowNum+seatColumn.get(String.valueOf(j));
								seatList.add(seatCode);
							}							
							j++;
						}
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return seatList;
	}
}
