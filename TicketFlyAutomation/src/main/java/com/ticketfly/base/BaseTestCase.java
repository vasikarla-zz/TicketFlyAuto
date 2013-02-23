package com.ticketfly.base;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
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
		Browser.go(url);
		log.info(" Launched Browser successfully  " + url);
	}

	/**
	 * Takes the screenshot after execution of TestMethod. Screen shots base
	 * path will be read from config name
	 * 
	 * @throws Exception
	 */
	@AfterMethod
	public void takeScreenShot(Method methodName) {
		try {
			log.info(" capturing screenshot " + methodName);
			if (methodName != null) {

				String path = CommonUtils.readFromConfig("screenshotpath")
						+ "TC_"
						+ methodName.getName().toString()+ "/"
						+ methodName.getName();
				Browser.captureScreenShot(CommonUtils.currentDateFileName(path)
						+ ".jpeg");

				log.info(" capturing screenshot methodName"
						+ methodName.getName());
			}

		} catch (Exception e) {
			log.error(" capturing screenshot exception " + e.getMessage());
		}

		log.info(" capturing screenshot successfully ");
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
			Browser.quit();
			log.info(" Qutting the browser successful");
		} catch (Exception e) {
			log.error("Exception in resetAndCloseBrowser function"
					+ e.getMessage());
		}
	}

	/**
	 * Place holder for any invocations after the test suite.
	 * 
	 * @throws Exception
	 */
	public void finalTestReport() throws Exception {

	}

}
