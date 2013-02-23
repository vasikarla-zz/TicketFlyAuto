package com.ticketfly.testpages;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ticketfly.base.BrowserFactory;
import com.ticketfly.test.framework.utils.CommonUtils;

public class TicketFlyPurchasePage {
	private static WebDriver browser = BrowserFactory.getBrowser();
	/**
	 * Method to return random tickets.
	 * @throws InterruptedException 
	 */
	public boolean selectRandomTickets() throws InterruptedException {
		browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		int randomNum;
		randomNum = CommonUtils.getRandomNumber(1, 10);
		Thread.sleep(3000);
		try {
			browser.findElement(By.xpath(".//*[@id='purchase-header-image']/img")).click();
			for (int i = 0; i < randomNum; i++) {
				browser.findElement(By.xpath(".//*[@id='productsDiv']/div/div[3]/p/button[2]")).click();
			}
			browser.findElement(By.xpath(".//*[@id='bestSeats']")).click();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Unable to Select Random Tickets...");
			return false;
		}
	}
}
