package com.schein.bean;

import java.sql.Timestamp;

public class EmailTable {

	/** Property controlNo */
	Integer controlNo;

	/** Property emailId */
	String emailId;

	/** Property toEmail */
	String toEmail;

	/** Property subject */
	String subject;

	/** Property message */
	String message;

	/** Property crtUser */
	String crtUser;

	/** Property crtPgm */
	String crtPgm;

	/** Property tsCrte */
	Timestamp tsCrte;

	/** Property updUser */
	String updUser;

	/** Property updPgm */
	String updPgm;

	/** Property tsUpd */
	Timestamp tsUpd;

	/**
	 * Gets the ControlNo
	 */
	public Integer getControlNo() {
		return controlNo;
	}

	/**
	 * Sets the ControlNo
	 */
	public void setControlNo(Integer controlNo) {
		this.controlNo = controlNo;
	}

	/**
	 * Gets the EmailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * Sets the EmailId
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * Gets the ToEmail
	 */
	public String getToEmail() {
		return toEmail;
	}

	/**
	 * Sets the ToEmail
	 */
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	/**
	 * Gets the Subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the Subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the Message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the Message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the CrtUser
	 */
	public String getCrtUser() {
		return crtUser;
	}

	/**
	 * Sets the CrtUser
	 */
	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}

	/**
	 * Gets the CrtPgm
	 */
	public String getCrtPgm() {
		return crtPgm;
	}

	/**
	 * Sets the CrtPgm
	 */
	public void setCrtPgm(String crtPgm) {
		this.crtPgm = crtPgm;
	}

	/**
	 * Gets the TsCrte
	 */
	public Timestamp getTsCrte() {
		return tsCrte;
	}

	/**
	 * Sets the TsCrte
	 */
	public void setTsCrte(Timestamp tsCrte) {
		this.tsCrte = tsCrte;
	}

	/**
	 * Gets the UpdUser
	 */
	public String getUpdUser() {
		return updUser;
	}

	/**
	 * Sets the UpdUser
	 */
	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}

	/**
	 * Gets the UpdPgm
	 */
	public String getUpdPgm() {
		return updPgm;
	}

	/**
	 * Sets the UpdPgm
	 */
	public void setUpdPgm(String updPgm) {
		this.updPgm = updPgm;
	}

	/**
	 * Gets the TsUpd
	 */
	public Timestamp getTsUpd() {
		return tsUpd;
	}

	/**
	 * Sets the TsUpd
	 */
	public void setTsUpd(Timestamp tsUpd) {
		this.tsUpd = tsUpd;
	}

}
