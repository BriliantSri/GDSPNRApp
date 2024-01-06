package com.utilities;

import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import com.base.TestBase;

public class MaeUtilities {
	private WebDriver driver;
	static final Logger logger = LogManager.getLogger(TestBase.class.getName());
	public MaeUtilities(WebDriver driver) {
		this.driver = driver;		
	}
	
	private By cityComboListEle = By.xpath("//md-virtual-repeat-container/div/div[2]/ul/li");
	
	public boolean selectDate() {
		boolean dateSelected = false;
		
		return dateSelected;
	}
	public void selectOptFrmCmbBx(By cmbElement, String selOption) throws InterruptedException {		
		if (driver.findElement(cmbElement).isDisplayed()) {
			driver.findElement(cmbElement).click();
			Thread.sleep(500);
			driver.findElement(By.xpath("//div[contains(@class, 'md-active')]//md-option//div[contains(text(),'"+selOption+"')]")).click();
			//System.out.println("Test Attribute	- 2" + driver.findElement(cmbElement).getAttribute("innerText"));
			Thread.sleep(500);
			logger.info("Selected Option from Auto dropdown: " +	selOption );
			//Assert.assertTrue(driver.findElement(cmbElement).getAttribute("innerText").contains(selOption), selOption + " - option not selected from available options");
		}else {
			logger.error(selOption + " - Option not selected from the dropdown");
		}
		
	}
	public void inputText(By inputElement, String inputValue) {
		if(this.isElementPresent(inputElement)) {
			driver.findElement(inputElement).clear();
			driver.findElement(inputElement).sendKeys(inputValue);
			System.out.println("Input Element:   "+driver.findElement(inputElement).getAttribute("value"));
			//Assert.assertTrue(driver.findElement(inputElement).getAttribute("value").contains(inputValue), "Value inserted in input field: "+inputValue);
		}
	}
	

	public void selectDynamicCmbValue(By cmbElement, String selOption) throws InterruptedException {
		List<WebElement> cityComboList;
		WebElement wElement;
		Iterator<WebElement> iter;
		boolean optionSelected = false;
		if (driver.findElement(cmbElement).isDisplayed()) {
			driver.findElement(cmbElement).click();
			driver.findElement(cmbElement).clear();
			driver.findElement(cmbElement).sendKeys(selOption);
			cityComboList = driver.findElements(this.cityComboListEle);
			Thread.sleep(500);
			if (!cityComboList.isEmpty()) {
				iter = cityComboList.iterator();
				while(iter.hasNext()) {
					wElement = iter.next();
					if (wElement.getText().contains("- "+ selOption.toUpperCase())){
						wElement.click();
						optionSelected = true;
						break;
					}else if (wElement.getText().trim().contains(selOption.toUpperCase())){
						wElement.click();
						optionSelected = true;
						break;
					}	
					Thread.sleep(500);
				}
				//.findElement(cmbElement).sendKeys(Keys.ENTER);	
				logger.info("Selected Option from Auto dropdown: " +	selOption );
			}else {				
				logger.info("Selected Option from Auto dropdown: " +	selOption );				
			}
			if(optionSelected == false) {
				driver.findElement(cmbElement).sendKeys(Keys.ENTER);
				optionSelected = true;
			}
			//Assert.assertTrue(driver.findElement(cmbElement).getAttribute("value").contains(selOption), selOption + " - option not selected from available options");
		}else {
			logger.error(selOption + " - Option not selected from the dropdown");
		}
	}
	public boolean isElementPresent(By locatorKey) {
	    try {
	        driver.findElement(locatorKey);
	        return true;
	    } catch (org.openqa.selenium.NoSuchElementException e) {
	        return false;
	    }
	}

	public boolean isElementVisible(String cssLocator){
	    return driver.findElement(By.cssSelector(cssLocator)).isDisplayed();
	}
}
