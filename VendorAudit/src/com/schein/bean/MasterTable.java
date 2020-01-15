/**
 * Bean for Audit Master Table
 */
package com.schein.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * This is the audit master table a.k.a. IM0160
 */
public class MasterTable {

	/** Property controlNo */
	Integer controlNo;

	/** Property poNumber */
	String poNumber;

	/** Property analyst */
	String analyst;

	/** Property observeDte */
	BigDecimal observeDte;

	/** Property status */
	String status;

	/** Property shipLoc */
	String shipLoc; // Always state

	/** Property proNumber */
	String proNumber;

	/** Property Good Load */
	String goodLoad;

	/** Property Follow up email date */
	BigDecimal followupDate;

	/** Property Responded date */
	BigDecimal responseDate;
	
	/**Property Carrier */
	Integer carrier;

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
	 * Constructor
	 */
	public MasterTable() {
	}

	/**
	 * Gets the controlNo
	 */
	public Integer getControlNo() {
		return this.controlNo;
	}

	/**
	 * Sets the controlNo
	 */
	public void setControlNo(Integer value) {
		this.controlNo = value;
	}

	/**
	 * Gets the poNumber
	 */
	public String getPoNumber() {
		return this.poNumber;
	}

	/**
	 * Sets the poNumber
	 */
	public void setPoNumber(String value) {
		this.poNumber = value;
	}

	/**
	 * Gets the analyst
	 */
	public String getAnalyst() {
		return this.analyst;
	}

	/**
	 * Sets the analyst
	 */
	public void setAnalyst(String value) {
		this.analyst = value;
	}

	/**
	 * Gets the observeDte
	 */
	public BigDecimal getObserveDte() {
		return this.observeDte;
	}

	/**
	 * Sets the observeDte
	 */
	public void setObserveDte(BigDecimal value) {
		this.observeDte = value;
	}

	/**
	 * Gets the status
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * Sets the status
	 */
	public void setStatus(String value) {
		this.status = value;
	}

	/**
	 * Gets the shipLoc
	 */
	public String getShipLoc() {
		return this.shipLoc;
	}

	/**
	 * Sets the shipLoc
	 */
	public void setShipLoc(String value) {
		this.shipLoc = value;
	}

	/**
	 * Gets the proNumber
	 */
	public String getProNumber() {
		return this.proNumber;
	}

	/**
	 * Sets the proNumber
	 */
	public void setProNumber(String value) {
		this.proNumber = value;
	}

	/**
	 * @return the goodLoad
	 */
	public String getGoodLoad() {
		return goodLoad;
	}

	/**
	 * @param goodLoad
	 *            the goodLoad to set
	 */
	public void setGoodLoad(String goodLoad) {
		this.goodLoad = goodLoad;
	}

	/**
	 * @return the followupDate
	 */
	public BigDecimal getFollowupDate() {
		return followupDate;
	}

	/**
	 * @param followupDate
	 *            the followupDate to set
	 */
	public void setFollowupDate(BigDecimal followupDate) {
		this.followupDate = followupDate;
	}

	/**
	 * @return the responseDate
	 */
	public BigDecimal getResponseDate() {
		return responseDate;
	}

	/**
	 * @param responseDate
	 *            the responseDate to set
	 */
	public void setResponseDate(BigDecimal responseDate) {
		this.responseDate = responseDate;
	}
	
	

	/**
	 * @return the carrier
	 */
	public Integer getCarrier() {
		return carrier;
	}

	/**
	 * @param carrier the carrier to set
	 */
	public void setCarrier(Integer carrier) {
		this.carrier = carrier;
	}

	/**
	 * Gets the crtUser
	 */
	public String getCrtUser() {
		return this.crtUser;
	}

	/**
	 * Sets the crtUser
	 */
	public void setCrtUser(String value) {
		this.crtUser = value;
	}

	/**
	 * Gets the crtPgm
	 */
	public String getCrtPgm() {
		return this.crtPgm;
	}

	/**
	 * Sets the crtPgm
	 */
	public void setCrtPgm(String value) {
		this.crtPgm = value;
	}

	/**
	 * Gets the tsCrte
	 */
	public Timestamp getTsCrte() {
		return this.tsCrte;
	}

	/**
	 * Sets the tsCrte
	 */
	public void setTsCrte(Timestamp value) {
		this.tsCrte = value;
	}

	/**
	 * Gets the updUser
	 */
	public String getUpdUser() {
		return this.updUser;
	}

	/**
	 * Sets the updUser
	 */
	public void setUpdUser(String value) {
		this.updUser = value;
	}

	/**
	 * Gets the updPgm
	 */
	public String getUpdPgm() {
		return this.updPgm;
	}

	/**
	 * Sets the updPgm
	 */
	public void setUpdPgm(String value) {
		this.updPgm = value;
	}

	/**
	 * Gets the tsUpd
	 */
	public Timestamp getTsUpd() {
		return this.tsUpd;
	}

	/**
	 * Sets the tsUpd
	 */
	public void setTsUpd(Timestamp value) {
		this.tsUpd = value;
	}
}