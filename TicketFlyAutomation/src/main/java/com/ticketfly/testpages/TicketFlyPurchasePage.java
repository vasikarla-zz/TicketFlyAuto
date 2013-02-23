package com.ticketfly.testpages;

import com.ticketfly.base.Browser;

public class TicketFlyPurchasePage {

	public void selectRandomTickets() {
		int randomNum;
		randomNum = 1 + (int) (Math.random() * 10);
		Browser.wait("3000");
		Browser.click("xpath=.//*[@id='purchase-header-image']/img");
		for (int i = 0; i < randomNum; i++) {
			Browser.click("xpath=.//*[@id='productsDiv']/div/div[3]/p/button[2]");
		}
		Browser.click("xpath=.//*[@id='bestSeats']");
	}
}
