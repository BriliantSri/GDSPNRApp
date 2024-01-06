<%@page import="com.gds.pnr.PNRTTYMessageGenerator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import = "com.common.*" %>    
 <%@ page import = "com.model.*" %>
 <%@ page import = "com.core.*" %>
  <%@ page import = "com.gds.pnr.*" %>
  <%@ page import = "com.utilities.*" %> 
 <%@ page import = "java.util.*" %>
 <%@ page import = "java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<script type ="text/javascript">
function Clicked(obj){		
	if(obj.value=="Back") {
		document.forms["submitForm"].action = "GDSPNRHome.jsp";	
		document.forms["submitForm"].submit();
	}
	if(obj.value=="Home") {
		document.forms["submitForm"].action = "GDSPNRHome.jsp";	
		document.forms["submitForm"].submit();
	}
	if(obj.value=="Generate Ticketing Messages") {
		document.forms["submitForm"].action = "EdifactTicketInputPage.jsp";	
		document.forms["submitForm"].submit();
	}if(obj.value=="Log Off") {
		document.forms["submitForm"].action = "LogOff.jsp";	
		document.forms["submitForm"].submit();
	}
}
function assignParameters(){
	var assignAllParameters = true;	
	if(checkFields()==true){				
			document.getElementById("hiddenFareType").value = document.getElementById("fareType").value;
			document.getElementById("hiddenPayType").value = document.getElementById("payType").value;
				
	}else{
		assignAllParameters=false;
	}
		return assignAllParameters;
}
function checkFields(){
	var checkFields = false;	
	if(document.getElementById("fareType")!=null){
	 	if(document.getElementById("fareType").value=='Select'){
	 		errorMessageString += (cnt+1) + ". Select the Fare Type\n\n";
	 		cnt++;
		 	document.getElementById("fareTypeLbl").style.color ='Red';
		 	incorrectValueSelected = true;		 
		}else{
			document.getElementById("fareTypeLbl").style.color ='Black';
			if(document.getElementById("payType")!=null){
			 	if(document.getElementById("payType").value=='Select'){
			 		errorMessageString += (cnt+1) + ". Select the Payment Type\n\n";
			 		cnt++;
				 	document.getElementById("payTypeLbl").style.color ='Red';
				 	incorrectValueSelected = true;		 
				}else{
					document.getElementById("payTypeLbl").style.color ='Black';
					checkFields = true;
				}
			}
		}
	}
	return checkFields;
}
</script>
<meta charset="ISO-8859-1">
<title>EDIFACT Ticket Message(s)</title>
</head>
<body  bgcolor="#778899" >
<%
	boolean disGeneTCKMessBtn = false;
	try{
		Connection connection = (Connection)session.getAttribute("connection");
		String envURL = session.getAttribute("envURL")!=null? session.getAttribute("envURL").toString():"";
		String loginID = session.getAttribute("logUserId")!=null? session.getAttribute("logUserId").toString():"";
		String pwd = session.getAttribute("logPassword")!=null? session.getAttribute("logPassword").toString():"";
		String gdsName = request.getParameter("hiddenGDSName")!=null?	request.getParameter("hiddenGDSName").toString():null;
		session.setAttribute("envURL", envURL);
		session.setAttribute("logUserId", loginID);
		session.setAttribute("logPassword", pwd);
		session.setAttribute("connection", connection);
		int paxCount = request.getParameter("hiddenNoOfPax")!=null?	Integer.parseInt(request.getParameter("hiddenNoOfPax").toString()):0;
		session.setAttribute("paxCount", paxCount);
		HashMap<String, Object> gdsPNRTTYRequestInputs = new HashMap<String, Object>();
		HashMap<Integer, HashMap<String, String>> paxDtlsMap = new HashMap<Integer, HashMap<String, String>>(); 
		HashMap<String, String> indPaxMap; 
		HashMap<String, String> retIndPaxMap = new HashMap<String, String>(); 
		HashMap<Integer, HashMap<String, String>> segMap = new HashMap<Integer, HashMap<String, String>>(); 
		HashMap<String, String> eachSegMap;
		HashMap<String, String> retEachSegMap = new HashMap<String, String>();
		String paxType, paxName, infName, paxSurName, paxGivName, chdAge, hasInfant, infSurName, infGivName, infDOB, tempString;
		String sharesResponse, pnrInfo;
		List<List> fullTTYMessageList;
		List <String> ttyFragmentList;
		HashMap<String, String> creditCardDtls;
		HashMap<String, String> debitCardDtls;
		String hostPNR, gdsPNR;
		CommonProperties.passengerMap = new HashMap();
		CommonProperties.segmentMap = new HashMap();
		FileUtility flUtility = new FileUtility();
		
		for (int i=1; i<=paxCount; i++){
			indPaxMap = new HashMap<String, String>();
			tempString = "hiddenPaxType"+i;
			paxType = request.getParameter(tempString)!=null?	request.getParameter(tempString).toString():null;
			tempString = "hiddenPaxSurName"+i;
			paxSurName = request.getParameter(tempString)!=null?	request.getParameter(tempString).toString():null;
			tempString = "hiddenPaxGivName"+i;			
			paxGivName = request.getParameter(tempString)!=null?	request.getParameter(tempString).toString():null;
			paxName = paxSurName+"/"+paxGivName;			
			tempString = "hiddenHasInfant"+i;
			hasInfant = request.getParameter(tempString)!=null?request.getParameter(tempString).toString():null;
			tempString = "hiddenSurINFName"+i;
			infSurName = request.getParameter(tempString)!=null?	request.getParameter(tempString).toString():"NA";
			tempString = "hiddenGivINFName"+i;
			infGivName = request.getParameter(tempString)!=null?	request.getParameter(tempString).toString():"NA";
			infName = infSurName+"/"+infGivName;
			tempString = "hiddenINFDOB"+i;			
			infDOB = request.getParameter(tempString)!=null?	request.getParameter(tempString).toString():null;
			
			indPaxMap.put("paxType", paxType);
			indPaxMap.put("paxSurName", paxSurName);
			indPaxMap.put("paxGivName", paxGivName);			
			indPaxMap.put("hasInfant", hasInfant);
			indPaxMap.put("infSurName", infSurName);
			indPaxMap.put("infGivName", infGivName);
			indPaxMap.put("infDOB", infDOB);
			paxDtlsMap.put(i, indPaxMap);
			//CommonProperties
			CommonProperties.passengerMap.put(i, indPaxMap);
		}
		
		
		int noOfSeg = request.getParameter("hiddenNoOfSeg")!=null? Integer.parseInt(request.getParameter("hiddenNoOfSeg").toString()):0;
		String airlineCode, fltNo, cosCode,org, des, doj, dojDay, dojMonth, DtOfJrny;
		for (int i=1; i<=noOfSeg; i++){
			eachSegMap = new HashMap<String, String>();
			tempString = "hiddenAirLineCode"+i;
			airlineCode = request.getParameter(tempString)!=null?	request.getParameter(tempString).toString():null;
			tempString = "hiddenFltNo"+i;
			fltNo = request.getParameter(tempString)!=null?	request.getParameter(tempString).toString():null;
			tempString = "hiddenCOS"+i;			
			cosCode = request.getParameter(tempString)!=null?	request.getParameter(tempString).toString():null;
			tempString = "hiddenORG"+i;
			org = request.getParameter(tempString)!=null?	request.getParameter(tempString).toString():null;
			tempString = "hiddenDES"+i;
			des = request.getParameter(tempString)!=null?	request.getParameter(tempString).toString():null;
			
			tempString = "hiddenDOJ"+i;
			doj = request.getParameter(tempString)!=null?	request.getParameter(tempString).toString():null;
			DtOfJrny = doj.substring(0,doj.length()-2);
			
			eachSegMap.put("airlineCode", airlineCode);
			eachSegMap.put("fltNo", fltNo);			
			eachSegMap.put("cosCode", cosCode);
			eachSegMap.put("org", org);
			eachSegMap.put("des", des);
			eachSegMap.put("DtOfJrny", DtOfJrny);
			
			segMap.put(i,eachSegMap);
			//Common Properties
			CommonProperties.segmentMap.put(i, eachSegMap);
		}
		
		//String fareType = request.getParameter("hiddenFareType")!=null?	request.getParameter("hiddenFareType").toString():null;
		//String payType = request.getParameter("hiddenPayType")!=null?	request.getParameter("hiddenPayType").toString():null;
		HashMap<String, String> paxMap = new HashMap<String, String>();
		Iterator<List> iter;
		Iterator<String> iter1;		
		gdsPNRTTYRequestInputs.put("gdsName", gdsName);
		gdsPNRTTYRequestInputs.put("noOfPax", Integer.valueOf(paxCount));
		gdsPNRTTYRequestInputs.put("noOfsegments", Integer.valueOf(noOfSeg));
		gdsPNRTTYRequestInputs.put("paxMap", paxDtlsMap);
		gdsPNRTTYRequestInputs.put("segMap", segMap);
		//gdsPNRTTYRequestInputs.put("fareType", fareType);
		//gdsPNRTTYRequestInputs.put("paymentType", payType);
		
		GDSPNRTTYMsgCreator gdsPNRTTYMsgCreator = new GDSPNRTTYMsgCreator();
		gdsPNRTTYMsgCreator.setGdsPNRTTYInputMap(gdsPNRTTYRequestInputs);
		gdsPNRTTYMsgCreator.setConn(connection);
		fullTTYMessageList = gdsPNRTTYMsgCreator.getPNRCreateTTYMessage();
		if(fullTTYMessageList!=null){
			ProcessGDSTTYMessage processGDSTTYMessage = new ProcessGDSTTYMessage();
			//processGDSTTYMessage.setEnvURL("https://tpfsb.svcs.entsvcs.net");
			//processGDSTTYMessage.setUserID("qzsvz9");
			//processGDSTTYMessage.setPassWord("Dxc@67890");
			processGDSTTYMessage.setEnvURL(envURL);
			processGDSTTYMessage.setUserID(loginID);
			processGDSTTYMessage.setPassWord(pwd);
			sharesResponse = processGDSTTYMessage.processTTYMessageInSHARES(fullTTYMessageList);
			if(sharesResponse!=null && (sharesResponse).toUpperCase().startsWith("SYS ACC MSG")){	
				gdsPNR = gdsPNRTTYMsgCreator.getGdsPnrNumber();
				pnrInfo = processGDSTTYMessage.getTTYProcessedPNR(gdsPNR);
				if(pnrInfo!=null && pnrInfo.length()>0){
					if(!pnrInfo.contains("NO MATCH FOUND")){
						out.println("<h1 align='center'>GDS PNR TTY Message Processed successfully</h1>");
						hostPNR = pnrInfo.substring(0, 6);
						out.println("<div align='center'><b>GDS System: </b>"+gdsName+"&emsp;<b>GDS PNR: </b>"+gdsPNR+"&emsp;<b>HOST System: </b>COPA Airlines (CM)&emsp;<b>COPA PNR: </b>"+hostPNR+"</div><br>");
						out.println("<div align='center'><textarea id ='pnrMessage'  name ='pnrMessage' style='font-family:courier; color:black;overflow:scroll;overflow-y:scroll;overflow-x:scroll; width:1200px; height: 200px'>");
						//out.println(pnrInfo.replace("\n", "<br>"));
						out.println(pnrInfo);
						out.println("</textarea></div>");
						processGDSTTYMessage.loggOffIShares();
						processGDSTTYMessage.closeDriver();
						flUtility.writePNRToFile("GDS PNR before ticketing", pnrInfo);
						session.setAttribute("tcktMessgParam", gdsPNRTTYRequestInputs);
						session.setAttribute("hostPNR", hostPNR);
						session.setAttribute("gdsPNR", gdsPNR);
						//CommonProperties
						CommonProperties.gdsPNR = gdsPNR;
						CommonProperties.hostPNR = hostPNR;
						disGeneTCKMessBtn = true;
						if(disGeneTCKMessBtn){
							out.println("<br>");
							out.println("<br>");
						
						}
					}else{
						flUtility.writePNRToFile("GDS PNR not processed - Response", pnrInfo);
						processGDSTTYMessage.loggOffIShares();
						processGDSTTYMessage.closeDriver();
						out.println("<h1 align='center'>Message Processed Failed: Please re-process the request</h1>");						
					}
				}else{
					processGDSTTYMessage.loggOffIShares();
					processGDSTTYMessage.closeDriver();
					out.println("<h2 align='center'>PNR not retrieved. Check in aRT tool with GDS PNR - "+gdsPNRTTYMsgCreator.getGdsPnrNumber()+"</h2>");
				}
			}else{
				processGDSTTYMessage.loggOffIShares();
				processGDSTTYMessage.closeDriver();
				out.println("<h1 align='center'>Message Processed Failed: Please re-process the request</h1>");
			}
			
		}else{			
			out.println("<h1 align='center'>Message not generated</h1>");
		}
		
		
	}catch(Exception e){
		throw e;
	}
	out.println("<form method='post' name = 'submitForm' id ='submitForm'>");
	out.println("<input type ='hidden' id = 'hiddenFareType' name = 'hiddenFareType' value =''/>");
	out.println("<input type ='hidden' id = 'hiddenPayType' name = 'hiddenPayType' value =''/>");	 
	out.println("</form>");
	out.println("<br>");
	out.println("<table>");
 	//out.println("<tr><td><input type='button' style='width:125px' value='Back' onClick ='Clicked(this);'>&nbsp;</td>");
 	
 	out.println("<td><input type='button' width = '225px' value='Home' onClick ='Clicked(this);'>&nbsp;</td>");
 	//out.println("</table>");	
 	//out.println("<table>");
 	if(disGeneTCKMessBtn==true){
 		//out.println("<tr>");
 		out.println("<td><input type='button' value='Generate Ticketing Messages' onClick ='Clicked(this);'></td>");
 		 
 		//out.println("</tr>");
 	}
 	out.println("<td><input type='button' value='Log Off' onClick ='Clicked(this);'><td>");
	out.println("</tr></table>");	
%>



</body>
</html>