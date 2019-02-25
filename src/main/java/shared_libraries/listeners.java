package shared_libraries;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import testbase.TestBase;

public class listeners implements ITestListener, ISuiteListener{
	TestBase base = new TestBase();
	String testResult;

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		testResult=null;
		System.out.println("I am start of test");		
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		testResult="PASS";

		try {
			base.captureScreenshot(result.getName()+"success");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		testResult="FAIL";
		try {
			base.captureScreenshot(result.getName()+"failure");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		testResult="SKIP";
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		base.testcaseID=context.getName();
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		base.testcaseID=null;
		try {
			base.postTestResult(context.getSuite().getName(), context.getName(), testResult, context.getStartDate().getTime(), context.getEndDate().getTime());
		} catch (IOException | ParseException | SQLException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	@Override
	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		base.testsuiteID=suite.getName();
	}

	@Override
	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		base.testsuiteID=null;
		
	}

}
