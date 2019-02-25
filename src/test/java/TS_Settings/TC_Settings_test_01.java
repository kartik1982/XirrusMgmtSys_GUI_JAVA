package TS_Settings;

import org.testng.annotations.Test;

import resources.TestcaseExecutor;


public class TC_Settings_test_01 extends TestcaseExecutor{
	
	@Test
	public void settings_sample_test01(){
	
		browser.get("http://www.login-test01.cloud.xirrus.com");
		System.out.println(browser.getTitle());
		System.out.println("Settings saple 01");
	}

}
