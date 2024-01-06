package com.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.common.CommonProperties;
import com.core.LoginIShares;
import com.model.TableDataService;
import com.pages.HomePage;
import com.pages.TerminalEmulation;

public class FarePricesCollector {
	WebDriver driver;
	private String org = null;
	private String des = null;
	private String envURL;
	private String logID;
	private String passWord;
	private int noOFSegments;
	private HashMap<String, HashMap<String, String>> fltTime;
	private HashMap<String, String> taxMap, specializedTaxMap;
	private String taxString;
	private Connection connection;
	private TableDataService tableDataService;
	private boolean manualFareQuote=false;
	
	public HashMap<String, String> getTaxMap() {
		return taxMap;
	}

	public void setTaxMap(HashMap<String, String> taxMap) {
		this.taxMap = taxMap;
	}

	public int getNoOFSegments() {
		return noOFSegments;
	}

	public HashMap<String, HashMap<String, String>> getFltTime() {
		return fltTime;
	}

	public void setFltTime(HashMap<String, HashMap<String, String>> fltTime) {
		this.fltTime = fltTime;
	}

	public void setNoOFSegments(int noOFSegments) {
		this.noOFSegments = noOFSegments;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public String getEnvURL() {
		return envURL;
	}

	public void setEnvURL(String envURL) {
		this.envURL = envURL;
	}

	public String getLogID() {
		return logID;
	}

	public void setLogID(String logID) {
		this.logID = logID;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public FarePricesCollector(TableDataService tableDataService){
		this.tableDataService = tableDataService;
	}
	
	public List<String> createBookingEntries(HashMap<String, Object> segmentMap){
		List<String> bookingEntryList = new ArrayList<String>();
		HashMap<String,String> segmentDtls = new HashMap<String, String>();
		String bkEntry, tmpString;
		int i, tempPos;
		try {
			if(segmentMap!=null && segmentMap.size()>0) {
				i=1;
				while (i<=segmentMap.size()) {
					segmentDtls = (HashMap)segmentMap.get(i);
					if(segmentDtls!=null && segmentDtls.size()>0) {
						//0UA1305Y28FEBIAHORDNN1
						bkEntry="0";
						
						if(segmentDtls.get("airlineCode")!=null && segmentDtls.get("airlineCode").toString().length()>0) {
							if(segmentDtls.get("airlineCode").toString().length() > 2 && segmentDtls.get("airlineCode").toString().contains("(")) {
								tempPos = segmentDtls.get("airlineCode").toString().indexOf("(");
								tmpString = segmentDtls.get("airlineCode").substring(0, tempPos).toUpperCase().trim();
							}else {
								tmpString = segmentDtls.get("airlineCode").toString().trim();
							}
						}else {
							tmpString="CM";
						}
						bkEntry = bkEntry+tmpString.toUpperCase();
						bkEntry = bkEntry+segmentDtls.get("fltNo").toString().toUpperCase();
						bkEntry = bkEntry+segmentDtls.get("cosCode").toString().toUpperCase();
						
						tmpString = segmentDtls.get("DtOfJrny").toString().toUpperCase();
						//bkEntry = bkEntry+tempStr.substring(0, tempStr.length()-2);
						bkEntry = bkEntry+tmpString;
						if (segmentDtls.get("org")!=null && segmentDtls.get("org").toString().length()>0) {
							if (segmentDtls.get("org").toString().contains("(")) {
								tempPos = segmentDtls.get("org").toString().indexOf("(");
								tmpString = segmentDtls.get("org").substring(0, tempPos).toUpperCase().trim();
							}else {
								tmpString = segmentDtls.get("org").toString().toUpperCase().trim();
							}
						}else {
							tmpString="   ";
						}
						bkEntry=bkEntry+tmpString;
						
						if (segmentDtls.get("des")!=null && segmentDtls.get("des").toString().length()>0) {
							if (segmentDtls.get("des").toString().contains("(")) {
								tempPos = segmentDtls.get("des").toString().indexOf("(");
								tmpString = segmentDtls.get("des").substring(0, tempPos).toUpperCase().trim();
							}else {
								tmpString = segmentDtls.get("des").toString().toUpperCase().trim();
							}
						}else {
							tmpString="   ";
						}
						bkEntry=bkEntry+tmpString;
						
						//bkEntry = bkEntry+segmentDtls.get("org").toString().toUpperCase();
						//bkEntry = bkEntry+segmentDtls.get("des").toString().toUpperCase();
						bkEntry = bkEntry+"NN1";
						
						bookingEntryList.add(bkEntry);
						i++;
					}
					
				}
			}
			
		}catch(Exception e) {
			
		}
		return bookingEntryList;
	}
	
	public HashMap <String, HashMap<String, HashMap>> getFarePrices(HashMap<String, Object> segmentMap) {
		//Removed param from method name - ArrayList<String> bookingEntries
		Iterator<String> iter;
		String response, lniata, pos, currCD;
		List<String> bookingEntries;
		HashMap <String, HashMap> allPaxFareMap = new HashMap<String, HashMap>(); 
		HashMap <String, HashMap<String, HashMap>> allTicktFareMap = new HashMap <String, HashMap<String, HashMap>>();
		int i, bookEntrySize, orgResLen=0, trimResLen=0;
		
		try {
			bookingEntries = this.createBookingEntries(segmentMap);
			bookEntrySize = bookingEntries.size();
			if(bookingEntries!=null && bookingEntries.size()>0) {
				LoginIShares loginIShares = new LoginIShares();
				driver = loginIShares.launchISHARESApplication(CommonProperties.envURL, false);
				loginIShares.logintoIShares(CommonProperties.logID, CommonProperties.passWord);
				HomePage homePage = new HomePage(driver);
					if(homePage.verifyHomePagedisplayed()) {
						homePage.selectTerminalEmulation();
						TerminalEmulation terminalEmulation = new TerminalEmulation(driver);
						terminalEmulation.runSingleLineEntry("LOGI CMRE");
						terminalEmulation.runSingleLineEntry("BSIB");
						
						iter = bookingEntries.iterator();
						this.fltTime = new HashMap<String, HashMap<String, String>>();
						i=1;
						
						lniata = this.getLniatafromRespose(terminalEmulation.runSingleLineEntry("RATS*"));
						pos = CommonProperties.pos;
						currCD = CommonProperties.currency;
						
						if(pos.length() >0 && currCD.length() > 0) {
							terminalEmulation.runSingleLineEntry("W"+lniata+"C3"+pos+"CM");
							//System.out.println("W"+lniata+"C3"+pos+"CM");
							terminalEmulation.runSingleLineEntry("BSIB");
						}
						while (iter.hasNext() && i<=4) {
							terminalEmulation.runSingleLineEntry(iter.next().toString());
							this.setFlightArrDep(terminalEmulation.getResponse());							
						}
						//Adult
						terminalEmulation.runSingleLineEntry("$-$-PADT");
						response = terminalEmulation.getResponse();
						allPaxFareMap.put("ADT", getPrices(response, "ADT"));
						//taxString = "";
						if(!this.manualFareQuote) {
							taxString = "";
							this.createTaxString(response,3, true);
							if(response.trim().length() < response.length()-2) {
								terminalEmulation.runSingleLineEntry("MD");
								response = terminalEmulation.getResponse();
								this.createTaxString(response,2, false);
							}
						}
						specializedTaxMap = this.getTxMap(this.taxString);
						allPaxFareMap.put("ADT_TX", specializedTaxMap);
						
						//Infant
						terminalEmulation.runSingleLineEntry("$-$-PINF");
						response = terminalEmulation.getResponse();						
						allPaxFareMap.put("INF", getPrices(response, "INF"));
						
						if(!this.manualFareQuote) {
							taxString = "";
							this.createTaxString(response,3, true);
							if(response.trim().length() < response.length()-2) {
								terminalEmulation.runSingleLineEntry("MD");
								response = terminalEmulation.getResponse();
								this.createTaxString(response,2, false);
							}
						}
						specializedTaxMap = this.getTxMap(this.taxString);
						allPaxFareMap.put("INF_TX", specializedTaxMap);
						
						//INS
						terminalEmulation.runSingleLineEntry("$-$-PINS");
						response = terminalEmulation.getResponse();
						allPaxFareMap.put("INS", getPrices(response, "INS"));
						//taxString = "";
						
						if(!this.manualFareQuote) {
							taxString = "";
							this.createTaxString(response,3, true);
							if(response.trim().length() < response.length()-2) {
								terminalEmulation.runSingleLineEntry("MD");
								response = terminalEmulation.getResponse();
								this.createTaxString(response,2, false);
							}
						}
						specializedTaxMap = this.getTxMap(this.taxString);
						allPaxFareMap.put("INS_TX", specializedTaxMap);
						
						//CHD
						terminalEmulation.runSingleLineEntry("$-$-PC08");
						response = terminalEmulation.getResponse();
						allPaxFareMap.put("CHD", getPrices(response, "CHD"));
						//taxString = "";
						if(!this.manualFareQuote) {
							taxString = "";
							this.createTaxString(response,3, true);
							if(response.trim().length() < response.length()-2) {
								terminalEmulation.runSingleLineEntry("MD");
								response = terminalEmulation.getResponse();
								this.createTaxString(response,2, false);
							}
						}
						specializedTaxMap = this.getTxMap(this.taxString);
						allPaxFareMap.put("CHD_TX", specializedTaxMap);
						terminalEmulation.runSingleLineEntry("I");
						allTicktFareMap.put("TCKT1", allPaxFareMap);
						
						//If the segments are more than 4
						if(bookEntrySize>4) {
							i=1;
							while (iter.hasNext() && i<=4) {
								terminalEmulation.runSingleLineEntry(iter.next().toString());
								this.setFlightArrDep(terminalEmulation.getResponse());							
							}
							//ADT
							terminalEmulation.runSingleLineEntry("$-$-PADT");
							response = terminalEmulation.getResponse();
							allPaxFareMap.put("ADT", getPrices(response, "ADT"));
							
							if(!this.manualFareQuote) {
								taxString = "";
								this.createTaxString(response,3, true);
								if(response.trim().length() < response.length()-2) {
									terminalEmulation.runSingleLineEntry("MD");
									response = terminalEmulation.getResponse();
									this.createTaxString(response,2, false);
								}
							}
							specializedTaxMap = this.getTxMap(this.taxString);
							allPaxFareMap.put("ADT_TX", specializedTaxMap);
							//INF
							terminalEmulation.runSingleLineEntry("$-$-PINF");
							response = terminalEmulation.getResponse();
							allPaxFareMap.put("INF", getPrices(response, "INF"));
							
							if(!this.manualFareQuote) {
								taxString = "";
								this.createTaxString(response,3, true);
								if(response.trim().length() < response.length()-2) {
									terminalEmulation.runSingleLineEntry("MD");
									response = terminalEmulation.getResponse();
									this.createTaxString(response,2, false);
								}
							}
							specializedTaxMap = this.getTxMap(this.taxString);
							allPaxFareMap.put("INF_TX", specializedTaxMap);
							
							//INS
							terminalEmulation.runSingleLineEntry("$-$-PINS");
							response = terminalEmulation.getResponse();
							allPaxFareMap.put("INS", getPrices(response, "INS"));
							
							if(!this.manualFareQuote) {
								taxString = "";
								this.createTaxString(response,3, true);
								if(response.trim().length() < response.length()-2) {
									terminalEmulation.runSingleLineEntry("MD");
									response = terminalEmulation.getResponse();
									this.createTaxString(response,2, false);
								}
							}
							specializedTaxMap = this.getTxMap(this.taxString);
							allPaxFareMap.put("INS_TX", specializedTaxMap);
							
							//CHD
							terminalEmulation.runSingleLineEntry("$-$-PC08");
							response = terminalEmulation.getResponse();
							allPaxFareMap.put("CHD", getPrices(response, "CHD"));
							
							if(!this.manualFareQuote) {
								taxString = "";
								this.createTaxString(response,3, true);
								if(response.trim().length() < response.length()-2) {
									terminalEmulation.runSingleLineEntry("MD");
									response = terminalEmulation.getResponse();
									this.createTaxString(response,2, false);
								}
							}
							specializedTaxMap = this.getTxMap(this.taxString);
							allPaxFareMap.put("CHD_TX", specializedTaxMap);
							
							
							terminalEmulation.runSingleLineEntry("I");
							allTicktFareMap.put("TCKT2", allPaxFareMap);							
						}
						//If the segments are more than 8
						if(bookEntrySize>8) {
							i=1;
							while (iter.hasNext() && i<=4) {
								terminalEmulation.runSingleLineEntry(iter.next().toString());
								this.setFlightArrDep(terminalEmulation.getResponse());							
							}
							//ADT
							terminalEmulation.runSingleLineEntry("$-$-PADT");
							response = terminalEmulation.getResponse();
							allPaxFareMap.put("ADT", getPrices(response, "ADT"));
							
							if(!this.manualFareQuote) {
								taxString = "";
								this.createTaxString(response,3, true);
								if(response.trim().length() < response.length()-2) {
									terminalEmulation.runSingleLineEntry("MD");
									response = terminalEmulation.getResponse();
									this.createTaxString(response,2, false);
								}
							}
							specializedTaxMap = this.getTxMap(this.taxString);
							allPaxFareMap.put("ADT_TX", specializedTaxMap);
							
							//INF
							terminalEmulation.runSingleLineEntry("$-$-PINF");
							response = terminalEmulation.getResponse();
							allPaxFareMap.put("INF", getPrices(response, "INF"));
							
							if(!this.manualFareQuote) {
								taxString = "";
								this.createTaxString(response,3, true);
								if(response.trim().length() < response.length()-2) {
									terminalEmulation.runSingleLineEntry("MD");
									response = terminalEmulation.getResponse();
									this.createTaxString(response,2, false);
								}
							}
							specializedTaxMap = this.getTxMap(this.taxString);
							allPaxFareMap.put("INF_TX", specializedTaxMap);
							
							//INS
							terminalEmulation.runSingleLineEntry("$-$-PINS");
							response = terminalEmulation.getResponse();
							allPaxFareMap.put("INS", getPrices(response, "INS"));
							
							if(!this.manualFareQuote) {
								taxString = "";
								this.createTaxString(response,3, true);
								if(response.trim().length() < response.length()-2) {
									terminalEmulation.runSingleLineEntry("MD");
									response = terminalEmulation.getResponse();
									this.createTaxString(response,2, false);
								}
							}
							specializedTaxMap = this.getTxMap(this.taxString);
							allPaxFareMap.put("INS_TX", specializedTaxMap);
							
							//CHD
							terminalEmulation.runSingleLineEntry("$-$-PC08");
							response = terminalEmulation.getResponse();
							allPaxFareMap.put("CHD", getPrices(response, "CHD"));
							
							if(!this.manualFareQuote) {
								taxString = "";
								this.createTaxString(response,3, true);
								if(response.trim().length() < response.length()-2) {
									terminalEmulation.runSingleLineEntry("MD");
									response = terminalEmulation.getResponse();
									this.createTaxString(response,2, false);
								}
							}
							specializedTaxMap = this.getTxMap(this.taxString);
							allPaxFareMap.put("CHD_TX", specializedTaxMap);
							
							terminalEmulation.runSingleLineEntry("I");
							allTicktFareMap.put("TCKT3", allPaxFareMap);							
						}
						
				}	
					homePage.logOff();
					driver.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return allTicktFareMap;		
	}
	
	public void setFlightArrDep(String response) {
		String str="", fltNo, arrTime, depTime, depZone, arrZone;
		HashMap<String, String> timeMap = new HashMap<String, String>();
		long timeVar;		
	
		StringTokenizer strTknzr;
		strTknzr = new StringTokenizer(response,"\n");
		str = strTknzr.nextToken();
		fltNo = str.substring(6,10);
		
		while(fltNo.trim().length() < 4) {
			fltNo = "0"+fltNo.trim();
		}
		depTime = str.substring(34,38);
		depZone = str.substring(38,40);
		if(depZone.equalsIgnoreCase("P")) {
			if(depTime.trim().length()<4) {
				timeVar=Long.parseLong(depTime);
				timeVar=timeVar+1200;
				depTime=String.valueOf(timeVar);
			}
		}else {
			if(depTime.trim().length()<4) {					
				depTime="0"+depTime.trim();
			}
		}
		arrTime = str.substring(40,44);
		if(str.length()>45) {
			arrZone = str.substring(44,46);
		}else {
			arrZone = str.substring(44);
		}
		if(arrZone.equalsIgnoreCase("P")) {
			if(arrTime.trim().length()<4) {
				timeVar=Long.parseLong(arrTime);
				timeVar=timeVar+1200;
				arrTime=String.valueOf(timeVar);
			}
		}else {
			if(arrTime.trim().length()<4) {					
				arrTime="0"+arrTime.trim();
			}
		}
		timeMap.put("arrTime", arrTime);
		timeMap.put("depTime", depTime);
		this.fltTime.put(fltNo, timeMap);
	
	}
	public ArrayList<String> returnFormattedString(String resString){
		ArrayList<String> formtdStrng = new ArrayList();
		StringTokenizer strTknzr = new StringTokenizer(resString,"\n");
		while (strTknzr.hasMoreTokens()){
			formtdStrng.add(strTknzr.nextToken().toString().trim());
		}		
		return formtdStrng;
	}
	
	public void createTaxString(String resString, int noOfRec, boolean checkRow){
		String rowString;
		StringTokenizer strTknzr = new StringTokenizer(resString,"\n");
		int lenBeforeTrim, afterTrim;
		
		while (strTknzr.hasMoreTokens() && noOfRec>0){
			rowString =strTknzr.nextToken();
			if(rowString.startsWith("1 -") && rowString.contains("PSGR") && checkRow) {
				while (strTknzr.hasMoreTokens() && noOfRec>0) {
					this.taxString = this.taxString + strTknzr.nextToken().toString().trim();
					this.taxString.replace("|>", "");
					noOfRec--;
				}
			}else if(!checkRow){
				this.taxString = this.taxString +rowString.trim();
				noOfRec--;
				lenBeforeTrim = rowString.length();
				afterTrim = rowString.trim().length();
				if(afterTrim < lenBeforeTrim-2) {
					noOfRec--;
				}
				while (strTknzr.hasMoreTokens() && noOfRec>0) {					
					this.taxString = this.taxString + strTknzr.nextToken().toString().trim();
					noOfRec--;
				}
			}
		}
	}
	
	
	public HashMap<String, String> getPrices(String response, String paxType) throws Exception{
		HashMap<String,String> fareMap = new HashMap<String, String>();
		HashMap<String,String> taxMap;
		HashMap<String,String> tblTaxDtls;
		int decValu=0;
		DecimalFormat decfor = new DecimalFormat("0.00");  
		int indexNo, tknCnt, txCnt=0;
		StringTokenizer strTknzr, farTknzr;
		String rowString, txFareString, currCD, txString, longTxString="XT", decFormatStr="#", txCD;
		boolean fareMapCreated=false, USDeqlFrDisp=false;
		double paxTypeFactor, baseFare, calCFactor, totalFare, calSpecTax;
		currCD = CommonProperties.currency;
		 if(response!=null && response.length()>0) {
				int i=1;
				strTknzr = new StringTokenizer(response,"\n");
				while (strTknzr.hasMoreTokens()){
					if(i>=1) {
						rowString = strTknzr.nextToken().toString().trim();
						//Check whether USDFare is displayed if currency is other than USD
						if(!currCD.equalsIgnoreCase("USD")) {
							if(rowString.startsWith("USD FARE")) {
								USDeqlFrDisp = true;
								txCnt = this.getNoOfTaxHdrs(rowString);
							}else if (rowString.startsWith(currCD+" FARE")) {
								USDeqlFrDisp = false;
								txCnt = this.getNoOfTaxHdrs(rowString);
							} 
						}else {
							if(rowString.startsWith("USD FARE")) {
								txCnt = this.getNoOfTaxHdrs(response);
							}
						}
						if(rowString.startsWith("1 -") && !rowString.contains("PSGR")) {
							indexNo = rowString.indexOf("1 -");
							rowString = rowString.substring(indexNo+4);
							farTknzr = new StringTokenizer(rowString," ");
							if(farTknzr.hasMoreTokens()) {
								if(USDeqlFrDisp) {
									fareMap.put("usdEqFare",farTknzr.nextToken());
								}else {
									fareMap.put("usdEqFare","0.00");
								}
								
								fareMap.put("baseFare",farTknzr.nextToken());
								if(txCnt>0) {
									fareMap.put("T1",farTknzr.nextToken());
									fareMap.put("T1CD",farTknzr.nextToken());
								}
								if(txCnt==2) {
									fareMap.put("T2",farTknzr.nextToken());
									fareMap.put("T2CD",farTknzr.nextToken());
								}
								fareMap.put("TTL",farTknzr.nextToken());
								fareMapCreated = true;
							}
							break;
						}				
					}
					i++;	
				}
				
			}
		 
		 	if(fareMapCreated==false) {
				if(this.noOFSegments==0) {
					setNoOFSegments(1);
				}
				this.manualFareQuote = true;
				paxTypeFactor=1.00;
				if(paxType.equalsIgnoreCase("INF")){
					paxTypeFactor = 0.39;				
				}else if(paxType.equalsIgnoreCase("INS") || paxType.equalsIgnoreCase("CHD")){
					paxTypeFactor = 0.85;				
				}
				tblTaxDtls = tableDataService.getTaxMap();
				totalFare=0.00;
				calSpecTax=0.00;
				
				if(tblTaxDtls!=null && !tblTaxDtls.isEmpty()) {
					decValu = Integer.parseInt(tblTaxDtls.get("decimalNum"));
					
					
					for(int i=0;i<decValu; i++) {
						if(i==0) {
							decFormatStr=decFormatStr+ ".";
						}
						decFormatStr=decFormatStr+"#";
					}
					
				     
					
					decfor = new DecimalFormat(decFormatStr);  
					baseFare=Double.parseDouble(tblTaxDtls.get("baseFare"));
					totalFare = baseFare;
					
					txFareString = this.formatFareWithDec(String.valueOf(decfor.format(baseFare*this.noOFSegments*paxTypeFactor)), decValu);
					fareMap.put("baseFare",txFareString);
					for(int i=1;i<10; i++) {
						txString = tblTaxDtls.get("TX"+String.valueOf(i));
						if(txString!=null && txString.length()>0) {
							txCD = txString.substring(0, 2);
							calCFactor = Double.parseDouble(txString.trim().substring(3));
							if(i==1) {
								calSpecTax = (baseFare*calCFactor/100)*this.noOFSegments*paxTypeFactor;
								totalFare = totalFare + calSpecTax;
								txFareString = this.formatFareWithDec(String.valueOf(decfor.format(calSpecTax)), decValu);
								fareMap.put("T1",txFareString);
								fareMap.put("T1CD",txCD);
							}else {
								txFareString = this.formatFareWithDec(String.valueOf(decfor.format((baseFare*calCFactor/100)*this.noOFSegments*paxTypeFactor)), decValu);
								longTxString = longTxString+txFareString+txCD;							
							}
						}else {
							break;
						}
						
					}
					txFareString = this.formatFareWithDec(String.valueOf(decfor.format(totalFare)), decValu);
					fareMap.put("TTL",txFareString);
					if(longTxString.trim().length()>0) {
						this.taxString = longTxString;
					}
					
				}
					/*
					fareMap.put("baseFare",String.valueOf(decfor.format(1279.00*this.noOFSegments*paxTypeFactor)));
					fareMap.put("T1",String.valueOf(decfor.format(81.50*this.noOFSegments*paxTypeFactor)));
					//fareMap.put("T1CD","PA");
					fareMap.put("T1CD",this.getTaxMap().get("txcdone"));
					fareMap.put("T2",String.valueOf(decfor.format(79.00*this.noOFSegments*paxTypeFactor)));
					//fareMap.put("T2CD","XT");
					fareMap.put("T1CD",this.getTaxMap().get("txcdtwo"));
					fareMap.put("TTL",String.valueOf(decfor.format(1439.50*this.noOFSegments*paxTypeFactor)));
					
					*/
			}
		
	
		return fareMap;
		
	}
	public void generateAvailabilityList(String response) throws Exception{
		 FileOutputStream fos=new FileOutputStream("C:\\testout.txt");
		 byte b[]=response.getBytes();
        fos.write(b);  
        fos.close();    
	}
	
	public String getPaxString() throws Exception{
		org = "PTY";
		des = "LAX";
		String cos = "Y";
		int arrLength;
		String tempStr;
		String fltSeq = "0";
		String paxEntry = null;
		boolean availabilityExists = false;
		File file=new File("C:\\testout.txt");    //creates a new file instance  
		FileReader fr=new FileReader(file);   //reads the file  
		BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
		StringBuffer sb=new StringBuffer();    //constructs a string buffer with no characters  
		String line;  
		//HashMap<String, String> bufferMap = new HashMap();
		List<String> bufferList = new ArrayList();
		/*while((line=br.readLine())!=null)  
		{  
		sb.append(line);      //appends line to string buffer  
		sb.append("\n");     //line feed   
		} */
		while((line=br.readLine())!=null) {
			bufferList.add(line);
		}
		arrLength = bufferList.size();
		int i = 0;
		while(i<arrLength){
			tempStr = bufferList.get(i).toString();
			if (tempStr.trim().length()>11){
				if (!tempStr.startsWith(" ")){
					fltSeq = tempStr.substring(0,1);
				}
				if(checkAvailabilityPerClass(tempStr, cos)){
					paxEntry = "N1"+ cos.concat(fltSeq);					
					break;
				}
				
			}
		i++;
		}
		fr.close();    //closes the stream and release the resources  
		//System.out.println("Contents of File: ");  
		//System.out.println(sb.toString()); 
		return paxEntry;
	}
	public boolean checkAvailabilityPerClass(String line, String cos) throws Exception{
		int i = 1;
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
	
	private int getNoOfTaxHdrs(String response) {
		StringTokenizer strTknzr = new StringTokenizer(response," ");		
		int i=0;
		while (strTknzr.hasMoreTokens()){
			if(strTknzr.nextToken().startsWith("TAX-")) {
				i++;
			}
		}
		
		return i;
	}
	
	//For getting lniata
	private String getOnlyLniata() {
		String termResp, lniata="";
		HashMap<String, String> lniataPOS = new HashMap<String, String>();
		try {
			LoginIShares loginIShares = new LoginIShares();
			driver = loginIShares.launchISHARESApplication(CommonProperties.envURL, false);
			loginIShares.logintoIShares(CommonProperties.logID, CommonProperties.passWord);
			HomePage homePage = new HomePage(driver);
			if (homePage.verifyHomePagedisplayed()) {
				homePage.selectTerminalEmulation();
				TerminalEmulation terminalEmulation = new TerminalEmulation(driver);
				terminalEmulation.runSingleLineEntry("LOGI CMRE");
				terminalEmulation.runSingleLineEntry("BSIB");
				terminalEmulation.runSingleLineEntry("RATS*");
				termResp = terminalEmulation.getResponse();
				lniata = this.getLniatafromRespose(termResp);								
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lniata;

	}
	private String getLniatafromRespose(String response) {
		String lniata = "", tmpStr;
		String ratsRecord;
		int indx;
		StringTokenizer strTknzr;
		strTknzr = new StringTokenizer(response, "\n");

		if (response != null && response.length() > 20) {
			if (strTknzr.hasMoreTokens()) {
				tmpStr = strTknzr.nextToken();
				indx = tmpStr.indexOf("LNIATA");
				ratsRecord = strTknzr.nextToken();
				if (ratsRecord.length() > 0) {
					lniata = ratsRecord.substring(indx, indx+6);
				}
			}
		}
		return lniata;
	}
	private HashMap<String, String> getTxMap(String response){
        String testString = response;
        String tmpString="", numString="";
        HashMap<String, String> txMap = new HashMap<>();
        String txCD="", txAmt="";
        int len, inNum;
        char testChar;
        inNum = testString.indexOf("XT");
        testString = testString.substring(inNum+2).trim();
        len = testString.length();
        int i = 0;
        while (i<len){
            testChar = testString.charAt(i);

            if(Character.isAlphabetic(testString.charAt(i))){
                if(numString.trim().length()>0){
                    //System.out.println("Not the End"+numString);
                    txAmt = numString;
                    numString="";
                }
                tmpString=tmpString+testString.charAt(i);
                if(tmpString.trim().length()==3){
                    //System.out.println("End of String"+tmpString);
                    tmpString="";
                    break;
                }else if(tmpString.trim().length()>0 && i+1==len ){
                    txCD = tmpString;
                    //System.out.println("End of String"+tmpString);
                    tmpString="";
                }

            }else if(Character.isDigit(testString.charAt(i))){
            	if(tmpString.length()==1){
                    //System.out.println("Not the End"+tmpString);
                    tmpString=tmpString+testString.charAt(i);
                }else{
                    numString = numString + testString.charAt(i);
                    if(tmpString.length()>0){
                        //System.out.println("Not the End"+tmpString);
                        txCD = tmpString;
                        tmpString="";                    
                    }
                }
            }else if(testString.charAt(i)=='.'){
                if(numString.length()>0){
                    numString = numString + testString.charAt(i);
                }
            }else if(testString.charAt(i)==','){
                //Nothing to do if it is a comma
            }else{
                if(tmpString.trim().length()>0){
                    //System.out.println("Not the End"+tmpString);
                    txCD = tmpString;
                    tmpString="";
                }
                if(numString.trim().length()>0){
                    //System.out.println("Not the End"+numString);
                    txAmt = numString;
                    numString="";
                }
            }
            if(txCD.trim().length()>0 && txAmt.trim().length()>0){
                txMap.put(txCD, txAmt);
                txCD = "";
                txAmt = "";
            }
            i++;
        }
        return txMap;
    }
	private String formatFareWithDec(String fareString, int decVal) {
		String formattedString="";
		int indOfDot=0,len;
		formattedString = fareString;
		if(decVal>0) {
			if(fareString.contains(".")) {
				indOfDot = fareString.indexOf(".");
				len = fareString.substring(indOfDot+1).length();
				if(len<decVal) {
					for (int i=0; i<decVal-len; i++) {
						formattedString = formattedString+"0";
					}
				}else if(len>decVal) {
					//formattedString = fareString;
					for (int i=0; i<len-decVal; i++) {
						formattedString = fareString.substring(0,fareString.length()-1);
					}
				}
			}else {
				formattedString=fareString+".";
				for (int i=0; i<decVal; i++) {
					formattedString = formattedString+"0";
				}
				
			}
		}else {
			//rare cases if decimal added where decimal is not required 
			if(fareString.contains(".")) {
				indOfDot = fareString.indexOf(".");
				formattedString = fareString.substring(0,indOfDot+1);				
			}
		}
		
		return formattedString;
	}
	
}
