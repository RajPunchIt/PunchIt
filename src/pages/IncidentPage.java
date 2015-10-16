package pages;


import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import utils.Reporter;
import wrapper.ServiceNowWrappers;

public class IncidentPage extends ServiceNowWrappers{

	private final RemoteWebDriver driver;
	private String incidentNumber;

	public IncidentPage(RemoteWebDriver driver) {
		this.driver = driver;

		goOutOfFrame();

		// Check that we're on the right page.
		if (!isExistById("Welcome_Id")) {
			Reporter.reportStep("This is not the home page", "FAILURE");
		}
		
		switchToMainFrame();
		
	}

	public IncidentPage switchToMainFrame(){

		// Switch to the menu frame
		switchToFrame("Frame_Main");

		return this;
	}

	public IncidentPage verifyAllMandatoryFields(){

		// Switch to the menu frame
		switchToMainFrame();


		// you need to change the mandatory fields when the application changes
		String[] mandatoryFields = {"CREATEINC_RepCustStar_Xpath",
				"CREATEINC_ConfigItemStar_Xpath",
				"CREATEINC_AsgGroupStar_Xpath",
		"CREATEINC_shortDescStar_Xpath"};

		verifyMandatoryFields(mandatoryFields);

		return this;

	}

	public IncidentPage verifyAllNonMandatoryFields(){

		// Switch to the menu frame
		switchToMainFrame();


		// you need to change the non mandatory fields when the application changes
		String[] nonMandatoryFields = { "CREATEINC_AffectedUserStar_Xpath",
				"CREATEINC_DescriptionStar_Xpath",
				"CREATEINC_ComponentStar_Xpath",
		"CREATEINC_AssignedToStar_Xpath"};

		verifyNonMandatoryFields(nonMandatoryFields);

		return this;

	}

	public IncidentPage verifyAllReadOnlyFields(){

		// Switch to the menu frame
		switchToMainFrame();

		// you need to change the read only fields when the application changes
		String[] readOnlyFields = { "CREATEINC_IncidentNumber_Xpath",
				"CREATEINC_IncidentState_Xpath",
		"CREATEINC_IncidentPriority_Xpath"};

		// Verify read only
		verifyDisabledFieldsByXpath(readOnlyFields);

		return this;

	}

	public IncidentPage verifyTabs(){

		// Switch to the menu frame
		switchToMainFrame();

		// you need to change the read only fields when the application changes
		String[] tabNames ={"CREATEINC_Notes_Xpath",
		"CREATEINC_Process_Xpath"};

		// Verify the tabs exists
		verifyFieldsExistByXpath(tabNames);

		return this;

	}

	public String getIncidentNumber() {
		incidentNumber = getAttributeByXpath("CREATEINC_IncidentNumber_Xpath", "value");

		if(incidentNumber.equals(""))
			Reporter.reportStep("The incident number is blank for newly created incident", "FAILURE");

		return incidentNumber;
	}

	public IncidentPage enterConfigurationItem(String configItem) {
		if(!enterAndChoose("CREATEINC_ConfigItem_Xpath", configItem))
			Reporter.reportStep("The configuration item: "+configItem+" not found / could not be entered", "FAILURE");

		return this;
	}
	
	public IncidentPage enterConfigurationItemForSuccess(String configItem) {
		if(enterAndChoose("CREATEINC_ConfigItem_Xpath", configItem))
			Reporter.reportStep("The configuration item: "+configItem+" found and entered", "SUCCESS");
		else
			Reporter.reportStep("The configuration item: "+configItem+" not found / could not be entered", "FAILURE");

		return this;
	}
	
	public IncidentPage enterConfigurationItemForFailure(String configItem) {
		if(!enterAndChoose("CREATEINC_ConfigItem_Xpath", configItem))
			Reporter.reportStep("The configuration item: "+configItem+" not found as expected", "SUCCESS");
		else
			Reporter.reportStep("The configuration item: "+configItem+" found and entered", "FAILURE");

		return this;
	}

	public IncidentPage enterReportingCustomer(String repCust) {
		if(!enterAndChoose("CREATEINC_RepCust_Xpath", repCust))
			Reporter.reportStep("The reporting customer: "+repCust+" not found / could not be entered", "FAILURE");

		return this;
	}

