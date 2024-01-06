package com.gds.pnr;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Vector;

import javax.print.attribute.standard.DateTimeAtCompleted;

import com.common.CommonProperties;
import com.model.TableDataService;

public class PNRTTYMessageGenerator {
	
	private HashMap<Integer, HashMap> paxMap;
	private HashMap<Integer, HashMap> segMap;
	private String GDSName;
	private int noOfPassengers, noOfSegments;
	private String fareType;
	private String paymentType;
	private HashMap<String, Object> gdsPNRTTYInputMap;
	private Connection conn;
	private TableDataService tableDataService;
	private List<String> paxList;
	private List<String> infantList;
	private List<String> infantOSList;
	private List<String> infantSSRList;
	private List<String> childSSRList;
	private List<String> ttyMessageList;
	private String gdsPnrNumber;
	
	
	
	public int getNoOfPassengers() {
		return noOfPassengers;
	}

	public void setNoOfPassengers(int noOfPassengers) {
		this.noOfPassengers = noOfPassengers;
	}
	public int getNoOfSegments() {
		return noOfSegments;
	}

	public void setNoOfSegments(int noOfSegments) {
		this.noOfSegments = noOfSegments;
	}
	public HashMap<Integer, HashMap> getPaxMap() {
		return paxMap;
	}

	public void setPaxMap(HashMap<Integer, HashMap> paxMap) {
		this.paxMap = paxMap;
	}

	public HashMap<Integer, HashMap> getSegMap() {
		return segMap;
	}

	public void setSegMap(HashMap<Integer, HashMap> segMap) {
		this.segMap = segMap;
	}

	public String getGDSName() {
		return GDSName;
	}

	public void setGDSName(String gDSName) {
		GDSName = gDSName;
	}
	
	public String getFareType() {
		return fareType;
	}

