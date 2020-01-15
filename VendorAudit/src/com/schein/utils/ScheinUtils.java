package com.schein.utils;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Dilip Nair
 * 
 *         This class contains some utitliy methods
 */
public class ScheinUtils {

	/**
	 * Method getJulianDate.
	 * 
	 * @param dateMDYYYY
	 * @return int
	 * 
	 *         Returns a julian date from a date with the format MM-DD-YYYY
	 */
	public static int getJulianDate(String dateMDYYYY) {
		DecimalFormat format = new DecimalFormat("000");
		int julianDate = 0;
		String convDate = "";
		int julianDay = 0;
		int year;
		int month;
		int day;
		if (dateMDYYYY.indexOf("-") > 0) {
			dateMDYYYY = replaceAll(dateMDYYYY, "-", "");
		} else if (dateMDYYYY.indexOf("/") > 0) {
			dateMDYYYY = replaceAll(dateMDYYYY, "/", "");
		}
		try {
			year = Integer.parseInt(dateMDYYYY.substring(4));
			month = Integer.parseInt(dateMDYYYY.substring(0, 2));
			month = month - 1;
			day = Integer.parseInt(dateMDYYYY.substring(2, 4));

			Calendar cal = new GregorianCalendar(year, month, day);

			julianDay = cal.get(Calendar.DAY_OF_YEAR);

			if ((year >= 1941) && (year <= 1999))
				convDate += "0";
			else
				convDate += "1";
			convDate += dateMDYYYY.substring(6) + format.format(julianDay);
			julianDate = Integer.parseInt(convDate);
		} catch (Exception e) {
			julianDate = 0;
		}
		return julianDate;
	}

	/**
	 * Method getJulianDateSep.
	 * 
	 * @param dateMDYYYY
	 * @return int
	 * 
	 *         Returns a julian date from a date with the format MMDDYYYY
	 *         containing any char separator
	 */
	public static int getJulianDateSep(String dateMDYYYY) {
		String newMDYYYY = dateMDYYYY.substring(0, 2)
				+ dateMDYYYY.substring(3, 5) + dateMDYYYY.substring(6);
		return getJulianDate(newMDYYYY);
	}

	/**
	 * Method getDateMDY.
	 * 
	 * @param julianDate
	 * @param separator
	 * @return String
	 * 
	 *         Converts a julian date to a String in MMDDYYYY format with a
	 *         separator
	 */
	public static String getDateMDY(int julianDate, char separator) {
		DecimalFormat format = new DecimalFormat("000000");
		DecimalFormat format1 = new DecimalFormat("00");
		GregorianCalendar gc = new GregorianCalendar();

		String convDate = "";
		String year;
		String dateMDYYYY = "";
		char century;

		int month;
		int days;
		int day;
		try {
			if (julianDate != 0) {

				convDate = format.format(julianDate);

				century = convDate.charAt(0);
				year = convDate.substring(1, 3);
				if (century == '1')
					year = "20" + year;
				else
					year = "19" + year;

				days = Integer.parseInt(convDate.substring(3));

				gc.set(GregorianCalendar.YEAR, Integer.parseInt(year));
				gc.set(GregorianCalendar.DAY_OF_YEAR, days);

				month = gc.get(GregorianCalendar.MONTH) + 1;
				day = gc.get(GregorianCalendar.DAY_OF_MONTH);
				if (separator == ' ')
					dateMDYYYY = format1.format(month) + format1.format(day)
							+ year;
				else
					dateMDYYYY = format1.format(month) + separator
							+ format1.format(day) + separator + year;
			}
		} catch (Exception e) {
			dateMDYYYY = "";
		}
		return dateMDYYYY;
	}

	/**
	 * Method getYMDSeparated.
	 * 
	 * @param YMD
	 * @param separator
	 * @return String Simple Util method to get a separated date from an String
	 *         date with YYMMDD format. Returns the String in MDYY format
	 * */
	public static String getYMDSeparated(String ymd, char separator) {
		StringBuffer sepDate = new StringBuffer();
		if (ymd != null && !"0".equals(ymd)) {
			if (ymd.length() == 6) {
				sepDate.append(ymd.substring(2, 4));
				sepDate.append(separator);
				sepDate.append(ymd.substring(4, 6));
				sepDate.append(separator);
				sepDate.append(ymd.substring(0, 2));
			} else if (ymd.length() == 8) {
				sepDate.append(ymd.substring(4, 6));
				sepDate.append(separator);
				sepDate.append(ymd.substring(6, 8));
				sepDate.append(separator);
				sepDate.append(ymd.substring(0, 4));
			} else {
				sepDate.append(ymd);
			}
		}

		return sepDate.toString();

	}

