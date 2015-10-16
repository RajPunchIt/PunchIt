package wrapper;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import utils.Reporter;


public class ServiceNowWrappers extends GenericWrappers {

	private static RemoteWebDriver driver;
	public static String testcaseName;

	public ServiceNowWrappers() {
	}

	public ServiceNowWrappers(String testcaseName) {
		super();
		ServiceNowWrappers.testcaseName = testcaseName;
	}

	/**
	 * login to the site using the data provided through arguments
	 * through entering user name, password and by submit click. The method
	 * waits until the loading of page.
	 * @author Rajkumar
	 * @param  user
	 *            The user name of the login (any type of login - admin,
	 *            reviewer)
	 * @param  pwd
	 *            The password as text (not in encrypted format)
	 * @throws IOException 
	 * @throws COSVisitorException 
	 */
	public boolean login(String user, String pwd)  {
		boolean bReturn = false;
		{
			// switch to the main frame
			switchToFrame("Frame_Main");

			// enter username, password and click login
			enterById("UserName_Id", user);
			enterById("Password_Id", pwd);
			clickById("LoginButton_Id");
			
			// If logout exists, send true
			if(!IsElementNotPresentByXpath("Logout_Xpath")){
				if(!isExistById("Frame_Nav")) /* Rajkumar -- Oct 12 2015 -- Code added to check if the frame is visible; if not, click on toggle */
					clickByXpath("ToggleNav_Xpath"); // Click on the toggle navigator
				bReturn = true;
			}
		}

		return bReturn;

	}

	/**
	 * select the menu item from the menu header
	 * @author Rajkumar
	 * @param  menuHeader - the name of the menu header
	 * @param  menu - the menu item 
	 *            
	 */
	public boolean selectMenu(String menuHeader, String menu) {
		boolean bReturn = false;
		try{

			// Get the driver handle
			driver = getDriver();

			// Go inside the frame
			switchToFrame("Frame_Nav");

			// find the menu and check if it is open
			List<WebElement> menuHeaders = getAllElementsByXpath("MenuHeader_Xpath");
			// Check the menu name and see if it is expanded
			for (WebElement menuHeaderEle : menuHeaders) {
				if(menuHeaderEle.getText().equals(menuHeader)){

					// Get the attribute name of the id
					if(driver.findElementById("app_"+menuHeaderEle.getAttribute("data-id")).getAttribute("data-open").equals("false"))
						menuHeaderEle.click();

					break;
				}
			}
			Wait(5000);

			// Click on the menu 
			clickLink(menu);
			bReturn = true;

		}catch(Exception e){

		}

		return bReturn;
	}

	/**
	 * select the menu item from the Ops Director
	 * @author Rajkumar
	 * @param  menuHeader - the name of the menu header
	 * @param  pwd
	 *            The password as text (not in encrypted format)
	 */
	public boolean chooseFromSearch(String searchIcon, String data) {
		boolean bReturn = false;
		{


			// Click search icon
			clickByXpath(searchIcon);

			// Wait for a while
			Wait(5000);

			// Move the control to the new window
			switchToSecondWindow();

			// Search for the data
			enterByXpath("//*[@placeholder='Search']/../input[@class='form-control']", data);

			// Click on the link
			clickLink(data);
			bReturn = true;
		}

		return bReturn;
	}

	/**
	 * verify the mandatory fields
	 */
	public boolean verifyMandatoryFields(String[] fields){
		Boolean bReturn = true;
		for (String field : fields) {
			if(!verifyAttributeTextByXpath(field, "mandatory", "true")){
				bReturn = false;
				Reporter.reportStep("The field :"+field+" is not displayed as mandatory. Hence Failed.","FAILURE");
			}		
		}
		
		if(bReturn){
			Reporter.reportStep("All the fields are mandatory as expected","SUCCESS");
		}

		return bReturn;
	}

	/**
	 * verify the non mandatory fields
	 */
	public boolean verifyNonMandatoryFields(String[] fields){
		Boolean bReturn = true;
		for (String field : fields) {
			if(verifyAttributeTextByXpath(field, "mandatory", "true")){
				bReturn = false;
				Reporter.reportStep("The field :"+field+" is not displayed as non - mandatory. Hence Failed.","FAILURE");
			}			
		}
		
		if(bReturn){
			Reporter.reportStep("All the fields are non mandatory as expected","SUCCESS");
		}

		return bReturn;
	}

	/**
	 * verify the read only fields
	 */
	public boolean verifyDisabledFieldsByXpath(String[] fields){
		Boolean bReturn = true;
		for (String field : fields) {
			if(verifyAttributeTextByXpath(field, "readonly", "true")){
			}else if(verifyAttributeTextByXpath(field, "disabled", "true")){
			}else{	
				bReturn = false;
				Reporter.reportStep("The field :"+field+" is enabled; hence failed","FAILURE");
			}		
		}
		
		if(bReturn){
			Reporter.reportStep("All the fields are disabled as expected","SUCCESS");
		}

		return bReturn;
	}
	
