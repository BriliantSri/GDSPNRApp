package com.core;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.openqa.selenium.WebDriver;

import com.common.CommonProperties;
import com.model.TableDataService;
import com.pages.HomePage;
import com.pages.TerminalEmulation;

public class FlightOperations {
	public TerminalEmulation getTerminalEmulation() {
		return terminalEmulation;
	}

	public void setTerminalEmulation(TerminalEmulation terminalEmulation) {
		this.terminalEmulation = terminalEmulation;
	}

	WebDriver driver;	
	boolean driverClosed=true;
	private TerminalEmulation terminalEmulation;	
	public FlightOperations() {
		
	}
	
	public void initDriverNavTerminalEmulation() {
		try {
		LoginIShares loginIShares = new LoginIShares();
		driver = loginIShares.launchISHARESApplication(CommonProperties.envURL, false);		
		loginIShares.logintoIShares(CommonProperties.logID, CommonProperties.passWord);
		driverClosed = false;
		
		HomePage homePage = new HomePage(driver);		
		if(homePage.verifyHomePagedisplayed()) {
			homePage.selectTerminalEmulation();
			this.terminalEmulation = new TerminalEmulation(driver);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void verifyAndAssignShip(String fltNo, String jrnyDate, String origination) {
		String shrEntry, response, shipNumber;
		StringTokenizer strTknzr;
		try {	
			this.initDriverNavTerminalEmulation();
			if(this.terminalEmulation!=null) {				
				terminalEmulation.runSingleLineEntry("LOGI CMRE");
				terminalEmulation.runSingleLineEntry("BSIB");
				if(!isShipAssigned(fltNo, jrnyDate)) {
					shipNumber = this.getShipNumber(fltNo, jrnyDate, origination);
					if(shipNumber.length()>0) {
						while(fltNo.length()<4) {
							fltNo="0"+fltNo;
						}
						shrEntry = "6:CA"+jrnyDate+"\n"+fltNo+origination+shipNumber;
						terminalEmulation.runMultiLineEntries(shrEntry);
						
						shrEntry = "6:CO"+fltNo+"/"+jrnyDate+origination;
						terminalEmulation.runSingleLineEntry(shrEntry);
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			driver.close();
			driverClosed=true;
		}
		
	}
	public boolean isShipAssigned(String fltNo, String jrnyDate) {
		boolean isShipAssiged=false;
		String flifoCMD, response, resRec;
		StringTokenizer strTknzr;
		try {	
			//Initialize driver if TerminalEmulation is not set
			if(driverClosed) {
				this.initDriverNavTerminalEmulation();
				driverClosed = false;
			}
			
			if(this.terminalEmulation!=null) {				
				flifoCMD = "2"+fltNo+"/"+jrnyDate;
				terminalEmulation.runSingleLineEntry(flifoCMD);
				response = terminalEmulation.getResponse();
				if (response.contains("FLT NOOP FOR FLT/DATE")) {
					
				}else {
					strTknzr =  new StringTokenizer(response, "\n");
					while (strTknzr.hasMoreTokens()) {
						resRec = strTknzr.nextToken().trim();
						if(resRec.startsWith("SKED") && resRec.contains("SHIP")) {
							isShipAssiged=true;
							break;
						}
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return isShipAssiged;
	}
	public String getShipNumber(String fltNo, String jrnyDate, String origination) {
		
		String shrEntry, response, resRec, eqpNum, shipNumber="";
		StringTokenizer strTknzr;
		try {	
			//Initialize driver if TerminalEmulation is not set
			if(driverClosed) {
				this.initDriverNavTerminalEmulation();
				driverClosed = false;
			}
			
			if(this.terminalEmulation!=null) {				
				eqpNum =  this.getEQPNumber(fltNo, jrnyDate, origination);
				if(eqpNum.length()>0) {
					shrEntry = "6:I*F"+eqpNum;
					terminalEmulation.runSingleLineEntry(shrEntry);
					response = terminalEmulation.getResponse();
					if(!response.contains("ITEM NOT IN TBL 7EE")) {
						strTknzr =  new StringTokenizer(response, "\n");
						while (strTknzr.hasMoreTokens()) {
							resRec = strTknzr.nextToken();
							if(resRec.trim().startsWith(eqpNum)) {
								shipNumber = resRec.substring(36, 40);
								break;
							}
						}
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return shipNumber;
		
	}
	private String getEQPNumber(String fltNo, String jrnyDate, String origination) {
		
		String shrEntry, response, resRec, eqpNum="";
		StringTokenizer strTknzr;
		try {	
			//Initialize driver if TerminalEmulation is not set
			if(driverClosed) {
				this.initDriverNavTerminalEmulation();
				driverClosed = false;
			}
			
			
			if(this.terminalEmulation!=null) {
				shrEntry = "6:*A"+fltNo+"/"+jrnyDate+origination;
				terminalEmulation.runSingleLineEntry(shrEntry);
				response = terminalEmulation.getResponse();
				if (response.contains("INVLD FLT NUMBER/DATE")) {
					
				}else {
					strTknzr =  new StringTokenizer(response, "\n");
					while (strTknzr.hasMoreTokens()) {
						resRec = strTknzr.nextToken();
						if(resRec.trim().startsWith(fltNo) && resRec.contains(origination)) {
							eqpNum = resRec.substring(6, 9);
							break;
						}
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return eqpNum;
	}
	
	public boolean isDriverClosed() {
		return driverClosed;
	}

	public void setDriverClosed(boolean driverClosed) {
		this.driverClosed = driverClosed;
	}

	public String isValidPastFlight(String fltNo, String org) {		
		String currDate, shrEntry="", response ="", dateNumber=null;
		int i;
		SimpleDateFormat sdfDT = new SimpleDateFormat("ddMMM");
		Calendar c;
		boolean flightIdentified = false;
		currDate = sdfDT.format(Calendar.getInstance().getTime());		
		try {
			
			LoginIShares loginIShares = new LoginIShares();
			driver = loginIShares.launchISHARESApplication(CommonProperties.envURL, true);
			loginIShares.logintoIShares(CommonProperties.logID, CommonProperties.passWord);
			HomePage homePage = new HomePage(driver);
			if(homePage.verifyHomePagedisplayed()) {
				homePage.selectTerminalEmulation();
				this.terminalEmulation = new TerminalEmulation(driver);
				i=-1;
				while (i>-6) {
					c = Calendar.getInstance();
					c.add(Calendar.DAY_OF_MONTH, i);
					currDate = sdfDT.format(c.getTime());					
					terminalEmulation.runSingleLineEntry("LOGI CMRE");
					terminalEmulation.runSingleLineEntry("BSIB");
					shrEntry="6:LD"+fltNo.trim()+"/"+currDate+org+"-All";
					terminalEmulation.runSingleLineEntry(shrEntry);
					response = terminalEmulation.getResponse();
					if(!response.toUpperCase().contains("INVLD FLT NUMBER") && response.toUpperCase().startsWith("NAME LIST")) {
						dateNumber=String.valueOf(i);
						break;
					}
					i--;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			driver.close();
		}
		return dateNumber;
	}
	public void closeDriver() {
		try {
			if(!driverClosed) {			
				driver.close();
				driverClosed=true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
