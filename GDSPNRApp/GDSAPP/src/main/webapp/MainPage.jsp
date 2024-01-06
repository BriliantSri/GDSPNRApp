<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import = "com.common.*" %>
 <%@ page import = "com.model.*" %>
 <%@ page import = "java.util.*" %>
 <%@ page import = "java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<script language ='javascript' type ="text/javascript">
function onLoadFunction(){
	parent.reLoadSidePlnFrame();
}
function logOffApp(){
	if (confirm("Confirm Log Off!") == true) {
		document.forms["submitForm"].action = "LogOff.jsp";	
		document.forms["submitForm"].submit();
	}
}
</script>
	

<meta charset="ISO-8859-1">
<title>Main Page</title>
</head>

<body bgcolor="#778899" onLoad ='onLoadFunction();'>

 <%

		boolean userAlreadyVerified = false;
		if(CommonProperties.validUser==true){
			if(CommonProperties.envURL!=null && CommonProperties.envURL.length()>0){				
				if(CommonProperties.logID!=null && CommonProperties.logID.length()>0){					
					if(CommonProperties.passWord!=null && CommonProperties.passWord.length()>0){
						userAlreadyVerified=true;
					}
				}
			}
		}
		
		if(userAlreadyVerified){
			out.println("<br>");
			out.println("<table><td width ='70%'");
			out.println("<label align='center' style='text-align:center;font-size:30px;'>Please select an option from left panel</label>");
			out.println("</td><td width ='30%'>");
			out.println("<p>");
			out.println("<img src='Images/pointingleft.jpg' width='50%' height='25%' align='bottom'>");
			out.println("</p>");
			out.println("<td>");
			out.println("</tr></table>");
			out.println("<table><tr><td>");
			out.println("<form method='POST' name = 'submitForm' >");
			out.println("<input type='button' value='Log Off' onClick ='logOffApp();'>");			
			out.println("</form>");
			out.println("</td></tr></table>");
		}else{
			RequestDispatcher requestDispactcher =  request.getRequestDispatcher("/LoginPage.jsp");
			requestDispactcher.forward(request, response);	
		}
		%>

</body>
</html>