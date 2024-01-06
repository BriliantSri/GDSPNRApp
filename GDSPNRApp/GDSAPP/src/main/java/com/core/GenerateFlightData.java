package com.core;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.openqa.selenium.WebDriver;

import com.common.CommonProperties;
import com.model.TableDataService;
import com.pages.HomePage;
import com.pages.TerminalEmulation;

public class GenerateFlightData {
	WebDriver driver;
	private String fltNo;
	private String connFltNo;
	private String jrnyDte;
	private int dojCnt;	
	private TableDataService tableDataService;
	private StringTokenizer stringTokenizer;
	Connection connection;		
	private HashMap<String, HashMap<String, String>> returnFlightDataMap, inputDtlsMapFrFltData;
	private TerminalEmulation terminalEmulation;
	HashMap<String, HashMap<String, String>> routeMap;
	private List<String> multiLegFlts = null;
	FlightOperations fltOpers;
	
	

	public String getFltNo() {
		return fltNo;
	}

	public String getJrnyDte() {
		return jrnyDte;
	}

	public void setFltNo(String fltNo) {
		this.fltNo = fltNo;
	}

	public void setJrnyDte(String jrnyDte) {
		this.jrnyDte = jrnyDte;
	}
	public String getConnFltNo() {
		return connFltNo;
	}

	public void setConnFltNo(String connFltNo) {
		this.connFltNo = connFltNo;
	}
	
	public HashMap<String, HashMap<String, String>> getRouteMap() {
		return routeMap;
	}

	public void setRouteMap(HashMap<String, HashMap<String, String>> routeMap) {
		this.routeMap = routeMap;
	}
	
	public int getDojCnt() {
		return dojCnt;
	}

	public void setDojCnt(int dojCnt) {
		this.dojCnt = dojCnt;
	}
	
	public GenerateFlightData(Connection connection) {
		tableDataService = new TableDataService(connection);
		fltOpers = new FlightOperations();
		this.setRouteMap(tableDataService.getRoutingMap());
		//Uncomment below 03
		tableDataService.truncateSpecTable();
		this.setTempFlightData();
		this.setMultiLegFlts();
		
	}
	
