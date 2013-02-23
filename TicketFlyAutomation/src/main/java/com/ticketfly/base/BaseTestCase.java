package com.ticketfly.base;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.ticketfly.test.framework.utils.CommonUtils;


/**
 * All the Test classes in the project will extend this BaseTestCase Contains
 * methods using testng annotations that perform operations
 * beforesuite,aftersuite,beforetest,aftertest etc.
 */
public class BaseTestCase {

	public static Logger log = Logger.getLogger(BaseTestCase.class);
	private static WebDriver browser = BrowserFactory.getBrowser();

	/**
	 * Place holder for any invocations before the test suite.
	 * 
	 * @throws Exception
	 */
	@BeforeSuite(alwaysRun = true)
	public void startSuiteTest() throws Exception {
	}

	/**
	 * Invokes the base URL before any launch of the test
	 * 
	 * @throws Exception
	 */
	@BeforeTest
	public void launchBaseURL() throws Exception {
		String url = CommonUtils.readFromConfig("BaseURL");
		log.info(" Launching Browser  " + url);
		browser.get(url);
		log.info(" Launched Browser successfully  " + url);
	}

	/**
	 * 
	 * closes the Browser object
	 * 
	 * @throws Exception
	 */
	@AfterSuite(alwaysRun = true)
	public void resetAndCloseBrowser() {
		try {
			log.info(" Qutting the browser ");
			browser.quit();
			log.info(" Qutting the browser successful");
		} catch (Exception e) {
			log.error("Exception in resetAndCloseBrowser function"
					+ e.getMessage());
		}
	}
}
