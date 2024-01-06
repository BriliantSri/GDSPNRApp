<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
.loader {
	  border: 16px solid #f3f3f3; /* Light grey */
	  border-top: 16px solid #3498db; /* Blue */
	  border-radius: 50%;
	  width: 120px;
	  height: 120px;
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
</head>
<body>
<%
	
	out.println("<div class= 'center'><div class='loader'></div></div>");

/*	

	Connection connection = (Connection)session.getAttribute("connection");
	if (connection==null || connection.isClosed()){
		ConnectToDatabase connDB = new ConnectToDatabase();
		connection = connDB.getConnection();
	}
	List <String> seatList = new ArrayList();
	Iterator<String> iter;
	TableDataService tableDataService = new TableDataService(connection);
	//tableDataService.truncateFlightRecord();
	//tableDataService.insertFlightRecord(877, "PTY", "ORG", "15MAR",2, "Y", 9, 233, "NA", 0);
	//tableDataService.insertFlightRecord(877, "PTY", "ORG", "16MAR",3, "Y", 9, "233", "NA", 0);
	/*
	CommonProperties.envURL = "https://tpfsb.svcs.entsvcs.net";	
	CommonProperties.logID="qzsvz9";
	CommonProperties.passWord="Dxc@67890";
	*/
	/*
	FlightSeatCollector flightSeatCollector = new FlightSeatCollector(connection);
	seatList = flightSeatCollector.getAvailableSeats("360", "PTY", "LAX", "25MAR", "C");
	if (seatList!=null && !seatList.isEmpty()){
		iter = seatList.iterator();
		int i=1;
		while (iter.hasNext()){
			out.println(iter.next()+", ");
			i++;
			if(i>10){
				out.println("<br>");
				i=1;
			}
		}
	}
	*/
	/*
	FlightOperations fltOperns = new FlightOperations();
	fltOperns.verifyAndAssignShip("173", "15MAR", "MIA");
	*/
	/*
	HashMap<String, HashMap<String, String>> autoScenarioMap = tableDataService.getATAllSRHeaderDTls();
	ExcelUtility excelUtility = new ExcelUtility();
	HashMap<String, String> headerMap;
	String astrID;
	TreeMap<String, HashMap<String, String>> sortedMap = new TreeMap<>();
	sortedMap.putAll(autoScenarioMap);
	for (Map.Entry<String, HashMap<String, String>> tdEntry: sortedMap.entrySet()){		
		//astrID = (tdEntry.getValue()).get("atsrid");
		//headerMap = tableDataService.getATSRDtls(astrID);
		excelUtility.createScenarioHeaders(tdEntry.getValue());			
	}*/
	
	
	

%>
</body>
</html>