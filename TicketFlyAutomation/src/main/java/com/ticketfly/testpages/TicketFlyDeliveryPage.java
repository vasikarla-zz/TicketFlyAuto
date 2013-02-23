package com.ticketfly.testpages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.ticketfly.base.BrowserFactory;
import com.ticketfly.test.framework.utils.CommonUtils;

public class TicketFlyDeliveryPage {
	private static WebDriver browser = BrowserFactory.getBrowser();

	/**
	 * Method to choose a Random Delivery Method
	 */
	public boolean chooseRandomDeliveryMethod() {
		browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		int randomNum = CommonUtils.getRandomNumber(1, 5);
		System.out.println("Delivery method : " + randomNum);
		try {
			browser.findElement(By.xpath(".//*[@id='wrapper']/section[2]/form/div/div/section[2]/div[1]/div["
					+ randomNum + "]/label")).click();
			browser.findElement(By.xpath(".//*[@id='update']")).click();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to Choose Random Delivery Method...");
			return false;
		}
	}
}
