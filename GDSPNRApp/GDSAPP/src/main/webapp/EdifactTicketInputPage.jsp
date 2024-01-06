<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.model.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.core.*" %>
<!DOCTYPE html>
<html>
<head>
<script type ="text/javascript">

var onLoad=false;
function onLoadFields(){
	//alert("inLoadFields");
	onLoad=true;
	hideFields();
	
}
function hideFields(){	
	
	
	if(document.getElementById("payHeaderOne")!=null){
		document.getElementById("payHeaderOne").style.display='none';
	}
	if(document.getElementById("payHeaderTwo")!=null){
		document.getElementById("payHeaderTwo").style.display='none';
	}
	
	///Latest
	if(document.getElementById("selCardLbl")!=null){
		document.getElementById("selCardLbl").style.display='none';
	}
	if(document.getElementById("cardTypeFld")!=null){
		document.getElementById("cardTypeFld").style.display='none';
	}
	
	if(document.getElementById("cardNoFldLbl")!=null){
		document.getElementById("cardNoFldLbl").style.display='none';
	}
	if(document.getElementById("cardNoFld")!=null){
		document.getElementById("cardNoFld").style.display='none';
	}
	
	
	
	// Second payment
	if(document.getElementById("sptPymtNo")!=null && document.getElementById("sptPymtNo").checked==true ){
		
		if(document.getElementById("splitPercentageLbl")!=null){
			document.getElementById("splitPercentageLbl").style.display='none';
		}
		if(document.getElementById("splitFirstLbl")!=null){
			document.getElementById("splitFirstLbl").style.display='none';
		}
		if(document.getElementById("splitFirstPercentage")!=null){
			document.getElementById("splitFirstPercentage").style.display='none';
		}
		
		if(document.getElementById("splitSecondLbl")!=null){
			document.getElementById("splitSecondLbl").style.display='none';
		}
		if(document.getElementById("splitSecondPercentage")!=null){
			document.getElementById("splitSecondPercentage").style.display='none';
		}
		/*
		if(document.getElementById("secFareTypeLbl")!=null){
			document.getElementById("secFareTypeLbl").style.display='none';
		}
		if(document.getElementById("secFareType")!=null){
			document.getElementById("secFareType").style.display='none';
		}*/
		
		
		if(document.getElementById("secPayTypeLbl")!=null){
			document.getElementById("secPayTypeLbl").style.display='none';
		}
		if(document.getElementById("secPayType")!=null){
			document.getElementById("secPayType").style.display='none';
		}
		
		 
	}
	
	if(document.getElementById("secCardNoFldLbl")!=null){
		document.getElementById("secCardNoFldLbl").style.display='none';
	}
	if(document.getElementById("secCardNoFld")!=null){
		document.getElementById("secCardNoFld").style.display='none';
	}
	
	
	if(document.getElementById("secSelCardLbl")!=null){
		document.getElementById("secSelCardLbl").style.display='none';
	}
	if(document.getElementById("secCardTypeFld")!=null){
		document.getElementById("secCardTypeFld").style.display='none';
	}
	
	
	
	
	
}
function populateCardNos(cdType){
	var cdNoStr;
	var newOption;
	var ele;
	var fldToPopulate, fldLabel;
	var selValue;
	var selectName = cdType.options[cdType.selectedIndex].value;
	if(cdType.id=="cardTypeFld"){
		ele = document.getElementById("payType");
		fldToPopulate = "cardNoFld";
		fldLabel = "cardNoFldLbl";
	}else if(cdType.id=="secCardTypeFld"){
		ele = document.getElementById("secPayType");
		fldToPopulate = "secCardNoFld";
		fldLabel = "secCardNoFldLbl";
	}

	selValue = ele.value;		
	if (selValue == "Credit Card"){
		selectName = "C" + cdType.value;
	}if (selValue == "Debit Card"){
		selectName = "D" + cdType.value;
	}
	
	//alert(selectName);
	if(document.getElementById(selectName)!=null){
		//alert("hi");
		cdNoStr = document.getElementById(selectName).value;
		//alert("cdNoStr" + cdNoStr);
		const myArray = cdNoStr.split("|");
		const select = document.getElementById(fldToPopulate);
		if(select!=null && myArray.length>0){			
			if(document.getElementById(fldToPopulate)!=null){
				document.getElementById(fldToPopulate).style.display='block';
				document.getElementById(fldLabel).style.display='block';
				removeSelectOptions(document.getElementById(fldToPopulate));
			}
			for (var i=0; i< myArray.length; i++){
				//alert(myArray[i]);
				if(myArray[i].length > 0){
					select.options[select.options.length] = new Option(myArray[i], myArray[i], false, false);
				}
			}
		}
	}
}
function removeSelectOptions(obj){
	//alert("----"+obj.name);
	var cdNoStr;
	var newOption;
	if(obj!=null){
		const select = obj; 
		select.options.length = 1;
	}
}
function displayCardOption(obj){
	var ele = obj;
	var selValue;
	
	if(ele.id=="payType"){
		//ele = document.getElementById("payType");
		selValue = ele.value;
		if (selValue == "Credit Card" || selValue == "Debit Card"){			
			if(document.getElementById("selCardLbl")!=null){
				document.getElementById("selCardLbl").style.display='block';
			}
			if(document.getElementById("cardTypeFld")!=null){
				document.getElementById("cardTypeFld").style.display='block';
			}	
			//alert("Credit card");
			populateCardTypes(selValue, "first");
		}else{
			//hideFields();
			hideCardFields(obj);
		}
		
	}else if(ele.id=="secPayType" && ele.style.display=='block'){
		//ele = document.getElementById("secPayType");
		selValue = ele.value;		
		if (selValue == "Credit Card" || selValue == "Debit Card"){			
			if(document.getElementById("secSelCardLbl")!=null){
				document.getElementById("secSelCardLbl").style.display='block';
			}
			if(document.getElementById("secCardTypeFld")!=null){
				document.getElementById("secCardTypeFld").style.display='block';
			}	
			//alert("Credit card");
			populateCardTypes(selValue,"second");
		}else{
			//hideFields();
			hideCardFields(obj);
		}
	}
}


