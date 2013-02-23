package com.ticketfly.testpages;

import com.ticketfly.base.Browser;
import com.ticketfly.test.framework.utils.CommonUtils;

public class TicketFlyDeliveryPage {

	/**
	 * Method to choose a Random Delivery Method
	 */
	public void chooseRandomDeliveryMethod() {
		int randomNum = CommonUtils.getRandomNumber(1, 5);
		System.out.println("Delivery method : " + randomNum);
		Browser.wait("3000");
		Browser.click("xpath=.//*[@id='wrapper']/section[2]/form/div/div/section[2]/div[1]/div["
				+ randomNum + "]");
		Browser.click("xpath=.//*[@id='update']");
	}
}
