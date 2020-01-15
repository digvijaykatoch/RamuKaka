package com.schein.utils;

import java.util.*;

/**
 * @author Dilip Nair
 *
 * This class contains values global to the backorder system.
 * The values which dont change often are kept here for fast and easy access
 * 
 * All the methods and attributes are defined static 
 */
public class CachingManager {

	private static LinkedList whseList;
	private static LinkedList boReasonList;
	private static HashMap boSalesDiv;
	private static HashMap sysProperties;
	private static String	 serverIpAddr;
	private static String	 company;
	private	static String	 dataSourceName;
	private static LinkedList boDivisionList;
	private	static String	 dataSourceNameForE;
	private static LinkedList fscWhseList;
	private static Map scacCodeMap;
	private static Map dcNameNoMap;
	
	
	
	
	/**
	 * Returns the boReasonList.
	 * @return LinkedList
	 * 
	 * returns a the reason code and descriptions
	 */
	public static LinkedList getBoReasonList() {
		if (boReasonList==null) {
			boReasonList = (LinkedList)BoUtils.getBoReasonCode();
		}
		return boReasonList;
	}

	/**
	 * Returns the whseList.
	 * @return LinkedList
	 * 
	 * Returns the active ware house list
	 */
	public static LinkedList getWhseList() {
		if (whseList==null) {
			whseList = (LinkedList)BoUtils.getActiveWhse();
		}
		return whseList;
	}

	/**
	 * Sets the boReasonList.
	 * @param boReasonList The boReasonList to set
	 */
	public static void setBoReasonList(LinkedList boReasonList) {
		CachingManager.boReasonList = boReasonList;
	}

	/**
	 * Sets the whseList.
	 * @param whseList The whseList to set
	 */
	public static void setWhseList(LinkedList whseList) {
		CachingManager.whseList = whseList;
	}

	/**
	 * Returns the boSalesDiv.
	 * @return HashMap
	 * 
	 * Returns the sales divisions used in the system
	 */
	public static HashMap getBoSalesDiv() {
		if (boSalesDiv==null) {
			boSalesDiv = (HashMap)(BoUtils.getSalesDivCode());
		}
		return boSalesDiv;
	}

	/**
	 * Sets the boSalesDiv.
	 * @param boSalesDiv The boSalesDiv to set
	 */
	public static void setBoSalesDiv(HashMap boSalesDiv) {
		CachingManager.boSalesDiv = boSalesDiv;
	}

	/**
	 * Returns the sysProperties.
	 * @return Properties
	 * 
	 * Returns the system properties 
	 */
	public static HashMap getSysProperties() {
		if(sysProperties==null) {
			sysProperties = (HashMap)BoUtils.getAllProperties();
		}
		return sysProperties;
	}

	/**
	 * Sets the sysProperties.
	 * @param sysProperties The sysProperties to set
	 */
	public static void setSysProperties(HashMap sysProperties) {
		CachingManager.sysProperties = sysProperties;
	}

	/**
	 * Returns the serverIpAddr.
	 * @return String
	 * 
	 * Returns the Ip Address of the server running the program
	 */
	public static String getServerIpAddr() {
		return serverIpAddr;
	}

	/**
	 * Sets the serverIpAddr.
	 * @param serverIpAddr The serverIpAddr to set
	 *	 *
	 */
	public static void setServerIpAddr(String serverIpAddr) {
		CachingManager.serverIpAddr = serverIpAddr;
	}



	/**
	 * Method getProperty.
	 * @param propertyName
	 * @return String
	 * 
	 * Returns the value of a single property from the properties list
	 */
	public static String getProperty(String propertyName) {
		if (getSysProperties().get(propertyName)!=null)
			return(((String)getSysProperties().get(propertyName)).trim());
		else 
			return null;
	}
	/**
	 * @author dnair<br>
	 * getCompany
	 * @return 
	 */
	public static String getCompany() {
		return company;
	}

	/**
	 * @author dnair<br>
	 * getDataSourceName
	 * @return 
	 */
	public static String getDataSourceName() {
		return dataSourceName;
	}

	/**
	 * @author dnair<br>
	 * setCompany
	 * @param string 
	 */
	public static void setCompany(String string) {
		company = string;
	}

	/**
	 * @author dnair<br>
	 * setDataSourceName
	 * @param string 
	 */
	public static void setDataSourceName(String string) {
		dataSourceName = string;
	}

	public static LinkedList getBoDivisionList() {
		return boDivisionList;
	}

	public static void setBoDivisionList(LinkedList boDivisionList) {
		CachingManager.boDivisionList = boDivisionList;
	}

	public static String getDataSourceNameForE() {
		return dataSourceNameForE;
	}

	public static void setDataSourceNameForE(String dataSourceNameForE) {
		CachingManager.dataSourceNameForE = dataSourceNameForE;
	}

	public static LinkedList getFscWhseList() {
		return fscWhseList;
	}

	public static void setFscWhseList(LinkedList fscWhseList) {
		CachingManager.fscWhseList = fscWhseList;
	}

	public static Map getScacCodeMap() {
		if(scacCodeMap == null) 
			scacCodeMap = (Map)BoUtils.getScacCodeMap();
				
		return scacCodeMap;
	}

	public static void setScacCodeMap(Map scacCodeMap) {
		CachingManager.scacCodeMap = scacCodeMap;
	}

	public static Map getDcNameNoMap() {
		if(dcNameNoMap==null)
			 dcNameNoMap = BoUtils.getWarehouseMap();
		return dcNameNoMap;
	}

	public static void setDcNameNoMap(Map dcNameNoMap) {
		CachingManager.dcNameNoMap = dcNameNoMap;
	}

}
