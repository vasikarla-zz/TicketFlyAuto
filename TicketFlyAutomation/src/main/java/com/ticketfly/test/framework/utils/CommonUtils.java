package com.ticketfly.test.framework.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Collection of methods for Common Utilities for use across projects and
 * framework
 */
public class CommonUtils {

	private final static Logger logger = Logger.getLogger(CommonUtils.class);
	private static final String CONFIG_PROPERTY_FILE_PATH = "src/test/resources/config.properties";

	/**
	 * Method to retun a random number within a given range.
	 */
	public static int getRandomNumber(int minimum, int maximum) {
		int randomNum;
		randomNum = minimum + (int) (Math.random() * maximum);
		return randomNum;
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
}
