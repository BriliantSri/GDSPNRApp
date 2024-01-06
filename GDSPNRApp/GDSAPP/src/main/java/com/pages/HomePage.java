package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage {
	WebDriver driver;
	HomePageObjects homePageObjects;
	String menuHeader = "Application Menu";
	public HomePage(WebDriver driver){
		homePageObjects = new HomePageObjects();
		this.driver = driver;
		PageFactory.initElements(driver,homePageObjects);
	}
	
	public boolean verifyHomePagedisplayed(){
		boolean homePageDisplayed = false;
		if (homePageObjects.menuHeader.isDisplayed() && 
			homePageObjects.menuHeader.getText().equalsIgnoreCase(menuHeader)){
				homePageDisplayed = true;
			}
		return homePageDisplayed;
	}
	public void selectTerminalEmulation(){
		homePageObjects.terminalLink.click();
	}
	
	public void logOff(){
		homePageObjects.logOffLink.click();
	}
	
	class HomePageObjects{
		
		@FindBy(xpath = "//a[@id='LogOffIn']")
        @CacheLookup
        private WebElement logOffLink;
		
		@FindBy(xpath = "//fieldset/legend")
        @CacheLookup
        private WebElement menuHeader;
		
		@FindBy(xpath = "//a[text()='Terminal Emulation']")
        @CacheLookup
        private WebElement terminalLink;
	}
	
}
