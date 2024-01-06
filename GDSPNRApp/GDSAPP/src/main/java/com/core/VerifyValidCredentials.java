package com.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.common.CommonProperties;
import com.core.LoginIShares;
import com.pages.HomePage;
import com.pages.TerminalEmulation;

public class VerifyValidCredentials {

	WebDriver driver;
	private String org = null;
	private String des = null;
	public VerifyValidCredentials() {
		
	}
	
	public boolean verifyValidLogins(String envURL, String usrID, String passWord) throws Exception {
		boolean isValidCredentials = false;
		String termResp,cityCd;
		LoginIShares loginIShares = new LoginIShares();
		driver = loginIShares.launchISHARESApplication(envURL, true);
		loginIShares.logintoIShares(usrID, passWord);		
		HomePage homePage = new HomePage(driver);
		if(homePage.verifyHomePagedisplayed()) {
			CommonProperties.envURL = envURL;
			CommonProperties.logID=usrID;
			CommonProperties.passWord=passWord;
			CommonProperties.validUser=true;
			//getting the POSDetails
			POSTransactions posTrans = new POSTransactions();
			homePage.selectTerminalEmulation();
			
			TerminalEmulation terminalEmulation = new TerminalEmulation(driver);
			terminalEmulation.waitTillPageLoads();
			if(terminalEmulation.isTerminalEmulationPageDisplayed()) {			
				terminalEmulation.runSingleLineEntry("LOGI CMRE");
				terminalEmulation.runSingleLineEntry("BSIB");
				terminalEmulation.runSingleLineEntry("RATS*");
				termResp = terminalEmulation.getResponse();
				cityCd = posTrans.getCityfromRespose(termResp);
				if(cityCd!=null && cityCd.length()>0) {
					CommonProperties.pos=cityCd;
					if (posTrans.getRatsRecord().length() > 10) {
						CommonProperties.lniata=posTrans.getRatsRecord().substring(0, 6);
					} else {
						CommonProperties.lniata="";
					}
				}else {
					CommonProperties.pos="";
				}
				
			}
			
			isValidCredentials = true;
			homePage.logOff();
		}else {
			CommonProperties.envURL = null;
			CommonProperties.logID=null;
			CommonProperties.passWord=null;
			CommonProperties.validUser=false;
		}
		driver.close();
		//Assert.assertTrue(homePage.verifyHomePagedisplayed(),"Home Page not displayed");
		//isValidCredentials = true;
		
		return isValidCredentials;
	}
	
}
