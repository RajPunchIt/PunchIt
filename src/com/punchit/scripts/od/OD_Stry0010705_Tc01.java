package com.punchit.scripts.od;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.openqa.selenium.WebElement;
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

public class OD_Stry0010705_Tc01 extends SuiteMethods {

	// Create Instance
	ServiceNowWrappers snW;

	@Test(dataProvider = "OD_Stry0010705_Tc01",groups="OpsDirector")
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

			// Step 2: In application navigator expand Ops Consoles and Alert Console
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

			//Click My Group Alerts
			if(!snW.clickByXpath("ALERT_MyGroupAlertHeader_Xpath"))
				Reporter.reportStep("Step 3: 'My Group Alerts' in the alert console", "FAILURE");
			
			// Click filter
			if(!snW.clickById("ALERT_FunnelIcon_Id"))
				Reporter.reportStep("Step 3: The funnel icon could not be clicked","FAILURE");
			
			snW.Wait(5000);

			// Remove All filters
			if(!snW.deleteAllFilters())
				Reporter.reportStep("Step 3: The filter could not be removed","FAILURE");

			snW.Wait(5000);

			// Add new filter with status as NEW
			snW.addNewFilter("State", "is", "New");
			
			// Click Run
			if(!snW.clickByXpath("ALERT_RunFilter_Xpath"))
				Reporter.reportStep("Step 3: 'Run' could not be clicked", "FAILURE");

			// Select an alert with “State” New and attempt to acknowledge
			String alertId = snW.getAttributeByXpath("ALERT_AlertId_Xpath", "data-title-value");

			if(!snW.rightClickByLinkText(alertId, false))
				Reporter.reportStep("Step 3: Right click on the alert could not be clicked","FAILURE");
			
			if (snW.IsElementNotPresentByXpath("ALERT_Acknowledge_Xpath"))
				Reporter.reportStep("Step 3: The Acknowledge button could not been found as expected.","SUCCESS");
			else
				Reporter.reportStep("Step 3: The Acknowledge button has been found.", "FAILURE");

			// Return to Alert Console and Select one or more new alerts by checking in the box to the left of the alert.
			snW.clickByXpath("ALERT_AllAlertsSelect_Xpath"); //just to keep the mouse out of the right click
			
			if(snW.clickByXpath("ALERT_AllAlertsChkBox_Xpath"))
				Reporter.reportStep("Step 4: All alerts checkbox is checked successfully","SUCCESS");
			else
				Reporter.reportStep("Step 4: All alerts checkbox could not be checked","FAILURE");

			if(!snW.selectByVisibleTextByXpath("ALERT_AllAlertsSelect_Xpath","Acknowledgment"))
				Reporter.reportStep("Step 5: The All alerts do not have Acknowledgment options as expected","SUCCESS");
			else
				Reporter.reportStep("Step 5: The All alerts has Acknowledgment options; hence failed","FAILURE");

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

	@DataProvider(name = "OD_Stry0010705_Tc01")
	public Object[][] loginData() throws IOException {
		Object[][] arrayObject = DataInputProvider.getSheet("OD_Stry0010705_Tc01");
		return arrayObject;
	}

}
