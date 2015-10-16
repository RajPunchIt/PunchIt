package utils;

import wrapper.ServiceNowWrappers;
import atu.testng.reports.ATUReports;
import atu.testng.reports.utils.Utils;

public class Reporter {
	
	private static String errorDescription;
	
	public static void reportStep(String desc, String status) {
		
		System.out.println(desc);
		setErrorDescription("");
		PdfWrite.appendReport(ServiceNowWrappers.testcaseName+".pdf", desc);

		// Write if it is successful or failure
		if(status.toUpperCase().equals("SUCCESS")){
			//ATUReports.add(desc, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}else if(status.toUpperCase().equals("WARNING")){
			//ATUReports.add(desc, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}else {
			setErrorDescription(desc);	
			throw new RuntimeException("FAILED");
		}
	}
	
	public static void setResult(String testcaseName){
		PdfWrite.createNewPdf(testcaseName+".pdf");

		//ATUReports.setWebDriver(new ServiceNowWrappers().getDriver());
		//ATUReports.indexPageDescription = "Service Now Project";
		//ATUReports.setAuthorInfo("Rajkumar",  Utils.getCurrentTime(), "1.0");
	}

	public static String getErrorDescription() {
		return errorDescription;
	}

	private static void setErrorDescription(String errorDescription) {
		Reporter.errorDescription = errorDescription;
	}

}
