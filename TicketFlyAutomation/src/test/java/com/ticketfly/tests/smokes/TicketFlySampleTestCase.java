package com.ticketfly.tests.smokes;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ticketfly.base.BaseTestCase;
import com.ticketfly.base.Browser;
import com.ticketfly.testpages.GoogleHomePage;
import com.ticketfly.testpages.GoogleSearchResultsPage;
import com.ticketfly.testpages.TicketFlyDeliveryPage;
import com.ticketfly.testpages.TicketFlyHomePage;
import com.ticketfly.testpages.TicketFlyOffersPage;
import com.ticketfly.testpages.TicketFlyPaymentsPage;
import com.ticketfly.testpages.TicketFlyPurchasePage;
import com.ticketfly.testpages.TicketFlySearchResultsPage;

public class TicketFlySampleTestCase extends BaseTestCase {

	public static GoogleHomePage googleHomePage = new GoogleHomePage();
	public static GoogleSearchResultsPage googleSearchResultsPage = new GoogleSearchResultsPage();
	public static TicketFlyHomePage ticketflyHomePage = new TicketFlyHomePage();
	public static TicketFlySearchResultsPage ticketflySearchResPage = new TicketFlySearchResultsPage();
	public static TicketFlyPurchasePage ticketflyPurchasePage = new TicketFlyPurchasePage();
	public static TicketFlyDeliveryPage ticketflyDeliveryPage = new TicketFlyDeliveryPage();
	public static TicketFlyOffersPage ticketflyOffersPage = new TicketFlyOffersPage();
	public static TicketFlyPaymentsPage ticketflyPaymentsPage = new TicketFlyPaymentsPage();

	@Test()
	public void launchGoogleAndSearch() {
		try {
			Assert.assertTrue(googleHomePage.searchString("TicketFly"),
					"Failed to Launch Google Home Page and Search for 'TicketFly'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(dependsOnMethods="launchGoogleAndSearch")
	public void selectFromSearchResults() {
		Assert.assertTrue(googleSearchResultsPage.selectFromSearchResults(1),
				"Failed to Select from Search Results...");
	}

	@Test(dependsOnMethods="selectFromSearchResults")
	public void findEventFromTicketFlyHomePage() {
		try {
			Assert.assertTrue(ticketflyHomePage.findEvent("Blackalicious"),
					"Failed to Find the specified event...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(dependsOnMethods="findEventFromTicketFlyHomePage")
	public void selectEventFromSearchResults() {
		ticketflySearchResPage.getSearchResultsCount();
		ticketflySearchResPage.selectEvent();
	}

	@Test(dependsOnMethods="selectEventFromSearchResults")
	public void selectRandomTickets() {
		Assert.assertTrue(ticketflyPurchasePage.selectRandomTickets(),
				"Unabled to Select Random Tickets...");
	}

	@Test(dependsOnMethods="selectRandomTickets")
	public void chooseRandomDeliveryMethod() {
		Assert.assertTrue(ticketflyDeliveryPage.chooseRandomDeliveryMethod(),
				"Failed to Choose Random Delivery Method...");
	}

	@Test(dependsOnMethods="chooseRandomDeliveryMethod")
	public void performCheckout() throws Exception {
		Assert.assertTrue(ticketflyOffersPage.doCheckout(),
				"Unable to Perform Checkout Operation...");
	}

	@Test(dependsOnMethods="performCheckout")
	public void verifyPaymentsPage() {
		Browser.wait("3000");
		Assert.assertEquals(Browser.getCurrentUrl(),
				"https://www.ticketfly.com/purchase/payment",
				"Page URL mismatch.");
	}
}