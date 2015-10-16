package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import testng.SuiteMethods;
import utils.DataInputProvider;
import utils.Reporter;
import wrapper.ServiceNowWrappers;

public class CheckIn{
	
	public static void main(String[] args) {

		Map<String, String> filterMap = new HashMap<>();
		filterMap.put("Assigned to", "Me");
		filterMap.put("State", "Acknowledged,In Progress");
		filterMap.put("Related Task", "Resolved,Closed");
		
		if(filterMap.containsKey("Related Task")){
			System.out.println("Success");			
		}
	}

}