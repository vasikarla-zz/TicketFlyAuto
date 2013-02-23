package com.ticketfly.base;

import static com.ticketfly.base.TestConstants.ALERT_ACTION_TYPE_ACCEPT;
import static com.ticketfly.base.TestConstants.ALERT_ACTION_TYPE_DISMISS;
import static com.ticketfly.base.TestConstants.DESELECT_ALL;
import static com.ticketfly.base.TestConstants.LONG_WAIT;
import static com.ticketfly.base.TestConstants.SELECT_BY_INDEX;
import static com.ticketfly.base.TestConstants.SELECT_BY_VALUE;
import static com.ticketfly.base.TestConstants.SELECT_BY_VISIBLE_TEXT;
import static com.ticketfly.base.TestConstants.SELECT_INFO_TYPE_ALL_SELECTED_OPTIONS;
import static com.ticketfly.base.TestConstants.SELECT_INFO_TYPE_FIRST_SELECTED_OPTION;
import static com.ticketfly.base.TestConstants.SELECT_INFO_TYPE_OPTIONS;
import static com.ticketfly.base.TestConstants.WAIT_SLEEP;
import static org.openqa.selenium.OutputType.FILE;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

import com.ticketfly.test.framework.utils.CommonUtils;

public class Browser {

	private static WebDriver browser = BrowserFactory.getBrowser();
	public static Logger log = Logger.getLogger(Browser.class);

	/**
	 * Wrapper method for Webdriver get, basically opens a URL in the browser.
	 * Handles IE issue for non protected mode URLS to run with IP
	 * 
	 * @param url
	 */
	public static void go(String url) {
		log.debug("Browsers Go method ");

		if (StringUtils.isNotEmpty(url) && url.contains("yahoo.com")) {
			if (browser instanceof InternetExplorerDriver) {
				try {
					url = CommonUtils.getIpAddressURL(url);
				} catch (Exception e) {
					log.error("error in resolving to IP address", e);
				}
			}
		}
		log.debug("Browsers Go method URL " + url);
		browser.get(url);

	}

	/**
	 * Wrapper methd for Webdriver navigateTo Handles IE issue for non protected
	 * mode URLS to run with IP
	 * 
	 * @param url
	 */

	public static void navigateToUrl(String url) {
		log.debug("Browsers navigateToUrl method ");
		if (browser instanceof InternetExplorerDriver) {
			try {
				url = CommonUtils.getIpAddressURL(url);
			} catch (Exception e) {
				log.error("error in resolving to IP address", e);
			}
		}
		log.debug("Browsers navigateToUrl method URL " + url);
		browser.navigate().to(url);

	}

	/**
	 * Returns the WebDriver reference object to which this Browser is wrapped
	 * 
	 * @return WebDriver
	 */

	public static WebDriver getBrowser() {
		return browser;
	}

	/**
	 * Sets the WebDriver object to the Browser class This is needed if you wnat
	 * to wrap any ecternal WebDriver object and use th Browser Class methods
	 * 
	 * @param WebDriver
	 * 
	 * 
	 */
	public static void setBrowser(WebDriver driver) {
		browser = driver;

	}

	/**
	 * Closes the Browser Object
	 * 
	 */
	public static void quit() {
		log.debug("Browsers quit method ");
		browser.quit();
		log.debug("Browsers quit method  ");

	}

	/**
	 * Resolves the BY object for your input webElementLocator should be in the
	 * format type=value where type can be className, css, name, id, linkText,
	 * partialLinkText, tagName and xpath
	 * 
	 * @param webElementLocator
	 *            web element locator
	 * 
	 * @return By object
	 */

