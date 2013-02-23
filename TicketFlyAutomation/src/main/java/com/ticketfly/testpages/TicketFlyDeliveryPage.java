package com.ticketfly.testpages;

import com.ticketfly.base.Browser;

public class TicketFlyDeliveryPage {

	public void chooseRandomDeliveryMethod() {
		int randomNum;
		randomNum = 1 + (int) (Math.random() * 5);
		System.out.println("Delivery method : " + randomNum);
		Browser.click("xpath=.//*[@id='wrapper']/section[2]/form/div/div/section[2]/div[1]/div["
				+ randomNum + "]");
		Browser.click("xpath=.//*[@id='update']");
	}
}