function hideCardFields(obj){
	var eleId = obj.id;
	if(eleId == "payType"){
		if(document.getElementById("selCardLbl")!=null){
			document.getElementById("selCardLbl").style.display='none';
		}
		if(document.getElementById("cardTypeFld")!=null){
			document.getElementById("cardTypeFld").style.display='none';
		}
		
		if(document.getElementById("cardNoFldLbl")!=null){
			document.getElementById("cardNoFldLbl").style.display='none';
		}
		if(document.getElementById("cardNoFld")!=null){
			document.getElementById("cardNoFld").style.display='none';
		}
	}else if (eleId=="secPayType"){
		if(document.getElementById("secCardNoFldLbl")!=null){
			document.getElementById("secCardNoFldLbl").style.display='none';
		}
		if(document.getElementById("secCardNoFld")!=null){
			document.getElementById("secCardNoFld").style.display='none';
		}
		if(document.getElementById("secSelCardLbl")!=null){
			document.getElementById("secSelCardLbl").style.display='none';
		}
		if(document.getElementById("secCardTypeFld")!=null){
			document.getElementById("secCardTypeFld").style.display='none';
		}
	}
	
}

function checkFields(){
	var incorrectValueSelected = false;
	var cnt=0;
	var errorMessageString="";
	if(document.getElementById("sptPymtYs")!=null && document.getElementById("sptPymtYs").checked==true ){
		if(document.getElementById("splitFirstPercentage").style.display=='block'){
			if(document.getElementById("splitFirstPercentage").value==""){
				if(document.getElementById("splitSecondPercentage").style.display=='block' && document.getElementById("splitSecondPercentage").value==""){
					errorMessageString += (cnt+1) + ". Enter the Values for Split percentages No\n\n";
					incorrectValueSelected = true;	
				}else{
					updateSliptPercentage(document.getElementById("splitSecondPercentage"));
				}
			}else{
				if(document.getElementById("splitSecondPercentage").style.display=='block' && document.getElementById("splitSecondPercentage").value==""){
					updateSliptPercentage(document.getElementById("splitFirstPercentage"));					
				}
			}
		}		
	}
	if(document.getElementById("fareType")!=null){
	 	if(document.getElementById("fareType").value=='Select'){
	 		errorMessageString += (cnt+1) + ". Select the Fare Type\n\n";
	 		cnt++;
		 	document.getElementById("fareTypeLbl").style.color ='Red';
		 	incorrectValueSelected = true;		 
		}else{
			document.getElementById("fareTypeLbl").style.color ='Black';
			
		}
	}
	if(document.getElementById("payType")!=null){
	 	if(document.getElementById("payType").value=='Select'){
	 		errorMessageString += (cnt+1) + ". Select the Payment Type\n\n";
	 		cnt++;
		 	document.getElementById("payTypeLbl").style.color ='Red';
		 	incorrectValueSelected = true;		 
		}else{
			document.getElementById("payTypeLbl").style.color ='Black';			
		}
	}
		
	if(document.getElementById("cardTypeFld")!=null && document.getElementById("cardTypeFld").style.display=="block"){
	 	if(document.getElementById("cardTypeFld").value=='Select'){
	 		errorMessageString += (cnt+1) + ". Select the Card Type\n\n";
	 		cnt++;
		 	document.getElementById("selCardLbl").style.color ='Red';
		 	incorrectValueSelected = true;		 
		}else{
			document.getElementById("selCardLbl").style.color ='Black';
		}
	}
	if(document.getElementById("cardNoFld")!=null && document.getElementById("cardNoFld").style.display=="block"){
	 	if(document.getElementById("cardNoFld").value=='Select'){
	 		errorMessageString += (cnt+1) + ". Select the Card No\n\n";
	 		cnt++;
		 	document.getElementById("cardNoFldLbl").style.color ='Red';
		 	incorrectValueSelected = true;		 
		}else{
			document.getElementById("cardNoFldLbl").style.color ='Black';	
		}
	}
	
	if(incorrectValueSelected==true){
		alert(errorMessageString);	
	}
	return incorrectValueSelected;
}

