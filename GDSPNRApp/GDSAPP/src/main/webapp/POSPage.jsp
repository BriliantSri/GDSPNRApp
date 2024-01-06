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

<script type ="text/javascript">
function logOffApp(){
	if (confirm("Confirm Log Off!") == true) {
		document.forms["submitForm"].action = "LogOff.jsp";	
		document.forms["submitForm"].submit();
	}
}

function onLoadFields(){
	document.getElementById("currLbl").style.display='none';
	setImage();
}
function setImage(){	
	parent.changeHeaderImage("frame1.jsp");	
}

function assignParameters(){
	var assignParameters = false;
	var tempStr;
	if(document.getElementById("posSel")!=null){
		if((document.getElementById("posSel").value).trim()!="Select"){
			document.getElementById("hiddenPOS").value = document.getElementById("posSel").value;
			//Currency
			if(document.getElementById("currFld")!=null){
				tempStr = document.getElementById("currFld").innerHTML;
				//alert(tempStr);
				if(tempStr.length>0){
					document.getElementById("hiddenCurr").value = tempStr;
					assignParameters=true;
				}else{
					alert("Issue with currency, Please check!!!");
				}
				
			}else{
				alert("Issue with currency, Please check!!!");
			}
		}else{
			//alert("Select Point of Sales");
			//document.getElementById("posSel").focus();
			assignParameters=true;
		}
	}
	//hiddenCurr hiddenLniata
	return assignParameters;
}

function populateCurrencyFld(){
	var posCurrStr;
	var currCD;
	var ele = document.getElementById("posSel");
	var dispLbl = false;
	//alert("1");
	
	if(document.getElementById("posCurrStr")!=null && document.getElementById("posCurrStr").value.length > 1){
		posCurrStr = document.getElementById("posCurrStr").value;
		//alert("cdNoStr" + posCurrStr);
		const myArray = posCurrStr.split("|");
		if(myArray.length>0){
			for (var i=0; i< myArray.length; i++){
				//alert(myArray[i]);
				if(myArray[i].length > 0 && ele.value!="Select"){
					if(myArray[i].indexOf(ele.value)==0){
						currCD = myArray[i].substring(myArray[i].indexOf(":")+1);
						document.getElementById("currLbl").style.display='block';
						document.getElementById('currFld').style.display ='block';
						document.getElementById('currFld').innerHTML = currCD;
						//document.getElementById("currFld").value=currCD;
						dispLbl = true;
						break;
					}
				}
			}
		}
	}
	if(dispLbl==false){
		document.getElementById("currLbl").style.display='none';
		document.getElementById('currFld').style.display ='none';
	}

}

</script>

<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>

<Style>
.back-to-topmsg {
  position: absolute;
  right: 5rem;
  top: 6rem;
}
.cell-style{
width: 200px;
border: 1px solid;
}


</Style>


<body bgcolor="#778899" onLoad ='onLoadFields();'>
<h1 align = 'center'>Point of Sales</h1>

