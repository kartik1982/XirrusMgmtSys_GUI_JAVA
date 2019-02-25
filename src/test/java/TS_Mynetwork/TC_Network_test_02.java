package TS_Mynetwork;

import org.testng.annotations.Test;

import resources.TestcaseExecutor;

public class TC_Network_test_02 extends TestcaseExecutor{
	
	@Test
	public void network_sample_test02(){
	
		browser.get("http://www.login-test01.cloud.xirrus.com");
		System.out.println(browser.getTitle());
		System.out.println("*****************8network saple 02**********************");
	}

}
