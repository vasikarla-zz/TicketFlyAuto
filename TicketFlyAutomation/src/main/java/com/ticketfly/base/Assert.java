package com.ticketfly.base;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;

/**
 * A set of custom assertion methods useful for writing tests
 */

public class Assert extends org.testng.Assert {

	public static Logger log = Logger.getLogger(Assert.class);

	/**
	 * Asserts that if the URL is correct URL
	 * is thrown.
	 * 
	 * @param actual
	 */
	public static void assertUrlString(String urlString) {
		try {
			log.info("Validating URL String : " + urlString);
			new URL(urlString);
		} catch (MalformedURLException ex) {
			log.error("Validating URL String Failed for URL : " + urlString);
			fail("url: " + urlString);
		}
	}

	// Assert for Same Values
	/**
	 * @param actual
	 * @param message
	 */
	public static void assertSameValues(List<String> actual, String message) {
		assertTrue(actual.size() > 0);
		String one = actual.get(0);
		for (int i = 1; i < actual.size(); i++) {
			String other = actual.get(i);
			assertEquals(one, other, message + ": " + one + " vs " + other);
		}
	}

	// Assert for Same values
	/**
	 * @param actual
	 * @param expected
	 */
	public static void assertSameValuesWith(List<String> actual, String expected) {
		log.info("Actual Value : " + actual);
		log.info("Expected Value : " + expected);
		assertSameValuesWith(actual, expected, "");
	}

	// Assert for Same values
	/**
	 * @param actual
	 * @param expected
	 * @param message
	 */
	public static void assertSameValuesWith(List<String> actual,
			String expected, String message) {
		assertTrue(actual.size() > 0);

		for (int i = 0; i < actual.size(); i++) {
			String one = actual.get(i);
			assertEquals(one, expected, message + ": " + one + " vs "
					+ expected);
		}
	}

	// Assert for Not Null Not Empty
	/**
	 * @param actual
	 */
	public static void assertNotNullNotEmpty(List<String> actual) {
		assertNotNullNotEmpty(actual, "");
	}

	// Assert for Not Null Not Empty
	/**
	 * @param actual
	 * @param message
	 */
	public static void assertNotNullNotEmpty(List<String> actual, String message) {
		for (int i = 0; i < actual.size(); i++) {
			String one = actual.get(i);
			assertTrue(one != null && !one.isEmpty(), message);
		}
	}

	// Assert Not Null not Empty
	/**
	 * @param actual
	 */
	public static void assertNotNullNotEmpty(String actual) {
		assertNotNullNotEmpty(actual, "");
	}

	// Assert Not Null Empty
	/**
	 * @param actual
	 * @param message
	 */
	public static void assertNotNullNotEmpty(String actual, String message) {
		assertTrue(actual != null && !actual.isEmpty(), message);
	}

	// Assert for Empty then same values
	/**
	 * @param actual
	 */
	public static void assertEmptyThenSameValues(List<String> actual) {
		assertEmptyThenSameValues(actual, "");
	}

	// Assert for Empty then Same Values
	/**
	 * @param actual
	 * @param message
	 */
	public static void assertEmptyThenSameValues(List<String> actual,
			String message) {
		assertTrue(actual.size() > 1);
		Assert.assertEquals(actual.get(0), "");
		String one = actual.get(1);
		for (int i = 2; i < actual.size(); i++) {
			String other = actual.get(i);
			assertEquals(one, other, message + ": " + one + " vs " + other);
		}
	}

	// Assert for Contains
	/**
	 * @param src
	 * @param expected
	 */
	public static void assertContains(String src, String expected) {
		try {
			log.info("Checking Expected string in Source");
			assertTrue(src.contains(expected));
		} catch (Exception e) {
			log.error("AssertContains Failed");
			e.printStackTrace();
		}
	}