function displaySecondPayment(){
	if(document.getElementById("sptPymtYs")!=null && document.getElementById("sptPymtYs").checked==true ){
		document.getElementById("hiddenSplitPayment").value="Yes";
		if(document.getElementById("payHeaderOne")!=null){
			document.getElementById("payHeaderOne").style.display='block';
		}
		if(document.getElementById("payHeaderTwo")!=null){
			document.getElementById("payHeaderTwo").style.display='block';
		}
		document.getElementById("splitPercentageLbl").style.display='block';
		document.getElementById("splitFirstPercentage").value="50";
		document.getElementById("splitFirstPercentage").style.display='block';		
		document.getElementById("splitFirstLbl").style.display='block';
		document.getElementById("splitSecondPercentage").value="50";
		document.getElementById("splitSecondPercentage").style.display='block';		
		document.getElementById("splitSecondLbl").style.display='block';
		/*
		document.getElementById("secCardNoFldLbl").style.display='block';
		document.getElementById("secCardNoFld").style.display='block';
		document.getElementById("secSelCardLbl").style.display='block';
		document.getElementById("secCardTypeFld").style.display='block';
		*/
		//document.getElementById("secFareTypeLbl").style.display='block';
		//document.getElementById("secFareType").style.display='block';
		document.getElementById("secPayTypeLbl").style.display='block';
		document.getElementById("secPayType").style.display='block';	
		//hideCardFields(document.getElementById("secPayType"));
	}
	
	if(document.getElementById("sptPymtNo")!=null && document.getElementById("sptPymtNo").checked==true ){
		document.getElementById("hiddenSplitPayment").value="No";
		if(document.getElementById("payHeaderOne")!=null){
			document.getElementById("payHeaderOne").style.display='none';
		}
		if(document.getElementById("payHeaderTwo")!=null){
			document.getElementById("payHeaderTwo").style.display='none';
		}
		
		document.getElementById("splitFirstPercentage").value="";
		
		document.getElementById("splitSecondPercentage").value="";
		
		if(document.getElementById("splitPercentageLbl")!=null){
			document.getElementById("splitPercentageLbl").style.display='none';
		}
		if(document.getElementById("splitFirstLbl")!=null){
			document.getElementById("splitFirstLbl").style.display='none';
		}
		if(document.getElementById("splitFirstPercentage")!=null){
			document.getElementById("splitFirstPercentage").style.display='none';
		}
		
		if(document.getElementById("splitSecondLbl")!=null){
			document.getElementById("splitSecondLbl").style.display='none';
		}
		if(document.getElementById("splitSecondPercentage")!=null){
			document.getElementById("splitSecondPercentage").style.display='none';
		}
		
		if(document.getElementById("secCardNoFldLbl")!=null){
			document.getElementById("secCardNoFldLbl").style.display='none';
		}
		if(document.getElementById("secCardNoFld")!=null){
			document.getElementById("secCardNoFld").style.display='none';
		}
		
		
		if(document.getElementById("secSelCardLbl")!=null){
			document.getElementById("secSelCardLbl").style.display='none';
		}
		if(document.getElementById("secCardTypeFld")!=null){
			document.getElementById("secCardTypeFld").style.display='none';
		}
		
		/*
		if(document.getElementById("secFareTypeLbl")!=null){
			document.getElementById("secFareTypeLbl").style.display='none';
		}
		if(document.getElementById("secFareType")!=null){
			document.getElementById("secFareType").style.display='none';
		}*/
		
		
		if(document.getElementById("secPayTypeLbl")!=null){
			document.getElementById("secPayTypeLbl").style.display='none';
		}
		if(document.getElementById("secPayType")!=null){
			document.getElementById("secPayType").style.display='none';
		}
		
		
	}
}


