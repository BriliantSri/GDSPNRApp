package com.core;

import java.sql.Connection;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import org.openqa.selenium.WebDriver;

import com.common.CommonProperties;
import com.model.TableDataService;
import com.pages.HomePage;
import com.pages.TerminalEmulation;

public class POSTransactions {
	WebDriver driver;
	String ratsRecord;

	public String getRatsRecord() {
		return ratsRecord;
	}

	public void setRatsRecord(String ratsRecord) {
		this.ratsRecord = ratsRecord;
	}

	public Vector<String> getPOSAndCurrency(Connection conn) {
		Vector<String> posDetails = new Vector<String>();
		try {
			TableDataService tblService = new TableDataService(conn);
			posDetails = tblService.getCityCurrencies();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return posDetails;
	}

	public HashMap<String, String> getlniataPOS(Connection conn) {
		String termResp, cityCd;
		HashMap<String, String> lniataPOS = new HashMap<String, String>();
		try {
			LoginIShares loginIShares = new LoginIShares();
			driver = loginIShares.launchISHARESApplication(CommonProperties.envURL, true);
			loginIShares.logintoIShares(CommonProperties.logID, CommonProperties.passWord);
			HomePage homePage = new HomePage(driver);
			if (homePage.verifyHomePagedisplayed()) {
				homePage.selectTerminalEmulation();
				TerminalEmulation terminalEmulation = new TerminalEmulation(driver);
				terminalEmulation.runSingleLineEntry("LOGI CMRE");
				terminalEmulation.runSingleLineEntry("BSIB");
				terminalEmulation.runSingleLineEntry("RATS*");
				termResp = terminalEmulation.getResponse();
				cityCd = getCityfromRespose(termResp);
				lniataPOS.put("POS", cityCd);
				if (ratsRecord.length() > 10) {
					lniataPOS.put("lniata", ratsRecord.substring(0, 6));
				} else {
					lniataPOS.put("lniata", "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lniataPOS;

	}

	public String getCityfromRespose(String response) {
		String cityCd = "", tmpStr;
		int indx;
		StringTokenizer strTknzr, farTknzr;
		strTknzr = new StringTokenizer(response, "\n");

		if (response != null && response.length() > 20) {
			if (strTknzr.hasMoreTokens()) {
				tmpStr = strTknzr.nextToken();
				indx = tmpStr.indexOf("CTY");
				ratsRecord = strTknzr.nextToken();
				if (ratsRecord.length() > 0) {
					cityCd = ratsRecord.substring(indx, indx+3);
				}
			}
		}
		return cityCd;
	}
	
	
	public void closeDriver() {
		driver.close();
	}

}
