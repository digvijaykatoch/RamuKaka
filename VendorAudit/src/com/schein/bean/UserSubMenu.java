/*******************************************************************************************************************
 * File			: UserSubMenu.java
 * 
 * Description	: This bean has getter and setter methods for User sub menu. 
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

public class UserSubMenu {
	
	private String menuCode = "";
	private String menuDesc = "";
	private String subMenuCode = "";
	private String subMenuDesc = "";
	private String subMenuTitle = "";
	private String activeFlag = "";
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
	public String getSubMenuCode() {
		return subMenuCode;
	}
	public void setSubMenuCode(String subMenuCode) {
		this.subMenuCode = subMenuCode;
	}
	public String getSubMenuDesc() {
		return subMenuDesc;
	}
	public void setSubMenuDesc(String subMenuDesc) {
		this.subMenuDesc = subMenuDesc;
	}
	public String getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSubMenuTitle() {
		return subMenuTitle;
	}
	public void setSubMenuTitle(String subMenuTitle) {
		this.subMenuTitle = subMenuTitle;
	}
	
}
