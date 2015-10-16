package com.punchit.scripts.im;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.IncidentPage;
import pages.IncidentsListPage;
import pages.LoginPage;
import pages.MenuPage;
import testng.SuiteMethods;
import utils.DataInputProvider;
import wrapper.ServiceNowWrappers;

public class IM_Stry000000_Tc03A extends SuiteMethods {

	// Create Instance
	ServiceNowWrappers snW;

	@Test(dataProvider = "IM_Stry000000_Tc03A",groups="IncidentManagement")
	public void createIncident(String regUser, String regPwd){

		// Pre-requisities
		snW = new ServiceNowWrappers(entityId);

		try {

			// Step 0: Launch the application
			snW.launchApp(browserName, true);

			// Step 1: Login to the application
			MenuPage home = new LoginPage().loginAs(regUser, regPwd);
		
			// Step 2: click on Open Incident
			IncidentsListPage incidentsList = home.clickOpen();
			
			//Step 3: Click on an Incident ticket 
			IncidentPage incident = incidentsList.clickFirstIncident();
			
			//hover over information button  next to affected user
			incident.hoverCallerId();
					
			status = "PASS";

		} finally {
			// close the browser
			snW.quitBrowser();
		}

	}


	@DataProvider(name = "IM_Stry000000_Tc03A")
	public Object[][] fetchData() throws IOException {
		Object[][] arrayObject = DataInputProvider.getSheet("IM_Stry000000_Tc03A");
		return arrayObject;
	}
}
