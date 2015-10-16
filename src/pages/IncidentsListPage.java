package pages;

import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Reporter;
import wrapper.ServiceNowWrappers;

public class IncidentsListPage extends ServiceNowWrappers{

	private final RemoteWebDriver driver;

	public IncidentsListPage(RemoteWebDriver driver) {
		this.driver = driver;

		switchToMainFrame();

		// Check that we're on the right page.
		if (!isExistByXpath("INCLIST_Table_Xpath")) {
			Reporter.reportStep("This is not the Incident List page", "FAILURE");
		}
		
		switchToMainFrame();
		
	}

	public IncidentsListPage switchToMainFrame(){

		// Switch to the menu frame
		switchToFrame("Frame_Main");

		return this;
	}

	public IncidentPage clickFirstIncident(){
		
		// click the first Incident Link
		if(clickByXpath("ALERTPROFILE_FirstAlert_Xpath"))
			Reporter.reportStep("The First Incident is clicked Successfully", "SUCCESS");
		else
			Reporter.reportStep("The First Incident could not be clicked", "FAILURE");

		return new IncidentPage(driver);
	}
	

}
