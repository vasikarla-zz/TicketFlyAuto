package com.ticketfly.testpages;

import org.testng.Assert;

import com.ticketfly.base.Browser;
import com.ticketfly.test.framework.utils.CommonUtils;

public class TicketFlyDeliveryPage {

	/**
	 * Method to choose a Random Delivery Method
	 */
	public boolean chooseRandomDeliveryMethod() {
		int randomNum = CommonUtils.getRandomNumber(1, 5);
		System.out.println("Delivery method : " + randomNum);
		Browser.wait("3000");
		try {
			Browser.click("xpath=.//*[@id='wrapper']/section[2]/form/div/div/section[2]/div[1]/div["
					+ randomNum + "]/label");
			Browser.click("xpath=.//*[@id='update']");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to Choose Random Delivery Method...");
			return false;
		}
	}
}
