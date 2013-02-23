package com.ticketfly.testpages;

import com.ticketfly.base.Browser;

public class TicketFlyOffersPage {

	/**
	 * Method to perform Checkout
	 * @throws Exception
	 */
	public void doCheckout() throws Exception {
		Browser.wait("5000");
		Browser.click("xpath=.//*[@id='offersSection']/footer/div/input");
		Browser.switchToPopUpByTitle("Login");
		Browser.sendKeys("xpath=.//*[@id='email']", "sample@sample.com");
		Browser.sendKeys("xpath=.//*[@id='password']", "sample");
		org.testng.Assert.assertTrue(Browser
				.isElementPresent("xpath=//*[@id=\"signInButton\"]/input"),
				"Unable to Find the Sign In Button...");
		Browser.click("xpath=//*[@id=\"signInButton\"]/input");
	}
}
