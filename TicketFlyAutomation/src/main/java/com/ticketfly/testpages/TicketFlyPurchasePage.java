package com.ticketfly.testpages;

import com.ticketfly.base.Browser;
import com.ticketfly.test.framework.utils.CommonUtils;

public class TicketFlyPurchasePage {

	/**
	 * Method to return random tickets.
	 */
	public void selectRandomTickets() {
		int randomNum;
		randomNum = CommonUtils.getRandomNumber(1, 10);
		Browser.wait("3000");
		Browser.click("xpath=.//*[@id='purchase-header-image']/img");
		for (int i = 0; i < randomNum; i++) {
			Browser.click("xpath=.//*[@id='productsDiv']/div/div[3]/p/button[2]");
		}
		Browser.click("xpath=.//*[@id='bestSeats']");
	}
}