function updateSliptPercentage(obj){
	var objID;
	objID = obj.id;
	var enteredValue;
	if(checkNum(obj)==true){
		if(objID=="splitFirstPercentage"){
			if((document.getElementById("splitFirstPercentage").value).length>0){
				enteredValue = parseInt(document.getElementById("splitFirstPercentage").value);
				if(enteredValue>99){
					if((document.getElementById("splitSecondPercentage").value).length>0){
						enteredValue = parseInt(document.getElementById("splitSecondPercentage").value);
						document.getElementById("splitFirstPercentage").value=100-enteredValue;	
						alert("Enter Percentage between 1 and 99. \n\n If complete payment to be made from single Payment, select Split Payment as No.");
					}else{
						alert("Enter Percentage between 1 and 99. \n\n If complete payment to be made from single Payment, select Split Payment as No.");
						document.getElementById("splitFirstPercentage").value = "";
					}
				}else if (enteredValue<100 && enteredValue>0){
					document.getElementById("splitSecondPercentage").value=100-enteredValue;
				}else if(enteredValue=0 ){
					alert("Enter Percentage between 1 and 99. \n\n If complete payment to be made from single Payment, select Split Payment as No.");
					document.getElementById("splitFirstPercentage").value = "";
					
				}
			}else{
				if((document.getElementById("splitSecondPercentage").value).length>0){
					enteredValue = parseInt(document.getElementById("splitSecondPercentage").value);
					document.getElementById("splitFirstPercentage").value=100-enteredValue;	
				}else{
					alert("Enter Percentage between 1 and 99. \n\n If complete payment to be made from single Payment, select Split Payment as No.");
				}
			}
			
		}else if(objID=="splitSecondPercentage"){
			if((document.getElementById("splitSecondPercentage").value).length>0){
				enteredValue = parseInt(document.getElementById("splitSecondPercentage").value);
				if(enteredValue>99){
					if((document.getElementById("splitFirstPercentage").value).length>0){
						enteredValue = parseInt(document.getElementById("splitFirstPercentage").value);
						document.getElementById("splitSecondPercentage").value=100-enteredValue;
						alert("Enter Percentage between 1 and 99. \n\n If complete payment to be made from single Payment, select Split Payment as No.");
					}else{
						alert("Enter Percentage between 1 and 99. \n\n If complete payment to be made from single Payment, select Split Payment as No.");
						document.getElementById("splitSecondPercentage").value = "";
					}
					
				}else if (enteredValue<100 && enteredValue>0){
					document.getElementById("splitFirstPercentage").value=100-enteredValue;
				}else if(enteredValue>0){
					alert("Enter Percentage between 1 and 99. \n\n If complete payment to be made from single Payment, select Split Payment as No.");
					document.getElementById("splitSecondPercentage").value = "";
					
				}
			}else{
				if((document.getElementById("splitFirstPercentage").value).length>0){
					enteredValue = parseInt(document.getElementById("splitFirstPercentage").value);
					document.getElementById("splitSecondPercentage").value=100-enteredValue;	
				}else{
					alert("Enter Percentage between 1 and 99. \n\n If complete payment to be made from single Payment, select Split Payment as No.");
				}
			}
			
		}
	}
}

