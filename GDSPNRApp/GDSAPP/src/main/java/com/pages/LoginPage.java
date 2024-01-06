package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;



public class LoginPage{
	LoginPageObjects loginPageObjects;
	WebDriver driver;
	public LoginPage(WebDriver driver){
		loginPageObjects = new LoginPageObjects();
		this.driver = driver;
		PageFactory.initElements(driver,loginPageObjects);
	}
	
	public void submitLogin(String uID, String password){
		loginPageObjects.loginID.sendKeys(uID);
		loginPageObjects.loginPassword.sendKeys(password);
		loginPageObjects.loginSubmit.click();
	}
	
	class LoginPageObjects{
        @FindBy(xpath = "//*[@id='header']/span[@id='site-title']")
        @CacheLookup
        private WebElement loginHeader;
        
        @FindBy(xpath = "//input[@id='ID']")
        @CacheLookup
        private WebElement loginID;
        
        @FindBy(xpath = "//input[@id='Password']")
        @CacheLookup
        private WebElement loginPassword;
        
        @FindBy(xpath = "//input[@type='submit' and @value = 'Login']")
        @CacheLookup
        private WebElement loginSubmit;
	}
}
