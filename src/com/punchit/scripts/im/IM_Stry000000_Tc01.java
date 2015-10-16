package com.punchit.scripts.im;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.IncidentPage;
import pages.LoginPage;
import pages.MenuPage;
import testng.SuiteMethods;
import utils.DataInputProvider;
import wrapper.ServiceNowWrappers;

public class IM_Stry000000_Tc01 extends SuiteMethods {

	// Create Instance
	ServiceNowWrappers snW;

	@Test(dataProvider = "IM_Stry000000_Tc01",groups="IncidentManagement")
	public void createIncident(String regUser, String regPwd,
							   String configItem, String repCust, 
							   String asgGroup, String desc,
							   String asgTo ){

		// Pre-requisities
		snW = new ServiceNowWrappers(entityId);

		try {

			// Step 0: Launch the application
			snW.launchApp(browserName, true);

			// Step 1: Login to the application
			MenuPage home = new LoginPage().loginAs(regUser, regPwd);
		
			// Step 2: Verify the Menus
			home.verifyExistanceOfIncidentMenus();
			
			// Step 3: click on create new
			IncidentPage incident = home.clickCreateNew();
			
			// Step 4: On the new Incident Screen, check the following fields are read only
			incident.verifyAllReadOnlyFields();
			
			// Step 5: The following fields are available to be populated and check they are flagged as mandatory
			incident.verifyAllMandatoryFields();
			
			// Step 6: The following fields are available to be populated and check they are NOT mandatory
			incident.verifyAllNonMandatoryFields();
			
			// Step 7: In the screen check for the tabs
			incident.verifyTabs();
			
			// Take a note of the INC number.
			String incNumber = incident.getIncidentNumber();
			System.out.println(incNumber);
			
			// Step 8: Enter all Mandatory fields
			incident.populateMandatoryFields(configItem, repCust, asgGroup, desc);
			
			// Step 9: Click on the Notes Tab and check for the available fields
			incident.verifyNotesFields();
			
			// Step 10: Under the Notes tabs, the following fields should be read only 
			incident.verifyNotesReadOnlyFields();
			
			// Step 12: Click on the Process tab, check for the following fields 
			incident.clickProcess().verifyProcessFields();
			
			// Step 13: Under the process tab,  Check the following fields should be read-only
			incident.verifyProcessReadOnlyFields();
			
			// Step 14: Under the process tab, the following fields should be available to be populated
			incident.verifyProcessEditableFields();
			
			// Step 15: Assign the incident to yourself and then save the ticket
			incident.enterAssignedTo(asgTo).saveIncident();
			
			// Step 16: Click on the WIP icon
			//home.clickWIP();
			
			// Step 17: Click on the Resolution Information tab , check for the following fields 
			
					
			status = "PASS";

		} finally {
			// close the browser
			//snW.quitBrowser();
		}

	}

	@DataProvider(name = "IM_Stry000000_Tc01")
	public Object[][] fetchData() throws IOException {
		Object[][] arrayObject = DataInputProvider.getSheet("IM_Stry000000_Tc01");
		return arrayObject;
	}
}
