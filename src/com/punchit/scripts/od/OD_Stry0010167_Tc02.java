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

public class OD_Stry0010167_Tc02 extends SuiteMethods {

	// Create Instance
	ServiceNowWrappers snW;

	@Test(dataProvider = "OD_Stry0010167_Tc02")
	public void testName(String regUser, String regPwd, String scheduleType, String shortDesc, 
						 String startHr, String startMn, String startSc, 
						 String endHr, String endMn,String endSc) {

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
			String alertCI= snW.getTextByXpath("ALERTPROFILE_FirstCI_Xpath");
			
			// Step 4: From My Alerts section, select the same alert	
			if(!snW.rightClickByLinkText(alertId, false))
				Reporter.reportStep("Step 3: Right click on the alert could not be clicked","FAILURE");

			// suppress alerts from Alert Profile
			if(!snW.clickByXpath("ALERT_SupressAlert_Xpath"))
				Reporter.reportStep("Step 3: Supress alert could not be clicked","FAILURE");

			// suppress alerts from Alert Profile
			if(snW.clickByXpath("ALERT_SupressByCI_Xpath"))
				Reporter.reportStep("Step 3: Right click on an alert and suppress alerts from Alert CI is successful.","SUCCESS");
			else
				Reporter.reportStep("Step 3: Supress by alert CI could not be clicked","FAILURE");
			
			// Validate the profile name is same as step 3.
			String sSuppProfile = snW.getAttributeById("ALERT_Suppress_Target_Id","value");
			if(sSuppProfile.contains(alertCI))
				Reporter.reportStep("Step 4: Alert Suppress CI name matches with the Alert CI","SUCCESS");
			else
				Reporter.reportStep("Step 4: Alert Suppress CI name do not match with the Alert CI","FAILURE");

			// Key in a name for Alert Suppressor. Use the Alert Profile Name appended by <timestame>-suppressor. For example Link Down 07102015 Suppressor
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy-h-mm-ss");
			String formattedDate = sdf.format(date);	
			String supressName = alertCI+" - "+formattedDate;
			
			if(!snW.enterById("ALERT_Suppress_Name_Id",supressName))
				Reporter.reportStep("Step 5: Alert Suppress Name could not be entered","FAILURE");
			
			// Short Description:  Suppressing the Alert profile < profile name>
			if(!snW.enterById("ALERT_Suppress_ShortDesc_Id", shortDesc+alertCI))
				Reporter.reportStep("Step 5: Alert Suppress 'Short Description' could not be entered","FAILURE");
			
			// Schedule Type: Run Daily
			if(!snW.selectByVisibleTextById("ALERT_Suppress_Type_Id", scheduleType))
				Reporter.reportStep("Step 5: Alert Suppress 'Schedule Type' could not be selected","FAILURE");
			
			// Start Time: Hours 01 00 00
			if(!snW.enterById("ALERT_Suppress_StartHour_Id", startHr))
				Reporter.reportStep("Step 5: Alert Suppress 'Start Hour' could not be entered","FAILURE");
			
			if(!snW.enterById("ALERT_Suppress_StartMin_Id", startMn))
				Reporter.reportStep("Step 5: Alert Suppress 'Start Minutes' could not be entered","FAILURE");
			
			if(!snW.enterById("ALERT_Suppress_StartSec_Id", startSc))
				Reporter.reportStep("Step 5: Alert Suppress 'Start Seconds' could not be entered","FAILURE");
			
			// Stop Time: 10 00 00
			if(!snW.enterById("ALERT_Suppress_StopHour_Id", endHr))
				Reporter.reportStep("Step 5: Alert Suppress 'End Hour' could not be entered","FAILURE");
			
			if(!snW.enterById("ALERT_Suppress_StopMin_Id", endMn))
				Reporter.reportStep("Step 5: Alert Suppress 'End Minutes' could not be entered","FAILURE");
			
			if(!snW.enterById("ALERT_Suppress_StopSec_Id", endSc))
				Reporter.reportStep("Step 5: Alert Suppress 'End Seconds' could not be entered","FAILURE");
			
			// and click submit button
			if(snW.clickById("Submit_Id"))
				Reporter.reportStep("Step 5: Alert Suppression:"+supressName+" saved successfully","SUCCESS");
			else
				Reporter.reportStep("Step 5: Alert Supression - Submit button could not be clicked","FAILURE");
			
			// Step 5: Open Alert Suppressors under Administration
			if(snW.selectMenu("Administration", "Alert_Suppressors"))
				Reporter.reportStep("Step 6A: The 'Alert Suppressors' under Administration - menu selected successfully","SUCCESS");
			else
				Reporter.reportStep("Step 6A: The 'Alert Suppressors' under Administration - menu could not be selected","FAILURE");

			// Switch to the main frame
			snW.switchToFrame("Frame_Main");
			
			// Validate the record created in step 5 exist.
			String shortenedName = supressName;
			if(shortenedName.length() > 40)
				shortenedName = shortenedName.substring(0, 40);
			
			snW.enterByXpathAndClick("ALERTPROFILE_Search_Xpath", shortenedName);	
			snW.Wait(5000);

			if(snW.getTextByXpath("ALERTPROFILE_FirstAlert_Xpath").equals(shortenedName))
				Reporter.reportStep("Step 6B: The 'Alert Suppressors' successfully found the newly created CI record :"+supressName,"SUCCESS");
			else
				Reporter.reportStep("Step 6B: The 'Alert Suppressors' could not find the newly created CI record :"+supressName,"FAILURE");
									
			// go out of the frame
			snW.switchToDefault();

			// Log out
			if(snW.clickByXpath("Logout_Xpath"))
				Reporter.reportStep("Step 7: The Log out is clicked successfully.","SUCCESS");
			else
				Reporter.reportStep("Step 7: The logout Failed", "FAILURE");		
						
			status = "PASS";

		} finally {
			// close the browser
			snW.quitBrowser();
		}

	}

	@DataProvider(name = "OD_Stry0010167_Tc02")
	public Object[][] fetchData() throws IOException {
		Object[][] arrayObject = DataInputProvider
				.getSheet("OD_Stry0010167_Tc02");
		return arrayObject;
	}

	

}