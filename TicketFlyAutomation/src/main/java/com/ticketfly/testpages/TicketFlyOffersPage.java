package com.ticketfly.testpages;

import com.ticketfly.base.Assert;
import com.ticketfly.base.Browser;

public class TicketFlyOffersPage {

	public void doCheckout() throws Exception{
		System.out.println("TicketFlyOffersPage.doCheckout()");
		Browser.wait("7000");
		Browser.switchToPopUpByTitle("Login");
		Browser.sendKeys("xpath=.//*[@id='email']", "sample@sample.com");
		Browser.sendKeys("xpath=.//*[@id='password']", "sample");
		Assert.assertElementPresent("xpath=//*[@id=\"signInButton\"]/input");
		Browser.click("xpath=//*[@id=\"signInButton\"]/input");
	}
}
