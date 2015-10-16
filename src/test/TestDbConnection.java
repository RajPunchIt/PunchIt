package test;

import org.testng.annotations.Test;

import utils.QueryDB;

public class TestDbConnection {

	@Test
	public void run() {
		//QueryDB.getAllData();
	String browser = QueryDB.getBrowserName("388");
	System.out.println("Browser is: "+browser);
		//QueryDB.updateResult("402", "FAIL", "The login is not successful", 63);
		
		//QueryDB.verifyResult("405");
	}
}