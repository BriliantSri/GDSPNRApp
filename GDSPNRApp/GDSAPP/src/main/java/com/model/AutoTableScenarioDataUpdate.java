package com.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.core.FlightOperations;
import com.core.FlightSeatCollector;

public class AutoTableScenarioDataUpdate {
	Connection connection;
	Statement stmt;
	ResultSet resultSet;
	FlightOperations fltOpers;
	HashMap<String, String> fltMap;
	List<HashMap<String, String>> fltDtls;
	String valueOne="NA", valueTwo="NA", valueThree="NA", valueFour="NA", valueFive="NA", valueSix="NA", valueSeven="NA";
	String valueEight="NA", valueNine="NA", valueTen="NA", valueEleven="NA", valueTweleve="NA", valueThirteen="NA",valueFourTeen="NA";
	String valueFifteen="NA", valueSixteen="NA", valueSeventeen="NA", valueEighteen="NA", valueNineteen="NA", valueTwenty="NA",valueTwentyone="NA";
	String valueTwentytwo="NA", valueTwentythree="NA", valueTwentyfour="NA", valueTwentyfive="NA", valueTwentysix="NA", valueTwentyseven="NA";
	String valueTwentyeight="NA", valueTwentynine="NA", valueThirty="NA", valueThirtyone="NA", valueThirtytwo="NA", valueThirtythree="NA",valueThirtyFour="NA", valueThirtyFive="NA";
	
