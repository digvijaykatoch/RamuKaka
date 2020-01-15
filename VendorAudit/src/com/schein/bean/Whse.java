package com.schein.bean;

/**
 * @author Dilip Nair
 *
 * Bean which holds the warehouse number and the display name
 */
public class Whse {

	private int bowhse;
	private String whseDspn;
	private String shipLoc;  //warehouse shipping location
	private String xShipCalculatorFlag;
	
	/**
	 * Returns the bowhse.
	 * @return int
	 */
	public int getBowhse() {
		return bowhse;
	}

	/**
	 * Returns the whseDspn.
	 * @return String
	 */
	public String getWhseDspn() {
		return whseDspn;
	}

	/**
	 * Sets the bowhse.
	 * @param bowhse The bowhse to set
	 */
	public void setBowhse(int bowhse) {
		this.bowhse = bowhse;
	}

	/**
	 * Sets the whseDspn.
	 * @param whseDspn The whseDspn to set
	 */
	public void setWhseDspn(String whseDspn) {
		this.whseDspn = whseDspn;
	}

	/**
	 * @return
	 */
	public String getXShipCalculatorFlag() {
		return xShipCalculatorFlag;
	}

	/**
	 * @param c
	 */
	public void setXShipCalculatorFlag(String c) {
		xShipCalculatorFlag = c;
	}

	public String getShipLoc() {
		return shipLoc;
	}

	public void setShipLoc(String shipLoc) {
		this.shipLoc = shipLoc;
	}

}