	public static By findBy(String input) {
		String returnVal = input;

		By locatorObj = null;

		if (input != null) {

			int cdataIndex = input.indexOf("CDATA[");

			if (cdataIndex > 0) {
				returnVal = input.substring(cdataIndex + 1);
				int cdataStart = returnVal.indexOf("[");
				int cdataEnd = returnVal.indexOf("]");

				if (cdataStart > 0 && cdataEnd > 0) {
					returnVal = (returnVal.substring(cdataStart + 1, cdataEnd))
							.trim();
				}

				int index = returnVal.indexOf("=");

				if (index > 0) {
					returnVal = returnVal.substring(index + 1);
				}

			} else {
				int index = input.indexOf("=");
				if (index > 0) {
					returnVal = input.substring(index + 1);
				}
			}

			if (returnVal != null)
				returnVal = returnVal.trim();
			if (input.startsWith("name=")) {
				locatorObj = By.name(returnVal);
			} else if (input.startsWith("id=")) {
				locatorObj = By.id(returnVal);
			} else if (input.startsWith("xpath=")) {
				locatorObj = By.xpath(returnVal);
			} else if (input.startsWith("css=")) {
				locatorObj = By.cssSelector(returnVal);
			} else if (input.startsWith("class=")) {
				locatorObj = By.className(returnVal);
			} else if (input.startsWith("link=")) {
				locatorObj = By.linkText(returnVal);
			} else if (input.startsWith("partialLink=")) {
				locatorObj = By.partialLinkText(returnVal);
			} else if (input.startsWith("tag=")) {
				locatorObj = By.tagName(returnVal);
			}

		}

		return locatorObj;

	}

	/**
	 * Searches for the list of elements in page and returns it.
	 * webElementLocator should be in the format type=value where type can be
	 * className, css, name, id, linkText, partialLinkText, tagName and xpath
	 * 
	 * @param webElementLocator
	 *            web element locator
	 * 
	 * @return list of web elements
	 */
	public static List<WebElement> findElements(String webElementLocator) {

		By webElementLocatorBy = findBy(webElementLocator);
		List<WebElement> elements = browser.findElements(webElementLocatorBy);

		if (elements.size() == 0) {
			log.debug("Unable to find element with locator "
					+ webElementLocator
					+ ". Trying to wait for element to load.");

			// Wait for the element to load before doing a findelement
			waitForPageElementToLoad(webElementLocator);
			elements = browser.findElements(webElementLocatorBy);
		}
		return elements;
	}

	/**
	 * Searches for the element in page and returns it. webElementLocator should
	 * be in the format type=value where type can be className, css, name, id,
	 * linkText, partialLinkText, tagName and xpath
	 * 
	 * @param webElementLocator
	 *            web element locator
	 * @return web element
	 */
	public static WebElement findTheElement(String webElementLocator) {

		By webElementLocatorBy = findBy(webElementLocator);
		try {
			return browser.findElement(webElementLocatorBy);
		} catch (Exception e) {
			log.error("Unable to find element with locator "
					+ webElementLocator
					+ ". Trying to wait for element to load.");

			// Wait for the element to load before doing a find element
			waitForPageElementToLoad(webElementLocator);
			return browser.findElement(webElementLocatorBy);
		}
	}

	/**
	 * Captures screen shot to file.
	 * 
	 * @param fileName
	 *            file name
	 * @return Screen shot file
	 * @throws IOException
	 *             IOException
	 */
	public static File captureScreenShot(String fileName) throws IOException {
		TakesScreenshot takesScreenshot = (TakesScreenshot) browser;
		File scrFile = takesScreenshot.getScreenshotAs(FILE);

		File outputFile = new File(fileName);
		try {
			FileUtils.copyFile(scrFile, outputFile);
		} catch (IOException ioe) {
			log.error(ioe);
		}

		return outputFile;
	}

	/**
	 * Performs click action
	 * 
	 * @param webElementLocator
	 */
	public static void click(String webElementLocator) {
		log.debug("Clicking Locator : " + webElementLocator);

		WebElement element = findTheElement(webElementLocator);
		try {
			element.click();
		} catch (ElementNotVisibleException e) {
			log.info("Element with locator "
					+ webElementLocator
					+ " is not visible for click, waiting for element to be visible.");

			// wait till element is visible for click
			waitForPageElementToBeDisplayed(element, LONG_WAIT, WAIT_SLEEP);
			element.click();
		}
	}

	/**
	 * This method will click the web element and wait for the provided element
	 * locator to appear for 60 seconds
	 * 
	 * @param webElementLocator
	 *            web element locator to click
	 * @param forWebElementLocator
	 *            web element locator to wait for
	 */
	public static void clickAndWaitForElement(final String webElementLocator,
			final String forWebElementLocator) {
		click(webElementLocator);
		waitForPageElementToLoad(forWebElementLocator);
	}

