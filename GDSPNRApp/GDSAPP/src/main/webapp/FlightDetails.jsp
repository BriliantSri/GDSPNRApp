<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.model.*" %>
 <%@ page import = "java.util.*" %>
 <%@ page import = "java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<script language ='javascript' type ="text/javascript">
//function onLoadFields(){
	//alert("inLoadFields");
	//collapsePaxFields();
//}
function collapseFltDtlFields(){
	var i, paxCount;
	paxCount = document.getElementById("hiddenPaxCount").value;	
	for (i=2; i<=paxCount; i++){			
		if(document.getElementById("tblFltDtls"+i)!=null){
		 	document.getElementById("tblFltDtls"+i).style.display='none';		 	
		}		
	}	
}
function checkNum(obj){
	if(isNaN(obj.value)){
		alert("Flight number should be numeric and upto 4 digits");
		obj.value="";
		obj.focus();
	}else{		
		if((obj.value).length > 4){
			alert("Flight number should not be more than 4 numeric digits");
			obj.value="";
			obj.focus();
		}
	}
}
function allLetter(inputtxt)
{
 var letters = /^[A-Za-z\s\(\)]+$/;
 var repInputValue;
 
 if(inputtxt.value.trim().length>0){
	 repInputValue=inputtxt.value.trim();
	 //repInputValue=repInputValue.replace(")","");
	 //repInputValue=repInputValue.replace(" ","");
	 
	 if(repInputValue.match(letters))
	   {	
		 //checkCityCode(inputtxt);
	   }
	 else
	   { 
		 alert("Enter only Alphabet Code");
		 inputtxt.value="";
		 inputtxt.focus();
	   }
	
	}
}
function resetFlds(num){
	var i = num;
	//alert("paxCount: "+paxCount);
	for (i = (parseInt(num)+1); i<=10; i++){			
		if(document.getElementById("airLineCode"+i)!=null){
		 	//document.getElementById("airLineCode"+i).selectedIndex = 0;	
		 	document.getElementById("airLineCode"+i).value = "";	
		}
		if(document.getElementById("cos"+i)!=null){
		 	document.getElementById("cos"+i).selectedIndex = 0;		 	
		}
		if(document.getElementById("org"+i)!=null){
		 	document.getElementById("org"+i).value = "";		 	
		}
		if(document.getElementById("des"+i)!=null){
		 	document.getElementById("des"+i).value = "";		 	
		}
		if(document.getElementById("doj"+i)!=null){
		 	document.getElementById("doj"+i).value = "";		 	
		}
		if(document.getElementById("dojMonth"+i)!=null){
		 	document.getElementById("dojMonth"+i).selectedIndex = 0;		 	
		}
		if(document.getElementById("fltNo"+i)!=null){
		 	document.getElementById("fltNo"+i).value = "";		 	
		}
	}
}
function getCityArr(){
 	//alert("inmethod");
	var	hidFld = document.getElementById("hiddenCityDescriptions");
	var cityDescStr;
	var myArray;
	if(hidFld!=null && hidFld.value.length>0){		
		cityDescStr = hidFld.value;
		//alert("cdNoStr" + cdNoStr);
		myArray = cityDescStr.split("|");
		
	}
	//alert(myArray);
	return myArray;
}
function getAirLineCodes(){
 	//alert("inmethod");
	var	hidFld = document.getElementById("hiddenAirlineCodes");
	var airLinesStr;
	var myArray;
	if(hidFld!=null && hidFld.value.length>0){		
		airLinesStr = hidFld.value;
		//alert("cdNoStr" + cdNoStr);
		myArray = airLinesStr.split("|");
		
	}
	//alert(myArray);
	return myArray;
}
/*
function checkAirlineCode(obj){
	
	var tmpStr = obj.value;
	var	hidFld = document.getElementById("hiddenAirlineCodes").value;

	if(hidFld!=null && hidFld.length>0){
		if( hidFld.indexOf(tmpStr.toUpperCase())!=-1){
			
		}else{
			alert("Enter/ Select Correct Airline code.");
			obj.value="";
			obj.focus();		 	
		}
	}
}
function checkCityCode(obj){
	
	var tmpStr = obj.value;
	var	hidFld = document.getElementById("hiddenCityDescriptions").value;

	if(hidFld!=null && hidFld.length>0){
		if(hidFld.indexOf(tmpStr.trim().toUpperCase())!=-1){
			if(hidFld.subString(0,hidFld.length)==hidFld && hidFld.indexOf("|"+tmpStr.trim().toUpperCase()+" ")!=-1){
				
			}else if(hidFld.indexOf("("+tmpStr.trim().toUpperCase()+")")!=-1){
				
			}else{
				alert("Enter/ Select Correct City code.");
			}
		}else{
			alert("Enter/ Select Correct City code.");
			obj.value="";
			obj.focus();		 	
		}
	}
}*/
</script>

