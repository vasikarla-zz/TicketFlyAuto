package com.ticketfly.test.framework.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ticketfly.base.TestConstants;

/**
 * Class contains Utilities for XML reading Operations
 * 
 * @author kchandra
 * 
 */
public class XMLUtil {

	private String xmlFile;
	private Document xmlDocument;
	private XPath xPath;
	private final static Logger logger = Logger.getLogger(XMLUtil.class);

	/**
	 * Constructor
	 * 
	 * @param xmlFile
	 */
	public XMLUtil(String xmlFile, InputStream... is) {
		this.xmlFile = xmlFile;
		logger.debug("XML File : " + xmlFile);
		initObjects(is);
	}

	/**
	 * Initialize the Objects
	 * 
	 */
	private void initObjects(InputStream... is) {
		try {

			if (is != null && is.length > 0) {
				xmlDocument = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder().parse(is[0]);
			} else {
				xmlDocument = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder().parse(xmlFile);
			}

			xPath = XPathFactory.newInstance().newXPath();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch (ParserConfigurationException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This method takes in Xpath Expression and a qualified name
	 * 
	 * @param expression
	 * @param returnType
	 * @return
	 */
	public Object read(String expression, QName returnType) {
		try {
			XPathExpression xPathExpression = xPath.compile(expression);
			return xPathExpression.evaluate(xmlDocument, returnType);
		} catch (XPathExpressionException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * This method takes a xmlfolder as an input and returns a list of xml files
	 * within the folder
	 * 
	 * @param xmlFolder
	 * @return
	 */
	public static ArrayList<String> readXmlFileNames(String xmlFolder) {

		ArrayList<String> xmlFileNames = new ArrayList<String>();
		File directory = new File(xmlFolder);
		String filename[] = directory.list();

		if (filename != null) {
			logger.debug(filename.toString());
			xmlFileNames = new ArrayList<String>(Arrays.asList(filename));
		}
		return xmlFileNames;
	}

	/**
	 * This method is used to read the object data from the XML's The files will
	 * be in folder src/main/resources/data/objectdata Takes pagename and
	 * element name as input
	 * 
	 * @param pageName
	 * @param elementName
	 * @return
	 * @throws Exception
	 */
	public static String readObjectData(String pageName, String elementName)
			throws Exception {

		String basePath = TestConstants.BASE_PATH;

		ArrayList<String> xmlFileNames = XMLUtil.readXmlFileNames(basePath);

		Iterator<String> ite = xmlFileNames.iterator();
		String expression = "/pages/page[@ name= '" + pageName
				+ "']/uiobject[@name='" + elementName + "']/locator";
		String returnValue = "";

		while (ite.hasNext()) {
			String fileName = (String) ite.next();
			if (fileName != null
					&& (fileName.endsWith(".xml") || fileName.endsWith(".XML"))) {
				XMLUtil xmlUtil = new XMLUtil(basePath + fileName);
				String value = (String) xmlUtil.read(expression,
						XPathConstants.STRING);
				if (StringUtils.isNotEmpty(value)) {
					// returnValue = Utils.parseElementValue(value);
					returnValue = value;
					break;
				}
			}

		}

		if (StringUtils.isEmpty(returnValue)) {
			ClassPathSearcher classPathSearcher = new ClassPathSearcher();
			Map<String, InputStream> fileMap = classPathSearcher
					.findFilesInClassPath(".*objectdata.*.xml");
			for (String key : fileMap.keySet()) {

				InputStream inputStream = fileMap.get(key);
				XMLUtil xmlUtil = new XMLUtil(basePath, inputStream);
				String value = (String) xmlUtil.read(expression,
						XPathConstants.STRING);
				if (StringUtils.isNotEmpty(value)) {
					// returnValue = Utils.parseElementValue(value);
					returnValue = value;
					break;
				}

			}

		}

		return returnValue;

	}

	/**
	 * This method is used to read the readTestData data from the XML's For
	 * exampleThe files will be in folder
	 * src/main/resources/data/testdata/INT1/EMEA Where INT1 is env name and
	 * EMEA region name read from the config value
	 * 
	 * @param commonData
	 * @param elementName
	 * @return
	 * @throws Exception
	 */
	public static String readTestData(String commonData, String elementName)
			throws Exception {

		String basePath = TestConstants.BASE_PATH_TEST_DATA;
		String env = System.getProperty("ENV");

		if (StringUtils.isEmpty(env)) {
			env = CommonUtils.readFromConfig("ENV");
		}

		basePath = basePath + "/" + env;

		ArrayList<String> xmlFileNames = XMLUtil.readXmlFileNames(basePath);

		Iterator<String> ite = xmlFileNames.iterator();
		String expression = "/TestData";
		String returnValue = "";

		while (ite.hasNext()) {
			String fileName = (String) ite.next();
			if (fileName != null
					&& (fileName.endsWith(".xml") || fileName.endsWith(".XML"))) {
				XMLUtil xmlUtil = new XMLUtil(basePath + "/" + fileName);

				Node rootNode = (Node) xmlUtil.read(expression,
						XPathConstants.NODE);

				if (rootNode != null) {

					Node childNode = childNode(rootNode, commonData);

					if (childNode != null) {

						Node subChildNode = childNode(childNode, elementName);
						returnValue = subChildNode.getTextContent();

						return returnValue;

					}
				}
			}

		}

		return returnValue;

	}

	/**
	 * This method returns a Node from Root Node and node name
	 * 
	 * @param rootNode
	 * @param nodeName
	 * @return
	 */
	private static Node childNode(Node rootNode, String nodeName) {

		NodeList childNodes = rootNode.getChildNodes();

		for (int index = 0; index < childNodes.getLength(); index++) {

			Node aNode = childNodes.item(index);
			if (aNode.getNodeType() == Node.ELEMENT_NODE) {
				if (nodeName.equalsIgnoreCase(aNode.getNodeName())) {
					return aNode;
				}
			}

		}

		return null;
	}

	/**
	 * This method resolves the object locator automatically and returns the By
	 * object
	 * 
	 * @param pageName
	 * @param elementName
	 * @return
	 * @throws Exception
	 */
	public static By resolveObjectLocater(String pageName, String elementName)
			throws Exception {

		String basePath = TestConstants.BASE_PATH;

		ArrayList<String> xmlFileNames = XMLUtil.readXmlFileNames(basePath);

		Iterator<String> ite = xmlFileNames.iterator();
		String expression = "/pages/page[@ name= '" + pageName
				+ "']/uiobject[@name='" + elementName + "']/locator";
		By returnValue = null;

		while (ite.hasNext()) {
			String fileName = (String) ite.next();
			if (fileName != null
					&& (fileName.endsWith(".xml") || fileName.endsWith(".XML"))) {
				XMLUtil xmlUtil = new XMLUtil(basePath + fileName);
				String value = (String) xmlUtil.read(expression,
						XPathConstants.STRING);
				if (StringUtils.isNotEmpty(value)) {
					returnValue = CommonUtils.parseValueForLocator(value);
					// return returnValue;
				}
			}

		}

		if (returnValue != null) {
			ClassPathSearcher classPathSearcher = new ClassPathSearcher();
			Map<String, InputStream> fileMap = classPathSearcher
					.findFilesInClassPath(".*objectdata.*.xml");
			for (String key : fileMap.keySet()) {

				InputStream inputStream = fileMap.get(key);
				XMLUtil xmlUtil = new XMLUtil(basePath, inputStream);
				String value = (String) xmlUtil.read(expression,
						XPathConstants.STRING);
				if (StringUtils.isNotEmpty(value)) {
					// returnValue = Utils.parseElementValue(value);
					returnValue = CommonUtils.parseValueForLocator(value);
					break;
				}

			}

		}

		return returnValue;

	}

	/**
	 * This method is used to read the readTestData data from the XML's For
	 * exampleThe files will be in folder
	 * src/main/resources/data/testdata/INT1/EMEA Where INT1 is env name and
	 * EMEA region name read from the config value This is similar to
	 * readTestData but you can pass the testcaseId as additional param
	 * 
	 * @param commonData
	 * @param elementName
	 * @param testCaseId
	 * @return
	 * @throws Exception
	 */

	public static String readTestDataWithId(String commonData,
			String elementName, String testcaseId) throws Exception {

		String basePath = TestConstants.BASE_PATH_TEST_DATA;
		String env = System.getProperty("ENV");

		if (StringUtils.isEmpty(env)) {
			env = CommonUtils.readFromConfig("ENV");
		}

		basePath = basePath + "/" + env + "/";

		ArrayList<String> xmlFileNames = XMLUtil.readXmlFileNames(basePath);

		Iterator<String> ite = xmlFileNames.iterator();
		String expression = "/TestDataSet/TestData[@id='" + testcaseId + "']";
		String returnValue = "";

		while (ite.hasNext()) {
			String fileName = (String) ite.next();
			if (fileName != null
					&& (fileName.endsWith(".xml") || fileName.endsWith(".XML"))) {
				XMLUtil xmlUtil = new XMLUtil(basePath + "/" + fileName);

				Node rootNode = (Node) xmlUtil.read(expression,
						XPathConstants.NODE);
				if (rootNode != null) {

					Node childNode = childNode(rootNode, commonData);

					if (childNode != null) {

						Node subChildNode = childNode(childNode, elementName);
						returnValue = subChildNode.getTextContent();

						return returnValue;

					}
				}
			}

		}

		return returnValue;

	}

	/**
	 * /** This method is used to read the readTestData data from the XML's For
	 * exampleThe files will be in folder
	 * src/main/resources/data/testdata/INT1/EMEA Where INT1 is env name and
	 * EMEA region name read from the config value Method will return the
	 * HashMap with Node and Value
	 * 
	 * 
	 * 
	 * @param elementName
	 * @param testCaseId
	 * @return HashMap<String,String>
	 * @throws Exception
	 */

	public static Map<String, String> loadTestData(String elementName,
			String testcaseId) throws Exception {

		String basePath = TestConstants.BASE_PATH_TEST_DATA;
		String env = System.getProperty("ENV");

		if (StringUtils.isEmpty(env)) {
			env = CommonUtils.readFromConfig("ENV");
		}

		basePath = basePath + "/" + env + "/";

		ArrayList<String> xmlFileNames = XMLUtil.readXmlFileNames(basePath);

		Iterator<String> ite = xmlFileNames.iterator();
		String expression = "/TestDataSet/TestData[@id='" + testcaseId + "']/"
				+ elementName;

		Map<String, String> map = new HashMap<String, String>();

		while (ite.hasNext()) {
			String fileName = (String) ite.next();
			if (fileName != null
					&& (fileName.endsWith(".xml") || fileName.endsWith(".XML"))) {
				XMLUtil xmlUtil = new XMLUtil(basePath + "/" + fileName);

				Node rootNode = (Node) xmlUtil.read(expression,
						XPathConstants.NODE);

				if (rootNode != null) {

					NodeList childNodes = rootNode.getChildNodes();

					for (int index = 0; index < childNodes.getLength(); index++) {

						Node aNode = childNodes.item(index);
						if (aNode.getNodeType() == Node.ELEMENT_NODE) {

							map.put(aNode.getNodeName(), aNode.getTextContent());

						}

					}
				}
			}

		}

		return map;

	}
}