	/**
	 * Method getMDYSeparated.
	 * 
	 * @param mdy
	 * @param separator
	 * @return String Simple Util method to get a separated date from MMDDYY
	 *         format to YYMMDD. If the date is MDDYY it will be adjusted to
	 *         MMDDYY.
	 */
	public static String getMDYSeparated(String mdy, char separator) {
		StringBuffer sepDate = new StringBuffer();

		if (mdy.length() == 5)
			mdy = "0" + mdy;

		if (mdy.length() == 6) {
			sepDate.append(mdy.substring(0, 2));
			sepDate.append(separator);
			sepDate.append(mdy.substring(2, 4));
			sepDate.append(separator);
			sepDate.append(mdy.substring(4));
		} else
			sepDate.append(mdy);

		return sepDate.toString();

	}

	/**
	 * Method getToday_Julian.
	 * 
	 * @return int
	 * 
	 *         Returns today in Julian format
	 */
	public static int getToday_Julian() {
		int julianToday = 0;

		SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
		julianToday = getJulianDate(sdf.format(new Date()));

		return julianToday;
	}

	/**
	 * Method getToday_YMD.
	 * 
	 * @param separator
	 * @return String
	 * 
	 *         Returns today in YYYYMMDD with a separator
	 */
	public static String getToday_YMD(char separator) {
		String today = "";
		SimpleDateFormat sdf = null;
		if (separator != ' ')
			sdf = new SimpleDateFormat("yyyy" + separator + "MM" + separator
					+ "dd");
		else
			sdf = new SimpleDateFormat("yyyyMMdd");

		today = sdf.format(new Date());
		return today;
	}

	/**
	 * Method getToday_MDY
	 * 
	 * @param separator
	 * @return String
	 * 
	 *         Returns today in MMDDYYYY with a separator
	 */
	public static String getToday_MDY(char separator) {
		String today = "";
		SimpleDateFormat sdf = null;
		if (separator != ' ')
			sdf = new SimpleDateFormat("MM" + separator + "dd" + separator
					+ "yyyy");
		else
			sdf = new SimpleDateFormat("MMddyyyy");

		today = sdf.format(new Date());
		return today;
	}

	/**
	 * Method getCurrentTime.
	 * 
	 * @return int
	 * 
	 *         Return the current time
	 */
	public static int getCurrentTime() {

		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		return (Integer.parseInt(sdf.format(new Date())));
	}

	/**
	 * Method replaceAll.
	 * 
	 * @param origString
	 * @param s1
	 * @param s2
	 * @return String This method will replace every occurence of String s1 with
	 *         s2 in String origString
	 */
	public static String replaceAll(String origString, String s1, String s2) {
		origString = origString.trim();
		StringBuffer st1 = new StringBuffer();
		StringTokenizer st = new StringTokenizer(origString, s1);
		int tokenCount = st.countTokens();

		int i = 0;
		if (origString.indexOf(s1) == 0)
			st1.append(s2);
		while (i < (tokenCount - 1)) {
			st1.append(st.nextToken() + s2);
			i++;
		}
		if (tokenCount > 0)
			st1.append(st.nextToken());
		if (origString.endsWith(s1))
			st1.append(s2);

		return (st1.toString());

	}

	/**
	 * @param YYYYMM
	 *            Returns a customized period from the Paramter given in YYYYMM
	 *            format Example for 200401 , Jan. 2004 wil be returned
	 * @return
	 */
	public static String convertPeriod(String YYYYMM) {
		String period = YYYYMM.substring(YYYYMM.length() - 2, YYYYMM.length());
		if (period.equals("01"))
			period = "Jan. ";
		else if (period.equals("02"))
			period = "Feb. ";
		else if (period.equals("03"))
			period = "Mar. ";
		else if (period.equals("04"))
			period = "Apr. ";
		else if (period.equals("05"))
			period = "May ";
		else if (period.equals("06"))
			period = "Jun. ";
		else if (period.equals("07"))
			period = "Jul. ";
		else if (period.equals("08"))
			period = "Aug. ";
		else if (period.equals("09"))
			period = "Sep. ";
		else if (period.equals("10"))
			period = "Oct. ";
		else if (period.equals("11"))
			period = "Nov. ";
		else if (period.equals("12"))
			period = "Dec. ";
		period += YYYYMM.substring(0, YYYYMM.length() - 2);

		return period;
	}

