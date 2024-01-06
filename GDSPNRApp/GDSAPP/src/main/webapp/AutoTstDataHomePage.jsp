<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import = "com.core.*" %>
<%@ page import = "com.model.*" %>
<%@ page import = "com.common.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script type ="text/javascript">
/*
function onLoadFields(){
	//alert("inLoadFields");
	onLoad=true;
	hideFields();
	
}*/

function getCheckedBoxes(){
	var checkBoxes = document.getElementsByName("ScenarioCheck");
	var checkBoxesChecked = [];
	var returnParam = false;
	  // loop over them all
	  for (var i=0; i<checkBoxes.length; i++) {
		 // And stick the checked ones onto an array...
	     if (checkBoxes[i].checked) {
	    	 //alert (checkBoxes[i].value);
	    	 checkBoxesChecked.push(checkBoxes[i].value);
	     }
	  }
	  if(checkBoxesChecked.length > 0 ){
		  document.getElementById("scenarioIDArr").value = checkBoxesChecked;		 
		  returnParam = true;
	  }else{
		  alert("None of the checkbox checked.");
	  }
	  if(returnParam==true){
		  doLoadSpinner();
	  }
	  return returnParam;
}

function checkBoxChange(obj){
	if(obj.checked){
		checkAllCheckBoxChecked();
	}else{
		document.getElementById("chk_all").checked=false;
	}
}
function checkAllCheckBoxChecked(){
	var checkBoxes = document.getElementsByName("ScenarioCheck");
	var checkBoxesChecked = [];
	var allCheckBoxesChecked = true;
	  // loop over them all
	  for (var i=0; i<checkBoxes.length; i++) {
		 // And stick the checked ones onto an array...
	     if (checkBoxes[i].checked) {
	    	
	     }else{
	    	 allCheckBoxesChecked=false;
	     }
	  }
	  if(allCheckBoxesChecked==true ){
		  document.getElementById("chk_all").checked=true;
	  }else{
		  document.getElementById("chk_all").checked=false;
	  }
}

function checkAllCheckBoxes(obj){
	var checkBoxes = document.getElementsByName("ScenarioCheck");
	var checkBoxesChecked = [];
	var allCheckBoxesChecked = true;
	
	if(obj.checked){
		for (var i=0; i<checkBoxes.length; i++) {
			checkBoxes[i].checked = true;
		}
	}else{
		for (var i=0; i<checkBoxes.length; i++) {
			checkBoxes[i].checked = false;
		}
		
	}	  
}
function doNotLoadSpinner(){
	document.getElementById("lblHeader").style.display='none';
	document.getElementById("divSpin").style.display='none';
	document.getElementById("fulScreen").style.display='block';
}
function doLoadSpinner(){
	window.scrollTo(0, 0);
	document.getElementById("lblHeader").style.display='block';
	document.getElementById("divSpin").style.display='block';
	document.getElementById("fulScreen").style.display='none';
}
function onLoadFunction(){
	parent.reLoadSidePlnFrame();
	parent.changeHeaderImage("AutoImage.jsp");
	doNotLoadSpinner();
	unCheckAllCheckBoxes();
}

function logOffApp(){
	if (confirm("Confirm Log Off!") == true) {
		document.forms["submitForm"].action = "LogOff.jsp";	
		document.forms["submitForm"].submit();
	}
}

function homeDisp(){
	document.forms["submitForm"].action = "frame2.jsp";	
	document.forms["submitForm"].submit();
}

function unCheckAllCheckBoxes(){
	var checkBoxes = document.getElementsByName("ScenarioCheck");
	var checkBoxesChecked = [];	
	document.getElementById("chk_all").checked=false;
	for (var i=0; i<checkBoxes.length; i++) {
		checkBoxes[i].checked = false;
	}	  
}
</script>
<style>
.cell-style{
border: 1px solid;
}
.loader {
	  border: 5px solid #f3f3f3; /* Light grey */
	  border-top: 5px solid #3498db; /* Blue */
	  border-radius: 50%;
	  width: 30px;
	  height: 30px;
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
<body bgcolor="#778899" onLoad ='onLoadFunction();'>
		<%
		out.println("<div id='lblHeader'><h2 align='center'>Test data generation is in progress....... </h2> </div>");
		out.println("<div class= 'center' id='divSpin'><div class='loader' ></div></div>");
		out.println("<div id='fulScreen'>"); 
//Deleted from Form tag
		//onclick=" return generateFile();"
		Connection connection = (Connection)session.getAttribute("connection");
		if (connection==null || connection.isClosed()){
			ConnectToDatabase connDB = new ConnectToDatabase();
			connection = connDB.getConnection();	
			CommonProperties.connection = connection;
		}
		// Setting fileName notexists
		CommonProperties.fileNameSet=false;
		HashMap<Integer, HashMap<String, String>> autoScenarioMap = new HashMap();
		HashMap<String, String> indScenarioMap = new HashMap();
		HashMap<String, HashMap<String, String>> flightData;
		String scenarioName="", functionality="";
		//GenerateFlightData generateFlightData = new GenerateFlightData(connection);
		TableDataService tbleDTTrans = new TableDataService(connection);
		autoScenarioMap = tbleDTTrans.getAutoScenarios();
		
		TreeMap<Integer, HashMap<String, String>> sortedMap = new TreeMap();
		
		
		
		if(autoScenarioMap!=null && !autoScenarioMap.isEmpty()){
			sortedMap.putAll(autoScenarioMap);
			out.println("<table style='align:center' class='cell-style'>");
			out.println("<tr><th style='width:20%' class='cell-style'><input type='checkbox' id='chk_all' name='chk_all' value='chk_all' onchange = 'checkAllCheckBoxes(this);'><label>  Select All </label></th><th style='width:75%' class='cell-style'>Scenario</th></tr>");
			for(int key:sortedMap.keySet()){
				indScenarioMap = sortedMap.get(key);
				scenarioName = indScenarioMap.get("scenario");
				//<input type="checkbox" id="vehicle1" name="vehicle1" value="Bike">
				out.println("<tr><td class='cell-style'><input type='checkbox' id='chk_"+indScenarioMap.get("ID").trim()+"' name='ScenarioCheck' value='"+indScenarioMap.get("ID").trim()+"' onchange = 'checkAllCheckBoxChecked(this);'></td>");
				out.println("<td class='cell-style'>"+scenarioName+"</td></tr>");
			}
			out.println("</table>");
			
			out.println("<table><tr><td>");
			out.println("<form method='POST' name = 'submitForm' onclick = 'return getCheckedBoxes();' action='ProcessAutoTestDataRequest.jsp'>");
			out.println("<input type='hidden' id ='scenarioIDArr' name = 'scenarioIDArr' value=''/>");	
			out.println("<div id ='submitDiv'>");
			out.println("<input type='submit' value='Generate Test Data'/>");			
			out.println("</div>");					
			out.println("</form></td>");
		}else{
			out.println("Some Error Occurred");
			out.println("<table><tr>");
		}
		
		out.println("<td><input type='button' value='Reset All' onClick ='onLoadFunction();'></td>");
		out.println("<td><input type='button' value='Home' onClick ='homeDisp();'></td>");
		out.println("<td><input type='button' value='Log Off' onClick ='logOffApp();'></td></tr>");
		out.println("</table>");
		out.println("</div>");
		
		%>
</body>
</html>