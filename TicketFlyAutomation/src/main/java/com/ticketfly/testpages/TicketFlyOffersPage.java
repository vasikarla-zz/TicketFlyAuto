package com.ticketfly.testpages;

import org.testng.Assert;

import com.ticketfly.base.Browser;

public class TicketFlyOffersPage {

	/**
	 * Method to perform Checkout
	 * @throws Exception
	 */
	public boolean doCheckout() throws Exception {
		try {
			Browser.wait("5000");
			Browser.click("xpath=.//*[@id='offersSection']/footer/div/input");
			Browser.switchToPopUpByTitle("Login");
			Browser.waitForPageElementToBeDisplayed("xpath=.//*[@id='email']");
			Browser.sendKeys("xpath=.//*[@id='email']", "sample@sample.com");
			Browser.waitForPageElementToBeDisplayed("xpath=.//*[@id='password']");
			Browser.sendKeys("xpath=.//*[@id='password']", "sample");
			Assert.assertTrue(Browser
					.isElementPresent("xpath=//*[@id=\"signInButton\"]/input"),
					"Unable to Find the Sign In Button...");
			Browser.click("xpath=//*[@id=\"signInButton\"]/input");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Unable to perform Checkout...");
			return false;
		}
	}
}