	public void setFareType(String fareType) {
		this.fareType = fareType;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public HashMap<String, Object> getGdsPNRTTYInputMap() {
		return gdsPNRTTYInputMap;
	}

	public void setGdsPNRTTYInputMap(HashMap<String, Object> gdsPNRTTYInputMap) {
		this.gdsPNRTTYInputMap = gdsPNRTTYInputMap;
	}
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	public List<String> getInfantList() {
		return infantList;
	}

	public void setInfantList(List<String> infantList) {
		this.infantList = infantList;
	}


	public PNRTTYMessageGenerator() {
		initiateDBService();
	}

	public List<String> getPNRCreateTTYMessage() {
		List<String> fullTTYMessage;
		Iterator<String> iter1;
		ListIterator<String> lIter;
		String tempString;
		int cntRec=0;
		
		try {	
			fullTTYMessage = new ArrayList<String>();			
			getInitialTTYString("NN");
			cntRec=this.ttyMessageList.size();
			fullTTYMessage = this.ttyMessageList;
			//CHD SSR
			if(assignTTYInputs()==true) {
				if(this.childSSRList!=null && this.childSSRList.size()>0) {
					lIter=this.childSSRList.listIterator();
					while (lIter.hasNext()) {
						if(cntRec<26 ){
							fullTTYMessage.add(lIter.next());	
							cntRec++;							
						}else if (cntRec%24==1) {							
							getInitialTTYString("HK");							
							iter1= this.ttyMessageList.iterator();
							while(iter1.hasNext()) {
								fullTTYMessage.add(iter1.next());
								cntRec++;
							}							
							fullTTYMessage.add(lIter.next());
							cntRec++;
						}						
					}
				}			
			
				//INF
				if(this.getInfantSSREntries(this.infantList)!=null && this.getInfantSSREntries(this.infantList).size()>0) {
					lIter=this.getInfantSSREntries(this.infantList).listIterator();
					
					while (lIter.hasNext()) {
						tempString = lIter.next();	
						if(cntRec<25 ){							
							
						}else if(cntRec%24==1) {
							getInitialTTYString("HK");
							iter1= this.ttyMessageList.iterator();
							while(iter1.hasNext()) {
								fullTTYMessage.add(iter1.next());
								cntRec++;
							}
						}
						
						if(tempString.length()>63) {
							if(cntRec%24 < 23 && cntRec%24 > 1) {
								fullTTYMessage.add(tempString.substring(0, 63));
								cntRec++;
								fullTTYMessage.add("SSRINFTCM///"+tempString.substring(63, tempString.length()));
								cntRec++;
							}else {
								lIter.previous();
								cntRec++;
							}							
						}else {
							fullTTYMessage.add(tempString);
							cntRec++;
						}
					}
				}
				//INS
				if(this.getInfantSSREntries(this.infantOSList)!=null && this.getInfantSSREntries(this.infantOSList).size()>0) {
					lIter=this.getInfantSSREntries(this.infantOSList).listIterator();
					
					while (lIter.hasNext()) {
						tempString = lIter.next();	
						if(cntRec<25 ){							
							
						}else if (cntRec%24==1) {
							getInitialTTYString("HK");
							iter1= this.ttyMessageList.iterator();
							while(iter1.hasNext()) {
								fullTTYMessage.add(iter1.next());
								cntRec++;
							}
						}
						if(tempString.length()>63) {
							if(cntRec%24 < 23  && cntRec%24 > 1) {
								fullTTYMessage.add(tempString.substring(0, 63));
								cntRec++;
								fullTTYMessage.add("SSRINFTCM///"+tempString.substring(63, tempString.length()));
								cntRec++;
							}else {
								lIter.previous();
								cntRec++;
							}
						}else {
							fullTTYMessage.add(tempString);
							cntRec++;
						}
					}
				}
			}
			return fullTTYMessage;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getSSRTKNETTYMessage() {
		List<String> fullTTYMessage;
		Iterator<String> iter1, keyIterator;
		ListIterator<String> lIter,  lisIter;
		String tempString, givName, surName;
		List<String> ssrTKNEMsgList;
		int cntRec=0;
		HashMap<String, String> individualPaxDetails = new HashMap<String, String>();
		HashMap<String, List<String>> ssrTKNEMsgs = new HashMap<String, List<String>>();
		try {	
			fullTTYMessage = new ArrayList<String>();			
			getInitialTTYString("HK");
			cntRec=this.ttyMessageList.size();
			fullTTYMessage = this.ttyMessageList;
			
			if(assignTTYInputs()==true) {
				ssrTKNEMsgs = this.getSSRTKNEMessages();
				if(ssrTKNEMsgs!=null && !ssrTKNEMsgs.isEmpty()) {
					for (int i=1; i<=this.getPaxMap().size(); i++) {
						individualPaxDetails = this.getPaxMap().get(Integer.valueOf(i)); 
						surName = individualPaxDetails.get("paxSurName").toUpperCase();
						givName = individualPaxDetails.get("paxGivName").toUpperCase();
						ssrTKNEMsgList = ssrTKNEMsgs.get(surName+"/"+givName);
						if(ssrTKNEMsgList!=null && !ssrTKNEMsgList.isEmpty()) {
							lisIter=ssrTKNEMsgList.listIterator();
							while(lisIter.hasNext()) {
								if(cntRec<26 ){
									fullTTYMessage.add(lisIter.next());	
									cntRec++;							
								}else if (cntRec%24==1) {							
									getInitialTTYString("HK");							
									fullTTYMessage.add(lisIter.next());
									cntRec++;
							
									fullTTYMessage.add(lisIter.next());
									cntRec++;
								}	
							}
						}
						
					}
				}
				//INF
				if(this.getInfantSSREntries(this.infantList)!=null && this.getInfantSSREntries(this.infantList).size()>0) {
					lIter=this.getInfantSSREntries(this.infantList).listIterator();
					
					while (lIter.hasNext()) {
						tempString = lIter.next();	
						if(cntRec<25 ){							
							
						}else if(cntRec%24==1) {
							getInitialTTYString("HK");
							iter1= this.ttyMessageList.iterator();
							while(iter1.hasNext()) {
								fullTTYMessage.add(iter1.next());
								cntRec++;
							}
						}
						
						if(tempString.length()>63) {
							if(cntRec%24 < 23 && cntRec%24 > 1) {
								fullTTYMessage.add(tempString.substring(0, 63));
								cntRec++;
								fullTTYMessage.add("SSRINFTCM///"+tempString.substring(63, tempString.length()));
								cntRec++;
							}else {
								lIter.previous();
								cntRec++;
							}							
						}else {
							fullTTYMessage.add(tempString);
							cntRec++;
						}
					}
				}
			}
			return fullTTYMessage;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public HashMap<String, List<String>> getSSRTKNEMessages(){
		HashMap<String, List<String>> ssrTkneMsgs = new HashMap<String, List<String>> ();
		Iterator iter, strIter;
		HashMap<String, String> individualPaxDetails = new HashMap<String, String> ();
		int i=1;
		String surName, givName, strTKNEMsg;
		List<String> ssrTKNE, listSSRTKNE;
		try {
			if(CommonProperties.ssrTKNEMsgs!=null && !CommonProperties.ssrTKNEMsgs.isEmpty()) {
				while (i<=this.getPaxMap().size()) {
					individualPaxDetails = this.getPaxMap().get(Integer.valueOf(i)); 
					surName = individualPaxDetails.get("paxSurName").toUpperCase();
					givName = individualPaxDetails.get("paxGivName").toUpperCase();
					listSSRTKNE = new ArrayList<String>();
					iter = CommonProperties.ssrTKNEMsgs.keySet().iterator();
					while (iter.hasNext()) {
						ssrTKNE = (ArrayList<String>)CommonProperties.ssrTKNEMsgs.get(iter.next());
						strIter = ssrTKNE.iterator();
						while (strIter.hasNext()) {
							strTKNEMsg = strIter.next().toString();
							if(strTKNEMsg.contains("surName") && strTKNEMsg.contains("givName")) {
								listSSRTKNE.add(strTKNEMsg);
							}
						}
						
					}
					ssrTkneMsgs.put(surName+"/"+givName, listSSRTKNE);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ssrTkneMsgs;
		
	}
	public void getInitialTTYString(String boookingCode) {
		String ttyMessage = null, paxRecord="";	
		List<String> paxList, segList;
		Iterator<String> iter;
		String tempString;
		boolean paxRecAdd=false;
		try {
			initiateDBService();	
			int cnt=0;
			ttyMessageList=new ArrayList<String>();
			ttyMessageList.add("Y");
			//ttyMessage = "I	Y";
			
			if(assignTTYInputs()==true) {
				ttyMessageList.add(this.getHostAddress());
				ttyMessageList.add("." + this.getGDSAddress());
				if(this.gdsPnrNumber!=null && this.gdsPnrNumber.length()>0) {
					//Nothing to do here
				}else {
					this.gdsPnrNumber = this.getGDSPNR();
					
				}
				ttyMessageList.add((this.getGDSAddress().replace("RM", "")) + " " + this.gdsPnrNumber);
				//ttyMessage = ttyMessage +"</br>M	" + this.getHostAddress();
				//ttyMessage = ttyMessage +"</br>M	." + this.getGDSAddress();				
				//ttyMessage = ttyMessage +"</br>M	" + (this.getGDSAddress().replace("RM", "")) + " " + this.getGDSPNR();
				paxList = this.getPaxEntries();
				
				//Code to include more than 1 pax in 1 row
				iter = paxList.iterator();
				while (iter.hasNext()) {
					paxRecAdd = false;
					tempString=iter.next();
					if((paxRecord.length() + tempString.length() + 1) < 63) {
						paxRecord = paxRecord +" "+ tempString;
					}else {
						ttyMessageList.add(paxRecord);
						paxRecAdd=true;
						paxRecord=tempString;
					}								
				}
				if(!paxRecAdd && paxRecord.length()>0) {
					ttyMessageList.add(paxRecord);
				}
				
				//End of code
				
				//Above block is replace of below block
				/*
				iter = paxList.iterator();
				while (iter.hasNext()) {
					ttyMessageList.add(iter.next());
					//ttyMessage = ttyMessage +"</br>M	" + iter.next();				
				}*/
				segList = this.createSegEntries(boookingCode);
				iter = segList.iterator();
				while (iter.hasNext()) {
					ttyMessageList.add(iter.next());
					//ttyMessage = ttyMessage +"</br>M	" + iter.next();				
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public String getGDSAddress() {
		String address = null, carrierCode;
		int positionDelimiter;
		try {
			positionDelimiter=this.getGDSName().indexOf("(");
			carrierCode = this.getGDSName().substring(positionDelimiter+1, positionDelimiter+3);
			address = this.tableDataService.getOAGDSAddress(carrierCode);
			return address;
		}catch (Exception e) {
			e.printStackTrace();
			return address;
		}
	}
	public String getHostAddress() {
		String address = null, carrierCode="CM";		
		try {			
			address = this.tableDataService.getOAGDSAddress(carrierCode);
			return address;
		}catch (Exception e) {
			e.printStackTrace();
			return address;
		}
	}
	public boolean assignTTYInputs() {
		boolean assignedTTYInputs = false;
		try {
			if (this.gdsPNRTTYInputMap!=null && this.gdsPNRTTYInputMap.size()>0) {
				this.setGDSName(this.gdsPNRTTYInputMap.get("gdsName").toString());
				this.setNoOfPassengers(Integer.parseInt(this.gdsPNRTTYInputMap.get("noOfPax").toString()));
				this.setNoOfSegments(Integer.parseInt(this.gdsPNRTTYInputMap.get("noOfsegments").toString()));
				this.setSegMap((HashMap<Integer, HashMap>)this.gdsPNRTTYInputMap.get("segMap"));
				this.setPaxMap((HashMap<Integer, HashMap>)this.gdsPNRTTYInputMap.get("paxMap"));
				this.setFareType(this.gdsPNRTTYInputMap.get("fareType").toString());
				this.setPaymentType(this.gdsPNRTTYInputMap.get("paymentType").toString());
				assignedTTYInputs = true;
			}
			return assignedTTYInputs;
		}catch (Exception e) {
			e.printStackTrace();
			return assignedTTYInputs;
		}
	}
	public void initiateDBService() {
		try {
			tableDataService = new TableDataService(this.conn);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private List<String> getPaxEntries(){
		try {
			List<String> paxList = new ArrayList<String>();
			String paxName, infString, chdSSR;
			HashMap<String, String> individualPaxDetails = new HashMap<String, String>();
			int i=1;
			
			this.infantList = new ArrayList<String>();
			this.infantOSList = new ArrayList<String>();
			this.childSSRList = new ArrayList<String>();
			
			while (i<=this.getPaxMap().size()) {
				individualPaxDetails = this.getPaxMap().get(Integer.valueOf(i)); 
				paxName = "1"+individualPaxDetails.get("paxSurName").toUpperCase()+"/"+individualPaxDetails.get("paxGivName").toUpperCase();
				paxList.add(paxName);
				//paxType
				if(individualPaxDetails.get("paxType").equalsIgnoreCase("ADT") && individualPaxDetails.get("hasInfant").equalsIgnoreCase("YES")) {
					//1GEORGE/GERR.GEORGE/SUNNY.19MAR22
					infString=paxName+"."+individualPaxDetails.get("infSurName").toUpperCase()+"/"+individualPaxDetails.get("infGivName").toUpperCase() +"."
							+ individualPaxDetails.get("infDOB");
					this.infantList.add(infString);
				}else if (individualPaxDetails.get("paxType").equalsIgnoreCase("INS")){
					infString = paxName+"."+individualPaxDetails.get("infDOB")+" OCCUPYING SEAT";
					this.infantOSList.add(infString);					
				}else if (individualPaxDetails.get("paxType").equalsIgnoreCase("CHD")){
					chdSSR = "SSRCHLDYYHK" +this.noOfPassengers +"-"+ paxName;
					this.childSSRList.add(chdSSR);
				}
				i++;
			}
			return paxList;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private List<String> getSSRTKNEPaxEntries(){
		try {
			List<String> paxList = new ArrayList<String>();
			String paxName, infString, chdSSR;
			HashMap<String, String> individualPaxDetails = new HashMap<String, String>();
			int i=1;
			
			this.infantList = new ArrayList<String>();
			this.infantOSList = new ArrayList<String>();
			this.childSSRList = new ArrayList<String>();
			
			while (i<=this.getPaxMap().size()) {
				individualPaxDetails = this.getPaxMap().get(Integer.valueOf(i)); 
				paxName = "1"+individualPaxDetails.get("paxSurName").toUpperCase()+"/"+individualPaxDetails.get("paxGivName").toUpperCase();
				paxList.add(paxName);
				//paxType
				if(individualPaxDetails.get("paxType").equalsIgnoreCase("ADT") && individualPaxDetails.get("hasInfant").equalsIgnoreCase("YES")) {
					//1GEORGE/GERR.GEORGE/SUNNY.19MAR22
					infString=paxName+"."+individualPaxDetails.get("infSurName").toUpperCase()+"/"+individualPaxDetails.get("infGivName").toUpperCase() +"."
							+ individualPaxDetails.get("infDOB");
					this.infantList.add(infString);
				}else if (individualPaxDetails.get("paxType").equalsIgnoreCase("INS")){
					infString = paxName+"."+individualPaxDetails.get("infDOB")+" OCCUPYING SEAT";
					this.infantOSList.add(infString);					
				}else if (individualPaxDetails.get("paxType").equalsIgnoreCase("CHD")){
					chdSSR = "SSRCHLDYYHK" +this.noOfPassengers +"-"+ paxName;
					this.childSSRList.add(chdSSR);
				}
				i++;
			}
			return paxList;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private List<String> createSegEntries(String boookingCode){
		try {
			List<String> segmentsList = new ArrayList<String>();
			String segEntry, tmpString;
			int tempPos;
			HashMap<String, String> indSegDetails = new HashMap<String, String>();
			int i=1;
			while (i<=this.getSegMap().size()) {
				indSegDetails = this.getSegMap().get(Integer.valueOf(i)); 
				if(indSegDetails.get("airlineCode")!=null && indSegDetails.get("airlineCode").toString().length()>0) {
					if(indSegDetails.get("airlineCode").toString().length() > 2 && indSegDetails.get("airlineCode").toString().contains("(")) {
						tempPos = indSegDetails.get("airlineCode").toString().indexOf("(");
						tmpString = indSegDetails.get("airlineCode").substring(0, tempPos).toUpperCase().trim();
					}else {
						tmpString = indSegDetails.get("airlineCode").toString().trim();
					}
				}else {
					tmpString="  ";
				}
				segEntry=""+tmpString;
				
				tmpString = indSegDetails.get("fltNo").toString();
				while(tmpString.length() < 4) {
					tmpString = "0"+tmpString;
				}
				segEntry=segEntry+tmpString;
				segEntry=segEntry+indSegDetails.get("cosCode").toString().toUpperCase();
				segEntry=segEntry+indSegDetails.get("DtOfJrny").toString().toUpperCase();
				if (indSegDetails.get("org")!=null && indSegDetails.get("org").toString().length()>0) {
					if (indSegDetails.get("org").toString().contains("(")) {
						tempPos = indSegDetails.get("org").toString().indexOf("(");
						tmpString = indSegDetails.get("org").substring(0, tempPos).toUpperCase().trim();
					}else {
						tmpString = indSegDetails.get("org").toString().toUpperCase().trim();
					}
				}else {
					tmpString="   ";
				}
				segEntry=segEntry+tmpString;
				
				if (indSegDetails.get("des")!=null && indSegDetails.get("des").toString().length()>0) {
					if (indSegDetails.get("des").toString().contains("(")) {
						tempPos = indSegDetails.get("des").toString().indexOf("(");
						tmpString = indSegDetails.get("des").substring(0, tempPos).toUpperCase().trim();
					}else {
						tmpString = indSegDetails.get("des").toString().toUpperCase().trim();
					}
				}else {
					tmpString="   ";
				}
				segEntry=segEntry+tmpString;
				segEntry=segEntry+boookingCode+this.noOfPassengers;				
				segmentsList.add(segEntry);
				i++;
			}
			return segmentsList;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private List<String> getInfantSSREntries(List<String> infantList){
		try {
			List<String> infantSSRList = new ArrayList<String>();
			String infantSSREntry, tmpString, passengerNameEntry;
			int tempPos;
			Iterator<String> iter;
			HashMap<String, String> indSegDetails = new HashMap<String, String>();
			if(this.infantSSRList==null) {
				this.infantSSRList=new ArrayList<String>();
			}
			if (infantList!=null && infantList.size()>0) {
				iter = infantList.iterator();
				while(iter.hasNext()) {
					passengerNameEntry = iter.next();
					int i=1;
					while (i<=this.getSegMap().size()) {
						indSegDetails = this.getSegMap().get(Integer.valueOf(i)); 
						if(indSegDetails.get("airlineCode")!=null && indSegDetails.get("airlineCode").toString().length()>0) {
							if(indSegDetails.get("airlineCode").toString().length() > 2 && indSegDetails.get("airlineCode").toString().contains("(")) {
								tempPos = indSegDetails.get("airlineCode").toString().indexOf("(");
								tmpString = indSegDetails.get("airlineCode").substring(0, tempPos).toUpperCase().trim();
							}else {
								tmpString = indSegDetails.get("airlineCode").toString().trim();
							}
						}else {
							tmpString="  ";
						}
						infantSSREntry="SSRINFT"+tmpString+"HK"+this.noOfPassengers;
						
						if (indSegDetails.get("org")!=null && indSegDetails.get("org").toString().length()>0) {
							if (indSegDetails.get("org").toString().contains("(")) {
								tempPos = indSegDetails.get("org").toString().indexOf("(");
								tmpString = indSegDetails.get("org").substring(0, tempPos).toUpperCase().trim();
							}else {
								tmpString = indSegDetails.get("org").toString().toUpperCase().trim();
							}
						}else {
							tmpString="   ";
						}
						infantSSREntry=infantSSREntry+tmpString;
						
						if (indSegDetails.get("des")!=null && indSegDetails.get("des").toString().length()>0) {
							if (indSegDetails.get("des").toString().contains("(")) {
								tempPos = indSegDetails.get("des").toString().indexOf("(");
								tmpString = indSegDetails.get("des").substring(0, tempPos).toUpperCase().trim();
							}else {
								tmpString = indSegDetails.get("des").toString().toUpperCase().trim();
							}
						}else {
							tmpString="   ";
						}
						infantSSREntry=infantSSREntry+tmpString;
						
						tmpString = indSegDetails.get("fltNo").toString();
						while(tmpString.length() < 4) {
							tmpString = "0"+tmpString;
						}
						infantSSREntry=infantSSREntry+tmpString;
						infantSSREntry=infantSSREntry+indSegDetails.get("cosCode").toString().toUpperCase();
						infantSSREntry=infantSSREntry+indSegDetails.get("DtOfJrny").toString().toUpperCase();
						infantSSREntry = infantSSREntry+"-"+passengerNameEntry;
						
						infantSSRList.add(infantSSREntry);
						i++;
					}
				}
			}
			return infantSSRList;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	private String getGDSPNR() {
		String gdsPNR, dteString;
		Date dte = new Date();
		SimpleDateFormat smpDtFrmt = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat smpTimeFrmt = new SimpleDateFormat("hmmss");
		dteString = smpDtFrmt.format(dte);
		
		int mnth = Calendar.MONTH;
		Random random = new Random();
				
		gdsPNR = getCharForNumber(mnth)+""+getString(Long.parseLong(dteString))+""+getString(Long.parseLong(dteString))
		+""+(random.nextInt(900)+100);
		
		return gdsPNR;
	}
	
	private String getCharForNumber(int i) {
	    return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
	}
	
	private String getString(long num) {
		long tempNum =0;
		
		
		do {
			tempNum = tempNum + (num%10);
			num=num/10;
		}while(num > 0);
		
		while (tempNum > 26) {
			tempNum = tempNum - 26;
		}
		return getCharForNumber( (int)tempNum);
	}

}
