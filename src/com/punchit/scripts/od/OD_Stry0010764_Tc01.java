package com.punchit.scripts.od;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import testng.SuiteMethods;
import utils.DataInputProvider;
import utils.Reporter;
import wrapper.ServiceNowWrappers;

public class OD_Stry0010764_Tc01 extends SuiteMethods {

	// Create Instance
	ServiceNowWrappers snW;

	@Test(dataProvider = "OD_Stry0010764_Tc01")
	public void testName(String regUser, String regPwd, String usrUser, String usrPwd){ 

		try {

			// Pre-requisities 
			snW = new ServiceNowWrappers(entityId);

			// Step 0: Launch the application
			snW.launchApp(browserName, true);

			// Step 1: Log in to application
			if (snW.login(regUser, regPwd))
				Reporter.reportStep("Step 1: The login with username:"
						+ regUser + " is successful", "SUCCESS");
			else
				Reporter.reportStep("Step 1: The login with username:"
						+ regUser + " is not successful", "FAILURE");

			// Step 2: Open Alert Console under user consoles
			if(snW.selectMenu("Ops_Consoles", "Alert_Console"))
				Reporter.reportStep("Step 2: The 'Alert Console' under OpsConsole - menu selected successfully","SUCCESS");
			else
				Reporter.reportStep("Step 2: The 'Alert Console' under OpsConsole - menu could not be selected","FAILURE");

			// Switch to the main frame
			snW.switchToFrame("Frame_Main");

			if(snW.getTextByXpath("ALERT_AlertId_Xpath").equals("No records to display")){
				status = "Insufficient Data";
				Reporter.reportStep("Step 3: There is no records to display for new Alerts","FAILURE");
			}


			// Pick an alert and take a note of its alert profile name 
			String alertId_Optr = snW.getTextByXpath("ALERTPROFILE_FirstAlert_Xpath");

			// Step 3: Click on the Alert Profile
			if(snW.clickLink(alertId_Optr, false))
				Reporter.reportStep("Step 3: 'Alert Profile':"+alertId_Optr+" on the first alert successfully clicked","SUCCESS");
			else
				Reporter.reportStep("Step 3: 'Alert Profile':"+alertId_Optr+" on the first alert could not be clicked","FAILURE");
			
			//Step 4: Validate that there is no delete option available for Operator login
			
			if(snW.IsElementNotPresentByXpath("Delete_Xpath"))
				Reporter.reportStep("Step 4: 'Delete option' is not found for the username "+ regUser + " hence successful" , "SUCCESS");
			else
				Reporter.reportStep("Step 4: 'Delete option' is found for the username "+ regUser +" hence failed","FAILURE");

			// go out of the frame
			snW.switchToDefault();

			// Step 5: Log out
			if(snW.clickByXpath("Logout_Xpath"))
				Reporter.reportStep("Step 5: The Log out is clicked successfully.","SUCCESS");
			else
				Reporter.reportStep("Step 5: The logout Failed", "FAILURE");		


			// Step 6: Log in to application
			if (snW.login(usrUser, usrPwd))
				Reporter.reportStep("Step 6: The login with username:"
						+ usrUser + " is successful", "SUCCESS");
			else
				Reporter.reportStep("Step 6: The login with username:"
						+ usrUser + " is not successful", "FAILURE");

			// Step 7: Open Alert Console under user consoles
			if(snW.selectMenu("Ops_Consoles", "Alert_Console"))
				Reporter.reportStep("Step 7A: The 'Alert Console' under OpsConsole - menu selected successfully","SUCCESS");
			else
				Reporter.reportStep("Step 7A: The 'Alert Console' under OpsConsole - menu could not be selected","FAILURE");

			// Switch to the main frame
			snW.switchToFrame("Frame_Main");

			// Pick an alert and take a note of its alert profile name
			String alertId_Usr = snW.getTextByXpath("ALERTPROFILE_FirstAlert_Xpath");

			// Click on the Alert Profile
			if(snW.clickLink(alertId_Usr, false))
				Reporter.reportStep("Step 7B: 'Alert Profile': "+alertId_Usr+" on the first alert successfully clicked","SUCCESS");
			else
				Reporter.reportStep("Step 7B: 'Alert Profile': "+alertId_Usr+" on the first alert could not be clicked","FAILURE");

			//Step 8: Validate that there is no delete option available for User login
			
			if(snW.IsElementNotPresentByXpath("Delete_Xpath"))
				Reporter.reportStep("Step 8: 'Delete option' is not found for the username "+ usrUser + " hence successful" , "SUCCESS");
			else
				Reporter.reportStep("Step 8: 'Delete option' is found for the username "+ usrUser +" hence failed","FAILURE");

			// go out of the frame
			snW.switchToDefault();

			//Step 9: Log out
			if(snW.clickByXpath("Logout_Xpath"))
				Reporter.reportStep("Step 9: The Log out is clicked successfully.","SUCCESS");
			else
				Reporter.reportStep("Step 9: The logout Failed", "FAILURE");		

			status = "PASS";

		} finally {
			// close the browser
			snW.quitBrowser();
		}

	}

	@DataProvider(name = "OD_Stry0010764_Tc01")
	public Object[][] fetchData() throws IOException {
		Object[][] arrayObject = DataInputProvider
				.getSheet("OD_Stry0010764_Tc01");
		return arrayObject;
	}



}