	/**
	 * This method will click the web element and wait for the provided element
	 * locator to appear for specified time
	 * 
	 * @param webElementLocator
	 *            web element locator to click
	 * @param forWebElementLocator
	 *            web element locator to wait for
	 * @param timeOutInSeconds
	 *            time to wait in seconds
	 */
	public static void clickAndWaitForElement(final String webElementLocator,
			final String forWebElementLocator, long timeOutInSeconds) {
		click(webElementLocator);
		waitForPageElementToLoad(forWebElementLocator, timeOutInSeconds,
				WAIT_SLEEP);
	}

	/**
	 * Clicks and waits for specified element text to match the specified value.
	 * Timeout and sleep time are also specified
	 * 
	 * @param webElementLocator
	 *            web element locator to click
	 * @param forWebElementLocator
	 *            web element locator to wait for
	 * @param expectedText
	 *            expected text
	 * @param checkExact
	 *            if true, will check for exact match
	 * @param timeOutInSeconds
	 *            wait time out in seconds
	 * @param webElementLocator
	 *            sleep sleep in ms
	 * 
	 */
	public static void clickAndWaitForElementText(
			final String webElementLocator, final String forWebElementLocator,
			String expectedText, String checkExact, long timeOutInSeconds) {
		click(webElementLocator);
		waitForPageElementText(forWebElementLocator, expectedText, checkExact,
				timeOutInSeconds, WAIT_SLEEP);
	}

	/**
	 * This method will click the web element and wait for LONG_WAIT = 60
	 * seconds. Use this when you are not sure about load time
	 * 
	 * @param webElementLocator
	 *            web element locator for a button or link
	 */
	public static void clickAndWaitForPageToLoad(final String webElementLocator) {
		click(webElementLocator);
		implicitlyWait(LONG_WAIT, TimeUnit.SECONDS);
	}

	/**
	 * This method will click the web element and wait the time specified
	 * 
	 * @param webElementLocator
	 *            web element locator for a button or link
	 * @param timeOutInSeconds
	 *            time to wait in seconds
	 */
	public static void clickAndWaitForPageToLoad(
			final String webElementLocator, long timeOutInSeconds) {
		click(webElementLocator);
		implicitlyWait(timeOutInSeconds, TimeUnit.SECONDS);
	}

	/**
	 * Performs de-select operation on select web element
	 * 
	 * @param selectElementLocator
	 *            select element locator
	 * @param deselectBy
	 *            de-select by - can be index, visibleText, value or deselectAll
	 * @param selection
	 *            item to de-select
	 */
	public static void deselect(String selectElementLocator, String deselectBy,
			String selection) {
		WebElement selectElement = findTheElement(selectElementLocator);
		Select select = new Select(selectElement);
		if (SELECT_BY_VALUE.equals(deselectBy)) {
			select.deselectByValue(selection);
		} else if (SELECT_BY_INDEX.equals(deselectBy)) {
			select.deselectByIndex(Integer.parseInt(selection));
		} else if (SELECT_BY_VISIBLE_TEXT.equals(deselectBy)) {
			select.deselectByVisibleText(selection);
		} else if (DESELECT_ALL.equals(deselectBy)) {
			select.deselectAll();
		}
	}

	/**
	 * Method to double click
	 * 
	 * @param webElementLocator
	 */
	public static void doubleClick(String webElementLocator) {
		log.info("Entering doubleClick...");

		WebElement element = (WebElement) findTheElement(webElementLocator);
		Actions actions = new Actions(browser);
		Action action = (Action) actions.doubleClick(element).build();
		action.perform();

		log.info("Exiting doubleClick...");

	}

	/**
	 * Gets the text in Alert
	 * 
	 * @return alert text
	 */
	public static String getAlertText() {
		return switchToAlert().getText();
	}

	/**
	 * Get a string representing the current URL that the browser is looking at.
	 * 
	 * @return The URL of the page currently loaded in the browser
	 */
	public static String getCurrentUrl() {
		return browser.getCurrentUrl();
	}

	/**
	 * Get the visible (i.e. not hidden by CSS) innerText of this element,
	 * including sub-elements, without any leading or trailing whitespace.
	 * 
	 * @param webElementLocator
	 *            web element locator
	 * @return The innerText of this element.
	 */
	public static String getText(String webElementLocator) {
		WebElement element = findTheElement(webElementLocator);
		return element.getText();
	}

