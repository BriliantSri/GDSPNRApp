<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.lang.*" %>
<%@ page import = "javax.sql.*" %>
<%@ page import = "java.sql.*" %>
<%@page import = "com.model.*" %>
<%@ page errorPage="/ErrorDetails.jsp" %>
<!DOCTYPE html>
<html>
<script language ='javascript' type ="text/javascript">
	
</script>
<body bgcolor="#588BA3">
<%
		try{
		Connection con = null;
		Statement stmt = null;
		boolean displayLoginPage = false;
		
		if(session.getAttribute("connection")!=null){
			if (con == null){			
			con = (Connection)session.getAttribute("connection");
			}
		}
		
		if(con==null || (con!=null && con.isClosed())){
			ConnectToDatabase connectToDB = new ConnectToDatabase();
			session.setAttribute("connection", connectToDB.getConnection());
			session.setAttribute("statement", connectToDB.getStatement());
			session.setAttribute("connectedToDB",true);		
			session.setAttribute("display",true);
			displayLoginPage = true;
		}else if(con!=null){
			if(!con.isClosed()){				
				session.setAttribute("connectedToDB",true);
				session.setAttribute("display",true);
				displayLoginPage = true;
			}
		}
		if (displayLoginPage==true){
			RequestDispatcher requestDispactcher =  request.getRequestDispatcher("/LoginPage.jsp");
			//RequestDispatcher requestDispactcher =  request.getRequestDispatcher("/GDSPNRHome.jsp");
			requestDispactcher.forward(request, response);
		}else{
			out.println("<H3 align ='CENTER' style ='color:red'>Database not connected<H3>");
		}
	}catch(Exception e)
	{
		session.setAttribute("exception",e);
		throw e;
	}
%>	
</body>
</html>