	/**
	 * @param currentURL
	 * @param testURL
	 */
	public static void assertGoRightUrl(String currentURL, String testURL) {

		boolean result = false;
		String message = null;
		if (currentURL == null && testURL == null) {
			return;

		} else if (currentURL == null && testURL != null) {

			message = "currentURL=null_vs_testURL_" + testURL;
			assertTrue(result, message);
			return;

		} else if (currentURL != null && testURL == null) {

			message = "currentURL:_" + currentURL + "_vs_testURL_" + testURL;
			assertTrue(result, message);
			return;
		}

		currentURL = currentURL.toLowerCase();
		testURL = testURL.toLowerCase();

		message = "Current URL: " + currentURL + "_vs_expected-URL:_" + testURL;

		if (currentURL.equalsIgnoreCase(testURL)) {
			result = true;
			assertTrue(result, message);
		} else {
			result = false;
			assertTrue(result, message);
		}
	}

	// Maleka

	/**
	 * Asserts if an alert dialog is not present
	 */
	public static void assertAlertNotPresent() {
		assertEquals(checkForAlertPopUp(), false,
				"Failed(assertAlertNotPresent): Alert Popup is present");
	}

	/**
	 * Asserts if an alert dialog is present
	 */
	public static void assertAlertPresent() {
		assertEquals(checkForAlertPopUp(), true,
				"Failed(assertAlertPresent): Alert Pop is not Present");
	}

	/**
	 * Asserts whether the specified element is displayed
	 * 
	 * @param webElementLocator
	 */
	public static void assertDisplayed(String webElementLocator) {
		assertEquals(Browser.isDisplayed(webElementLocator), true,
				"Failed(assertDisplayed): Element is not displayed");
	}

	/**
	 * Asserts whether specified element is NOT present
	 * 
	 * @param webElementLocator
	 *            web element locator
	 */
	public static void assertElementNotPresent(String webElementLocator) {
		assertEquals(Browser.isElementPresent(webElementLocator), false,
				"Failed(assertElementNotPresent): Element is present!!");
	}

	/**
	 * Asserts whether specified element is present
	 * 
	 * @param webElementLocator
	 *            web element locator
	 */
	public static void assertElementPresent(String webElementLocator) {

		assertEquals(Browser.isElementPresent(webElementLocator), true,
				"Failed(assertElementPresent): Element is not present!!");
	}

	/**
	 * Asserts whether specified element is NOT present in specified parent
	 * element
	 * 
	 * @param parentElementLocator
	 *            parent element locator
	 * @param embeddedElementLocator
	 *            embedded element locator
	 */
	public static void assertEmbeddedElementNotPresent(
			String parentElementLocator, String embeddedElementLocator) {

		assertEquals(Browser.isEmbeddedElementPresent(parentElementLocator,
				embeddedElementLocator), false,
				"Failed (assertEmbeddedElementNotPresent): Embedded element is present !!");
	}

	/**
	 * Asserts whether specified element is present in specified parent element
	 * 
	 * @param parentElementLocator
	 *            parent element locator
	 * @param embeddedElementLocator
	 *            embedded element locator
	 */
	public static void assertEmbeddedElementPresent(
			String parentElementLocator, String embeddedElementLocator) {
		assertEquals(Browser.isEmbeddedElementPresent(parentElementLocator,
				embeddedElementLocator), true,
				"Failed (assertEmbeddedElementPresent): Embedded element is not present !!");
	}

	/**
	 * Asserts whether the specified element is enabled
	 * 
	 * @param webElementLocator
	 *            element locator
	 */
	public static void assertEnabled(String webElementLocator) {
		assertEquals(Browser.isEnabled(webElementLocator), true,
				"Failed (assertEnabled): Element not enabled !!");
	}

	/**
	 * Asserts whether error list contains specified text
	 * 
	 * @param errorList
	 *            error list
	 * @param errorText
	 *            error text to check for presence
	 */
	public static void assertErrorPresent(ArrayList<String> errorList,
			String errorText) {

		assertEquals(isErrorPresent(errorList, errorText), true,
				"Failed (assertErrorPresent): Error text not present !!");
	}

