package serverprog;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.CommonProperties;

/**
 * Servlet implementation class FlightDataValidate
 */
public class FlightDataValidate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FlightDataValidate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		HashMap<String, String> eachSegMap;
		
		String airlineCode, fltNo, cosCode,org, des, doj, dojDay, dojMonth, DtOfJrny, tempString;
		HashMap<Integer, HashMap<String, String>> segMap = new HashMap<Integer, HashMap<String, String>>();
		int noOfSeg = request.getParameter("hiddenNoOfSeg")!=null? Integer.parseInt(request.getParameter("hiddenNoOfSeg").toString()):0;
		 
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
	}

}
