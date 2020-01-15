package com.schein.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collection;



/**
 * @author Dilip Nair
 *
 *This bean is used to hold the information for a user logged in to the BO system.
 */
public class LoginUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private	User		userInfo;
	private	LinkedList	autBuyerList;
	private	String		autBuyerString;
	private	LinkedList	buyerFillRate;
	private 	HashMap		bOptions;//options selected on buyerfillrate	
//	private 	HashMap		dOptions;//options selected on division fillrate
	
	private HashMap		navigatorTable;		//used to control the page navigation in reports
											//This hashtable can contain entries for different pages in the  application
	private String		currentListId;		//this will keep ListID of the last selected item list
										
	private	LinkedList	userMainMenuList; 
	private	LinkedList	userSubMenuList; 
	/**
	 * Returns the autBuyerList.
	 * @return LinkedList
	 */
	public LinkedList getAutBuyerList() {
		return autBuyerList;
	}

	/**
	 * Returns the autBuyerString.
	 * @return String
	 */
	public String getAutBuyerString() {
		return autBuyerString;
	}

	/**
	 * Returns the buyerFillRate.
	 * @return LinkedList
	 */
	public LinkedList getBuyerFillRate() {
		return buyerFillRate;
	}

	/**
	 * Returns the userInfo.
	 * @return User
	 */
	public User getUserInfo() {
		return userInfo;
	}

	/**
	 * Sets the autBuyerList.
	 * @param autBuyerList The autBuyerList to set
	 */
	public void setAutBuyerList(LinkedList autBuyerList) {
		this.autBuyerList = autBuyerList;
	}

	/**
	 * Sets the autBuyerString.
	 * @param autBuyerString The autBuyerString to set
	 */
	public void setAutBuyerString(String autBuyerString) {
		this.autBuyerString = autBuyerString;
	}

	/**
	 * Sets the buyerFillRate.
	 * @param buyerFillRate The buyerFillRate to set
	 */
	public void setBuyerFillRate(LinkedList buyerFillRate) {
		this.buyerFillRate = buyerFillRate;
	}

	/**
	 * Sets the userInfo.
	 * @param userInfo The userInfo to set
	 */
	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * Returns the bOptions.
	 * @return HashMap
	 */
	public HashMap getBOptions() {
		return bOptions;
	}

	/**
	 * Sets the bOptions.
	 * @param bOptions The bOptions to set
	 */
	public void setBOptions(HashMap bOptions) {
		this.bOptions = bOptions;
	}

	/**
	 * @return
	 */
	public HashMap getNavigatorTable() {
		return navigatorTable;
	}
	
	/**
	 * @param map
	 */
	public void setNavigatorTable(HashMap map) {
		navigatorTable = map;
	}
	
	/**
	 * 
	 * @param reportId
	 * @param list
	 * Stores a navigation list into the navigatorTable
	 */
	
	public void setListForReport(String reportId,Collection list){
		this.navigatorTable.put(reportId,list);
	}
	
	/**
	 * 
	 * @param reportId
	 * @return Collection
	 * Returns a list(any collection class) stored in the hashtable , to help navigating the reports
	 */
	/*
	 * navigatorTable is a HashMap which contains any list to help navigating pages.
	 * Any new navigation list required can be stored in to this hashmap with an id, and later retrieved when required.
	 */
	public Collection getListForReport(String reportId){
		if (this.navigatorTable.containsKey(reportId))
			return (Collection)this.navigatorTable.get(reportId);
			else
			return null;
				
	}
	/**
	 * @return
	 */
	public String getCurrentListId() {
		return currentListId;
	}

	/**
	 * @param string
	 */
	public void setCurrentListId(String string) {
		currentListId = string;
	}

	public LinkedList getUserMainMenuList() {
		return userMainMenuList;
	}

	public void setUserMainMenuList(LinkedList userMainMenuList) {
		this.userMainMenuList = userMainMenuList;
	}

	public LinkedList getUserSubMenuList() {
		return userSubMenuList;
	}

	public void setUserSubMenuList(LinkedList userSubMenuList) {
		this.userSubMenuList = userSubMenuList;
	}

}