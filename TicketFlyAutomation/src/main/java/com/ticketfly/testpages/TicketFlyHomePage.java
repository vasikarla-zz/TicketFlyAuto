package com.ticketfly.testpages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.ticketfly.base.Browser;

public class TicketFlyHomePage {

	public static Logger log = Logger.getLogger(TicketFlyHomePage.class);

	/**
	 * Method to find an event in the Ticketfly main page.
	 * @param event
	 * @throws Exception
	 */
	public void findEvent(String event) throws Exception {
		Browser.click("xpath=html/body/div[3]/div/h2/span/a");
		WebElement el = Browser.findTheElement("xpath=//*[@id=\"q\"]");
		System.out.println("#########" + el.toString());
		Browser.sendKeys("xpath=//*[@id=\"q\"]", event);
		Browser.click("xpath=html/body/div[3]/div/form/fieldset/ol/button");
	}

}