<script language="JavaScript" src="htmlDatePicker_doj.js" type="text/javascript"></script>
<link href="htmlDatePicker.css" rel="stylesheet" />
<script language ='javascript' type ="text/javascript">
	function loadFields(fname){
		document.getElementById("calbut").style.backgroundImage="url('calendar2.gif')";
	}
</script>
<link rel="stylesheet" href="AutoComplete.css" type="text/css">
<script src="./dist/js/DatePickerX.min.js"></script>
 <link rel="stylesheet" href="./dist/css/DatePickerX.css">
    <script>
  	  window.addEventListener('DOMContentLoaded', function()
      {
      	var $tmp;
      	var $max;  
      	var i;      	
      	var paxCount = document.getElementById("hiddenPaxCount").value;	
      	for(i=1; i<=paxCount; i++){      	
		$tmp = document.querySelector("#doj"+ i.toString());
           $tmp.DatePickerX.init({
               mondayFirst: true,
               format 	   : 'ddMyy',
               //minDate    : new Date(2021, 5, 9),
               minDate	   : function()
               {
                   var date = new Date();
                   return new Date().setDate(date.getDate());
               },               
               maxDate    : function()
               {
                   var date = new Date();
                   return new Date().setDate(date.getDate() + 315);
               },
               clearButton: false
           });
      	}
      });
    </script>
   <script language="JavaScript" src="AutoComplete.js" type="text/javascript"></script>
 
<meta charset="ISO-8859-1">
<title>Home Page</title>
</head>
<Style>
.back-to-topmsg {
  position: absolute;
  right: 1rem;
  top: 8rem;
}
</Style>



     
    
     <!--  Latest datepickercode -->


<label class='back-to-topmsg'><b>Follow the instructions below for the flight entries</b><br><br>
1. Select Correct Airline/ Carrier code<br>
2. Select the flight and Class of Service where the seats are <br>
   &nbsp;&nbsp;&nbsp;&nbsp;available for all the passengers listed above<br>
3. Select Correct Originating City code as per the flight# entered<br>
4. Select Correct Destination City code as per the flight# entered<br>
5. Select valid Date of Journey</label>
<body bgcolor="#778899" >



