package com.ticketfly.test.framework.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/**
 * Collection of methods for Common Utilities for use across projects and
 * framework 
 */
public class CommonUtils {

	private final static Logger logger = Logger.getLogger(CommonUtils.class);
	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	public static final String ENCODING = "UTF-8";
	public static final String EOL = System.getProperty("line.separator");
	private static final String CONFIG_PROPERTY_FILE_PATH = "src/test/resources/config.properties";

	/**
	 * Method to retun a random number within a given range. 
	 */
	public static int getRandomNumber(int minimum, int maximum){
		int randomNum;
		randomNum = minimum + (int) (Math.random() * maximum);
		return randomNum;
	}
	/**
	 * This method is used to parse the CDATA text
	 * 
	 * @param input
	 * @return
	 */
	public static String parseElementValue(String input) {
		String returnVal = input;
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

		}

		if (returnVal != null)
			returnVal = returnVal.trim();
		return returnVal;

	}

	/**
	 * Method to read values from the config file.
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public static String readFromConfig(String input)
			throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(CONFIG_PROPERTY_FILE_PATH));
		String returnVal = properties.getProperty(input);
		return returnVal;
	}

	/**
	 * Method to parse value for locator
	 * 
	 * @param input
	 * @return
	 */
	public static By parseValueForLocator(String input) {
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
	 * 
	 * @param resource
	 * @return InputStream
	 */
	public static InputStream loadFromClass(String resource) {
		return CommonUtils.class.getResourceAsStream(resource);
	}

	/**
	 * 
	 * @param resource
	 * @return InputStream
	 */
	public static InputStream loadFromClassLoader(String resource) {
		return CommonUtils.class.getClassLoader().getResourceAsStream(resource);
	}

	/**
	 * This method returns the host operating system name.
	 * 
	 * @param tcID
	 * @return
	 */
	public static String getHostOperatingSystem() {
		String osName = System.getProperty("os.name");
		logger.info("Current Host Operating System is identified as " + osName);
		return osName;
	}

	/**
	 * Generates random id
	 * 
	 * @param prefix
	 *            prefix
	 * 
	 * @return random id
	 */
	public static String currentDateFileName(String prefix) {
		logger.info("currentDateFileName->Using date formatter: dd-MMM-yyyy-hh_mm_ss");
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy-hh_mm_ss");
		String formattedTime = formatter.format(new Date());
		logger.info("currentDateFileName->Formatted time: " + formattedTime);

		return (StringUtils.isEmpty(prefix) ? formattedTime : prefix
				+ formattedTime);
	}

	/**
	 * Method to Get IP Address from a URL
	 * 
	 * @param httpURL
	 * @return
	 * @throws Exception
	 */
	public static String getIpAddressURL(String httpURL) throws Exception {
		logger.info("Getting the IP address of the url " + httpURL);
		InetAddress address = InetAddress.getByName(new URL(httpURL).getHost());

		String resolvedIp = address.getHostAddress();
		String resolvedHost = address.getHostName();

		logger.info(" resolved to IP:" + address.getHostAddress()
				+ " resolved host" + resolvedHost);
		httpURL = httpURL.replaceAll(resolvedHost, resolvedIp);

		logger.info("getIpAddressURL () Final IP URL " + httpURL);

		return httpURL;
	}
}
