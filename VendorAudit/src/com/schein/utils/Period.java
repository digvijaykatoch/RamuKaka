package com.schein.utils;

public class Period implements Option {
	private int month;
	private int year;
	private boolean isSelected;
	
	public Period(int period) {
		this.month = period % 100;
		this.year = period / 100;
	}
	
	public Period(int period, boolean isSelected) {
		this.month = period % 100;
		this.year = period / 100;
		this.isSelected = isSelected;
	}
	
	public String getDisplayValue() {
		
		return getMonthText() + " " + year;
	}

	public String getKeyValue() {
		return ""+(year*100+month);
	}

	public boolean isSelected() {
		return isSelected;
	}
	
	private String getMonthText() {
		switch (month) {
		case 1: return "Jan";
		case 2: return "Feb";
		case 3: return "Mar";
		case 4: return "Apr";
		case 5: return "May";
		case 6: return "Jun";
		case 7: return "Jul";
		case 8: return "Aug";
		case 9: return "Sep";
		case 10: return "Oct";
		case 11: return "Nov";
		case 12: return "Dec";
		default: return "";
		}
	}
}
