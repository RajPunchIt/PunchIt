package wrapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Reporter;


public abstract class GenericWrappers {

	static RemoteWebDriver driver;
	private static String primaryWindowHandle;
	public String sUrl, OD_Url, sHubUrl,sHubPort;
	protected Properties prop, objRep;

	public GenericWrappers() {

		// Load the property file
		prop = loadProperties("config.properties");
		objRep = loadProperties("object.properties");

		sHubUrl = prop.getProperty("HUB");
		sHubPort = prop.getProperty("PORT");

	}

	/**
	 * This method will launch only firefox and maximise the browser and set the
	 * wait for 30 seconds and load the url
	 * @author Rajkumar
	 * @param url - The url with http or https
	 * 
	 */
	public void launchApp() {
		launchApp("firefox",false);
	}

	/**
	 * This method will launch any browser and maximise the browser and set the
	 * wait for 30 seconds and load the url
	 * 
	 * @param browser - Browser of type firefox or chrome or ie
	 * @param url - The url with http or https
	 * @author Rajkumar
	 *  
	 */
	public boolean launchApp(String browser)  {
		return launchApp(browser, false);

	}

	/**
	 * This method will launch only firefox and maximise the browser and set the
	 * wait for 30 seconds and load the url
	 * @author Rajkumar
	 * @param url - The url with http or https
	 * 
	 */
	public boolean launchApp(String browser, boolean remote) {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		for (int i = 0; i < stElements.length; i++) {
			if(stElements[i].getClassName().startsWith("com.punchit.scripts")){
				String url = stElements[i].getClassName().substring(stElements[i].getClassName().lastIndexOf(".")+1).split("_")[0].toUpperCase();
				sUrl = prop.getProperty(url+"_URL");
				break;
			}
		}
		boolean bReturn = false;
		try {

			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setBrowserName(browser);
			dc.setPlatform(Platform.WINDOWS);

			if(remote)
				driver = new RemoteWebDriver(new URL("http://"+sHubUrl+":"+sHubPort+"/wd/hub"), dc);
			else{

				// if the browser is firefox
				if (browser.equals("firefox")) {
					driver = new FirefoxDriver();

				} else if (browser.equals("chrome")) {
					System.setProperty("webdriver.chrome.driver", "C:\\ServiceNow\\drivers\\chromedriver.exe");

					driver = new ChromeDriver();

				} else if (browser.equals("ie")) {
					System.setProperty("webdriver.ie.driver", "C:\\ServiceNow\\drivers\\IEDriverServer.exe");

					driver = new InternetExplorerDriver();

				} else if (browser.equals("headless")){
					DesiredCapabilities caps = new DesiredCapabilities();
					caps.setJavascriptEnabled(true);                
					caps.setCapability("takesScreenshot", true);  
					caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
							"drivers\\phantomjs.exe"
							);
					driver = new  PhantomJSDriver(caps);
				}
			}

			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(sUrl);

			primaryWindowHandle = driver.getWindowHandle();		
			Reporter.setResult(ServiceNowWrappers.testcaseName);
			bReturn = true;
			Reporter.reportStep("The browser:" + browser + " launched successfully", "SUCCESS");

			
		} catch (Exception e) {
			Reporter.reportStep("The browser:" + browser + " could not be launched", "FAILURE");
		}
		return bReturn;
	}

	/**
	 * This method will enter the value to the text field using id attribute to locate
	 * 
	 * @param idValue - id of the webelement
	 * @param data - The data to be sent to the webelement
	 * @author Rajkumar
	 * @throws IOException 
	 * @throws COSVisitorException 
	 */
	public boolean enterById(String idValue, String data) {
		boolean bReturn = false;
		try {
			driver.findElement(By.id(objRep.getProperty(idValue))).clear();
			driver.findElement(By.id(objRep.getProperty(idValue))).sendKeys(data);	
			bReturn = true;

		} catch (NoSuchElementException e) {
		} catch (WebDriverException e1) {
		}
		return bReturn;
	}

	/**
	 * This method will enter the value to the text field using xpath attribute to locate
	 * 
	 * @param xpathVal - xpath of the webelement
	 * @param data - The data to be sent to the webelement
	 * @author Rajkumar
	 */
	public boolean enterByXpath(String xpathVal, String data){
		boolean bReturn = false;
		try{
			driver.findElement(By.xpath(objRep.getProperty(xpathVal))).clear();
			driver.findElement(By.xpath(objRep.getProperty(xpathVal))).sendKeys(data);
			bReturn = true;
		} catch (NoSuchElementException e) {
		} catch (WebDriverException e1) {
		}
		return bReturn;
	}

	/**
	 * This method will enter the value to the text field using xpath attribute to locate
	 * 
	 * @param xpathVal - xpath of the webelement
	 * @param data - The data to be sent to the webelement
	 * @author Rajkumar
	 */
	public boolean enterByXpathAndClick(String xpathVal, String data){
		boolean bReturn = false;
		try{
			driver.findElement(By.xpath(objRep.getProperty(xpathVal))).clear();
			driver.findElement(By.xpath(objRep.getProperty(xpathVal))).sendKeys(data, Keys.ENTER);
			bReturn = true;

		} catch (NoSuchElementException e) {
		} catch (WebDriverException e1) {
		}
		return bReturn;
	}

	/**
	 * This method will enter the value to the text field using xpath attribute to locate
	 * 
	 * @param xpathVal - xpath of the webelement
	 * @param data - The data to be sent to the webelement
	 * @author Rajkumar
	 */
	public boolean enterAndChoose(String xpathVal, String data){
		boolean bReturn = false;
		try{
			WebElement ele = driver.findElement(By.xpath(objRep.getProperty(xpathVal)));
			
			ele.clear();

			Actions builder = new Actions(driver);			
			builder.sendKeys(ele, data)
			.pause(4000)
			.sendKeys(Keys.DOWN)
			.sendKeys(Keys.ENTER)
			.build().perform();
			
			if(!getAttributeByXpath(xpathVal, "title").equals("Invalid reference"))
				bReturn = true;

		} catch (NoSuchElementException e) {
		} catch (WebDriverException e1) {

		}
		return bReturn;
	}

	/**
	 * This method will click by class name 
	 * @param classValue - class locator 
	 * @author Rajkumar
	 */
	public boolean clickByClassName(String classValue){
		boolean bReturn = false;
		try {
			driver.findElement(By.className(objRep.getProperty(classValue))).click();
			bReturn = true;

		} catch (NoSuchElementException e) {
		} catch (WebDriverException e1) {

		}
		return bReturn;
	}

	/**
	 * This method will verify the title of the browser 
	 * @param title - The expected title of the browser
	 * @author Rajkumar
	 */
	public boolean verifyTitle(String title){
		boolean bReturn = false;
		try{
			if (driver.getTitle().equalsIgnoreCase(title))
				bReturn = true;
		}catch(Exception e){

		}

		return bReturn;
	}

	// Close browser
	public void quitBrowser() {
		driver.quit();
		Reporter.reportStep("The browser closed successfully", "SUCCESS");

	}

	public boolean clickLink(String linkName) {
		return clickLink(linkName,true);

	}

	public boolean clickLink(String linkName, boolean bObjRep) {
		boolean bReturn = false;
		String loc = linkName;
		try{
			if(bObjRep)
				loc = objRep.getProperty(linkName);				

			driver.findElement(By.linkText(loc)).click();
			bReturn = true;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		} catch (WebDriverException e1) {
			e1.printStackTrace();

		}
		return bReturn;

	}

	public boolean clickPartialLink(String linkName) {
		boolean bReturn = false;
		try{
			driver.findElement(By.partialLinkText(objRep.getProperty(linkName))).click();
			bReturn = true;

		} catch (NoSuchElementException e) {
		} catch (WebDriverException e1) {
		}
		return bReturn;

	}

	public boolean clickById(String id) {
		boolean bReturn = false;
		try{
			driver.findElement(By.id(objRep.getProperty(id))).click();
			bReturn = true;

		} catch (NoSuchElementException e) {
		} catch (WebDriverException e1) {
		}
		return bReturn;
	}

	public boolean clickByName(String name) {
		boolean bReturn = false;
		try{
			driver.findElement(By.name(objRep.getProperty(name))).click();
			bReturn = true;

		} catch (NoSuchElementException e) {
		} catch (WebDriverException e1) {
		}
		return bReturn;
	}

	// Click by xpath
	public boolean clickByXpath(String xpathVal) {
		boolean bReturn = false;
		try{
			driver.findElement(By.xpath(objRep.getProperty(xpathVal))).click();
			bReturn = true;

		} catch (NoSuchElementException e) {
		} catch (WebDriverException e1) {
		}
		return bReturn;
	}

	// Click by css
	public boolean clickByCss(String css) {
		boolean bReturn = false;
		try{
			driver.findElement(By.cssSelector(objRep.getProperty(css))).click();
			bReturn = true;

		} catch (NoSuchElementException e) {
		} catch (WebDriverException e1) {
		}
		return bReturn;
	}

	// Select visible text by xpath
	public boolean selectByVisibleTextByXpath(String xpathVal, String val) {
		boolean bReturn = false;
		try{
			new Select(driver.findElement(By.xpath(objRep.getProperty(xpathVal)))).selectByVisibleText(val);
			bReturn = true;
		} catch (NoSuchElementException e) {
		} catch (WebDriverException e1) {
		}
		return bReturn;
	}

	public boolean selectByVisibleTextById(String id, String val) {
		boolean bReturn = false;
		try{
			new Select(driver.findElement(By.id(objRep.getProperty(id)))).selectByVisibleText(val);
			bReturn = true;
		} catch (NoSuchElementException e) {
		} catch (WebDriverException e1) {
		}
		return bReturn;
	}

	public String getDefaultValueById(String id) {
		String retValue = "";
		try{
			retValue = getDefaultValue(driver.findElement(By.id(objRep.getProperty(id))));
			System.out.println(retValue);
		} catch (NoSuchElementException e) {
		} catch (WebDriverException e1) {
		}
		return retValue;
	}

	public String getDefaultValueByXpath(String xpath) {
		String retValue = "";
		try{
			retValue = getDefaultValue(driver.findElement(By.xpath(objRep.getProperty(xpath))));
		} catch (NoSuchElementException e) {
		} catch (WebDriverException e1) {
		}
		return retValue;
	}

	public String getDefaultValue(WebElement ele) {
		String retValue = "";
		try{
			retValue = new Select(ele).getFirstSelectedOption().getText();
		} catch (NoSuchElementException e) {
		} catch (WebDriverException e1) {
		}
		return retValue;
	}

	public List<String> getOptionsById(String id) {
		return getOptions(driver.findElement(By.id(objRep.getProperty(id))));
	}

	public List<String> getOptionsByXpath(String xpath) {
		return getOptions(driver.findElement(By.xpath(objRep.getProperty(xpath))));
	}

	public List<String> getOptions(WebElement ele) {
		List<String> retValues = new ArrayList<>();
		try{
			List<WebElement> options = new Select(ele).getOptions();			
			for (WebElement optionEle : options) {
				retValues.add(optionEle.getText());
			}
		} catch (NoSuchElementException e) {
		} catch (WebDriverException e1) {
		}
		return retValues;
	}

	// switch to the frame until it is available within timeout
	public boolean switchToFrame(String nameOrId) {
		boolean bReturn = false;
		try {
			driver.switchTo().defaultContent();

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(objRep.getProperty(nameOrId)));
			bReturn = true;
		} catch (WebDriverException e1) {
		}
		return bReturn;
	}

	public WebDriver goOutOfFrame() {
		return driver.switchTo().defaultContent();
	}

	// instead pass an argument for number of window to be switched...
	// have a counter to check the nth window switched 
	public void switchToSecondWindow() {
		Set<String> winHandles = driver.getWindowHandles();
		for (String winHandle : winHandles) {
			driver.switchTo().window(winHandle);
		}

	}

	public void switchToPrimary() {
		driver.switchTo().window(primaryWindowHandle);		
	}

	public String getAttributeById(String id, String attribute) {
		return getAttributeById(id, attribute, true);
	}

	public String getAttributeByXpath(String xpathVal, String attribute) {
		String attrVal = "";
		try{
			attrVal = driver.findElement(By.xpath(objRep.getProperty(xpathVal))).getAttribute(attribute);
		}catch(Exception e){
			
		}
		return attrVal;
	}

	public String getAttributeById(String id, String attribute, boolean bObjRep) {
		String loc = id;
		if(bObjRep)
			loc = objRep.getProperty(id);
		return driver.findElement(By.id(loc)).getAttribute(attribute);
	}



	public RemoteWebDriver getDriver(){
		return driver;
	}

	public void Wait(long waitTime){
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void alertAccept(){
		driver.switchTo().alert().accept();
	}

	public String getTextAndAcceptAlert(){
		String sText = "";
		try{
			sText = driver.switchTo().alert().getText();
			alertAccept();
		} catch(Exception e){

		}
		return sText;
	}

	public boolean switchToDefault() {
		boolean bReturn = false;
		try {
			driver.switchTo().defaultContent();	
			bReturn = true;
		} catch (WebDriverException e1) {
		}
		return bReturn;
	}

	public boolean sendKeys(String xpathVal, Keys keyName) {
		boolean bReturn = false;
		try {
			Actions builder = new Actions(driver);
			builder.contextClick(driver.findElement(By.xpath(objRep.getProperty(xpathVal)))).pause(10000)
			.sendKeys(keyName)
			.build().perform();	
			bReturn = true;
		} catch (WebDriverException e1) {
		}
		return bReturn;
	}

	public int getCountOfElementsByXpath(String xpathVal){
		return getCountOfElementsByXpath(xpathVal, true);
	}

	public int getCountOfElementsByXpath(String xpathVal, boolean bObjRep){
		if(bObjRep)
			return getCountOfElements(driver.findElementsByXPath(objRep.getProperty(xpathVal)));
		else
			return getCountOfElements(driver.findElementsByXPath(xpathVal));

	}

	
	public int getCountOfElementsById(String id){
		return getCountOfElements(driver.findElementsById(objRep.getProperty(id)));
	}
	
	public int getCountOfElements(List<WebElement> eles){
		return eles.size();
	}

	public List<WebElement> getAllElementsByXpath(String xpathVal){
		return driver.findElementsByXPath(objRep.getProperty(xpathVal));
	}

	// Select visible text by xpath
	public String getTextByXpath(String xpathVal) {
		String sText = "";
		try{
			sText = getText(driver.findElement(By.xpath(objRep.getProperty(xpathVal))));

		} catch (NoSuchElementException e) {
		} catch (WebDriverException e1) {
		}	
		return sText;

	}

	// Select visible text by xpath
	public String getTextById(String id) {
		String sText = "";
		try{
			sText = getText(driver.findElement(By.id(objRep.getProperty(id))));

		} catch (NoSuchElementException e) {
		} catch (WebDriverException e1) {
		}	
		return sText;

	}

	public String getText(WebElement ele) {
		String sText = "";
		try{
			sText = ele.getText();

		} catch (NoSuchElementException e) {
		} catch (WebDriverException e1) {
		}	
		return sText;

	}

	// Select visible text by xpath
	public boolean verifyTextByXpath(String xpathVal, String text) {
		boolean bReturn = false;
		try {
			if(text.trim().equals(getTextByXpath(xpathVal))){
				bReturn = true;
			}
		} catch (Exception e) {
		}
		return bReturn;

	}

	public boolean rightClickById(String id){	
		return rightClick(driver.findElement(By.id(objRep.getProperty(id))));

	}

	public boolean rightClickByLinkText(String linkName){	
		return rightClickByLinkText(linkName, true);
	}

	public boolean rightClickByLinkText(String linkName, Boolean bObjRep){	
		if(bObjRep)
			return rightClick(driver.findElement(By.linkText(objRep.getProperty(linkName))));
		else
			return rightClick(driver.findElement(By.linkText(linkName)));
	}

	
	public boolean rightClickByXpath(String xpathVal){	
		return rightClick(driver.findElement(By.xpath(objRep.getProperty(xpathVal))));
	}
	
	public boolean rightClick(WebElement ele){	
		boolean bReturn = false;
		try{
			// Right click and save	
			Actions builder = new Actions(driver);
			builder.contextClick(ele).pause(4000)
			.build().perform();	
			bReturn = true;
		}catch(Exception e){

		}

		return bReturn;
	}
	
	

	public boolean IsElementNotPresentByXpath(String xpathVal){	
		boolean bReturn = false;

		resetImplicitWait(5);

		if(getCountOfElementsByXpath(xpathVal) == 0)
			bReturn = true;

		resetImplicitWait(30);
		return bReturn;
	}

	public boolean IsElementNotPresentById(String id){	
		boolean bReturn = false;

		resetImplicitWait(5);

		if(getCountOfElementsById(id) == 0)
			bReturn = true;

		resetImplicitWait(30);

		return bReturn;
	}

	public boolean IsElementPresentByXpath(String xpathVal){	
		return !IsElementNotPresentByXpath(xpathVal);
	}

	public boolean IsElementPresentById(String id){	
		return !IsElementNotPresentById(id);
	}

	public Properties loadProperties(String fileName){
		// Load the property file
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("C:\\ServiceNow\\"+fileName));
		} catch (FileNotFoundException e) {
			Reporter.reportStep("The property file:"+fileName+" is not found", "FAILURE");
		} catch (IOException e) {
			Reporter.reportStep("The property file:"+fileName+" could not loaded", "FAILURE");
		}	
		return prop;
	}

	public void resetImplicitWait(long wait){
		driver.manage().timeouts().implicitlyWait(wait, TimeUnit.SECONDS);
	}

	public boolean isExistById(String id) {
		return isExist(driver.findElementById(objRep.getProperty(id)));
	}

	public boolean isExistByXpath(String xpathVal){
		return isExist(driver.findElementByXPath(objRep.getProperty(xpathVal)));

	}

	public boolean isExist(WebElement ele) {
		return ele.isDisplayed();
	}

	public boolean isEnabledById(String id) {
		return isEnabled(driver.findElementById(objRep.getProperty(id)));
	}

	public boolean isEnabledByXpath(String xpathVal){
		return isEnabled(driver.findElementByXPath(objRep.getProperty(xpathVal)));

	}

	public boolean isEnabled(WebElement ele) {
		return ele.isEnabled();
	}

	// Verify Attribute text by xpath
	public boolean verifyAttributeTextByXpath(String xpathVal,String attribute, String attrValue) {
		boolean bReturn = false;
		try {
			if(attrValue.trim().equals(getAttributeByXpath(xpathVal, attribute))){
				bReturn = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bReturn;

	}

	// Verify  Attribute text by Id
	public boolean verifyAttributeTextById(String xpathVal,String attribute, String attrValue) {
		boolean bReturn = false;
		try {
			if(attrValue.trim().equals(getAttributeById(xpathVal, attribute))){
				bReturn = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bReturn;

	}

	public void refresh() {
		boolean bReturn = false;
		try {
			driver.navigate().refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void mouseOverById(String id) {
		mouseOver(driver.findElementById(objRep.getProperty(id)));
	}

	public void mouseOverByXpath(String xpathVal){
		mouseOver(driver.findElementByXPath(objRep.getProperty(xpathVal)));

	}

	public void mouseOver(WebElement ele) {
		new Actions(driver).moveToElement(ele).build().perform();
	}
	

}