	/**
	 * @author dnair<br>
	 *         getmaxdays
	 * @param month
	 * @param year
	 * @return Maximum number of days in a given month
	 */
	public static int getmaxdays(int month, int year) {
		// create a GregorianCaleder object for the month and year passed and
		// here the last paramete 1 is just first date of the month.
		GregorianCalendar m_calendar = new GregorianCalendar(year, month, 1);
		int maxDays = m_calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return maxDays;
	}

	/**
	 * @author dnair<br>
	 *         formatString
	 * @param string
	 * @return Check for nulls and format the string
	 */
	public static String formatString(String string) {
		if (string != null)
			return string.trim();
		else
			return "";
	}

	/**
	 * @author Santosh.Thakur getMonthYear method is used to get a list of year
	 *         and month list for fromdate todate drop down list
	 */
	public static HashMap getMonthYear() {

		HashMap dropDownMap = new HashMap();
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int preYear = year - 2;
		String theYear = "" + year;
		String yearMonth = "" + year;
		MonthYrHolder mnthYrHolder = new MonthYrHolder();
		String mm = "";
		int count = 0;
		for (int kk = 1; kk <= 2; kk++) {
			for (int j = 1; j <= 12; j++) {
				if (("" + j).length() < 2) {
					mm = "0" + j;
				} else {
					mm = "" + j;
				}
				String preYearMonth = "" + preYear + mm;
				String name = preYearMonth;
				String value = convertPeriod(preYearMonth);
				mnthYrHolder = new MonthYrHolder(name, value);
				// dropDownMap.put(""+j,mnthYrHolder);
				count++;
				dropDownMap.put("" + count, mnthYrHolder);
			}
			preYear = preYear + 1;
		}
		for (int i = 1; i <= month + 1; i++) {
			if (("" + i).length() < 2) {
				mm = "0" + i;
			} else {
				mm = "" + i;
			}
			yearMonth = "" + theYear + mm;
			String name = yearMonth;
			String value = convertPeriod(yearMonth);
			mnthYrHolder = new MonthYrHolder(name, value);
			// dropDownMap.put(""+(i+12),mnthYrHolder);
			count++;
			dropDownMap.put("" + count, mnthYrHolder);
		}
		return dropDownMap;
	}

	/*
	 * Takes time string (HHMMSS or HMMSS) as input and returns ":" separated
	 * time stamp *
	 */

	public static String getFormatedTime(String timeString) {
		String formatedTime = "";
		if (timeString.length() == 6) {
			formatedTime = timeString.substring(0, 2) + ":"
					+ timeString.substring(2, 4) + ":"
					+ timeString.substring(4, 6);
		} else if (timeString.length() == 5) {
			formatedTime = timeString.substring(0, 1) + ":"
					+ timeString.substring(1, 3) + ":"
					+ timeString.substring(3, 5);
		}
		return formatedTime;
	}

	/**
	 * Method getPoOrigin.
	 * 
	 * @author Sriram.Krishnamurthy
	 * @param poOriginCode
	 * @return poOriginDescription Returns Purchase Order Origin Description
	 */

	public static String getPoOrigin(String poOriginCode) {
		String poOriginDescription = "";

		if (poOriginCode != null) {
			if (poOriginCode.trim().equalsIgnoreCase("E3")) {
				poOriginDescription = "E3";
			} else if (poOriginCode.trim().equalsIgnoreCase("SO")) {
				poOriginDescription = "Sales Order";
			} else if (poOriginCode.trim().equalsIgnoreCase("SP")) {
				poOriginDescription = "Suggested PO";
			} else if (poOriginCode.trim().equalsIgnoreCase("RQ")) {
				poOriginDescription = "Pro Repair";
			} else if (poOriginCode.trim().equalsIgnoreCase("EQ")) {
				poOriginDescription = "Equipment";
			} else if (poOriginCode.trim().equalsIgnoreCase("WZ")) {
				poOriginDescription = "Zahn";
			} else if (poOriginCode.trim().equalsIgnoreCase("TL")) {
				poOriginDescription = "Tooth Counter";
			} else if (poOriginCode.trim().equalsIgnoreCase("SK")) {
				poOriginDescription = "School Kit";
			} else {
				poOriginDescription = poOriginCode;
			}
		}
		return poOriginDescription;
	}