function populateCardTypes(cdType, payNum){
	var cdTypeStr;
	var newOption;	
	var select;
	var myArray;
	var hidFld;
	if(payNum=="first"){
		if(document.getElementById("cardTypeFld")!=null){
			if (cdType == "Credit Card"){
				hidFld = document.getElementById("hidCreCardTypes");
			}else if (cdType == "Debit Card"){
				hidFld = document.getElementById("hidDebCardTypes");
			}
			if(hidFld!=null){		
				cdTypeStr = hidFld.value;
				//alert("cdNoStr" + cdNoStr);
				myArray = cdTypeStr.split("|");
				select = document.getElementById("cardTypeFld");
				if(select!=null && myArray.length>0){			
					removeSelectOptions(select);
					
					for (var i=0; i< myArray.length; i++){
						//alert(myArray[i]);
						if(myArray[i].length > 0){
							select.options[select.options.length] = new Option(myArray[i], myArray[i], false, false);
						}
					}
				}
			}
		}
	}else if(payNum=="second"){
		if(document.getElementById("secCardTypeFld")!=null){
			if (cdType == "Credit Card"){
				hidFld = document.getElementById("hidCreCardTypes");
			}else if (cdType == "Debit Card"){
				hidFld = document.getElementById("hidDebCardTypes");
			}
			if(hidFld!=null){		
				cdTypeStr = hidFld.value;
				//alert("cdNoStr" + cdNoStr);
				myArray = cdTypeStr.split("|");
				select = document.getElementById("secCardTypeFld");
				if(select!=null && myArray.length>0){			
					removeSelectOptions(select);
					
					for (var i=0; i< myArray.length; i++){
						//alert(myArray[i]);
						if(myArray[i].length > 0){
							select.options[select.options.length] = new Option(myArray[i], myArray[i], false, false);
						}
					}
				}
			}
		}
	}
	
}
function assignParameters(){
	var assignAllParameters = true;	

	if(checkFields()==false){			
			document.getElementById("hiddenFareType").value = document.getElementById("fareType").value;
			document.getElementById("hiddenPayType").value = document.getElementById("payType").value;
			document.getElementById("hiddenCardNo").value = document.getElementById("cardNoFld").value;
			document.getElementById("hiddenCardType").value = document.getElementById("cardTypeFld").value;
			
			//document.getElementById("hiddenSecFareType").value = document.getElementById("secFareType").value;
			document.getElementById("hiddenSecPayType").value = document.getElementById("secPayType").value;
			document.getElementById("hiddenSecCardNo").value = document.getElementById("secCardNoFld").value;
			document.getElementById("hiddenSecCardType").value = document.getElementById("secCardTypeFld").value;
			document.getElementById("hiddenFirstPayPercent").value = document.getElementById("splitFirstPercentage").value;
			document.getElementById("hiddenSecondPayPercent").value = document.getElementById("splitSecondPercentage").value;
			
			if(document.getElementById("nvaYs")!=null && document.getElementById("nvaYs").checked==true){
				document.getElementById("hiddenNVA").value="Yes";
			}else if(document.getElementById("nvaNo")!=null && document.getElementById("nvaNo").checked==true){
				document.getElementById("hiddenNVA").value="No";
			}
			
	}else{
		alert("Parameters not assigned due to issues");
		assignAllParameters=false;
	}
		return assignAllParameters;
}
function Clicked(obj){		
	if(obj.value=="Back") {
		document.forms["submitForm"].action = "GDSPNRHome.jsp";	
		document.forms["submitForm"].submit();
	}
	if(obj.value=="Home") {
		document.forms["submitForm"].action = "GDSPNRHome.jsp";	
		document.forms["submitForm"].submit();
	}
	if(obj.value=="Generate") {	
		if(assignParameters()==true){	
			document.forms["submitForm"].action = "GenerateTickMesgs.jsp";	
			document.forms["submitForm"].submit();
		}
		
	}
	if(obj.value=="Log Off"){
		if (confirm("Confirm Log Off!") == true) {
			document.forms["submitForm"].action = "LogOff.jsp";	
			document.forms["submitForm"].submit();
		}
	}
}

