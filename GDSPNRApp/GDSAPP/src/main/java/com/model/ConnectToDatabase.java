package com.model;
import java.sql.*;


public class ConnectToDatabase {
	
	//Variables
	static Connection connection;
	//private Connection connection;
	Statement curStatement;
	Statement statement;
	public String userName;
	public String passWord;	
	
	//Getters and Setters
	public Connection getConnection() {
		if (this.connection!=null) {			
		}else{
			getDBConnection();			
		}
		return this.connection;
	}
	public void setStatement(Statement statement) {
		this.statement = statement;
	}
	public Statement getStatement() throws Exception{
		this.setStatement(connection.createStatement());
		return this.statement;
	}
	public void setConnection(Connection con) {
		this.connection = con;
	}
	public void setCurStatement(Statement curStatement) {
		this.curStatement = curStatement;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}	
	public void getDBConnection(){
		
		try {
			// loading the jdbc odbc driver  
           // Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");  
            // creating connection to the data base  
            //Connection con = DriverManager.getConnection("jdbc:odbc:mydsn", "", "");
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");			
			Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:/Databases/GDSDB.accdb");
			
            this.setConnection(con);
            this.setStatement(con.createStatement());  
            // create an execute sql command on database 
			
		}catch(Exception e) {
			e.printStackTrace();
		}		
	}
	//Constructor
		public ConnectToDatabase() {
			getDBConnection();
			// TODO Auto-generated constructor stub		
		}
	
	
}
