package com.punchit.scripts.od;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.junit.internal.runners.SuiteMethod;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

public class OD_Stry0010709_Tc01 extends SuiteMethods {

	@Test(dataProvider = "OD_Stry0010709_Tc01")
	public void acknowledgingUser(String regUser, String regPwd) throws InterruptedException,IOException, COSVisitorException {
		// Create Instance
		ServiceNowWrappers snW = new ServiceNowWrappers(entityId);

		try{
			// Step 0: Launch the application
			snW.launchApp(browserName, true);

			// Step 1: Log in to application
			if(snW.login(regUser, regPwd))
				Reporter.reportStep("Step 1: The login with username:"+ regUser + " is successful", "SUCCESS");
			else
				Reporter.reportStep("Step 1: The login with username:"+ regUser + " is not successful", "FAILURE");

			// get the full name
			String fullName = snW.getTextById("FullName_Id");
			
			// Step 2: In application navigator expand OpsDirector/Registration to
			// select CI Scope Registration
			if(snW.selectMenu("Ops_Consoles", "My_Alert_Console"))
				Reporter.reportStep("Step 2: The My Alerts under OpsConsole - menu selected successfully","SUCCESS");
			else
				Reporter.reportStep("Step 2: The New Alerts under OpsConsole - menu could not be selected","FAILURE");

			// Switch to the main frame
			snW.switchToFrame("Frame_Main");

			//Step 3:Validate that there is a box with heading ‘My  Alerts’ and click on My Alerts link
			if(snW.clickByXpath("ALERT_MyAlertHeader_Xpath"))
				Reporter.reportStep("Step 3: The My Alerts Link has been clicked successfully","SUCCESS");
			else
				Reporter.reportStep("Step 3: The My Alerts Link could not be clicked","FAILURE");


			//Step 4:Expand the filter by clicking on the funnel icon
			if(snW.clickById("ALERT_FunnelIcon_Id"))
				Reporter.reportStep("Step 4: The funnel icon has been clicked successfully","SUCCESS");
			else
				Reporter.reportStep("Step 4: The funnel icon could not be clicked","FAILURE");

			snW.Wait(10000);

			// Step 5:Validate that filter is set to:Assigned to is me, 
			// State is Acknowledged or In Progress, 
			// Related Task is Not Resolved or Closed.
			String val = snW.getTextByXpath("ALERT_BREADCRUMB_Xpath");
			Boolean bSuccess = true;
			
			if(!val.contains("Assigned to is "+fullName)){
				bSuccess = false;
				Reporter.reportStep("Step 5: The filter 'Assigned to is' not set with the value: "+fullName, "FAILURE");
			}
			
			if(!val.contains("State = Acknowledged .or. State = In Progress")){
				bSuccess = false;
				Reporter.reportStep("Step 5: The filter 'State' is not set with the value: Acknowledged or In Progress", "FAILURE");
			}
			
			if(!val.contains("Related Task != Resolved .or. Related Task != Closed")){
				bSuccess = false;
				Reporter.reportStep("Step 5: The filter 'Related Task' is not set with the value: Resolved or Closed", "FAILURE");
			}	
			
			if(bSuccess)
				Reporter.reportStep("Step 5: The filter conditions matches with all the expected values", "SUCCESS");

			/*
			Map<String, String> filterMap = new HashMap<>();
			filterMap.put("Assigned to", "Me");
			filterMap.put("State", "Acknowledged,In Progress");
			filterMap.put("Related Task", "Resolved,Closed");

			Boolean bSuccess = true;
			List<WebElement> filters = snW.getAllElementsByXpath("ALERT_FilterTypeList_Xpath");
			for (WebElement filter : filters) {	
				String key = snW.getDefaultValue(filter);
				if(filterMap.containsKey(key)){
					boolean bMatches = false;
					String enteredFilterValue = "";

					new Actions(snW.getDriver()).click(filter).sendKeys(Keys.TAB, Keys.TAB).sendKeys(Keys.TAB).build().perform();
					WebElement ele = snW.getDriver().switchTo().activeElement();
					if(ele.getTagName().equals("select"))
						enteredFilterValue = snW.getDefaultValue(ele);					
					else if(ele.getTagName().equals("input"))
						enteredFilterValue = snW.getText(ele);				

					if(!enteredFilterValue.equals("")){
						String[] values = filterMap.get(key).split(",");
						for (String value : values) {
							if(value.equals(enteredFilterValue)){
								bMatches = true;
								break;
							}
						}
					}

					if(!bMatches){
						Reporter.reportStep("The filter:'"+key+"' do not have matching value", "FAILURE");
					}
					filterMap.remove(key); 
				}
			}

			int filterMapSize = filterMap.size();

			if(filterMapSize > 0)
				Reporter.reportStep("The expected filter: '"+filterMap.entrySet().iterator().next().getKey()+"' is missing in the selection filters" , "FAILED");
			else if(filterMapSize == 0 && bSuccess)
				Reporter.reportStep("The expected filters and its value matches in the selection filters" , "SUCCESS");
			*/
			
			// go out of the frame
			snW.switchToDefault();

			// Step 6: Log out
			if(snW.clickByXpath("Logout_Xpath"))
				Reporter.reportStep("Step 6: The Log out is clicked successfully","SUCCESS");
			else
				Reporter.reportStep("Step 6: The Log out could not be clicked", "FAILURE");

			status = "PASS";

		} finally {
			// close the browser
			snW.quitBrowser();
		}
	}

	@DataProvider(name = "OD_Stry0010709_Tc01")
	public Object[][] loginData() throws IOException {
		Object[][] arrayObject = DataInputProvider.getSheet("OD_Stry0010709_Tc01");
		return arrayObject;
	}

}
