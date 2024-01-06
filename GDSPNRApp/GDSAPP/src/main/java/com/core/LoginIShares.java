package com.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;


import com.base.TestBase;
import com.pages.LoginPage;

public class LoginIShares extends TestBase{
	private WebDriver driver;	
	private Properties prop;	
	private String projectPath = System.getProperty("user.dir")	;	
	static final Logger logger = LogManager.getLogger(TestBase.class.getName());
	
	public LoginIShares(){
		
	}
	//@Test
	public WebDriver launchISHARESApplication(String urlString, boolean headless) throws IOException {
		
		TestBase testBase =  new TestBase();
		if(headless) {
			driver = testBase.initializeHeadlessDriver();
			//driver = testBase.initializeDriver();
		}else {
			driver = testBase.initializeDriver();
		}
		if (urlString!=null) {
			driver.get(urlString);		
			driver.manage().window().maximize();
			
		}
		return driver;		
		
	}
	public void logintoIShares(String uID, String passwrd) throws IOException{
		LoginPage loginPage;	
		if (uID!=null && uID.length()>0 && passwrd!=null && passwrd.length()>0){
			loginPage = new LoginPage(driver);
			loginPage.submitLogin(uID,passwrd);
		}
	}
}

