<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>GDS PNR Messages Generator</title>
<script type ="text/javascript">
function logOffApp(){
	if (confirm("Confirm Log Off!") == true) {
		document.forms["submitForm"].action = "LogOff.jsp";	
		document.forms["submitForm"].submit();
	}
}

function changeHeaderImage(obj){
	document.getElementById("frame1").src=obj;	
}
function reLoadSidePlnFrame(){
	document.getElementById("leftFrame").src="SidePnlFrm.jsp";	
}
</script>

</head>
<body style='overflow:hidden'>
 <!-- <iframe src="frame1.jsp" name="frame1" id="frame1" height ='75px' width ='100%' frameBorder='0'></iframe>-->
 <iframe src="MainImage.jsp" name="frame1" id="frame1" height ='75px' width ='100%' frameBorder='0'></iframe>
 <iframe src="SidePnlFrm.jsp" name="leftFrame" id="leftFrame" height="500px" width ='19%' align="left" frameBorder="1" ></iframe>
 <iframe src="frame2.jsp" name="frame2" height="500px" width ='80%' align="right" frameBorder="1" ></iframe>
 <!-- scrolling="no" -->
<iframe src="CopyRight.jsp" name="frame3" id ="frame3" height ='30px' width ='100%' frameBorder="0" ></iframe>
 
 </body>
</html>