function checkNum(obj){
	var validValue=true;
	if(isNaN(obj.value)){
		alert("Enter numeric value greater than 0 and less than 100");
		obj.value="";
		validValue = false;
		obj.focus();
	}
	return validValue;
}
</script>
<meta charset="ISO-8859-1">
<title>Ticket Input Page</title>
</head>
<body bgcolor="#778899" onLoad ='onLoadFields();'>
<%
	Connection connection = session.getAttribute("connection")!=null? (Connection)session.getAttribute("connection"):null;
	if (connection==null || connection.isClosed()){
		ConnectToDatabase connDB = new ConnectToDatabase();
		connection = connDB.getConnection();
	}
	Iterator iter, tmpIter;	
	TableDataService tbleDTTrans = new TableDataService(connection);
	HashMap<String, String> cardDtls;
	List<String> cardTypes, cardNos;
	String tempString="", cdTypStr="", cdNoStr="";
	
	out.println("<h1 align='center'>GDS PNR Ticketing messages creation</h1>");
	out.println("<h2 align='left'>Select Payment details</h2>");
	out.println("<br>");
	
	out.println("<table>");
	out.println("<tr><td width = '225px'><label>Split Payment:</label></td>");
	out.println("<td><input type='radio' id='sptPymtYs' name='splitPayment' value='Yes' onClick='displaySecondPayment();' ");
	out.println("<label for='html'>Yes</label>");
	out.println("<input type='radio' id='sptPymtNo' name='splitPayment' value='No' checked='checked' onClick='displaySecondPayment();'>");
	out.println("<label for='css'>No</label>");
	out.println("</td></tr>");
			
	//out.println("<table>");
	out.println("<tr><td><label id='splitPercentageLbl'>Split Percentages:</label></td>");
	out.println("<td><div style='display:flex; flex-direction: row; justify-content: left; align-items: center'>");
	out.println("<input type='text' id='splitFirstPercentage' name='splitFirstPercentage' value='' onBlur='updateSliptPercentage(this);' style='width:45px;'></input>");
	out.print("<label id='splitFirstLbl'>&nbsp;% &nbsp;&nbsp;&nbsp;</label>");	
	out.println("<input type='text' id='splitSecondPercentage' name='splitSecondPercentage' value='' onBlur='updateSliptPercentage(this);' style='width:45px; display: inline-block;'></input> ");
	out.print("<label id='splitSecondLbl'>&nbsp;%</label></div>");
	out.println("</td></tr>");
			
	out.println("<tr><td width = '225px'><label id='fareTypeLbl'> Select the Fare type</label></td>");
	out.println("<td><select id='fareType' name='fareType' style='width:150px;'>");
	out.println("<option>Select</option>");
	out.println("<option>Regular</option>");
	out.println("<option>IT Fares</option>");			
	out.println("</select></td></tr>");
	
	out.print("<tr id='payHeaderOne'><td><label><b>Split Payment - Payment - 1</b></label></td></tr>");
	
	out.println("<tr><td width = '225px'><label id='payTypeLbl'>Select the Payment type</label></td>");
	out.println("<td><select id='payType' name='payType' onchange='displayCardOption(this);' style='width:150px;'>");
	out.println("<option>Select</option>");
	out.println("<option>Bank Transfer</option>");
	out.println("<option>Cash</option>");
	out.println("<option>Credit Card</option>");
	out.println("<option>Debit Card</option>");
	out.println("<option>EMD</option>");
	out.println("<option>Miscellaneous</option>");
	out.println("</select></td></tr>");
	
	out.println("<tr>");
	out.println("<td width = '225px'><label id='selCardLbl'>Select the Card type</label></td>");
	
	cardTypes = tbleDTTrans.getCardTypes("CreditCard");
	
	if(!(cardTypes==null && cardTypes.isEmpty())){
		iter = cardTypes.iterator();
		if(iter.hasNext()){
			cdTypStr="";
			while(iter.hasNext()){
				cdTypStr = cdTypStr+"|"+iter.next().toString();						
			}
			out.println("<input type ='hidden' id = 'hidCreCardTypes' name = 'hidCreCardTypes' value='"+cdTypStr+"'/>");
			
		}
		iter = cardTypes.iterator();
		
		while(iter.hasNext()){	
			tempString = iter.next().toString();
			cardNos = tbleDTTrans.getCardNums("CreditCard",tempString);
			if(cardNos!=null && cardNos.size()>0){
				tmpIter = cardNos.iterator();
				cdNoStr = "";
				while(tmpIter.hasNext()){
					cdNoStr = cdNoStr+"|"+tmpIter.next().toString();						
				}
				out.println("<input type ='hidden' id = 'C"+tempString+"' name = 'C"+tempString+"' value='"+cdNoStr+"'/>");
			}
		}
	}
	
	
	
	cardTypes = tbleDTTrans.getCardTypes("DebitCard");
	
	if(!(cardTypes==null && cardTypes.isEmpty())){
		iter = cardTypes.iterator();
		if(iter.hasNext()){
			cdTypStr="";
			while(iter.hasNext()){
				cdTypStr = cdTypStr+"|"+iter.next().toString();						
			}
			out.println("<input type ='hidden' id = 'hidDebCardTypes' name = 'hidDebCardTypes' value='"+cdTypStr+"'/>");
			
		}
		iter = cardTypes.iterator();		
		while(iter.hasNext()){	
			tempString = iter.next().toString();
			cardNos = tbleDTTrans.getCardNums("DebitCard",tempString);
			if(cardNos!=null && cardNos.size()>0){
				tmpIter = cardNos.iterator();
				cdNoStr = "";
				while(tmpIter.hasNext()){
					cdNoStr = cdNoStr +"|"+tmpIter.next().toString();						
				}
				out.println("<input type ='hidden' id = 'D"+tempString+"' name = 'D"+tempString+"' value='"+cdNoStr+"'/>");				
			}
		}
	}
	out.println("<td><select id='cardTypeFld' name='cardTypeFld' onchange='populateCardNos(this);' style='width:150px;'> ");
	out.println("<option>Select</option>");
	out.println("</select></td></tr>");
	out.println("<tr>");
	

	out.println("<td width = '225px'><label id='cardNoFldLbl'>Select the Card Number</label></td>");
	out.println("<td><select id='cardNoFld' name='cardNoFld' style='width:150px;'>");
	out.println("<option>Select</option>");
	out.println("</select></td>");			
	out.println("</tr>");
	
	
	//Second Payment mode
	/*
	out.println("<tr id='rowFareType'><td width = '225px'><label id='secFareTypeLbl'> Select the Fare type</label></td>");
	out.println("<td><select id='secFareType' name='secFareType'>");
	out.println("<option>Select</option>");
	out.println("<option>Regular</option>");
	out.println("<option>IT Fares</option>");			
	out.println("</select></td></tr>");
	*/
	out.print("<tr id='payHeaderTwo'><td><label><b>Split Payment - Payment - 2</b></label></td></tr>");
	out.println("<tr id='rowPaymentType'><td width = '225px'><label id='secPayTypeLbl'>Select the Payment type</label></td>");
	out.println("<td><select id='secPayType' name='secPayType' onchange='displayCardOption(this);' style='width:150px;'>");
	out.println("<option>Select</option>");
	out.println("<option>Bank Transfer</option>");
	out.println("<option>Cash</option>");
	out.println("<option>Credit Card</option>");
	out.println("<option>Debit Card</option>");
	out.println("<option>EMD</option>");
	out.println("<option>Miscellaneous</option>");
	out.println("</select></td></tr>");
	
	out.println("<tr id='rowCardType'>");
	out.println("<td width = '225px'><label id='secSelCardLbl'>Select the Card type</label></td>");
	
	out.println("<input type ='hidden' id = 'secHidCreCardTypes' name = 'secHidCreCardTypes' value='"+cdTypStr+"'/>");
