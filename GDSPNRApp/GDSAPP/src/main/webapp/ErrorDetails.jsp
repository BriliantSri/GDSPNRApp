<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page isErrorPage="true" %>
    <%@ page import="java.sql.SQLException" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error Details</title>
</head>
<body bgcolor="#778899">
<basefont face="arial" size="3" >
<%	
	Exception exceptionCought = (session.getAttribute("exception")!=null)?(Exception)session.getAttribute("exception"):null;
	if(exceptionCought!=null){		
		if(exceptionCought instanceof SQLException){
			out.println("<H2 align = 'center'><font color='purple'>Database Error Occured</font></H2>");		
		}else{
			out.println("<H2 align = 'center'><font color='purple'>An Exception occured</font> </H2>");
		}
		out.println("<Table>");		
		out.println("<TR><TH align ='left'><font color='blue'>Exception Message:</font></TH></TR>");
		out.println("<TR><TD>"+exceptionCought.getMessage()+"</TD></TR>");
		out.println("</Table>");
		out.println("<br><br><br><br><br>");
		
		//Technical Details
		out.println("<hr>");
		out.println("<Table>");
		out.println("<TR align ='center'><TH><font color='blue'>Technical Details</font></TH></TR>");
			
	    StackTraceElement[] stack = exceptionCought.getStackTrace();
	    String exceptionDetails = exceptionCought.getMessage()+ "<br>";
	    for (StackTraceElement stElement : stack) {
	    	exceptionDetails = exceptionDetails + stElement.toString() + "<br>";	    	
	    }
	    out.println("<TR><TD>"+exceptionDetails+"</TD></TR>");
		out.println("</Table>");
		out.println(" <hr>");
		out.flush();
	}
%>
</body>
</html>