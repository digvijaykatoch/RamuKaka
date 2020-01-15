/*******************************************************************************************************************
 * File			: PageAccess.java
 * 
 * Description	: This bean has getter and setter methods for Page Access Information. 
 * 
 * REVISION HISTORY
 * ****************
 * Date			Version		Phase					Change Description
 * =============================================================================================================
 * 08/31/2009	1.0		BO Release 4	 IM Portal Security
 * 
 *******************************************************************************************************************
 */
package com.schein.bean;

public class PageAccess {
 
	private String userRole = ""; 
	private String jspFile= "";
	private String userId= "";
	public String getJspFile() {
		return jspFile;
	}
	public void setJspFile(String jspFile) {
		this.jspFile = jspFile;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