	/**
	 * Method getPoType.
	 * 
	 * @author Sriram.Krishnamurthy
	 * @param poTypeCode
	 * @return poTypeDescription Returns Purchase Order Type Description
	 */

	public static String getPoType(String poTypeCode) {
		String poTypeDescription = "";

		if (poTypeCode.trim().equalsIgnoreCase("DS")) {
			poTypeDescription = "Dropship";
		} else if (poTypeCode.trim().equalsIgnoreCase("RP")) {
			poTypeDescription = "Replenishment";
		} else if (poTypeCode.trim().equalsIgnoreCase("TR")) {
			poTypeDescription = "Transfer";
		} else if (poTypeCode.trim().equalsIgnoreCase("FB")) {
			poTypeDescription = "Forward Buy";
		} else if (poTypeCode.trim().equalsIgnoreCase("SD")) {
			poTypeDescription = "Ship Direct to Center";
		} else if (poTypeCode.trim().equalsIgnoreCase("VR")) {
			poTypeDescription = "Vendor Repair";
		} else if (poTypeCode.trim().equalsIgnoreCase("AS")) {
			poTypeDescription = "Special Purchase";
		} else if (poTypeCode.trim().equalsIgnoreCase("SQ")) {
			poTypeDescription = "Sequester";
		} else if (poTypeCode.trim().equalsIgnoreCase("ST")) {
			poTypeDescription = "Sequester Transfer";
		} else {
			poTypeDescription = poTypeCode;
		}
		return poTypeDescription;
	}

	/**
	 * Method getYYYYMMDDSeparated.
	 * 
	 * @author Sriram.Krishnamurthy
	 * @param YYYYMMDDD
	 * @param separator
	 * @return String Simple Util method to get a separated date from an String
	 *         date with YYYYMMDD format. Returns the String in YYYYMMDD format
	 * */
	public static String getYYYYMMDDSeparated(String YYYYMMDDD, char separator) {
		StringBuffer sepDate = new StringBuffer();
		if (YYYYMMDDD.length() == 6) {
			sepDate.append(YYYYMMDDD.substring(0, 2));
			sepDate.append(separator);
			sepDate.append(YYYYMMDDD.substring(2, 4));
			sepDate.append(separator);
			sepDate.append(YYYYMMDDD.substring(4, 6));
		} else if (YYYYMMDDD.length() == 8) {
			sepDate.append(YYYYMMDDD.substring(0, 4));
			sepDate.append(separator);
			sepDate.append(YYYYMMDDD.substring(4, 6));
			sepDate.append(separator);
			sepDate.append(YYYYMMDDD.substring(6, 8));
		} else {
			sepDate.append(YYYYMMDDD);
		}

		return sepDate.toString();

	}

	/**
	 * Method getMMDDYYYYSeparated.
	 * 
	 * @author Sriram.Krishnamurthy
	 * @param mdy
	 * @param separator
	 * @return String Simple Util method to get a separated date from MMDDYY
	 *         format to MMDDYYYY.
	 */
	public static String getMMDDYYYYSeparated(String mdy, char separator) {
		StringBuffer sepDate = new StringBuffer();

		if (mdy.length() == 5)
			mdy = "0" + mdy;

		if (mdy.length() == 6) {
			sepDate.append(mdy.substring(0, 2));
			sepDate.append(separator);
			sepDate.append(mdy.substring(2, 4));
			sepDate.append(separator);
			sepDate.append("20");
			sepDate.append(mdy.substring(4));
		} else
			sepDate.append(mdy);
		return sepDate.toString();
	}

	/**
	 * Method formatHours.
	 * 
	 * @author Sriram.Krishnamurthy
	 * @param hours
	 * @return formattedHours Simple Util method to format the hours.
	 */
	public static String formatHours(int hours) {
		StringBuffer formattedHour = new StringBuffer();
		String separator = " ";
		int numberOfDays = hours / 24;
		int numberOfHours = hours % 24;

		if (numberOfDays > 1) {
			formattedHour.append(Integer.toString(numberOfDays));
			formattedHour.append(separator);
			formattedHour.append("Days");
			formattedHour.append(separator);
		}

		if (numberOfDays == 1) {
			formattedHour.append(Integer.toString(numberOfDays));
			formattedHour.append(separator);
			formattedHour.append("Day");
			formattedHour.append(separator);
		}

		if (numberOfHours > 1) {
			formattedHour.append(Integer.toString(numberOfHours));
			formattedHour.append(separator);
			formattedHour.append("Hours");
		}

		if (numberOfHours == 1) {
			formattedHour.append(Integer.toString(numberOfHours));
			formattedHour.append(separator);
			formattedHour.append("Hour");
		}

		return formattedHour.toString();
	}

