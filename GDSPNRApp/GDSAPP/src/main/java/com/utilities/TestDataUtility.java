package com.utilities;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.testng.Assert;

import com.base.TestBase;

public class TestDataUtility {
		
	public TestDataUtility() {
		this.getTestDataSheetAccess();
	}
	
	static final Logger logger = LogManager.getLogger(TestBase.class.getName());
	private XSSFWorkbook testWorkBook;
	private HashMap<String,String> testCaseReferences = null;
	
	private String testID;
	private String logID;
	private String posID;
	private String posCity;
	private String posCode;
	private String posCurrency;
	private String iteneraryID;
	private String paxCntId;
	private String fltCosID;
	private String prcOptionID;
	private String paymentID;
	private HashMap<String, String> segCOS;
	private String appURL;
	private String envLink;
	private String uname;
	private String password;
	private String posCityCode;
	private String posCityName;	
	private String pointOfSale;
	private boolean roundTrip;	
	private HashMap<String, HashMap> iteneraryMap;
	private HashMap <String, String> segDetails, roundTripDts;
	private String priceOption;
	private HashMap <String, String> paxCountIncFQTV;
	private HashMap <String, String> paxEntities;
	private int noOFPayments;	
	private List <HashMap> paymentDetails; 
	private int fqtvCount;
	private int noOfItineraries;
	private List<HashMap> passengerEntities;
	
	
	private String getTestID() {
		return testID;
	}
	public void setTestID(String testID) {
		this.testID = testID;
	}
	public String getLogID() {
		return logID;
	}
	private void setLogID(String logID) {
		this.logID = logID;
	}
	public String getPosID() {
		return posID;
	}
	private void setPosID(String posID) {
		this.posID = posID;
	}
	public String getPosCity() {
		return posCity;
	}
	private void setPosCity(String posCity) {
		this.posCity = posCity;
	}
	public String getPosCode() {
		return posCode;
	}
	private void setPosCode(String posCode) {
		this.posCode = posCode;
	}
	public String getPosCurrency() {
		return posCurrency;
	}
	private void setPosCurrency(String posCurrency) {
		this.posCurrency = posCurrency;
	}
	public String getIteneraryID() {
		return iteneraryID;
	}
	private void setIteneraryID(String iteneraryID) {
		this.iteneraryID = iteneraryID;
	}
	public String getPaxCntId() {
		return paxCntId;
	}
	private void setPaxCntId(String paxCntId) {
		this.paxCntId = paxCntId;
	}
	public String getFltCosID() {
		return fltCosID;
	}
	private void setFltCosID(String fltCosID) {
		this.fltCosID = fltCosID;
	}
	public String getPrcOptionID() {
		return prcOptionID;
	}
	private void setPrcOptionID(String prcOptionID) {
		this.prcOptionID = prcOptionID;
	}
	public String getPaymentID() {
		return paymentID;
	}
	private void setPaymentID(String paymentID) {
		this.paymentID = paymentID;
	}
	public HashMap<String, String> getSegCOS() {
		return segCOS;
	}
	private void setSegCOS(HashMap<String, String> segCOS) {
		this.segCOS = segCOS;
	}
	public String getAppURL() {
		return appURL;
	}
	private void setAppURL(String appURL) {
		this.appURL = appURL;
	}
	public String getEnvLink() {
		return envLink;
	}
	private void setEnvLink(String envLink) {
		this.envLink = envLink;
	}	
	public String getUname() {
		return uname;
	}
	private void setUname(String uname) {
		this.uname = uname;
	}
	public String getPassword() {
		return password;
	}
	private void setPassword(String password) {
		this.password = password;
	}
	public String getPosCityCode() {
		return posCityCode;
	}
	private void setPosCityCode(String posCityCode) {
		this.posCityCode = posCityCode;
	}
	public String getPosCityName() {
		return posCityName;
	}
	private void setPosCityName(String posCityName) {
		this.posCityName = posCityName;
	}
	public String getPointOfSale() {
		return pointOfSale;
	}
	private void setPointOfSale(String pointOfSale) {
		this.pointOfSale = pointOfSale;
	}
	public HashMap<String,HashMap> getIteneraryMap() {
		return iteneraryMap;
	}
	private void setIteneraryMap(HashMap<String, HashMap> iteneraryMap) {
		this.iteneraryMap = iteneraryMap;
	}
	public HashMap<String, String> getSegDetails() {
		return segDetails;
	}
	private void setSegDetails(HashMap<String, String> segDetails) {
		this.segDetails = segDetails;
	}
	public String getPriceOption() {
		return priceOption;
	}
	private void setPriceOption(String priceOption) {
		this.priceOption = priceOption;
	}
	public HashMap<String, String> getPaxCountIncFQTV() {
		return paxCountIncFQTV;
	}
	private void setPaxCountIncFQTV(HashMap<String, String> paxCountIncFQTV) {
		this.paxCountIncFQTV = paxCountIncFQTV;
	}
	public HashMap<String, String> getPaxEntities() {
		return paxEntities;
	}
	private void setPaxEntities(HashMap<String, String> paxEntities) {
		this.paxEntities = paxEntities;
	}
	private int getNoOFPayments() {
		return noOFPayments;
	}
	private void setNoOFPayments(int noOFPayments) {
		this.noOFPayments = noOFPayments;
	}
	public List<HashMap> getPaymentDetails() {
		return paymentDetails;
	}
	private void setPaymentDetails(List<HashMap> paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	private int getFqtvCount() {
		return fqtvCount;
	}
	private void setFqtvCount(int fqtvCount) {
		this.fqtvCount = fqtvCount;
	}
	public boolean isRoundTrip() {
		return roundTrip;
	}
	private void setRoundTrip(boolean roundTrip) {
		this.roundTrip = roundTrip;
	}
	public HashMap<String, String> getRoundTripDts() {
		return roundTripDts;
	}
	private void setRoundTripDts(HashMap<String, String> roundTripDts) {
		this.roundTripDts = roundTripDts;
	}

	public int getNoOfItineraries() {
		return noOfItineraries;
	}
	private void setNoOfItineraries(int noOfItineraries) {
		this.noOfItineraries = noOfItineraries;
	}
	public List<HashMap> getPassengerEntities() {
		return passengerEntities;
	}
	private void setPassengerEntities(List<HashMap> passengerEntities) {
		this.passengerEntities = passengerEntities;
	}
	
	
	private void getTestDataSheetAccess() {
		try {
			FileInputStream fis = new FileInputStream("C:\\Srikanth Kokkula\\Selenium\\Test Data sheets\\MAETestData_V1.0.xlsx");
			logger.info("Accessed Test Data");
			this.testWorkBook = new XSSFWorkbook(fis);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void getTestCaseDetails(String testID){
		this.testCaseReferences = new HashMap<String, String>();
		Iterator <Row> rowIter;
		Iterator <Cell> cellIter;		
		int wbSheetCount, i;
		List sheetNames = null;
		XSSFSheet testSheet = null;
		Row row = null;
		Cell cell = null;
		String tstScenario = null;
		String logIDRef = null;
		String posCity = null;
		String posCode = null;
		String posCurrency = null;
		String itenRef = null;
		String paxCntRef = null;
		String fltCosRef = null;
		String priceOptRef = null;
		double noOfPayments;
		String paymentRef_1 = null;
		String paymentRef_2 = null;
		String paymentRef_3 = null;
		
		try {			
			
			wbSheetCount = this.testWorkBook.getNumberOfSheets();
			//System.out.println(wbSheetCount);
					
			for (i=0; i< wbSheetCount; i++) {
				//System.out.println(testWorkBook.getSheetName(i));
				if (testWorkBook.getSheetName(i).equalsIgnoreCase("Test_Scenario_Identifiers")) {
					testSheet = testWorkBook.getSheetAt(i);
				}			
			}
			if (testSheet!=null) {
				rowIter = testSheet.iterator();
				while (rowIter.hasNext()) {
					row = rowIter.next(); 
					cellIter =  row.cellIterator(); // Cells iterator in the row
					while (cellIter.hasNext()) {
						cell = cellIter.next(); 					
						if (cell.getStringCellValue().equalsIgnoreCase(testID)) {
							tstScenario = cellIter.next().getStringCellValue();
							logIDRef = cellIter.next().getStringCellValue();
							posCity = cellIter.next().getStringCellValue();
							posCode = cellIter.next().getStringCellValue();
							posCurrency = cellIter.next().getStringCellValue();
							itenRef = cellIter.next().getStringCellValue();
							paxCntRef = cellIter.next().getStringCellValue();
							fltCosRef = cellIter.next().getStringCellValue();
							priceOptRef = cellIter.next().getStringCellValue();
							noOfPayments = cellIter.next().getNumericCellValue();
							paymentRef_1 = cellIter.next().getStringCellValue();
							paymentRef_2 = cellIter.next().getStringCellValue();
							paymentRef_3 = cellIter.next().getStringCellValue();
							this.testCaseReferences.put("tstScenario", tstScenario);
							this.testCaseReferences.put("logIDRef", logIDRef);
							this.testCaseReferences.put("posCity", posCity);
							this.testCaseReferences.put("posCode", posCode);
							this.testCaseReferences.put("posCurr", posCurrency);
							this.testCaseReferences.put("itenRef", itenRef);
							this.testCaseReferences.put("paxCntRef", paxCntRef);
							this.testCaseReferences.put("fltCosRef", fltCosRef);
							this.testCaseReferences.put("priceOptRef", priceOptRef);
							this.testCaseReferences.put("noOfPayments", String.valueOf(noOfPayments));
							this.testCaseReferences.put("paymentRef_1", paymentRef_1);
							this.testCaseReferences.put("paymentRef_2", paymentRef_2);
							this.testCaseReferences.put("paymentRef_3", paymentRef_3);
							break;
						}
					}					
				}				
			}
			if(!this.testCaseReferences.isEmpty()) {
				
			}
			//return testCaseEntries;
		}catch (Exception e) {
			e.printStackTrace();	
			//return null;
		}
	}
	
	public void setTestCaseEntries(){
		HashMap<String, String> logCredentials = new HashMap<String, String>();
		Iterator <Row> rowIter;
		Iterator <Cell> cellIter;		
		int wbSheetCount, i;
		XSSFSheet testSheet = null;
		Row row = null;
		Cell cell = null;
		String userName = null;
		String password = null;
		
		try {			
			
			wbSheetCount = this.testWorkBook.getNumberOfSheets();
			//System.out.println(wbSheetCount);
					
			for (i=0; i< wbSheetCount; i++) {
				System.out.println(testWorkBook.getSheetName(i));
				if (testWorkBook.getSheetName(i).equalsIgnoreCase("EnvironmentURLs")) {
					testSheet = testWorkBook.getSheetAt(i);
					this.setAppAccessEntries(testSheet);
				}else if (testWorkBook.getSheetName(i).equalsIgnoreCase("LoginCredentials")) {
					testSheet = testWorkBook.getSheetAt(i);
					this.setLogCred(testSheet);					
				}else if (testWorkBook.getSheetName(i).equalsIgnoreCase("PointOFSales")) {
					testSheet = testWorkBook.getSheetAt(i);
					this.setCityName(testSheet);					
				}else if (testWorkBook.getSheetName(i).equalsIgnoreCase("Flight_Selection")) {
					testSheet = testWorkBook.getSheetAt(i);
					this.setSegmentsCOS(testSheet);					
				}else if (testWorkBook.getSheetName(i).equalsIgnoreCase("Test_Itinerary")) {
					testSheet = testWorkBook.getSheetAt(i);
					this.setItineraryDetails(testSheet);
				}else if (testWorkBook.getSheetName(i).equalsIgnoreCase("PriceOptions")) {
					testSheet = testWorkBook.getSheetAt(i);
					this.setPricingOptions(testSheet);
				}else if (testWorkBook.getSheetName(i).equalsIgnoreCase("PassengerCount")) {
					testSheet = testWorkBook.getSheetAt(i);
					this.setPassengerCount(testSheet);
					this.setPassengerDetailsList();
				}else if (testWorkBook.getSheetName(i).equalsIgnoreCase("PaymentTable")) {
					testSheet = testWorkBook.getSheetAt(i);
					this.setOrderPaymentDetails(testSheet);
				}
			}
		}catch(Exception e) {
		}
	}
	
	//Retrieve the city name based on the POS from test data sheet.
	private void setCityName(XSSFSheet testSheet){		
		Iterator <Row> rowIter;
		Iterator <Cell> cellIter;		
		int i;
		Row row = null;
		Cell cell = null;			
		String tstCos;
		boolean segCtyNameRetrieved = false;
		try {
			this.setPosCityCode(this.testCaseReferences.get("posCity"));
			this.setPosCode(this.testCaseReferences.get("posCode"));
			this.setPosCurrency(this.testCaseReferences.get("posCurr"));
			if (testSheet!=null) {
				rowIter = testSheet.iterator();
				while (rowIter.hasNext()) {
					row = rowIter.next(); 
					cellIter =  row.cellIterator(); // Cells iterator in the row
					while (cellIter.hasNext()) {
						cell = cellIter.next(); 					
						if (cell.getStringCellValue().equalsIgnoreCase(this.testCaseReferences.get("posCity"))) {
							this.setPosCityName(cellIter.next().getStringCellValue());
							segCtyNameRetrieved = true;
							break;
						}						
					}		
					//Assert.assertTrue(segCtyNameRetrieved, "Error retrieving segment COS from test data sheet");
				}				
			}else {
				//Assert.assertTrue(false, "Error Flight_Selection test data sheet is not available");
			}			
		}catch (Exception e) {
			e.printStackTrace();
		}
		}

	//Retrieve the App URL, env link from test data sheet.
	private void setAppAccessEntries(XSSFSheet testSheet){		
		Iterator <Row> rowIter;
		Iterator <Cell> cellIter;
		Row row = null;
		Cell cell = null;
		String urlString = null;
		String envLink = null;
		boolean appURLRetrieved = false;
		try {
			if (testSheet!=null) {
				rowIter = testSheet.iterator();
				while (rowIter.hasNext()) {
					row = rowIter.next(); 
					cellIter =  row.cellIterator(); // Cells iterator in the row
					while (cellIter.hasNext()) {
						cell = cellIter.next();
						System.out.println(cell.getStringCellValue());
						if (cell.getStringCellValue().equalsIgnoreCase("SIT_Main")) {
							urlString = cellIter.next().getStringCellValue();
							envLink = cellIter.next().getStringCellValue();
							this.setAppURL(urlString);
							this.setEnvLink(envLink);
							appURLRetrieved = true;
							break;
						}						
					}
				}	
				//Assert.assertTrue(appURLRetrieved, "Error retrieving App URL from test data sheet");
			}else {
				//Assert.assertTrue(false, "Error EnvironmentURLs test data sheet is not available");
			}			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Retrieving and Setting up login credentials from test data sheet
	private void setLogCred(XSSFSheet testSheet){
		Iterator <Row> rowIter;
		Iterator <Cell> cellIter;		
		Row row = null;
		Cell cell = null;
		String userName = null;
		String password = null;
		boolean credRetrived = false;
		try {			
			if (testSheet!=null) {
				rowIter = testSheet.iterator();
				while (rowIter.hasNext()) {
					row = rowIter.next(); 
					cellIter =  row.cellIterator(); // Cells iterator in the row
					while (cellIter.hasNext()) {
						cell = cellIter.next(); 					
						if (cell.getStringCellValue().equalsIgnoreCase(this.testCaseReferences.get("logIDRef"))) {
							userName = cellIter.next().getStringCellValue();
							password = cellIter.next().getStringCellValue();
							this.setUname(userName);
							this.setPassword(password);
							credRetrived = true;
							break;
						}
					}					
				}			
				//Assert.assertTrue(credRetrived, "Error retrieving Login Credentials from test data sheet");
			}else {
				//Assert.assertTrue(false, "Error LoginCredentials test data sheet is not available");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}	
	

	//Retrieve the Itinerary details from test data sheet
	private void setItineraryDetails(XSSFSheet testSheet){		
		Iterator <Row> rowIter;
		Iterator <Cell> cellIter;	
		Calendar cal;
		LocalDate dte;
		SimpleDateFormat smdf = new SimpleDateFormat("mm/dd/yyyy");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String segReq;
		String dteOfJny;
		int dteOfJnyDaysCnt, i, j;
		HashMap<String, HashMap> itenMap = new HashMap<String, HashMap>();
		HashMap<String, String> itenEntries=null;
		Row row = null;
		Cell cell = null;
		String urlString = null;
		String envLink = null;
		boolean payDetlsRetrieved = false;
		
		try {
			if (testSheet!=null) {
				System.out.println(testSheet.getSheetName());
				rowIter = testSheet.iterator();
				while (rowIter.hasNext()) {
					row = rowIter.next(); 					
					cellIter =  row.cellIterator(); // Cells iterator in the row
					i=1;
					while (cellIter.hasNext()) {
						cell = cellIter.next(); 	
						if(cell.getStringCellValue().equalsIgnoreCase("NA")) {
							//Setting number of itineraries after an Origin is marked as NA in test data sheet - No segments after than
							this.calAndSetNoOfItinery(i);
							break;
						}
						System.out.println(cell.getStringCellValue());
						if (cell.getStringCellValue().equalsIgnoreCase(this.testCaseReferences.get("itenRef"))) {
							segReq = cellIter.next().getStringCellValue();
							while (!segReq.trim().equalsIgnoreCase("NA")) {
								itenEntries = new HashMap<String, String>();
								itenEntries.put("ORG", segReq.trim());
								itenEntries.put("DES", cellIter.next().getStringCellValue().trim());
								dteOfJnyDaysCnt = (int)cellIter.next().getNumericCellValue();
								dte = LocalDate.now().plusDays(dteOfJnyDaysCnt);								
								dteOfJny = formatter.format(dte);
								itenEntries.put("doj", dteOfJny);
								itenEntries.put("time", cellIter.next().getStringCellValue().trim());
								
								//RoundTrip
								if (i==1) {
									if((cellIter.next().getStringCellValue().trim()).equalsIgnoreCase("Yes")) {
										itenEntries.put("rndTrp", "Yes");
										this.setRoundTrip(true);
									}else {
										itenEntries.put("rndTrp", "NA");
										this.setRoundTrip(false);
									}			
								}
								itenMap.put("itenEntry_"+i, itenEntries);
								segReq = cellIter.next().getStringCellValue();	
								i++;
								payDetlsRetrieved = true;								
							}
						}						
					}
					this.setIteneraryMap(itenMap);
					if(this.isRoundTrip()) {
						j=1;
						itenEntries = new HashMap<String, String>();
						while(i>1) { //given > 1 because i is incremented 1 after all entries								
							//return dates starts from column W
							dteOfJnyDaysCnt = (int)row.getCell(21+j).getNumericCellValue();
							dte = LocalDate.now().plusDays(dteOfJnyDaysCnt);
							dteOfJny = formatter.format(dte);
							itenEntries.put("returnDt_"+j, dteOfJny);
							j++; i--;
						}
						this.setRoundTripDts(itenEntries);
					}
				}	
				//Assert.assertTrue(payDetlsRetrieved, "Error retrieving Itinerary Details from test data sheet");
			}else {
				//Assert.assertTrue(false, "Error Test_Itinerary test data sheet is not available");
			}			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Retrieve the count of passengers and FQTV count	
	private void setPassengerCount(XSSFSheet testSheet){		
		Iterator <Row> rowIter;
		Iterator <Cell> cellIter;		
		Row row = null;
		Cell cell = null;
		HashMap<String,String> paxCount = new HashMap<String, String>();
		boolean paxCountRetrieved = false;
		try {
			if (testSheet!=null) {
				rowIter = testSheet.iterator();
				while (rowIter.hasNext()) {
					row = rowIter.next(); 
					cellIter =  row.cellIterator(); // Cells iterator in the row
					while (cellIter.hasNext()) {
						cell = cellIter.next(); 					
						if (cell.getStringCellValue().equalsIgnoreCase(this.testCaseReferences.get("paxCntRef"))) {
							paxCount.put("adultCnt", String.valueOf((int)cellIter.next().getNumericCellValue()));
							this.setFqtvCount((int)cellIter.next().getNumericCellValue());							
							paxCount.put("chdCnt", String.valueOf((int)cellIter.next().getNumericCellValue()));
							paxCount.put("infCnt", String.valueOf((int)cellIter.next().getNumericCellValue()));
							paxCount.put("insCnt", String.valueOf((int)cellIter.next().getNumericCellValue()));
							this.setPaxCountIncFQTV(paxCount);
							paxCountRetrieved = true;
							break;
						}						
					}
				}	
				//Assert.assertTrue(paxCountRetrieved, "Error retrieving Passenger Count from test data sheet");
			}else {
				//Assert.assertTrue(false, "Error PassengerCount test data sheet is not available");
			}			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	//Retrieve the COS of each segment from test data sheet.
	private void setSegmentsCOS(XSSFSheet testSheet){		
		Iterator <Row> rowIter;
		Iterator <Cell> cellIter;		
		int i;
		HashMap<String, String> segCOSMap = new HashMap<String, String>();
		//XSSFSheet testSheet = null;
		Row row = null;
		Cell cell = null;			
		String tstCos;
		boolean segCOSRetrieved = false;
		try {
			if (testSheet!=null) {
				rowIter = testSheet.iterator();
				while (rowIter.hasNext()) {
					row = rowIter.next(); 
					cellIter =  row.cellIterator(); // Cells iterator in the row
					while (cellIter.hasNext()) {
						cell = cellIter.next(); 					
						if (cell.getStringCellValue().equalsIgnoreCase(this.testCaseReferences.get("fltCosRef"))) {
							i=1;
							tstCos = cellIter.next().getStringCellValue().trim();
							while (!tstCos.equalsIgnoreCase("NA")) {
								segCOSMap.put("segCos_"+i, tstCos);
								tstCos = cellIter.next().getStringCellValue().trim();
								i++;
							}
							segCOSRetrieved = true;		
							break;
						}						
					}
					this.setSegCOS(segCOSMap);
				}	
				//Assert.assertTrue(segCOSRetrieved, "Error retrieving segment COS from test data sheet");
			}else {
				//Assert.assertTrue(false, "Error Flight_Selection test data sheet is not available");
			}			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void calAndSetNoOfItinery(int noOfSegments) {
		if(this.isRoundTrip()) {
			this.setNoOfItineraries(noOfSegments*2);
		}else {
			this.setNoOfItineraries(noOfSegments);
		}
		
	}
	
	//Retrieving and setting up the Pricing options from test data sheet
		private void setPricingOptions(XSSFSheet testSheet){		
			Iterator <Row> rowIter;
			Iterator <Cell> cellIter;		
			Row row = null;
			Cell cell = null;
			boolean prcOption = false;
			try {
				if (testSheet!=null) {
					rowIter = testSheet.iterator();
					while (rowIter.hasNext()) {
						row = rowIter.next(); 
						cellIter =  row.cellIterator(); // Cells iterator in the row
						while (cellIter.hasNext()) {
							cell = cellIter.next(); 					
							if (cell.getStringCellValue().equalsIgnoreCase(this.testCaseReferences.get("priceOptRef"))) {
								this.setPriceOption(cellIter.next().getStringCellValue());
								prcOption = true;
								break;
							}						
						}
					}	
					//Assert.assertTrue(prcOption, "Error retrieving Price Option from test data sheet");
				}else {
					//Assert.assertTrue(false, "Error PriceOptions test data sheet is not available");
				}			
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		
	//Retrieve the passenger details
	public void setPassengerDetailsList() {
		Iterator <Row> rowIter;
		Iterator <Cell> cellIter;		
		Row row = null;
		Cell cell = null;
		int i, adtCnt,chdCnt,infCnt,insCnt, fqtvCount;
		this.passengerEntities = new ArrayList<HashMap>();
		boolean prcOption = false;
		try {			
			if (this.getPaxCountIncFQTV().size()>0) {
				fqtvCount = this.getFqtvCount();
				adtCnt = Integer.parseInt(this.getPaxCountIncFQTV().get("adultCnt"));
				if (this.getFqtvCount() > 0) {
					adtCnt = adtCnt-fqtvCount;
				}
				while (fqtvCount>0) {
					//Code should be embedded later
				}
				while(adtCnt>0) {
					this.getPaxDetails(false);
					adtCnt = adtCnt-1;
				}								
			}
						
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getPaxDetails(boolean isFQTV) {
		Iterator <Row> rowIter;
		Iterator <Cell> cellIter;		
		int wbSheetCount, i;
		List sheetNames = null;
		XSSFSheet testSheet = null;
		Row row = null;
		Cell cell = null;
		boolean passengerRetrieved = false;
		HashMap<String, String> passengerEntity = new HashMap<String, String>();		 
		try {			
			
			wbSheetCount = this.testWorkBook.getNumberOfSheets();
								
			for (i=0; i< wbSheetCount; i++) {				
				if (testWorkBook.getSheetName(i).equalsIgnoreCase("PaxInformation")) {
					testSheet = testWorkBook.getSheetAt(i);
				}			
			}
			if (testSheet!=null) {
			rowIter = testSheet.iterator();
			while (rowIter.hasNext()) {
				row = rowIter.next();
					cellIter =  row.cellIterator(); // Cells iterator in the row
					while (cellIter.hasNext()) {
						cellIter.next();
						cell = cellIter.next();//Points to second cell
						if (cell.getStringCellValue().equalsIgnoreCase("Adult")) {
							passengerEntity.put("passengerType", "ADT");
							passengerEntity.put("fqtvPax", "NA");
							passengerEntity.put("fqtvNumber", "");
							passengerEntity.put("surName", cellIter.next().getStringCellValue());
							passengerEntity.put("givenName", cellIter.next().getStringCellValue());
							passengerEntity.put("dteOfBirth", cellIter.next().getStringCellValue());
							passengerEntity.put("gender", cellIter.next().getStringCellValue());
							passengerEntity.put("email", cellIter.next().getStringCellValue());
							passengerEntity.put("emailLang", cellIter.next().getStringCellValue());
							passengerEntity.put("nationality", cellIter.next().getStringCellValue());
							passengerEntity.put("location", cellIter.next().getStringCellValue());
							passengerEntity.put("phonetype", cellIter.next().getStringCellValue());
							passengerEntity.put("countryCode", cellIter.next().getStringCellValue());
							passengerEntity.put("areaCode", this.getCellValue(cellIter.next()));
							passengerEntity.put("phoneNumber", this.getCellValue(cellIter.next()));
							passengerEntity.put("emerCntName", this.getCellValue(cellIter.next()));
							passengerEntity.put("emerRel", this.getCellValue(cellIter.next()));
							passengerEntity.put("emerPhonetype", this.getCellValue(cellIter.next()));
							passengerEntity.put("emerCountryCode", this.getCellValue(cellIter.next()));
							passengerEntity.put("emerAreaCode", this.getCellValue(cellIter.next()));
							passengerEntity.put("emerPhoneNumber", this.getCellValue(cellIter.next()));		
							this.passengerEntities.add(passengerEntity);
							passengerRetrieved = true;
							break;
						}						
					}
				}
			//Assert.assertTrue(passengerRetrieved, "Passenger details not retrieved");
			}else 
			{
				//Assert.assertTrue(false, "Error PaxInformation test data sheet is not available");
			}	
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private String getCellValue(Cell cell) { 
		String cellValue="";
		
		if (cell != null && cell.getCellType() != CellType.BLANK) {
			if(cell.getCellType()==CellType.NUMERIC) {
				cellValue = String.valueOf((int)cell.getNumericCellValue());
			}else if (cell.getStringCellValue().length()>0){
				cellValue = cell.getStringCellValue();
			}
		}else {
			cellValue="";
		}
		return cellValue;
	}
	//Retrieving and setting up the Payment details from test data sheet
	private void setOrderPaymentDetails(XSSFSheet testSheet) {
		Iterator <Row> rowIter;
		Iterator <Cell> cellIter;		
		Row row = null;
		Cell cell = null;	
		HashMap<String, String> tstDatPaymentDetails = new HashMap<String,String>();
		String payRefPar, tstDataPayRef;
		boolean payDetailAvbl = false;
		this.paymentDetails =  new ArrayList<HashMap>();
		int noOfPayments = Integer.parseInt(this.testCaseReferences.get("noOfPayments"));
		
		int i = 1; 
		while (i <= noOfPayments) {
			payRefPar =  "paymentRef_"+ String.valueOf(i) ;
			tstDataPayRef = this.testCaseReferences.get(payRefPar);
			if (testSheet!=null) {
				rowIter = testSheet.iterator();
				while (rowIter.hasNext()) {
					row = rowIter.next(); 
					cellIter =  row.cellIterator(); // Cells iterator in the row
					while (cellIter.hasNext()) {
						cell = cellIter.next(); 					
						if (cell.getStringCellValue().equalsIgnoreCase(this.testCaseReferences.get("tstDataPayRef"))) {
							tstDatPaymentDetails.put("pamentType", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("cardType", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("cardNum", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("expDate", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("cvvNum", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("crdHldr", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("emdNum", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("miscAccName", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("miscCstCntr", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("tarjetaRef", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("sstNum", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("bnkTrSSN", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("fName", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("lName", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("email", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("phone", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("addOne", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("addTwo", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("city", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("state", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("zip", cellIter.next().getStringCellValue());
							tstDatPaymentDetails.put("country", cellIter.next().getStringCellValue());
							this.paymentDetails.add(tstDatPaymentDetails);							
							payDetailAvbl = true;
							break;
						}
					}					
				}				
				//Assert.assertTrue(payDetailAvbl, "Error retrieving Payment Details from test data sheet");
			}else {
				//Assert.assertTrue(false, "Error PaymentEntries test data sheet is not available");
			}
		}
		
	}
}
