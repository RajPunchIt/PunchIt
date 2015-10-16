package testng;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import utils.QueryDB;
import utils.Reporter;
import wrapper.ServiceNowWrappers;

public class SuiteMethods {
	
	protected String browserName = "firefox";
	protected String entityId;
	protected String status = "FAIL";
	protected ITestContext context;
	
	// Create Instance
	ServiceNowWrappers snW;

	@BeforeClass
	public void getBrowserName(ITestContext context) {
		this.context = context;
		entityId = context.getCurrentXmlTest().getSuite().getName();
		entityId = "402";
		browserName = QueryDB.getBrowserName(entityId);
		System.out.println("Sending the request to run browser :"+browserName+" to the hub..");
		browserName = "chrome";
	}
	
	@AfterClass(alwaysRun = true)
	public void updateResult(ITestContext context) {
		String errorMsg = Reporter.getErrorDescription();
		long executionTime = (System.currentTimeMillis() - context.getStartDate().getTime())/1000;
		if(status.equals("FAIL") && errorMsg.trim().equals(""))
			status = "ERROR";
				
		System.out.println(context.getCurrentXmlTest().getSuite().getName());
		System.out.println(status);
		System.out.println(errorMsg);
		System.out.println(executionTime);
		QueryDB.updateResult(entityId, status, Reporter.getErrorDescription(), executionTime);
	}

}