<%
//Deleted from Form tag
		//onclick=" return generateFile();"
		Connection connection = (Connection)session.getAttribute("connection");
		if (connection==null || connection.isClosed()){
			ConnectToDatabase connDB = new ConnectToDatabase();
			connection = connDB.getConnection();
		}

		
		Iterator<HashMap<String, String>> iter;	
		HashMap<String, String> lniataPOS, posCurr;
		String pos="", lniata="", currencyCD="", dbPOS="";
		boolean lniataAndPOSSaved=false;
		if(CommonProperties.lniata!=null && CommonProperties.lniata.length()>0){
			if(CommonProperties.pos!=null && CommonProperties.pos.length()>0){
				pos = CommonProperties.pos;
				lniata = CommonProperties.lniata;
				lniataAndPOSSaved=true;
			}			
		}
		if(!lniataAndPOSSaved){
			POSTransactions posTrans = new POSTransactions();
			lniataPOS = posTrans.getlniataPOS(connection);
			if(lniataPOS!=null && !lniataPOS.isEmpty()){
				pos = lniataPOS.get("POS");
				CommonProperties.pos = pos;
				lniata = lniataPOS.get("lniata");
				CommonProperties.lniata = lniata;
			}
			posTrans.closeDriver();
		}
		
		TableDataService tbleDTTrans = new TableDataService(connection);
		if(CommonProperties.currency!=null && CommonProperties.currency.length()>0){
			currencyCD = CommonProperties.currency;	
		}else{
			
			currencyCD = tbleDTTrans.getcurrCD(pos);			
			CommonProperties.currency = currencyCD;
		}
		Vector<HashMap<String, String>> posCurrecy = tbleDTTrans.getCityAndCurr();
		out.println("<br>");
		
		out.println("<table>");
		out.println("<tr><td width = '50px'></td><td width = '180px'><label id = 'posAssignedLbl'><b>POS Assigned:</b></label> "+pos+"</td>");
		out.println("<td><label id = 'currencyLbl'><b>Currency:</b></label> "+currencyCD+"</td></tr></table>");
		out.println("<br>");
	out.println("<table>");
	out.println("<tr><td width = '50px'></td><td><label><b>Select different POS if required.</b></label></td>");
	out.println("</table>");
	out.println("<table ><tr><td width = '50px'></td><td width = '180px'><label>Point of Sales:</label></td>");
	
	
	if(posCurrecy.size()>0){
		out.println("<td width = '110px'><select id='posSel' name='posSel' onchange='populateCurrencyFld()'>");	
		out.println("<option>Select</option>");
		iter = posCurrecy.iterator();
		while(iter.hasNext()){
			posCurr = iter.next();
			if(posCurr.get("city")!=null && posCurr.get("city").length()>0){
				out.println ("<option>"+posCurr.get("city")+"</option>");
				dbPOS = dbPOS+""+posCurr.get("city")+":"+posCurr.get("currency")+"|";
			}
		}
		out.println("</select></td><td><label id='currLbl'><b>Currency: </b></label></td><td><label id='currFld' name='currFld' value=''><label></td></tr>");
	}else{
		//out.println("<td><input id='posSel' name='posSel'></td></tr>");
	}
	
	out.println("</table>");
	
	
	out.println("<input type ='hidden' id = 'posCurrStr' name = 'posCurrStr' value='"+dbPOS+"'/>");
	out.println("<br>");
	out.println("<table><tr><td width = '50px'></td><td>");
	//out.println("<tr><td>");
	
	out.println("<form method='POST' name = 'submitForm' onclick = 'return assignParameters();' action='GDSPNRHome.jsp'>");
	out.println("<input type ='hidden' id = 'hiddenLniata' name = 'hiddenLniata' value =''/>");
	out.println("<input type ='hidden' id = 'hiddenPOS' name = 'hiddenPOS' value =''/>");
	out.println("<input type ='hidden' id = 'hiddenCurr' name = 'hiddenCurr' value =''/>");
    out.println("<div id ='submitDiv'>");
	out.println("<input type='submit' value='Continue'>");	
	out.println("<input type='button' value='Log Off' onClick ='logOffApp();'></tr>");
	out.println("</div>");
	out.println("</form></td></tr>");
	
	out.println("</table>");
	if(posCurrecy.size()>0){
		iter = posCurrecy.iterator();
		out.println("<table class='back-to-topmsg' style ='border-collapse: collapse;width:300px'><tr ><th class='cell-style'>Point of Sales</th><th class='cell-style'>Currency</th><tr>");
		while(iter.hasNext()){			
			posCurr = iter.next();	
			if(posCurr.get("city")!=null && posCurr.get("city").length()>0){
				out.println("<tr ><td align='center' class='cell-style'>"+posCurr.get("city")+"</td>");
				out.println("<td align='center' class='cell-style'> "+posCurr.get("currency")+"</td></tr>");
			}
		
		}
		out.println("</table>");
	}
	
%>
</body>

</html>