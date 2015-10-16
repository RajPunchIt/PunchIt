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


public class OD_Stry0010935_Tc01 extends SuiteMethods {
	

	// Create Instance
	ServiceNowWrappers snW;

	@Test(dataProvider = "OD_Stry0010935_TC01",groups="OpsDirector")
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
			if(snW.selectMenu("Administration", "Application_Properties"))
				Reporter.reportStep("Step 2: The Application Properties under OpsConsole - menu selected successfully","SUCCESS");
			else
				Reporter.reportStep("Step 2: The Application Properties under OpsConsole - menu could not be selected","FAILURE");

			// Move to main menu
			snW.switchToFrame("Frame_Main");
			
			// Step 3: Select Newest alert severity
			snW.selectByVisibleTextByXpath("AP_AlertSevRec_Xpath", "Severity is always updated with new Alert Recurrences");
			snW.clickByXpath("Save_Xpath");
			snW.Wait(5000);
			
			// Step 3: Severity is always updated with new alert recurrences
			String sevRecValue = snW.getDefaultValueByXpath("AP_AlertSevRec_Xpath");

			if(sevRecValue.equals("Severity is always updated with new Alert Recurrences"))	
				Reporter.reportStep("Step 3: The Severity is always updated with new Alert Recurrences is selected successfully","SUCCESS");
			else
				Reporter.reportStep("Step 3: The Severity is always updated with new Alert Recurrences could not be Selected","FAILURE");

				
			// Step 4: Select worst alert severity
			snW.selectByVisibleTextByXpath("AP_AlertSevRec_Xpath", "Severity always shows the worst Severity encountered");
			snW.clickByXpath("Save_Xpath");
			snW.Wait(5000);

			// Step 4: Severity is always updated with new alert recurrences
			sevRecValue = snW.getDefaultValueByXpath("AP_AlertSevRec_Xpath");

			if(sevRecValue.equals("Severity always shows the worst Severity encountered"))	
				Reporter.reportStep("Step 4: The Severity always shows the worst Severity encountered is selected successfully","SUCCESS");
			else
				Reporter.reportStep("Step 4: The Severity always shows the worst Severity encountered could not be Selected","FAILURE");

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

	@DataProvider(name = "OD_Stry0010935_TC01")
	public Object[][] fetchData() throws IOException {
		Object[][] arrayObject = DataInputProvider
				.getSheet("OD_Stry0010935_TC01");
		return arrayObject;
	}

}