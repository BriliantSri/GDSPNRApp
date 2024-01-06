<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.model.*" %>
 <%@ page import = "java.util.*" %>
 <%@ page import = "java.sql.*" %>

<!DOCTYPE html>
<html>
<head>
<script language ='javascript' type ="text/javascript">
var onLoad=false;
function onLoadFields(){
	//alert("inLoadFields");
	onLoad=true;
	collapsePaxFields(1);
	collapseFltDtlFields();
	resetFlds(0);
	prevPaxSelection = 1;
	onLoad=false;
	doNotLoadSpinner();
}
function collapsePaxFields(num){
	var i, paxCount;
	var cntr = parseInt(num);
	paxCount = document.getElementById("hiddenPaxCount").value;
	
	//alert("paxCount: "+paxCount);
	for (i=1; i<=paxCount; i++){	
		if (i>cntr){			
			if(document.getElementById("paxDtlsRow"+i)!=null){
			 	document.getElementById("paxDtlsRow"+i).style.visibility='collapse';
			 	//document.getElementById("paxDtlsRow"+i).className='hideRow';	
			}
			if(document.getElementById("adultType"+i)!=null){
			 	document.getElementById("adultType"+i).checked=false;
			}
			
			if(document.getElementById("paxTypeRow"+i)!=null){
			 	document.getElementById("paxTypeRow"+i).style.visibility='collapse';
			 	//infantName"+i
			}
			if(document.getElementById("paxSurNameRow"+i)!=null){
			 	document.getElementById("paxSurNameRow"+i).style.visibility='collapse';
			 	//infantName"+i
			}
			if(document.getElementById("paxGivNameRow"+i)!=null){
			 	document.getElementById("paxGivNameRow"+i).style.visibility='collapse';
			 	//infantName"+i
			}
			if(document.getElementById("checkInfant"+i)!=null){
			 	document.getElementById("checkInfant"+i).style.visibility='collapse';
			 	//infantName"+i
			}
			if(document.getElementById("infantSurName"+i)!=null){
			 	document.getElementById("infantSurName"+i).style.visibility='collapse';
			 	//infantName"+i
			}
			if(document.getElementById("infantGivName"+i)!=null){
			 	document.getElementById("infantGivName"+i).style.visibility='collapse';
			 	//infantName"+i
			}
			/* Child age not required for now
			if(document.getElementById("chdAgeRow"+i)!=null){
			 	document.getElementById("chdAgeRow"+i).style.visibility='collapse';		 
			}
			*/
			
			if(document.getElementById("infantDOBRow"+i)!=null){
			 	document.getElementById("infantDOBRow"+i).style.visibility='collapse';		 
			}
			displayInfantRadio(i);
		}//adultType
		
	}		
	if(cntr==1 && onLoad==true){
		if(document.getElementById("adultType"+cntr)!=null){
		 	document.getElementById("adultType"+cntr).checked=true;		 	
		}
		displayInfantRadio(cntr);			
	}

}
function onInfantChoiceSelect(num){
	//alert("selectedTransaction");
	//alert(num);	
	if(document.getElementById("hasInfantYes"+num)!=null && document.getElementById("hasInfantYes"+num).checked==true){
		document.getElementById("infantSurName"+num).style.visibility='visible';
		//document.getElementById("infantSurName"+num).className='showRow';
		//showRow
		document.getElementById("infantGivName"+num).style.visibility='visible';
		document.getElementById("infantDOBRow"+num).style.visibility='visible';
	}else if(document.getElementById("hasInfantNo"+num)!=null && document.getElementById("hasInfantNo"+num).checked==true){
		//alert("Im here");
		document.getElementById("infantSurName"+num).style.visibility='collapse';
		document.getElementById("infantGivName"+num).style.visibility='collapse';
		//document.getElementById("infantDOB"+num).value="";
		document.getElementById("infantDOBRow"+num).style.visibility='collapse';	
	}
}
function displayInfantRadio(num){
	if(document.getElementById("adultType"+num)!=null && document.getElementById("adultType"+num).checked==true){
		document.getElementById("checkInfant"+num).style.visibility='visible';
		document.getElementById("hasInfantNo"+num).checked=true;
		//Child Age not required
		//document.getElementById("chdAgeRow"+num).style.visibility='collapse';
		onInfantChoiceSelect(num);
	}else {		
		document.getElementById("checkInfant"+num).style.visibility='collapse';
		document.getElementById("infantSurName"+num).style.visibility='collapse';
		document.getElementById("infantGivName"+num).style.visibility='collapse';		
		document.getElementById("hasInfantYes"+num).checked=false;
		document.getElementById("hasInfantNo"+num).checked=false;
		if(document.getElementById("chldType"+num)!=null && document.getElementById("chldType"+num).checked==true){
			//Child Age not required
			//document.getElementById("chdAgeRow"+num).style.visibility='visible';
			document.getElementById("infantDOBRow"+num).style.visibility='collapse';
		}else if(document.getElementById("insType"+num)!=null && document.getElementById("insType"+num).checked==true){
			//Child Age not required
			//document.getElementById("chdAgeRow"+num).style.visibility='collapse';
			document.getElementById("infantDOBRow"+num).style.visibility='visible';
		}
	}
}
function allLetter(obj)
{
	var inputtxt = obj.value
	var errorMessage;
	var fieldName;
 	var letters = /^[A-Za-z]+$/;
 	var cntr;
 	if(inputtxt.length>0){
 		setAllLabelfontsBlack(obj);1
	 	if(inputtxt.match(letters))
		   {
		    return true;
		   }
		 else
		   {
			 fieldName = obj.name;
			 //alert (fieldName);
			 if(fieldName.lastIndexOf("paxSurName", 0)==0){
				 //alert("1");
				 cntr=fieldName.charAt(fieldName.length-1);
				 errorMessage = "Enter valid Surname for passenger  - "+ cntr + "\n(Only alphabets name allowed)";
				 document.getElementById("paxSurNamelbl"+cntr).style.color = 'Red';
				 obj.focus();
			 }else if(fieldName.lastIndexOf('paxGivName',0)==0){
				 cntr=fieldName.charAt(fieldName.length-1);
				 errorMessage = "Enter valid Given name for passenger - "+ cntr + "\n(Only alphabets name allowed)";
				 document.getElementById("paxGivNamelbl"+cntr).style.color = 'Red';	
				 obj.focus();
			 }else if(fieldName.lastIndexOf('infSurName',0)==0){
				 cntr=fieldName.charAt(fieldName.length-1);
				 errorMessage = "Enter valid Surname for infant of passenger - "+ cntr + "\n(Only alphabets name allowed)";
				 document.getElementById("infSurNamelbl"+cntr).style.color = 'Red';
				 obj.focus();
			 }else if(fieldName.lastIndexOf('infGivName',0)==0){
				 cntr=fieldName.charAt(fieldName.length-1);
				 errorMessage = "Enter valid Given name for infant of passenger - "+ cntr + "\n(Only alphabets name allowed)";
				 document.getElementById("infGivNamelbl"+cntr).style.color = 'Red';
				 obj.focus();
			 }		
		   alert(errorMessage);
		   obj.value="";
		   return false;
		   }
 		}else{
 			//setAllLabelfontsBlack(obj);
 		}
	}
	function setAllLabelfontsBlack(obj){
		var fieldName;
		var cntr;
		fieldName = obj.name;		
		 if(fieldName.lastIndexOf("paxSurName", 0)==0){			 
			 cntr=fieldName.charAt(fieldName.length-1);			
			 document.getElementById("paxSurNamelbl"+cntr).style.color = 'Black';
		 }else if(fieldName.lastIndexOf('paxGivName',0)==0){
			 cntr=fieldName.charAt(fieldName.length-1);			
			 document.getElementById("paxGivNamelbl"+cntr).style.color = 'Black';				 
		 }else if(fieldName.lastIndexOf('infSurName',0)==0){
			 cntr=fieldName.charAt(fieldName.length-1);			 
			 document.getElementById("infSurNamelbl"+cntr).style.color = 'Black';
		 }else if(fieldName.lastIndexOf('infGivName',0)==0){
			 cntr=fieldName.charAt(fieldName.length-1);			 
			 document.getElementById("infGivNamelbl"+cntr).style.color = 'Black';
		 }else if(fieldName.lastIndexOf('infantDOBlbl',0)==0){
			 cntr=fieldName.charAt(fieldName.length-1);			 
			 document.getElementById("infantDOBlbl"+cntr).style.color = 'Black';
		 }
		 //
	}
	function resetPaxFields(num){
		var fieldName;
		var cntr;
//		fieldName = obj.name;
		cntr = num;
		for (var i=cntr; i<=paxCount; i++){
			if(document.getElementById("paxSurName"+i)!=null){
				document.getElementById("paxSurName"+i)="";
			}
			if(document.getElementById("paxGivName"+i)!=null){
				document.getElementById("paxGivName"+i)="";
			}
			if(document.getElementById("infSurName"+i)!=null){
				document.getElementById("infSurName"+i)="";
			}
			if(document.getElementById("infGivName"+i)!=null){
				document.getElementById("infGivName"+i)="";
			}//infantDOB'
			if(document.getElementById("infantDOB"+i)!=null){
				document.getElementById("infantDOB"+i)="";
			}
		}		 
	}
