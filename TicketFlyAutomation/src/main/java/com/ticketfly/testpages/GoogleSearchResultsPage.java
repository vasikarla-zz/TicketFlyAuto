package com.ticketfly.testpages;

import org.apache.log4j.Logger;

import com.ticketfly.base.Browser;

public class GoogleSearchResultsPage {

	public static Logger log = Logger.getLogger(GoogleSearchResultsPage.class);

	/**
	 * This method is used to select from the search results. This takes in a
	 * numeric value and selects n'th result. If the input is 1 the first result
	 * is selected.
	 * 
	 * @param Result
	 * @return
	 * @return
	 */
	public void selectFromSearchResults(int Result) {
		String selectSearchRes = null;
		if (Result <= 1) {
			log.info("Selecting Search Result No :" + Result);
			selectSearchRes = "xpath=//*[@id=\"rso\"]/li[" + Result
					+ "]/div/h3/a/em";
			Browser.click(selectSearchRes);
		}else {
			log.error("Input should be Greater than or equal to 1");
		}
	}
}