	/**
	 * The title of the current page.
	 * 
	 * @return The title of the current page, with leading and trailing
	 *         whitespace stripped, or null if one is not already set
	 */
	public static String getTitle() {
		return browser.getTitle();
	}

	/**
	 * Return an opaque handle to this window that uniquely identifies it within
	 * this driver instance. This can be used to switch to this window at a
	 * later date
	 * 
	 * @return window handle
	 */
	public static String getWindowHandle() {
		return browser.getWindowHandle();
	}

	/**
	 * Return a set of window handles which can be used to iterate over all open
	 * windows of this web driver instance by passing them to
	 * #switchTo().window(String)
	 * 
	 * @return Set<String> - Empty set if Window Handles are not available
	 */
	public static Set<String> getWindowHandles() {
		Set<String> windowHandles = null;
		try {
			windowHandles = browser.getWindowHandles();

			if (windowHandles == null) {
				log.info("No Windows are open to fetch handles");
				return new HashSet<String>();
			} else if (windowHandles.isEmpty()) {
				log.info("No Window handles are available");
				return windowHandles;
			}

			int totalWindows = windowHandles.size();
			int i = 1;
			for (String handle : windowHandles) {
				log.info("Window Handle " + i + " of " + totalWindows + " is "
						+ handle);
				i++;
			}
		} catch (Exception e) {
			log.error(e);
			return new HashSet<String>();
		}

		return windowHandles;
	}

	/**
	 * Specifies the amount of time the driver should wait when searching for an
	 * element if it is not immediately present.
	 * 
	 * @param timeOut
	 *            time duration to wait for
	 * @param timeOutTimeUnit
	 *            time unit of time out
	 */
	public static void implicitlyWait(long timeOut, TimeUnit timeOutTimeUnit) {
		browser.manage().timeouts().implicitlyWait(timeOut, timeOutTimeUnit);
	}

	/**
	 * Verifies whether the specified element is displayed
	 * 
	 * @param webElementLocator
	 *            element locator
	 * @return Whether or not the element is displayed
	 */
	public static boolean isDisplayed(String webElementLocator) {

		return (findTheElement(webElementLocator).isDisplayed());
	}