</script>
 <script language="JavaScript" src="htmlDatePicker.js" type="text/javascript"></script> 
 <link href="htmlDatePicker.css" rel="stylesheet" /> 
 <script language ='javascript' type ="text/javascript">
	function loadFields(fname){
		document.getElementById("calbut").style.backgroundImage="url('calendar2.gif')";
	}
</script>
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
		$tmp = document.querySelector("#infantDOB"+ i.toString());
           $tmp.DatePickerX.init({
               mondayFirst: true,
               format 	   : 'ddMyy',
               //minDate    : new Date(2021, 5, 9),
               minDate	   : function()
               {
                   var date = new Date();
                   return new Date().setDate(date.getDate() - 1095);
               },               
               maxDate    : function()
               {
                   var date = new Date();
                   return new Date().setDate(date.getDate()-1);
               },
               clearButton: false
           });
      	}
      });
    </script>
<style>
.hideRow{
visibility: collapse;
max-height: 0;
}
.showRow{
visibility: visible;
}
</style>
    
<meta charset="ISO-8859-1">
<title>Home Page</title>
</head>
<body bgcolor="#778899" onLoad ='onLoadFields();'>
<%
	String infSurNameRow = "";
	String infGivNameRow = "";
	String rdInfantYs="";
	String rdInfantNo="";
	int noOfPassengers = 10;//(int)session.getAttribute("paxCount");
	Connection connection = (Connection)session.getAttribute("connection");
	Iterator<HashMap> iter = null;
	HashMap<String, String> passengerMap;
	TableDataService tbleDTTrans = new TableDataService(connection);
	List<HashMap> passengerList = tbleDTTrans.getPaxNames();
	
	if(passengerList!=null)
	iter = passengerList.iterator();
	
	String lName="", fName="";
	
	out.println("<Table>");
	out.println("<tr id='blankRow'><td>");
	out.println("</td><td></td><td></td></tr>");
	for (int i=1; i<=noOfPassengers; i++){	
		
		out.println("<tr id='paxDtlsRow"+i+"'><td width = '225px'>");
		out.println("<b><label Font = 'color:blue'>Enter passenger - "+i+" - details</label></b></td><td></td><td></td></tr>");
		out.println("<tr id='paxTypeRow"+i+"'> <td><label>Passenger Type:</label></td><td>");
		out.println("<input type='radio' id='adultType"+i+"' name='paxType"+i+"' value='ADT'");
		out.println("onClick='displayInfantRadio("+i+")'>");
		out.println("<label for='html'>Adult</label>");
		out.println("<input type='radio' id='chldType"+i+"' name='paxType"+i+"' value='CHD'");
		out.println("onClick='displayInfantRadio("+i+")'>");
		out.println("<label for='css'>Child</label>");
		out.println("<input type='radio' id='insType"+i+"' name='paxType"+i+"' value='INS'");
		out.println("onClick='displayInfantRadio("+i+")'>");
		out.println("<label for='javascript'>Infant occupying seat</label></td></tr>");
		out.println("</tr>");
		if(iter!=null && iter.hasNext()){
			passengerMap = iter.next();
			lName = passengerMap.get("LNAME");
			fName = passengerMap.get("FNAME");
		}else{
			lName = "";
			fName = "";
		}
		//out.println("<Table>");
		out.println("<tr id='paxSurNameRow"+i+"'><td><label id='paxSurNamelbl"+i+"'>Passenger Surname:</label></td>");
		out.println("<td><input type='text' id='paxSurName"+i+"' name='paxSurName"+i+"' value='"+lName+"' onblur ='allLetter(this);'>");		
		out.println("</td></tr>");	
		out.println("<tr id='paxGivNameRow"+i+"'><td><label id='paxGivNamelbl"+i+"'>Passenger Given Name:</label></td>");
		out.println("<td><input type='text' id='paxGivName"+i+"' name='paxGivName"+i+"' value='"+fName+"' onblur ='allLetter(this);'>");		
		out.println("</td></tr>");	
		
		/*Child Age not required
		out.println("<tr id='chdAgeRow"+i+"'><td><label>Select Child age:</label></td>");
		out.println("<td><select id ='chdAge"+i+"' name ='chdAge"+i+"'><option>Select</option>");		 
			int cntr =1;
			while(cntr < 10){
				out.println ("<option>"+cntr+"</option>");
				cntr++;
			}
		out.println("</select>");
		out.println("</td></tr>");
		*/
		
		rdInfantYs = "hasInfantYes"+i;
		rdInfantNo = "hasInfantNo"+i;
		
		out.println("<tr id='checkInfant"+i+"'><td><label>Passenger has Infant on lap:</label></td>");
		out.println("<td><input type='radio' id='"+rdInfantYs+"' name='hasInfant"+i+"' value='Yes' ");
		out.println("onClick='onInfantChoiceSelect("+i+")'>");
		out.println("<label for='html'>Yes</label>");
		out.println("<input type='radio' id='"+rdInfantNo+"' name='hasInfant"+i+"' value='No'");
		out.println("onClick='onInfantChoiceSelect("+i+")'>");
		out.println("<label for='css'>No</label>");
		out.println("</td>");
		//out.println("</Table>");
		
		//out.println("<Table>");
		
		if(iter!=null && iter.hasNext()){
			passengerMap = iter.next();
			lName = passengerMap.get("LNAME");
			fName = passengerMap.get("FNAME");
		}else{
			lName = "";
			fName = "";
		}
		
		infSurNameRow = "infantSurName"+i;
		infGivNameRow = "infantGivName"+i;
		out.println("<tr id='"+infSurNameRow+"'><td><label id='infSurNamelbl"+i+"'>Enter Infant Surname:</label></td>");
		out.println("<td><input type='text' id='infSurName"+i+"' name='infSurName"+i+"' value='"+lName+"' onblur ='allLetter(this);'></td>");	
		out.println("<tr id='"+infGivNameRow+"'><td><label id='infGivNamelbl"+i+"'>Enter Infant Given Name:</label></td>");
		out.println("<td><input type='text' id='infGivName"+i+"' name='infGivName"+i+"' value='"+fName+"' onblur ='allLetter(this);'></td>");	
		out.println("</tr>");
		//out.println("</Table>");
		
		out.println("<tr id='infantDOBRow"+i+"'><td><label id = 'infantDOBlbl"+i+"'>Select Date of Birth:</label></td>");		
		//out.println("<td><input type='text' name = 'infantDOB"+i+"' id ='infantDOB"+i+"' readonly onfocus='GetDate(this);' onClick='GetDate(this); tabNext();' value='DDMMMYY' ></td>");
		out.println("<td><input type='text' name = 'infantDOB"+i+"' id ='infantDOB"+i+"' readonly placeholder='Select date'  ></td>");
		out.println("</tr>");
		
		
		
		//out.println("<TR><TD><label id ='billEndLab'>Billing End Date</label></TD><TD><INPUT TYPE='text' NAME = 'billEndDate' ID ='billEndDate' onClick='GetDate(this);' value='' ></TD></TR>");
		//out.println("<td><input id = 'dojDay"+i+"' name = 'dojDay"+i+"' value=''></td></tr>");
	}
	out.println("</Table>");
	
	out.println("<input type='hidden' id='hiddenPaxCount' value='"+noOfPassengers+"'/>");
	
%>

</body>
</html>