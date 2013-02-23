package com.ticketfly.base;

import java.io.File;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.ticketfly.test.framework.utils.CommonUtils;

public class BrowserFactory {
	public static Logger log = Logger.getLogger(BrowserFactory.class);
	private static WebDriver browser = null;

	/**
	 * Instantiates the appropriate Browser object Reads the config and creates
	 * Corresponding IE, Firefox or Chrome browser object Also ensures the
	 * Instantiation of Correct RemoteWebdriver if the test are running on
	 * remote machine
	 * 
	 * @return WebDriver
	 * 
	 */
	public static WebDriver getBrowser() {

		log.info("Entering getRemoteWebDriver...");

		if (browser == null) {
			try {

				String browserName = CommonUtils.readFromConfig("Browser");
				String remoteRun = CommonUtils.readFromConfig("RemoteRun");
				boolean isRemoteRun = false;

				if (StringUtils.isNotEmpty(remoteRun)
						&& "true".equalsIgnoreCase(remoteRun)) {
					isRemoteRun = true;
				}

				if ("FF".equalsIgnoreCase(browserName)) {
					browser = loadFireFoxDriver(isRemoteRun);
				} else if ("IE".equalsIgnoreCase(browserName)) {
					browser = loadIEDriver(isRemoteRun);
				} else if ("chrome".equalsIgnoreCase(browserName)) {
					browser = loadChromeDriver(isRemoteRun);
				}

			} catch (Exception exception) {
				log.error("Exception in getting the Browser ", exception);
			}

			log.info("Exiting getRemoteWebDriver...");
		}

		return browser;
	}

	/**
	 * private method to load the Firefox Driver
	 * 
	 * @param loadRemote
	 * 
	 */

	private static RemoteWebDriver loadFireFoxDriver(boolean loadRemote)
			throws Exception {

		log.info("Entering BrowserFactory class loadFireFoxDriver...");
		String loadffProfile = CommonUtils.readFromConfig("loadffProfile");

		RemoteWebDriver remoteDriver = null;

		FirefoxProfile profile = null;

		if ("true".equalsIgnoreCase(loadffProfile)) {
			String profilePath = CommonUtils
					.readFromConfig("FIREFOXPROFILEDIR");
			File profileDir = new File(profilePath);
			profile = new FirefoxProfile(profileDir);
			profile.setAcceptUntrustedCertificates(false);

		}

		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		if (loadRemote) {

			log.info("loading firefox driver in remote");
			log.info("Loading  Remote Run URL "
					+ CommonUtils.readFromConfig("RemoteWebAppUrl"));
			URL url = new URL(CommonUtils.readFromConfig("RemoteWebAppUrl"));

			if ("true".equalsIgnoreCase(loadffProfile)) {
				log.info("loading firefox profile in remote");
				capabilities.setCapability(FirefoxDriver.PROFILE, profile);
				log.info("loading firefox profile in remote sucessful");
			}
			remoteDriver = new RemoteWebDriver(url, capabilities);
			log.info("loading firefox driver in remote successful");

		}

		else {
			if ("true".equalsIgnoreCase(loadffProfile)) {
				log.info("loading firefox driver with profile");

				remoteDriver = new FirefoxDriver(profile);
				log.info("loading firefox driver loadffProfile profile successfully");
			} else {
				log.info("loading firefox driver without profile");
				remoteDriver = new FirefoxDriver();

				log.info("loading firefox driver without profile successfully");
			}
		}

		log.info("Exiting BrowserFactory class loadFireFoxDriver...");

		return remoteDriver;

	}

	/**
	 * private method to load the InternetExplorer Driver
	 * 
	 * @param loadRemote
	 * 
	 */
	private static RemoteWebDriver loadIEDriver(boolean loadRemote)
			throws Exception {

		log.info("Entering BrowserFactory class loadIEDriver...");

		RemoteWebDriver remoteDriver = null;
		DesiredCapabilities capabilities = DesiredCapabilities
				.internetExplorer();
		capabilities
				.setCapability(
						InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);

		if (loadRemote) {
			log.info("loading IE driver in remote");
			log.info("Loading  Remote Run URL "
					+ CommonUtils.readFromConfig("RemoteWebAppUrl"));
			URL url = new URL(CommonUtils.readFromConfig("RemoteWebAppUrl"));
			remoteDriver = new RemoteWebDriver(url, capabilities);
			log.info("loading IE driver in remote successful");
		} else {
			log.info("loading Generic IE driver");
			System.setProperty("webdriver.ie.driver",
					"src/main/resources/browser_exe/ie/IEDriverServer.exe");
			remoteDriver = new InternetExplorerDriver(capabilities);
			log.info("loading Generic IE driver successful");
		}

		log.info("Exiting BrowserFactory class loadIEDriver...");

		return remoteDriver;

	}

	/**
	 * private method to load the Chrome Driver
	 * 
	 * @param loadRemote
	 * 
	 */

	private static RemoteWebDriver loadChromeDriver(boolean loadRemote)
			throws Exception {

		log.info("Entering BrowserFactory class loadChromeDriver...");

		RemoteWebDriver remoteDriver = null;
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();

		if (loadRemote) {

			log.info("loading Chrome driver in remote");
			log.info("Loading  Remote Run URL "
					+ CommonUtils.readFromConfig("RemoteWebAppUrl"));
			URL url = new URL(CommonUtils.readFromConfig("RemoteWebAppUrl"));
			remoteDriver = new RemoteWebDriver(url, capabilities);
			log.info("loading IE driver in remote successful");

		}

		else {
			log.info("loading Generic Chrome driver");

			String hostOS = CommonUtils.getHostOperatingSystem();
			System.out.println("Host OS : " + hostOS);

			if (hostOS.equalsIgnoreCase("Mac OS X")) {
				System.setProperty("webdriver.chrome.driver",
						"src/main/resources/browser_exe/chrome/chromedriver");
			} else {
				System.setProperty("webdriver.chrome.driver",
						"src/main/resources/browser_exe/chrome/chromedriver.exe");
			}
			remoteDriver = new ChromeDriver();
			log.info("loading Generic Chrome driver successful");
		}

		log.info("Exiting BrowserFactory class loadChromeDriver...");

		return remoteDriver;

	}

}