//	out.println("<input type ='hidden' id = 'secC"+tempString+"' name = 'secC"+tempString+"' value='"+cdNoStr+"'/>");
	
	/*
	cardTypes = tbleDTTrans.getCardTypes("CreditCard");
	
	if(!(cardTypes==null && cardTypes.isEmpty())){
		iter = cardTypes.iterator();
		if(iter.hasNext()){
			cdTypStr="";
			while(iter.hasNext()){
				cdTypStr = cdTypStr+"|"+iter.next().toString();						
			}
			out.println("<input type ='hidden' id = 'secHidCreCardTypes' name = 'secHidCreCardTypes' value='"+cdTypStr+"'/>");
			
		}
		iter = cardTypes.iterator();
		
		while(iter.hasNext()){	
			tempString = iter.next().toString();
			cardNos = tbleDTTrans.getCardNums("CreditCard",tempString);
			if(cardNos!=null && cardNos.size()>0){
				tmpIter = cardNos.iterator();
				cdNoStr = "";
				while(tmpIter.hasNext()){
					cdNoStr = cdNoStr+"|"+tmpIter.next().toString();						
				}
				out.println("<input type ='hidden' id = 'secC"+tempString+"' name = 'secC"+tempString+"' value='"+cdNoStr+"'/>");
			}
		}
	}
	*/
	
	
	out.println("<input type ='hidden' id = 'secHidDebCardTypes' name = 'secHidDebCardTypes' value='"+cdTypStr+"'/>");
