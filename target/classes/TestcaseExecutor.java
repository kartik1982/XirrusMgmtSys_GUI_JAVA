package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import testbase.TestBase;

public class TestcaseExecutor extends TestBase{

	@BeforeSuite
	public void prepareTestbedForTestsuiteExecution() throws ClassNotFoundException, SQLException, IOException {
		//Load Config file
		FileInputStream fileIN = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\\\resources\\configuration.properties");
		config.load(fileIN);
		//Initialize Project level variables
		projectID="XirrusMgmtSys_GUI";
		releaseID="9.0.0";
		testcycleID= "development"; //development or regression
		buildID="4852-23420"; //UI version - Backend version
		//Initialize SQL connection variables
		sqlUser= "kartik01";
		sqlPassword = "Xirrus!23";				
		sqlHost="10.100.185.3";
		sqlPort="3306";
		sqlDatabase="xirrustestdb";		
		//Initialize XMSC variables
		baseURL="https://login-test03.cloud.xirrus.com";
		xmsUserName = config.getProperty("username");
		xmsPassword= config.getProperty("password");	
		//Selenium variables
		browserName = config.getProperty("browser").trim();
		baseURL=config.getProperty("test_url");
		//Initalize Logger
		initalizeLogger("xmsc");
		//Establish Database connection
		estabilishDatabaseConnection(sqlHost, sqlPort, sqlDatabase, sqlUser, sqlPassword);
//		//Get backoffice API token - NOT NEEDED IF GUI TEST CASE IS EXECUTING
//		getBackofficeAPIToken(baseURL, xmsUserName, xmsPassword);		
	}
	
	@BeforeTest
	public void setEnviornmentForTestcaseExecution() {
		//Selenium Browser initalize 
		initalizeBrowser_redirectToTestURL(browserName, baseURL);
	}
	
	@AfterTest 
	public void cleanupAfterTestcaseExecution(){
		browser.close();
    	System.out.println("Browser closed after testing");
	}
	
	@AfterSuite
	public void cleanupTestbedAfterTestSuiteExecution() throws SQLException {
		browser.quit();
		browser=null;
		testdbConn.close();
	}

}
