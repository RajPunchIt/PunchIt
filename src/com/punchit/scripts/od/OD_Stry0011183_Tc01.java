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


public class OD_Stry0011183_Tc01 extends SuiteMethods {


	// Create Instance
	ServiceNowWrappers snW;

	@Test(dataProvider = "OD_Stry0011183_Tc01",groups="OpsDirector")
	public void testName(String regUser, String regPwd, 
			String refRate, String groupName,
			String filterType, String filterCondition,
			String filterValue) {

		// Pre-requisities
		snW = new ServiceNowWrappers(entityId);

		try {

			// Step 0: Launch the application
			if (snW.launchApp(browserName, true))
				Reporter.reportStep("The browser:" + browserName
						+ " launched successfully", "SUCCESS");
			else
				Reporter.reportStep("The browser:" + browserName
						+ " could not be launched", "FAILURE");

			// Step 1: Log in to application
			if (snW.login(regUser, regPwd))
				Reporter.reportStep("Step 1: The login with username:"
						+ regUser + " is successful", "SUCCESS");
			else
				Reporter.reportStep("Step 1: The login with username:"
						+ regUser + " is not successful", "FAILURE");

			// Step 2: Open Alert Console under user consoles

			if(snW.selectMenu("Ops_Consoles", "Alert_Console"))
				Reporter.reportStep("Step 2: The Alert Console under OpsConsole - menu selected successfully","SUCCESS");
			else
				Reporter.reportStep("Step 2: The Alert Console under OpsConsole - menu could not be selected","FAILURE");

			// Switch to the main frame
			snW.switchToFrame("Frame_Main");

			// Step 3: Valid the minimum refresh cycle is 5 minutes by clicking the drop down box on the right side
			if(snW.getTextById("ALERT_SelectRefresh_Id").equals(refRate))
				Reporter.reportStep("Step 3: The alert refresh rate is set to the minimum value:"+refRate+" as expected", "SUCCESS");
			else
				Reporter.reportStep("Step 3: The alert refresh rate is set to the minimum value:"+refRate+" as expected", "SUCCESS");

			// Step 4: Validate that the alerts listed are owned by groups that the user belongs to or descendant groups by clicking on the “My Group Alerts” in the alert console

			//Click My Group Alerts
			if(snW.clickByXpath("ALERT_MyGroupAlertHeader_Xpath"))
				Reporter.reportStep("Step 4: 'My Group Alerts' in the alert console", "SUCCESS");
			else
				Reporter.reportStep("Step 4: 'My Group Alerts' in the alert console", "FAILURE");

			// Validate the condition has group name listed.
			String val = snW.getTextByXpath("ALERT_BREADCRUMB_Xpath");

			if(val.contains(groupName))
				Reporter.reportStep("Step 5: The filter 'Alert Profile Owning group' is set with the group: "+groupName, "SUCCESS");				
			else
				Reporter.reportStep("Step 5: The filter 'Alert Profile Owning group' is not set with the group: "+groupName, "FAILURE");
					
			// Click filter - Check the functionality of filter by adding another condition to it
			if(snW.clickById("ALERT_FunnelIcon_Id"))
				Reporter.reportStep("Step 6: The funnel icon has been clicked successfully","SUCCESS");
			else
				Reporter.reportStep("Step 6: The funnel icon could not be clicked","FAILURE");

			snW.Wait(10000);

			// Add an filter condition with severity being Critical
			if(!snW.selectByVisibleTextByXpath("CIS_FirstFilterType2_Xpath", filterType))
				Reporter.reportStep("Step 7: The Filter type: " + filterType + " could not be selected", "FAILURE");
			if(!snW.selectByVisibleTextByXpath("CIS_FilterCondition2_Xpath", filterCondition))
				Reporter.reportStep("Step 7: The Filter condition: " + filterCondition + " could not be selected", "FAILURE");
			if(!snW.selectByVisibleTextByXpath("CIS_FilterValue2_Xpath", filterValue))
				Reporter.reportStep("Step 7: The Filter value: " + filterValue + " could not be selected", "FAILURE");

			// Click Run
			// report both positive and negative

			if(snW.clickByXpath("ALERT_RunFilter_Xpath"))
				Reporter.reportStep("Step 7: The filter values selected with severity and Run is clicked", "SUCCESS");
			else
				Reporter.reportStep("Step 7: 'Run' could not be clicked", "FAILURE");

			// Verify the console has alerts that have only Critical as severity
			if(snW.getTextByXpath("ALERT_AlertId_Xpath").equals("No records to display")){
				status = "Insufficient Data";
				Reporter.reportStep("Step 8: There is no records to display for new Alerts","FAILURE");
			}

			int countOfSeverity = snW.getCountOfElementsByXpath("//td[contains(text(),'"+filterValue+"')]", false);			
			String numOfRows = snW.getTextByXpath("ALERT_RowCount_Xpath");

			if((""+countOfSeverity).equals(numOfRows))
				Reporter.reportStep("Step 9: The console has alerts that have only "+filterValue+" as severity ","SUCCESS");
			else
				Reporter.reportStep("Step 9: The console has alerts other than "+filterValue+" as severity as well.","FAILURE");

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
	
	@DataProvider(name = "OD_Stry0011183_Tc01")
	public Object[][] fetchData() throws IOException {
		Object[][] arrayObject = DataInputProvider
				.getSheet("OD_Stry0011183_Tc01");
		return arrayObject;
	}

}
