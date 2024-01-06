package com.utilities;

import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

import org.apache.tools.ant.types.resources.comparators.Date;
import org.openqa.selenium.WebDriver;

import com.common.CommonProperties;
import com.core.LoginIShares;
import com.pages.HomePage;
import com.pages.TerminalEmulation;

public class TicketGenerator {
	
	private long ticketNumber;
	WebDriver driver;
	private TerminalEmulation terminalEmulation;
	private HomePage homePage;
	
	public TicketGenerator(){
		logIntoISHARES();
	}
	
	private void logIntoISHARES() {
		try {
			LoginIShares loginIShares = new LoginIShares();
			driver = loginIShares.launchISHARESApplication(CommonProperties.envURL, true);
			loginIShares.logintoIShares(CommonProperties.logID, CommonProperties.passWord);
			homePage = new HomePage(driver);
			if(homePage.verifyHomePagedisplayed()) {
				homePage.selectTerminalEmulation();
				terminalEmulation = new TerminalEmulation(driver);
				terminalEmulation.runSingleLineEntry("BSIB");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public long getTicketNumber() {
		this.ticketNumber =  this.generateTicketNumber();
		while(this.verifyTicketNumberExists(this.ticketNumber)) {
			this.ticketNumber =  this.generateTicketNumber();
		}
		return this.ticketNumber;
	}

	public void setTicketNumber(long ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	
	public long generateTicketNumber() {
		Random random = new Random();
		int dte = Calendar.DAY_OF_MONTH;
		int mnth = Calendar.MONTH;
		int hr = Calendar.HOUR;
		int min = Calendar.MINUTE;
		int sec = Calendar.SECOND;
		long tickNumber=230;
		tickNumber= (tickNumber*100)+random.nextInt(99);
		tickNumber= (tickNumber*100)+dte;
		tickNumber= (tickNumber*100)+random.nextInt(99);
		tickNumber= (tickNumber*100)+min;
		tickNumber= (tickNumber*100)+sec;
		
		return tickNumber;
	}
	public boolean verifyTicketNumberExists(long tickNumber) {
		String response;
		boolean ticketExists = false;
		try {
			/*if(terminalEmulation.isTerminalEmulationPageDisplayed()) {
				
			}else {
				if(driver!=null) {
					driver.close();
				}
				logIntoISHARES();
			}*/
			terminalEmulation = new TerminalEmulation(driver);
			terminalEmulation.runSingleLineEntry("*ET-"+tickNumber);
			response = terminalEmulation.getResponse();
			if(response.toUpperCase().contains("NO MATCH FOUND")) {
				ticketExists = false;	
			}else if(response.trim().toUpperCase().startsWith("E-TKT LIST DISPLAY")){
				ticketExists = true;
			}else {
				//Since there might be issue in ticketFormat hence for generating new ticket
				ticketExists = true;
			}	//homePage.logOff();
			//NO MATCH FOUND
			//driver.close();	
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ticketExists;
	}
	public void closeISHARES() {
		if(driver!=null) {
			homePage.logOff();
			driver.close();
		}
	}

}
