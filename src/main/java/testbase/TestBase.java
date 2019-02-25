package testbase;

import org.testng.SkipException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestBase {
	//Project level arguments
	public static String projectID=null;
	public static String releaseID=null;
	public static String testcycleID= null; //development or regression
	public static String buildID=null; //UI version - Backend version
	//Test level arguments
	public static String testcaseID=null;
	public static String testsuiteID=null;
	//SQL arguemnts
	public static Connection testdbConn;
	public String sqlHost;
	public String sqlPort;
	public String sqlDatabase;
	public String sqlUser;
	public String sqlPassword;
	String sqlQuery;
	PreparedStatement sqlPrepStat;
	String sqlServerURL;
	//XMS-C Arguemnts
	public static String baseURL=null;
	public static String xmsUserName=null;
	public static String xmsPassword=null;
	//Seleneium arguments
	public static WebDriver browser;	
	public static String browserName;	
	int timeout=10;
	//API Arguments
	public static String resource_path=null;
	public String payload=null;
	public static String token=null;
	public static String tokenType=null;
	
	
	File file;
	FileWriter filestream;
    BufferedWriter out;
	public static Properties config = new Properties();
    Logger log;

    //Common methods
	public void initalizeLogger(String name) {
		log= LogManager.getLogger(name);
	}
    public void initalizeBrowser_redirectToTestURL(String browserName, String testURL) {
		if (browserName.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", "c://Webdrivers//chromedriver.exe");
			browser = new ChromeDriver();
		}
		browser.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		browser.get(testURL);
		System.out.println("Browser open for testing");
    }	
	public void estabilishDatabaseConnection(String sqlHost, String sqlPort, String sqlDatabase, String sqlUser, String sqlPassword) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		sqlServerURL="jdbc:mysql://"+sqlHost+":"+sqlPort+"/"+sqlDatabase;
		testdbConn= DriverManager.getConnection(sqlServerURL, sqlUser, sqlPassword);
	}
	public void captureScreenshot(String filename) throws IOException {
		File src=((TakesScreenshot)browser).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File("C://CapturedImage//screenshot_"+filename+".png"));
	}
	
	public void postTestResult(String testSuiteName, String testCaseName, String testResult, long startDateTime, long endDateTime) throws IOException, ParseException, SQLException, ClassNotFoundException {	
		SimpleDateFormat dt= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startDate= dt.format(startDateTime);
		String endDate= dt.format(endDateTime);
		file =new File(System.getProperty("user.dir")+"\\logs\\resultfile.txt");
		filestream = new FileWriter(file, true);
		out = new BufferedWriter(filestream);
		out.write(testSuiteName+"||"+testCaseName+"||"+testResult+"||"+startDate+"||"+endDate);
		out.newLine();
		out.close();
		insertTestResultIntoDataBase(testSuiteName, testCaseName, testResult, startDate, endDate);
		
	}
	
	public void insertTestResultIntoDataBase(String testSuiteName, String testCaseName, String testResult, String startDate, String endDate) throws SQLException, ClassNotFoundException {
		//Create SQL Query  
		sqlQuery = "insert into testexecutionresults (releaseID, projectID, testcycleID, buildID, testsuiteID, testcaseID, testcaseResult, testcaseStartTime, testcaseEndTime, testcaseLogPath)"+ 
				" values (?,?,?,?,?,?,?,?,?,?)";
		// create the mysql insert preparedstatement
		sqlPrepStat = testdbConn.prepareStatement(sqlQuery);
		sqlPrepStat.setString (1, releaseID);
		sqlPrepStat.setString (2, projectID);
		sqlPrepStat.setString (3, testcycleID);
		sqlPrepStat.setString (4, buildID);
		sqlPrepStat.setString (5, testSuiteName);
		sqlPrepStat.setString (6, testCaseName);
		sqlPrepStat.setString (7, testResult);
		sqlPrepStat.setString (8, startDate);
		sqlPrepStat.setString (9, endDate);
		sqlPrepStat.setString (10, "c:\\logs.txt");
		// execute the preparedstatement
	    sqlPrepStat.execute();
	}
	
	public void isTestsuiteExecutable(String testsuiteName) throws SQLException, ClassNotFoundException {
		String testsuite=testsuiteID;		
		//Prepare SQL Query 
		sqlQuery="select * from testsuites_list where testsuiteID='"+testsuite+"'";
		sqlPrepStat = testdbConn.prepareStatement(sqlQuery);
		//Execute SQL Query
        ResultSet rs = sqlPrepStat.executeQuery();	      
	    rs.next();
		System.out.println(rs.getString("testsuiteID"));
		System.out.println(rs.getString("description"));
		System.out.println(rs.getBoolean("runMode"));
		if(!rs.getBoolean("runMode")) {
			throw new SkipException("Skipped Test case "+testsuiteID+", was set as No");
		}
	}
	
	public void isTestcaseExecutable(String testcaseName) throws ClassNotFoundException, SQLException {
		String testcase= testcaseID;
		sqlQuery="select * from testcases_list where testcaseID='"+testcase+"'";
		sqlPrepStat = testdbConn.prepareStatement(sqlQuery);
        ResultSet rs = sqlPrepStat.executeQuery();
        rs.next();
		System.out.println(rs.getString("testcaseID"));
		System.out.println(rs.getString("description"));
		System.out.println(rs.getBoolean("runMode"));
		if(!rs.getBoolean("runMode")) {
			throw new SkipException("Skipped Test case "+testcaseID+", was set as No");
		}
	}

}
