<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.model.*" %>
<%@ page import = "com.gds.pnr.*" %>
<%@ page import = "com.common.*" %>
<%@ page import = "com.utilities.*" %> 
<%@ page import = "com.core.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
 
<!DOCTYPE html>
<html>
<head>
<script type ="text/javascript">
function clicked(obj){
	if(obj.value=="Log Off") {
			document.forms["submitForm"].action = "LogOff.jsp";	
			document.forms["submitForm"].submit();
	}
}
</script>
<meta charset="ISO-8859-1">
<title>Process SSR TKNE</title>
</head>
<body  bgcolor="#778899">
	<%
		try{
			Connection connection = (Connection)session.getAttribute("connection");		
			if (connection==null || connection.isClosed()){
				ConnectToDatabase connDB = new ConnectToDatabase();
				connection = connDB.getConnection();
			}
			Iterator<List> iter;
			Iterator<String> arrIter, intIter;	
			TableDataService tbleDTTrans = new TableDataService(connection);
			HashMap<String, Object> reqParam = new HashMap<String, Object>();
			HashMap<String,List<String>> individualEdifact;
			reqParam = session.getAttribute("tcktMessgParam")!=null?(HashMap<String, Object>)session.getAttribute("tcktMessgParam"):null;
			String hostPNR = session.getAttribute("hostPNR")!=null?	session.getAttribute("hostPNR").toString():null;
			String gdsPNR = session.getAttribute("gdsPNR")!=null?	session.getAttribute("gdsPNR").toString():null;
			List<List> fullTTYMessageList;
			List <String> ttyFragmentList;
			String sharesResponse="", pnrInfo="";
			FileUtility flUtility = new FileUtility();
			
			GDSPNRTTYMsgCreator gdsPNRTTYMsgCreator = new GDSPNRTTYMsgCreator();
			gdsPNRTTYMsgCreator.setGdsPNRTTYInputMap(reqParam);
			gdsPNRTTYMsgCreator.setConn(connection);
			fullTTYMessageList = gdsPNRTTYMsgCreator.getSSRTKNETTYMessage();
			/*
			if(fullTTYMessageList!=null){
				iter=fullTTYMessageList.iterator();
				while(iter.hasNext()){
					arrIter = iter.next().iterator();
					while (arrIter.hasNext()){
						out.println(arrIter.next().toString()+"</br>");
					}
				}
			}*/
			if(fullTTYMessageList!=null){
				ProcessGDSTTYMessage processGDSTTYMessage = new ProcessGDSTTYMessage();				
				processGDSTTYMessage.setEnvURL(CommonProperties.envURL);
				processGDSTTYMessage.setUserID(CommonProperties.logID);
				processGDSTTYMessage.setPassWord(CommonProperties.passWord);
				sharesResponse = processGDSTTYMessage.processTTYMessageInSHARES(fullTTYMessageList);
				if(sharesResponse!=null && (sharesResponse).toUpperCase().startsWith("SYS ACC MSG")){	
					gdsPNR = gdsPNRTTYMsgCreator.getGdsPnrNumber();
					pnrInfo = processGDSTTYMessage.getTTYProcessedPNR(gdsPNR);
					if(pnrInfo!=null && pnrInfo.length()>0){
						if(!pnrInfo.contains("NO MATCH FOUND")){
							out.println("<h1 align='center'>GDS PNR - TKNE SSR(s) Process</h1>");
							out.println("<h2 align='center'>GDS PNR TTY Message with TKNE SSR entries Processed successfully</h2>");
							hostPNR = pnrInfo.substring(0, 6);
							out.println("<div><b>GDS PNR: <b>"+CommonProperties.gdsPNR+"</div><br><br>");
							out.println("<div align='center'><textarea id ='pnrMessage'  name ='pnrMessage' style='font-family:courier; color:black;overflow:scroll;overflow-y:scroll;overflow-x:scroll; width:1200px; height: 200px'>");
							//out.println(pnrInfo.replace("\n", "<br>"));
							out.println(pnrInfo);
							out.println("</textarea></div>");
							flUtility.writePNRToFile("GDS PNR after ticketing and TKNE SSRs adition", pnrInfo);
							processGDSTTYMessage.loggOffIShares();
							processGDSTTYMessage.closeDriver();
						}else{
							flUtility.writePNRToFile("No Response recevied after ticketing and TKNE SSRs adition", pnrInfo);
							processGDSTTYMessage.loggOffIShares();
							processGDSTTYMessage.closeDriver();
							out.println("<h1 align='center'>Message Processed Failed: Please re-process the request</h1>");						
						}
					}else{
						processGDSTTYMessage.loggOffIShares();
						processGDSTTYMessage.closeDriver();
						out.println("<h2 align='center'>PNR not retrieved. Check in aRT tool with PNR - "+CommonProperties.hostPNR+"</h2>");
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
			e.printStackTrace();
		}
		out.println("<form method='POST' name = 'submitForm' action='GDSPNRHome.jsp'>");
		out.println("&nbsp;&nbsp;&nbsp;&nbsp;<br><input type='submit' value='Home' /> &nbsp;");
		out.println("<input type='button' value='Log Off' onClick ='clicked(this);'>"); 	
		out.println("</form>");
	%>
</body>
</html>