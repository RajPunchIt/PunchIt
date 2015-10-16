package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class QueryDB {
	
	static String dbName;
	static String serverip;
    static String url;
    static String prefix;
    static String username;
    static String password;
	
	public static void setUp() {
		
		try {
			// Load the property file
			Properties prop = new Properties();
			prop.load(new FileInputStream("C:\\ServiceNow\\config.properties"));	
			
			dbName = prop.getProperty("DBNAME");
			serverip = prop.getProperty("DBIP");
			username = prop.getProperty("DBUSERNAME");
			password = prop.getProperty("DBPASSWORD");
			url = "jdbc:mysql://"+serverip+":3306/"+dbName+"?user="+username+"&password="+password;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}
   
	public static String getBrowserName(String entityId) {
		
    	Connection conn = null;
        Statement stmt = null;
        ResultSet result = null;
        String browserName = "";
        
        setUp(); // initialize 

        String driver = "com.mysql.jdbc.Driver";
         try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url);
            
            stmt = conn.createStatement();
            result = null;

            result = stmt.executeQuery("SELECT browser AS browserName FROM wa_testrun_run run join wa_testrun_runtc runtc on run.entity_id = runtc.run_id where runtc.entity_id = "+entityId);
            while(result.next()){
            	browserName = result.getString("browserName");
            }
            
           
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		return browserName;
    }
   
	// Status - PASS, FAIL, ERROR, INSUCFFICENT DATA
	// @ add - time for execution  
	public static void updateResult(String entityId, String status, String errorMsg, long execTime) {
    	Connection conn = null;
        Statement stmt = null;
        
        setUp();

        String driver = "com.mysql.jdbc.Driver";
         try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            stmt.executeUpdate("Update wa_testrun_runtc Set result ='"+status+"', status = 2,  execution ='"+errorMsg+"', execTime = "+execTime+" where entity_id = "+entityId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
  

    
	public static String verifyResult(String entityId) {
		
    	Connection conn = null;
        Statement stmt = null;
        ResultSet result = null;
        String browserName = "";
        
        setUp(); // initialize 

        String driver = "com.mysql.jdbc.Driver";
         try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url);
            
            stmt = conn.createStatement();
            result = null;

            result = stmt.executeQuery("SELECT result, status, execution, exectime FROM wa_testrun_runtc where entity_id = "+entityId);
            while(result.next()){
            	System.out.println(result.getString("result"));
            	System.out.println(result.getInt("status"));
            	System.out.println(result.getString("execution"));
            	System.out.println(result.getInt("exectime"));
            }
            
           
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		return browserName;
    }
   

}