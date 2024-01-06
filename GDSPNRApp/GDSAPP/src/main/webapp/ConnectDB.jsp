<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page errorPage="/ErrorDetails.jsp" %>    
<%@ page import = "java.lang.*" %>
<%@ page import = "javax.sql.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.model.*" %>
<%@ page import = "javax.servlet.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.util.Date" %> 
<%@ page import ="java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	/*
	String userName = request.getParameter("hiddenUserName")!=null?request.getParameter("hiddenUserName").toString():"";
	String password = request.getParameter("hiddenPassword")!=null?request.getParameter("hiddenPassword").toString():"";
	boolean connSuccess = false;	
	try{
		DEBConnectToDatabase connectToDatabase = new DEBConnectToDatabase();
		if(userName!=null && password!=null){
			connectToDatabase.setUserName(userName);
			connectToDatabase.setPassWord(password);
		    connSuccess = connectToDatabase.connectDatabase();
		    if (connSuccess){
				out.println ("Database connection is Successful");	
			}else{
				out.println ("Database connection Failed");
			}
		}else{
			out.println ("Username or Password is Null");
		}
	}catch(Exception e)
	{
		session.setAttribute("exception",e);
		throw e;
	}
	*/

%>
</body>
</html>