package com.core;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.pages.HomePage;
import com.pages.TerminalEmulation;

public class ProcessGDSTTYMessage {
	WebDriver driver;
	private String envURL, userID, passWord;
	public String getEnvURL() {
		return envURL;
	}
	public void setEnvURL(String envURL) {
		this.envURL = envURL;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String processTTYMessageInSHARES(List<List> gdsTTYMessageList) throws IOException {
		Iterator<List> iter;
		Iterator<String> iterStr;
		String ttyFragment;
		String outPutMessage = null;
		try{
			LoginIShares loginIShares = new LoginIShares();
			driver = loginIShares.launchISHARESApplication(this.envURL, false);
			loginIShares.logintoIShares(this.userID, this.passWord);
			HomePage homePage = new HomePage(driver);
			if(homePage.verifyHomePagedisplayed()) {
				homePage.selectTerminalEmulation();
				TerminalEmulation terminalEmulation = new TerminalEmulation(driver);
				
				terminalEmulation.runSingleLineEntry("LOGI CMRE");
				terminalEmulation.runSingleLineEntry("BSIB");
				//terminalEmulation.selectRequiredLinesInEntry("24");
				if(gdsTTYMessageList.size()>0) {
					iter = gdsTTYMessageList.iterator();
					while(iter.hasNext()) {
						iterStr = (iter.next()).iterator();
						ttyFragment="";
						while(iterStr.hasNext()) {
							ttyFragment = ttyFragment+""+iterStr.next()+"\n";
						}
						outPutMessage = terminalEmulation.runMultiLineEntries(ttyFragment);
					}
					//driver.close();
				}
			}
		}catch(Exception e) {
			e.printStackTrace();		
		}
		return outPutMessage;
	}
	
	public String getTTYProcessedPNR(String gdsPNR) {
		String ttyProcessedPNRDtls = null;
		try {
			TerminalEmulation terminalEmulation = new TerminalEmulation(driver);
			terminalEmulation.runSingleLineEntry("LOGC CMRE");
			terminalEmulation.runSingleLineEntry("BSIB");
			//wait(2000);
			ttyProcessedPNRDtls = terminalEmulation.runSingleLineEntry("*ER-"+gdsPNR);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ttyProcessedPNRDtls;
	}
	
	public void loggOffIShares() {
		TerminalEmulation terminalEmulation = new TerminalEmulation(driver);
		terminalEmulation.logOffApplication();
	}
	public void closeDriver() {
		driver.close();
	}
}
