package com.ticketfly.testpages;

import org.openqa.selenium.WebElement;

import com.ticketfly.base.Browser;

public class TicketFlySearchResultsPage {

	public int getSearchResultsCount() {
		WebElement el = Browser
				.findTheElement("xpath=.//*[@id='post-7']/div[1]/div/div");
		String searchResults = el.getText();
		String[] results = searchResults.split("search results for");
		int resCount = Integer.parseInt(results[0].trim());
		return resCount;
	}

	public void selectEvent() {
		Browser.click("xpath=.//*[@id='post-7']/div[1]/div/ul/li[3]/div[3]/span/a");
	}
}
