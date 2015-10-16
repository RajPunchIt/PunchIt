package com.punchit.scripts.od;

import java.io.IOException;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import testng.SuiteMethods;
import utils.DataInputProvider;
import utils.Reporter;
import wrapper.ServiceNowWrappers;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;

public class OD_Stry0010409_Tc01 extends SuiteMethods {

	// Create Instance
	ServiceNowWrappers snW;
	
	@Test(dataProvider="OD_Stry0010409_Tc01",groups="OpsDirector")
	public void implementSLA(String regUser, String regPwd) {

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

			// Step 2: Expand OpsDirector/Ops Consoles and click on AlertsDashboard
			if(snW.selectMenu("Ops_Consoles","Alert_Dashboard"))
				Reporter.reportStep("Step 2: The Alert Dashboard - menu selected successfully","SUCCESS");
			else
				Reporter.reportStep("Step 2: The Alert Dashboard - menu could not be selected","FAILURE");

			// Switch to the main frame
			snW.switchToFrame("Frame_Main");

			if(snW.IsElementNotPresentByXpath("ALERT_SLAChart_Xpath")){
				status = "Insufficient Data";
				Reporter.reportStep("Step 3: There is no matching records for SLA Chart","FAILURE");
			}	

			// Step 3: Validate that there is a dashboard for Alerts breached SLA			
			if(snW.clickByXpath("ALERT_SLAChart_Xpath"))
				Reporter.reportStep("Step 3: The Alerts breached SLA Dashboard has been clicked successfully","SUCCESS");
			else
				Reporter.reportStep("Step 3: The Alerts breached SLA Dashboard could not be clicked","FAILURE");

			// Step 4: Open the Alert by clicking on one of the Tasks
			if(snW.clickByXpath("ALERT_FirstAlert_Xpath"))
				Reporter.reportStep("Step 4: The First Alert has been clicked successfully","SUCCESS");
			else
				Reporter.reportStep("Step 4: The First Alert could not be clicked","FAILURE");

			// Step 5: Scroll down to Task SLA’s section and click on settings to include another field to display
			if(snW.clickByXpath("ALERT_TaskSLA_SettingsIcon_Xpath"))	
				Reporter.reportStep("Step 5: The settings in Task SLA’s section has been clicked successfully","SUCCESS");
			else
				Reporter.reportStep("Step 5: The settings in Task SLA’s section could not be clicked","FAILURE");

			// Step 6:		
			if(snW.getCountOfElementsByXpath("ALERT_BreachInSelected_Xpath") == 1){

				if(!snW.selectByVisibleTextByXpath("ALERT_BreachInAvailable_Xpath", "Has breached"))
					Reporter.reportStep("Step 6: The 'Has Breached' is not present in either available or selected option", "FAILURE");
				if(!snW.clickByXpath("ALERT_MoveBreach_ToSelected_Xpath"))
					Reporter.reportStep("Step 6: The 'Has Breached' is unable to moved to selected", "FAILURE");

				Reporter.reportStep("Step 6: The 'Has Breached' value has been moved from Available to Selected","SUCCESS");
			}
			else
				Reporter.reportStep("Step 6: The 'Has Breached' is already in Selected option","SUCCESS");

			// Click Ok
			if(!snW.clickById("Ok_Id"))
				Reporter.reportStep("Ok button could not be clicked","FAILURE");

			// Step 7: Validate the value of “Has Breached” is true
			if(snW.verifyTextByXpath("ALERT_HasBreached_Xpath", "true"))
				Reporter.reportStep("Step 7: The value of 'Has Breached' is True as expected.","SUCCESS");
			else
				Reporter.reportStep("Step 7: The value of 'Has Breached' is NOT True","FAILURE");

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
	@DataProvider(name="OD_Stry0010409_Tc01")
	public Object[][] loginData() throws IOException {
		Object[][] arrayObject = DataInputProvider.getSheet("OD_Stry0010409_Tc01");
		return arrayObject;
	}
}