<%
	String fldName = "";
	String rdNameYs="";
	String rdNameNo="";
	String ctyDescrStr="";
	String airLineCdsStr="";
	int noOfPassengers = 10;//(int)session.getAttribute("paxCount");
	
	Connection connection = (Connection)session.getAttribute("connection");
	Iterator<String> iter;	
	TableDataService tbleDTService = new TableDataService(connection);
	Vector<String> airlineCodeVec = tbleDTService.getAirlineCodes();	
	Vector<String> cosCodeVec = tbleDTService.getCOSCodes();
	Vector<String> cityCodeVec = tbleDTService.getCityCodes();
	String tempString="";
	
	
	if(!(cityCodeVec==null && cityCodeVec.isEmpty())){
		iter = cityCodeVec.iterator();
		if(iter.hasNext()){
			ctyDescrStr="";
			while(iter.hasNext()){
				ctyDescrStr = ctyDescrStr+"|"+iter.next().toString();						
			}
		}
	}
	out.println("<input type='hidden' id='hiddenCityDescriptions' value='"+ctyDescrStr+"'/>");
	
	if(!(airlineCodeVec==null && airlineCodeVec.isEmpty())){
		iter = airlineCodeVec.iterator();
		if(iter.hasNext()){
			airLineCdsStr="";
			while(iter.hasNext()){
				airLineCdsStr = airLineCdsStr+"|"+iter.next().toString();						
			}
		}
	}else{
		airLineCdsStr="CM (COPA)|UA (United Airlines)|LH (Lufthansa)|AA (American Airlines)";
	}
	out.println("<input type='hidden' id='hiddenAirlineCodes' value='"+airLineCdsStr+"'/>");
	
	//Blank row addition
	out.println("<Table>");
	out.println("<tr><td>");
	out.println("</td><td></td><td></td></tr>");
	out.println("</table>");
	
	out.println("<input type='hidden' id='hiddenPaxCount' value='"+noOfPassengers+"'/>");
	
	for (int i=1; i<=noOfPassengers; i++){
		out.println("<Table id='tblFltDtls"+i+"'>");
		out.println("<tr><td width = '225px'><b><label>Enter Segment - "+i+" flight details </label></b></td><td></td></tr>");
		
		out.println("<tr><td><label><label id='airLineCodeLbl"+i+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("Airline Code:</label></td>");
		/*
		out.println("<td><select id ='airLineCode"+i+"' name ='airLineCode"+i+"'><option>Select</option>");
	 
		if(airlineCodeVec.size()>0){
			iter = airlineCodeVec.iterator();
			while(iter.hasNext()){
				out.println ("<option>"+iter.next()+"</option>");
			}			
		}else{
			out.println ("<option>CM (COPA)</option>");
			out.println ("<option>UA (United Airlines)</option>");
			out.println ("<option>LH (Lufthansa)</option>");
		}*/
		out.println("<td><div class='autocomplete' style='AutoComplete.css;'>");
		out.println("<input type='text' id ='airLineCode"+i+"' name ='airLineCode"+i+"' placeholder = 'Enter Airline Code' style='width:200px;' >");
		out.println("<div>"); 
		%>
		<script>
		autocomplete(document.getElementById("airLineCode<%=i%>"), getAirLineCodes());
		
		</script>
		<%
		out.println ("</select>");
		out.println("</td></tr>");
		
		out.println("<tr><td><label id='fltNoLbl"+i+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("Flight number:</label></td>");
		out.println("<td><input id = 'fltNo"+i+"' name = 'fltNo"+i+"' value='' onblur ='checkNum(this);' placeholder='Enter Flight Number'> </td></tr>");
		out.println("<tr><td><label id='cosLbl"+i+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("Class of Service:</label></td>");
		out.println("<td><select id ='cos"+i+"' name ='cos"+i+"'><option>Select</option>");
		 
		if(cosCodeVec.size()>0){
			iter = cosCodeVec.iterator();
			while(iter.hasNext()){
		out.println ("<option>"+iter.next()+"</option>");
			}
		}else{
			out.println ("<option>C</option>");
			out.println ("<option>J</option>");
			out.println ("<option>D</option>");
			out.println ("<option>R</option>");
			out.println ("<option>Y</option>");
			out.println ("<option>M</option>");
			out.println ("<option>Q</option>");
			out.println ("<option>K</option>");
			out.println ("<option>E</option>");
			out.println ("<option>L</option>");
		}
		out.println ("</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("</td></tr>");
		
		//out.println("<td><input id = 'cos"+i+"' name = 'cos"+i+"' value=''></td></tr>");
		out.println("<tr><td><label id ='orgLbl"+i+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("Origin:</label></td>");/*
		out.println("<td><select id ='org"+i+"' name ='org"+i+"'><option>Select</option>");
		 
		if(cityCodeVec.size()>0){
			iter = cityCodeVec.iterator();
			while(iter.hasNext()){
		out.println ("<option>"+iter.next()+"</option>");
			}
		}else{
			out.println ("<option>PTY (PANAMA)</option>");
			out.println ("<option>BOG (BAGOTA, USA)</option>");
			out.println ("<option>ORD (CHICAGO, USA)</option>");
			out.println ("<option>IAH (HOUSTON, USA)</option>");
			out.println ("<option>SFO (SAN FRANCISCO, USA)</option>");			
			out.println ("<option>YYZ (TORONTO, CA)</option>");			
		}
		out.println ("</select>");*/
		
		tempString = "org"+String.valueOf(i);
		out.println("<td><div class='autocomplete' style='AutoComplete.css;'>");
		out.println("<input type='text' id ='"+tempString+"' name ='"+tempString+"' placeholder='Enter Departure City' style='width:200px;' onblur='allLetter(this);'");
		out.println("<div>");
		%>
		<script>
		autocomplete(document.getElementById("<%=tempString%>"), getCityArr());
		
		</script>
		<%
		out.println("</td></tr>");		
		out.println("<tr><td><label id='desLbl"+i+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("Destination:</label></td>");
		
		
		/*out.println("<td><select id ='des"+i+"' name ='des"+i+"'><option>Select</option>");
		
		 
		if(cosCodeVec.size()>0){
			iter = cityCodeVec.iterator();
			while(iter.hasNext()){
				out.println ("<option>"+iter.next()+"</option>");
			}			
		}else{
			out.println ("<option>PTY (PANAMA)</option>");
			out.println ("<option>BOG (BAGOTA, USA)</option>");
			out.println ("<option>ORD (CHICAGO, USA)</option>");
			out.println ("<option>IAH (HOUSTON, USA)</option>");
			out.println ("<option>SFO (SAN FRANCISCO, USA)</option>");			
			out.println ("<option>YYZ (TORONTO, CA)</option></select>");			
		}
		out.println ("</select>");*/
		tempString = "des"+String.valueOf(i);
		out.println("<td><div class='autocomplete' style='AutoComplete.css;'>");
		out.println("<input type='text' id ='"+tempString+"' name ='des"+i+"' placeholder = 'Enter Arrival City' style='width:200px;' onblur='allLetter(this);'>");
		out.println("<div>"); 
		%>
		<script>
		autocomplete(document.getElementById("<%=tempString%>"), getCityArr());
		
		</script>
		<%
		out.println("</td></tr>");
		
		out.println("<tr id='dojRow"+i+"'><td><label id = 'dojLbl"+i+"' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("Date of Journey:</label></td>");
		//out.println("<td><input type='text' name = 'doj"+i+"' id ='doj"+i+"' readonly onClick='GetDateDOJ(this);' value='DDMMMYY' ></td>");
		out.println("<td><input type='text' name = 'doj"+i+"' id ='doj"+i+"' readonly placeholder='Select date'></td>");
		out.println("</tr>");
		
		out.println("</Table>");
		
		
		
	}
	
	
	
	
%>
  
</body>
</html>