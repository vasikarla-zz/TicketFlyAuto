package com.ticketfly.dataobjects;

/**
 * Class for Get/Set values for the DustDataSheet
 * 
 * @author rvasikarla
 * 
 */
public class DustDataSheet {

	private String testcaseid;
	private String xpathdata;
	private String reftext;

	/**
	 * Method to get the Test Case Id
	 * @return
	 */
	public String getTestcaseid() {
		return testcaseid;
	}

	/**
	 * Method to set Test Case Id
	 * @param testcaseid
	 */
	public void setTestcaseid(String testcaseid) {
		this.testcaseid = testcaseid;
	}

	/**
	 * Method to get Xpath Data
	 * @return
	 */
	public String getXpathdata() {
		return xpathdata;
	}

	/**
	 * Method to Set Xpath Data
	 * @param xpathdata
	 */
	public void setXpathdata(String xpathdata) {
		this.xpathdata = xpathdata;
	}

	/**
	 * Method to get RefText
	 * @return
	 */
	public String getReftext() {
		return reftext;
	}

	/**
	 * Method to set RefText
	 * @param reftext
	 */
	public void setReftext(String reftext) {
		this.reftext = reftext;
	}

}
