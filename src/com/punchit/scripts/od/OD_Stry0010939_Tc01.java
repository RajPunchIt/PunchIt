package com.punchit.scripts.od;

import java.io.IOException;
import java.util.List;

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


public class OD_Stry0010939_Tc01 extends SuiteMethods {
	

	// Create Instance
	ServiceNowWrappers snW;

	@Test(dataProvider = "OD_Stry0010939_Tc01",groups="OpsDirector")
	public void testName(String regUser, String regPwd) {

		// Pre-requisities
		snW = new ServiceNowWrappers(entityId);

		try {

			// Step 0: Launch the application
			snW.launchApp(browserName, true);

			// Step 1: Log in to application
			if (snW.login(regUser, regPwd))
				Reporter.reportStep("Step 1: The login with username:"
						+ regUser + " is successful", "SUCCESS");
			else
				Reporter.reportStep("Step 1: The login with username:"
						+ regUser + " is not successful", "FAILURE");

			// Step 2: Write a code to select the menu using 
			if(snW.selectMenu("Ops_Consoles", "My_Alert_Console"))
				Reporter.reportStep("Step 2: The My Alerts Console under OpsConsole - menu selected successfully","SUCCESS");
			else
				Reporter.reportStep("Step 2: The My Alerts Console under OpsConsole - menu could not be selected","FAILURE");

			// Move to main menu
			snW.switchToFrame("Frame_Main");
			
			// Step 3: Click on settings to display
			if(snW.clickByXpath("ALERT_SettingsIcon_Xpath"))	
				Reporter.reportStep("Step 5: The settings in Alert section has been clicked successfully","SUCCESS");
			else
				Reporter.reportStep("Step 5: The settings in Alert section could not be clicked","FAILURE");

			// Step 4:	Validate that the fields of Related Tasks – Related Task and State are available in the list
			String[] expectedValues = {"Related Task","Related Task.State"};
			snW.Wait(5000);
			
			// The expected value validation against the list of the items
			if(snW.verifyListContents("ALERT_SlushSelected_Xpath",expectedValues))
				Reporter.reportStep("Step 4: The fields :Related Task,State are found in the available list.", "SUCCESS");
			else
				Reporter.reportStep("Step 4: The fields :Related Task, Related Task.State are not found in the available list.", "FAILURE");

			// Click Ok
			if(!snW.clickById("Ok_Id"))
				Reporter.reportStep("Step 4: Ok button could not be clicked","FAILURE");

			snW.Wait(5000);

			// Validate that Related Task and Related Task, State fields are present in the view
			String[] expectedTableValues = {"Related Task","State"};
			
			if(snW.verifyTableHeaders("ALERT_MyAlertsTableHeader_Xpath",expectedTableValues))
				Reporter.reportStep("Step 5: The fields :Related Task,State are found in the table columns.", "SUCCESS");
			else
				Reporter.reportStep("Step 5: The fields :Related Task,State are not found in the table columns.", "FAILURE");
			
			// go out of the frame
			snW.switchToDefault();

			// Log out
			if(!snW.clickByXpath("Logout_Xpath"))
				Reporter.reportStep("The logout Failed", "FAILURE");

			status = "PASS";			

		} finally {
			
			// close the browser
			snW.quitBrowser();
			
		}

	}

	@DataProvider(name = "OD_Stry0010939_Tc01")
	public Object[][] fetchData() throws IOException {
		Object[][] arrayObject = DataInputProvider
				.getSheet("OD_Stry0010939_Tc01");
		return arrayObject;
	}

}