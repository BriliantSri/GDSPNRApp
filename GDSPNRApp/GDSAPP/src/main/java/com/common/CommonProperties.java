package com.common;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;

public class CommonProperties {
	public static String envURL;
	public static String logID;
	public static String passWord;
	public static boolean validUser;
	public static HashMap<String, List<String>> ssrTKNEMsgs;
	public static String gdsPNR;
	public static String hostPNR;
	public static HashMap<Integer, HashMap<String, String>> segmentMap;
	public static HashMap<Integer, HashMap<String, String>> passengerMap;
	public static String filePath = "C:\\GDS PNR Messages\\";
	public static String fileDirectory ="PNRMessages";
	public static String fileName ="";
	public static boolean fileNameSet;
	public static WebDriver driver;
	public static String pos;
	public static String currency;
	public static String lniata;
	public static Connection connection;
	// Below for Automation test data
	public static String autoTDFilePath = "C:\\Automation Test Data\\";
	public static String autoFileDirectory ="Automation_TestData";
	public static String autoScenarioString="";
}
