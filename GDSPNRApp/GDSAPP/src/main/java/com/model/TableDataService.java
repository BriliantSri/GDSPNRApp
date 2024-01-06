package com.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.common.CommonProperties;
import com.core.FlightOperations;
import com.utilities.SHRUtilities;

public class TableDataService {
	Connection connection;
	Statement stmt;
	ResultSet resultSet;
	
	public TableDataService(Connection connection) {
		this.connection = connection;		
	}
	
	public String getOAGDSAddress(String gdsCode) {
		String oaGDSAddress=null;
		String sqlQry;
		try {			
			stmt = connection.createStatement();
			sqlQry = "select Address from GDSAddress where GDSCode = '"+gdsCode+"';";
			resultSet = stmt.executeQuery(sqlQry);
			if(resultSet.next()) {
				oaGDSAddress = resultSet.getString("Address");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return oaGDSAddress;
	}
	public Vector<String> getEnvNames() {
		Vector<String> envNames=null;
		String sqlQry;
		String envCode;
		try {			
			stmt = connection.createStatement();
			sqlQry = "select ENVCODE, DESC from ENVTable;";
			resultSet = stmt.executeQuery(sqlQry);
			envNames = new Vector<String>();
			while(resultSet.next()) {
				envCode = resultSet.getString("ENVCODE") +" ("+resultSet.getString("DESC")+")";
				envNames.add(envCode);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return envNames;
	}
	public String getEnvURL(String envCode) {
		String sqlQry;
		String envURL="";
		try {			
			stmt = connection.createStatement();
			sqlQry = "select ENVURL from ENVTable where ENVCODE ='"+envCode+"';";
			resultSet = stmt.executeQuery(sqlQry);
			
			if(resultSet.next()) {
				envURL = resultSet.getString("ENVURL");		
				if(envURL.startsWith("#")) {
					envURL = envURL.replace("#", "");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return envURL;
	}
	public List<HashMap> getPaxNames(){
		List<String> ranCntList;
		List<HashMap> paxList = new ArrayList<HashMap>();
		HashMap<String,String> paxName;
		String sqlQry;
		int cnt=0;
		Iterator<String> iter;
		try {			
			stmt = connection.createStatement();
			sqlQry = "select count(ID) from PassengerNamesTbl;";
			resultSet = stmt.executeQuery(sqlQry);
			if(resultSet.next()) {
				cnt = resultSet.getInt(1);				
			}
			if(cnt>0) {
				SHRUtilities shrUtilities = new SHRUtilities();
				ranCntList = shrUtilities.getRandomNumbers(cnt);
				iter = ranCntList.iterator();
				while(iter.hasNext()) {
					sqlQry = "select LNAME, FNAME from PassengerNamesTbl where ID="+Integer.parseInt(iter.next())+";";
					resultSet = stmt.executeQuery(sqlQry);
					if(resultSet.next()) {
						paxName=new HashMap<String,String>();
						paxName.put("LNAME", resultSet.getString("LNAME"));
						paxName.put("FNAME", resultSet.getString("FNAME"));
						paxList.add(paxName);
					}
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}		
		return paxList;
	}
	public Vector<String> getOAGDSDescCode() {		
		Vector<String> oaGDSDescCdVec = new Vector<String>();
		String sqlQry;
		try {			
			stmt = connection.createStatement();
			sqlQry = "select GDSCode, Name from GDSAddress;";
			resultSet = stmt.executeQuery(sqlQry);
			while(resultSet.next()) {
				oaGDSDescCdVec.add(resultSet.getString("Name") +" ("+resultSet.getString("GDSCode")+")");
			}
			if(oaGDSDescCdVec.size()>0)
				oaGDSDescCdVec.sort(null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return oaGDSDescCdVec;
	}
	
	//AIRLINECODE
	public Vector<String> getAirlineCodes() {		
		Vector<String> airlineCdVec = new Vector<String>();
		String sqlQry;
		try {			
			stmt = connection.createStatement();
			sqlQry = "select AIRLINECODE, DESCRIPTION from AIRLINECDETbl;";
			resultSet = stmt.executeQuery(sqlQry);
			while(resultSet.next()) {
				airlineCdVec.add(resultSet.getString("AIRLINECODE")  +" ("+resultSet.getString("DESCRIPTION")+")");
			}
			if(airlineCdVec.size()>0)
				airlineCdVec.sort(null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return airlineCdVec;
	}
	
	//Class of Service Codes
	public Vector<String> getCOSCodes() {		
		Vector<String> cosCdVec = new Vector<String>();
		String sqlQry;
		try {			
			stmt = connection.createStatement();
			sqlQry = "select COSCODE from COSCDETbl;";
			resultSet = stmt.executeQuery(sqlQry);
			while(resultSet.next()) {
				cosCdVec.add(resultSet.getString("COSCODE"));
			}
			if(cosCdVec.size()>0)
				cosCdVec.sort(null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return cosCdVec;
	}
	
	//CITYCODES
	public Vector<String> getCityCodes() {		
		Vector<String> cityCdVec = new Vector<String>();
		String sqlQry;
		try {			
			stmt = connection.createStatement();
			sqlQry = "select CITYCODE, DESCRIPTION from CityCodeTBL;";
			resultSet = stmt.executeQuery(sqlQry);
			while(resultSet.next()) {
				cityCdVec.add(resultSet.getString("CITYCODE")  +" ("+resultSet.getString("DESCRIPTION")+")");
			}
			if(cityCdVec.size()>0)
				cityCdVec.sort(null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return cityCdVec;
	}
	public String getCardTypeCode(String cardNo) {
		String cardTypeCode = null;
		String sqlQry;
		try {			
			stmt = connection.createStatement();
			sqlQry = "select CardCode from PaymentCards where CardNumber='"+cardNo.trim()+"';";
			resultSet = stmt.executeQuery(sqlQry);
			while(resultSet.next()) {
				cardTypeCode = resultSet.getString("CardCode");
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return cardTypeCode;
	}
	public String getCardExpDate(String cardNo) {
		String cardExpDate = null;
		String sqlQry;
		try {			
			stmt = connection.createStatement();
			sqlQry = "select ExpDate from PaymentCards where CardNumber='"+cardNo.trim()+"';";
			resultSet = stmt.executeQuery(sqlQry);
			while(resultSet.next()) {
				cardExpDate = resultSet.getString("ExpDate");
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return cardExpDate;
	}
	public HashMap<String, String> getPaymentOptions(String cardVariant){
		HashMap<String, String> payOptions = new HashMap<String, String>();
		String sqlQry;
		try {			
			stmt = connection.createStatement();
			sqlQry = "select CardType, CardNumber from PaymentCards where CardVariant='"+cardVariant+"';";
			resultSet = stmt.executeQuery(sqlQry);
			while(resultSet.next()) {
				payOptions.put(resultSet.getString("CardType")+""+resultSet.getString("CardNumber"), resultSet.getString("CardNumber"));
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		return payOptions;
	}
	public List<String> getCardTypes(String cardVariant){
		List<String> payOptions = new ArrayList<String>();
		String sqlQry;
		try {			
			stmt = connection.createStatement();
			sqlQry = "select distinct(CardType) from PaymentCards where CardVariant='"+cardVariant+"';";
			resultSet = stmt.executeQuery(sqlQry);
			while(resultSet.next()) {
				payOptions.add(resultSet.getString("CardType"));
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		return payOptions;
	}
	public List<String> getCardNums(String cardVariant, String cardType){
		List<String> cardNums = new ArrayList<String>();
		String sqlQry;
		try {			
			stmt = connection.createStatement();
			sqlQry = "select CardNumber from PaymentCards where CardVariant='"+cardVariant+"' and CardType ='"+cardType+"';";
			resultSet = stmt.executeQuery(sqlQry);
			while(resultSet.next()) {
				cardNums.add(resultSet.getString("CardNumber"));
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		return cardNums;
	}
	
	public String getCardCode(String cardType){
		String sqlQry, cardCode=null;
		try {			
			stmt = connection.createStatement();
			sqlQry = "select CardCode from PaymentCards where CardNumber='"+cardType+"';";
			resultSet = stmt.executeQuery(sqlQry);
			if(resultSet.next()) {
				cardCode = resultSet.getString("CardCode");
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		return cardCode;
	}
	public HashMap<String, String> getFareCodeBaggageCnt(String paxType, String cos){
		HashMap<String, String> fareCodeBaggageCnt = new HashMap<String, String>();
		//EDIFACTFareCodeBaggCnt
		String sqlQry;
		try {
			stmt = connection.createStatement();
			sqlQry = "select FARECODE, BAGCNT from EDIFACTFareCodeBaggCnt where PAXTYPE='"+paxType+"' and COS='"+cos+"';";
			resultSet = stmt.executeQuery(sqlQry);
			if(resultSet.next()) {
				fareCodeBaggageCnt.put("fareCode",resultSet.getString("FARECODE"));
				fareCodeBaggageCnt.put("bagCnt",String.valueOf(resultSet.getInt("BAGCNT")));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return fareCodeBaggageCnt;
	}
	
	public Vector<String> getCityCurrencies(){
		Vector<String> cityCurrencies = new Vector<String>();
		String sqlQry;
		try {
			stmt = connection.createStatement();
			sqlQry = "select CITY, CURRENCY from CITYCurrency;";
			resultSet = stmt.executeQuery(sqlQry);
			while(resultSet.next()) {
				cityCurrencies.add(resultSet.getString("CITY") +" (Currency: "+resultSet.getString("CURRENCY")+")");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return cityCurrencies;
	}
	
	public Vector<HashMap<String, String>> getCityAndCurr(){
		Vector<HashMap<String, String>> cityAndCurr = new Vector<>();
		HashMap<String, String> cityCurrencies;
		String sqlQry;
		try {
			stmt = connection.createStatement();
			sqlQry = "select CITY, CURRENCY from CITYCurrency;";
			resultSet = stmt.executeQuery(sqlQry);
			while(resultSet.next()) {
				cityCurrencies = new HashMap<String, String>();
				cityCurrencies.put("city",resultSet.getString("CITY"));
				cityCurrencies.put("currency",resultSet.getString("CURRENCY"));
				cityAndCurr.add(cityCurrencies);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return cityAndCurr;
	}
	
	
	public String getcurrCD(String pos){
		String currCd="";		
		String sqlQry;		
		try {
			stmt = connection.createStatement();
			sqlQry = "select CURRENCY from CITYCurrency where CITY='"+pos+"';";
			resultSet = stmt.executeQuery(sqlQry);
			if(resultSet.next()) {
				currCd = resultSet.getString("CURRENCY");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return currCd;
	}
	public HashMap<String, String> getTaxCd(String pos, String curr){
		String currCd="";		
		String sqlQry;
		HashMap<String, String> taxMap = new HashMap();
		try {
			stmt = connection.createStatement();
			sqlQry = "select TXCDONE, TXCDTWO from CITYCurrency where CITY='"+pos+"' and CURRENCY = '"+curr+"';";
			resultSet = stmt.executeQuery(sqlQry);
			if(resultSet.next()) {
				taxMap.put("txcdone", resultSet.getString("TXCDONE"));
				taxMap.put("txcdtwo", resultSet.getString("TXCDTWO"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return taxMap;
	}
	public HashMap<String, String> getTaxMap(){
		HashMap<String, String> taxMap = new HashMap();
		String sqlQry, txcol, txValuCol;
		try {
			stmt = connection.createStatement();
			sqlQry = "select * from CITYCurrency where CITY='"+CommonProperties.pos+"' and CURRENCY = '"+CommonProperties.currency+"';";
			resultSet = stmt.executeQuery(sqlQry);
			if(resultSet.next()) {
				taxMap.put("baseFare", String.valueOf(resultSet.getDouble("BASEFARE")));
				taxMap.put("decimalNum", String.valueOf(resultSet.getInt("DECIMAL")));
				
				for (int i=1; i<10; i++) {
					txcol="TX"+String.valueOf(i);
					txValuCol = txcol+"CALPCNT";
					if(resultSet.getString(txcol).trim().length()>0) {
						taxMap.put(txcol, resultSet.getString(txcol).trim()+"_"+String.valueOf(resultSet.getDouble(txValuCol)));
					}
				}
			}
		}catch(Exception e) {
			
		}
		return taxMap;
	}
	public HashMap<Integer, HashMap<String, String>> getAutoScenarios(){
		HashMap<Integer, HashMap<String, String>> autoScenarioMap = new HashMap();
		HashMap<String, String> indvScenario;
		String sqlQry,sceID="" ;
		int i;
		try {
			stmt = connection.createStatement();
			sqlQry = "select * from AutoScenarioTbl";
			resultSet = stmt.executeQuery(sqlQry);
			i=1;
			while(resultSet.next()) {
				indvScenario = new HashMap();
				indvScenario.put("ID", String.valueOf(resultSet.getInt("ID")));
				indvScenario.put("scenario", resultSet.getString("SCENARIO"));
				indvScenario.put("atsrid", String.valueOf(resultSet.getInt("ATSRID")));
				indvScenario.put("specseg", resultSet.getString("SPEC_SEG"));
				indvScenario.put("segkey", resultSet.getString("SEG_KEY"));
				indvScenario.put("checkin", resultSet.getString("CHECKIN"));
				indvScenario.put("cos", resultSet.getString("COS"));
				indvScenario.put("paxcnt", String.valueOf(resultSet.getInt("PAX_CNT")));
				indvScenario.put("connection", resultSet.getString("CONNECTION"));
				indvScenario.put("seatNoReq", resultSet.getString("SEATNOREQ"));
				indvScenario.put("openFlight", resultSet.getString("OPENFLIGHT"));
				/*if(i<10) {
					sceID="00"+String.valueOf(i);
				}else if(i<100) {
					sceID="0"+String.valueOf(i);
				}else {
					sceID=String.valueOf(i);
				}*/
				//sceID=String.valueOf(i);
				autoScenarioMap.put(resultSet.getInt("ID"), indvScenario);
				i++;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return autoScenarioMap;
	}
	
	//Individual scenario
	public HashMap<String, String> getIndvScenario(String scenario){
		HashMap<String, String> indvScenario  = new HashMap();
		String sqlQry;
		
		try {
			stmt = connection.createStatement();
			sqlQry = "select * from AutoScenarioTbl where SCENARIO ='"+scenario+"';";
			resultSet = stmt.executeQuery(sqlQry);
			
			if(resultSet.next()) {				
				indvScenario.put("ID", String.valueOf(resultSet.getInt("ID")));
				indvScenario.put("scenario", resultSet.getString("SCENARIO"));
				indvScenario.put("atsrid", String.valueOf(resultSet.getInt("ATSRID")));
				indvScenario.put("specseg", resultSet.getString("SPEC_SEG"));
				indvScenario.put("segkey", resultSet.getString("SEG_KEY"));
				indvScenario.put("checkin", resultSet.getString("CHECKIN"));
				indvScenario.put("cos", resultSet.getString("COS"));
				indvScenario.put("paxcnt", String.valueOf(resultSet.getInt("PAX_CNT")));
				indvScenario.put("connection", resultSet.getString("CONNECTION"));
				indvScenario.put("seatNoReq", resultSet.getString("SEATNOREQ"));
				indvScenario.put("openFlight", resultSet.getString("OPENFLIGHT"));	
				indvScenario.put("noOfSeg", String.valueOf(resultSet.getInt("NOOfSEG")));	
				indvScenario.put("needShipNumber", resultSet.getString("SHIPNUMREQ"));
				indvScenario.put("reqNoOfDays", String.valueOf(resultSet.getInt("REQNOOFDAY")));
				indvScenario.put("connAirCode", resultSet.getString("CONNAIRCODE"));
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return indvScenario;
	}
	
	public HashMap<String, HashMap<String, String>> getRoutingMap(){
		HashMap<String, HashMap<String, String>> routingMap = new HashMap();
		HashMap<String, String> indvRoute;
		String sqlQry;
		int i;
		try {
			stmt = connection.createStatement();
			sqlQry = "select * from RoutingTbl";
			//sqlQry = "select * from TempTable";
			resultSet = stmt.executeQuery(sqlQry);
			i=1;
			while(resultSet.next()) {
				indvRoute = new HashMap();
				indvRoute.put("ID", String.valueOf(resultSet.getInt("ID")));
				indvRoute.put("origin", resultSet.getString("ORIGIN"));
				indvRoute.put("destination", resultSet.getString("DESTINATION"));
				indvRoute.put("conncity", resultSet.getString("CONNCITY"));
				indvRoute.put("hasconnect", resultSet.getString("HASCONNECT"));
				indvRoute.put("connAirCode", resultSet.getString("CONNAIRCODE"));
				routingMap.put(String.valueOf(i), indvRoute);
				i++;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return routingMap;
	}
	public HashMap<String, String> getATSRDtls(int atsrid){
		HashMap<String, String> autoTDSheetMap = new HashMap();
		String sqlQry;
		int i;
		try {
			stmt = connection.createStatement();
			sqlQry = "select * from AutoTestSheetReqTbl where ID = "+atsrid;
			resultSet = stmt.executeQuery(sqlQry);
			i=1;
			if(resultSet.next()) {
				autoTDSheetMap = new HashMap();
				autoTDSheetMap.put("ID", String.valueOf(resultSet.getInt("ID")));
				autoTDSheetMap.put("sheetName", resultSet.getString("SheetName"));
				autoTDSheetMap.put("headerOne", resultSet.getString("HeaderOne"));
				autoTDSheetMap.put("headerTwo", resultSet.getString("HeaderTwo"));
				autoTDSheetMap.put("headerThree", resultSet.getString("HeaderThree"));
				autoTDSheetMap.put("headerFour", resultSet.getString("HeaderFour"));
				autoTDSheetMap.put("headerFive", resultSet.getString("HeaderFive"));
				autoTDSheetMap.put("headerSix", resultSet.getString("HeaderSix"));
				autoTDSheetMap.put("headerSeven", resultSet.getString("HeaderSeven"));
				autoTDSheetMap.put("headerEight", resultSet.getString("HeaderEight"));
				autoTDSheetMap.put("headerNine", resultSet.getString("HeaderNine"));
				autoTDSheetMap.put("headerTen", resultSet.getString("HeaderTen"));
				autoTDSheetMap.put("headerEleven", resultSet.getString("HeaderEleven"));
				autoTDSheetMap.put("headerTwelve", resultSet.getString("HeaderTwelve"));
				autoTDSheetMap.put("headerThirteen", resultSet.getString("HeaderThirteen"));
				autoTDSheetMap.put("headerFourteen", resultSet.getString("HeaderFourteen"));
				autoTDSheetMap.put("headerFifteen", resultSet.getString("HeaderFifteen"));
				autoTDSheetMap.put("headerSixteen", resultSet.getString("HeaderSixteen"));
				autoTDSheetMap.put("headerSeventeen", resultSet.getString("HeaderSeventeen"));
				autoTDSheetMap.put("headerEighteen", resultSet.getString("HeaderEighteen"));
				autoTDSheetMap.put("headerNineteen", resultSet.getString("HeaderNineteen"));
				autoTDSheetMap.put("headerTwenty", resultSet.getString("HeaderTwenty"));
				autoTDSheetMap.put("headerTwentyOne", resultSet.getString("HeaderTwentyOne"));
				autoTDSheetMap.put("headerTwentyTwo", resultSet.getString("HeaderTwentyTwo"));
				autoTDSheetMap.put("headerTwentyThree", resultSet.getString("HeaderTwentyThree"));
				autoTDSheetMap.put("headerTwentyFour", resultSet.getString("HeaderTwentyFour"));
				autoTDSheetMap.put("headerTwentyFive", resultSet.getString("HeaderTwentyFive"));
				autoTDSheetMap.put("headerTwentySix", resultSet.getString("HeaderTwentySix"));
				autoTDSheetMap.put("headerTwentySeven", resultSet.getString("HeaderTwentySeven"));
				autoTDSheetMap.put("headerTwentyEight", resultSet.getString("HeaderTwentyEight"));
				autoTDSheetMap.put("headerTwentyNine", resultSet.getString("HeaderTwentyNine"));
				autoTDSheetMap.put("headerThirty", resultSet.getString("HeaderThirty"));
				autoTDSheetMap.put("headerThirtyOne", resultSet.getString("HeaderThirtyOne"));
				autoTDSheetMap.put("headerThirtyTwo", resultSet.getString("HeaderThirtyTwo"));
				autoTDSheetMap.put("headerThirtyThree", resultSet.getString("HeaderThirtyThree"));
				autoTDSheetMap.put("headerThirtyFour", resultSet.getString("HeaderThirtyFour"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return autoTDSheetMap;
	}
	// Below is to get all the headers for all scenarios
	public HashMap<String, HashMap<String, String>> getATAllSRHeaderDTls(){
		HashMap<String, HashMap<String, String>> autoAllScenTDSheetMap = new HashMap();
		HashMap<String, String> autoTDSheetMap = new HashMap();
		String sqlQry, idStr="";
		int i;
		try {
			stmt = connection.createStatement();
			sqlQry = "select * from AutoTestSheetReqTbl;";
			resultSet = stmt.executeQuery(sqlQry);
			i=1;
			while(resultSet.next()) {
				autoTDSheetMap = new HashMap();
				if(resultSet.getInt("ID")<10) {
					idStr="00"+String.valueOf(resultSet.getInt("ID"));
				}else if(resultSet.getInt("ID")<100) {
					idStr="0"+String.valueOf(resultSet.getInt("ID"));
				}else {
					idStr=String.valueOf(resultSet.getInt("ID"));
				}
				autoTDSheetMap.put("ID", String.valueOf(resultSet.getInt("ID")));
				autoTDSheetMap.put("sheetName", resultSet.getString("SheetName"));
				autoTDSheetMap.put("headerOne", resultSet.getString("HeaderOne"));
				autoTDSheetMap.put("headerTwo", resultSet.getString("HeaderTwo"));
				autoTDSheetMap.put("headerThree", resultSet.getString("HeaderThree"));
				autoTDSheetMap.put("headerFour", resultSet.getString("HeaderFour"));
				autoTDSheetMap.put("headerFive", resultSet.getString("HeaderFive"));
				autoTDSheetMap.put("headerSix", resultSet.getString("HeaderSix"));
				autoTDSheetMap.put("headerSeven", resultSet.getString("HeaderSeven"));
				autoTDSheetMap.put("headerEight", resultSet.getString("HeaderEight"));
				autoTDSheetMap.put("headerNine", resultSet.getString("HeaderNine"));
				autoTDSheetMap.put("headerTen", resultSet.getString("HeaderTen"));
				autoTDSheetMap.put("headerEleven", resultSet.getString("HeaderEleven"));
				autoTDSheetMap.put("headerTwelve", resultSet.getString("HeaderTwelve"));
				autoTDSheetMap.put("headerThirteen", resultSet.getString("HeaderThirteen"));	
				autoTDSheetMap.put("headerFourteen", resultSet.getString("HeaderFourteen"));
				autoTDSheetMap.put("headerFifteen", resultSet.getString("HeaderFifteen"));
				autoTDSheetMap.put("headerSixteen", resultSet.getString("HeaderSixteen"));
				autoTDSheetMap.put("headerSeventeen", resultSet.getString("HeaderSeventeen"));
				autoTDSheetMap.put("headerEighteen", resultSet.getString("HeaderEighteen"));
				autoTDSheetMap.put("headerNineteen", resultSet.getString("HeaderNineteen"));
				autoTDSheetMap.put("headerTwenty", resultSet.getString("HeaderTwenty"));
				autoTDSheetMap.put("headerTwentyOne", resultSet.getString("HeaderTwentyOne"));
				autoTDSheetMap.put("headerTwentyTwo", resultSet.getString("HeaderTwentyTwo"));
				autoTDSheetMap.put("headerTwentyThree", resultSet.getString("HeaderTwentyThree"));
				autoTDSheetMap.put("headerTwentyFour", resultSet.getString("HeaderTwentyFour"));
				autoTDSheetMap.put("headerTwentyFive", resultSet.getString("HeaderTwentyFive"));
				autoTDSheetMap.put("headerTwentySix", resultSet.getString("HeaderTwentySix"));
				autoTDSheetMap.put("headerTwentySeven", resultSet.getString("HeaderTwentySeven"));
				autoTDSheetMap.put("headerTwentyEight", resultSet.getString("HeaderTwentyEight"));
				autoTDSheetMap.put("headerTwentyNine", resultSet.getString("HeaderTwentyNine"));
				autoTDSheetMap.put("headerThirty", resultSet.getString("HeaderThirty"));
				autoTDSheetMap.put("headerThirtyOne", resultSet.getString("HeaderThirtyOne"));
				autoTDSheetMap.put("headerThirtyTwo", resultSet.getString("HeaderThirtyTwo"));
				autoTDSheetMap.put("headerThirtyThree", resultSet.getString("HeaderThirtyThree"));
				autoTDSheetMap.put("headerThirtyFour", resultSet.getString("HeaderThirtyFour"));
				autoAllScenTDSheetMap.put(idStr, autoTDSheetMap);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return autoAllScenTDSheetMap;
	}
	
	public void insertFlightRecord(int seqNo, String orgAirCode, int flightNo,String org, String des, String jrnyDate, int dtCnt, String cos, int seatsAvail, String conFlt, String conCty, int cnSeatsAvail, String stringMultiLeg, int connDateCnt, String connAirCode) {
		String sqlQry;
		int idNum;
		try {
			stmt = connection.createStatement();
			idNum = this.getFltRecordCnt("TempFlightData")+1;
			sqlQry = "insert into TempFlightData (ID, SEQNO, ORGAIRCODE, FLTNO,ORG,DES,DOJ,DATECOUNT, COS, ST_AVL, CONFLT, CONCTY,CON_ST_AVL,MULTILEG, CONNDATECOUNT, CONNAIRCODE) values "
					+ "("+idNum+","+seqNo+",'"+orgAirCode+"', "+flightNo+",'"+org+"','"+des+"','"+jrnyDate+"',"+dtCnt+",'"+cos+"',"+seatsAvail+",'"+conFlt+"','"+conCty+"',"+cnSeatsAvail+",'"+stringMultiLeg+"',"+connDateCnt+",'"+connAirCode+"');";
			stmt.executeUpdate(sqlQry);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void insertConnFlightRecord(int seqNo, String orgAirCode,int flightNo,String org, String des, String jrnyDate, int dtCnt, String cos, int seatsAvail, String conFlt, String conCty, int cnSeatsAvail, int connDays, String connAirCode) {
		String sqlQry;
		int idNum;
		try {
			stmt = connection.createStatement();			
			//ID, FLTNO,ORG,DES,DOJ,DATECOUNT, COS, ST_AVL, CONCTY,CON_ST_AVL
			sqlQry = "update TempFlightData set CONFLT = '"+conFlt+"', CONCTY = '"+conCty+"', CON_ST_AVL="+cnSeatsAvail+", CONNDATECOUNT="+connDays+", CONNAIRCODE = '"+connAirCode+"'  where "
					+ "SEQNO = "+seqNo+" and ORGAIRCODE = '"+orgAirCode+"' and FLTNO = "+flightNo+" and ORG='"+org+"' and DES = '"+des+"' and DOJ = '"+jrnyDate+"' and COS = '"+cos+"';";
			stmt.executeUpdate(sqlQry);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void truncateFlightRecord() {
		String sqlQry;
		boolean getResult;
		try {
			stmt = connection.createStatement();
			sqlQry = "Delete from TempFlightData;";
			stmt.executeUpdate(sqlQry);
			//sqlQry = "ALTER TABLE TempFlightData ALTER Column ID INT;";
			//stmt.executeUpdate(sqlQry);
			//sqlQry = "ALTER TABLE TempFlightData ALTER COLUMN ID COUNTER(1,1)";
			//stmt.executeUpdate(sqlQry);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public int getFltRecordCnt(String tblName) {
		String sqlQry;
		int getResult=0;
		try {
			stmt = connection.createStatement();
			sqlQry = "select count(*) from "+tblName+";";
			resultSet = stmt.executeQuery(sqlQry);
			if(resultSet!=null && resultSet.next()) {
				getResult = resultSet.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return getResult;
	}
	public ArrayList<String> getTDCOS() {
		String sqlQry;
		int getResult=0;
		ArrayList<String> cosList = new ArrayList();
		try {
			stmt = connection.createStatement();
			sqlQry = "select distinct(cos) from AutoScenarioTbl;";
			resultSet = stmt.executeQuery(sqlQry);
			while(resultSet.next()) {
				cosList.add(resultSet.getString(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return cosList;
	}
	public HashMap<String, String> getFlightDetails(int seatsReq, String cos, boolean isCheckInReq, boolean isConnectionReq, boolean isShipRequired, int reqDayJrny, int noOfSeg, String connAirCode) {
		String sqlQry, shipNumber="", connDes="";
		HashMap<String, String> fltDtls = new HashMap();
		HashMap<String, String> connFltDtls;
		FlightOperations flitOpers = new FlightOperations();
		boolean addFltDetails=true;
		int idNum;
		try {
			stmt = connection.createStatement();			
			//ID, FLTNO,ORG,DES,DOJ,DATECOUNT, COS, ST_AVL, CONCTY,CON_ST_AVL
			sqlQry = "select * from TempFlightData where ST_AVL>="+seatsReq+" and cos='"+cos.toUpperCase()+"' and DATECOUNT >="+reqDayJrny;
			if(isCheckInReq) {
				sqlQry = sqlQry + " and DATECOUNT <=3";
			}
			if(isConnectionReq) {
				sqlQry = sqlQry + " and CONCTY<>'NA' and CON_ST_AVL>="+seatsReq+" and CONNAIRCODE='"+connAirCode+"'";
			}else {
				sqlQry = sqlQry + " and CONCTY='NA';";
			}
			resultSet = stmt.executeQuery(sqlQry);
			while(resultSet!=null && resultSet.next() && resultSet.getInt("ID")>0) {
				if(isShipRequired){
					if(!flitOpers.isShipAssigned(String.valueOf(resultSet.getInt("FLTNO")), resultSet.getString("DOJ"))) {
						shipNumber = flitOpers.getShipNumber(String.valueOf(resultSet.getInt("FLTNO")), resultSet.getString("DOJ"), resultSet.getString("ORG"));
						addFltDetails=true;
					}else {
						addFltDetails=false;
					}
					
				}
				if(addFltDetails) {
					fltDtls.put("id", String.valueOf(resultSet.getInt("ID")));
					fltDtls.put("fltNo", String.valueOf(resultSet.getInt("FLTNO")));
					fltDtls.put("origin", resultSet.getString("ORG"));
					fltDtls.put("destination", resultSet.getString("DES"));
					fltDtls.put("doj", resultSet.getString("DOJ"));
					fltDtls.put("dtCnt", String.valueOf(resultSet.getInt("DATECOUNT")));
					fltDtls.put("cos", resultSet.getString("COS"));
					fltDtls.put("stAvl", String.valueOf(resultSet.getInt("ST_AVL")));
					fltDtls.put("cnnFlt", resultSet.getString("CONFLT"));
					fltDtls.put("cnnCty", resultSet.getString("CONCTY"));
					fltDtls.put("cnStAvl", String.valueOf(resultSet.getInt("CON_ST_AVL")));
					fltDtls.put("conDtCnt", String.valueOf(resultSet.getInt("CONNDATECOUNT")));
					fltDtls.put("shipNumber", shipNumber);
					if(isConnectionReq && noOfSeg > 2) {
						connFltDtls = this.getNextConnectFlightDetails(seatsReq, cos, resultSet.getString("DES"), resultSet.getInt("DATECOUNT")+1, isCheckInReq, true);
						if(connFltDtls!=null && !connFltDtls.isEmpty()) {
							fltDtls.put("cnnTwoFlt", connFltDtls.get("fltNo"));
							fltDtls.put("cnnTwoOrg", connFltDtls.get("origin"));
							fltDtls.put("cnnTwoDes", connFltDtls.get("destination"));
							fltDtls.put("cnnTwoConnFlt", connFltDtls.get("cnnFlt"));
							fltDtls.put("cnnTwoConnCty", connFltDtls.get("cnnCty"));
							fltDtls.put("cnnTwoDOJ", connFltDtls.get("doj"));
							fltDtls.put("cnnTwoDtCnt", connFltDtls.get("dtCnt"));
							connDes = connFltDtls.get("destination");
						}
					}
					if(isConnectionReq && noOfSeg > 4) {
						connFltDtls = this.getNextConnectFlightDetails(seatsReq, cos, connDes, resultSet.getInt("DATECOUNT")+1, isCheckInReq, true);
						if(connFltDtls!=null && !connFltDtls.isEmpty()) {
							fltDtls.put("cnnThrFlt", connFltDtls.get("fltNo"));
							fltDtls.put("cnnThrOrg", connFltDtls.get("origin"));
							fltDtls.put("cnnThrDes", connFltDtls.get("destination"));
							fltDtls.put("cnnThrConnFlt", connFltDtls.get("cnnFlt"));
							fltDtls.put("cnnThrConnCty", connFltDtls.get("cnnCty"));
							fltDtls.put("cnnThrDOJ", connFltDtls.get("doj"));
							fltDtls.put("cnnThrDtCnt", connFltDtls.get("dtCnt"));
						}
						
					}
					break;
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//Finally closing the Driver opened by flitOpers.
			if(flitOpers!=null)				
			flitOpers.closeDriver();
		}
		return fltDtls;
	}
	public HashMap<String, String> getNextConnectFlightDetails(int seatsReq, String cos, String connCity, int departureDays, boolean isCheckInReq, boolean isConnectionReq) {
		String sqlQry;
		HashMap<String, String> fltDtls = new HashMap();
		int idNum;
		try {
			stmt = connection.createStatement();			
			//ID, FLTNO,ORG,DES,DOJ,DATECOUNT, COS, ST_AVL, CONCTY,CON_ST_AVL
			sqlQry = "select * from TempFlightData where org = '"+connCity+"' and ST_AVL>="+seatsReq+" and cos='"+cos.toUpperCase()+"' and  datecount >= "+departureDays;
			if(isCheckInReq) {
				sqlQry = sqlQry + " and DATECOUNT<=3";
			}
			if(isConnectionReq) {
				sqlQry = sqlQry + " and CONCTY<>'NA' and CON_ST_AVL>="+seatsReq+";";
			}else {
				sqlQry = sqlQry + " and CONCTY='NA';";
			}
			resultSet = stmt.executeQuery(sqlQry);
			if(resultSet!=null && resultSet.next()) {
				//&& resultSet.getInt("ID")>0
				fltDtls.put("id", String.valueOf(resultSet.getInt("ID")));
				fltDtls.put("fltNo", String.valueOf(resultSet.getInt("FLTNO")));
				fltDtls.put("origin", resultSet.getString("ORG"));
				fltDtls.put("destination", resultSet.getString("DES"));
				fltDtls.put("doj", resultSet.getString("DOJ"));
				fltDtls.put("dtCnt", String.valueOf(resultSet.getInt("DATECOUNT")));
				fltDtls.put("cos", resultSet.getString("COS"));
				fltDtls.put("stAvl", String.valueOf(resultSet.getInt("ST_AVL")));
				fltDtls.put("cnnFlt", resultSet.getString("CONFLT"));
				fltDtls.put("cnnCty", resultSet.getString("CONCTY"));
				fltDtls.put("cnStAvl", String.valueOf(resultSet.getInt("CON_ST_AVL")));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return fltDtls;
	}
	public void updateFlightSeatCount(int ID, int seatsAvail, int cnSeatsAvail) {
		String sqlQry;
		int idNum;
		try {
			stmt = connection.createStatement();			
			//ID, FLTNO,ORG,DES,DOJ,DATECOUNT, COS, ST_AVL, CONCTY,CON_ST_AVL
			sqlQry = "update TempFlightData set ST_AVL = "+seatsAvail+", CON_ST_AVL="+cnSeatsAvail+" where ID = "+ID+";";
			stmt.executeUpdate(sqlQry);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertSpecificFltTD(int fltNo, String jrnyDate, int daysCnt, String oringin, String destination, String multileg, String fltOpenSts, String cndfld1, String cndfld2) {
		//SpecificDataTempTable
		String sqlQry;		
		int idNum = this.getFltRecordCnt("SpecificDataTempTable");
		try {
			stmt = connection.createStatement();
			sqlQry = "insert into SpecificDataTempTable (ID, FLTNO, JRNYDT, DAYNO, ORGIN,DESTINATION,MULTILEG, FLTOPENSTS, CNDFLD1, CNDFLD2) values"
					+ "("+idNum+","+fltNo+",'"+jrnyDate+"',"+daysCnt+",'"+oringin+"','"+destination+"','"+multileg+"','"+fltOpenSts+"','"+cndfld1+"','"+cndfld2+"');";
			stmt.executeUpdate(sqlQry);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void truncateTempTable() {
		String sqlQry;
		try {
			stmt = connection.createStatement();
			sqlQry = "Delete from TempTable;";
			stmt.executeUpdate(sqlQry);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void truncateSpecTable() {
		String sqlQry;
		try {
			stmt = connection.createStatement();
			sqlQry = "Delete from SpecificDataTempTable;";
			stmt.executeUpdate(sqlQry);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
