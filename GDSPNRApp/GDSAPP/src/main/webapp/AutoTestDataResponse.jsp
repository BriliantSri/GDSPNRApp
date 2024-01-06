<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body bgcolor="#778899">
	<%
	
		String filePath = request.getAttribute("filePath")!=null?request.getAttribute("filePath").toString():"";	
	
				
			if(filePath.length()>0){
				out.println("<h2 align='center'> Succesfully Automation Test Data generated </h2>");
				out.println("<h3 align='center'>Test Data sheet saved at: " + filePath+"</h3>");
			}else{
				out.println("TestData document not created due to errors");
			}
		
			
	%>
</body>
</html>