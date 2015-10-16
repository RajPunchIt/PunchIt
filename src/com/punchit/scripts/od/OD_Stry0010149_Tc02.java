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

public class OD_Stry0010149_Tc02 extends SuiteMethods {
	// Create Instance
	ServiceNowWrappers snW;

	@Test(dataProvider="OD_Stry0010149_Tc02",groups="OpsDirector")

	public void assignAlert(String regUser, String regPwd, String assignedTo, String verUser, String verPwd, String state,String assignedToOld) {
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

			// Step 2: Expand OpsDirector/OpsConsole/under application navigator to select Alert Console
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

			// Step 3: From New Alerts section, select the  alert and right click to select acknowledge
			String alertId = snW.getAttributeByXpath("ALERT_AlertId_Xpath", "data-title-value");

			if(!snW.rightClickByXpath("ALERT_ListBody_Xpath"))
				Reporter.reportStep("Step 3A: Right click on the alert could not be clicked","FAILURE");

			if(snW.clickByXpath("ALERT_AssignToMe_Xpath"))
				Reporter.reportStep("Step 3B: The New Alerts has been Assigned successfully","SUCCESS");
			else
				Reporter.reportStep("Step 3B: The New Alerts could not be Assigned","FAILURE");

			// go out of the frame
			snW.switchToDefault();

			// Step 4: Expand OpsDirector/OpsConsole/under application navigator to select Alert Console
			if(snW.selectMenu("Ops_Consoles", "My_Alert_Console"))
				Reporter.reportStep("Step 4A: The My Alert Console under OpsConsole - menu selected successfully","SUCCESS");
			else
				Reporter.reportStep("Step 4A: The My Alert Console under OpsConsole - menu could not be selected","FAILURE");

			// Switch to the main frame
			snW.switchToFrame("Frame_Main");

			// Step 4: From My Alerts section, select the same alert	
			if(!snW.rightClickByLinkText(alertId, false))
				Reporter.reportStep("Step 4B: Right click on the alert could not be clicked","FAILURE");

			if(snW.clickByXpath("ALERT_Acknowledge_Xpath"))
				Reporter.reportStep("Step 4C: The My Alerts has been Acknowledged successfully","SUCCESS");
			else
				Reporter.reportStep("Step 4C: The My Alerts could not be Acknowledged","FAILURE");
			snW.Wait(5000);

			// Step 5: From My Alerts section, select the same alert
			if(snW.clickLink(alertId, false))
				Reporter.reportStep("Step 5A: Alert is clicked successfully in My Alerts","SUCCESS");
			else
				Reporter.reportStep("Step 5A: Alert could not be clicked in My Alerts","FAILURE");


			// Step 5: Click the Look Up icon near Assigned to field and select new assignee “test.opd.opertor2
			if(snW.enterAndChoose("ALERT_AssignedTo_Xpath", assignedTo))		
				Reporter.reportStep("Step 5B: New assignee "+ assignedTo +" has been assigned", "SUCCESS");
			else
				Reporter.reportStep("Step 5B: New assignee "+ assignedTo +" could not be assigned","FAILURE");

			// Step 6: Click update to save the record 
			if(snW.clickById("CIS_UpdateButton_Id")) 
				Reporter.reportStep("Step 6: Update has been clicked successfully", "SUCCESS");
			else
				Reporter.reportStep("Step 6: Update could not be clicked","FAILURE");
			snW.Wait(5000);

			// go out of the frame
			snW.switchToDefault();

			// Log out
			if(!snW.clickByXpath("Logout_Xpath"))
				Reporter.reportStep("The logout Failed", "FAILURE");

			snW.Wait(5000);

			// Step 7: Log in as a different group
			if(snW.login(verUser,verPwd))
				Reporter.reportStep("Step 7: The login with username:"+ verUser + " is successful", "SUCCESS");
			else
				Reporter.reportStep("Step 7: The login with username:"+ verUser + " is not successful", "FAILURE");

			// Step 8 : Expand OpsDirector/OpsConsole/ under application navigator to select Alert Console
			if(snW.selectMenu("Ops_Consoles", "My_Alert_Console"))
				Reporter.reportStep("Step 8: The My Alerts Console under OpsConsole - menu selected successfully","SUCCESS");
			else
				Reporter.reportStep("Step 8: The My Alerts Console under OpsConsole - menu could not be selected","FAILURE");


			// Switch to the main frame
			snW.switchToFrame("Frame_Main");

			// Step 9 :Click Alert Console and select the alert record you assigned 
			if(snW.clickLink(alertId, false))
				Reporter.reportStep("Step 9: The assigned alert record  has been clicked successfully","SUCCESS");
			else
				Reporter.reportStep("Step 9: The assigned alert record could not be clicked","FAILURE");

			// Step 10 :Code to verify the status change
			String sAssignedTo = snW.getTextByXpath("ALERT_ActivityUpdateGroup_Xpath"); 
			String sStatus = snW.getTextByXpath("ALERT_AssignedStatus_Xpath"); 

			
			if(sAssignedTo.toLowerCase().contains(assignedTo.toLowerCase()) && sStatus.contains("Acknowledged") )
				Reporter.reportStep("Step 10: The Alert has been assigned to "+ assignedTo + " and the Status has been changed to Acknowledged","SUCCESS");
			else
				Reporter.reportStep("Step 10: The Alert could not be assigned to "+ assignedTo + " and/or the Status is not in Acknowledged state","FAILURE");


			// Step 11 :Scroll up to alert record section and select the “state” drop-down box to change the state from Acknowledged to In-Progress
			if(snW.selectByVisibleTextById("ALERT_State_Xpath", state))
				Reporter.reportStep("Step 11: State has been changed to "+ state +"", "SUCCESS");
			else
				Reporter.reportStep("Step 11: State could not be changed to "+ state +"","FAILURE");

			// Step 12 : Change the assignment again Click the Look Up icon near Assigned to field and select new assignee “test.opd.opertor1”
			if(snW.enterAndChoose("ALERT_AssignedTo_Xpath", assignedToOld))
				Reporter.reportStep("Step 12: Assignee "+ assignedToOld +" has been assigned", "SUCCESS");
			else
				Reporter.reportStep("Step 12: Assignee "+ assignedToOld +" could not be assigned","FAILURE");

			// Click update to save the record 
			if(!snW.clickById("CIS_UpdateButton_Id"))
				Reporter.reportStep("Step 12: Update button could not be clicked.","FAILURE");

			snW.Wait(5000);

			// go out of the frame
			snW.switchToDefault();

			//Step 13A : Log out
			if(snW.clickByXpath("Logout_Xpath"))
				Reporter.reportStep("Step 13A: The Log out is clicked successfully","SUCCESS");
			else
				Reporter.reportStep("Step 13A: The Log out could not be clicked", "FAILURE");

			snW.Wait(5000);

			// Step 13B : Log in as a  regular user
			if(snW.login(regUser, regPwd))
				Reporter.reportStep("Step 13B: The login with username:"+ regUser + " is successful","SUCCESS");
			else
				Reporter.reportStep("Step 13B: The login with username:"+ regUser + " is not successful", "FAILURE");

			// Step 14A : Expand OpsDirector/OpsConsole/ under application navigator to select Alert Console
			if(!snW.selectMenu("Ops Consoles", "My Alerts"))			 
				Reporter.reportStep("Step 14A: The My Alerts under OpsConsole - menu could not be selected","FAILURE");

			// Switch to the main frame
			snW.switchToFrame("Frame_Main");

			// Step 14B : Click on the link
			if(snW.clickLink(alertId))
				Reporter.reportStep("Step 14B: The login with username:"+ regUser + " is successful","SUCCESS");
			else
				Reporter.reportStep("Step 14B: The login with username:"+ regUser + " is not successful", "FAILURE");

			// Step 15: Code to verify the status change
			String sAssignedToNew = snW.getTextByXpath("ALERT_ActivityUpdateGroup_Xpath"); 
			if(sAssignedToNew.contains(assignedToOld))
				Reporter.reportStep("Step 15A: Alert assignment has been changed successfully to" + assignedToOld +" ","SUCCESS");
			else
				Reporter.reportStep("Step 15A: Alert assignment could not be changed to" + assignedToOld +" ", "FAILURE");

			String sStatusNew = snW.getTextByXpath("ALERT_AssignedStatus_Xpath");
			if(sStatusNew.contains(state))
				Reporter.reportStep("Step 15B: Status has been changed successfully to" + state +" ","SUCCESS");
			else
				Reporter.reportStep("Step 15B: Status could not be changed to" + state +" ", "FAILURE");

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

	@DataProvider(name="OD_Stry0010149_Tc02")
	public Object[][] loginData() throws IOException {
		Object[][] arrayObject = DataInputProvider.getSheet("OD_Stry0010149_Tc02");
		return arrayObject;
	}	
}