	/**
	 * Method formatHoursDDHH.
	 * 
	 * @author Sriram.Krishnamurthy
	 * @param hours
	 * @return formattedHours in DDHH Format Simple Util method to format the
	 *         hours.
	 */
	public static String formatHoursDDHH(int hours) {
		DecimalFormat df = new DecimalFormat("00");
		StringBuffer formattedHour = new StringBuffer();
		String separator = ":";
		int numberOfDays = hours / 24;
		int numberOfHours = hours % 24;

		formattedHour.append(Integer.toString(numberOfDays));
		formattedHour.append(separator);
		formattedHour.append(df.format(numberOfHours));
		return formattedHour.toString();
	}

	/**
	 * Method getYYYYMMDD.
	 * 
	 * @author Sriram.Krishnamurthy
	 * @param mdy
	 * @return String Simple Util method to convert date in MMDDYYYY format with
	 *         separator to YYYYMMDD format without separator.
	 */
	public static String getYYYYMMDD(String mdy) {
		StringBuffer sepDate = new StringBuffer();
		if (mdy.length() == 10) {
			sepDate.append(mdy.substring(6));
			sepDate.append(mdy.substring(0, 2));
			sepDate.append(mdy.substring(3, 5));
		} else
			sepDate.append(mdy);
		return sepDate.toString();
	}


	public static int parseInteger(String value) {
		try {
			return (Integer.parseInt(value));
		} catch (Exception e) {
			return 0;
		}
	}

	public static double parseDouble(String value) {
		try {
			return (Double.parseDouble(value));
		} catch (Exception e) {
			return 0.0;
		}
	}

	public static Timestamp getCurrentGMTTimeStamp() {

		return convertLocalToGMTTime(new java.sql.Timestamp(
				new java.util.Date().getTime()), "EST", true);

	}

	private static Timestamp convertLocalToGMTTime(Timestamp ts, String tzName,
			boolean useDaylightTime) {
		TimeZone tz = TimeZone.getTimeZone(tzName.trim());

		// LoggerUtil.log("debug", ".........."+tzName+","+tz.getDisplayName(true,
		// TimeZone.LONG) );

		GregorianCalendar calendar = new GregorianCalendar(tz);
		calendar.setTime((java.util.Date) ts);

		// LoggerUtil.log("debug", "_____________........"+tzName+","+tz.getDisplayName(true,
		// TimeZone.LONG) );

		// int daylightOffSet = (calendar.get(Calendar.ZONE_OFFSET) +
		// calendar.get(Calendar.DST_OFFSET))/(60*60*1000);
		int daylightOffSet = calendar.get(Calendar.ZONE_OFFSET);
		if (useDaylightTime)
			daylightOffSet += calendar.get(Calendar.DST_OFFSET);

		// LoggerUtil.log("debug", ".........input GMT time:" +
		// FORMAT.format(ts)+", GMT offset:"+
		// daylightOffSet+","+calendar.getTime().toString());

		calendar.add(Calendar.MILLISECOND, daylightOffSet);

		Timestamp newts = new Timestamp((calendar.getTime()).getTime());
		// LoggerUtil.log("debug", "..........new Local time:"+FORMAT.format(newts)+"\n");

		return newts;

	}

	public static int getModifiedDate(int days) {

		int modifiedDate = 0;
		Calendar cal = GregorianCalendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -days);
		Date daysAgo = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String newDate = sdf.format(daysAgo);
		if (newDate != null && !"".equals(newDate))
			modifiedDate = Integer.parseInt(newDate);

		return modifiedDate;
	}

	/**
	 * Validates if the date is in MM/DD/YYYY format
	 * 
	 * @param date
	 * @return
	 */
	public static boolean validateDateMMDDYYYY(String date) {
		Pattern dateFrmtPtrn =Pattern.compile("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)");
		Matcher mtch = dateFrmtPtrn.matcher(date.trim());  
		return mtch.matches();
	}
}