	public IncidentPage enterAssignmentGroup(String asgGroup) {
		if(!enterAndChoose("CREATEINC_AsgGroup_Xpath", asgGroup))
			Reporter.reportStep("The Assignment Group: "+asgGroup+" not found / could not be entered", "FAILURE");

		return this;
	}
	
	public IncidentPage enterAssignedTo(String asgTo) {
		if(enterAndChoose("CREATEINC_AssignedTo_Xpath", asgTo))
			Reporter.reportStep("The Assigned To is entered successfully", "SUCCESS");
		else
			Reporter.reportStep("The Assigned To: "+asgTo+" not found / could not be entered", "FAILURE");

		return this;
	}

	public IncidentPage enterAffectedUser(String aUser) {
		if(enterAndChoose("CREATEINC_AffectedUser_Xpath", aUser))
			Reporter.reportStep("The Affected User is entered successfully", "SUCCESS");
		else
			Reporter.reportStep("The Affected User: "+aUser+" not found / could not be entered", "FAILURE");

		return this;
	}

	public IncidentPage enterShortDescription(String desc) {
		if(!enterByXpath("CREATEINC_shortDesc_Xpath", desc))
			Reporter.reportStep("The short description: "+desc+" could not be entered", "FAILURE");

		return this;
	}
	
	public IncidentPage enterBusinessServiceForSuccess(String configItem) {
		if(enterAndChoose("CREATEINC_ConfigItem_Xpath", configItem))
			Reporter.reportStep("The configuration item: "+configItem+" found and entered", "SUCCESS");
		else
			Reporter.reportStep("The configuration item: "+configItem+" not found / could not be entered", "FAILURE");

		return this;
	}
	

	// The page allows the user to submit the incident form
	public IncidentPage submitIncident() {
		clickByXpath("CREATEINC_Submit_Xpath");

		if (isExistById("Welcome_Id"))
			Reporter.reportStep("The Create Incident process is successful", "SUCCESS");
		else
			Reporter.reportStep("The Create Incident process failed. Check snapshot", "SUCCESS");

		return this;
	}
	
	// The page allows the user to submit the incident form
	public IncidentPage saveIncident() {
		clickById("Save_Id");
		Wait(5000);
		
		if (getTextByXpath("CREATEINC_Pointer_Xpath").contains(getIncidentNumber()))
			Reporter.reportStep("The Create Incident process is successful", "SUCCESS");
		else
			Reporter.reportStep("The Create Incident process failed. Check snapshot", "SUCCESS");


		return this;
	}
		
	// The page allows the user to submit the incident form
	public IncidentPage saveIncidentExpectingFailure()  {
		clickById("Save_Id");

		return this;
	}

	public IncidentPage createNewIncident(String configItem, String repCust, String asgGroup, String desc) {

		return  enterConfigurationItem(configItem).
				enterReportingCustomer(repCust).
				enterAssignmentGroup(asgGroup).
				enterShortDescription(desc).
				submitIncident();

	}

	public IncidentPage populateMandatoryFields(String configItem, String repCust, String asgGroup, String desc) {

		enterConfigurationItem(configItem).
		enterReportingCustomer(repCust).
		enterAssignmentGroup(asgGroup).
		enterShortDescription(desc);
		
		Reporter.reportStep("The Mandatory fields of Create Incident are entered successfully", "SUCCESS");
		return this;

	}

	public IncidentPage verifyNotesFields(){

		// Switch to the menu frame
		switchToMainFrame();

		// you need to change the read only fields when the application changes
		String[] notesFields = {"CREATEINC_Worknoteslist_Xpath",
				"CREATEINC_WorkNotes_Xpath",
				"CREATEINC_Latestworknotes_Xpath",
				"CREATEINC_CustomerWatchlist_Xpath",
				"CREATEINC_CustomerComms_Xpath",
		"CREATEINC_LatestCustomerUpdate_Xpath"};

		// Verify the notes fields exists
		verifyFieldsExistByXpath(notesFields);

		return this;

	}

	public IncidentPage verifyNotesReadOnlyFields(){

		// Switch to the menu frame
		switchToMainFrame();

		// you need to change the read only fields when the application changes
		String[] readOnlyFields = { "CREATEINC_Latestworknotes_Xpath",
		"CREATEINC_LatestCustomerUpdate_Xpath"};

		// Verify read only
		verifyDisabledFieldsByXpath(readOnlyFields);

		return this;

	}

