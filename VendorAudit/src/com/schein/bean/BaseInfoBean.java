/*******************************************************************************************************************
 * File			: BaseInfoBean.java
 * 
 * Description	: This class has setter and getter methods for log fields and beans can extend this file.
 * REVISION HISTORY
 * ****************
 * Date			Version		Phase					Change Description
 * =============================================================================================================
 * 09/23/2008	1.0		Expired DB Release-I	Initial Release
 * 
 *******************************************************************************************************************
 */
package com.schein.bean;

import java.io.Serializable;

/**
 * @author Sridhar.Muppa
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BaseInfoBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String crtUser; //Record Created User id
	private String crtDate; //Record Create date
	private String crtTime; //Record Create Time
	private String programId;//Program ID
	private String workID ;//Workstation ID 
	
	/**
	 * @return
	 */
	public String getCrtDate() {
		return crtDate;
	}
	
	/**
	 * @param crtDate
	 */
	public void setCrtDate(String crtDate) {
		this.crtDate = crtDate;
	}
	
	/**
	 * @return
	 */
	public String getCrtTime() {
		return crtTime;
	}
	
	/**
	 * @param crtTime
	 */
	public void setCrtTime(String crtTime) {
		this.crtTime = crtTime;
	}
	
	/**
	 * @return
	 */
	public String getCrtUser() {
		return crtUser;
	}
	
	/**
	 * @param crtUser
	 */
	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}
	
	/**
	 * @return
	 */
	public String getProgramId() {
		return programId;
	}
	
	/**
	 * @param programId
	 */
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	/**
	 * @return
	 */
	public String getWorkID() {
		return workID;
	}
	
	/**
	 * @param workID
	 */
	public void setWorkID(String workID) {
		this.workID = workID;
	}
}
