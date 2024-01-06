package com.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BrowserUtil {
	
	public String getChromeVersion() throws IOException {
		ArrayList<String> output = new ArrayList<String>();
	    Process p = Runtime.getRuntime().exec("reg query \"HKEY_CURRENT_USER\\Software\\Google\\Chrome\\BLBeacon\" /v Version");
	    BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()),8*1024);
	    BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	    String s = null;
	 
	    while ((s = stdInput.readLine()) != null) {
	        output.add(s);
	    }
	    
	    ////
	    
	    String dummyForGit="DummyGit";
	    
	    String dummyToGit="DummyToGit";
	 
	    String chrome_value = (output.get(2));
	    String chrome_full_version = chrome_value.trim().split("   ")[2];
	    String chrome_version_partone = chrome_full_version.trim().substring(0,chrome_full_version.indexOf(".")-1);
	    
	    //System.out.println(chrome_full_version);
	    //System.out.println(chrome_version_partone);
		return chrome_version_partone;
	}

}