	/**
	 * Verifies whether specified element is present
	 * 
	 * @param webElementLocator
	 *            web element locator
	 * @return true if element is present, false otherwise
	 */
	public static boolean isElementPresent(String webElementLocator) {

		try {
			WebElement element = browser.findElement(findBy(webElementLocator));
			return true;

		} catch (NoSuchElementException e) {
			return false;
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Checks whether specified element is present in specified parent element
	 * 
	 * @param parentElementLocator
	 *            parent element locator
	 * @param embeddedElementLocator
	 *            embedded element locator
	 * @return returns true if present, false otherwise
	 */
	public static boolean isEmbeddedElementPresent(String parentElementLocator,
			String embeddedElementLocator) {
		WebElement parentElement = findTheElement(parentElementLocator);
		List<WebElement> embeddedElements = parentElement
				.findElements(findBy(embeddedElementLocator));
		return (embeddedElements.size() > 0);
	}

	/**
	 * Verifies whether the specified element is enabled
	 * 
	 * @param webElementLocator
	 *            element locator
	 * @return True if the element is enabled, false otherwise
	 */
	public static boolean isEnabled(String webElementLocator) {

		return (findTheElement(webElementLocator).isEnabled());
	}

	/**
	 * Checks whether specified attribute is present in specified locator
	 * 
	 * @param element
	 * @param attributeKey
	 * @param attributeValueToCheck
	 * 
	 * @return true if specified attribute is present in specified locator
	 */
	protected static boolean iskAttributeValuePresent(WebElement element,
			String attributeKey, String attributeValueToCheck) {
		String attributeValue = StringUtils.EMPTY;
		try {
			attributeValue = element.getAttribute(attributeKey);
		} catch (Exception e) {
			log.debug(e);
		}

		if (StringUtils.equals(attributeValueToCheck, attributeValue)) {
			log.debug("Found form attribute=" + attributeValue);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks whether the specified select is a multi-select
	 * 
	 * @param selectElementLocator
	 *            select element locator
	 * @return boolean: whether the specified select is a multi-select
	 */
	public static boolean isMultiple(String selectElementLocator) {
		Select select = new Select(findTheElement(selectElementLocator));
		return select.isMultiple();
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

		return (findTheElement(webElementLocator).isSelected());
	}

	/**
	 * Verifies whether specified text is present in the specified locator.
	 * 
	 * @param webElementLocator
	 *            web element locator
	 * @param expectedText
	 *            expected text
	 * @param exactMatch
	 *            is exact match
	 * @return true if text is present, false otherwise
	 */
	public static boolean isTextPresent(String webElementLocator,
			String expectedText, String exactMatch) {
		boolean isExactMatch = BooleanUtils.toBoolean(exactMatch);

		log.debug("Expected text: " + expectedText);
		log.debug("Actual text: " + findTheElement(webElementLocator).getText());

		if (isExactMatch) {
			log.debug("Now checking for exact match");
			return StringUtils.equals(expectedText,
					findTheElement(webElementLocator).getText());
		} else {
			log.debug("Now checking whether actual text contains expected text");
			return StringUtils.contains(findTheElement(webElementLocator)
					.getText(), expectedText);
		}
	}

	/**
	 * Verifies whether specified text is present anywhere in the page body.
	 * 
	 * @param expectedText
	 *            expected text
	 * @return true if text is present, false otherwise
	 */
	public static boolean isTextPresent(String expectedText) {

		log.debug("Expected text: " + expectedText);
		try {
			browser.findElement(By.xpath("//body[contains(.,'" + expectedText
					+ "')]"));
			return true;
		} catch (Exception e) {
			log.error("isTextPresent: Text ::" + expectedText
					+ " :: does not exists!! ");
			return false;
		}
	}

	/**
	 * Switches to Alert and Accepts or Dismisses an alert
	 * 
	 * @param actionType
	 *            action type like ACCEPT, DISMISS
	 */
	public static void performAlertAction(String actionType) {
		Alert alert = switchToAlert();
		if (ALERT_ACTION_TYPE_ACCEPT.equals(actionType)) {
			alert.accept();
		} else if (ALERT_ACTION_TYPE_DISMISS.equals(actionType)) {
			alert.dismiss();
		}
	}

	/**
	 * Performs select operation on select web element
	 * 
	 * @param selectLocator
	 * @param optionText
	 * @throws Exception
	 */
	public static void select(String webElementLocator, String optionText)
			throws Exception {
		Select select = new Select(findTheElement(webElementLocator));
		select.selectByVisibleText(optionText);
		log.debug("Selected the Desired Option: " + optionText);
	}

	/**
	 * Performs select operation on select web element
	 * 
	 * @param selectElementLocator
	 *            select element locator
	 * @param selectBy
	 *            select by - can be index, visibleText, value
	 * @param selection
	 *            item to select
	 */
	public static void select(String selectElementLocator, String selectBy,
			String selection) {
		WebElement selectElement = findTheElement(selectElementLocator);
		Select select = new Select(selectElement);

		if (SELECT_BY_VALUE.equals(selectBy)) {
			select.selectByValue(selection);
		} else if (SELECT_BY_INDEX.equals(selectBy)) {
			select.selectByIndex(Integer.parseInt(selection));
		} else if (SELECT_BY_VISIBLE_TEXT.equals(selectBy)) {
			select.selectByVisibleText(selection);
		}

	}

	/**
	 * This method simulates typing into an element, which sets its value. It
	 * clears the existing value within the element.
	 * 
	 * @param webElementLocator
	 * @param text
	 * @throws Exception
	 */
	public static void sendKeys(String webElementLocator, String text)
			throws Exception {
		findTheElement(webElementLocator).clear();
		findTheElement(webElementLocator).sendKeys(text);
	}

	public static void sendKeyboardInput(String webElementLocator, Keys key)
			throws Exception {
		findTheElement(webElementLocator).sendKeys(key);
	}
	
	/**
	 * This method appends given text into an element.
	 * 
	 * @param webElementLocator
	 * @param text
	 * @throws Exception
	 */
	public static void appendKeys(String webElementLocator, String text)
			throws Exception {
		findTheElement(webElementLocator).sendKeys(text);
	}

	/**
	 * Switches to specified window or frame.
	 * 
	 * @param switchTo
	 *            switch to - like window name,handle or frame name, id, index
	 * @param switchToType
	 *            switch to type - like WINDOW, FRAME
	 */
	public static WebDriver switchTo(String switchTo, String switchToType) {

		WebDriver webdriver = null;
		TargetLocator targetLocator = browser.switchTo();
		if (TestConstants.SWITCH_TO_TYPE_WINDOW.equals(switchToType)) {
			webdriver = targetLocator.window(switchTo);
		} else if (TestConstants.SWITCH_TO_TYPE_FRAME.equals(switchToType)) {
			System.out.println("Switching to Frame...");
			try {
				System.out.println("Switching to : " + switchTo);
				webdriver = targetLocator.frame(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Switched to Frame...");
		}

		return webdriver;
	}

	/**
	 * Switches to specified popup window by title
	 * 
	 * @param title
	 *            of the window
	 * 
	 */
	public static void switchToPopUpByTitle(String title) {

		Set<String> windowHandleSet = browser.getWindowHandles();
		Iterator<String> windowIterator = windowHandleSet.iterator();
		String windowHandle = null;
		WebDriver popup = null;
		while (windowIterator.hasNext()) {
			windowHandle = windowIterator.next();

			popup = switchTo(windowHandle, TestConstants.SWITCH_TO_TYPE_FRAME);

			if (popup != null) {
				if (title.equalsIgnoreCase(popup.getTitle())) {
					browser = popup;
					break;
				}

			}

		}

	}

	/**
	 * Used for switching back to parent using the window handle Need to have
	 * the parentWindow handle before calling the popup Refer FlickrLibrary for
	 * example
	 * 
	 * @param switchTo
	 * 
	 * 
	 */
	public static void switchToParent(String parentWindoHandle) {

		WebDriver webdriver = switchTo(parentWindoHandle,
				TestConstants.SWITCH_TO_TYPE_WINDOW);
		browser = webdriver;

	}

	/**
	 * Switches to specified window or frame.
	 * 
	 * @param switchTo
	 *            switch to - like window name,handle or frame name, id, index
	 * @param switchToType
	 *            switch to type - like WINDOW, FRAME
	 */
	public static String getParentHandle() {

		String parentHandle = browser.getWindowHandle();

		return parentHandle;

	}

	/**
	 * Switches to the alert
	 * 
	 * @return Alert object
	 */
	public static Alert switchToAlert() {
		TargetLocator targetLocator = browser.switchTo();
		return targetLocator.alert();
	}

	/**
	 * Sleeps for specified ms
	 * 
	 * @param timeOutInMilliSecs
	 *            sleep time in ms
	 */
	protected static void threadSleep(long timeOutInMilliSecs) {
		try {
			log.debug("Sleeping for " + (timeOutInMilliSecs * .001)
					+ " seconds. ");
			Thread.sleep(timeOutInMilliSecs);
		} catch (InterruptedException e) {
			log.error(e);
		}
	}

	/**
	 * Sleeps for specified ms
	 * 
	 * @param timeOutInMilliSecs
	 *            wait time in ms
	 */
	public static void wait(String timeOutInMilliSecs) {
		log.info("Waiting for " + timeOutInMilliSecs + " Seconds");
		if (StringUtils.isNotEmpty(timeOutInMilliSecs)) {
			threadSleep((new Long(timeOutInMilliSecs)).longValue());
		}
	}

	/**
	 * Waits for specified element attribute value to match the specified value.
	 * Timeout and sleep time are pre-configured.
	 * 
	 * 
	 * @param webElementLocator
	 *            locator of web element
	 * @param attributeName
	 *            element attribute name
	 * @param expectedAttributeValue
	 *            element attribute value to wait on
	 * @param exactMatch
	 *            whether to check for exact match
	 */
	public static void waitForPageElementAttributeValue(
			String webElementLocator, String attributeName,
			String expectedAttributeValue, String exactMatch) {
		log.info("Waiting " + LONG_WAIT
				+ " seconds for  the Page Element Attribute Value");
		waitForPageElementAttributeValue(webElementLocator, attributeName,
				expectedAttributeValue, exactMatch, LONG_WAIT, WAIT_SLEEP);
	}

	/**
	 * Waits for specified element attribute value to match the specified value.
	 * Timeout and sleep time needs to be specified
	 * 
	 * @param webElementLocator
	 *            locator of web element
	 * @param attributeName
	 *            element attribute name
	 * @param expectedAttributeValue
	 *            element attribute value to wait on
	 * @param exactMatch
	 *            whether to check for exact match
	 * @param timeOutInSeconds
	 *            wait time out in seconds
	 * @param sleepInMillis
	 *            sleep sleep in ms
	 */
	public static void waitForPageElementAttributeValue(
			final String webElementLocator, final String attributeName,
			final String expectedAttributeValue, String exactMatch,
			long timeOutInSeconds, long sleepInMillis) {
		log.info("Waiting " + timeOutInSeconds
				+ " seconds for  the Page Element Attribute Value");
		final boolean isExactMatch = BooleanUtils.toBoolean(exactMatch);
		(new WebDriverWait(browser, timeOutInSeconds, sleepInMillis))
				.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver d) {
						WebElement element = null;
						String currentAttributeValue = StringUtils.EMPTY;
						try {
							By webElementLocatorBy = findBy(webElementLocator);
							element = browser.findElement(webElementLocatorBy);
							currentAttributeValue = element
									.getAttribute(attributeName);
						} catch (NoSuchElementException e) {
							log.debug("Element: " + webElementLocator
									+ " not yet loaded");
							return false;
						}

						log.debug("waitForPageElementAtributeValue->current attribute value: "
								+ currentAttributeValue
								+ ".  Expected attribute value: "
								+ expectedAttributeValue);
						if (isExactMatch) {
							log.debug("Checking for exact match");
							return (StringUtils.equals(expectedAttributeValue,
									currentAttributeValue));
						} else {
							log.debug("Checking whether current attribute value contains expected value");
							return (StringUtils.contains(currentAttributeValue,
									expectedAttributeValue));
						}
					}
				});
	}

	/**
	 * Waits for specified element text to match the specified value. Timeout
	 * and sleep time are pre-configured
	 * 
	 * 
	 * @param webElementLocator
	 *            locator of web element
	 * @param expectedText
	 *            expected text
	 * @param exactMatch
	 *            if true, will check for exact match
	 */
	public static void waitForPageElementText(final String webElementLocator,
			final String expectedText, String exactMatch) {

		waitForPageElementText(webElementLocator, expectedText, exactMatch,
				LONG_WAIT, WAIT_SLEEP);
	}

	/**
	 * Waits for specified element text to match the specified value. Timeout
	 * and sleep time needs to be specified
	 * 
	 * @param webElementLocator
	 *            locator of web element
	 * @param expectedText
	 *            expected text
	 * @param exactMatch
	 *            if true, will check for exact match
	 * @param timeOutInSeconds
	 *            wait time out in seconds
	 * @param sleepInMillis
	 *            sleep sleep in ms
	 */
	public static void waitForPageElementText(final String webElementLocator,
			final String expectedText, String exactMatch,
			long timeOutInSeconds, long sleepInMillis) {

		final boolean isExactMatch = BooleanUtils.toBoolean(exactMatch);
		(new WebDriverWait(browser, timeOutInSeconds, sleepInMillis))
				.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver d) {
						String currentText = StringUtils.EMPTY;
						try {
							currentText = findTheElement(webElementLocator)
									.getText();
						} catch (NoSuchElementException e) {
							log.debug("Element: " + webElementLocator
									+ " not yet loaded");
							return false;
						}

						log.debug("waitForPageElementText->current text: "
								+ currentText + ".  Expected text: "
								+ expectedText);
						if (isExactMatch) {
							log.debug("Checking for exact match");
							return (StringUtils.equals(expectedText,
									currentText));
						} else {
							log.debug("Checking whether current text contains expected text");
							return (StringUtils.contains(currentText,
									expectedText));
						}

					}
				});
	}

