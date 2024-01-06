package com.core;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.model.AutoTableScenarioDataUpdate;
import com.model.TableDataService;
import com.utilities.ExcelUtility;

public class AutoTestDataGenerator {
	GenerateFlightData generateFlightData;
	StringTokenizer stringTokenizer;
	Connection connection;
	TableDataService tableDataService;	
	AutoTableScenarioDataUpdate autoTableScenarioDataUpdate;
	HashMap<Integer, HashMap<String, String>> autoScenarioMap;
	HashMap<String, HashMap<String, String>> autoScenarioMapreturnScenarioMap, inputDtlsMapFrFltData;
	ExcelUtility excelUtility;
	FlightOperations fltOperations;
	

	public AutoTestDataGenerator(Connection connection) {
		this.connection = connection;
		generateFlightData = new GenerateFlightData(connection);
		tableDataService = new TableDataService(connection);
		autoTableScenarioDataUpdate = new AutoTableScenarioDataUpdate(connection);
		autoScenarioMap = tableDataService.getAutoScenarios();	
		excelUtility = new ExcelUtility();
		fltOperations = new FlightOperations();
	}
	
	
	public String generateTestData(String autoScenIDsString) {
		stringTokenizer = new StringTokenizer(autoScenIDsString, ",");
		HashMap<String, String> indScenarioMap, indvTDSheetMap;
		//returnScenarioMap = new HashMap();
		boolean datSheetCreated = false;
		String autoTDSheetID = "", strToken="", tdSheetName="";
		try {
			this.createTDDocument();
			autoTableScenarioDataUpdate.truncateTestDataRecordTable();
			tableDataService.truncateTempTable();			
			while (stringTokenizer.hasMoreElements()) {
				strToken = stringTokenizer.nextElement().toString();
				indScenarioMap = autoScenarioMap.get(Integer.parseInt(strToken.trim()));
				if (indScenarioMap != null && indScenarioMap.size() > 0) {
					autoTDSheetID = indScenarioMap.get("atsrid");
					if (autoTDSheetID != null && autoTDSheetID.length() > 0) {
						indvTDSheetMap = tableDataService.getATSRDtls(Integer.parseInt(autoTDSheetID.trim()));
						if (indvTDSheetMap != null && indvTDSheetMap.size() > 0) {
							//Test data work book sheet name
							tdSheetName = indvTDSheetMap.get("sheetName");
							//To create Excel worksheet added method 'createTDDocument()' to handle adding all headers and commented below
							//excelUtility.createScenarioHeaders(indvTDSheetMap);
							//Update table with the required test data
							autoTableScenarioDataUpdate.updateScenarioTestData(this.createTestDataDerivedInputs(indScenarioMap.get("scenario"), tdSheetName));							
						}
					}
				}
			}
			TreeMap<String, HashMap<String, String>> sortedMap = new TreeMap<>();
			sortedMap.putAll(autoTableScenarioDataUpdate.getTestDataRecords());
			excelUtility.addTestDataToExcel(sortedMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return excelUtility.getFileName().getPath();
	}
	
	public HashMap<String, String> createTestDataDerivedInputs(String scenarioName, String tdSheetName) {
		String cos, shipNumber="", connAirCode="CM";
		boolean isCheckInReq=false, isConnectionReq=false, isShipRequired=false;
		int noOfPax, tblID, inStAvl,connStAvl, updatedInitalSegSeatCnt=0, updatedConnSegSeatCnt=0, reqDayJrny=0, noOfSeg=0;
		HashMap<String, String> indvScenario, scenarioTestData = new HashMap();
		try {
			indvScenario = tableDataService.getIndvScenario(scenarioName.trim());
			if(indvScenario!=null && !indvScenario.isEmpty()) {
				cos = indvScenario.get("cos");
				if(indvScenario.get("checkin").equalsIgnoreCase("TRUE")) {
					isCheckInReq=true;
				}
				if(indvScenario.get("connection").equalsIgnoreCase("TRUE")) {
					isConnectionReq=true;
				}
				if(indvScenario.get("needShipNumber").equalsIgnoreCase("TRUE")) {
					isShipRequired=true;
				}
				noOfSeg = Integer.parseInt(indvScenario.get("noOfSeg"));
				noOfPax = Integer.parseInt(indvScenario.get("paxcnt"));
				connAirCode = indvScenario.get("connAirCode");
				scenarioTestData = tableDataService.getFlightDetails(noOfPax, cos, isCheckInReq, isConnectionReq, isShipRequired,Integer.parseInt(indvScenario.get("reqNoOfDays")), noOfSeg, connAirCode);
				if(scenarioTestData!=null && !scenarioTestData.isEmpty()) {
					scenarioTestData.put("noOfPax", indvScenario.get("paxcnt"));
					scenarioTestData.put("tdSheetName", tdSheetName);
					scenarioTestData.put("scenarioName", scenarioName);
					
					//updating the seats in table
					if(scenarioTestData.get("id")==null) {
						System.out.print(isConnectionReq);
					}
					tblID = Integer.parseInt(scenarioTestData.get("id"));
					inStAvl = Integer.parseInt(scenarioTestData.get("stAvl"));
					connStAvl = Integer.parseInt(scenarioTestData.get("cnStAvl"));
					
					if(inStAvl>0) {
						updatedInitalSegSeatCnt = inStAvl - noOfPax;
					}
					if(connStAvl>0 && isConnectionReq) {
						updatedConnSegSeatCnt = connStAvl - noOfPax;
					}else {
						updatedConnSegSeatCnt = connStAvl;
					}
					tableDataService.updateFlightSeatCount(tblID, updatedInitalSegSeatCnt, updatedConnSegSeatCnt);
				}
				
				//End of table update
				
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scenarioTestData;
	}
	private void createTDDocument() {
		HashMap<String, HashMap<String, String>> autoScenarioMap = tableDataService.getATAllSRHeaderDTls();
		TreeMap<String, HashMap<String, String>> sortedMap = new TreeMap<>();
		try {			
			sortedMap.putAll(autoScenarioMap);
			for (Map.Entry<String, HashMap<String, String>> tdEntry: sortedMap.entrySet()){
				excelUtility.createScenarioHeaders(tdEntry.getValue());			
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