//	out.println("<input type ='hidden' id = 'secD"+tempString+"' name = 'secD"+tempString+"' value='"+cdNoStr+"'/>");
	
	/*
	cardTypes = tbleDTTrans.getCardTypes("DebitCard");
	
	if(!(cardTypes==null && cardTypes.isEmpty())){
		iter = cardTypes.iterator();
		if(iter.hasNext()){
			cdTypStr="";
			while(iter.hasNext()){
				cdTypStr = cdTypStr+"|"+iter.next().toString();						
			}
			out.println("<input type ='hidden' id = 'hidDebCardTypes' name = 'hidDebCardTypes' value='"+cdTypStr+"'/>");
			
		}
		iter = cardTypes.iterator();		
		while(iter.hasNext()){	
			tempString = iter.next().toString();
			cardNos = tbleDTTrans.getCardNums("DebitCard",tempString);
			if(cardNos!=null && cardNos.size()>0){
				tmpIter = cardNos.iterator();
				cdNoStr = "";
				while(tmpIter.hasNext()){
					cdNoStr = cdNoStr +"|"+tmpIter.next().toString();						
				}
				out.println("<input type ='hidden' id = 'D"+tempString+"' name = 'D"+tempString+"' value='"+cdNoStr+"'/>");				
			}
		}
	}
	*/
	out.println("<td><select id='secCardTypeFld' name='secCardTypeFld' onchange='populateCardNos(this);' style='width:150px;'> ");
	//onchange='populateCardNos(this);'
	out.println("<option>Select</option>");
	out.println("</select></td></tr>");
	
	out.println("<tr id='rowCardNo'>");
	out.println("<td width = '225px'><label id='secCardNoFldLbl'>Select the Card Number</label>");
	out.println("<td><select id='secCardNoFld' name='secCardNoFld' style='width:150px;'>");
	out.println("<option>Select</option>");
	out.println("</select></td>");			
	out.println("</tr>");
	
	out.println("<tr><td><label>Ignore NVA:</label></td>");
	out.println("<td><input type='radio' id='nvaYs' name='nvaRadio' value='Yes' checked='checked'> ");	
	out.println("<label id='nvaYesLbl'>Yes</label>");
	out.println("<input type='radio' id='nvaNo' name='nvaRadio' value='No'>");	
	out.println("<label id='nvaYesNo'>No</label>");
	out.println("</td></tr>");
			
	out.println("</table>");
	
	
	
	out.println("<form method='post' name = 'submitForm' id ='submitForm'>");
	out.println("<input type ='hidden' id = 'hiddenFareType' name = 'hiddenFareType' value =''/>");
	out.println("<input type ='hidden' id = 'hiddenPayType' name = 'hiddenPayType' value =''/>");	 
	out.println("<input type ='hidden' id = 'hiddenCardNo' name = 'hiddenCardNo' value =''/>");
	out.println("<input type ='hidden' id = 'hiddenCardType' name = 'hiddenCardType' value =''/>");
	
	out.println("<input type ='hidden' id = 'hiddenSplitPayment' name = 'hiddenSplitPayment' value =''/>");
	//out.println("<input type ='hidden' id = 'hiddenSecFareType' name = 'hiddenSecFareType' value =''/>");
	out.println("<input type ='hidden' id = 'hiddenSecPayType' name = 'hiddenSecPayType' value =''/>");	 
	out.println("<input type ='hidden' id = 'hiddenSecCardNo' name = 'hiddenSecCardNo' value =''/>");
	out.println("<input type ='hidden' id = 'hiddenSecCardType' name = 'hiddenSecCardType' value =''/>");
	out.println("<input type ='hidden' id = 'hiddenFirstPayPercent' name = 'hiddenFirstPayPercent' value =''/>");
	out.println("<input type ='hidden' id = 'hiddenSecondPayPercent' name = 'hiddenSecondPayPercent' value =''/>");
	out.println("<input type ='hidden' id = 'hiddenNVA' name = 'hiddenNVA' value =''/>");
	
	out.println("</form>");
	out.println("<br>");
	out.println("<table>");
 	out.println("<tr><td></td>");//<input type='button' value='Back' onClick ='Clicked(this);'>&nbsp;
 	out.println("<td><input type='submit' value='Generate' onClick ='Clicked(this);'>&nbsp;</td>");
 	out.println("<td><input type='button' value='Home' onClick ='Clicked(this);'>&nbsp;</td>"); 	
 	//out.println("<td><input type='button' value='Reset all' onClick ='onLoadFields();' >Reset All</button></td>");
 	out.println("<td><input type='button' value='Log Off' onClick ='Clicked(this);'></td></tr>"); 	
	out.println("</table>");	
%>

</body>
</html>