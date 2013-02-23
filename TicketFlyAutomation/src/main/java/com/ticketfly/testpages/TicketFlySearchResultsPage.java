package com.ticketfly.testpages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ticketfly.base.BrowserFactory;

public class TicketFlySearchResultsPage {
	private static WebDriver browser = BrowserFactory.getBrowser();

	public int getSearchResultsCount() {
		browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement el = browser.findElement(By
				.xpath(".//*[@id='post-7']/div[1]/div/div"));
		String searchResults = el.getText();
		String[] results = searchResults.split("search results for");
		int resCount = Integer.parseInt(results[0].trim());
		return resCount;
	}

	public void selectEvent() {
		browser.findElement(
				By.xpath(".//*[@id='post-7']/div[1]/div/ul/li[3]/div[3]/span/a"))
				.click();
	}
}
