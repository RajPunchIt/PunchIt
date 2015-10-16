package com.punchit.scripts.od;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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

// for using ATU reporting -- added the listeners

public class OD_Stry0010027_Tc01 extends SuiteMethods {

	// Create Instance
	ServiceNowWrappers snW;

	@Test(dataProvider = "OD_Stry0010027_Tc01")
	public void testName(String regUser, String regPwd, String grpName) {

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

			// Pick an alert and take a note of its alert profile name 
			String alertId = snW.getTextByXpath("ALERTPROFILE_FirstAlert_Xpath");
			String alertProfile = snW.getTextByXpath("ALERTPROFILE_FirstProfile_Xpath_Usr");

			// Step 3: Select alert profile on first alert	
			if(snW.clickLink(alertProfile, false))
				Reporter.reportStep("Step 3A: 'Alert Profile' on the alert "+ alertId +" clicked sucessfully","SUCCESS");
			else	
				Reporter.reportStep("Step 3A: 'Alert Profile' on the alert "+ alertId +" could not be clicked","FAILURE");
			
			//Step 3: Validate that the alerts listed belong to the group 'Punch Group1'

			if(snW.getAttributeById("ALERTPROFILE_GroupName_Id", "value").equals(grpName))
				Reporter.reportStep("Step 3B: 'Alert Group Name' matched with "+ grpName +" sucessfully","SUCCESS");
			else	
				Reporter.reportStep("Step 3B: 'Alert Group Name' could not be matched with "+ grpName,"FAILURE");
	
			// Step 4: Click Back
			if(!snW.clickByXpath("Back_Xpath"))
				Reporter.reportStep("Step 4: 'Back Button' under Alert Record could not be clicked","FAILURE");
			
			snW.Wait(5000);
			// Step 4: From My Alerts section, select the same alert
			if(!snW.clickLink(alertId, false))
				Reporter.reportStep("Step 4: Click on the alert could not be clicked","FAILURE");
			
			snW.Wait(5000);
			// Step 4: Verify all ReadOnly Fields
			String[] readOnlyFields={"ALERTRECORD_Num_Xpath", "ALERTRECORD_Assignto_Xpath", "ALERTRECORD_AlertProfile_Xpath", 
										"ALERTRECORD_ReactionType_Xpath", "ALERTRECORD_ConfItem_Xpath", "ALERTRECORD_AlertSeverity_Xpath", 
										"ALERTRECORD_AlertState_Xpath", "ALERTRECORD_ClosedBy_Xpath", "ALERTRECORD_ClosureCode_Xpath",
										"ALERTRECORD_Tally_Xpath", "ALERTRECORD_Rating_Xpath", "ALERTRECORD_ShortDesc_Xpath", "ALERTRECORD_AlertDesc_Xpath"};
			
			snW.verifyDisabledFieldsByXpath(readOnlyFields);			
			
			// go out of the frame
			snW.switchToDefault();

			// Log out
			if(snW.clickByXpath("Logout_Xpath"))
				Reporter.reportStep("Step 5: The Log out is clicked successfully.","SUCCESS");
			else
				Reporter.reportStep("Step 5: The logout Failed", "FAILURE");		


			status = "PASS";

		} finally {
			// close the browser
			snW.quitBrowser();
		}

	}

	@DataProvider(name = "OD_Stry0010027_Tc01")
	public Object[][] fetchData() throws IOException {
		Object[][] arrayObject = DataInputProvider
				.getSheet("OD_Stry0010027_Tc01");
		return arrayObject;
	}



}