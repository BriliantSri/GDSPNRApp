package com.pages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;


public class TerminalEmulation {
	WebDriver driver;
	String pageTitle = "Terminal Emulation";
	TerminalEmulationPageObjects terminalEmulationPgObj;
	private String org = null;
	private String des = null;
	public TerminalEmulation(WebDriver driver){
		this.driver=driver;
		terminalEmulationPgObj = new TerminalEmulationPageObjects();
		PageFactory.initElements(driver, terminalEmulationPgObj);
	}
	
	public boolean isTerminalEmulationPageDisplayed(){
		boolean isTerminalEmulationPageDisplayed = false;
		if(terminalEmulationPgObj.pageTitle.isDisplayed() && 
				terminalEmulationPgObj.pageTitle.getText().toUpperCase().contains(pageTitle.toUpperCase())){
			isTerminalEmulationPageDisplayed = true;
		}
		
		return isTerminalEmulationPageDisplayed;
	}
	
	public void processEntry(String entry) throws Exception{
		//this.selectRequiredLinesInEntry(lineOption);
		terminalEmulationPgObj.entryTextFld.sendKeys(entry);
		terminalEmulationPgObj.btnSend.click();
	}
	
	public String getResponse() throws Exception{
		String responseString;
		responseString = terminalEmulationPgObj.outPutWrap.getText();
		return responseString;
	}
	
	public String runSingleLineEntry(String entry) throws Exception{
		String receivedResponse ="false";
		try {
			this.selectRequiredLinesInEntry("1");
			//this.initializePageObjects();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			terminalEmulationPgObj.entryTextFld.sendKeys(entry);
			terminalEmulationPgObj.btnSend.click();		
			receivedResponse = terminalEmulationPgObj.outPutWrap.getText();			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return receivedResponse;		
	}
	public String runMultiLineEntries(String entry) throws Exception{
		String receivedResponse ="false";
		StringSelection stringSelection = new StringSelection(entry);
		Toolkit.getDefaultToolkit().getSystemClipboard()
        .setContents(stringSelection, null);
		try {
			this.selectRequiredLinesInEntry("24");		
			//this.initializePageObjects();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			terminalEmulationPgObj.entryTextFld.click();		    
			terminalEmulationPgObj.entryTextFld.sendKeys(Keys.LEFT_CONTROL+"v");
			terminalEmulationPgObj.btnSend.click();
			Thread.sleep(2000);
			//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			receivedResponse = terminalEmulationPgObj.outPutWrap.getText();			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return receivedResponse;
	}
	public void generateAvailabilityList(String response) throws Exception{
		 FileOutputStream fos=new FileOutputStream("C:\\testout.txt");
		 byte b[]=response.getBytes();
         fos.write(b);  
         fos.close();    
	}
	
	
	public boolean checkAvailabilityPerClass(String line, String cos) throws Exception{
		int i = 1;
		int j;
		boolean isAvlExists = false;
		 while (i<10){
			 j=i;
			 
			 if (line.contains(cos.toUpperCase().concat(String.valueOf(j)))){
				 isAvlExists = true;
				 break;
			 }
			 i++;
		 }
		 return isAvlExists;
	}
	public void selectRequiredLinesInEntry(String lineOption){	
		this.initializePageObjects();
		//driver.navigate().refresh();
		//terminalEmulationPgObj.selectType.click();
		Iterator<WebElement> iter = terminalEmulationPgObj.selectTypeOptions.iterator();
		WebElement iterElement;
		String tempString;
		while (iter.hasNext()){
			iterElement = iter.next();
			try{	
				tempString = iterElement.getText();
				if(iterElement.getText().trim().equalsIgnoreCase(lineOption)){					
					iterElement.click();
					break;
				}
			
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	public void waitTillPageLoads(){
        // Javascript executor to return value
        JavascriptExecutor j = (JavascriptExecutor)driver;
        j.executeScript("return document.readyState")
                .toString().equals("complete");
    }
	
	public boolean staleElementResolve(WebElement element) {
		boolean elementFound = false;
		int i=1;
		while (!elementFound && i<6) {
			try {
				new Actions(driver)
	            .moveToElement(element)
	            .perform();
				
				new Actions(driver)
	            .click(element)
	            .perform();
				i++;
			}catch(Exception e) {
				staleElementResolve(element);
			}
		}
		return elementFound;
	}
	
	public void initializePageObjects(){
		PageFactory.initElements(this.driver, terminalEmulationPgObj);
	}
	
	public void logOffApplication(){
		terminalEmulationPgObj.btnLogOff.click();
	}
	
	
	class TerminalEmulationPageObjects{
				
		@FindBy(xpath = "//*[@id='site-title']")
        @CacheLookup
        private WebElement pageTitle;
		
		
		@FindBy(xpath="//*[@id='gs']/textarea")
        @CacheLookup
        private WebElement entryTextAreaFld;
		
		//@FindBy(xpath="//*[@id='gs']/text")
		@FindBy(xpath = "//*[@name='q']")
        @CacheLookup
        private WebElement entryTextFld;
		
		@FindBy(xpath = "//input[@value='Send']")
        @CacheLookup
        private WebElement btnSend;
		
		@FindBy(xpath = "//input[@value='Reset']")
        @CacheLookup
        private WebElement btnReset;
		
		//@FindBy(xpath = "//*[@id='Type']")
		@FindBy(xpath = "//select[@name='Type']")
        @CacheLookup
        private WebElement selectType;
		
		//@FindBy(xpath = "//*[@id='Type']/option")
		@FindBy(xpath = "//select[@name='Type']/option")
        @CacheLookup
        private List<WebElement> selectTypeOptions;
		
		@FindBy(xpath = "//input[@value='Privacy']")
        @CacheLookup
        private WebElement btnPrivacy;
		
		@FindBy(xpath = "//pre[@id='content-wrap']")
        @CacheLookup
        private WebElement outPutWrap;
		
		//LogOffIn
		@FindBy(xpath = "//a[@id='LogOffIn']")
        @CacheLookup
        private WebElement btnLogOff;

	}

}
