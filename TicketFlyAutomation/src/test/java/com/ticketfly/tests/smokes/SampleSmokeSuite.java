package com.ticketfly.tests.smokes;

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

public class SampleSmokeSuite extends BaseTestCase {

	public static GoogleHomePage googleHomePage = new GoogleHomePage();
	public static GoogleSearchResultsPage googleSearchResultsPage = new GoogleSearchResultsPage();
	public static TicketFlyHomePage ticketflyHomePage = new TicketFlyHomePage();
	public static TicketFlySearchResultsPage ticketflySearchResPage = new TicketFlySearchResultsPage();
	public static TicketFlyPurchasePage ticketflyPurchasePage = new TicketFlyPurchasePage();
	public static TicketFlyDeliveryPage ticketflyDeliveryPage = new TicketFlyDeliveryPage();
	public static TicketFlyOffersPage ticketflyOffersPage = new TicketFlyOffersPage();
	public static TicketFlyPaymentsPage ticketflyPaymentsPage = new TicketFlyPaymentsPage();
	
	@Test
	public static void launchApp() throws Exception {
		googleHomePage.searchString("TicketFly");
		googleSearchResultsPage.selectFromSearchResults(1);
		ticketflyHomePage.findEvent("Blackalicious");
		ticketflySearchResPage.getSearchResultsCount();
		ticketflySearchResPage.selectEvent();
		ticketflyPurchasePage.selectRandomTickets();
		ticketflyDeliveryPage.chooseRandomDeliveryMethod();
		ticketflyOffersPage.doCheckout();
		ticketflyPaymentsPage.verifyPaymentsPage();
	}
}
