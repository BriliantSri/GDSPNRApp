<%@page import="com.gds.pnr.PNRTTYMessageGenerator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%@ page import = "com.model.*" %>
 <%@ page import = "java.util.*" %>
 <%@ page import = "com.gds.pnr.*" %>
 <%@ page import = "java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	try{
		Connection connection = (Connection)session.getAttribute("connection");
		String gdsName = request.getParameter("hiddenGDSName")!=null?	request.getParameter("hiddenGDSName").toString():null;
		int paxCount = request.getParameter("hiddenNoOfPax")!=null?	Integer.parseInt(request.getParameter("hiddenNoOfPax").toString()):0;
		session.setAttribute("paxCount", paxCount);
		HashMap<String, Object> gdsPNRTTYRequestInputs = new HashMap<String, Object>();
		HashMap<Integer, Object> paxDtlsMap = new HashMap<Integer, Object>(); 
		HashMap<String, String> indPaxMap; 
		HashMap<String, String> retIndPaxMap = new HashMap<String, String>(); 
		HashMap<Integer, Object> segMap = new HashMap<Integer, Object>(); 
		HashMap<String, String> eachSegMap;
		HashMap<String, String> retEachSegMap = new HashMap<String, String>();
		String paxType, paxName, infName, paxSurName, paxGivName, chdAge, hasInfant, infSurName, infGivName, infDOB, tempString;
		List<String> ttyMessageList;
		
		
		for (int i=1; i<=paxCount; i++){
			indPaxMap = new HashMap<String, String>();
			tempString = "hiddenPaxType"+i;
			paxType = request.getParameter(tempString)!=null?	request.getParameter(tempString).toString():null;
			tempString = "hiddenPaxSurName"+i;
			paxSurName = request.getParameter(tempString)!=null?	request.getParameter(tempString).toString():null;
			tempString = "hiddenPaxGivName"+i;			
			paxGivName = request.getParameter(tempString)!=null?	request.getParameter(tempString).toString():null;
			paxName = paxSurName+"/"+paxGivName;
			/*Child Age not required
			tempString = "hiddenCHDAge"+i;			
			chdAge = request.getParameter(tempString)!=null?	request.getParameter(tempString).toString():null;
			*/
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
			//Child Age not required
			//indPaxMap.put("chdAge", chdAge);
			indPaxMap.put("hasInfant", hasInfant);
			indPaxMap.put("infSurName", infSurName);
			indPaxMap.put("infGivName", infGivName);
			indPaxMap.put("infDOB", infDOB);
			paxDtlsMap.put(i, indPaxMap);
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
			/*
			tempString = "hiddenDOJDay"+i;
			dojDay = request.getParameter(tempString)!=null?	request.getParameter(tempString).toString():null;
			if (dojDay.length()==1){
				dojDay = "0"+dojDay;
			}
			tempString = "hiddenDOJMonth"+i;
			dojMonth = request.getParameter(tempString)!=null?	request.getParameter(tempString).toString():null;
			*/
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
		}
		String fareType = request.getParameter("hiddenFareType")!=null?	request.getParameter("hiddenFareType").toString():null;
		String payType = request.getParameter("hiddenPayType")!=null?	request.getParameter("hiddenPayType").toString():null;
		HashMap<String, String> paxMap = new HashMap<String, String>();
		Iterator<String> iter, iter1;
		/*
		if(connection!=null && !connection.isClosed()){
			PaxTblMgr paxTblMgr = new PaxTblMgr(connection);
			paxMap = paxTblMgr.getPaxDetails();
		    
			if (paxMap!=null){
				PNRTTYMessageGenerator pnrTTyMessage = new PNRTTYMessageGenerator();
				out.println(pnrTTyMessage.getPNRCreateTTYMessage());
			}
		}*/
		/*
		int mapSize = paxDtlsMap.size();
		int i=1;
		out.println("</br></br></br>");
		out.println("<label color='Blue'>Pax Details</label>");
		out.println("<table>");
		while(i<=mapSize){
			retIndPaxMap = (HashMap<String, String>)paxDtlsMap.get(Integer.valueOf(i));
			out.println("<tr><td width = '225px'><label><b>Passenger - "+i+" - Details</b></label</td><td>");
			out.println("<tr><td><label>Pax Type</b></label</td>"+retIndPaxMap.get("paxType")+"<td>");
			out.println("<tr><td><label>Passenger Surname</b></label</td>"+retIndPaxMap.get("paxSurName")+"<td>");
			out.println("<tr><td><label>Passenger Given Name</b></label</td>"+retIndPaxMap.get("paxGivName")+"<td>");
			out.println("<tr><td><label>Has Infant</b></label</td>"+retIndPaxMap.get("hasInfant")+"<td>");
			out.println("<tr><td><label>Infant Surname</b></label</td>"+retIndPaxMap.get("infSurName")+"<td>");
			out.println("<tr><td><label>Infant Given Name</b></label</td>"+retIndPaxMap.get("infGivName")+"<td>");
			i++;
		}
		mapSize = segMap.size();
		i=1;
		while(i<=mapSize){
			retEachSegMap = (HashMap<String, String>)segMap.get(Integer.valueOf(i));
			out.println("<tr><td width = '225px'><label><b>Segment - "+i+" - Details</b></label</td><td>");
			out.println("<tr><td><label>Airline Code</b></label</td>"+retEachSegMap.get("airlineCode")+"<td>");
			out.println("<tr><td><label>Flight Number</b></label</td>"+retEachSegMap.get("fltNo")+"<td>");
			out.println("<tr><td><label>COS Code</b></label</td>"+retEachSegMap.get("cosCode")+"<td>");
			out.println("<tr><td><label>Origination</b></label</td>"+retEachSegMap.get("org")+"<td>");
			out.println("<tr><td><label>Destination</b></label</td>"+retEachSegMap.get("des")+"<td>");
			out.println("<tr><td><label>Dateofjourney</b></label</td>"+retEachSegMap.get("DtOfJrny")+"<td>");
			i++;
		}
		out.println("</table>");
		out.println("</br></br></br>");
		*/
		gdsPNRTTYRequestInputs.put("gdsName", gdsName);
		gdsPNRTTYRequestInputs.put("noOfPax", Integer.valueOf(paxCount));
		gdsPNRTTYRequestInputs.put("noOfsegments", Integer.valueOf(noOfSeg));
		gdsPNRTTYRequestInputs.put("paxMap", paxDtlsMap);
		gdsPNRTTYRequestInputs.put("segMap", segMap);
		gdsPNRTTYRequestInputs.put("fareType", fareType);
		gdsPNRTTYRequestInputs.put("paymentType", payType);
		
		PNRTTYMessageGenerator pnrTTYMessageGenerator = new PNRTTYMessageGenerator();
		pnrTTYMessageGenerator.setGdsPNRTTYInputMap(gdsPNRTTYRequestInputs);
		pnrTTYMessageGenerator.setConn(connection);
		ttyMessageList = pnrTTYMessageGenerator.getPNRCreateTTYMessage();
		if(ttyMessageList!=null){
			iter = ttyMessageList.iterator();
			while (iter.hasNext()){
				out.println(iter.next()+"</br>");
			}
		}else{
			out.println("<h1 align='center'>Message not generated</h1>");
		}
		//out.println(pnrTTYMessageGenerator.getPNRCreateTTYMessage());
		
	}catch(Exception e){
		throw e;
	}
%>



</body>
</html>