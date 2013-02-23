package com.ticketfly.testpages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.ticketfly.base.Browser;

public class TicketFlyHomePage {

	public static Logger log = Logger.getLogger(TicketFlyHomePage.class);

	/**
	 * Method to find an event in the Ticketfly main page.
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void findEvent(String event) throws Exception {
		log.info("Entering findEvent Method with Input : " + event);
		if ((!event.equals("")) && (!event.equals(null))) {
			Browser.waitForPageElementToLoad("xpath=//*[@id=\"q\"]");
			Browser.sendKeys("xpath=//*[@id=\"q\"]", event);
			Browser.click("xpath=html/body/div[3]/div/form/fieldset/ol/button");
			log.info("Searching for the event :" + event);
		} else {
			log.info("Event cannot be NULL or Empty...");
		}

	}

}
