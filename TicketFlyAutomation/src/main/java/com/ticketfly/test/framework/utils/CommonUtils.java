package com.ticketfly.test.framework.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.ticketfly.base.Browser;
import com.ticketfly.base.TestConstants;

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
	public static final String BASE_PATH = "src/main/resources/data/objectdata/";

	/**
	 * Returns the content of a text file in String format
	 * 
	 * @param pTextFile
	 * @return The content of the file
	 * @throws IOException
	 */
	public static String getTextFileContent(File pTextFile) throws IOException {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(pTextFile), ENCODING));

			String str;
			while ((str = in.readLine()) != null) {
				pw.println(str);
			}
			in.close();
		} catch (IOException e) {
			logger.error("Error reading text file " + pTextFile + " : "
					+ e.getMessage());
			throw e;
		}

		String result = sw.toString();
		return result;
	}

	/**
	 * Method to Get the File content.
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static String getTextFileContent(String filePath) throws IOException {
		return getTextFileContent(new File(filePath));
	}

	/**
	 * Returns the current time in a String
	 */
	public static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

	/**
	 * Returns the current millisecond time in a long
	 */
	public static long getCurrentMillisecondTime() {
		Date date = new Date();
		return date.getTime();
	}

	/**
	 * Returns a comma separated list of elements included in the given array
	 * 
	 * @param pStrings
	 *            Array of Strings
	 */
	public static String displayArray(String[] pStrings) {
		StringBuffer result = new StringBuffer();
		result.append("{");

		if (pStrings != null && pStrings.length > 0) {
			for (int i = 0; i < pStrings.length; i++) {
				String item = pStrings[i];
				result.append(item);
				if (i < (pStrings.length - 1)) {
					result.append(",");
				}
			}
		}

		result.append("}");
		return result.toString();
	}

	/**
	 * Returns a comma separated list of elements included in the given array
	 * 
	 * @param pStrings
	 *            Array of Strings
	 */
	public static String displayArray(String[][] pStrings) {
		StringBuffer result = new StringBuffer();
		result.append("{");

		if (pStrings != null && pStrings.length > 0) {
			for (int i = 0; i < pStrings.length; i++) {
				String[] item = pStrings[i];
				result.append(displayArray(item));
				if (i < (pStrings.length - 1)) {
					result.append(",");
				}
			}
		}

		result.append("}");
		return result.toString();
	}

	/**
	 * Creates a file that contains the given binary data
	 * 
	 * @param pFile
	 *            File where the data will be located
	 * @param pData
	 *            Byte array with the data
	 */
	public static void writeFile(File pFile, byte[] pData) {
		if (pData == null || pData.length < 1) {
			logger.warn("Data is empty, file " + pFile
					+ " will not be generated.");
		} else {
			try {
				if (pFile.exists()) {
					logger.warn("File " + pFile
							+ " already exists. It will be overwritten !");
					pFile.delete();
				}

				File path = new File(pFile.getParent());
				path.mkdirs();
				path = null;

				pFile.createNewFile();
				BufferedOutputStream bw = new BufferedOutputStream(
						new FileOutputStream(pFile));
				bw.write(pData);
				bw.flush();
				bw.close();

			} catch (Throwable e) {
				logger.warn("Error trying to create file " + pFile
						+ " with data: " + CommonUtils.EOL + pData
						+ CommonUtils.EOL + e.getMessage(), e);
			}
		}
	}

	/**
	 * Sleeps a certain number of ms...
	 * 
	 * @param pTimeInMs
	 */
	public static void sleep(int pTimeInMs) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// do nothing...
		}
	}

	/**
	 * If the given String is null, the empty String is returned, otherwise the
	 * original String is then returned
	 * 
	 * @param pString
	 */
	public static Object noNull(String pString) {
		if (pString == null) {
			logger.info("Input Object is Null");
			return "";
		} else {
			return pString;
		}
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
	 * Gets File As a Stream
	 * 
	 * @param fileName
	 * @return InputStream
	 * @throws FileNotFoundException
	 */
	public static InputStream getFileAsStream(String fileName)
			throws FileNotFoundException {
		File f = new File(fileName);
		if (f.exists()) {
			return new FileInputStream(f);
		}

		throw new FileNotFoundException("File " + fileName
				+ " not found from dir " + System.getProperty("user.dir"));
	}

	/**
	 * Gets Resource As a Stream
	 * 
	 * @param resource
	 * @return InputStream
	 * @throws ResourceNotFoundException
	 */
	public static InputStream getResouceAsStream(String resource)
			throws Exception {
		InputStream is = loadFromClass(resource);
		if (is != null) {
			logger.info("Loaded resource using class as resource stream relative to current class relative path: "
					+ resource);
			return is;
		}

		is = loadFromClassLoader(resource);
		if (is != null) {
			logger.info("Loaded resource using classloader as resource stream as absolute path: "
					+ resource);
			return is;
		}

		logger.error("Unable to find resource " + resource
				+ " in application's class path");
		throw new Exception("Unable to find resource " + resource
				+ " in application's class path");
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
	 * Creates a Linked Hash Map from form a sorted Map
	 * 
	 * @param colNames
	 * @param val
	 * @return
	 */
	public static LinkedHashMap<String, String> formSortedMap(
			Object[] colNames, Object[] val) {

		LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
		// Map<String, String> mapData = new HashMap<String, String>();
		for (int j = 1; j <= colNames.length; j++) {

			mapData.put((String) colNames[j - 1], (String) val[j - 1]);
		}

		return mapData;
	}

	/**
	 * This method returns the host operating system name.
	 * 
	 * @param tcID
	 * @return
	 */
	public static String getHostOperatingSystem(String tcID) {
		String osName = System.getProperty("os.name");
		logger.info("Current Host Operating System is identified as " + osName);
		return osName;
	}

	/**
	 * Method to compare two files
	 * 
	 * @param sFile1
	 * @param sFile2
	 * @return
	 */
	public static boolean comparefiles(String sFile1, String sFile2) {
		File fileone = new File(sFile1);
		File filetwo = new File(sFile2);
		int difference = fileone.compareTo(filetwo);
		if (difference == 0)
			return true;
		else
			return false;
	}

	/**
	 * Method to Delete files
	 * 
	 * @param file
	 */
	public static void deletefile(String file) {

		File f1 = new File(file);
		boolean success = f1.delete();
		if (!success) {
			System.out.println("Failed to delete files");
		}
	}

	/**
	 * Reads from a file and returns a byte array
	 * 
	 * @param file
	 * @param tcid
	 * @return byte[] - A byte array of data read from the file
	 * @throws IOException
	 */
	public static byte[] readFromFile(File file, String tcid)
			throws IOException {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			byte[] content = new byte[(int) file.length()];
			inputStream.read(content);
			return content;
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
	}

	/**
	 * Writes to a file
	 * 
	 * @param file
	 * @param content
	 * @param tcid
	 * @throws IOException
	 */
	public static void writeToFile(File file, byte[] content, String tcid)
			throws IOException {
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file, false);
			outputStream.write(content);
		} finally {
			if (outputStream != null)
				outputStream.close();
		}
	}

	/**
	 * Generates random id
	 * 
	 * @param prefix
	 *            prefix
	 * 
	 * @return random id
	 */
	public static String generateRandomId(String prefix) {
		Calendar calendar = Calendar.getInstance();
		logger.info("generateRandomId->Using date formatter: dd-MMM-yyyy-hh_mm_ss");
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy-hh_mm_ss");
		String formattedTime = formatter.format(calendar.getTime());
		logger.info("generateRandomId->Formatted time: " + formattedTime);

		return (StringUtils.isEmpty(prefix) ? formattedTime : prefix
				+ formattedTime);
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

	/**
	 * Util method to print list
	 * 
	 * @param list
	 */
	public static void printList(List<?> list) {
		String mapBuf = new String("\n");
		for (Object obj : list) {
			mapBuf += (obj + "\n");
		}
		logger.debug(mapBuf);
	}

	/**
	 * Method to Print List of Maps
	 * 
	 * @param list
	 */
	public static void printListOfMaps(List<HashMap<String, String>> list) {
		for (HashMap<String, String> map : list) {
			printMap(map);
		}
	}

	/**
	 * Util method to print map
	 * 
	 * @param map
	 */
	public static void printMap(Map<?, ?> map) {
		String mapBuf = new String("\n");

		Set<?> mapKeys = map.keySet();
		for (Object key : mapKeys) {
			mapBuf += (key + "=" + map.get(key) + "\n");
		}

		logger.info(mapBuf);
	}

	/**
	 * Util method to print map of maps
	 * 
	 * @param mapOfMaps
	 *            map of maps to print
	 */
	public static void printMapOfMaps(
			LinkedHashMap<String, HashMap<String, String>> mapOfMaps) {
		Set<String> mapKeys = mapOfMaps.keySet();
		for (String key : mapKeys) {
			logger.debug(key);
			printMap(mapOfMaps.get(key));
		}
	}

	/**
	 * Selects an Option from the picklist, while calling the function window
	 * handle has to be passed along with the searchitem to be selected.
	 * 
	 * author: tripty
	 */

	public static void lookUpListItem(String popUpHandle, String searchItem) {

		WebDriver popUp = null;
		String srchQuery;
		srchQuery = searchItem;

		logger.debug("In the pick list method selecting: " + searchItem);
		popUp = Browser.switchTo(popUpHandle,
				TestConstants.SWITCH_TO_TYPE_WINDOW);
		WebElement editable = popUp
				.switchTo()
				.frame("searchFrame")
				.findElement(
						By.xpath("//*[@id='theForm']/div/div[2]/input[@id='lksrch']"));
		editable.sendKeys(srchQuery);
		editable.sendKeys(Keys.TAB);
		editable.sendKeys(Keys.ENTER);
		popUp.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		popUp.switchTo().window(popUpHandle);

		WebElement resFrame = popUp
				.switchTo()
				.frame("resultsFrame")
				.findElement(
						By.xpath("//*[@id='new']/div/div[3]/div/div[2]/table/tbody/tr[2]/th/a"));
		resFrame.click();
	}

	/**
	 * Provide Nodelist having all the uiobjects under a page from object data
	 * XML
	 * 
	 * @param formPageName
	 * @return NodeList
	 */
	public static NodeList fetchUiObjectsForPage(String formPageName) {

		NodeList listOfUiobjects = null;
		ArrayList<String> xmlFileNames = XMLUtil.readXmlFileNames(BASE_PATH);

		Iterator<String> ite = xmlFileNames.iterator();
		try {
			while (ite.hasNext()) {
				String fileName = (String) ite.next();
				if (fileName != null
						&& (fileName.endsWith(".xml") || fileName
								.endsWith(".XML"))) {
					String xmlUtil = (BASE_PATH + fileName);

					DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder docBuilder = docBuilderFactory
							.newDocumentBuilder();
					Document doc = docBuilder.parse(new File(xmlUtil));

					// normalize text representation
					doc.getDocumentElement().normalize();
					NodeList nList = doc.getElementsByTagName("page");

					for (int p = 0; p < nList.getLength(); p++) {

						Node firstPagename = nList.item(p);

						if (firstPagename.getNodeType() == Node.ELEMENT_NODE) {

							Element firstPage = (Element) firstPagename;

							String pagename = firstPage.getAttribute("name");

							if (pagename.equals(formPageName)) {
								logger.info("pagename: "
										+ firstPage.getAttribute("name"));
								listOfUiobjects = firstPagename.getChildNodes();
								return listOfUiobjects;
							}
						}
					}
				}
			}
		} catch (SAXParseException err) {
			logger.info("** Parsing error" + ", line " + err.getLineNumber()
					+ ", uri " + err.getSystemId());
			logger.info(" " + err.getMessage());

		} catch (SAXException e) {
			Exception x = e.getException();
			((x == null) ? e : x).printStackTrace();

		} catch (Throwable t) {
			t.printStackTrace();
		}
		return listOfUiobjects;
	}

	/**
	 * Provide HashMap having the uiobject name and locator pair for a page from
	 * object data XML
	 * 
	 * @param formPageName
	 * @return
	 */
	public static HashMap<String, String> fetchObjectLabelMapping(
			String formPageName) {

		HashMap<String, String> formLabelLocatorMap = new HashMap<String, String>();
		NodeList listOfFormLabelUiobjects = null;
		listOfFormLabelUiobjects = fetchUiObjectsForPage(formPageName);

		for (int s = 0; s < listOfFormLabelUiobjects.getLength(); s++) {
			Node firstPageNode = listOfFormLabelUiobjects.item(s);
			if (firstPageNode.getNodeType() == Node.ELEMENT_NODE) {
				Element firstPageElement = (Element) firstPageNode;

				String labelName = firstPageElement.getAttribute("name");

				NodeList locatorList = firstPageElement
						.getElementsByTagName("locator");
				Element locatorElement = (Element) locatorList.item(0);
				NodeList textLNList = locatorElement.getChildNodes();
				String labelLocator = ((Node) textLNList.item(0))
						.getNodeValue().trim();

				formLabelLocatorMap.put(labelName, labelLocator);
			}
		}
		return formLabelLocatorMap;
	}

}
