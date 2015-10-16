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


public class OD_Stry0011296_Tc01 extends SuiteMethods {

	// Create Instance
	ServiceNowWrappers snW;

	@Test(dataProvider = "OD_Stry0011296_TC01",groups="OpsDirector")
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
			if(snW.selectMenu("Ops_Consoles", "Alert_Console"))
				Reporter.reportStep("Step 2: The Alert Console under OpsConsole - menu selected successfully","SUCCESS");
			else
				Reporter.reportStep("Step 2: The Alert Console under OpsConsole - menu could not be selected","FAILURE");

			// Switch to the main frame
			snW.switchToFrame("Frame_Main");
			snW.Wait(5000);

			// Step 3: Validate that there is the Alert Volume by Application view in the Alert Console
			if(snW.isExistByXpath("ALERT_MyAlertVolumeByApp_Xpath"))
				Reporter.reportStep("Step 3: The 'Alert Volume by Application' under Alert Console - menu is displayed successfully","SUCCESS");
			else
				Reporter.reportStep("Step 3: The 'Alert Volume by Application' under Alert Console - menu is missing","FAILURE");


			// Step 4: Refresh the view to validate it is working.
			snW.refresh();
			snW.selectMenu("Ops_Consoles", "Alert_Console");
			snW.switchToFrame("Frame_Main");
			snW.Wait(5000);

			// Step 3: Validate that there is the Alert Volume by Application view in the Alert Console
			if(snW.isExistByXpath("ALERT_MyAlertVolumeByApp_Xpath"))
				Reporter.reportStep("Step 4: The 'Alert Volume by Application' under Alert Console - menu is displayed successfully after refresh","SUCCESS");
			else
				Reporter.reportStep("Step 4: The 'Alert Volume by Application' under Alert Console - menu is missing after refresh","FAILURE");

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

	@DataProvider(name = "OD_Stry0011296_TC01")
	public Object[][] fetchData() throws IOException {
		Object[][] arrayObject = DataInputProvider
				.getSheet("OD_Stry0011296_TC01");
		return arrayObject;
	}



}