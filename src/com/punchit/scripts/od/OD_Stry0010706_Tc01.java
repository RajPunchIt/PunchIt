package com.punchit.scripts.od;

import java.io.IOException;

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

public class OD_Stry0010706_Tc01 extends SuiteMethods {

	// Create Instance
	ServiceNowWrappers snW;

	@Test(dataProvider = "OD_Stry0010706_Tc01",groups="OpsDirector")
	public void acknowledgingUser(String regUser, String regPwd) {

		try {
			// Pre-requisities 
			snW = new ServiceNowWrappers(entityId);

			// Step 0: Launch the application
			snW.launchApp(browserName, true);

			// Step 1: Log in to application
			if(snW.login(regUser, regPwd))
				Reporter.reportStep("Step 1: The login with username:"+ regUser + " is successful", "SUCCESS");
			else
				Reporter.reportStep("Step 1: The login with username:"+ regUser + " is not successful", "FAILURE");

			// get the full name
			String fullName = snW.getTextById("FullName_Id");
			
			// Step 2: In application navigator expand OpsDirector/Registration to
			// select CI Scope Registration
			if(snW.selectMenu("Ops_Consoles", "Alert_Console"))
				Reporter.reportStep("Step 2: The Alert Console under OpsConsole - menu selected successfully","SUCCESS");
			else
				Reporter.reportStep("Step 2: The Alert Console under OpsConsole - menu could not be selected","FAILURE");

			// Switch to the main frame
			snW.switchToFrame("Frame_Main");

			if(snW.getTextByXpath("ALERT_AlertId_Xpath").equals("No records to display")){
				status = "Insufficient Data";
				Reporter.reportStep("Step 3: There is no records to display for new Alerts","FAILURE");
			}

			// Step 3: From My Alerts section, select the same alert and right click
			// to select acknowledge
			String alertId = snW.getAttributeByXpath("ALERT_AlertId_Xpath", "data-title-value");
			snW.Wait(5000);

			if(!snW.rightClickByXpath("ALERT_ListBody_Xpath"))
				Reporter.reportStep("The Right click upon My Alert failed.","FAILURE");

			if(snW.clickByXpath("ALERT_Acknowledge_Xpath"))			
				Reporter.reportStep("Step 3: The My Alert acknowledged successfully","SUCCESS");
			else
				Reporter.reportStep("Step 3: The My Alert is not acknowledged","FAILURE");

			snW.Wait(5000);

			// go out of the frame
			snW.switchToDefault();	

			if(!snW.selectMenu("Ops_Consoles", "My_Alert_Console"))
				Reporter.reportStep("The My Alerts under OpsConsole - menu could not be selected","FAILURE");

			// Switch to the main frame
			snW.switchToFrame("Frame_Main");

			// Step 4: From My Alerts section, select the same alert
			if(snW.clickLink(alertId,false))
				Reporter.reportStep("Step 4: My Alert:"+alertId+" is clicked successfully","SUCCESS");
			else
				Reporter.reportStep("Step 4: My Alert:"+alertId+" is not clicked","FAILURE");

			// Step 5: Validate that the ‘Assigned To’ field has the value of login ID
			if(snW.getAttributeByXpath("ALERT_AssignedTo_Xpath","value").contains(fullName))
				Reporter.reportStep("Step 5: The Assigned To field has the value of login name:"+fullName+" as expected","SUCCESS");
			else
				Reporter.reportStep("Step 5: The Assigned To field does not have the value of login name :"+fullName,"FAILURE");			

			// Step 6: Scroll down to the Activity Log of alert record has the entry:
			if(snW.getTextByXpath("ALERT_ActivityLog_Xpath").contains(fullName))
				Reporter.reportStep("Step 6: The Activity Log of alert record has the entry: "+fullName+" as expected","SUCCESS");
			else
				Reporter.reportStep("Step 6: The Activity Log of alert record does not have the entry :"+fullName,"FAILURE");

			// go out of the frame
			snW.switchToDefault();

			// Log out
			if(!snW.clickByXpath("Logout_Xpath"))
				Reporter.reportStep("The logout Failed", "FAILURE");

			status = "PASS";

		} finally{

			// close the browser
			snW.quitBrowser();	

		}

	}

	@DataProvider(name = "OD_Stry0010706_Tc01")
	public Object[][] loginData() throws IOException {
		Object[][] arrayObject = DataInputProvider.getSheet("OD_Stry0010706_Tc01");
		return arrayObject;
	}

}
