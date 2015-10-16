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

public class IM_Stry000000_Tc04 extends SuiteMethods {

	// Create Instance
	ServiceNowWrappers snW;

	@Test(dataProvider = "IM_Stry000000_Tc04",groups="IncidentManagement")
	public void createIncident(String regUser, String regPwd,
							   String configItem, String repCust, 
							   String asgGroup, String desc,
							   String aUser ){

		// Pre-requisities
		snW = new ServiceNowWrappers(entityId);

		try {

			// Step 0: Launch the application
			snW.launchApp(browserName, true);

			// Step 1: Login to the application
			MenuPage home = new LoginPage().loginAs(regUser, regPwd);
		
			// Step 2: click on create new
			IncidentPage incident = home.clickCreateNew();
			
			// Take a note of the INC number.
			String incNumber = incident.getIncidentNumber();
			System.out.println(incNumber);
			
			// Step 3: Enter all Mandatory fields
			incident.populateMandatoryFields(configItem, repCust, asgGroup, desc);
			
			// Enter Testuser2 into the Affected User field.  
			incident.enterAffectedUser(aUser);
			
			// Save the ticket
			incident.saveIncident();
			
			// Step 2: click on create new
			incident = home.clickCreateNew();
			
			// Take a note of the INC number.
			incNumber = incident.getIncidentNumber();
			System.out.println(incNumber);
			
			// Step 3: Enter all Mandatory fields
			incident.enterConfigurationItem(configItem).enterAssignmentGroup(asgGroup).enterShortDescription(desc);
			
			// Enter Testuser2 into the Affected User field.  
			incident.enterAffectedUser(aUser);
			
			// Save the ticket
			incident.saveIncidentExpectingFailure();
			
			// Confirm the error Message
			incident.verifyErrorMessage("The following mandatory fields are not filled in: Reporting Customer");
			
			// Remove the name from the Affected User field..  
			incident.enterAffectedUser("");
			
			// Enter “Testuser1” into the Reporting customer field and Save the ticket.
			incident.enterReportingCustomer(aUser);
			
			// Save the ticket
			incident.saveIncident();
					
			status = "PASS";

		} finally {
			// close the browser
			snW.quitBrowser();
		}

	}


	@DataProvider(name = "IM_Stry000000_Tc04")
	public Object[][] fetchData() throws IOException {
		Object[][] arrayObject = DataInputProvider.getSheet("IM_Stry000000_Tc04");
		return arrayObject;
	}
}
