package com.autotstdatagen;

import java.sql.Connection;

import com.model.TableDataService;

public class AdvancePassengerInfo {
	Connection connection;
	TableDataService tableDataService;	
	public AdvancePassengerInfo(Connection conn) {
		this.connection = conn;
		tableDataService = new TableDataService(conn);
	}
	
	public void prepareTestData(String scenario) {
		try {
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