	//Latest
	public void setTempFlightData() {
		Iterator<String> iter;
		HashMap<String, String> indvRouteDtls = null;
		String origin, destination, connCity, connAirCode;
		boolean hasConnection=false;
		try {
			tableDataService.truncateFlightRecord();
			LoginIShares loginIShares = new LoginIShares();
			driver = loginIShares.launchISHARESApplication(CommonProperties.envURL, true);
			loginIShares.logintoIShares(CommonProperties.logID, CommonProperties.passWord);
			HomePage homePage = new HomePage(driver);
			if(homePage.verifyHomePagedisplayed()) {
				homePage.selectTerminalEmulation();
				this.terminalEmulation = new TerminalEmulation(driver);
				terminalEmulation.runSingleLineEntry("LOGI CMRE");
				terminalEmulation.runSingleLineEntry("BSIB");
				iter = routeMap.keySet().iterator();
				while (iter.hasNext()) {
					indvRouteDtls = routeMap.get(iter.next());
					if (indvRouteDtls!=null && !indvRouteDtls.isEmpty()) {
						origin=indvRouteDtls.get("origin");
						destination = indvRouteDtls.get("destination");
						connCity = indvRouteDtls.get("conncity");
						connAirCode = indvRouteDtls.get("connAirCode");
						if(indvRouteDtls.get("hasconnect").trim().equalsIgnoreCase("TRUE")) {
							hasConnection = true;
						}else {
							hasConnection = false;
						}
						//indvRoute.put("conncity", resultSet.getString("CONNCITY"));
						//indvRoute.put("hasconnect", resultSet.getString("HASCONNECT"));
						this.addFlightRecordsToTable(origin, destination, hasConnection, connCity, false,"NA", connAirCode);
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally{
			driver.close();
		}
	}
	//Latest
	public void addFlightRecordsToTable(String origin, String destination, boolean hasConnection, String connCity, boolean isMutlLeg, String mulFltNo, String connAirCode){
		//Vector<HashMap<String, String>> flightData = new Vector();
		String fltNo=null, conFltNo=null, currDate, outResponse, shrEntry, availStr, cos, stringMultiLeg="FALSE", tempFltStr="", segDate="", orgAirCode="CM";
		int i, availability, seqNo =0, connDays=0, tempString=0;
		StringTokenizer strTknzr;
		//String[] cosArray = {"Y","C","Q","B","E","K"};
		String[] cosArray = tableDataService.getTDCOS().toArray(new String[0]);
		boolean validFlight = false, connectFlt=false;
		try {
			SimpleDateFormat sdfDT = new SimpleDateFormat("ddMMM");
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMMYYY - HHmmss");
			Calendar c;
			boolean flightIdentified = false;
			currDate = sdfDT.format(Calendar.getInstance().getTime());
			if(this.terminalEmulation!=null) {
				i=0;
				//Taking 10 days data
				while(i<15) {
					//Setting connectFlt flag to false
					connectFlt = false;
					validFlight = false;
					c = Calendar.getInstance();
					c.add(Calendar.DAY_OF_MONTH, i);
					currDate = sdfDT.format(c.getTime());
					
					if(hasConnection && (connCity!=null && connCity.trim().length()==3) && (connAirCode!=null && !connAirCode.trim().equalsIgnoreCase("CM"))) {
						shrEntry = "A "+origin+destination+"/"+connCity+" "+currDate+" -CM/" +connAirCode;
					}if(hasConnection && (connCity!=null && connCity.trim().length()==3)) {
						shrEntry = "A "+origin+destination+"/"+connCity+" "+currDate;
					}else {
						shrEntry = "A "+origin+destination+" "+currDate;
					}
					outResponse = terminalEmulation.runSingleLineEntry(shrEntry);
					strTknzr =  new StringTokenizer(outResponse, "\n");
					
					
					//FLTNO,ORG,DES,DOJ,COS, ST_AVL, CONCTY,CON_ST_AVL
					while (strTknzr.hasMoreTokens()) {
						tempString = seqNo;
						availStr = strTknzr.nextToken();
						if(availStr.length()>11 && (availStr.substring(0, 1).trim().length()>0 && availStr.substring(1, 3).equalsIgnoreCase("CM"))) {
							if(!availStr.substring(0, 1).trim().equalsIgnoreCase("x")) {
								if(availStr.contains((origin+destination).toUpperCase())){
									if(availStr.substring(0, 1).trim().equalsIgnoreCase("*")) {
										seqNo = tempString+1;
									}else {
										seqNo = Integer.parseInt(availStr.substring(0, 1).trim());
									}
									fltNo = availStr.substring(3, 7).trim();
									while(fltNo.length()<5) {
										fltNo="0"+fltNo;
									}
									while(mulFltNo.length()<5) {
										mulFltNo="0"+mulFltNo;
									}
									if(isMutlLeg && fltNo.equalsIgnoreCase(mulFltNo)) {
										this.setFltNo(fltNo);
										validFlight = true;
									}else if (!isMutlLeg) {
										this.setFltNo(fltNo);
										validFlight = true;
									}else {
										validFlight = false;
									}
									if(validFlight) {
										for(int j = 0; j<cosArray.length; j++) {
											cos = cosArray[j];
											availability = this.getAvailability(availStr, cos);
											if(availability>0){
												if(isMutlLeg) {
													stringMultiLeg = "TRUE";
												}
												tableDataService.insertFlightRecord(seqNo, orgAirCode, Integer.parseInt(fltNo.trim()), origin, destination, currDate, i, cos, availability, "NA", "NA", 0,stringMultiLeg,0, connAirCode);
											}		
										}
										connectFlt = false;
									}
								}else if(hasConnection && (connCity!=null && connCity.length()>0) && availStr.contains((origin.trim()+connCity.trim()).toUpperCase())){
									seqNo = Integer.parseInt(availStr.substring(0, 1).trim());
									validFlight = true;
									fltNo = availStr.substring(3, 7);								
									this.setFltNo(fltNo);
									for(int j = 0; j<cosArray.length; j++) {
										cos = cosArray[j];
										availability = this.getAvailability(availStr, cos);
										if(availability>0){
											tableDataService.insertFlightRecord(seqNo, orgAirCode, Integer.parseInt(fltNo.trim()), origin, destination, currDate, i, cos, availability, "NA", "NA", 0,stringMultiLeg,0, connAirCode);
										}		
									}	
									connectFlt = false;
								}else {
									validFlight = false;
								}
							}else {
								validFlight = false;
							}
							
						}else if(availStr.length()>11 && (availStr.substring(0, 1).trim().length()==0 && availStr.substring(1, 3).equalsIgnoreCase("CM"))) {
							if(hasConnection && availStr.contains(("   "+destination.trim()).toUpperCase())){
								validFlight = true;
								conFltNo = availStr.substring(3, 7);
								segDate = availStr.substring(8, 10);
								if(segDate.trim().length() > 0) {
									if(segDate.trim().equalsIgnoreCase("#1")) {
										connDays=1;
									}else if(segDate.trim().equalsIgnoreCase("#2")) {
										connDays=2;
									}
								}else {
									connDays=0;
								}
								for(int j = 0; j<cosArray.length; j++) {
									cos = cosArray[j];
									availability = this.getAvailability(availStr, cos);
									if(availability>0){
										//seqNo will be set when firstsegment is read
										tableDataService.insertConnFlightRecord(seqNo, orgAirCode, Integer.parseInt(fltNo.trim()), origin, destination, currDate, i, cos, availability, conFltNo.trim(), connCity.trim(), availability, i+connDays, connAirCode);
									}		
								}
								connectFlt = true;
							}else {
								validFlight = false;
							}
						}//Modified for UA flight
						else if(availStr.length()>11 && (availStr.substring(0, 1).trim().length()==0 && availStr.substring(1, 3).equalsIgnoreCase("UA"))) {
							if(hasConnection && availStr.contains(("   "+destination.trim()).toUpperCase())){
								validFlight = true;
								conFltNo = availStr.substring(3, 7);
								segDate = availStr.substring(8, 10);
								if(segDate.trim().length() > 0) {
									if(segDate.trim().equalsIgnoreCase("#1")) {
										connDays=1;
									}else if(segDate.trim().equalsIgnoreCase("#2")) {
										connDays=2;
									}
								}else {
									connDays=0;
								}
								for(int j = 0; j<cosArray.length; j++) {
									cos = cosArray[j];
									availability = this.getAvailability(availStr, cos);
									if(availability>0){
										//seqNo will be set when firstsegment is read
										tableDataService.insertConnFlightRecord(seqNo, orgAirCode, Integer.parseInt(fltNo.trim()), origin, destination, currDate, i, cos, availability, conFltNo.trim(), connCity.trim(), availability, i+connDays, connAirCode);
									}		
								}
								connectFlt = true;
							}else {
								validFlight = false;
							}
						}else if(availStr.length()>11 && validFlight){
							for(int j = 0; j<cosArray.length; j++) {
								cos = cosArray[j];
								availability = this.getAvailability(availStr, cos);
								if(availability>0 && !connectFlt){
									tableDataService.insertFlightRecord(seqNo, orgAirCode, Integer.parseInt(fltNo.trim()), origin, destination, currDate, i, cos, availability, "NA", "NA", 0,stringMultiLeg,0, connAirCode);
								}else if(availability>0) {
									tableDataService.insertConnFlightRecord(seqNo, orgAirCode, Integer.parseInt(fltNo.trim()), origin, destination, currDate, i, cos, availability, conFltNo.trim(), connCity.trim(), availability, i+connDays, connAirCode);
								}		
							}
						}
					}	
					//End of inner while
					i++;					
				}
				//End of outer While
			
			}
			
		}catch(Exception e) {
			
			//System.out.println(" - ORigin:"+ origin+ " - Destination:"+ destination+ " - Has Connection:"+ hasConnection+ " - Connection city:"+ connCity);
			e.printStackTrace();
			
		}
		
	}
	
	
	public HashMap<String, HashMap<String, String>> generateTestDataInputs(String autoScenIDsString, HashMap<String, HashMap<String, String>> autoScenarioMap) {
		stringTokenizer = new StringTokenizer(autoScenIDsString, ",");
		HashMap<String, String> indScenarioMap, indScenFltDataMap;
		
		returnFlightDataMap = new HashMap();
		String strToken="", scenarioID = "", cos="";
		int noOfPAx;
		boolean isCheckInReq;
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
				
				while (stringTokenizer.hasMoreElements()) {
					scenarioID = stringTokenizer.nextElement().toString();
					indScenarioMap = autoScenarioMap.get(scenarioID);
					if (indScenarioMap != null && indScenarioMap.size() > 0) {
						cos = indScenarioMap.get("cos");
						noOfPAx = Integer.parseInt(indScenarioMap.get("paxcnt"));
						if (indScenarioMap.get("checkin").equalsIgnoreCase("TRUE")) {
							isCheckInReq = true;
						}else {
							isCheckInReq = false;
						}
						
						indScenFltDataMap = this.getFlightData(scenarioID, cos, noOfPAx, isCheckInReq);
						returnFlightDataMap.put(scenarioID, indScenFltDataMap);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			//driver.close();
		}
		finally {
			driver.close();
		}
		return returnFlightDataMap;
	}
	
	public HashMap<String, String> getFlightData(String scenaioID, String cos, int noOfPax, boolean checkinReq) {
		this.setFltNo(null);
		this.setJrnyDte(null);
		this.setDojCnt(0);
		//String fltNo=null;
		HashMap<String, HashMap<String, String>> routeMap = new HashMap();
		HashMap<String, String> indvRouteDtls, fltDataMap=null;
		Iterator <String> iter;
		String origin=null, destination=null;
		boolean fltNoSet=false;
		try {
			
			
			routeMap = tableDataService.getRoutingMap();
			iter = routeMap.keySet().iterator();
			while (iter.hasNext()) {
				indvRouteDtls = routeMap.get(iter.next());
				if (indvRouteDtls!=null && !indvRouteDtls.isEmpty()) {
					origin=indvRouteDtls.get("origin");
					destination = indvRouteDtls.get("destination");
					this.setFltNumAndDOJCnt(origin, destination, noOfPax, cos, checkinReq);
					//Above method sets the flight number if valid flight is found
					if(this.getFltNo()!=null && this.getFltNo().length()>0) {
						fltNoSet = true;
						break;
					}
				}
			}
			if(fltNoSet) {
				fltDataMap = new HashMap();
				fltDataMap.put("fltNo", this.getFltNo());
				fltDataMap.put("origin", origin);
				fltDataMap.put("destination", destination);
				fltDataMap.put("cos", cos);
				fltDataMap.put("dojCnt", String.valueOf(this.getDojCnt()));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			//driver.close();
		}
		return fltDataMap;
	}
	
	public void setFltNumAndDOJCnt(String org, String des, int noOfPax, String cos, boolean checkinRequired) {		
		String fltNo=null, currDate, outResponse, shrEntry, availStr;		
		HashMap<String, String> fltData;
		Iterator <String> iter;
		int i, noOfDays;
		SimpleDateFormat sdfDT = new SimpleDateFormat("ddMMM");
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMYYY - HHmmss");
		Calendar c = Calendar.getInstance();
		boolean flightIdentified = false;
		currDate = sdfDT.format(Calendar.getInstance().getTime());
		StringTokenizer strTknzr;
		try{
			if(this.terminalEmulation!=null) {
				i=0;
				if(checkinRequired) {
					noOfDays=3;
				}else {
					noOfDays=10;
				}
				while(i<noOfDays) {
					c.add(Calendar.DATE, 1);
					currDate = sdfDT.format(Calendar.getInstance().getTime());
					shrEntry = "A "+org+des+" "+currDate;					
					outResponse = terminalEmulation.runSingleLineEntry(shrEntry);
					strTknzr =  new StringTokenizer(outResponse, "\n");
					while (strTknzr.hasMoreTokens()) {
						availStr = strTknzr.nextToken();
						if(availStr.length()>11 && (availStr.substring(1, 3).equalsIgnoreCase("CM"))) {
							fltNo = availStr.substring(3, 7);
							this.setFltNo(fltNo);
							if(checkAvailabilityPerClass(availStr, cos, noOfPax)){
								this.setDojCnt(i+1);	
								flightIdentified = true;
							}							
						}else if(availStr.length()>11){
							if(checkAvailabilityPerClass(availStr, cos, noOfPax)){
								this.setDojCnt(i+1);
								flightIdentified = true;
							}
						}
					}
					if(flightIdentified) {
						break;
					}
					i++;
					
				}
				terminalEmulation.runSingleLineEntry("I");
			}
		}catch(Exception e) {
			e.printStackTrace();		
		}
		
	}
	//Derives the mutli leg flights
	public void setMultiLegFlts() {		
		String fltNo=null, currDate, outResponse, shrEntry, availStr, tempStr;		
		HashMap<String, String> fltData;
		Iterator <String> iter;
		int i, noOfDays;
		SimpleDateFormat sdfDT = new SimpleDateFormat("ddMMM");
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMYYY - HHmmss");
		Calendar c = Calendar.getInstance();
		boolean flightIdentified = false;
		currDate = sdfDT.format(Calendar.getInstance().getTime());
		StringTokenizer strTknzr, fltTknzr;
		try{
			LoginIShares loginIShares = new LoginIShares();
			driver = loginIShares.launchISHARESApplication(CommonProperties.envURL, false);
			loginIShares.logintoIShares(CommonProperties.logID, CommonProperties.passWord);
			HomePage homePage = new HomePage(driver);
			if(homePage.verifyHomePagedisplayed()) {
				homePage.selectTerminalEmulation();
				this.terminalEmulation = new TerminalEmulation(driver);		
				terminalEmulation.runSingleLineEntry("LOGI CMRE");
				terminalEmulation.runSingleLineEntry("BSIB");
				outResponse = terminalEmulation.runSingleLineEntry("JSY0FLT");
				outResponse = outResponse.substring(outResponse.indexOf("JSY0FLT"));
				outResponse = outResponse.replaceAll("LEG....\\*\\*", "LEG....02");
				outResponse = outResponse.substring(0, outResponse.indexOf("02")+2);
				outResponse = outResponse.replaceAll(" \n", "\n");
				outResponse = terminalEmulation.runMultiLineEntries(outResponse);
				strTknzr =  new StringTokenizer(outResponse, "\n");
				while (strTknzr.hasMoreTokens()) {
					tempStr = strTknzr.nextToken();
					if(!tempStr.trim().startsWith("\\*")) {
						fltTknzr = new StringTokenizer(tempStr.trim()," ");
						if(fltTknzr.hasMoreElements()) {
							this.multiLegFlts = new ArrayList();
							while(fltTknzr.hasMoreElements()) {
								multiLegFlts.add(fltTknzr.nextToken());
							}
						}
					}
				}	
				this.setMultiLegTestData();
			}
		}catch(Exception e) {
			e.printStackTrace();		
		}finally{
			driver.close();
			this.fltOpers.setDriverClosed(true);
		}
		
	}
	//Updates the Multi leg in database
	public void setMultiLegTestData() {		
		String fltNo=null, currDate, outResponse, shrEntry, flifoString, org="", des="", fltOpen="FALSE";		
		Iterator <String> iter;
		int i;
		SimpleDateFormat sdfDT = new SimpleDateFormat("ddMMM");
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMYYY - HHmmss");
		Calendar c = Calendar.getInstance();	
		currDate = sdfDT.format(Calendar.getInstance().getTime());
		StringTokenizer strTknzr;
		
		try{
			if(this.multiLegFlts!=null && !this.multiLegFlts.isEmpty() && this.terminalEmulation!=null) {
				iter = this.multiLegFlts.iterator();
							
				
				while(iter.hasNext()) {
					fltNo = iter.next();
					i=0;	
					while(i<5) {
						c.add(Calendar.DATE, 1);
						currDate = sdfDT.format(Calendar.getInstance().getTime());						
						shrEntry = "2"+fltNo+"/"+currDate;
						outResponse = terminalEmulation.runSingleLineEntry(shrEntry);
						strTknzr = new StringTokenizer(outResponse,"\n");
						while (strTknzr.hasMoreElements()) {
							flifoString = strTknzr.nextToken();
							if(flifoString.trim().startsWith("SKED")) {
								flifoString = flifoString.replaceAll("SKED","").trim();
								org = flifoString.substring(0, 3);
								//connecting row
								flifoString = strTknzr.nextToken();
								//destination row
								flifoString = strTknzr.nextToken().trim();
								des = flifoString.substring(0, 3);
								this.addFlightRecordsToTable(org, des, false, "NA", true, fltNo,"NA");		
								this.fltOpers.setTerminalEmulation(this.terminalEmulation);
								this.fltOpers.setDriverClosed(false);
								if(fltOpers.isShipAssigned(fltNo, currDate)) {
									fltOpen = "TRUE";
								}			
								tableDataService.insertSpecificFltTD(Integer.parseInt(fltNo.trim()), currDate, i, org, des, "TRUE", fltOpen, "NA", "NA");
								fltOpen="FALSE";
								break;
							}
						}
						i++;
					}					
				}
			}
		}catch(Exception e) {
			e.printStackTrace();		
		}
		
	}
	
	
	//Separately coded for "FlightDepartureInfoService" "Flight has two legs and NotOpen status"
	public HashMap<String, String> multiLegNonOpen() {
		String fltNo=null, currDate, outResponse, shrEntry, availStr, tempStr, flifoString, org;		
		HashMap<String, String> fltData=null;
		Iterator <String> iter;
		int i, noOfDays;
		SimpleDateFormat sdfDT = new SimpleDateFormat("ddMMM");
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMYYY - HHmmss");
		Calendar c = Calendar.getInstance();
		boolean flightIdentified = false;
		currDate = sdfDT.format(Calendar.getInstance().getTime());
		StringTokenizer strTknzr, fltTknzr;
		try{
			LoginIShares loginIShares = new LoginIShares();
			driver = loginIShares.launchISHARESApplication(CommonProperties.envURL, false);
			loginIShares.logintoIShares(CommonProperties.logID, CommonProperties.passWord);
			HomePage homePage = new HomePage(driver);
			if(homePage.verifyHomePagedisplayed()) {
				homePage.selectTerminalEmulation();
				this.terminalEmulation = new TerminalEmulation(driver);		
				terminalEmulation.runSingleLineEntry("LOGI CMRE");
				terminalEmulation.runSingleLineEntry("BSIB");
				outResponse = terminalEmulation.runSingleLineEntry("JSY0FLT");
				outResponse = outResponse.substring(outResponse.indexOf("JSY0FLT"));
				outResponse = outResponse.replaceAll("LEG....\\*\\*", "LEG....02");
				outResponse = outResponse.substring(0, outResponse.indexOf("02")+2);
				outResponse = outResponse.replaceAll(" \n", "\n");
				outResponse = terminalEmulation.runMultiLineEntries(outResponse);
				strTknzr =  new StringTokenizer(outResponse, "\n");
				while (strTknzr.hasMoreTokens()) {
					tempStr = strTknzr.nextToken();
					if(!tempStr.trim().startsWith("\\*")) {
						fltTknzr = new StringTokenizer(tempStr.trim()," ");
						if(fltTknzr.hasMoreElements()) {
							this.multiLegFlts = new ArrayList();
							while(fltTknzr.hasMoreElements()) {
								multiLegFlts.add(fltTknzr.nextToken());
							}
						}
					}
				}	
				if(this.multiLegFlts!=null && !this.multiLegFlts.isEmpty()) {
					iter = this.multiLegFlts.iterator();
					fltData = new HashMap();
					while(iter.hasNext()) {
						fltNo = iter.next();
						i=0;	
						while(i<5) {
							c.add(Calendar.DATE, 1);
							currDate = sdfDT.format(Calendar.getInstance().getTime());						
							shrEntry = "2"+fltNo+"/"+currDate;
							outResponse = terminalEmulation.runSingleLineEntry(shrEntry);
							strTknzr = new StringTokenizer(outResponse,"\n");
							while (strTknzr.hasMoreElements()) {
								flifoString = strTknzr.nextToken();
								if(flifoString.trim().startsWith("SKED")) {
									flifoString = flifoString.replaceAll("SKED","").trim();
									org = flifoString.substring(0, 3);
									//connecting row
									this.fltOpers.setTerminalEmulation(this.terminalEmulation);
									this.fltOpers.setDriverClosed(false);
									if(!fltOpers.isShipAssigned(fltNo, currDate)) {
										fltData.put("fltNo", fltNo);
										fltData.put("org", org);
										fltData.put("doj", currDate);
										break;
									}			
								}
							}
							i++;
						}					
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();		
		}finally{
			driver.close();
			this.fltOpers.setDriverClosed(true);
		}
		return fltData;
	}
	
	// This is for checking the availability as per the cos and no of pax
	public boolean checkAvailabilityPerClass(String line, String cos, int noOfPax) throws Exception{
		int i = noOfPax;
		int j;
		boolean isAvlExists = false;
		 while (i<10){
			 j=i;
			 
			 if (line.contains(cos.toUpperCase().concat(String.valueOf(j)))){
				 isAvlExists = true;
				 break;
			 }
			 i++;
		 }
		 return isAvlExists;
	}
	
	//Below is to get availability count for specific flight and cos
	public int getAvailability(String line, String cos) throws Exception{
		int i=1;
		int j;
		boolean isAvlExists = false;
		 while (i<10){
			 j=i;
			 if (line.contains(cos.toUpperCase().concat(String.valueOf(j)))){
				 isAvlExists = true;
				 break;
			 }
			 i++;
		 }
		 if(!isAvlExists) {
			 i=0;
		 }
		 return i;
	}
	

}
