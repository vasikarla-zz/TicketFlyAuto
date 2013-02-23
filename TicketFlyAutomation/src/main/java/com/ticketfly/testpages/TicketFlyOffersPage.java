package com.ticketfly.testpages;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.testng.Assert;

import com.ticketfly.base.BrowserFactory;
import com.ticketfly.base.TestConstants;

public class TicketFlyOffersPage {
	private static WebDriver browser = BrowserFactory.getBrowser();

	/**
	 * Method to perform Checkout
	 * 
	 * @throws Exception
	 */
	public boolean doCheckout() throws Exception {
		browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		try {
			browser.findElement(
					By.xpath(".//*[@id='offersSection']/footer/div/input"))
					.click();
			switchToPopUpByTitle("Login");
			browser.findElement(By.xpath(".//*[@id='email']")).sendKeys(
					"sample@sample.com");
			browser.findElement(By.xpath(".//*[@id='password']")).sendKeys(
					"sample");
			// TO DO
			browser.findElement(By.xpath("//*[@id=\"signInButton\"]/input"))
					.click();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Unable to perform Checkout...");
			return false;
		}
	}

	public static void switchToPopUpByTitle(String title) {

		Set<String> windowHandleSet = browser.getWindowHandles();
		Iterator<String> windowIterator = windowHandleSet.iterator();
		String windowHandle = null;
		WebDriver popup = null;
		while (windowIterator.hasNext()) {
			windowHandle = windowIterator.next();

			popup = switchTo(windowHandle, TestConstants.SWITCH_TO_TYPE_FRAME);

			if (popup != null) {
				if (title.equalsIgnoreCase(popup.getTitle())) {
					browser = popup;
					break;
				}

			}

		}

	}

	public static WebDriver switchTo(String switchTo, String switchToType) {

		WebDriver webdriver = null;
		TargetLocator targetLocator = browser.switchTo();
		if (TestConstants.SWITCH_TO_TYPE_WINDOW.equals(switchToType)) {
			webdriver = targetLocator.window(switchTo);
		} else if (TestConstants.SWITCH_TO_TYPE_FRAME.equals(switchToType)) {
			System.out.println("Switching to Frame...");
			try {
				System.out.println("Switching to : " + switchTo);
				webdriver = targetLocator.frame(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Switched to Frame...");
		}

		return webdriver;
	}
}
