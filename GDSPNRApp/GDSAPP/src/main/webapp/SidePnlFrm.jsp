<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.common.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1" >
<!-- http-equiv="refresh" content="2" -->
<!-- <title>Insert title here</title>-->
<script language ='javascript' type ="text/javascript">
	function assignHidden(fname){
		document.getElementById(getfile).value=fname;
	}
	function setLinkClicked(link){	
		if(document.cookie!=null && document.cookie.length > 0){
			var urlID = document.cookie;
			if(document.getElementById(urlID)!=null){
				document.getElementById(urlID).className ="changeStyle";
			}
		}
		var linkId = link.id;
		document.cookie=link.id;
		link.className ="setStyle";
	}
	function highLightURL(){
		if(document.cookie!=null && document.cookie.length > 0){
			var urlID = document.cookie;
			if(document.getElementById(urlID)!=null){
				document.getElementById(urlID).className ="setStyle";
			}
		}
	}	
</script>
<style type="text/css">
	.changeStyle
	{ 
		text-decoration: none;		
	}
	.defaultStyle
	{ 
		color: #850057;
		text-decoration: none;
		
	}
	.setStyle
	{ 
		color: #003F60;
		text-decoration: underline;
		border-bottom:1px solid #003F60;
	}
	a:hover
	{ 
		color: #003F60;
		text-decoration: underline;
		border-bottom:1px solid #003F60;
	}
</style>
</head>
<body bgcolor="#588BA3">
<basefont face="arial" size="2" >	
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
			out.println("<div style='text-align:center;' ><label ><u>Test Data options</u></label></div>");
			out.println("<br>");
			out.println("<ul>");
			out.println("<li><a href='POSPage.jsp' target ='frame2' id ='gdsPNR' style='text-decoration:none;' onClick='setLinkClicked(this);'>GDS PNR Creation</a></li><br>");	
			out.println("<li><a href='AutoTstDataHomePage.jsp' target ='frame2' id ='autoTstData' style='text-decoration:none;' onClick='setLinkClicked(this);'> Create Automation test data</a></li><br>");
			out.println("</ul>");
		}else{
			out.println("<br>");
			out.println("<div style='text-align:center;' ><label align='center' style='font-size:30px;'>Please Login</label></div>");
			out.println("<br>");
			out.println("<br>");
			out.println("<p style='text-align:center;'>");
			out.println("<img src='Images/pointingright.jpg' width='75%' height='25%' align='bottom'>");
			out.println("</p>");
		}
		%>
	
</body>
</html>