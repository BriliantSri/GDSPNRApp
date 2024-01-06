<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import = "com.model.*" %>
 <%@ page import = "java.util.*" %>
 <%@ page import = "java.sql.*" %>
  <%@ page import = "com.core.*" %>
<!DOCTYPE html>
<html>
<head>
<script language ='javascript' type ="text/javascript">
function logOffApp(){
		if (confirm("Confirm Log Off!") == true) {
			document.forms["submitForm"].action = "LogOff.jsp";	
			document.forms["submitForm"].submit();
		}
	}
	
function clicked(obj){
	if(obj.value=="Home") {
		document.forms["submitForm"].action = "GDSPNRHome.jsp";	
		document.forms["submitForm"].submit();
	}
}
</script>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body bgcolor="#778899">
	<%
	Connection connection = (Connection)session.getAttribute("connection");
	if (connection==null || connection.isClosed()){
		ConnectToDatabase connDB = new ConnectToDatabase();
		connection = connDB.getConnection();
	}
	Iterator iter, arrIter, intIter;	
	TableDataService tbleDTTrans = new TableDataService(connection);
	
	String envURL = session.getAttribute("envURL")!=null? session.getAttribute("envURL").toString():"";
	String loginID = session.getAttribute("logUserId")!=null? session.getAttribute("logUserId").toString():"";
	String pwd = session.getAttribute("logPassword")!=null? session.getAttribute("logPassword").toString():"";	
	String fareType = request.getParameter("hiddenFareType")!=null?	request.getParameter("hiddenFareType").toString():null;
	String payType = request.getParameter("hiddenPayType")!=null?	request.getParameter("hiddenPayType").toString():null;
	String cardNo = request.getParameter("hiddenCardNo")!=null?	request.getParameter("hiddenCardNo").toString():"";
	String cardType = request.getParameter("hiddenCardType")!=null?	request.getParameter("hiddenCardType").toString():"";
	
	String splitPayment = request.getParameter("hiddenSplitPayment")!=null?	request.getParameter("hiddenSplitPayment").toString():"";
	//String secFareType = request.getParameter("hiddenSecFareType")!=null?	request.getParameter("hiddenSecFareType").toString():null;
	String secPayType = request.getParameter("hiddenSecPayType")!=null?	request.getParameter("hiddenSecPayType").toString():null;
	String secCardNo = request.getParameter("hiddenSecCardNo")!=null?	request.getParameter("hiddenSecCardNo").toString():"";
	String secCardType = request.getParameter("hiddenSecCardType")!=null?	request.getParameter("hiddenSecCardType").toString():"";
	String firstPayPercent = request.getParameter("hiddenFirstPayPercent")!=null?	request.getParameter("hiddenFirstPayPercent").toString():"";
	String secondPayPercent = request.getParameter("hiddenSecondPayPercent")!=null?	request.getParameter("hiddenSecondPayPercent").toString():"";
			
	
	String nvaIgnore = request.getParameter("hiddenNVA")!=null?	request.getParameter("hiddenNVA").toString():"";
	
	session.setAttribute("envURL", envURL);
	session.setAttribute("logUserId", loginID);
	session.setAttribute("logPassword", pwd);
	session.setAttribute("connection", connection);
	HashMap<String, Object> reqParam = new HashMap<String, Object>();
	HashMap<String,List<String>> individualEdifact;
	reqParam = session.getAttribute("tcktMessgParam")!=null?(HashMap<String, Object>)session.getAttribute("tcktMessgParam"):null;
	String hostPNR = session.getAttribute("hostPNR")!=null?	session.getAttribute("hostPNR").toString():null;
	String gdsPNR = session.getAttribute("gdsPNR")!=null?	session.getAttribute("gdsPNR").toString():null;
	int noOfMsgs, i;
	reqParam.put("hostPNR", hostPNR);
	reqParam.put("gdsPNR", gdsPNR);
	reqParam.put("logUserId", loginID);
	reqParam.put("logPassword", pwd);
	reqParam.put("envURL", envURL);
	reqParam.put("cardNo", cardNo);
	reqParam.put("cardType", cardType);
	reqParam.put("secCardNo", secCardNo);
	reqParam.put("secCardType", secCardType);
	
	EdifactTickMsg edifactTickMsg = new EdifactTickMsg(connection);
	edifactTickMsg.setTickMsgParam(reqParam);
	edifactTickMsg.setFareType(fareType);
	edifactTickMsg.setPaymentType(payType);
	//edifactTickMsg.setSecFareType(secFareType);
	edifactTickMsg.setSecPaymentType(secPayType);
	
	if(splitPayment.length()>0 && splitPayment.equalsIgnoreCase("Yes")){
		edifactTickMsg.setSplitPayment(true);
		edifactTickMsg.setSplitFirstPayPercent(firstPayPercent);
		edifactTickMsg.setSplitSecondPayPercent(secondPayPercent);
	}else{
		edifactTickMsg.setSplitPayment(false);
	}
	
	edifactTickMsg.setNvaIgnore(nvaIgnore);
	
	HashMap<String, HashMap<String,List<String>>> edifactMsgMap = edifactTickMsg.generateEdifactMessages();
	List<String> edifactTckList;
	if (edifactMsgMap!=null && edifactMsgMap.size()>0){
		iter = edifactMsgMap.entrySet().iterator();
		out.println("<h1 align='center'>GDS Ticketing EDIFACT messages</h1>");
		out.println("<h3 align=center>Activate Loadset and run the GDS Ticketing EDIFACT messages in aRT Tool</h3>");		
		//out.println("Activate Loadset before running the GDS Ticketing EDIFACT messages.</h3>");
		out.println("</br>");
		Map.Entry<String, HashMap<String, List<String>>> entry;
		Map.Entry<String, List<String>> inEntry;
		i=1;
		while (iter.hasNext()){
			
			entry = (Map.Entry<String, HashMap<String,List<String>>>)iter.next();
            System.out.println("Key = " + entry.getKey() + 
                                ", Value = " + entry.getValue());
			individualEdifact = (HashMap<String, List<String>>)entry.getValue();
			
			if(individualEdifact!=null){
				intIter = individualEdifact.entrySet().iterator();
				noOfMsgs = individualEdifact.size();
				while(intIter.hasNext()){
					inEntry = (Map.Entry)intIter.next();
					
					edifactTckList = (ArrayList<String>)inEntry.getValue();
					//out.println("<textarea width = '600px' style='font-family:courier; color:red;' value = '' >");
					
					if(noOfMsgs>1){
						out.println("</br><b>Edifact Message - "+i+"</b></br></br>");
					}
					else{
						//out.println("<b>Edifact Message<br><br>");
						out.println("<br><b>Edifact Message</b><br><br>");
					}
						out.println("<textarea id ='edifactMsg'  name ='edifactMsg"+i+"' style='font-family:courier; color:red;overflow:scroll;overflow-y:scroll;overflow-x:scroll; width:1200px; height: 200px'>");
					arrIter = edifactTckList.iterator();
					while(arrIter.hasNext()){
						//out.println(arrIter.next()+"<br>");
						out.println(arrIter.next());
					}	
					out.println("</textarea>");
					out.println("</br>");
					i++;
				}
				out.println("</br>");
			}else{
				out.println("<h2 align=center> Issue occurred in creating Edifact message(s)</h2>");
			}
		}
	} 
	out.println("<form method='POST' name ='submitForm' action='ProcessSSRTKNE.jsp'>");
	out.println("<input type='submit' value='Process TKNE SSR' />&nbsp;");
	out.println("<input type='button' value='Home' onClick ='clicked(this);'>&nbsp;");
	out.println("<input type='button' value='Log Off' onClick ='logOffApp();'>"); 	
	out.println("</form>");
	
	%>
	
</body>
</html>