package TS_Settings;

import org.testng.Assert;
import org.testng.annotations.Test;

import resources.TestcaseExecutor;


public class TC_Settings_test_02 extends TestcaseExecutor{
	
	@Test()
	public void settings_sample_test02(){
		browser.get("http://www.login-test01.cloud.xirrus.com");
		System.out.println(browser.getTitle());
		System.out.println("Settings saple 02");
		Assert.assertEquals(true, false);
	}

}
