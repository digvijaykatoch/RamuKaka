/*******************************************************************************************************************
 * File			: CustomPropertyBean.java
 * 
 * Description	: This Bean class contains Disposition Details from F570406 table.
 * REVISION HISTORY
 * ****************
 * Date			Version		Phase					Change Description
 * =============================================================================================================
 * 10/22/2008	1.0		Expired DB Release-I	Initial Release
 * 
 *******************************************************************************************************************
 */
package com.schein.bean;

/**
 * @author Sridhar.Muppa
 *
 */
public class CustomPropertyBean extends BaseInfoBean{
	private static final long serialVersionUID = 1L;
	private String key; 
	private int keyCode; 
	private int sequence; 
	private String shortValue;
	private String longValue;
	private String activeFlag;
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @return the longValue
	 */
	public String getLongValue() {
		return longValue;
	}
	/**
	 * @return the keyCode
	 */
	public int getKeyCode() {
		return keyCode;
	}
	/**
	 * @param keyCode the keyCode to set
	 */
	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}
	/**
	 * @return the sequence
	 */
	public int getSequence() {
		return sequence;
	}
	/**
	 * @return the shortValue
	 */
	public String getShortValue() {
		return shortValue;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @param longValue the longValue to set
	 */
	public void setLongValue(String longValue) {
		this.longValue = longValue;
	}
	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	/**
	 * @param shortValue the shortValue to set
	 */
	public void setShortValue(String shortValue) {
		this.shortValue = shortValue;
	}
	/**
	 * @return the activeFlag
	 */
	public String getActiveFlag() {
		return activeFlag;
	}
	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
}
