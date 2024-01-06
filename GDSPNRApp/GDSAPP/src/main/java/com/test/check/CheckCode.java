package com.test.check;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

//import io.github.bonigarcia.wdm.WebDriverManager;

public class CheckCode {
	public static void main(String args[]) throws IOException {
		
		/* WebDriverManager.chromedriver().setup();
		
		WebDriver driver = new ChromeDriver();

		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = caps.getBrowserName();
		String browserVersion = caps.getVersion();
		System.out.println(browserName+" "+browserVersion);*/
		
		ArrayList<String> output = new ArrayList<String>();
	    Process p = Runtime.getRuntime().exec("reg query \"HKEY_CURRENT_USER\\Software\\Google\\Chrome\\BLBeacon\" /v Version");
	    BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()),8*1024);
	    BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	    String s = null;
	 
	    while ((s = stdInput.readLine()) != null) {
	        output.add(s);
	    }
	 
	    String chrome_value = (output.get(2));
	    String chrome_full_version = chrome_value.trim().split("   ")[2];
	    String chrome_first_version = chrome_full_version.trim().substring(0,chrome_full_version.indexOf(".")-1);
	    
	    System.out.println(chrome_full_version);
	    System.out.println(chrome_first_version);
		
	}
	
}
