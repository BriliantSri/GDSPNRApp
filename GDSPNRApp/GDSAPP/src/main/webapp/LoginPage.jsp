<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import = "com.common.*" %>
 <%@ page import = "com.model.*" %>
 <%@ page import = "java.util.*" %>
 <%@ page import = "java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<script language ='javascript' type ="text/javascript">

function assignParameters(){	
	if(checkFields()==true){
		if(document.getElementById("envName")!=null){
			document.getElementById("hiddenEnv").value = document.getElementById("envName").value;		
		}
		
		if(document.getElementById("userID")!=null){
			document.getElementById("hiddenUserID").value = document.getElementById("userID").value;
		}
		if(document.getElementById("passWrd")!=null){
			document.getElementById("hiddenPassword").value = document.getElementById("passWrd").value;
		}
		doLoadSpinner();
		return true;
	}else{
		return false;
	}
}
function checkFields(){
	var verifiedFields=true;
	var errorMessage="";
	var i=0;
	if(document.getElementById("envName").value=="Select"){
		errorMessage+= (i+1) +". Environment: Select Environment. \n";
		verifiedFields=false;	
		i++;
	}
	if(document.getElementById("userID").value.trim().length==0){
		errorMessage+= (i+1) +". User Name: Enter User name. \n";
		verifiedFields=false;	
		i++;
	}
	if(document.getElementById("passWrd").value.trim().length==0){
		errorMessage+= (i+1) +". Password: Enter Password.";
		verifiedFields=false;
		i++;
	}
	if(verifiedFields==false){
		alert(errorMessage);
	}
	return verifiedFields;
}
function resetFields(){
	if(document.getElementById("envName")!=null){
	 	document.getElementById("envName").selectedIndex = 0;		 	
	}
	if(document.getElementById("userID")!=null){
		document.getElementById("userID").value="";
	}
	if(document.getElementById("passWrd")!=null){
		document.getElementById("passWrd").value="";
		
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
function onLoadfunction(){
	doNotLoadSpinner();
	parent.reLoadSidePlnFrame();
	parent.changeHeaderImage("MainImage.jsp")
}


</script>
<style>
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

<meta charset="ISO-8859-1">
<title>Login Page</title>
</head>

<h3 align='center'>Login Page (Internally verifies i-SHARES Access)</h3>
<body bgcolor="#778899" onLoad ='onLoadfunction();'>
<p id="gfg"></p>
<%

out.println("<div id='lblHeader'><h2 align='center'>Verifying Credentials..... </h2> </div>");
out.println("<div class= 'center' id='divSpin'><div class='loader' ></div></div>");
out.println("<div id='fulScreen'>"); 
//Previous header
//i-SHARES Access verification

//Deleted from Form tag
		//onclick=" return generateFile();"
		Connection connection = session.getAttribute("connection")!=null? (Connection)session.getAttribute("connection"):null;
		if (connection==null || connection.isClosed()){
			ConnectToDatabase connDB = new ConnectToDatabase();
			connection = connDB.getConnection();
		}
		Iterator iter;
		boolean userAlreadyVerified = false;
		if(CommonProperties.validUser==true){
			if(CommonProperties.envURL!=null && CommonProperties.envURL.length()>0){
				
				if(CommonProperties.logID!=null && CommonProperties.logID.length()>0){
					
					if(CommonProperties.passWord!=null && CommonProperties.passWord.length()>0){
						userAlreadyVerified=true;
					}
				}
			}
		}
		
		if(userAlreadyVerified){
			//RequestDispatcher requestDispactcher =  request.getRequestDispatcher("/GDSPNRHome.jsp");
			RequestDispatcher requestDispactcher =  request.getRequestDispatcher("/MainPage.jsp");
			requestDispactcher.forward(request, response);
		}else{
			if(session!=null && session.getAttribute("logOffMessage")!=null && (boolean)session.getAttribute("logOffMessage")==true){
				out.println("<h4 align='center'> Successfully logged off.</h4>");
				session.setAttribute("logOffMessage", false);
			}
			TableDataService tbleDTTrans = new TableDataService(connection);
			Vector envNamesVector = tbleDTTrans.getEnvNames();
			
	
			out.println("<table>");
			out.println("<tr><td width = '130px'><label id = 'envNameLbl'>Environment:</label></td>");
			out.println("<td><select id ='envName' name ='envName' style='width:150px;'><option>Select</option>");
			if(envNamesVector!=null && envNamesVector.size()>0){
				iter = envNamesVector.iterator();
				while(iter.hasNext()){
					out.println ("<option>"+iter.next()+"</option>");
				}
			}else{
			
				out.println ("<option>TPFSA (UA - Test)</option>");
				out.println ("<option>TPFSB (CM - Test)</option>");
				out.println ("<option>VPSHA (UA - Stage)</option>");
				out.println ("<option>VPSHB (CM - Stage)</option>");
				out.println ("<option>SHA (UA - PROD)</option>");
				out.println ("<option>SHB (CM - PROD)</option>");
			}
					out.println("</Select></td></tr>");
					out.println("</table>");
					out.println("<br>");
			out.println("<table>");
			
			out.println("<tr><td><label color='red' name=message></label></td></tr>");
			out.println("<tr><td><label><b>Enter credential of i-SHARES application</b></label></td></tr>");
			if(request.getParameter("hiddenEnv")!=null){
				//out.println(request.getParameter("message").toString());
				out.println("<tr><td><label style='color:red' name=message>"+request.getParameter("message").toString()+"</label></td></tr>");
			} 
			out.println("</table>");
			out.println("<table>");
			out.println("<tr><td></td><td></td></tr>");
			out.println("<tr><td width = '130px'><label id = 'logInIDLbl' >User Name: </label></td>");
			out.println("<td><input type='text' id='userID' name='userID' style='width:150px;' placeholder='User Name'></td></tr>");
			//style='text-align:right;'
			out.println("<tr><td><label id = 'passwordLbl'>Password: </label></td>");
			out.println("<td><input type='password' id='passWrd' name='passWrd' style='width:150px;' placeholder='Password'></td></tr>");
			out.println("</table>");
			
			
			out.println("<table><tr><td width = '130px'></td><td>");
			out.println("<form method='POST' id='submitForm' onclick = 'return assignParameters();' action='VerifyValidCredntials.jsp'>");
			out.println("<input type ='hidden' id = 'hiddenEnv' name = 'hiddenEnv' value =''/>");
		    out.println("<input type ='hidden' id = 'hiddenUserID' name = 'hiddenUserID' value =''/>");
		    out.println("<input type ='hidden' id = 'hiddenPassword' name = 'hiddenPassword' value =''/>");
		    //out.println("</td><td>");
			out.println("<div id ='submitDiv' align='center'>");
			out.println("<input type='submit' id ='logIn' value='Log In' style='width:75px'>");	
			out.println("</div>");
			out.println("</form></td>");
			out.println("<td><input type='button' id='reset' value='Reset' style='width:75px' onclick='resetFields();'></td></tr>");			
			out.println("</table>");
			
		}
		out.println("</div>"); 
		
%>

</body>
<script>
//var inputText = document.getElementById("passWrd");
   document.body.addEventListener("keyup", function(event) {
      if (event.keyCode === 13 ) {
    	  //document.getElementById("reset") ===
    	  var acEle =  document.activeElement;
    	  
    	  if(acEle.id!="reset" && assignParameters()==true){			    		  
    		  document.forms["submitForm"].submit(); 
    	  }
      }
   });
	
</script>	
</html>