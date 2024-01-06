<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.common.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	
		session = request.getSession(false);
		session.setAttribute("logOffMessage",true);
		CommonProperties.envURL="";
		CommonProperties.logID="";
		CommonProperties.passWord="";				
		CommonProperties.validUser=false;		
		RequestDispatcher requestDispactcher =  request.getRequestDispatcher("/LoginPage.jsp");
		//RequestDispatcher requestDispactcher =  request.getRequestDispatcher("/GDSPNRHome.jsp");
		requestDispactcher.forward(request, response);
	%>
</body>
</html>