package com.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SHRUtilities {
	
	private List idArray;
	
	public List<String> getRandomNumbers(int num){
		String ranNum;
		List<String> randomNumStrings = new ArrayList<String>();
		int cntr=0;
		try {
			while(cntr<=25) {
				Random randomNum = new Random();
				ranNum = String.valueOf(randomNum.nextInt(num));
				if(randomNumStrings.size()>0) {
					if(!randomNumStrings.contains(ranNum)) {
						randomNumStrings.add(ranNum);
						cntr++;
					}
				}else {
					randomNumStrings.add(ranNum);
					cntr++;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();	
		}
		return randomNumStrings;
	}

}
