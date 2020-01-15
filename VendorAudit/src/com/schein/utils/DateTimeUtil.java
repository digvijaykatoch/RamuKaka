/*
 * Created on Mar 24, 2006
 *
 * To change the template for this generated file go to Window&gt;Preferences&gt;Java&gt;Code
 * Generation&gt;Code and Comments
 */
package com.schein.utils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.TimeZone;

/**
 * @author dnair
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DateTimeUtil {

  public static java.text.SimpleDateFormat DEFAULT_FORMAT =
      new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");

  /**
   * Convert local time to GMT time
   * 
   * @param Timestamp ts: local timestamp
   * @param String : local timezone: PST
   * @return Timestamp: GMT time
   */
  public static Timestamp convertLocalToGMTTime(Timestamp ts, String tzName,
      boolean useDaylightTime) {
    TimeZone tz = TimeZone.getTimeZone(tzName.trim());
    GregorianCalendar calendar = new GregorianCalendar(tz);
    calendar.setTime((java.util.Date) ts);

    // indicating the raw offset from GMT in milliseconds
    int daylightOffSet = calendar.get(Calendar.ZONE_OFFSET);

    // indicating the daylight savings offset in milliseconds
    if (useDaylightTime)
      daylightOffSet += calendar.get(Calendar.DST_OFFSET);

    calendar.add(Calendar.MILLISECOND, (-1) * daylightOffSet);
    Timestamp newts = new Timestamp((calendar.getTime()).getTime());

    return newts;
  }

  /**
   * Converting GMT time to local time
   * 
   * @param Timestamp : GMT Timestamp from database
   * @param String : local Timezone Name: PST, CST
   * @return Timestamp: local timezone Timestamp
   */
  public static Timestamp convertGMTToLocalTime(Timestamp ts, String tzName,
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
   *         getCurrentTimeStamp
   * @return current time Stamp
   */
  public static Timestamp getCurrentTimeStamp() {

    return new java.sql.Timestamp(new java.util.Date().getTime());

  }

  /**
   * @author dnair<br>
   *         getCurrentGMTTimeStamp
   * @return current time Stamp
   */
  public static Timestamp getCurrentGMTTimeStamp() {

    return convertLocalToGMTTime(new java.sql.Timestamp(new java.util.Date().getTime()), "EST",
        true);

  }

  /**
   * @author dnair<br>
   *         getYMD
   * @param ts
   * @return YMD format for a given timestamp
   */
  public static String getYMD(Timestamp ts) {

    return DEFAULT_FORMAT.format(ts);

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
      sdf = new SimpleDateFormat("yyyy" + separator + "MM" + separator + "dd");
    else
      sdf = new SimpleDateFormat("yyyyMMdd");

    today = sdf.format(new Date());
    return today;
  }

  public static void main(String args[]) {
    java.sql.Timestamp ts = new java.sql.Timestamp(new java.util.Date().getTime());
    LoggerUtil.log("debug", "" + ts);

    LoggerUtil.log("debug", "Convert " + getYMD(ts));
    LoggerUtil.log("debug", "Server GMT  " + getCurrentGMTTimeStamp());
    java.sql.Timestamp gmtTs = convertLocalToGMTTime(ts, "EST", true);
    LoggerUtil.log("debug", "GMT IS -->>" + gmtTs + "\n\n");
    LoggerUtil.log("debug", "GMT Convert " + getYMD(convertLocalToGMTTime(ts, "EST", true)));

    LoggerUtil.log("debug", "Indian Time " + convertGMTToLocalTime(gmtTs, "IST", true));

    LoggerUtil.log("debug", "Spain time " + convertGMTToLocalTime(gmtTs, "ECT", true));
    LoggerUtil.log("debug", "Spain time " + convertGMTToLocalTime(gmtTs, "Europe/London", true));

    LoggerUtil.log("debug", "NY Time " + convertGMTToLocalTime(gmtTs, "EST", true));

    String ts1[] = TimeZone.getAvailableIDs();
    for (int i = 0; i < ts1.length; i++)
      LoggerUtil.log("debug", ts1[i]);

  }

  /**
   * @param fromDate
   * @param toDate
   * @return number of days between 2 dates excluding the weekends
   */
  public static int getNumberOfDays(Date fromDate, Date toDate) {

    return getNumberOfDays(fromDate, toDate, false);
  }

  /**
   * @param fromDate
   * @param toDate
   * @param includeWeekends is true will include the weekend days
   * @return number of days between 2 dates
   */
  public static int getNumberOfDays(Date fromDate, Date toDate, boolean includeWeekends) {
    int numberOfDays = 0;
    int dayOfWeek = 0;
    Calendar startDate = Calendar.getInstance();
    Calendar endDate = Calendar.getInstance();

    startDate.setTime(fromDate);
    endDate.setTime(toDate);

    endDate.add(Calendar.DATE, 1);

    while (endDate.after(startDate)) {
      dayOfWeek = startDate.get(Calendar.DAY_OF_WEEK);
      if (includeWeekends) {
        numberOfDays++;
      } else {
        if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY)
          numberOfDays++;
      }
      startDate.add(Calendar.DATE, 1);

    }

    return numberOfDays;
  }

  public static Date getDateFromStringMDY(String dateMDYYYY) {
    int year;
    int month;
    int day;

    dateMDYYYY = ScheinUtils.replaceAll(dateMDYYYY, "-", "");
    year = Integer.parseInt(dateMDYYYY.substring(4));
    month = Integer.parseInt(dateMDYYYY.substring(0, 2));
    month = month - 1;
    day = Integer.parseInt(dateMDYYYY.substring(2, 4));
    Calendar cal = new GregorianCalendar(year, month, day);

    return cal.getTime();
  }

  /**
   * @param dateVar
   * @param pattern
   * @return Validate if the input parameter is a valid date based on the pattern
   */
  public static boolean validateDate(String dateVar, String pattern) {
    SimpleDateFormat sdf = new SimpleDateFormat();
    try {
      sdf.applyPattern(pattern);
      Date dt = sdf.parse(dateVar);
      if (sdf.format(dt).equals(dateVar))
        return true;
      else
        return false;
    } catch (ParseException e) {
      return false;
    }

  }

  public static String getCYMD(String mdyDate) {
    if (mdyDate == null)
      return null;
    String year = "";
    String month = "";
    String date = "";
    StringTokenizer st = new StringTokenizer(mdyDate, "/");
    month = st.nextToken();
    date = st.nextToken();
    year = st.nextToken();
    String newPeriod = "";
    newPeriod = year + month + date;
    return newPeriod;

  }

  public static String getMDY(String ymdDate) {
    if (ymdDate == null) {
      return null;
    }
    String year = ymdDate.substring(0, 4);
    String month = ymdDate.substring(4, 6);
    String day = ymdDate.substring(6, 8);
    return month + "/" + day + "/" + year;
  }

  /**
   * Method getJulianDate.
   * 
   * @param dateMDYYYY
   * @return int
   * 
   *         Returns a julian date from a date with the format MM/DD/YYYY
   */
  public static int getJulianDate(String dateMDYYYY) {
    DecimalFormat format = new DecimalFormat("000");
    int julianDate = 0;
    String convDate = "";
    int julianDay = 0;
    int year;
    int month;
    int day;
    if (dateMDYYYY.indexOf("/") > 0) {
      dateMDYYYY = replaceAll(dateMDYYYY, "/", "");
    }
    try {
      year = Integer.parseInt(dateMDYYYY.substring(4));
      month = Integer.parseInt(dateMDYYYY.substring(0, 2));
      month = month - 1;
      day = Integer.parseInt(dateMDYYYY.substring(2, 4));
      Calendar cal = new GregorianCalendar(year, month, day);
      julianDay = cal.get(Calendar.DAY_OF_YEAR);
      if ((year >= 1941) && (year <= 1999)) {
        convDate += "0";
      } else {
        convDate += "1";
      }
      convDate += dateMDYYYY.substring(6) + format.format(julianDay);
      julianDate = Integer.parseInt(convDate);
    } catch (Exception e) {
      julianDate = 0;
    }
    return julianDate;
  }

  /**
   * Method replaceAll.
   * 
   * @param origString
   * @param s1
   * @param s2
   * @return String This method will replace every occurence of String s1 with s2 in String
   *         origString
   */
  public static String replaceAll(String origString, String s1, String s2) {
    origString = origString.trim();
    StringBuffer st1 = new StringBuffer();
    StringTokenizer st = new StringTokenizer(origString, s1);
    int tokenCount = st.countTokens();
    int i = 0;
    if (origString.indexOf(s1) == 0) {
      st1.append(s2);
    }
    while (i < (tokenCount - 1)) {
      st1.append(st.nextToken() + s2);
      i++;
    }
    if (tokenCount > 0) {
      st1.append(st.nextToken());
    }
    if (origString.endsWith(s1)) {
      st1.append(s2);
    }
    return (st1.toString());
  }

  public static String getYYYYMMDDDateWithoutSeparators(String theDate) {
    String newDate = "";
    if (theDate != null && !theDate.trim().equals("") && !theDate.trim().equals("0")) {
      String theDay = theDate.substring(3, 5);
      String theMonth = theDate.substring(0, 2);
      String theYear = theDate.substring(6, 10);
      newDate = theYear + theMonth + theDay;
    }
    return newDate;

  }

  public static String getFormattedTmsp(Timestamp timestamp) {

    if (timestamp == null)
      return "";
    else {
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
      return sdf.format(timestamp);
    }
  }

  /**
   * This method adds the given number of days to todays date
   * 
   * @param days
   * @return Todays Date + Number of days
   */
  public static int addDaysToTodaysDate(int days) {
    int modifiedDate = 0;
    Calendar cal = GregorianCalendar.getInstance();
    cal.add(Calendar.DAY_OF_YEAR, days);
    Date dateAfter = cal.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String newDate = sdf.format(dateAfter);
    if (newDate != null && !"".equals(newDate))
      modifiedDate = Integer.parseInt(newDate);

    return modifiedDate;
  }

  /**
   * This method adds days to the given date
   * 
   * @param date in YYYYMMDD
   * @param daysToAdd
   * @return the date after adding the days
   */

  public static int addDaysToDate(String date, int daysToAdd) {
    int modifiedDate = 0;
    Calendar cal = GregorianCalendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    try {
      cal.setTime(sdf.parse(date));
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    cal.add(Calendar.DAY_OF_YEAR, daysToAdd);
    Date dateAfter = cal.getTime();
    String newDate = sdf.format(dateAfter);
    if (newDate != null && !"".equals(newDate))
      modifiedDate = Integer.parseInt(newDate);

    return modifiedDate;
  }

  /**
   * 
   * @return Todays date in YYYYMMDD
   */
  public static int getTodaysDateInYYYYMMDD() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    return ScheinUtils.parseInteger(simpleDateFormat.format(new Date()));
  }

}
