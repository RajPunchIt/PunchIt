package pages;


import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Reporter;
import wrapper.ServiceNowWrappers;

public class MenuPage extends ServiceNowWrappers{
	
    private final RemoteWebDriver driver;

    public MenuPage(RemoteWebDriver driver) {
        this.driver = driver;

        // Check that we're on the right page.
        if (!isExistById("Welcome_Id")) {
            Reporter.reportStep("This is not the home page", "FAILURE");
        }
    }

    public MenuPage switchToMenuFrame(){
    	
    	// Switch to the menu frame
    	switchToFrame("Frame_Nav");
    	
    	return this;
    }
    
    public MenuPage verifyExistanceOfIncidentMenus(){
    	
    	// Switch to the menu frame
    	switchToMenuFrame();
    	    	
    	// you need to change the menu names when the application changes
    	// also maintain the order of the links as in screen
    	String[] expectedMenus = {	"INCMENU_NEW",
    								"INCMENU_ASSIGN",
    								"INCMENU_WIP",
    								"INCMENU_OPEN",		 
    								"INCMENU_UNASSIGN",	
    								"INCMENU_RESOLVED",	
    								"INCMENU_CLOSED",	
    								"INCMENU_ALL",	
    								"INCMENU_OVERVIEW",	
    								"INCMENU_CIM"};


    	// Verify the menus of the incident
    	verifyMenuItems("INCMENU_ALLMENU",expectedMenus);

		return this;
    	
    }
    
    public IncidentPage clickCreateNew(){
    	
    	// Switch to the menu frame
    	switchToMenuFrame();
    	
    	// Click create new
    	if(clickLink("INCMENU_NEW"))
    		Reporter.reportStep("The create new link is clicked", "SUCCESS");
    	else
    		Reporter.reportStep("The create new link is not found or clicked.", "FAILURE");
    	
    	return new IncidentPage(driver);
    }
    
    public IncidentPage clickWIP(){
    	
    	// Switch to the menu frame
    	switchToMenuFrame();
    	
    	// Click create new
    	if(clickLink("INCMENU_WIP"))
    		Reporter.reportStep("The Work In Progress link is clicked", "SUCCESS");
    	else
    		Reporter.reportStep("The Work In Progress link is not found or clicked.", "FAILURE");
    	
    	return new IncidentPage(driver);
    }
    
  public IncidentsListPage clickOpen(){
    	
    	// Switch to the menu frame
    	switchToMenuFrame();
    	
    	// Click create new
    	if(clickLink("INCMENU_OPEN"))
    		Reporter.reportStep("The Open link is clicked", "SUCCESS");
    	else
    		Reporter.reportStep("The Open is not found or clicked.", "FAILURE");
    	
    	return new IncidentsListPage(driver);
    }
       

}