	FlightSeatCollector flightSeatCollector;
	List<String> seats;
	Iterator<String> iter;
	public AutoTableScenarioDataUpdate(Connection connection) {
		this.connection = connection;	
		flightSeatCollector = new FlightSeatCollector(connection);
		fltOpers = new FlightOperations();
	}
	
	
	public void setDefaulValues() {
		this.valueOne="NA";this.valueTwo="NA";this.valueThree="NA";this.valueFour="NA";this.valueFive="NA";
				this.valueSix="NA";this.valueSeven="NA";this.valueEight="NA";this.valueNine="NA";this.valueTen="NA";this.valueEleven="NA";
				this.valueTweleve="NA";this.valueThirteen="NA";this.valueFourTeen="NA";this.valueFifteen="NA";this.valueSixteen="NA";
				this.valueSeventeen="NA";this.valueEighteen="NA";this.valueNineteen="NA";this.valueTwenty="NA";this.valueTwentyone="NA";
				this.valueTwentytwo="NA";this.valueTwentythree="NA";this.valueTwentyfour="NA";this.valueTwentyfive="NA";this.valueTwentysix="NA";
				this.valueTwentyseven="NA";this.valueTwentyeight="NA";this.valueTwentynine="NA";this.valueThirty="NA";this.valueThirtyone="NA";
				this.valueThirtytwo="NA";this.valueThirtythree="NA";this.valueThirtyFour="NA";this.valueThirtyFive="NA";
	}
	public void updateScenarioTestData(HashMap<String, String> scenarioTestData) {
		HashMap<String, String> tableTestData = new HashMap<String, String> ();
		String sheetName, scenario, departureDays, fltNo, org, des, cos, noOfPax, connFlt, connCity, jrnyDt,shipNumber, conDtCnt; 
		
		try {
			if(scenarioTestData!=null) {
				this.setDefaulValues();
				sheetName=scenarioTestData.get("tdSheetName");
				scenario=scenarioTestData.get("scenarioName");
				departureDays=scenarioTestData.get("dtCnt");
				fltNo=scenarioTestData.get("fltNo");
				org=scenarioTestData.get("origin");
				des=scenarioTestData.get("destination");
				cos=scenarioTestData.get("cos");
				noOfPax=scenarioTestData.get("noOfPax");
				connFlt= scenarioTestData.get("cnnFlt");
				conDtCnt = scenarioTestData.get("conDtCnt");
				connCity=scenarioTestData.get("cnnCty");
				jrnyDt = scenarioTestData.get("doj");		
				shipNumber = scenarioTestData.get("shipNumber");		
				if(scenario.equalsIgnoreCase("create_booking_service_onepax")) {	
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;
					this.valueSeven=cos;
					this.valueEight=noOfPax;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Create_booking_multiplepax_with_same_surname(3 PAX)")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;
					this.valueSeven=cos;
					this.valueEight=noOfPax;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Start Boarding using Boarding option as ‘Sequence’")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;	
					this.valueSix = this.getSeatNumber(fltNo, org, des, jrnyDt, cos);
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Input with more PassengerFlightInfo informations")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;					
					this.valueSix = this.getSeatNumber(fltNo, org, des, jrnyDt, cos);
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Start CancelBoardedPassenger function with BoardingOption as ‘Seat’")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;					
					this.valueSix = this.getSeatNumber(fltNo, org, des, jrnyDt, cos);
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("AbortBoarding for a particular flight.")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;					
					this.valueSix = this.getSeatNumber(fltNo, org, des, jrnyDt, cos);
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Start BoardingComplete function for a flight number")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;					
					this.valueSix = this.getSeatNumber(fltNo, org, des, jrnyDt, cos);
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Host Airline Inventory Request")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("HA Inventory request with a date in the past")) {					
					fltMap = this.getPastFlightDetails(2, "Y");
					if(fltMap!=null && !fltMap.isEmpty()) {
						this.valueOne = sheetName;
						this.valueTwo=scenario;
						this.valueThree=fltMap.get("dtCnt");
						this.valueFour=fltMap.get("fltNo");
						this.valueFive=fltMap.get("origin");					
						this.valueSix = fltMap.get("destination");
						this.insertTestDataRecord();
					}
				
				}else if(scenario.equalsIgnoreCase("More than 7 HA Inventory requests at a time")) {
					fltDtls = this.getSevenHAFltDetails(2, "Y");
					Iterator<HashMap<String, String>> iter;
					if(fltDtls!=null && !fltDtls.isEmpty()) {
						iter = fltDtls.iterator();
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueOne = sheetName;
							this.valueTwo=scenario;
							this.valueThree=fltMap.get("dtCnt");
							this.valueFour=fltMap.get("fltNo");
							this.valueFive=fltMap.get("origin");					
							this.valueSix = fltMap.get("destination");							
						}					
						if (iter.hasNext()) {
							fltMap = iter.next();							
							this.valueSeven=fltMap.get("dtCnt");
							this.valueEight=fltMap.get("fltNo");
							this.valueNine=fltMap.get("origin");					
							this.valueTen = fltMap.get("destination");							
						}
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueEleven=fltMap.get("dtCnt");
							this.valueTweleve=fltMap.get("fltNo");
							this.valueThirteen=fltMap.get("origin");					
							this.valueFourTeen = fltMap.get("destination");							
						}
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueFifteen=fltMap.get("dtCnt");
							this.valueSixteen=fltMap.get("fltNo");
							this.valueSeventeen=fltMap.get("origin");					
							this.valueEighteen = fltMap.get("destination");							
						}
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueNineteen=fltMap.get("dtCnt");
							this.valueTwenty=fltMap.get("fltNo");
							this.valueTwentyone=fltMap.get("origin");					
							this.valueTwentytwo = fltMap.get("destination");							
						}
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueTwentythree=fltMap.get("dtCnt");
							this.valueTwentyfour=fltMap.get("fltNo");
							this.valueTwentyfive=fltMap.get("origin");					
							this.valueTwentysix = fltMap.get("destination");							
						}
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueTwentyseven=fltMap.get("dtCnt");
							this.valueTwentyeight=fltMap.get("fltNo");
							this.valueTwentynine=fltMap.get("origin");					
							this.valueThirty = fltMap.get("destination");							
						}
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueThirtyone=fltMap.get("dtCnt");
							this.valueThirtytwo=fltMap.get("fltNo");
							this.valueThirtythree=fltMap.get("origin");					
							this.valueThirtyFour = fltMap.get("destination");							
						}
						
						this.insertTestDataRecord();
					}					
				}else if(scenario.equalsIgnoreCase("Code_2_eticketed_passengers")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;
					this.valueSeven=cos;
					this.valueEight=noOfPax;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Code_12_Passengers_with_advance_seats")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;
					this.valueSeven=cos;
					this.valueEight=noOfPax;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Regular availability with defaults")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Reward availability")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("create_booking_service_onepax_1")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario.substring(0, scenario.length()-2);
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;
					this.valueSeven=cos;
					this.valueEight=noOfPax;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Create_booking_multiplepax_with_same_surname(3 PAX)")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;
					this.valueSeven=cos;
					this.valueEight=noOfPax;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("create_booking_one_seg_telephone_ticketing")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;		
					//this.valueSeven ="KUNGFU";
					//this.valueEight = "PANDA";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("create_booking_four_seg_two_pax_two_SSR")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=connCity;
					this.valueSeven = departureDays;
					this.valueEight=connFlt;
					this.valueNine=connCity;
					this.valueTen=des;	
					this.valueEleven=scenarioTestData.get("cnnTwoDtCnt");
					this.valueTweleve=scenarioTestData.get("cnnTwoFlt");
					this.valueThirteen=scenarioTestData.get("cnnTwoOrg");					
					this.valueFourTeen = scenarioTestData.get("cnnTwoConnCty");							
					this.valueFifteen=scenarioTestData.get("cnnTwoDtCnt");
					this.valueSixteen=scenarioTestData.get("cnnTwoConnFlt");
					this.valueSeventeen=scenarioTestData.get("cnnTwoConnCty");					
					this.valueEighteen=scenarioTestData.get("cnnTwoDes");							
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("create_booking_four_seg_2_pax_one_remark_asa")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=connCity;
					this.valueSeven = departureDays;
					this.valueEight=connFlt;
					this.valueNine=connCity;
					this.valueTen=des;	
					this.valueEleven=scenarioTestData.get("cnnTwoDtCnt");
					this.valueTweleve=scenarioTestData.get("cnnTwoFlt");
					this.valueThirteen=scenarioTestData.get("cnnTwoOrg");					
					this.valueFourTeen = scenarioTestData.get("cnnTwoConnCty");							
					this.valueFifteen=scenarioTestData.get("cnnTwoDtCnt");
					this.valueSixteen=scenarioTestData.get("cnnTwoConnFlt");
					this.valueSeventeen=scenarioTestData.get("cnnTwoConnCty");					
					this.valueEighteen=scenarioTestData.get("cnnTwoDes");							
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Check for invalid Ticketing in request")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Check for invalid PriceInfo in request")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Add a default time limit when no data is specified in Ticketing")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("ModifyInventory Request contains single Authorization level")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=fltNo;
					this.valueFour=departureDays;
					this.valueFive=org;
					this.valueSix=des;					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("ModifyInventory Request contains single MaxSeatsAllotted")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=fltNo;
					this.valueFour=departureDays;
					this.valueFive=org;
					this.valueSix=des;					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("ModifyInventory Request with AuthorizationLevel and MaxSeatsAllotted for CM carrier")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=fltNo;
					this.valueFour=departureDays;
					this.valueFive=org;
					this.valueSix=des;					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Cancel Booking")) {
					//Re-check on the connection city date
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=connCity;
					this.valueSeven = departureDays;
					this.valueEight=connFlt;
					this.valueNine=connCity;
					this.valueTen=des;									
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Itinerary Changes")) {
					//Re-check on the connection city date
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=connCity;
					this.valueSeven = departureDays;
					this.valueEight=connFlt;
					this.valueNine=connCity;
					this.valueTen=des;									
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Divide PNR")) {
					//Re-check on the connection city date
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Reduce PNR")) {
					//Re-check on the connection city date
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Other Changes")) {
					//Re-check on the connection city date
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=connCity;
					this.valueSeven = departureDays;
					this.valueEight=connFlt;
					this.valueNine=connCity;
					this.valueTen=des;									
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display the passenger list (All) option")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=fltNo;
					this.valueFour=org;
					this.valueFive="";
					//this.valueFive=connCity;
					this.valueSix=departureDays;
					//this.valueSeven = departureDays;
					//this.valueEight=connFlt;
					//this.valueNine=connCity;
					//this.valueTen=des;									
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Multiple passenger list request")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=fltNo;
					this.valueFour=org;
					this.valueFive="";
					//this.valueFive=connCity;
					this.valueSix=departureDays;
					//this.valueSeven = departureDays;
					//this.valueEight=connFlt;
					//this.valueNine=connCity;
					//this.valueTen=des;									
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Create Booking")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=fltNo;
					this.valueFour=org;
					this.valueFive=connCity;
					this.valueSix=departureDays;
					this.valueSeven = connFlt;
					this.valueEight=connCity;
					this.valueNine=des;
					this.valueTen=departureDays;									
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display the passenger list (Inbound connection) option")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=connFlt;
					this.valueFour=connCity;
					this.valueFive="";
					//this.valueFive=connCity;
					this.valueSix=departureDays;
					//this.valueSeven = departureDays;
					//this.valueEight=connFlt;
					//this.valueNine=connCity;
					//this.valueTen=des;									
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display loyalty account")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=this.getLoyalty(scenario);
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display Partner Airline Elite member loyalty account")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=this.getLoyalty(scenario);
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Error on displayLoyaltyAccount - Invalid loyalty account")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=this.getLoyalty(scenario);
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Assign aircraft")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;													
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Assign aircraft with generic and specific seat reaccommodation")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;	
					this.valueSix=shipNumber;					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Start checkin")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Block more than one seat")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;	
					this.valueSix="";
					this.valueSeven = this.getSeatNumber(fltNo, org, des, jrnyDt, cos);
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Error on block seat - Invalid seat number")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Unblock more than one seat")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;	
					this.valueSix="";
					this.valueSeven = this.getSeatNumber(fltNo, org, des, jrnyDt, cos);
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Define inoperative seat")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;	
					this.valueSix="";
					this.valueSeven = this.getSeatNumber(fltNo, org, des, jrnyDt, cos);
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Clear inoperative seat")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;	
					this.valueSix="";
					this.valueSeven = this.getSeatNumber(fltNo, org, des, jrnyDt, cos);
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Cancel misconnect")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;	
					this.valueSix="";
					this.valueSeven = "";
					this.valueEight=connFlt;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Reinstate connecting passengers")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;	
					this.valueSix="";
					this.valueSeven = "";
					this.valueEight=connFlt;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Restrict checkin")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;													
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Close flight")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="0";
					this.valueFour="";
					this.valueFive="";													
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display board point messages")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;													
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Add/Update/Delete board point messages")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;													
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Restrict boarding")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;													
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Remove boarding restriction")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;													
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Create booking_Assign aircraft with generic and specific seat reaccommodation")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive="";
					this.valueSix="";		
					this.valueSeven = "";
					this.valueEight= "";
					this.valueNine=org;
					this.valueTen=connCity;	
					this.valueEleven=departureDays;
					this.valueTweleve=connFlt;
					this.valueThirteen=connCity;					
					this.valueFourTeen = des;												
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23
				else if(scenario.equalsIgnoreCase("Display a host airline booking(any create booking)")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=connCity;		
					this.valueSeven = departureDays;
					this.valueEight= connFlt;
					this.valueNine=connCity;
					this.valueTen=des;	
					this.valueEleven="NA";
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23
				else if(scenario.equalsIgnoreCase("Display booking by both record locator and Eticket number(any create booking)")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;		
					this.valueSeven = "NA";
					this.valueEight= "";
					this.valueNine="";
					this.valueTen="";	
					this.valueEleven="0";						
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23
				else if(scenario.equalsIgnoreCase("Display a booking from a specific address (LNIATA) without recloc given(any create booking) (no to confirm)")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;		
					this.valueSeven = "NA";
					this.valueEight= "";
					this.valueNine="";
					this.valueTen="";	
					this.valueEleven="NA";
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23
				else if(scenario.equalsIgnoreCase("Display confirmed booking list")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;		
					this.valueSeven = "NA";
					this.valueEight= "";
					this.valueNine="";
					this.valueTen="";	
					this.valueEleven="NA";
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23
				else if(scenario.equalsIgnoreCase("Display confirmed and waitlist booking list")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;		
					this.valueSeven = "NA";
					this.valueEight= "";
					this.valueNine="";
					this.valueTen="";	
					this.valueEleven="NA";
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23 - FQTV implementation
				else if(scenario.equalsIgnoreCase("Search a booking by frequent traveler number")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;		
					this.valueSeven = "NA";
					this.valueEight= "";
					this.valueNine="";
					this.valueTen="";	
					this.valueEleven="NA";
					this.valueTweleve="";
					this.valueThirteen="";					
					this.valueFourTeen = "";							
					this.valueFifteen="";
					this.valueSixteen="";
					this.valueSeventeen="";					
					this.valueEighteen=this.getLoyalty(scenario);		

					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23
				else if(scenario.equalsIgnoreCase("Display confirmed booking by 2nd flight in booking")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=connCity;		
					this.valueSeven = departureDays;
					this.valueEight= connFlt;
					this.valueNine=connCity;
					this.valueTen=des;		
					this.valueEleven="NA";
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23
				else if(scenario.equalsIgnoreCase("Display cancelled booking")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;		
					this.valueSeven = "NA";
					this.valueEight= "";
					this.valueNine="";
					this.valueTen="";	
					this.valueEleven="NA";
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23
				else if(scenario.equalsIgnoreCase("Credit Card search (multiple results)(after create booking - issue)")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;		
					this.valueSeven = "NA";
					this.valueEight= "";
					this.valueNine="";
					this.valueTen="";	
					this.valueEleven="NA";
					this.valueTweleve="";
					this.valueThirteen="";					
					this.valueFourTeen = "";							
					this.valueFifteen="";
					this.valueSixteen="";
					this.valueSeventeen="";					
					this.valueEighteen="";			
					this.valueNineteen = "";						
					this.valueTwenty = this.getCrediCard("VI");
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23
				else if(scenario.equalsIgnoreCase("Credit Card search (partial- CC number - single result)(after create booking - issue)")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;		
					this.valueSeven = "NA";
					this.valueEight= "";
					this.valueNine="";
					this.valueTen="";	
					this.valueEleven="NA";
					this.valueTweleve="";
					this.valueThirteen="";					
					this.valueFourTeen = "";							
					this.valueFifteen="";
					this.valueSixteen="";
					this.valueSeventeen="";					
					this.valueEighteen="";			
					this.valueNineteen = "";						
					this.valueTwenty = this.getCrediCard("MC");
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23 - FQTV to be implmented
				else if(scenario.equalsIgnoreCase("FQTV search (multiple results)")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;		
					this.valueSeven = "NA";
					this.valueEight= "";
					this.valueNine="";
					this.valueTen="";	
					this.valueEleven="NA";
					this.valueTweleve="";
					this.valueThirteen="";					
					this.valueFourTeen = "";							
					this.valueFifteen="";
					this.valueSixteen="";
					this.valueSeventeen="";					
					this.valueEighteen=this.getLoyalty(scenario);	
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23
				else if(scenario.equalsIgnoreCase("Date range search (single result)")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;		
					this.valueSeven = "NA";
					this.valueEight= "";
					this.valueNine="";
					this.valueTen="";	
					this.valueEleven="NA";
					this.valueTweleve="";
					this.valueThirteen="";					
					this.valueFourTeen = "";							
					this.valueFifteen="";
					this.valueSixteen="";
					this.valueSeventeen="";					
					this.valueEighteen="";			
					this.valueNineteen = "";						
					this.valueTwenty = this.getCrediCard("AX");
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23
				else if(scenario.equalsIgnoreCase("Search for a non-existent credit card (direct req)")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;		
					this.valueSeven = "NA";
					this.valueEight= "";
					this.valueNine="";
					this.valueTen="";	
					this.valueEleven="NA";
					this.valueTweleve="";
					this.valueThirteen="";					
					this.valueFourTeen = "";							
					this.valueFifteen="";
					this.valueSixteen="";
					this.valueSeventeen="";					
					this.valueEighteen="";			
					this.valueNineteen = "";						
					this.valueTwenty = this.getCrediCard("VI");
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23
				else if(scenario.equalsIgnoreCase("Flight data search - given name - surname (create booking - issue)")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;		
					this.valueSeven = "NA";
					this.valueEight= "";
					this.valueNine="";
					this.valueTen="";	
					this.valueEleven="NA";
					this.valueTweleve="";
					this.valueThirteen="";					
					this.valueFourTeen = "";							
					this.valueFifteen="";
					this.valueSixteen="";
					this.valueSeventeen="";					
					this.valueEighteen="";			
					this.valueNineteen = "";						
					this.valueTwenty = this.getCrediCard("MC");
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23
				else if(scenario.equalsIgnoreCase("Display active fare quote and fare quote history")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;		
					this.valueSeven = "NA";
					this.valueEight= "";
					this.valueNine="";
					this.valueTen="";	
					this.valueEleven="NA";
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23
				else if(scenario.equalsIgnoreCase("No fare quote history in booking")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;		
					this.valueSeven = "NA";
					this.valueEight= "";
					this.valueNine="";
					this.valueTen="";	
					this.valueEleven="NA";
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23
				else if(scenario.equalsIgnoreCase("Display booking history with canceling the booking")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=connCity;		
					this.valueSeven = departureDays;
					this.valueEight= connFlt;
					this.valueNine=connCity;
					this.valueTen=des;	
					this.valueEleven="NA";
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23
				else if(scenario.equalsIgnoreCase("Display booking history with adding_deleting remarks, SSR, OSI and phone")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;		
					this.valueSeven = "NA";
					this.valueEight= "";
					this.valueNine="";
					this.valueTen="";	
					this.valueEleven="NA";
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23
				else if(scenario.equalsIgnoreCase("Display booking history for CM flight")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;		
					this.valueSeven = "NA";
					this.valueEight= "";
					this.valueNine="";
					this.valueTen="";	
					this.valueEleven="NA";
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23
				else if(scenario.equalsIgnoreCase("Advance Seat Assignment on OA flight")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;		
					this.valueSeven = "NA";
					this.valueEight= "";
					this.valueNine="";
					this.valueTen="";	
					this.valueEleven="NA";
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23
				else if(scenario.equalsIgnoreCase("Advance Seat Assignment on a flight with multiple legs")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;		
					this.valueSeven = "NA";
					this.valueEight= "";
					this.valueNine="";
					this.valueTen="";	
					this.valueEleven="NA";
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23 - Manage sessions
				else if(scenario.equalsIgnoreCase("Modify_Booking")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=departureDays;
					this.valueFive="";
					this.valueSix="";		
					this.valueSeven = "";
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23 - Manage sessions
				else if(scenario.equalsIgnoreCase("Create_a_booking_for_a_group")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=departureDays;
					this.valueFive="";
					this.valueSix="";		
					this.valueSeven = "CM";
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23 - Manage sessions
				else if(scenario.equalsIgnoreCase("Create_a_booking_for_two_segments")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=departureDays;
					this.valueFive="";
					this.valueSix="";		
					this.valueSeven = "CM";
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23 - DisplayTicketService
				else if(scenario.equalsIgnoreCase("Search_for_a_non_existent_PNR")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="NA";
					this.valueFive="NA";
					this.valueSix="";		
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23 - DisplayTicketService
				else if(scenario.equalsIgnoreCase("Search_for_a_PNR_with_no_ETKT")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="NA";
					this.valueFive="NA";
					this.valueSix="";		
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23 - DisplayTicketService
				else if(scenario.equalsIgnoreCase("Retrieve_E_Ticket_History_information_for_a_given_PNR")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="NA";
					this.valueFive="NA";
					this.valueSix="";		
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23 - DisplayTicketService
				else if(scenario.equalsIgnoreCase("Booking_multiple_tickets")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="";
					this.valueFour="2";
					this.valueFive="2";
					this.valueSix="2";		
					this.insertTestDataRecord();
				}//New Scenarios - 12-Jun-23 - DisplayTicketService
				else if(scenario.equalsIgnoreCase("Bulk_Ticket")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="";
					this.valueFour="1";
					this.valueFive="1";
					this.valueSix="1";		
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Flifo_for_one_flight_specifying_departure_arrival_city_actual_times")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="1";
					this.valueFour="CM";
					this.valueFive="1";
					this.valueSix="NA";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Flifo_for_Two_flights")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="1";
					this.valueFour="CM";
					this.valueFive=" ";
					this.valueSix="1";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Flifo_for_codeshare_flight")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="1";
					this.valueFour="CM";
					this.valueFive=" ";
					this.valueSix="1";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display_queue_booking_all_items_full_data_format_not_remove_from_queue")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="NA";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display_queue_booking_from_empty_queue")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="NA";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Queue_booking")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="NA";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display_message")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="NA";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display_And_Remove_Message_Default_City")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="NA";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display_and_remove_message")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="NA";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Queue_Multiple_Messages")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="NA";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Transfer_Queue_Today_to_End_Date")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="30";
					this.valueFive=this.getCalculatedData();
					this.valueSix="NA";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Abort_schedule_change_queue_transfer")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="NA";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Sort_Queue")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="NA";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display_Booking_queue_count_for_a_given_city")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="NA";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display_queue_count_for_Booking_Message_Queues")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="NA";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Queue_Passenger_List")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="1";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Queue_a_specific_passenger_list")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="1";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("One request with vendor preferences")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=org;
					this.valueFive=des;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("One request")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=org;
					this.valueFive=des;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Request with departure time that will return date change flights")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=org;
					this.valueFive=des;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Ticket a booking with one flight, one passenger with credit card form of payment")) {
					fltDtls = this.getSevenHAFltDetails(1, "Y");
					Iterator<HashMap<String, String>> iter;
					if(fltDtls!=null && !fltDtls.isEmpty()) {
						iter = fltDtls.iterator();
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueOne = sheetName;
							this.valueTwo=scenario;
							this.valueThree=fltMap.get("dtCnt");
							this.valueFour=fltMap.get("fltNo");
							this.valueFive=fltMap.get("origin");					
							this.valueSix = fltMap.get("destination");
							this.valueSeven="0";
						}					
						this.insertTestDataRecord();
					}	
				}
				else if(scenario.equalsIgnoreCase("Ticket one passenger, 6 flights in booking with credit card form of payment (conjunction tickeets)")) {
					fltDtls = this.getSevenHAFltDetails(1, "Y");
					Iterator<HashMap<String, String>> iter;
					if(fltDtls!=null && !fltDtls.isEmpty()) {
						iter = fltDtls.iterator();
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueOne = sheetName;
							this.valueTwo=scenario;
							this.valueThree=fltMap.get("dtCnt");
							this.valueFour=fltMap.get("fltNo");
							this.valueFive=fltMap.get("origin");					
							this.valueSix = fltMap.get("destination");
							this.valueSeven="0";
						}					
						if (iter.hasNext()) {
							fltMap = iter.next();	
							this.valueEight=fltMap.get("fltNo");
							this.valueNine=fltMap.get("origin");					
							this.valueTen = fltMap.get("destination");	
							this.valueEleven=fltMap.get("dtCnt");
						}
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueNineteen=fltMap.get("fltNo");
							this.valueTwenty=fltMap.get("origin");
							this.valueTwentyone=fltMap.get("destination");					
							this.valueTwentytwo = fltMap.get("dtCnt");		
							this.valueTwentythree=fltMap.get("dtCnt");
						}
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueTwentyfour=fltMap.get("fltNo");
							this.valueTwentyfive=fltMap.get("origin");
							this.valueTwentysix=fltMap.get("destination");					
							this.valueTwentyseven = fltMap.get("dtCnt");							
						}
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueTwentyeight=fltMap.get("fltNo");
							this.valueTwentynine=fltMap.get("origin");
							this.valueThirty=fltMap.get("destination");					
							this.valueThirtyone = fltMap.get("dtCnt");							
						}
						this.insertTestDataRecord();
					}
				}
				else if(scenario.equalsIgnoreCase("Ticket with check form of payment")) {
					// Need to check
				}
				else if(scenario.equalsIgnoreCase("Reissue - add collect with credit card")) {
					fltDtls = this.getSevenHAFltDetails(1, "Y");
					Iterator<HashMap<String, String>> iter;
					if(fltDtls!=null && !fltDtls.isEmpty()) {
						iter = fltDtls.iterator();
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueOne = sheetName;
							this.valueTwo=scenario;
							this.valueThree=fltMap.get("dtCnt");
							this.valueFour=fltMap.get("fltNo");
							this.valueFive=fltMap.get("origin");					
							this.valueSix = fltMap.get("destination");
							this.valueSeven="0";
						}					
						if (iter.hasNext()) {
							fltMap = iter.next();	
							this.valueEight=fltMap.get("fltNo");
							this.valueNine=fltMap.get("origin");					
							this.valueTen = fltMap.get("destination");	
							this.valueEleven=fltMap.get("dtCnt");
						}
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueNineteen=fltMap.get("fltNo");
							this.valueTwenty=fltMap.get("origin");
							this.valueTwentyone=fltMap.get("destination");					
							this.valueTwentytwo = fltMap.get("dtCnt");		
							this.valueTwentythree=fltMap.get("dtCnt");
						}
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueTwentyfour=fltMap.get("fltNo");
							this.valueTwentyfive=fltMap.get("origin");
							this.valueTwentysix=fltMap.get("destination");					
							this.valueTwentyseven = fltMap.get("dtCnt");							
						}
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueTwentyeight=fltMap.get("fltNo");
							this.valueTwentynine=fltMap.get("origin");
							this.valueThirty=fltMap.get("destination");					
							this.valueThirtyone = fltMap.get("dtCnt");							
						}
						this.insertTestDataRecord();
					}
				}else if(scenario.equalsIgnoreCase("Issue bulk ticket for a PNR with two pax")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;
					this.valueSeven="0";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Issue inclusive tour ticket for a PNR with two pax")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;
					this.valueSeven="0";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Reissue ticket by fare quote number")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;
					this.valueSeven="0";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Issue ticket for a booking with an infant")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;
					this.valueSeven="0";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Send entry")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="W*";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Error ScreenText - Not allowed entry")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="A "+org+des;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Decode city code LAX")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="LAX";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Decode airline (CO)")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CO";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Encode airline (United)")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="United";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Request mutliple mixed messages of city, airport, airline, country and flight")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="";
					this.valueFour="US";
					this.valueFive="Los Angel";
					this.valueSix="BOSNIA";
					this.valueSeven = "United";
					this.valueEight="PA";
					this.valueNine="PTY";
					this.valueTen="NYC";									
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Decode city code LAX specifying an agent sine and airport code for sign in")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="LAX";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Flight has two legs and NotOpen status")) {
					this.setMultiLegNonOpenFlt(sheetName, scenario);					
				}else if(scenario.equalsIgnoreCase("Flight has one leg and OPEN status")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=fltNo;
					this.valueFour=org;
					this.valueFive="IATA";
					this.valueSix=departureDays;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("The request has an invalid flight number")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="25021";
					this.valueFour=org;
					this.valueFive="IATA";
					this.valueSix=departureDays;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Approval for Visa credit card")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="150";
					this.valueFour="USD";
					this.valueFive="VI";
					this.valueSix="4005554444444403";
					this.valueSeven="1029";
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display a single 737 aircraft on a one legged flight. Map contains 2 compartments")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Association: EMD coupon 1 with ETKT coupon 1 (POS info)")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;
					this.valueSeven="0";
					//this.valueSeven=String.valueOf(Integer.parseInt(departureDays)-1);
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Association: multiple coupons for primary and conjunctive ETKT")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=connCity;
					this.valueSeven=String.valueOf(Integer.parseInt(departureDays)-1);					
					this.valueEight=connFlt;
					this.valueNine=connCity;
					this.valueTen=des;	
					this.valueEleven=departureDays;//
					this.valueTweleve=scenarioTestData.get("cnnTwoFlt");
					this.valueThirteen=scenarioTestData.get("cnnTwoOrg");					
					this.valueFourTeen = scenarioTestData.get("cnnTwoConnCty");							
					this.valueFifteen=scenarioTestData.get("cnnTwoDtCnt");
					this.valueSixteen=scenarioTestData.get("cnnTwoConnFlt");
					this.valueSeventeen=scenarioTestData.get("cnnTwoConnCty");					
					this.valueEighteen=scenarioTestData.get("cnnTwoDes");			
					this.valueNineteen = scenarioTestData.get("cnnTwoDtCnt");						
					this.valueTwenty = scenarioTestData.get("cnnThrFlt");	
					this.valueTwentyone = scenarioTestData.get("cnnThrOrg");	
					this.valueTwentytwo = scenarioTestData.get("cnnThrConnCty");	
					this.valueTwentythree = scenarioTestData.get("cnnThrDtCnt");	
					this.valueTwentyfour = scenarioTestData.get("cnnThrConnFlt");	
					this.valueTwentyfive = scenarioTestData.get("cnnThrConnCty");	
					this.valueTwentysix = scenarioTestData.get("cnnThrDes");	
					this.valueTwentyseven = scenarioTestData.get("cnnThrDtCnt");
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Disassociation: EMD coupon 1 with ETKT coupon 1")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;
					this.valueSeven="0";
					//this.valueSeven=String.valueOf(Integer.parseInt(departureDays)-1);
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Void a Ticket")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;
					this.valueSeven=String.valueOf(Integer.parseInt(departureDays)-1);
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Refund Multiple tickets")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;
					this.valueSeven=String.valueOf(Integer.parseInt(departureDays)-1);
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Print an Eticket")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=connCity;
					this.valueSeven=String.valueOf(Integer.parseInt(departureDays)-1);
					this.valueEight=connFlt;
					this.valueNine=connCity;
					this.valueTen=des;	
					this.valueEleven=departureDays;
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Refund Error - cancell all segments prior refund")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;
					this.valueSeven=String.valueOf(Integer.parseInt(departureDays)-1);
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Void Error - no valid coupons to void (ticket already voided)")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=des;
					this.valueSeven="0";
					this.insertTestDataRecord();					
				}else if(scenario.equalsIgnoreCase("Get control of one coupon of one ticket")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=connCity;
					this.valueSeven=conDtCnt;
					this.valueEight=connFlt;
					this.valueNine=connCity;
					this.valueTen=des;	
					//this.setOAConnTstData("CM", "UA");						
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Push control of multiple coupons within one ticket")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree=departureDays;
					this.valueFour=fltNo;
					this.valueFive=org;
					this.valueSix=connCity;
					this.valueSeven=conDtCnt;
					this.valueEight=connFlt;
					this.valueNine=connCity;
					this.valueTen=des;	
					//this.setOAConnTstData("CM", "UA");						
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display Category List")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display Data Page")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="";
					this.valueFour="A08";
					this.valueFive="B01";
					this.valueSix="C01";					
					this.insertTestDataRecord();
				}
				else if(scenario.equalsIgnoreCase("Request_Visa_Info_one_destination_transit_visited_point")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="";					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Visa_singlepoint_normal_request")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="";					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Visa_singlepoint_seaman_request")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="";					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Both_DEorTR_missing_request")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="";					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Health_DEorTR_missing_request")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="";					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Visa_DEorTR_missing_request")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="";					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Both_multipoint_gov_request")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="";					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Both_multipoint_normal_request")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="";					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Both_multipoint_seaman_request")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="";					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display_Country_List_in_a_Group")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="";					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display_Rules_with_Rule_Index")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="";					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display_the_List_of_News_Items")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="";					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display_City_list_by_Country_code")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="";					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display_for_section_Passport_with_its_subsection")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="";					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display_for_section_Currency_with_its_subsection")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="";					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Display_all_sections_for_a_country")) {
					this.valueOne = sheetName;
					this.valueTwo=scenario;
					this.valueThree="CM";
					this.valueFour="";
					this.valueFive="";
					this.valueSix="";					
					this.insertTestDataRecord();
				}else if(scenario.equalsIgnoreCase("Adjust Name")) {
					fltDtls = this.getSevenHAFltDetails(2, "Y");
					Iterator<HashMap<String, String>> iter;
					if(fltDtls!=null && !fltDtls.isEmpty()) {
						iter = fltDtls.iterator();
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueOne = sheetName;
							this.valueTwo=scenario;
							this.valueThree=fltMap.get("dtCnt");
							this.valueFour=fltMap.get("fltNo");
							this.valueFive=fltMap.get("origin");					
							this.valueSix = fltMap.get("destination");							
						}					
						if (iter.hasNext()) {
							fltMap = iter.next();							
							this.valueSeven=fltMap.get("dtCnt");
							this.valueEight=fltMap.get("fltNo");
							this.valueNine=fltMap.get("origin");					
							this.valueTen = fltMap.get("destination");							
						}
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueEleven=fltMap.get("dtCnt");
							this.valueTweleve=fltMap.get("dtCnt");
							this.valueThirteen=fltMap.get("fltNo");
							this.valueFourTeen=fltMap.get("origin");					
							this.valueFifteen = fltMap.get("destination");							
						}
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueSixteen=fltMap.get("dtCnt");
							this.valueSeventeen=fltMap.get("dtCnt");
							this.valueEighteen=fltMap.get("fltNo");
							this.valueNineteen=fltMap.get("origin");					
							this.valueTwenty = fltMap.get("destination");							
						}
						this.valueTwentyone="0";											
						this.insertTestDataRecord();
					}	
				}else if(scenario.equalsIgnoreCase("Adjust Class of service")) {
					fltDtls = this.getSevenHAFltDetails(2, "Y");
					Iterator<HashMap<String, String>> iter;
					if(fltDtls!=null && !fltDtls.isEmpty()) {
						iter = fltDtls.iterator();
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueOne = sheetName;
							this.valueTwo=scenario;
							this.valueThree=fltMap.get("dtCnt");
							this.valueFour=fltMap.get("fltNo");
							this.valueFive=fltMap.get("origin");					
							this.valueSix = fltMap.get("destination");							
						}					
						if (iter.hasNext()) {
							fltMap = iter.next();							
							this.valueSeven=fltMap.get("dtCnt");
							this.valueEight=fltMap.get("fltNo");
							this.valueNine=fltMap.get("origin");					
							this.valueTen = fltMap.get("destination");							
						}
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueEleven=fltMap.get("dtCnt");
							this.valueTweleve=fltMap.get("dtCnt");
							this.valueThirteen=fltMap.get("fltNo");
							this.valueFourTeen=fltMap.get("origin");					
							this.valueFifteen = fltMap.get("destination");							
						}
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueSixteen=fltMap.get("dtCnt");
							this.valueSeventeen=fltMap.get("dtCnt");
							this.valueEighteen=fltMap.get("fltNo");
							this.valueNineteen=fltMap.get("origin");					
							this.valueTwenty = fltMap.get("destination");							
						}
						this.valueTwentyone="0";											
						this.insertTestDataRecord();
					}	
				}else if(scenario.equalsIgnoreCase("Adjust flight number and flight date")) {
					fltDtls = this.getSevenHAFltDetails(2, "Y");
					Iterator<HashMap<String, String>> iter;
					if(fltDtls!=null && !fltDtls.isEmpty()) {
						iter = fltDtls.iterator();
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueOne = sheetName;
							this.valueTwo=scenario;
							this.valueThree=fltMap.get("dtCnt");
							this.valueFour=fltMap.get("fltNo");
							this.valueFive=fltMap.get("origin");					
							this.valueSix = fltMap.get("destination");							
						}					
						if (iter.hasNext()) {
							fltMap = iter.next();							
							this.valueSeven=fltMap.get("dtCnt");
							this.valueEight=fltMap.get("fltNo");
							this.valueNine=fltMap.get("origin");					
							this.valueTen = fltMap.get("destination");							
						}
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueEleven=fltMap.get("dtCnt");
							this.valueTweleve=fltMap.get("dtCnt");
							this.valueThirteen=fltMap.get("fltNo");
							this.valueFourTeen=fltMap.get("origin");					
							this.valueFifteen = fltMap.get("destination");							
						}
						if (iter.hasNext()) {
							fltMap = iter.next();
							this.valueSixteen=fltMap.get("dtCnt");
							this.valueSeventeen=fltMap.get("dtCnt");
							this.valueEighteen=fltMap.get("fltNo");
							this.valueNineteen=fltMap.get("origin");					
							this.valueTwenty = fltMap.get("destination");							
						}
						this.valueTwentyone="0";											
						this.insertTestDataRecord();
					}	
				}				
			}
		}catch(Exception e) {
			
		}
		
	}
	
	public void truncateTestDataRecordTable() {
		String sqlQry;
		boolean getResult;
		try {
			stmt = connection.createStatement();
			sqlQry = "Delete from AutoTestDataReqTbl;";
			stmt.executeUpdate(sqlQry);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void insertTestDataRecord() {
		String sqlQry;int recID;
		try {
			stmt = connection.createStatement();
			recID = this.getTDRecordCnt("AutoTestDataReqTbl")+1;			
			sqlQry = "insert into AutoTestDataReqTbl (ID, SheetName, HeaderOne,HeaderTwo,HeaderThree,HeaderFour,HeaderFive, HeaderSix, "
					+ "HeaderSeven, HeaderEight, HeaderNine,HeaderTen, HeaderEleven, HeaderTwelve, HeaderThirteen,HeaderFourteen,HeaderFifteen,"
					+ "HeaderSixteen,	HeaderSeventeen, HeaderEighteen, HeaderNineteen, HeaderTwenty, HeaderTwentyOne, HeaderTwentyTwo, "
					+ "HeaderTwentyThree, HeaderTwentyFour,	HeaderTwentyFive, HeaderTwentySix, HeaderTwentySeven,"
					+ "HeaderTwentyEight, HeaderTwentyNine, HeaderThirty, HeaderThirtyOne, HeaderThirtyTwo, HeaderThirtyThree,	HeaderThirtyFour) values"
					+ "("+recID+",'"+this.valueOne+"','"+this.valueTwo+"','"+this.valueThree+"','"+this.valueFour+"','"+this.valueFive+"',"
					+ "'"+this.valueSix+"','"+this.valueSeven+"','"+this.valueEight+"','"+this.valueNine+"','"+this.valueTen+"','"+this.valueEleven+"',"
					+ "'"+this.valueTweleve+"','"+this.valueThirteen+"','"+this.valueFourTeen+"','"+this.valueFifteen+"','"+this.valueSixteen+"',"
					+ "'"+this.valueSeventeen+"','"+this.valueEighteen+"','"+this.valueNineteen+"','"+this.valueTwenty+"','"+this.valueTwentyone+"',"
					+ "'"+this.valueTwentytwo+"','"+this.valueTwentythree+"','"+this.valueTwentyfour+"','"+this.valueTwentyfive+"','"+this.valueTwentysix+"',"
					+ "'"+this.valueTwentyseven+"','"+this.valueTwentyeight+"','"+this.valueTwentynine+"','"+this.valueThirty+"','"+this.valueThirtyone+"',"
					+ "'"+this.valueThirtytwo+"','"+this.valueThirtythree+"','"+this.valueThirtyFour+"','"+this.valueThirtyFive+"');";
			stmt.executeUpdate(sqlQry);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public int getTDRecordCnt(String tblName) {
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
	public HashMap<String, HashMap<String, String>> getTestDataRecords() {
		HashMap<String, HashMap<String, String>> scenarioTestData = null;
		HashMap<String, String> indvScenarioTD;
		String sqlQry;int recID;
		try {
			scenarioTestData = new HashMap();
			stmt = connection.createStatement();
			recID = this.getTDRecordCnt("AutoTestDataReqTbl")+1;			
			sqlQry = "select * from AutoTestDataReqTbl;";
			resultSet = stmt.executeQuery(sqlQry);
			
			//(ID, SheetName, HeaderOne,HeaderTwo,HeaderThree,HeaderFour,HeaderFive, HeaderSix, "
			// "HeaderSeven, HeaderEight, HeaderNine,HeaderTen, HeaderEleven, HeaderTwelve, HeaderThirteen)"
			
			while(resultSet.next()) {
				indvScenarioTD = new HashMap();
				indvScenarioTD.put("ID", String.valueOf(resultSet.getInt("ID")));
				indvScenarioTD.put("sheetName", resultSet.getString("sheetName"));
				indvScenarioTD.put("scenario", resultSet.getString("HeaderOne"));
				indvScenarioTD.put("valueOne", resultSet.getString("HeaderTwo"));
				indvScenarioTD.put("valueTwo", resultSet.getString("HeaderThree"));
				indvScenarioTD.put("valueThree", resultSet.getString("HeaderFour"));
				indvScenarioTD.put("valueFour", resultSet.getString("HeaderFive"));
				indvScenarioTD.put("valueFive", resultSet.getString("HeaderSix"));
				indvScenarioTD.put("valueSix", resultSet.getString("HeaderSeven"));
				indvScenarioTD.put("valueSeven", resultSet.getString("HeaderEight"));
				indvScenarioTD.put("valueEight", resultSet.getString("HeaderNine"));
				indvScenarioTD.put("valueNine", resultSet.getString("HeaderTen"));
				indvScenarioTD.put("valueTen", resultSet.getString("HeaderEleven"));
				indvScenarioTD.put("valueEleven", resultSet.getString("HeaderTwelve"));
				indvScenarioTD.put("valueTwelve", resultSet.getString("HeaderThirteen"));	
				indvScenarioTD.put("valueThirteen", resultSet.getString("HeaderFourteen"));
				indvScenarioTD.put("valueFourTeen", resultSet.getString("HeaderFifteen"));
				indvScenarioTD.put("valueFifteen", resultSet.getString("HeaderSixteen"));
				indvScenarioTD.put("valueSixteen", resultSet.getString("HeaderSeventeen"));
				indvScenarioTD.put("valueSeventeen", resultSet.getString("HeaderEighteen"));
				indvScenarioTD.put("valueEighteen", resultSet.getString("HeaderNineteen"));
				indvScenarioTD.put("valueNineteen", resultSet.getString("HeaderTwenty"));
				indvScenarioTD.put("valueTwenty", resultSet.getString("HeaderTwentyOne"));
				indvScenarioTD.put("valueTwentyone", resultSet.getString("HeaderTwentyTwo"));
				indvScenarioTD.put("valueTwentytwo", resultSet.getString("HeaderTwentyThree"));
				indvScenarioTD.put("valueTwentythree", resultSet.getString("HeaderTwentyFour"));
				indvScenarioTD.put("valueTwentyfour", resultSet.getString("HeaderTwentyFive"));
				indvScenarioTD.put("valueTwentyfive", resultSet.getString("HeaderTwentySix"));
				indvScenarioTD.put("valueTwentysix", resultSet.getString("HeaderTwentySeven"));
				indvScenarioTD.put("valueTwentyseven", resultSet.getString("HeaderTwentyEight"));
				indvScenarioTD.put("valueTwentyeight", resultSet.getString("HeaderTwentyNine"));
				indvScenarioTD.put("valueTwentynine", resultSet.getString("HeaderThirty"));
				indvScenarioTD.put("valueThirty", resultSet.getString("HeaderThirtyOne"));
				indvScenarioTD.put("valueThirtyone", resultSet.getString("HeaderThirtyTwo"));
				indvScenarioTD.put("valueThirtytwo", resultSet.getString("HeaderThirtyThree"));
				indvScenarioTD.put("valueThirtythree", resultSet.getString("HeaderThirtyFour"));
				scenarioTestData.put(String.valueOf(resultSet.getInt("ID")), indvScenarioTD);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return scenarioTestData;
	}
	public String getSeatNumber(String fltNo, String org, String des, String jrnyDt, String cos) {
		String seatNo="";
		seats = flightSeatCollector.getAvailableSeats(fltNo, org, des, jrnyDt, cos);
		iter = seats.iterator();
		while(iter.hasNext()) {
			seatNo = iter.next();
			if(this.seatUtilized(Integer.parseInt(fltNo), jrnyDt, seatNo)) {
				
			}else {
				this.addSeats(Integer.parseInt(fltNo), jrnyDt, seatNo);
				break;
			}			
		}
		return seatNo;
	}
	public void addSeats(int fltNo, String jrnyDate, String seatNo) {
		String sqlQry;
		int idNum;
		try {
			stmt = connection.createStatement();
			idNum = this.getTDRecordCnt("TempTable")+1;
			sqlQry = "insert into TempTable (ID, FLTNO,JRNYDATE,SEATNO) values "
					+ "("+idNum+","+fltNo+",'"+jrnyDate+"','"+seatNo+"');";
			stmt.executeUpdate(sqlQry);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public boolean seatUtilized(int fltNo, String jrnyDate, String seatNo) {
		boolean isUsed=false;
		String sqlQry;
		try {
			stmt = connection.createStatement();
			sqlQry = "select * from TempTable where FLTNO="+fltNo+" and JRNYDATE='"+jrnyDate+"' and SEATNO='"+seatNo+"';";
			resultSet = stmt.executeQuery(sqlQry);
			if(resultSet.next()) {
				isUsed=true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return isUsed;
	}
	
	public HashMap<String, String> getPastFlightDetails(int seatsReq, String cos) {
		String sqlQry, dateNum;
		HashMap<String, String> fltDtls = new HashMap();
		int idNum;
		SimpleDateFormat sdfDT = new SimpleDateFormat("ddMMM");
		Calendar c;
		try {
			stmt = connection.createStatement();			
			//ID, FLTNO,ORG,DES,DOJ,DATECOUNT, COS, ST_AVL, CONCTY,CON_ST_AVL
			sqlQry = "select * from TempFlightData where ST_AVL>="+seatsReq+" and cos='"+cos.toUpperCase()+"'";
			
			resultSet = stmt.executeQuery(sqlQry);
			while(resultSet.next()) {
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
				dateNum = fltOpers.isValidPastFlight(String.valueOf(resultSet.getInt("FLTNO")), resultSet.getString("ORG"));
				if(dateNum!=null) {
					fltDtls.put("doj", resultSet.getString("DOJ"));
					fltDtls.put("dtCnt", dateNum);
					c = Calendar.getInstance();
					c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(dateNum));
					fltDtls.put("doj", sdfDT.format(c.getTime()));
					break;
				}
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return fltDtls;
	}
	public List<HashMap<String, String>> getSevenHAFltDetails(int seatsReq, String cos) {
		String sqlQry, dateNum;
		HashMap<String, String> indFltDtl = new HashMap();
		List<HashMap<String, String>> fltDtls = new ArrayList();
		int dtcount=0, i=0;
		List<String> fltNos;
		boolean addFlightDetails;
		SimpleDateFormat sdfDT = new SimpleDateFormat("ddMMM");
		Calendar c;
		try {
			stmt = connection.createStatement();			
			//ID, FLTNO,ORG,DES,DOJ,DATECOUNT, COS, ST_AVL, CONCTY,CON_ST_AVL
			sqlQry = "select * from TempFlightData where ST_AVL>="+seatsReq+" and cos='"+cos.toUpperCase()+"'";			
			resultSet = stmt.executeQuery(sqlQry);
			fltNos = new ArrayList();
			while(resultSet.next() && i<8) {	
				addFlightDetails = false;
				if(fltNos.isEmpty()) {
					fltNos.add(String.valueOf(resultSet.getInt("FLTNO")));
					addFlightDetails = true;
				}else if(!fltNos.contains(String.valueOf(resultSet.getInt("FLTNO")))) {
					fltNos.add(String.valueOf(resultSet.getInt("FLTNO")));
					addFlightDetails = true;
				}
				if(addFlightDetails) {
					indFltDtl = new HashMap();
					indFltDtl.put("fltNo", String.valueOf(resultSet.getInt("FLTNO")));
					indFltDtl.put("origin", resultSet.getString("ORG"));
					indFltDtl.put("destination", resultSet.getString("DES"));
					indFltDtl.put("doj", resultSet.getString("DOJ"));
					indFltDtl.put("dtCnt", String.valueOf(resultSet.getInt("DATECOUNT")));
					fltDtls.add(indFltDtl);	
					dtcount = resultSet.getInt("DATECOUNT")+1;
					i++;					
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return fltDtls;
	}
	public void setMultiLegNonOpenFlt(String sheetName, String scenario) {
		String sqlQry;
		
		try {
			stmt = connection.createStatement();			
			sqlQry = "select * from SpecificDataTempTable where multileg ='TRUE' and fltOpenSts='FALSE'";
			resultSet = stmt.executeQuery(sqlQry);
			if(resultSet.next()) {
				this.valueOne = sheetName;
				this.valueTwo=scenario;
				this.valueThree=String.valueOf(resultSet.getInt("FLTNO"));
				this.valueFour=resultSet.getString("ORGIN");
				this.valueFive="IATA";
				this.valueSix=String.valueOf(resultSet.getInt("DAYNO"));	
				this.insertTestDataRecord();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	private String getLoyalty(String scenario) {
		String sqlQry, loyaltyNum="";
		
		try {
			stmt = connection.createStatement();			
			sqlQry = "select LoyaltyNum from LoyalTable where Scenario ='"+scenario+"'";
			resultSet = stmt.executeQuery(sqlQry);
			if(resultSet.next()) {
				loyaltyNum=resultSet.getString("LoyaltyNum");				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return loyaltyNum;
	}
	private String getCrediCard(String cardType) {
		String sqlQry, cardNo="";
		
		try {
			stmt = connection.createStatement();			
			sqlQry = "select CardNumber from PaymentCards where CardCode ='"+cardType+"' and CardVariant='CreditCard'";
			resultSet = stmt.executeQuery(sqlQry);
			if(resultSet.next()) {
				cardNo=resultSet.getString("CardNumber");				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return cardNo;
	}
	private void setOAConnTstData(String originAirCode, String connAirCode) {
		String sqlQry, dateNum;
		HashMap<String, String> indFltDtl = new HashMap();
		List<HashMap<String, String>> fltDtls = new ArrayList();
		int dtcount=0, i=0;
		List<String> fltNos;
		boolean addFlightDetails;
		SimpleDateFormat sdfDT = new SimpleDateFormat("ddMMM");
		Calendar c;
		try {
			stmt = connection.createStatement();			
			//ID, FLTNO,ORG,DES,DOJ,DATECOUNT, COS, ST_AVL, CONCTY,CON_ST_AVL
			sqlQry = "select * from TempFlightData where ORGAIRCODE>='"+originAirCode.toUpperCase()+"' and CONNAIRCODE='"+connAirCode.toUpperCase()+"'";			
			resultSet = stmt.executeQuery(sqlQry);
			
			if(resultSet.next()) {	
				this.valueThree=String.valueOf(resultSet.getInt("DATECOUNT"));
				this.valueFour=String.valueOf(resultSet.getInt("FLTNO"));
				this.valueFive=resultSet.getString("ORG");
				this.valueSix=resultSet.getString("CONCTY");
				this.valueSeven = String.valueOf(resultSet.getInt("CONNDATECOUNT"));
				this.valueEight=String.valueOf(resultSet.getInt("CONFLT"));
				this.valueNine=resultSet.getString("CONCTY");
				this.valueTen=resultSet.getString("DES");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	private String getCalculatedData() {
		SimpleDateFormat sdfDT = new SimpleDateFormat("MM-dd-YYYY");
		String currentDate="";
		int day;
		Calendar cal = Calendar.getInstance();  
		cal.add(Calendar.DAY_OF_MONTH, 20);
		currentDate = sdfDT.format(cal.getTime());		
		return currentDate;
	}
	
}
