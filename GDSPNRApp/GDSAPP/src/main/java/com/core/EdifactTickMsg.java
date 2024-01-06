package com.core;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import com.common.CommonProperties;
import com.model.TableDataService;
import com.utilities.FileUtility;
import com.utilities.TicketGenerator;

public class EdifactTickMsg {
	
	private HashMap<String, Object> paxMap;
	private HashMap<String, Object> segMap;	
	private String gdsCode;
	private String gdsPNR;
	private String hostPNR;
	private String fareType;
	private String paymentType;
	private String baseFare;
	private String totalFare;
	private String currencyCode;
	private String totalTax;
	private int noOfPax;	
	private int noOfSeg;
	private String gdsOACode;
	private String gdsCityCode;
	private TableDataService tableDataService;
	private String cardNo;	
	private String cardType;
	private String cardTypeCode;
	private String cardExpDate;
	private boolean splitPayment;
	//private String secFareType;
	private String secPaymentType;
	private String secCardNo;	
	private String secCardType;
	private String secCardTypeCode;
	private String secCardExpDate;
	private String splitFirstPayPercent;
	private String splitSecondPayPercent;
	private String nvaIgnore;	
	private HashMap<String, HashMap<String, String>> fltTime;
	private HashMap<String,HashMap<String, HashMap>> fareMap;
	private HashMap<String,String> ssrTKNEPaxnames;
	private TicketGenerator ticketGenerator;
	
	
	public String getSplitFirstPayPercent() {
		return splitFirstPayPercent;
	}


	public String getSplitSecondPayPercent() {
		return splitSecondPayPercent;
	}


	public void setSplitFirstPayPercent(String splitFirstPayPercent) {
		this.splitFirstPayPercent = splitFirstPayPercent;
	}


	public void setSplitSecondPayPercent(String splitSecondPayPercent) {
		this.splitSecondPayPercent = splitSecondPayPercent;
	}
	public boolean isSplitPayment() {
		return splitPayment;
	}


	public void setSplitPayment(boolean splitPayment) {
		this.splitPayment = splitPayment;
	}
	/*
	public String getSecFareType() {
		return secFareType;
	}

	*/
	public String getSecPaymentType() {
		return secPaymentType;
	}
	

	public String getSecCardNo() {
		return secCardNo;
	}


	public String getSecCardType() {
		return secCardType;
	}


	public String getSecCardTypeCode() {
		return secCardTypeCode;
	}


	public String getSecCardExpDate() {
		return secCardExpDate;
	}


	/*public void setSecFareType(String secFareType) {
		this.secFareType = secFareType;
	}*/


	public void setSecPaymentType(String secPaymentType) {
		this.secPaymentType = secPaymentType;
	}


	public void setSecCardNo(String secCardNo) {
		this.secCardNo = secCardNo;
	}


	public void setSecCardType(String secCardType) {
		this.secCardType = secCardType;
	}


	public void setSecCardTypeCode(String secCardTypeCode) {
		this.secCardTypeCode = secCardTypeCode;
	}


	public void setSecCardExpDate(String secCardExpDate) {
		this.secCardExpDate = secCardExpDate;
	}
	public String getNvaIgnore() {
		return nvaIgnore;
	}


	public void setNvaIgnore(String nvaIgnore) {
		this.nvaIgnore = nvaIgnore;
	}
	public HashMap<String, String> getSsrTKNEPaxnames() {
		return ssrTKNEPaxnames;
	}


	public void setSsrTKNEPaxnames(HashMap<String, String> ssrTKNEPaxnames) {
		this.ssrTKNEPaxnames = ssrTKNEPaxnames;
	}


	public HashMap<String, HashMap<String, HashMap>> getFareMap() {
		return fareMap;
	}


	public void setFareMap(HashMap<String, HashMap<String, HashMap>> fareMap) {
		this.fareMap = fareMap;
	}


	public String getCardExpDate() {
		return cardExpDate;
	}


	public void setCardExpDate(String cardExpDate) {
		this.cardExpDate = cardExpDate;
	}


	public String getCardTypeCode() {
		return cardTypeCode;
	}


	public void setCardTypeCode(String cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}


	public String getCardNo() {
		return cardNo;
	}


	public String getCardType() {
		return cardType;
	}


	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	public int getNoOfPax() {
		return noOfPax;
	}


	public void setNoOfPax(int noOfPax) {
		this.noOfPax = noOfPax;
	}


	public int getNoOfSeg() {
		return noOfSeg;
	}


	public void setNoOfSeg(int noOfSeg) {
		this.noOfSeg = noOfSeg;
	}
	
	public HashMap<String, Object> getPaxMap() {
		return paxMap;
	}


	public void setPaxMap(HashMap<String, Object> paxMap) {
		this.paxMap = paxMap;
	}

	public HashMap<String, Object> getSegMap() {
		return segMap;
	}


	public void setSegMap(HashMap<String, Object> segMap) {
		this.segMap = segMap;
	}
	public String getGdsCode() {
		return gdsCode;
	}


	public void setGdsCode(String gdsCode) {
		this.gdsCode = gdsCode;
	}


	public String getGdsPNR() {
		return gdsPNR;
	}


	public void setGdsPNR(String gdsPNR) {
		this.gdsPNR = gdsPNR;
	}


	public String getHostPNR() {
		return hostPNR;
	}


	public void setHostPNR(String hostPNR) {
		this.hostPNR = hostPNR;
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


	public String getBaseFare() {
		return baseFare;
	}


	public void setBaseFare(String baseFare) {
		this.baseFare = baseFare;
	}


	public String getTotalFare() {
		return totalFare;
	}


	public void setTotalFare(String totalFare) {
		this.totalFare = totalFare;
	}


	public String getCurrencyCode() {
		return currencyCode;
	}


	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}


	public String getTotalTax() {
		return totalTax;
	}


	public void setTotalTax(String totalTax) {
		this.totalTax = totalTax;
	}
	
