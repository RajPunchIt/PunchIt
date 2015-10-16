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

// for using ATU reporting -- added the listeners

public class OD_Stry0010169_Tc01 extends SuiteMethods {

	// Create Instance
	ServiceNowWrappers snW;

	@Test(dataProvider = "OD_Stry0010169_Tc01")
	public void testName(String regUser, String regPwd, String Search, String Overrides, String NewSearch) {

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

			// Step 2: Expand OpsDirector/OpsConsole/under application navigator to select Alert Console
			if(snW.selectMenu("Configurations", "Alert_Profiles"))
				Reporter.reportStep("Step 2: The Alert Profiles under Configurations - menu selected successfully","SUCCESS");
			else
				Reporter.reportStep("Step 2: The Alert Profiles under Configurations - menu could not be selected","FAILURE");

			// Switch to the main frame
			snW.switchToFrame("Frame_Main");
			
			// Step 3: Open an Alert  Profile by clicking on Number link
			snW.enterByXpathAndClick("ALERTPROFILE_Search_Xpath", Search);	
			snW.Wait(2000);

			String alertId = snW.getTextByXpath("ALERTPROFILE_FirstAlert_Xpath");
			if(snW.clickByXpath("ALERTPROFILE_FirstAlert_Xpath"))
				Reporter.reportStep("Step 3: Alert Profile is Opened by clicking on the first alert" , "SUCCESS");
			else
				Reporter.reportStep("Step 3: The Alert Profile could not be opened","FAILURE");
			

			// Step 4: Go to Overrides field and click on look up link		
			if (snW.enterAndChoose("ALERTPROFILE_Overrides_Xpath", Overrides))
				Reporter.reportStep("Step 4: The Overrides has been selected successfully with value: "+Overrides , "SUCCESS");
			else
				Reporter.reportStep("Step 4: The Overrides Group could not be entered","FAILURE");
			
			// Step 5: Choose an Alert Profile from the list, make a note of the Number and update the record
			if (snW.clickById("CIS_UpdateButton_Id"))
				Reporter.reportStep("Step 4: The Update is successful","SUCCESS");
			else
				Reporter.reportStep("Step 4: The Update button could not be clicked","FAILURE");


			// Step 6: Go back to Alert Profiles and open the Alert Profile selected 
			snW.enterByXpathAndClick("ALERTPROFILE_Search_Xpath", NewSearch);	
			snW.Wait(2000);
			
			snW.clickByXpath("ALERTPROFILE_FirstAlert_Xpath");			
			snW.Wait(2000);

			// Step 7: Go to Overriding Profiles tab and validate that the overriding profile is listed there			
			if(snW.clickLink(alertId, false))
				Reporter.reportStep("Step 7: Status has been changed successfully","SUCCESS");
			else
				Reporter.reportStep("Step 7: Status could not be changed to", "FAILURE");
			

			// Step 8: Reset the data
			snW.Wait(2000);
			snW.enterById("ALERTPROFILE_Overrides_Xpath", "");
			snW.clickById("CIS_UpdateButton_Id");

			// go out of the frame
			snW.switchToDefault();

			// Log out
			if(snW.clickByXpath("Logout_Xpath"))
				Reporter.reportStep("Step 8: The Log out is clicked successfully.","SUCCESS");
			else
				Reporter.reportStep("Step 8: The logout Failed", "FAILURE");		

			status = "PASS";

		} finally {
			// close the browser
			snW.quitBrowser();
		}

	}

	@DataProvider(name = "OD_Stry0010169_Tc01")
	public Object[][] fetchData() throws IOException {
		Object[][] arrayObject = DataInputProvider
				.getSheet("OD_Stry0010169_Tc01");
		return arrayObject;
	}

	

}