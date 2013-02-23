package com.ticketfly.testpages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import com.ticketfly.base.BrowserFactory;

public class TicketFlyPaymentsPage {
	private static WebDriver browser = BrowserFactory.getBrowser();

	public boolean verifyPaymentsPage() throws InterruptedException {
		browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(3000);
		if (browser.getCurrentUrl().equalsIgnoreCase(
				"https://www.ticketfly.com/purchase/payment")) {
			return true;
		} else {
			return false;
		}
	}
}