	public IncidentPage clickProcess() {
		if(clickByXpath("CREATEINC_Process_Xpath"))
			Reporter.reportStep("The Process Tab is clicked successfully", "SUCCESS");
		else
			Reporter.reportStep("The Process Tab could not be entered", "FAILURE");

		return this;
	}

	public IncidentPage verifyProcessFields(){

		// Switch to the menu frame
		switchToMainFrame();

		// you need to change the read only fields when the application changes
		String[] processFields = {  "CREATEINC_MasterIncident_Xpath",
									"CREATEINC_GTrackChange_Xpath",
									"CREATEINC_DeviationNumber_Xpath",
									"CREATEINC_GxPSystem_Xpath",
									"CREATEINC_SOXSystem_Xpath"};

		// Verify the notes fields exists
		verifyFieldsExistByXpath(processFields);

		return this;

	}

	public IncidentPage verifyProcessReadOnlyFields(){

		// Switch to the menu frame
		switchToMainFrame();

		// you need to change the read only fields when the application changes
		String[] readOnlyFields = { "CREATEINC_GxPSystem_Xpath",
									"CREATEINC_SOXSystem_Xpath"};

		// Verify read only
		verifyDisabledFieldsByXpath(readOnlyFields);

		return this;

	}

	public IncidentPage verifyProcessEditableFields(){

		// Switch to the menu frame
		switchToMainFrame();

		// you need to change the read only fields when the application changes
		String[] editableFields = { "CREATEINC_MasterIncident_Xpath",
									"CREATEINC_GTrackChange_Xpath",
									"CREATEINC_DeviationNumber_Xpath"};

		// Verify read only
		verifyEnabledFieldsByXpath(editableFields);

		return this;

	}

	public IncidentPage verifyErrorMessage(String errorMessage){
		if(getTextAndAcceptAlert().equals(errorMessage))
			Reporter.reportStep("The error message :"+errorMessage+" appeared as expected", "SUCCESS");
		else
			Reporter.reportStep("The error message :"+errorMessage+" did not appear", "FAILURE");

		return this;	
		
	}

	public IncidentPage isReportingCustomerDisabled() {
		String[] field = {"CREATEINC_RepCustLabel_Xpath"};
		verifyDisabledFieldsByXpath(field);
		return this;
	}

	public IncidentPage isAffectedUserDisbled() {
		String[] field = {"CREATEINC_AffectedUserLabel_Xpath"};
		verifyDisabledFieldsByXpath(field);
		return this;
	}
	
	public IncidentPage isReportingCustomerEnabled() {
		String[] field = {"CREATEINC_RepCustLabel_Xpath"};
		verifyEnabledFieldsByXpath(field);
		return this;
	}

	public IncidentPage isAffectedUserEnabled() {
		String[] field = {"CREATEINC_AffectedUserLabel_Xpath"};
		verifyEnabledFieldsByXpath(field);
		return this;
	}
	
	public IncidentPage hoverCallerId(){
		
		mouseOverById("CREATEINC_Caller_Id");
		
		if(isExistById("CREATEINC_SysUserName_Id"))
			Reporter.reportStep("The User Information displayed successfully", "SUCCESS");
		else	
			Reporter.reportStep("The User Information is not on mouse Over", "FAILURE");

		return this;
		
	}
	
	public IncidentPage verifyBusinessServiceReadOnly(){

		// Switch to the menu frame
		switchToMainFrame();

		// you need to change the read only fields when the application changes
		String[] readOnlyField = { "CREATEINC_BusinesService_Xpath"};

		// Verify read only
		verifyDisabledFieldsByXpath(readOnlyField);

		return this;

	}
	
	public IncidentPage verifyBusinessServiceEditable(){

		// Switch to the menu frame
		switchToMainFrame();

		// you need to change the enabled fields when the application changes
		String[] editableField = { "CREATEINC_BusinesService_Xpath"};

		// Verify read only
		verifyEnabledFieldsByXpath(editableField);

		return this;

	}

	public IncidentPage verifyBusinessServiceContent(String sBusinessService){

		// Switch to the menu frame
		switchToMainFrame();

		if(getAttributeByXpath("CREATEINC_BusinesService_Xpath","value").equals(sBusinessService))
			Reporter.reportStep("The business service field has value: "+sBusinessService+" as expected" , "SUCCESS");
		else
			Reporter.reportStep("The business service field do not have value: "+sBusinessService+"; hence failed" , "FAILURE");

		return this;

	}
}
