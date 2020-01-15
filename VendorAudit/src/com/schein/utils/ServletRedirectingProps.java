/*
 * Created on Oct 31, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.schein.utils;

import java.io.*;
/**
 * @author Santosh.Thakur
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ServletRedirectingProps implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String ReportName ="";
	public String ClassName ="";
	public String JspName ="";
	public String MethodName ="";
	
	public ServletRedirectingProps(){
		ReportName = "null";
		ClassName = "null";
		JspName = "null";
		MethodName = "null";
	};
	/**
	 * @return
	 */
	public String getReportName() {
		return ReportName;
	}

	/**
	 * @return
	 */
	public String getClassName() {
		return ClassName;
	}

	/**
	 * @return
	 */
	public String getJspName() {
		return JspName;
	}

	/**
	 * @return
	 */
	public String getMethodName() {
		return MethodName;
	}
	/**
	 * @param string
	 */
	public void setReportName(String string) {
		ReportName = string;
	}

	/**
	 * @param string
	 */
	public void setClassName(String string) {
		ClassName = string;
	}

	/**
	 * @param string
	 */
	public void setJspName(String string) {
		JspName = string;
	}

	/**
	 * @param string
	 */
	public void setMethodName(String string) {
		MethodName = string;
	}
	public String doPrint() {
		return (	ClassName+" "+JspName+" "+MethodName);
	}
}
