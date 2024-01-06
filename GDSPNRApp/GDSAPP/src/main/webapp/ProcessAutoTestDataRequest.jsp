<%@page import="com.core.AutoTestDataGenerator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.core.*" %>
<%@ page import = "com.model.*" %>
<%@ page import = "com.common.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.utilities.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script type ="text/javascript">
function logOffApp(){
	if (confirm("Confirm Log Off!") == true) {
		document.forms["submitForm"].action = "LogOff.jsp";	
		document.forms["submitForm"].submit();
	}
}

function homeDisp(){
	document.forms["submitForm"].action = "HomePage.jsp";	
	document.forms["submitForm"].submit();
}
</script>


<style>
.loader {
	  border: 16px solid #f3f3f3; /* Light grey */
	  border-top: 16px solid #3498db; /* Blue */
	  border-radius: 50%;
	  width: 120px;
	  height: 120px;
	  animation: spin 2s linear infinite;
	}

	@keyframes spin {
	  0% { transform: rotate(0deg); }
	  100% { transform: rotate(360deg); }
	}
	.container {
  height: 500px;
  position: relative;
  border: 3px solid #dbe4ed; /* Border color is optional */ 
}
.center {
  margin: 0;
  position: absolute;
  top: 50%;
  left: 50%;
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
}
</style>
</head>
<body bgcolor="#778899">
	
	<%
		
		String scenarioIDArrayStr = request.getParameter("scenarioIDArr")!=null? request.getParameter("scenarioIDArr"):null;
		HashMap<String, HashMap<String, String>> tdMap;
		String entryID="";
		String filePath="";
		
		Connection connection = (Connection)session.getAttribute("connection");
		if (connection==null || connection.isClosed()){
			ConnectToDatabase connDB = new ConnectToDatabase();
			connection = connDB.getConnection();
		}
		
		if(scenarioIDArrayStr!=null && scenarioIDArrayStr.length()>0){
			CommonProperties.autoScenarioString=scenarioIDArrayStr;
			
			
			
			//Excel Sheet creator
			ExcelUtility excelUtility = new ExcelUtility();
			AutoTestDataGenerator autoTDGenerator = new AutoTestDataGenerator(connection);
			filePath = autoTDGenerator.generateTestData(scenarioIDArrayStr);
			
			/*if(filePath!=null && filePath.length()>0){
				session.setAttribute("filePath", filePath);
				out.println("Test Data sheet saved at: " + filePath);
			}else{
				session.setAttribute("filePath", "");
				out.println("TestData document not created due to errors");
			}*/
			
			if(filePath!=null && filePath.length()>0){
				//request.setAttribute("filePath", filePath);	
				out.println("<h2 align='center'> Succesfully Automation Test Data generated </h2>");
				out.println("<h3 align='center'>Test Data sheet saved at: " + filePath+"</h3>");
			}else{
				//request.setAttribute("filePath", "");		
				out.println("TestData document not created due to errors");
			}
			//RequestDispatcher requestDispactcher =  request.getRequestDispatcher("/AutoTestDataResponse.jsp");
			//requestDispactcher.forward(request, response);
			
			
			/*
			if(tdMap!=null && tdMap.size()>0){
				out.println("<table>");
				for (Map.Entry<String, HashMap<String, String>> tdEntry: tdMap.entrySet()){
					entryID = tdEntry.getKey();
					out.println("<tr><td>"+entryID+"</td>");
					excelUtility.createScenarioHeaders(tdEntry.getValue());
					for(Map.Entry<String, String> mEntry: tdEntry.getValue().entrySet()){
						out.println("<td>"+mEntry.getValue()+"</td>");						
					}
					out.println("</tr>");	
				}
				out.println("</table>");
			}
			
			/*
			AutoTestDataGenerator autoTDGenerator = new AutoTestDataGenerator(connection);
			tdMap = autoTDGenerator.createTestDataDerivedInputs(scenarioIDArrayStr);
			if(tdMap!=null && tdMap.size()>0){
				out.println("<table>");
				for (Map.Entry<String, HashMap<String, String>> tdEntry: tdMap.entrySet()){
					entryID = tdEntry.getKey();
					out.println("<tr><td>"+entryID+"</td>");
					
					for(Map.Entry<String, String> mEntry: tdEntry.getValue().entrySet()){
						out.println("<td>"+mEntry.getKey()+" - "+mEntry.getValue()+"</td>");						
					}
					out.println("</tr>");	
				}
				out.println("</table>");
			}*/
			
		}else{
			out.println("Recevied null Scenario Ids Array");
		}
		
		out.println("<table><tr><td>");
		out.println("<form method='POST' name = 'submitForm' action='frame2.jsp'>");		
		out.println("<div id ='submitDiv'>");
		out.println("<input type='submit' value='Home'/>");			
		out.println("</div>");
		out.println("<td><input type='button' value='Log Off' onClick ='logOffApp();'></td></tr>");
		out.println("</table>");
	%>
</body>
</html>