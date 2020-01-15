package com.schein.utils;

public class DropDownOption implements Option {
	private String keyValue;
	private String dispValue;
	private boolean isSelected;
	
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public void setDispValue(String dispValue) {
		this.dispValue = dispValue;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getDisplayValue() {
		return dispValue;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public boolean isSelected() {
		return isSelected;
	}
	
}
