package com.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.common.BrowserUtil;

public class TestBase {
	private WebDriver driver;
	
	private Properties prop;
	
	private String browserVersion;	
	
	private String projectPath = System.getProperty("user.dir")	;
	private static final Logger logger = LogManager.getLogger(TestBase.class.getName());	
	public WebDriver getDriver() {
		return driver;
	}
	
	public Properties getProp() throws IOException{
		prop = new Properties();
		FileInputStream fis = new FileInputStream(projectPath+"\\src\\main\\java\\mae\\resources\\data.properties");
		prop.load(fis);
		return prop;
	}
	private void setProp() throws IOException {
		prop = new Properties();
		FileInputStream fis = new FileInputStream(projectPath+"\\src\\main\\java\\mae\\resources\\data.properties");
		prop.load(fis);
	}
	public WebDriver initializeDriver() throws IOException {
		BrowserUtil bsw = new BrowserUtil();
		browserVersion = bsw.getChromeVersion();
		//String browserName = this.getProp().getProperty("browser");
		String browserName = "chrome";
		if (browserName.contains("chrome")) {
			logger.trace("Initiated Chrome");
			ChromeOptions options = new ChromeOptions();
			options.setBrowserVersion(browserVersion);
			//options.addArguments("--headless");			
			if (browserName.contains("headless")) {
				options.addArguments("--headless");
			}
			
			driver = new ChromeDriver(options);
			
			
			
		}else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", projectPath+"\\src\\main\\java\\mae\\resources\\geckodriver-v0.18.0-win64\\geckodriver.exe");			
			driver = new FirefoxDriver();
		}else if (browserName.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", projectPath+"\\src\\main\\java\\mae\\resources\\IEDriverServer.exe");			
			driver = new ChromeDriver();
		}else if (browserName.equalsIgnoreCase("edge")) {
			//WebDriverManager.edgedriver().setup();			
			EdgeOptions options = new EdgeOptions();			
			driver = new EdgeDriver(options);
			
		}else {
			//Default one if nothing is mentioned
			System.setProperty("webdriver.chrome.driver", projectPath+"\\src\\main\\java\\mae\\resources\\chromedriver_win32Version86\\chromedriver.exe");			
			driver = new ChromeDriver();
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return driver;
		
	}
	public WebDriver initializeHeadlessDriver() throws IOException {
		BrowserUtil bsw = new BrowserUtil();
		browserVersion = bsw.getChromeVersion();
		//String browserName = this.getProp().getProperty("browser");
		String browserName = "chrome";
		if (browserName.contains("chrome")) {
			//System.setProperty("webdriver.chrome.driver", "C:\\Srikanth Kokkula\\Selenium\\Chrome Driver\\chromedriver_win32Version86\\chromedriver.exe");			
			//replacing with below for all browsers as per Training - 223
			//System.setProperty("webdriver.chrome.driver", "C:\\WebDrivers\\chromedriver.exe");
			
			//WebDriverManager.chromedriver().setup();
			// Below as per training 224
			logger.trace("Initiated Chrome");
			ChromeOptions options = new ChromeOptions();			
			options.setBrowserVersion(browserVersion);
			options.addArguments("--headless");			
			if (browserName.contains("headless")) {
				options.addArguments("--headless");
			}
			
			driver = new ChromeDriver(options);
			
			
			
		}else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", projectPath+"\\src\\main\\java\\mae\\resources\\geckodriver-v0.18.0-win64\\geckodriver.exe");			
			driver = new FirefoxDriver();
		}else if (browserName.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", projectPath+"\\src\\main\\java\\mae\\resources\\IEDriverServer.exe");			
			driver = new ChromeDriver();
		}else if (browserName.equalsIgnoreCase("edge")) {
			//WebDriverManager.edgedriver().setup();			
			EdgeOptions options = new EdgeOptions();			
			driver = new EdgeDriver(options);
			
		}else {
			//Default one if nothing is mentioned
			System.setProperty("webdriver.chrome.driver", projectPath+"\\src\\main\\java\\mae\\resources\\chromedriver_win32Version86\\chromedriver.exe");			
			driver = new ChromeDriver();
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return driver;
		
	}
	
	public void terminateDriver(){
		driver.close();
	}
}