	public EdifactTickMsg(Connection connection){
		tableDataService = new TableDataService(connection);
	}
	
	
	public boolean setTickMsgParam(HashMap<String, Object> msgParam) {
		boolean msgParamSet = false;
		HashMap<String, String> taxMap;
		try {			
			if(msgParam!=null) {
				this.setNoOfPax(Integer.parseInt(msgParam.get("noOfPax").toString()));
				this.setNoOfSeg(Integer.parseInt(msgParam.get("noOfsegments").toString()));
				this.setGdsCode(msgParam.get("gdsName").toString());
				this.setPaxMap((HashMap<String,Object>)msgParam.get("paxMap"));
				this.setSegMap((HashMap<String,Object>)msgParam.get("segMap"));
				this.setGdsPNR(msgParam.get("gdsPNR").toString());
				this.setHostPNR(msgParam.get("hostPNR").toString());
				this.setCardNo(msgParam.get("cardNo").toString());
				this.setCardType(msgParam.get("cardType").toString());
				this.setCardTypeCode(tableDataService.getCardCode(this.cardNo));
				this.setCardExpDate(tableDataService.getCardExpDate(this.cardNo));	
				this.setSecCardNo(msgParam.get("secCardNo").toString());
				this.setSecCardType(msgParam.get("secCardType").toString());
				if(this.secCardNo.length()>0)
				this.setSecCardTypeCode(tableDataService.getCardCode(this.secCardNo));
				
				if(this.secCardNo.length()>0)
				this.setSecCardExpDate(tableDataService.getCardExpDate(this.secCardNo));
				
				//taxMap = tableDataService.getTaxCd(CommonProperties.pos, CommonProperties.currency);
				FarePricesCollector farPrcColl = new FarePricesCollector(tableDataService);
				//farPrcColl.setTaxMap(taxMap);
				this.setFareMap(farPrcColl.getFarePrices(this.getSegMap()));
				this.fltTime = farPrcColl.getFltTime();
				
				msgParamSet=true;
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return msgParamSet;
	}
	
	public HashMap<String, HashMap<String,List<String>>> generateEdifactMessages() {
		HashMap<String, String> individualPaxMap = new HashMap<String, String>();
		HashMap<String, HashMap<String,List<String>>> edifactMessagesMap = new HashMap<String, HashMap<String,List<String>>>();
		Iterator<String> iter;
		String firstName,lastName,paxType, hasInfant;
		
		try {
			FileUtility flUtility = new FileUtility();
			CommonProperties.ssrTKNEMsgs = new HashMap();
			iter = this.getPaxMap().keySet().iterator();
			ticketGenerator = new TicketGenerator();
			int i=0;
			while (iter.hasNext()) {
				individualPaxMap = (HashMap<String, String>)this.getPaxMap().get(iter.next());
				ssrTKNEPaxnames = new HashMap<String, String>();
				lastName = individualPaxMap.get("paxSurName").toString();
				firstName = individualPaxMap.get("paxGivName").toString();
				paxType = individualPaxMap.get("paxType").toString();
				//for ssrTKNE
				ssrTKNEPaxnames.put("lastName", lastName);
				ssrTKNEPaxnames.put("firstName", firstName);
				ssrTKNEPaxnames.put("paxType", paxType);
				edifactMessagesMap.put(""+String.valueOf(i+1), this.getEdifactMessages(firstName, lastName, paxType, this.getSegMap()));
				i++;
				if (paxType.equalsIgnoreCase("ADT")) {
					hasInfant = individualPaxMap.get("hasInfant");
					if(hasInfant.equalsIgnoreCase("Yes")) {						
						lastName = individualPaxMap.get("infSurName").toString();
						firstName = individualPaxMap.get("infGivName").toString();						
						paxType = "INF";
						//for ssrTKNE
						ssrTKNEPaxnames.remove("paxType");
						ssrTKNEPaxnames.put("paxType", paxType);
						edifactMessagesMap.put(""+String.valueOf(i+1), this.getEdifactMessages(firstName, lastName, paxType, this.getSegMap()));
						i++;
					}
				}	
				
			}
			flUtility.writeEdifactToFile("Edifact Ticketing message(s)", edifactMessagesMap);
			ticketGenerator.closeISHARES();
		}catch(Exception e) {			
			e.printStackTrace();
		}
		return edifactMessagesMap;
	}
	
	
	public HashMap<String, List<String>> getEdifactMessages(String firstName,String lastName, String paxType,HashMap<String, Object> segMap ) {
		HashMap<String, List<String>> edifactMessageMap = new HashMap<String, List<String>>();
		List<String> edifactMessage = new ArrayList<String>();
		List<String> ssrTKNEMsgsList = new ArrayList<String>();
		
		//HashMap<String,HashMap<String, HashMap>> fareMap;
		HashMap<String, String> echSegDtls, paxFareBagDtls, fltTimeDtls;
		//FarePricesCollector farPrcColl;
		int noOfSeg=0;
		String seatCode="OK", fltNo="", tvlDate="", arrTime="", depTime="", arrDate="", carrierCode, depCity, arrCity, ssrTKNEMsg="", infCode="";
		int i=1; int lineCnt=0;
		long ticketNumber=0;
		try {
			if(segMap!=null && segMap.size()>0) {
				noOfSeg = segMap.size();
				if(paxType.equalsIgnoreCase("INF")) {
					seatCode="NS";
				}			
				edifactMessage = this.getInitPrtTestEdifactMessage(firstName, lastName, paxType, (HashMap<String, String>)this.fareMap.get("TCKT1").get(paxType), this.fareMap.get("TCKT1").get(paxType+"_TX"));
				//TicketGenerator tickGen = new TicketGenerator(); 
				ticketNumber = ticketGenerator.getTicketNumber();
				edifactMessage.add("M	TKT+"+String.valueOf(ticketNumber)+":T:1:3");
				lineCnt = edifactMessage.size();
				for(i=1; i<=noOfSeg; i++) {
						echSegDtls = (HashMap<String, String>)segMap.get(i);
						
					if(i<=4 && echSegDtls!=null && echSegDtls.size()>0) {	
						
						//Commented as the new method -addPZAEntry(); introduced for adding PZA/ADD or PZA/END lines for EDIFACT message
						//if(lineCnt%14==0) {edifactMessage.add("I	PZA/END");}
						
						edifactMessage.add("M	CPN+"+String.valueOf(i)+":I");
						lineCnt++;
						
						//Commented as the new method -addPZAEntry(); introduced for adding PZA/ADD or PZA/END lines for EDIFACT message
						//if(lineCnt%14==0) {edifactMessage.add("I	PZA/END");}
						
						fltNo= echSegDtls.get("fltNo").trim();
						while(fltNo.length()<4) {
							fltNo = "0"+fltNo;
						}
						
						fltTimeDtls=(HashMap<String,String>)this.fltTime.get(fltNo);
						depTime = fltTimeDtls.get("depTime");
						arrTime = fltTimeDtls.get("arrTime");
						tvlDate = this.getEdifactDate(echSegDtls.get("DtOfJrny"));
						carrierCode = this.getAirlineCode(echSegDtls.get("airlineCode"));
						depCity =  this.getCityCode(echSegDtls.get("org"));
						arrCity =  this.getCityCode(echSegDtls.get("des"));
						
						if(this.nvaIgnore!=null && this.nvaIgnore.length()>0 && this.nvaIgnore.equalsIgnoreCase("Yes")) {
							arrDate="";
						}else {
							arrDate = "+A:"+tvlDate;
						}
						
						edifactMessage.add("M	TVL+"+tvlDate+":"+depTime+"+"+depCity+"+"+arrCity+"+"+carrierCode+"+"+echSegDtls.get("fltNo").trim()+":"+echSegDtls.get("cosCode")+"+J+1");
						lineCnt++;
						
						
						edifactMessage.add("M	RPI++"+seatCode);
						lineCnt++;
						
						paxFareBagDtls = this.getFareCodesAndBaggage(paxType, echSegDtls.get("cosCode").trim().toUpperCase());
						edifactMessage.add("M	PTS++"+paxFareBagDtls.get("fareCode"));
						lineCnt++;
						
						
						edifactMessage.add("M	EBD++"+paxFareBagDtls.get("bagCnt")+"::N");
						lineCnt++;
						
						
						edifactMessage.add("M	DAT+B:"+tvlDate+""+arrDate);
						lineCnt++;
						
						
						//For ssr TKNE
						if(paxType.equalsIgnoreCase("INF")){
							infCode=paxType;
						}						
						ssrTKNEMsg="SSRTKNE"+carrierCode+"HK1"+depCity+""+arrCity+""+fltNo+""+echSegDtls.get("cosCode")+""+echSegDtls.get("DtOfJrny")+"-";						
						ssrTKNEMsg=ssrTKNEMsg+"1"+this.ssrTKNEPaxnames.get("lastName")+"/"+this.ssrTKNEPaxnames.get("firstName")+"."+infCode+""+ticketNumber+"C"+String.valueOf(i);
						ssrTKNEMsgsList.add(ssrTKNEMsg);
					}
					
				}
				edifactMessage.add("M	UNT+"+lineCnt+"+1");
				edifactMessage.add("M	UNZ+1+01C0VAXPVZ0001");
				
				//Adding PZA/ADD and PZA/END entries
				edifactMessage = addPZAEntry(edifactMessage);
				edifactMessageMap.put("1",edifactMessage);
				
				CommonProperties.ssrTKNEMsgs.put(paxType+"-"+String.valueOf(ticketNumber), ssrTKNEMsgsList);
				
				
				if(noOfSeg>4) {
					edifactMessage = this.getInitPrtTestEdifactMessage(firstName, lastName, paxType, (HashMap<String, String>)this.fareMap.get("TCKT2").get(paxType), this.fareMap.get("TCKT2").get(paxType+"_TX"));
					ssrTKNEMsgsList = new ArrayList<String>();
					ticketNumber=ticketNumber+1;
					while(ticketGenerator.verifyTicketNumberExists(ticketNumber)) {
						ticketNumber = ticketNumber+1;
					}
					edifactMessage.add("M	TKT+"+String.valueOf(ticketNumber)+":T:1:3");
					lineCnt = edifactMessage.size();
					
					
					for(i=5; i<=noOfSeg; i++) {
						echSegDtls = (HashMap<String, String>)segMap.get(i);
						if(i-4 <= 4 && echSegDtls!=null && echSegDtls.size()>0) {	
							
							edifactMessage.add("M	CPN+"+String.valueOf(i-4)+":I");
							lineCnt++;
							
							fltNo= echSegDtls.get("fltNo").trim();
							while(fltNo.length()<4) {
								fltNo = "0"+fltNo;
							}
							
							fltTimeDtls=(HashMap<String,String>)this.fltTime.get(fltNo);
							depTime = fltTimeDtls.get("depTime");
							arrTime = fltTimeDtls.get("arrTime");
							tvlDate = this.getEdifactDate(echSegDtls.get("DtOfJrny"));
							carrierCode = this.getAirlineCode(echSegDtls.get("airlineCode"));
							depCity =  this.getCityCode(echSegDtls.get("org"));
							arrCity =  this.getCityCode(echSegDtls.get("des"));
							
							if(this.nvaIgnore!=null && this.nvaIgnore.length()>0 && this.nvaIgnore.equalsIgnoreCase("Yes")) {
								arrDate="";
							}else {
								arrDate = "+A:"+tvlDate;
							}
							
							edifactMessage.add("M	TVL+"+tvlDate+":"+depTime+"+"+depCity+"+"+arrCity+"+"+carrierCode+"+"+echSegDtls.get("fltNo").trim()+":"+echSegDtls.get("cosCode")+"+J+1");
							lineCnt++;
							
							edifactMessage.add("M	RPI++"+seatCode);
							lineCnt++;
							
							paxFareBagDtls = this.getFareCodesAndBaggage(paxType, echSegDtls.get("cosCode").trim().toUpperCase());
							edifactMessage.add("M	PTS++"+paxFareBagDtls.get("fareCode"));
							lineCnt++;
							
							
							edifactMessage.add("M	EBD++"+paxFareBagDtls.get("bagCnt")+"::N");
							lineCnt++;
							
							edifactMessage.add("M	DAT+B:"+tvlDate+""+arrDate);
							lineCnt++;
							
							if(paxType.equalsIgnoreCase("INF")){
								infCode=paxType;
							}						
							ssrTKNEMsg="SSRTKNE"+carrierCode+"HK1"+depCity+""+arrCity+""+fltNo+""+echSegDtls.get("cosCode")+""+echSegDtls.get("DtOfJrny")+"-";
							ssrTKNEMsg=ssrTKNEMsg+"1"+this.ssrTKNEPaxnames.get("lastName")+"/"+this.ssrTKNEPaxnames.get("firstName")+"."+infCode+""+ticketNumber+"C"+String.valueOf(i-4);
							ssrTKNEMsgsList.add(ssrTKNEMsg);
						}
						
					}
					edifactMessage.add("M	UNT+"+lineCnt+"+1");
					edifactMessage.add("M	UNZ+1+01C0VAXPVZ0001");
					
					//Adding PZA/ADD and PZA/END entries
					edifactMessage = addPZAEntry(edifactMessage);
					edifactMessageMap.put("2",edifactMessage);
					
					//For SSR TKNE
					//CommonProperties.ssrTKNEMsgs.put(String.valueOf(ticketNumber), ssrTKNEMsgsList);
					CommonProperties.ssrTKNEMsgs.put(paxType+"-"+String.valueOf(ticketNumber), ssrTKNEMsgsList);
					
				}
				if(noOfSeg>8) {
					edifactMessage = this.getInitPrtTestEdifactMessage(firstName, lastName, paxType, (HashMap<String, String>)this.fareMap.get("TCKT3").get(paxType), this.fareMap.get("TCKT3").get(paxType+"_TX"));
					ssrTKNEMsgsList = new ArrayList<String>();
					ticketNumber=ticketNumber+1;
					while(ticketGenerator.verifyTicketNumberExists(ticketNumber)) {
						ticketNumber = ticketNumber+1;
					}
					edifactMessage.add("M	TKT+"+String.valueOf(ticketNumber)+":T:1:3");
					lineCnt = edifactMessage.size();
					
					for(i=9; i<=noOfSeg; i++) {
						echSegDtls = (HashMap<String, String>)segMap.get(i);
						if(i-8 < 4 && echSegDtls!=null && echSegDtls.size()>0) {	
							
							
							edifactMessage.add("M	CPN+"+String.valueOf(i-8)+":I");
							lineCnt++;
							
							fltNo= echSegDtls.get("fltNo").trim();
							while(fltNo.length()<4) {
								fltNo = "0"+fltNo;
							}
							
							fltTimeDtls=(HashMap<String,String>)this.fltTime.get(fltNo);
							depTime = fltTimeDtls.get("depTime");
							arrTime = fltTimeDtls.get("arrTime");
							tvlDate = this.getEdifactDate(echSegDtls.get("DtOfJrny"));
							carrierCode = this.getAirlineCode(echSegDtls.get("airlineCode"));
							depCity =  this.getCityCode(echSegDtls.get("org"));
							arrCity =  this.getCityCode(echSegDtls.get("des"));
							
							if(this.nvaIgnore!=null && this.nvaIgnore.length()>0 && this.nvaIgnore.equalsIgnoreCase("Yes")) {
								arrDate="";
							}else {
								arrDate = "+A:"+tvlDate;
							}
							
							edifactMessage.add("M	TVL+"+tvlDate+":"+depTime+"+"+depCity+"+"+arrCity+"+"+carrierCode+"+"+echSegDtls.get("fltNo").trim()+":"+echSegDtls.get("cosCode")+"+J+1");
							lineCnt++;
							
							
							edifactMessage.add("M	RPI++"+seatCode);
							lineCnt++;
							
							
							paxFareBagDtls = this.getFareCodesAndBaggage(paxType, echSegDtls.get("cosCode").trim().toUpperCase());
							edifactMessage.add("M	PTS++"+paxFareBagDtls.get("fareCode"));
							lineCnt++;
							
							
							edifactMessage.add("M	EBD++"+paxFareBagDtls.get("bagCnt")+"::N");
							lineCnt++;
							
							edifactMessage.add("M	DAT+B:"+tvlDate+""+arrDate);
							lineCnt++;
							
							
							if(paxType.equalsIgnoreCase("INF")){
								infCode=paxType;
							}						
							ssrTKNEMsg="SSRTKNE"+carrierCode+"HK1"+depCity+""+arrCity+""+fltNo+""+echSegDtls.get("cosCode")+""+echSegDtls.get("DtOfJrny")+"-";
							ssrTKNEMsg=ssrTKNEMsg+"1"+this.ssrTKNEPaxnames.get("lastName")+"/"+this.ssrTKNEPaxnames.get("firstName")+"."+infCode+""+ticketNumber+"C"+String.valueOf(i-8);
							ssrTKNEMsgsList.add(ssrTKNEMsg);
						}
						
					}
					
					
					
					
					edifactMessage.add("M	UNT+"+lineCnt+"+1");
					edifactMessage.add("M	UNZ+1+01C0VAXPVZ0001");
					
					//Adding PZA/ADD and PZA/END entries
					edifactMessage = addPZAEntry(edifactMessage);
					edifactMessageMap.put("3",edifactMessage);
					
					//For SSR TKNE
					//CommonProperties.ssrTKNEMsgs.put(String.valueOf(ticketNumber), ssrTKNEMsgsList);
					CommonProperties.ssrTKNEMsgs.put(paxType+"-"+String.valueOf(ticketNumber), ssrTKNEMsgsList);
					
				}
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return edifactMessageMap;
	
	}
	
	private List<String> addPZAEntry(List<String> edifactMessage){
		List<String> updateEdifactMessage = new ArrayList<String>();
		Iterator<String> iter;
		int edifactMsgSize, remLines;
		try {
			edifactMsgSize = edifactMessage.size();
			//deleting 02 for last 02 lines where count and end of message is indicated;
			//else the those lines will be considered and adds the PZA/END at end of last line
			edifactMsgSize=edifactMsgSize-2;
			remLines = edifactMsgSize % 14;
			int i=1;
			iter = edifactMessage.iterator();
			updateEdifactMessage.add("I	PZA/ADD");
			while(iter.hasNext()) {				
				updateEdifactMessage.add(iter.next());
				if(i % 14 == 0 && i < (edifactMsgSize-remLines)) {
					updateEdifactMessage.add("I	PZA/ADD");
				}else if(i % 14 == 0 && i == (edifactMsgSize-remLines)){
					updateEdifactMessage.add("I	PZA/END");
				}
				i++;				
			}
		}catch(Exception e) {
			
		}
		return updateEdifactMessage;
	}
	
	//Old version
	private List<String> getInitPrtEdifactMessage(String firstName,String lastName, String paxType, HashMap<String, String> fareMap){
		List<String> edifactMessage = new ArrayList<String>();
		String formatTodayDate, specificPaxCode="", formattedbtDate;
		int noOfSeg, tempPos, tempPos2, payOnePercent, payTwoPercent;
		String tmpString="", cityCode="", baseFare="0", tax="0", payOnettlFare="0",payTwottlFare="0", currencyCode=CommonProperties.currency, expDate;
		Double tempDbl;
		String paymentString="";
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYMMdd");
		SimpleDateFormat sdfBTDtFrmt = new SimpleDateFormat("YYYYMMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Using today's date
		c.add(Calendar.DATE, -1); // Adding -1 days
		formatTodayDate = sdf.format(c.getTime());
		
		
		SimpleDateFormat sdfmt = new SimpleDateFormat("MMYY");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date()); // Using today's date
		cal.add(Calendar.YEAR, 1);
		expDate = sdfmt.format(cal.getTime());
		
		try {
			//Commented as the new method -addPZAEntry(); introduced for adding PZA/ADD or PZA/END lines for EDIFACT message
			//edifactMessage.add("I	PZA/ADD");
			if(this.gdsCode!=null && this.gdsCode.length()>0) {
				if(this.gdsCode.length() > 2 && this.gdsCode.contains("(")) {
					tempPos = this.gdsCode.indexOf("(");
					tempPos2 = this.gdsCode.indexOf(")");
					tmpString = this.gdsCode.substring(tempPos+1, tempPos2).toUpperCase().trim();
				}else {
					tmpString = this.gdsCode.toString().trim();
				}
			}
			cityCode = tableDataService.getOAGDSAddress(tmpString);
			cityCode = cityCode.substring(0, 3);
			edifactMessage.add("M	UNB+IATB:1+"+tmpString+"0TKD+CM0TK+"+formatTodayDate+":0727+01C0VAXPVZ0001+++O");
			edifactMessage.add("M	UNH+1+TKTREQ:08:1:IA+00010L87ZIVI27");
			edifactMessage.add("M	MSG+:130");
			edifactMessage.add("M	ORG+"+tmpString+":"+cityCode+"+69567735:234856+YKX++T+CA:CAD:EN+A9997WSSU");
			edifactMessage.add("M	TAI+7906+9997WS/S");
			edifactMessage.add("M	RCI+"+tmpString+":"+this.gdsPNR+":1+CM:"+this.hostPNR+":1");
			edifactMessage.add("M	EQN+1:TD");
			edifactMessage.add("M	IFT+4:39+SAINT-LAURE QC H4R 2N1+VOYAGES A LA CARTE");
			
			if(paxType.equalsIgnoreCase("INF") || paxType.equalsIgnoreCase("INS")) {
				specificPaxCode=":IN";
			}else if(paxType.equalsIgnoreCase("CHD")) {				
				specificPaxCode=":C";				
			}
			
			edifactMessage.add("M	TIF+"+lastName+""+specificPaxCode+"+"+firstName+"");
			edifactMessage.add("M	RCI+:12019250:6");
			
			if(this.fareType!=null && this.fareType.equalsIgnoreCase("IT Fares")) {
				edifactMessage.add("M	MON+B:IT+T:IT+I:"+fareMap.get("baseFare").trim()+":"+currencyCode+"+D:"+fareMap.get("TTL").trim()+"+G:00000");
			}else {	
				edifactMessage.add("M	MON+B:"+fareMap.get("baseFare").trim()+":"+currencyCode+"+T:"+fareMap.get("TTL").trim()+":"+currencyCode+"+G:00000");
			}
			
			if(this.isSplitPayment()) {
				payOnePercent = Integer.parseInt(this.getSplitFirstPayPercent());
				payTwoPercent = Integer.parseInt(this.getSplitSecondPayPercent());
				tempDbl = Double.parseDouble(fareMap.get("TTL").trim())*payOnePercent/100;
				payOnettlFare = String.format("%.2f",tempDbl);
				payTwottlFare = String.format("%.2f",Double.parseDouble(fareMap.get("TTL").trim())-tempDbl);
				
			}else {
				payOnettlFare = fareMap.get("TTL").trim();
			}
			if(this.paymentType!=null && this.paymentType.equalsIgnoreCase("Bank Transfer")) {
				paymentString = "M	FOP+MS:3:"+payOnettlFare+"::::::::::MSX/BT/"+this.generateBTNumber()+"//"+sdfBTDtFrmt.format(c.getTime());
				//edifactMessage.add("M	FOP+MS:3:"+fareMap.get("TTL").trim()+"::::::::::MSX/BT/"+this.generateBTNumber()+"//"+sdfBTDtFrmt.format(c.getTime()));
			}else if(this.paymentType!=null && this.paymentType.equalsIgnoreCase("Cash")) {
				paymentString = "M	FOP+CA:3:"+payOnettlFare+":S";
				//edifactMessage.add("M	FOP+CA:3:"+fareMap.get("TTL").trim());
			}else if(this.paymentType!=null && this.paymentType.equalsIgnoreCase("EMD")) {
				paymentString = "M	FOP+MS:3:"+payOnettlFare+"::::::::::MSEMD"+this.generateEMDNumber();
				//edifactMessage.add("M	FOP+MS:3:"+fareMap.get("TTL").trim()+"::::::::::MSEMD"+this.generateEMDNumber());
			}else if(this.paymentType!=null && this.paymentType.equalsIgnoreCase("miscellaneous")) {
				paymentString = "M	FOP+MS:3:"+payOnettlFare+":::::::::::S";
				//edifactMessage.add("M	FOP+MS:3:"+fareMap.get("TTL").trim()+":::::::::::S");
			}else 
			{
				paymentString = "M	FOP+CC:3:"+payOnettlFare+":"+this.cardTypeCode+":"+this.cardNo+":"+this.cardExpDate+":441930:S";
				//edifactMessage.add("M	FOP+CC:3:"+fareMap.get("TTL").trim()+":"+this.cardTypeCode+":"+this.cardNo+":"+this.cardExpDate+":441930:S");
			}
			
			if(this.isSplitPayment()) {
				if(this.secPaymentType!=null && this.secPaymentType.equalsIgnoreCase("Bank Transfer")) {
					paymentString = paymentString + "+MS:3:"+payTwottlFare+"::::::::::MSX/BT/"+this.generateBTNumber()+"//"+sdfBTDtFrmt.format(c.getTime());					
				}else if(this.secPaymentType!=null && this.secPaymentType.equalsIgnoreCase("Cash")) {
					paymentString = paymentString + "+CA:3:"+payTwottlFare;					
				}else if(this.secPaymentType!=null && this.secPaymentType.equalsIgnoreCase("EMD")) {
					paymentString = paymentString + "+MS:3:"+payTwottlFare+"::::::::::MSEMD"+this.generateEMDNumber();					
				}else if(this.secPaymentType!=null && this.secPaymentType.equalsIgnoreCase("miscellaneous")) {
					paymentString = paymentString + "+MS:3:"+payTwottlFare+":::::::::::S";
				}else 
				{
					paymentString = paymentString + "+CC:3:"+payTwottlFare+":"+this.secCardTypeCode+":"+this.secCardNo+":"+this.secCardExpDate+":441930:S";
				}
			}
			
			edifactMessage.add(paymentString);
			
			edifactMessage.add("M	PTK+N::I++100121");
			edifactMessage.add("M	ODI+MVD+YTO");
			//Commented as the new method -addPZAEntry(); introduced for adding PZA/ADD or PZA/END lines for EDIFACT message
			/*
			if(this.getNoOfSeg()>1) {
				edifactMessage.add("I	PZA/ADD");
			}else {
				edifactMessage.add("I	PZA/END");
			}*/
			edifactMessage.add("M	EQN+1:TF");
			edifactMessage.add("M	TXD+700+40.00::USD:CO+25.00::USD:DG+10.50::USD:YS+1.25::USD:AH+19.10::USD:US+3.96::USD:XA+7.00::USD:XY+5.99::USD:YC");
			edifactMessage.add("M	IFT+4:5+16475205423");
			edifactMessage.add("M	IFT+4:10+NON END / VALID CM ONLY / REF ONLY TO ISSUING AGENCY");
			edifactMessage.add("M	IFT+4:15:0+MVD CM X/PTY CM YTO250.00USD250.00END");
		}catch(Exception e){
			e.printStackTrace();
		}
		return edifactMessage;
	}
	
	//New version of above
	
	private List<String> getInitPrtTestEdifactMessage(String firstName,String lastName, String paxType, HashMap<String, String> fareMap, HashMap<String, String> taxMap){
		List<String> edifactMessage = new ArrayList<String>();
		String formatTodayDate, specificPaxCode="", formattedbtDate;
		int noOfSeg, tempPos, tempPos2, payOnePercent, payTwoPercent;
		String tmpString="", cityCode="", baseFare="0", tax="0", payOnettlFare="0",payTwottlFare="0", currencyCode=CommonProperties.currency, expDate, firstTaxCode="";
		Double tempDbl;
		String paymentString="", taxString="";
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYMMdd");
		SimpleDateFormat sdfBTDtFrmt = new SimpleDateFormat("YYYYMMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Using today's date
		c.add(Calendar.DATE, -1); // Adding -1 days
		formatTodayDate = sdf.format(c.getTime());
		
		
		SimpleDateFormat sdfmt = new SimpleDateFormat("MMYY");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date()); // Using today's date
		cal.add(Calendar.YEAR, 1);
		expDate = sdfmt.format(cal.getTime());
		
		try {
			//Commented as the new method -addPZAEntry(); introduced for adding PZA/ADD or PZA/END lines for EDIFACT message
			//edifactMessage.add("I	PZA/ADD");
			if(this.gdsCode!=null && this.gdsCode.length()>0) {
				if(this.gdsCode.length() > 2 && this.gdsCode.contains("(")) {
					tempPos = this.gdsCode.indexOf("(");
					tempPos2 = this.gdsCode.indexOf(")");
					tmpString = this.gdsCode.substring(tempPos+1, tempPos2).toUpperCase().trim();
				}else {
					tmpString = this.gdsCode.toString().trim();
				}
			}
			cityCode = tableDataService.getOAGDSAddress(tmpString);
			cityCode = cityCode.substring(0, 3);
			edifactMessage.add("M	UNB+IATB:1+"+tmpString+"0TKD+CM0TK+"+formatTodayDate+":0727+01C0VAXPVZ0001+++O");
			edifactMessage.add("M	UNH+1+TKTREQ:08:1:IA+00010L87ZIVI27");
			edifactMessage.add("M	MSG+:130");
			edifactMessage.add("M	ORG+"+tmpString+":"+cityCode+"+69567735:234856+YKX++T+CA:CAD:EN+A9997WSSU");
			edifactMessage.add("M	TAI+7906+9997WS/S");
			edifactMessage.add("M	RCI+"+tmpString+":"+this.gdsPNR+":1+CM:"+this.hostPNR+":1");
			edifactMessage.add("M	EQN+1:TD");
			edifactMessage.add("M	IFT+4:39+SAINT-LAURE QC H4R 2N1+VOYAGES A LA CARTE");
			
			if(paxType.equalsIgnoreCase("INF") || paxType.equalsIgnoreCase("INS")) {
				specificPaxCode=":IN";
			}else if(paxType.equalsIgnoreCase("CHD")) {				
				specificPaxCode=":C";				
			}
			
			edifactMessage.add("M	TIF+"+lastName+""+specificPaxCode+"+"+firstName+"");
			edifactMessage.add("M	RCI+:12019250:6");
			
			if(this.fareType!=null && this.fareType.equalsIgnoreCase("IT Fares")) {
				edifactMessage.add("M	MON+B:IT+T:IT+I:"+fareMap.get("baseFare").trim()+":"+currencyCode+"+D:"+fareMap.get("TTL").trim()+"+G:00000");
			}else {	
				edifactMessage.add("M	MON+B:"+fareMap.get("baseFare").trim()+":"+currencyCode+"+T:"+fareMap.get("TTL").trim()+":"+currencyCode+"+G:00000");
			}
			
			if(this.isSplitPayment()) {
				payOnePercent = Integer.parseInt(this.getSplitFirstPayPercent());
				payTwoPercent = Integer.parseInt(this.getSplitSecondPayPercent());
				tempDbl = Double.parseDouble(fareMap.get("TTL").trim())*payOnePercent/100;
				payOnettlFare = String.format("%.2f",tempDbl);
				payTwottlFare = String.format("%.2f",Double.parseDouble(fareMap.get("TTL").trim())-tempDbl);
				
			}else {
				payOnettlFare = fareMap.get("TTL").trim();
			}
			if(this.paymentType!=null && this.paymentType.equalsIgnoreCase("Bank Transfer")) {
				paymentString = "M	FOP+MS:3:"+payOnettlFare+"::::::::::MSX/BT/"+this.generateBTNumber()+"//"+sdfBTDtFrmt.format(c.getTime());
				//edifactMessage.add("M	FOP+MS:3:"+fareMap.get("TTL").trim()+"::::::::::MSX/BT/"+this.generateBTNumber()+"//"+sdfBTDtFrmt.format(c.getTime()));
			}else if(this.paymentType!=null && this.paymentType.equalsIgnoreCase("Cash")) {
				paymentString = "M	FOP+CA:3:"+payOnettlFare;
				//edifactMessage.add("M	FOP+CA:3:"+fareMap.get("TTL").trim());
			}else if(this.paymentType!=null && this.paymentType.equalsIgnoreCase("EMD")) {
				paymentString = "M	FOP+MS:3:"+payOnettlFare+"::::::::::MSEMD"+this.generateEMDNumber();
				//edifactMessage.add("M	FOP+MS:3:"+fareMap.get("TTL").trim()+"::::::::::MSEMD"+this.generateEMDNumber());
			}else if(this.paymentType!=null && this.paymentType.equalsIgnoreCase("miscellaneous")) {
				paymentString = "M	FOP+MS:3:"+payOnettlFare+":::::::::::S";
				//edifactMessage.add("M	FOP+MS:3:"+fareMap.get("TTL").trim()+":::::::::::S");
			}else 
			{
				paymentString = "M	FOP+CC:3:"+payOnettlFare+":"+this.cardTypeCode+":"+this.cardNo+":"+this.cardExpDate+":441930:S";
				//edifactMessage.add("M	FOP+CC:3:"+fareMap.get("TTL").trim()+":"+this.cardTypeCode+":"+this.cardNo+":"+this.cardExpDate+":441930:S");
			}
			
			if(this.isSplitPayment()) {
				if(this.secPaymentType!=null && this.secPaymentType.equalsIgnoreCase("Bank Transfer")) {
					paymentString = paymentString + "+MS:3:"+payTwottlFare+"::::::::::MSX/BT/"+this.generateBTNumber()+"//"+sdfBTDtFrmt.format(c.getTime());					
				}else if(this.secPaymentType!=null && this.secPaymentType.equalsIgnoreCase("Cash")) {
					paymentString = paymentString + "+CA:3:"+payTwottlFare;					
				}else if(this.secPaymentType!=null && this.secPaymentType.equalsIgnoreCase("EMD")) {
					paymentString = paymentString + "+MS:3:"+payTwottlFare+"::::::::::MSEMD"+this.generateEMDNumber();					
				}else if(this.secPaymentType!=null && this.secPaymentType.equalsIgnoreCase("miscellaneous")) {
					paymentString = paymentString + "+MS:3:"+payTwottlFare+":::::::::::S";
				}else 
				{
					paymentString = paymentString + "+CC:3:"+payTwottlFare+":"+this.secCardTypeCode+":"+this.secCardNo+":"+this.secCardExpDate+":441930:S";
				}
			}
			
			edifactMessage.add(paymentString);
			
			edifactMessage.add("M	PTK+N::I++100121");
			edifactMessage.add("M	ODI+MVD+YTO");
			//Commented as the new method -addPZAEntry(); introduced for adding PZA/ADD or PZA/END lines for EDIFACT message
			/*
			if(this.getNoOfSeg()>1) {
				edifactMessage.add("I	PZA/ADD");
			}else {
				edifactMessage.add("I	PZA/END");
			}*/
			edifactMessage.add("M	EQN+1:TF");
			//Main Tax
			firstTaxCode = fareMap.get("T1CD").trim();
			taxString = "M	TXD+700";
			
			if((fareMap.get("T1CD")!=null && fareMap.get("T1")!=null) && fareMap.get("T1CD").trim().length()>0 && fareMap.get("T1").trim().length()>0) {
				taxString = taxString+"+"+fareMap.get("T1")+"::"+currencyCode+":"+firstTaxCode;
			}
			//edifactMessage.add("M	TXD+700+40.00::USD:CO+25.00::USD:DG+10.50::USD:YS+1.25::USD:AH+19.10::USD:US+3.96::USD:XA+7.00::USD:XY+5.99::USD:YC");
			edifactMessage.add(taxString+getTaxMap(taxMap, currencyCode, firstTaxCode));
			edifactMessage.add("M	IFT+4:5+16475205423");
			edifactMessage.add("M	IFT+4:10+NON END / VALID CM ONLY / REF ONLY TO ISSUING AGENCY");
			edifactMessage.add("M	IFT+4:15:0+MVD CM X/PTY CM YTO250.00USD250.00END");
		}catch(Exception e){
			e.printStackTrace();
		}
		return edifactMessage;
	}
	private String getTaxMap(HashMap<String, String> taxMap, String currCD, String firstTaxCode) {
		String taxString ="";
		String tmpString ="M	TXD+700+40.00::USD:CO+25.00::USD:DG+10.50::USD:YS+1.25::USD:AH+19.10::USD:US+3.96::USD:XA+7.00::USD:XY+5.99::USD:YC";
		
		try {
			if(taxMap!=null && !taxMap.isEmpty()) {
				//taxString = taxString +"M	TXD+700";
				if(taxMap.containsKey(firstTaxCode)) {
					//taxString = taxString+"+"+taxMap.get(firstTaxCode)+"::"+currCD+":"+firstTaxCode;
					taxMap.remove(firstTaxCode);
				}
				for (Map.Entry<String, String> entry: taxMap.entrySet()){
		          //  System.out.println ("key: "+entry.getKey()+" - Value: "+entry.getValue());
		            taxString = taxString+"+"+entry.getValue()+"::"+currCD+":"+entry.getKey();
		        }
			 }
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return taxString;
	}
	public String getCityCode(String city) {
		String cityCode = null;
		int positionDelimiter;
		try {
			if(city.contains("(")) {
				positionDelimiter=city.indexOf("(");
				cityCode = city.substring(0, positionDelimiter).trim();	
			}else {
				cityCode=city.trim().toUpperCase();
			}
			return cityCode;
		}catch (Exception e) {
			e.printStackTrace();
			return cityCode;
		}
	}
	public String getEdifactDate(String tvlDate) {
		String edDate=null, dd="",mm="",yy="", mnStr;
		try {
			if(tvlDate!=null && tvlDate.length()>0) {
				dd=tvlDate.substring(0,2);
				mnStr = tvlDate.substring(2);
				if(mnStr.trim().equalsIgnoreCase("JAN")) {
					mm="01";
				}else if(mnStr.trim().equalsIgnoreCase("FEB")) {
					mm="02";
				}else if(mnStr.trim().equalsIgnoreCase("MAR")) {
					mm="03";
				}else if(mnStr.trim().equalsIgnoreCase("APR")) {
					mm="04";
				}else if(mnStr.trim().equalsIgnoreCase("MAY")) {
					mm="05";
				}else if(mnStr.trim().equalsIgnoreCase("JUN")) {
					mm="06";
				}else if(mnStr.trim().equalsIgnoreCase("JUL")) {
					mm="07";
				}else if(mnStr.trim().equalsIgnoreCase("AUG")) {
					mm="08";
				}else if(mnStr.trim().equalsIgnoreCase("SEP")) {
					mm="09";
				}else if(mnStr.trim().equalsIgnoreCase("OCT")) {
					mm="10";
				}else if(mnStr.trim().equalsIgnoreCase("NOV")) {
					mm="11";
				}else if(mnStr.trim().equalsIgnoreCase("DEC")) {
					mm="12";
				}
				edDate = dd+mm+""+getYear(mm,dd);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return edDate;
	}
	private String getYear(String month, String dte) {
		String yr;
		int currMonth, currDate;
		boolean returnNextYear=false;
		LocalDate lclDate =  LocalDate.now();
		SimpleDateFormat sdf = new SimpleDateFormat("YY");
		Calendar cal = Calendar.getInstance(Locale.US);
		currMonth = cal.get(Calendar.MONTH)+1;
		currDate = cal.get(Calendar.DATE);
		yr=sdf.format(Calendar.getInstance().getTime());
		if(currMonth < Integer.parseInt(month)) {
			returnNextYear=false;
		}else if(currMonth == Integer.parseInt(month)) {
			if(currDate<=Integer.parseInt(dte)) {
				returnNextYear=false;
			}else {
				returnNextYear=true;
			}
			
		}else {
			returnNextYear=true;
		}
		if(returnNextYear) {
			cal.add(Calendar.YEAR, 1);
			yr=sdf.format(cal.getTime());
		}
		return yr;
	}
	private String getAirlineCode(String airlinedesc) {
		String airlineCD=null;
		int tempPos;
		try {
			if(airlinedesc!=null && airlinedesc.toString().length()>0) {
				if(airlinedesc.toString().length() > 2 && airlinedesc.toString().contains("(")) {
					tempPos = airlinedesc.toString().indexOf("(");
					airlineCD = airlinedesc.substring(0, tempPos).toUpperCase().trim();
				}else {
					airlineCD = airlinedesc.toString().trim();
				}
			}else {
				airlineCD="CM";
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return airlineCD;
	}
	
	public String generateBTNumber() {
		Random random = new Random();
		long btNumber=23;
		btNumber= (btNumber*10000000)+random.nextInt(9999999);
		
		
		return String.valueOf(btNumber);
	}
	public String generateEMDNumber() {
		Random random = new Random();
		long emdNumber=2301;
		emdNumber= (emdNumber*1000000000)+random.nextInt(999999999);
		
		
		return String.valueOf(emdNumber);
	}
	private HashMap<String,String> getFareCodesAndBaggage(String paxType, String cos){
		HashMap<String, String> fareCodeBaggage = new HashMap<String, String>();
		String fareCode="AAAAOZ2S", baggage="0";
		
		
		if(paxType!=null && paxType.length()>0 && cos!=null && cos.length()>0) {
			if(cos.equalsIgnoreCase("Y") || cos.equalsIgnoreCase("C") || cos.equalsIgnoreCase("A")) {
				fareCodeBaggage = tableDataService.getFareCodeBaggageCnt(paxType, cos);
			}else {
				fareCodeBaggage.put("fareCode", fareCode);
				fareCodeBaggage.put("bagCnt", baggage);
			}
			
		}else {
			fareCodeBaggage.put("fareCode", fareCode);
			fareCodeBaggage.put("bagCnt", baggage);
		}
		return fareCodeBaggage;
	}
	

}