	/**
	 * Asserts whether the specified select is a multi-select
	 * 
	 * @param selectElementLocator
	 *            select element locator
	 */
	public static void assertMultiple(String selectElementLocator) {

		assertEquals(Browser.isMultiple(selectElementLocator), true,
				"Failed (assertMultiple): Its not a multi-select !!");
	}

	/**
	 * Asserts whether the specified element is NOT displayed
	 * 
	 * @param webElementLocator
	 *            element locator
	 */
	public static void assertNotDisplayed(String webElementLocator) {

		assertEquals(Browser.isDisplayed(webElementLocator), false,
				"Failed (assertNotDisplayed): The element is displayed !!");
	}

	/**
	 * Asserts whether the specified element is NOT enabled
	 * 
	 * @param webElementLocator
	 *            element locator
	 */
	public static void assertNotEnabled(String webElementLocator) {
		assertEquals(Browser.isEnabled(webElementLocator), false,
				"Failed (assertNotEnabled): The element is enabled !!");
	}

	/**
	 * Asserts whether the element is not selected. This only applies to input
	 * elements like check-boxes or radio buttons.
	 * 
	 * @param webElementLocator
	 *            element locator
	 */
	public static void assertNotSelected(String webElementLocator) {

		assertEquals(isSelected(webElementLocator), false,
				"Failed (assertNotSelected): The element is selected !!");
	}

	/**
	 * Asserts whether the element is selected. This only applies to input
	 * elements like check-boxes or radio buttons.
	 * 
	 * @param webElementLocator
	 */
	public static void assertSelected(String webElementLocator) {
		assertEquals(Browser.isSelected(webElementLocator), true,
				"Failed (assertSelected): The element is not selected !!");
	}

	/**
	 * Asserts whether specified text is present in specified element
	 * 
	 * @param webElementLocator
	 *            web element locator
	 * @param expectedText
	 *            expected text
	 */
	public static void assertTextEquals(String webElementLocator,
			String expectedText) {

		assertEquals(
				Browser.isTextPresent(webElementLocator, expectedText, "true"),
				true,
				"Failed (assertTextEquals): The element is does not have the text !!");
	}

	/**
	 * Asserts whether specified text is NOT present in specified element
	 * 
	 * @param webElementLocator
	 *            web element locator
	 * @param expectedText
	 *            expected text
	 */
	public static void assertTextNotEquals(String webElementLocator,
			String expectedText, String message) {

		assertEquals(
				Browser.isTextPresent(webElementLocator, expectedText, "true"),
				false,
				"Failed (assertTextNotEquals): The element is has the text !!");
	}

	/**
	 * Determines whether or not this element is selected or not. This only
	 * applies to input elements like checkboxes or radio buttons.
	 * 
	 * @param webElementLocator
	 *            element locator
	 * @return true if element is currently selected, false otherwise
	 */
	public static boolean isSelected(String webElementLocator) {

		return (Browser.findTheElement(webElementLocator).isSelected());
	}

	/**
	 * /** Check whether an Alert modal dialog is present or not
	 * 
	 * @return boolean
	 */
	public static boolean checkForAlertPopUp() {
		Alert alert = null;
		boolean alertPresent = false;
		try {
			alert = Browser.switchToAlert();
			if (alert != null) {
				alertPresent = true;
				log.info("Alert Popup is present ");
			}
		} catch (NoAlertPresentException noae) {
			log.error(noae);
			alertPresent = false;
			log.info("Alert Popup is not present ");
		}

		return alertPresent;
	}

	/**
	 * Checks whether error list contains specified text
	 * 
	 * @param errorList
	 *            error list
	 * @param errorText
	 *            error text to check for presence
	 * 
	 * @return error list
	 */
	public static boolean isErrorPresent(ArrayList<String> errorList,
			String errorText) {
		return errorList.contains(errorText);
	}

}