	/**
	 * verify the read only fields
	 */
	public boolean verifyEnabledFieldsByXpath(String[] fields){
		Boolean bReturn = true;
		for (String field : fields) {
			if(!isEnabledByXpath(field)){
				bReturn = false;
				Reporter.reportStep("The field :"+field+" is disabled; hence failed","FAILURE");
			}			
		}
		if(bReturn){
			Reporter.reportStep("All the fields are enabled as expected","SUCCESS");
		}

		return bReturn;
	}

	/**
	 * verify the existance of fields
	 */
	public boolean verifyFieldsExistByXpath(String[] fields){
		Boolean bReturn = true;
		for (String field : fields) {
			if(!isExistByXpath(field)){
				bReturn = false;
				Reporter.reportStep("The field :"+field+" is not displayed; hence failed","FAILURE");
			}			
		}
		
		if(bReturn){
			Reporter.reportStep("All the fields are displayed as expected","SUCCESS");
		}

		return bReturn;
	}

	/**
	 * verify the non mandatory fields
	 */
	public boolean verifyMenuItems(String menuHeader, String[] expectedMenus){
		Boolean bReturn = true;

		List<WebElement> actualMenus = getAllElementsByXpath(menuHeader);
		if(expectedMenus.length != actualMenus.size())
			Reporter.reportStep("The menus expected: do not match with actual menus on the given page.", "FAILURE");

		for (int i = 0; i < actualMenus.size(); i++) {
			if(!objRep.getProperty(expectedMenus[i]).trim().equals(actualMenus.get(i).getText().trim())){
				Reporter.reportStep("The menu at index:"+i+" with expected value:"+objRep.getProperty(expectedMenus[i])+" do not match with actual menu text: "+actualMenus.get(i).getText()+" on the incident home page.", "FAILURE");
				bReturn = false;
			}
		}

		if(bReturn)
			Reporter.reportStep("The menus expected: matched with actual menus on the given page.", "SUCCESS");
		
		return bReturn;


	}

	public Boolean deleteAllFilters(){
		Boolean bReturn = false;
		try {
			List<WebElement> filterDeletes = getAllElementsByXpath("ALERT_MyGroups_Delete_Xpath");
			for (WebElement filterDelete : filterDeletes) {	
				filterDelete.click();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(getCountOfElementsByXpath("ALERT_MyGroups_Delete_Xpath") == 1)
			bReturn = true;
		
		return bReturn;
		
	}
	
	public boolean addNewFilter(String filterType,String filterCondition,String filterValue){
		boolean bReturn = false;
		
		try {
			if(!selectByVisibleTextByXpath("CIS_FirstFilterType1_Xpath",filterType))
				Reporter.reportStep("The Filter type "+ filterType+ " could not be selected","FAILURE");

			if(!selectByVisibleTextByXpath("CIS_FilterCondition1_Xpath",filterCondition))
				Reporter.reportStep("The Filter condition "+ filterCondition+ " could not be selected","FAILURE");
			
			if(!selectByVisibleTextByXpath("CIS_FilterValueSelect1_Xpath",filterValue))
				Reporter.reportStep("The Filter value "+ filterValue+ " could not be selected","FAILURE");
			
			bReturn = true;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bReturn;
	}

	public boolean verifyListContents(String xPathVal, String[] expectedValues){
		
		Boolean bReturn = true;
		List<String> optionValues = getOptionsByXpath(xPathVal);

		for (String expectedValue : expectedValues) {
			Boolean bMatch = false;
			for (String option : optionValues) {
					if(option.equals(expectedValue)){
						bMatch = true;
						optionValues.remove(option);
						break;
					}						
				}
			
			if(!bMatch){
				bReturn = false;
				break;
			}
		}
		
		return bReturn;
	}
	
	public boolean verifyTableHeaders(String xPathVal, String[] expectedValues){
		
		Boolean bReturn = true;
		String optionValues = getTextByXpath(xPathVal);
		for (String expectedValue : expectedValues) {
			Boolean bMatch = false;
		
			if(optionValues.contains(expectedValue)){
				bMatch = true;
				break;
			}						
			
			if(!bMatch){
				bReturn = false;
				break;
			}
		}
		
		return bReturn;
	}
	
	public int getColumnIndex(String xPathVal, String columnValue){
		
		int colIndex = 0;
		
		String[] optionValues = getTextByXpath(xPathVal).split("\n");
		
		for (int i=0; i < optionValues.length; i++) {
			if(optionValues[i].equals(columnValue)){
				colIndex = i+1;
				break;
			}			
		}
		
		return colIndex;
	}
}
