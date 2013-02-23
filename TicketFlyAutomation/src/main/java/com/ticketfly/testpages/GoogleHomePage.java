package com.ticketfly.testpages;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import com.ticketfly.base.Browser;

public class GoogleHomePage {

	public static Logger log = Logger.getLogger(GoogleHomePage.class);

	/**
	 * Method to Search for a given string in Google Home Page
	 * 
	 * @param searchVal
	 * @return
	 * @throws Exception
	 */
	public boolean searchString(String searchVal) throws Exception {
		log.info("Entering searchString Method with the Input Value : '"
				+ searchVal + "'.");
		if ((!searchVal.equals("")) && (!searchVal.equals(null))) {
			Browser.sendKeys("xpath=.//*[@id='gbqfq']", searchVal);
			Browser.sendKeyboardInput("xpath=.//*[@id='gbqfq']", Keys.RETURN);
			Browser.wait("5000");
			return true;
		} else {
			log.info("Exiting searchString Method as the Input Value is either Null or Blank.");
			return false;
		}
	}
}