	/**
	 * Waits for specified page element to be displayed. Timeout and sleep time
	 * are pre-configured.
	 * 
	 * @param webElementLocator
	 *            locator of web element to wait for to display
	 */
	public static void waitForPageElementToBeDisplayed(
			final String webElementLocator) {
		waitForPageElementToBeDisplayed(webElementLocator, LONG_WAIT,
				WAIT_SLEEP);
	}

	/**
	 * Waits for specified page element to be displayed. Timeout and sleep time
	 * needs to be specified
	 * 
	 * @param webElementLocator
	 *            locator of web element to wait for to display
	 * @param timeOutInSeconds
	 *            wait time out in seconds
	 * @param sleepInMillis
	 *            sleep time in ms
	 */
	public static void waitForPageElementToBeDisplayed(
			final String webElementLocator, long timeOutInSeconds,
			long sleepInMillis) {

		WebElement element = findTheElement(webElementLocator);
		waitForPageElementToBeDisplayed(element, timeOutInSeconds,
				sleepInMillis);
	}

	/**
	 * Waits for specified page element to be displayed. Timeout and sleep time
	 * needs to be specified
	 * 
	 * @param webElement
	 *            web element to wait for to display
	 * @param timeOutInSeconds
	 *            wait time out in seconds
	 * @param sleepInMillis
	 *            sleep time in ms
	 */
	public static void waitForPageElementToBeDisplayed(
			final WebElement element, long timeOutInSeconds, long sleepInMillis) {
		(new WebDriverWait(browser, timeOutInSeconds, sleepInMillis))
				.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver d) {
						return (element.isDisplayed() == true);
					}
				});
	}

	/**
	 * Waits for specified page element to load. Timeout and sleep time are
	 * pre-configured.
	 * 
	 * @param webElementLocator
	 *            locator of web element to wait for to load
	 */
	public static void waitForPageElementToLoad(final String webElementLocator) {
		waitForPageElementToLoad(webElementLocator, LONG_WAIT, WAIT_SLEEP);
	}

	/**
	 * Waits for specified page element to load. Timeout and sleep time needs to
	 * be specified
	 * 
	 * @param webElementLocator
	 *            locator of web element to wait for to load
	 * @param timeOutInSeconds
	 *            wait time out in seconds
	 * @param sleepInMillis
	 *            sleep time in ms
	 */
	public static void waitForPageElementToLoad(final String webElementLocator,
			long timeOutInSeconds, long sleepInMillis) {

		(new WebDriverWait(browser, timeOutInSeconds, sleepInMillis))
				.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver d) {
						WebElement element = null;
						try {
							log.debug("Element1: " + webElementLocator);
							By webElementLocatorBy = findBy(webElementLocator);
							log.debug("Element: " + webElementLocatorBy);
							element = browser.findElement(webElementLocatorBy);
						} catch (NoSuchElementException e) {
						}
						log.debug("Element: " + webElementLocator
								+ " not yet loaded");
						return (element != null);
					}
				});
	}

	/**
	 * Gets specified select info
	 * 
	 * @param selectElementLocator
	 *            select element locator
	 * @param selectInfoType
	 *            OPTIONS : returns All options belonging to this select tag,
	 *            FIRST_SELECTED_OPTION: the first selected option in the select
	 *            tag (or the currently selected option in a normal select),
	 *            ALL_SELECTED_OPTIONS: All selected options belonging to this
	 *            select tag
	 * @return requested select info
	 */
	public static LinkedList<String> getSelectSnapshot(
			String selectElementLocator, String selectInfoType) {
		Select select = new Select(findTheElement(selectElementLocator));
		LinkedList<String> selectInfoMap = new LinkedList<String>();

		List<WebElement> selectInfoElements = null;
		if (SELECT_INFO_TYPE_OPTIONS.equals(selectInfoType)) {
			selectInfoElements = select.getOptions();
		} else if (SELECT_INFO_TYPE_ALL_SELECTED_OPTIONS.equals(selectInfoType)) {
			selectInfoElements = select.getAllSelectedOptions();
		} else if (SELECT_INFO_TYPE_FIRST_SELECTED_OPTION
				.equals(selectInfoType)) {
			WebElement firstSelectedOption = select.getFirstSelectedOption();
			if (firstSelectedOption != null) {
				selectInfoElements = new ArrayList<WebElement>();
				selectInfoElements.add(firstSelectedOption);
			}
		}

		if (selectInfoElements != null) {
			for (WebElement element : selectInfoElements) {
				selectInfoMap.add(element.getText());
			}
		}

		return selectInfoMap;
	}

}
