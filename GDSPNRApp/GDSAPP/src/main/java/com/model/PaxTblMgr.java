package com.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;

public class PaxTblMgr {
	
	Connection connection;
	ResultSet resultSet;
	Statement stmt;
	
	public PaxTblMgr(Connection connection) {
		this.connection = connection;
	}
	
	
	public HashMap<String, String> getPaxDetails() {
		HashMap<String, String> paxMap = new HashMap<String, String>();
		int i =1;
		String query = "select * from PassengerTbl";
		try {
			if(this.connection!=null && !this.connection.isClosed()) {
				stmt = connection.createStatement();
				resultSet = stmt.executeQuery(query);
				ResultSetMetaData rsmd = resultSet.getMetaData();  
	            // this getColumnCount rerun the number of column in the selected table  
	            int numberOfColumns = rsmd.getColumnCount();  
	            // while loop and with while loop code use for print the data 
	            
				while (resultSet.next()) {
					System.out.println("row: " +i+ " - First name: " + resultSet.getString("FIRSTNAME") );
					System.out.println("row: " +i+ " - Last name: " + resultSet.getString("LASTNAME") );
					System.out.println("row: " +i+ " - Middle name: " + resultSet.getString("MIDDLENAME") );
					System.out.println("row: " +i+ " - IS FQTV: " + resultSet.getString("ISFQTV") );
					System.out.println("row: " +i+ " - AIRLINE CODE: " + resultSet.getString("AIRLINECODE") );
					System.out.println("row: " +i+ " - FQTVNUM: " + resultSet.getString("FQTVNUM") );
					System.out.println("row: " +i+ " - FQTV Tier: " + resultSet.getString("FQTVTIER") );
					
					paxMap.put("firstName", resultSet.getString("FIRSTNAME"));
					paxMap.put("lastName", resultSet.getString("LASTNAME"));
					paxMap.put("middleName", resultSet.getString("MIDDLENAME"));
					paxMap.put("isFQTV", resultSet.getString("ISFQTV"));
					paxMap.put("airlineCode", resultSet.getString("AIRLINECODE"));
					paxMap.put("fqtvNum", resultSet.getString("FQTVNUM"));
					paxMap.put("fqtvTier", resultSet.getString("FQTVTIER"));
					
					/*
					for (int i = 1; i <= numberOfColumns; i++) {  
	                    if (i > 1)  
	                        System.out.print(", ");  
	                    String columnValue = resultSet.getString(i);  
	                    System.out.print(columnValue);  
	                }*/  
					
				}
				resultSet.close();
				stmt.close();
				
			}else {
				paxMap = null;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return paxMap;
	}

}
