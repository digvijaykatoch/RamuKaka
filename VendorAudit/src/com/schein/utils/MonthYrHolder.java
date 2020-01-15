/*
 * Created on Oct 4, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.schein.utils;

/**
 * @author Santosh.Thakur
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MonthYrHolder {
	String monthYearName = "";
	String monthYearValue = "";
	
	public MonthYrHolder(){
	}
	public MonthYrHolder(String name, String value){
		monthYearName = name;
		monthYearValue = value;
	}
	/**
	 * @return
	 * returns String
	 */
	public String getMonthYearName() {
		return monthYearName;
	}

	/**
	 * @return
	 * returns String
	 */
	public String getMonthYearValue() {
		return monthYearValue;
	}

	/**
	 * @param string
	 * returns void
	 */
	public void setMonthYearName(String string) {
		monthYearName = string;
	}

	/**
	 * @param string
	 * returns void
	 */
	public void setMonthYearValue(String string) {
		monthYearValue = string;
	}

}
