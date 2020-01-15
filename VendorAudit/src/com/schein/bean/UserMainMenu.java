/*******************************************************************************************************************
 * File			: UserMainMenu.java
 * 
 * Description	: This bean has getter and setter methods for user Main menu. 
 * 
 * REVISION HISTORY
 * ****************
 * Date			Version		Phase					Change Description
 * =============================================================================================================
 * 08/31/2009	1.0		BO Release 4	 IM Portal Security
 * 
 *******************************************************************************************************************
 * @author Nilesh.Kawoor
 */
package com.schein.bean;

public class UserMainMenu {
 
	private String ctgryCode = ""; 
	private String menuCode = "";
	private String menuDesc = "";
	private String menuTitle = "";
	private String url = "";
	
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getMenuDesc() {
		return menuDesc;
	}
	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}
	public String getCtgryCode() {
		return ctgryCode;
	}
	public void setCtgryCode(String ctgryCode) {
		this.ctgryCode = ctgryCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMenuTitle() {
		return menuTitle;
	}
	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}
	
	
}
