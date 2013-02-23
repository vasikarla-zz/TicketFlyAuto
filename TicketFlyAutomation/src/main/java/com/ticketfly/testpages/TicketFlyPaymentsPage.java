package com.ticketfly.testpages;

import org.testng.Assert;

import com.ticketfly.base.Browser;

public class TicketFlyPaymentsPage {

	public void verifyPaymentsPage() {
		Browser.wait("3000");
		Assert.assertEquals(Browser.getCurrentUrl(),
				"https://www.ticketfly.com/purchase/payment", "Page URL mismatch.");
	}
}
