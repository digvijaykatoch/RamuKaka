package com.schein.bean;

/**
 * @author Dilip Nair
 *
 * Simple bean which holds the reason code and description applied when an Item is backordered
 * 
 */
public class BoReasonCode {

	private int reasonCode;
	private String reasonDesc;
	/**
	 * Returns the reasonCode.
	 * @return int
	 */
	public int getReasonCode() {
		return reasonCode;
	}

	/** 
	 * Returns the reasonDesc.
	 * @return String
	 */
	public String getReasonDesc() {
		return reasonDesc;
	}

	/**
	 * Sets the reasonCode.
	 * @param reasonCode The reasonCode to set
	 */
	public void setReasonCode(int reasonCode) {
		this.reasonCode = reasonCode;
	}

	/**
	 * Sets the reasonDesc.
	 * @param reasonDesc The reasonDesc to set
	 */
	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}

}
