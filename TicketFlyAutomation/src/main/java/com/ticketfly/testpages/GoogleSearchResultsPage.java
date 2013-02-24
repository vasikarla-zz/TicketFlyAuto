package com.ticketfly.testpages;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.ticketfly.base.BrowserFactory;

public class GoogleSearchResultsPage {
	private static WebDriver browser = BrowserFactory.getBrowser();
	public static Logger log = Logger.getLogger(GoogleSearchResultsPage.class);

	/**
	 * This method is used to select from the search results. This takes in a
	 * numeric value and selects n'th result. If the input is 1 the first result
	 * is selected.
	 * 
	 * @param Result
	 * @return
	 * @return
	 * @throws InterruptedException 
	 */
	public boolean selectFromSearchResults(int Result) throws InterruptedException {
		String selectSearchRes = null;
		if (Result >= 1) {
			log.info("Selecting Search Result No :" + Result);
			browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			selectSearchRes = "//*[@id=\"rso\"]/li[" + Result
					+ "]/div/h3/a/em";
			browser.findElement(By.xpath((selectSearchRes))).click();
			return true;
		}else {
			log.error("Input should be Greater than or equal to 1");
			Assert.fail("Input should be Greater than or equal to 1");
			return false;
		}
	}
}
