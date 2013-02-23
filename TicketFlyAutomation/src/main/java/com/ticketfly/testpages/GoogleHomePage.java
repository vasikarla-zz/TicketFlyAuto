package com.ticketfly.testpages;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.ticketfly.base.BrowserFactory;

public class GoogleHomePage {
	private static WebDriver browser = BrowserFactory.getBrowser();
	public static Logger log = Logger.getLogger(GoogleHomePage.class);

	/**
	 * Method to Search for a given string in Google Home Page
	 * 
	 * @param searchVal
	 * @return
	 * @throws Exception
	 */
	public boolean searchString(String searchVal) throws Exception {
		browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		log.info("Entering searchString Method with the Input Value : '"
				+ searchVal + "'.");
		if ((!searchVal.equals("")) && (!searchVal.equals(null))) {
			browser.findElement(By.xpath(".//*[@id='gbqfq']")).sendKeys(searchVal);
			browser.findElement(By.xpath(".//*[@id='gbqfq']")).sendKeys(Keys.RETURN);
			return true;
		} else {
			log.info("Exiting searchString Method as the Input Value is either Null or Blank.");
			return false;
		}
	}
}
