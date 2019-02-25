package TS_Profile;

import org.testng.annotations.Test;

import resources.TestcaseExecutor;

public class TC_Profile_test_02 extends TestcaseExecutor{
	
	@Test
	public void profile_sample_test02(){
	
		browser.get("http://www.login-test01.cloud.xirrus.com");
		System.out.println(browser.getTitle());
		System.out.println("************************Profile saple 02****************************");
	}

}
