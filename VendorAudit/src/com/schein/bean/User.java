/*******************************************************************************************************************
 * File			: User.java
 * 
 * Description	: This Object contains the user information. 
 * 				  
 * REVISION HISTORY
 * ****************
 * Date			Version		Phase						Change Description							Author
 * =============================================================================================================
 * 11/21/2011	1.0			FSC Release 2011		    Item Details Screen - Recreated for FSC 	Sriram Krishnamurthy
 *														instead of linking to BO Item Details.  
 * 														Added attribute isIMUser.
 *******************************************************************************************************************
 */
package com.schein.bean;

import java.io.Serializable;

/**
 * @author Dilip Nair
 *
 * This bean holds the user information.
 * All the fields in the table F560212
 */
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;					//UserId for user
	private String userName;				//UserName		
	private String	userDspn;				//Display Name if used
	private String role;					//Role for user ,eg:buyer,manager etc;
	private int	buyerId;				//unique ID for buyer
	private String	parentId;				//ID for the manager/parent
	private String team;					//ID of the team lead
	private String division;				//Divsion/Group user belongs to 
	private String email;					//Email Id for user
	private String active;					//Active Flag y/n
	private boolean isIMUser;
	private int userLevel;				//User Level used for defining authority
	private int mgrLevel;					//Mgr Level		
	private String admUser;				//Flag to know if admin user or not.
	private String updUser;				//User id updated this record
	private String updDate;				//Date updated
	private String crtDate;				//Date created

	/**
	 * 
	 */
	public User() {
		super();
		this.userId="";
		this.userName="";
		this.userDspn="";
		this.role="";
		this.buyerId=0;
		this.parentId="";
		this.team="";
		this.division="";
		this.email="";
		this.active="N";
		
		this.userLevel=0;
		this.mgrLevel=0;
		this.admUser="N";
		this.updUser="";
		this.updDate="";
		this.crtDate="";
	}

	public boolean isAdmin() {
		if (this.admUser.equalsIgnoreCase("Y"))
			return true;
			else
			return false;
	}
	
	public boolean isActive() {
		if (this.active.equalsIgnoreCase("Y"))
			return true;
			else
			return false;
	}
	/**
	 * Returns the active.
	 * @return String
	 */
	public String getActive() {
		return active;
	}

	/**
	 * Returns the admUser.
	 * @return String
	 */
	public String getAdmUser() {
		return admUser;
	}

	/**
	 * Returns the buyerId.
	 * @return int
	 */
	public int getBuyerId() {
		return buyerId;
	}

	/**
	 * Returns the crtDate.
	 * @return String
	 */
	public String getCrtDate() {
		return crtDate;
	}

	/**
	 * Returns the division.
	 * @return String
	 */
	public String getDivision() {
		return division;
	}

	/**
	 * Returns the email.
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the mgrLevel.
	 * @return int
	 */
	public int getMgrLevel() {
		return mgrLevel;
	}

	/**
	 * Returns the parentId.
	 * @return String
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * Returns the role.
	 * @return String
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Returns the updDate.
	 * @return String
	 */
	public String getUpdDate() {
		return updDate;
	}

	/**
	 * Returns the updUser.
	 * @return String
	 */
	public String getUpdUser() {
		return updUser;
	}

	/**
	 * Returns the userDspn.
	 * @return String
	 */
	public String getUserDspn() {
		return userDspn;
	}

	/**
	 * Returns the userId.
	 * @return String
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Returns the userLevel.
	 * @return int
	 */
	public int getUserLevel() {
		return userLevel;
	}

	/**
	 * Returns the userName.
	 * @return String
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the active.
	 * @param active The active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * Sets the admUser.
	 * @param admUser The admUser to set
	 */
	public void setAdmUser(String admUser) {
		this.admUser = admUser;
	}

	/**
	 * Sets the buyerId.
	 * @param buyerId The buyerId to set
	 */
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	/**
	 * Sets the crtDate.
	 * @param crtDate The crtDate to set
	 */
	public void setCrtDate(String crtDate) {
		this.crtDate = crtDate;
	}

	/**
	 * Sets the division.
	 * @param division The division to set
	 */
	public void setDivision(String division) {
		this.division = division;
	}

	/**
	 * Sets the email.
	 * @param email The email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Sets the mgrLevel.
	 * @param mgrLevel The mgrLevel to set
	 */
	public void setMgrLevel(int mgrLevel) {
		this.mgrLevel = mgrLevel;
	}

	/**
	 * Sets the parentId.
	 * @param parentId The parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * Sets the role.
	 * @param role The role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Sets the updDate.
	 * @param updDate The updDate to set
	 */
	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}

	/**
	 * Sets the updUser.
	 * @param updUser The updUser to set
	 */
	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}

	/**
	 * Sets the userDspn.
	 * @param userDspn The userDspn to set
	 */
	public void setUserDspn(String userDspn) {
		this.userDspn = userDspn;
	}

	/**
	 * Sets the userId.
	 * @param userId The userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Sets the userLevel.
	 * @param userLevel The userLevel to set
	 */
	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}

	/**
	 * Sets the userName.
	 * @param userName The userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Returns the team.
	 * @return String
	 */
	public String getTeam() {
		return team;
	}

	/**
	 * Sets the team.
	 * @param team The team to set
	 */
	public void setTeam(String team) {
		this.team = team;
	}
	public boolean isIMUser() {
		return isIMUser;
	}

	public void setIMUser(boolean isIMUser) {
		this.isIMUser = isIMUser;
	}	
}
