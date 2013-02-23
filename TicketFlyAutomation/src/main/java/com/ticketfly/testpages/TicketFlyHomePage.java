package com.ticketfly.testpages;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ticketfly.base.BrowserFactory;

public class TicketFlyHomePage {
	private static WebDriver browser = BrowserFactory.getBrowser();
	public static Logger log = Logger.getLogger(TicketFlyHomePage.class);

	/**
	 * Method to find an event in the Ticketfly main page.
	 * 
	 * @param event
	 * @throws Exception
	 */
	public boolean findEvent(String event) throws Exception {
		log.info("Entering findEvent Method with Input : " + event);
		browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if ((!event.equals("")) && (!event.equals(null))) {
			browser.findElement(By.cssSelector("#q")).sendKeys(event);
			browser.findElement(By.xpath("html/body/div[3]/div/form/fieldset/ol/button")).click();
			log.info("Searching for the event :" + event);
			return true;
		} else {
			log.info("Event cannot be NULL or Empty...");
			Assert.fail("Event cannot be NULL or Empty...");
			return false;
		}

	}

}
