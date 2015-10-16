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

public class OD_Stry0010121_Tc01 extends SuiteMethods {

	// Create Instance
	ServiceNowWrappers snW;

	@Test(dataProvider = "OD_Stry0010121_Tc01",groups="OpsDirector")
	public void createCIScope(String regUser, String regPwd, String name,
			String shortDescription, String filter, String owningGroup,
			String f1Section, String f1Condition, String f1Value,
			String f2Section, String f2Condition, String f2Value,
			String verUser, String verPwd) throws COSVisitorException,
			IOException {

		// Pre-requisities
		snW = new ServiceNowWrappers(entityId);

		try {

			// Step 0: Launch the application
			snW.launchApp(browserName, true);

			// Step 1: Log in to application
			if (snW.login(regUser, regPwd))
				Reporter.reportStep("Step 1: The login with username:"+ regUser + " is successful", "SUCCESS");
			else
				Reporter.reportStep("Step 1: The login with username:"+ regUser + " is not successful", "FAILURE");

			// Step 2: In application navigator expand OpsDirector/Registration to select CI Scope Registration
			if (snW.selectMenu("Registration", "CIS_Registration"))
				Reporter.reportStep("Step 2: The CI Scope Registration - menu selected successfully","SUCCESS");
			else
				Reporter.reportStep("Step 2: The CI Scope Registration - menu could not be selected","FAILURE");

			// Switch to the main frame
			snW.switchToFrame("Frame_Main");

			// Step 3: Fill in Name and Short Description from reference data
			if (!snW.enterById("CIS_Name_Id", name))
				Reporter.reportStep("Step 3: The CI Scope Name - could not be entered","FAILURE");

			if (snW.enterById("CIS_ShortDesc_Id", shortDescription))
				Reporter.reportStep("Step 3: The CI Scope name:" + name	+ " and description:" + shortDescription + " entered successfully", "SUCCESS");
			else
				Reporter.reportStep("Step 3: The CI Scope description could not be entered","FAILURE");

			// Step 4: Choose Type as Static Filter in the drop down
			if (snW.selectByVisibleTextById("CIS_Filter_Id", filter))
				Reporter.reportStep("Step 4: The CI Scope Filter has been selected successfully with value:"+ filter, "SUCCESS");
			else
				Reporter.reportStep("Step 4: The CI Scope Filter could not be selected","FAILURE");

			// Step 5: Choose Owning Group as Punch Group from the list that
			// appears by clicking the Search icon
			if (snW.enterAndChoose("CIS_OwningGroup_Xpath", owningGroup))
				Reporter.reportStep("Step 5: The Owning Group has been selected successfully with value:"+ owningGroup, "SUCCESS");
			else
				Reporter.reportStep("Step 5: The Owning Group could not be selected","FAILURE");

			// Step 6: Click ‘submit’ and switch to the frame in new window
			if (snW.clickById("CIS_SubmitButton_Id"))
				Reporter.reportStep("Step 6: The submit button has been clicked successfully","SUCCESS");
			else
				Reporter.reportStep("Step 6: The submit button could not be clicked","FAILURE");

			// Step 6B: Read the scope number
			String scopeNum = snW.getAttributeById("CIS_ScopeNumber_Id", "value");
			snW.Wait(5000);

			// Step 7: In the CI Filter section, select filter condition from
			// reference data
			if(!snW.selectByVisibleTextByXpath("CIS_FirstFilterType1_Xpath",f1Section))
				Reporter.reportStep("Step 7A: In CI Filter section "+ f1Section+ " could not be selected","FAILURE");

			if(!snW.selectByVisibleTextByXpath("CIS_FilterCondition1_Xpath",f1Condition))
				Reporter.reportStep("Step 7B: In CI Filter section "+ f1Condition+ " could not be selected","FAILURE");

			if(!snW.enterAndChoose("CIS_FilterValue1_Xpath", f1Value))
				Reporter.reportStep("Step 7C: In CI Filter section "+ f1Value+ " could not be selected","FAILURE");

			// Wait for few seconds
			snW.Wait(5000);

			// Click And Condition
			if(!snW.clickByXpath("CIS_AndCondition1_Xpath"))
				Reporter.reportStep("Step 7D: In CI Filter section And Condition could not be clicked","FAILURE");

			// Select the next filter
			if(!snW.selectByVisibleTextByXpath("CIS_FirstFilterType2_Xpath",f2Section))
				Reporter.reportStep("Step 7E: In CI Filter section "+ f2Section+ " could not be selected","FAILURE");

			// select is
			if(!snW.selectByVisibleTextByXpath("CIS_FilterCondition2_Xpath",f2Condition))
				Reporter.reportStep("Step 7F: In CI Filter section "+ f2Condition+ " could not be selected","FAILURE");

			// Select value
			if(snW.selectByVisibleTextByXpath("CIS_FilterValue2_Xpath", f2Value))
				Reporter.reportStep("Step 7: In CI Filter section all the condition has been selected","SUCCESS");
			else
				Reporter.reportStep("Step 7: In CI Filter section all the condition could not be selected","FAILURE");

			// Wait for few seconds
			snW.Wait(5000);

			// Step 8: Run the selected filter condition by clicking Update
			if (snW.clickById("CIS_UpdateButton_Id")) {
				snW.Wait(5000);
				Reporter.reportStep("Step 8: The Update is successful","SUCCESS");
			} else
				Reporter.reportStep("Step 8: The Update button could not be clicked","FAILURE");

			// Step 9: Save Record by clicking update at top of the screen
			if (!snW.rightClickById("CIS_Menu_Id"))
				Reporter.reportStep("Step 9: The Right click could not be clicked","FAILURE");

			if (snW.clickByXpath("CIS_SaveRecord_Xpath"))
				Reporter.reportStep("Step 9: The Save is clicked successfully","SUCCESS");
			else
				Reporter.reportStep("Step 9: The Save could not be clicked","FAILURE");

			// go out of the frame
			snW.switchToDefault();

			// Step 10: Log out
			if (snW.clickByXpath("Logout_Xpath"))
				Reporter.reportStep("Step 10: The Log out is clicked successfully","SUCCESS");
			else
				Reporter.reportStep("Step 10: The Log out could not be clicked","FAILURE");

			// Wait for few seconds
			snW.Wait(5000);

			// Step 11: Log in as a different group
			if (snW.login(verUser, verPwd))
				Reporter.reportStep("Step 11: The login with username:"+ verUser + " is successful", "SUCCESS");
			else
				Reporter.reportStep("Step 11: The login with username:"+ verUser + " is not successful", "FAILURE");

			// Step 12: Expand OpsDirector/Configurations under application
			if (snW.selectMenu("Configurations", "CIS_Scopes"))
				Reporter.reportStep("Step 12: The CI Scopes - menu selected successfully","SUCCESS");
			else
				Reporter.reportStep("Step 12: The CI Scopes - menu could not be selected","FAILURE");

			// Switch to the main frame
			snW.switchToFrame("Frame_Main");

			// Step 13: Fill in Name and Short Description from reference data
			if (!snW.enterByXpathAndClick("CIS_SearchReferenceData_Xpath", scopeNum))
				Reporter.reportStep("Step 13: The created Data: " + scopeNum + " could not be found", "FAILURE");

			// Wait for few seconds
			snW.Wait(5000);

			// Step 13B: Click on the scope Number
			if (snW.clickLink(scopeNum, false))
				Reporter.reportStep("Step 13B: The created scope number : " + scopeNum + " has been found and clicked successfully", "SUCCESS");
			else
				Reporter.reportStep("Step 13B: The created scope number : " + scopeNum + " could not be found", "FAILURE");

			// Step 14: Validate that there is no Update button and no Save
			if (snW.IsElementNotPresentById("CIS_UpdateButton_Id"))
				Reporter.reportStep("Step 14: The Update button could not been found as expected.","SUCCESS");
			else
				Reporter.reportStep("Step 14: The Update button has been found.", "FAILURE");

			// option when right click in the form
			if (!snW.rightClickById("CIS_Menu_Id"))
				Reporter.reportStep("Step 14B: The Right click on menu could not be performed.","FAILURE");

			if (snW.IsElementNotPresentByXpath("CIS_SaveRecord_Xpath"))
				Reporter.reportStep("Step 14C: The Save option could not been found as expected.","SUCCESS");
			else
				Reporter.reportStep("Step 14C: The Save option has been found.","FAILURE");

			// go out of the frame
			snW.switchToDefault();

			// Step 15: Log out
			if (snW.clickByXpath("Logout_Xpath"))
				Reporter.reportStep("Step 15: The Log out is clicked successfully.","SUCCESS");
			else
				Reporter.reportStep("Step 15: The Log out could not be clicked.", "FAILURE");

			status = "PASS";

		} finally {
			// close the browser
			snW.quitBrowser();
		}

	}


	@DataProvider(name = "OD_Stry0010121_Tc01")
	public Object[][] fetchData() throws IOException {
		Object[][] arrayObject = DataInputProvider.getSheet("OD_Stry0010121_Tc01");
		return arrayObject;
	}
}
