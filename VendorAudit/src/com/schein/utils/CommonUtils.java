package com.schein.utils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.log4j.Logger;

import com.schein.jar.TokenUtil;

/**
 * @author Sridhar.Muppa
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CommonUtils {

	/** The Constant BLANK. */
	public static final String BLANK = "";

	/** The Constant NULL. */
	public static final String NULL = "null";

	/** The Constant SPACE. */
	public static final String SPACE = " ";

	/** The Constant COMMA. */
	public static final String COMMA = ",";

	/**
	 * defaulst constructor.
	 */

	public CommonUtils() {
	}

	/**
	 * This logger method is used to logging debug comments. The logMsg is the
	 * actual logging message. The method parameter will decide whether it is start/end
	 * of method which is optional.
	 *
	 * @param logMsg of type String
	 * @param Logger logger instance
	 * @param logger the logger
	 * @param method of type String
	 */
	public static void debug(Logger logger, String logMsg, String method) {
		if (!logger.isDebugEnabled()) {
			return;
		}

		StringBuffer buffer = new StringBuffer();
		buffer.append("==>");
		buffer.append(logMsg);

		if (method != null) {
			buffer.append(method);
		}

		logger.debug(buffer.toString());
	}

	/**
		 * This method constructs a String object separated by comma for the given ArrayList.
		 *
		 * @param inputList the input list
		 * @param List of String values
		 *
		 * @return String of Comma separated values.
		 */
	public static String constructStringWithComma(List inputList) {
		if (isNull(inputList)) {
			return null;
		}

		StringBuffer buffer = new StringBuffer();
		String strCommmaValues;
		int inputListSize = inputList.size();

		for (int i = 0; i < inputListSize; i++) {
			strCommmaValues = (String) inputList.get(i);
			buffer.append(strCommmaValues);

			if (i != (inputListSize - 1)) {
				buffer.append(COMMA);
			}
		}

		return buffer.toString();
	}

	/**
	 * This method is used to check the given string is null or empty.
	 *
	 * @param inputStr type String
	 *
	 * @return true if the given string is null/empty. Else return false.
	 */
	public static boolean isNull(String inputString) {
		String inputStr;
		inputStr = inputString;
		if (inputStr == null) {
			return true;
		}

		inputStr = inputStr.trim();

		if (inputStr.length() == 0) {
			return true;
		}

		return false;
	}

	/**
	 * This method to used whether the input object is null.
	 *
	 * @param obj as type Object
	 *
	 * @return boolean
	 */
	public static boolean isNull(Object obj) {
		return (obj == null) ? true : false;
	}

	/**
	 * This method to used whether the input List is null or size is  empty
	 * Return true if List is empty or null.  Otherwise false.
	 *
	 * @param inputList the input list
	 * @param List as type ArrayList.
	 *
	 * @return boolean
	 */
	public static boolean isNull(List inputList) {
		return ((inputList == null) || (inputList.size() == 0)) ? true : false;
	}

	/**
	 * This method to used whether the input array object is null.
	 *
	 * @param obj as type Object
	 *
	 * @return boolean true if input array object is null or array length is zero otherwise returns false.
	 */
	public static boolean isNull(Object[] obj) {
		return ((obj == null) || (obj.length <= 0)) ? true : false;
	}

	/**
	 * Return a double value from a String double value.
	 * @param strDouble
	 * @return double
	 */
	public static double getDouble(String strDouble) {

		if (isNull(strDouble)) {
			return 0;
		}

		return (new Double(strDouble).doubleValue());
	}

	/**
	 * Return a double value from a Double object value.
	 * @param strDouble
	 * @return double
	 */
	public static double getDouble(Double objDouble) {

		if (isNull(objDouble)) {
			return 0;
		}

		return objDouble.doubleValue();
	}
	
	/**
		 * @param line
		 * @param delimiter
		 * @return
		 */
		public String[] getTokens(String line,String delimiter){
			TokenUtil tu = new TokenUtil();
			return tu.splitTokens(line,delimiter);
	 	
		}
	
		/**
		 * @param str
		 * @return
		 */
		public static boolean isFullString(String str){
			if (str != null && str.trim().length() != 0) return true;
			else 
				return false;
		}

		/**
		 * @param str
		 * @return
		 */
		public static boolean isNotFullString(String str){
			if (str == null) return true;
			return(str.trim().length() == 0);
		}

	
	
		/**
		 * @param data
		 * @return
		 */
		public static String checkNull(String data){
				if(data==null) return "";
				else return data.trim();
			}	
		
		/**
		 * @param data
		 * @return
		 */
		public static Timestamp checkNull(Timestamp data){
				if(data==null) return new Timestamp(0);
				else return data;
		
			}	
		
		/**
		 * @param origString
		 * @param searchStr
		 * @param replaceStr
		 * @return
		 */
		public static String replaceAll(String origString,String searchStr,String replaceStr){
			StringBuffer sb = new StringBuffer();
		
			int startIndex = 0;
			int index = origString.indexOf(searchStr,startIndex);
		
			while(index>=0){
				sb.append(origString.substring(startIndex,index));
				sb.append(replaceStr);
				startIndex= index+searchStr.length();
				index = origString.indexOf(searchStr,startIndex);
			}
			sb.append(origString.substring(startIndex));
		
			return sb.toString();			
		}
		
	/**
		 * @param value
		 * @return
		 * Format Decimal to 2 decimal places
		 */
		public static String formatDecimal_2(double value) {
			DecimalFormat format = new DecimalFormat("0.00");
			try {
				return format.format(value);
			} catch (Exception e) {
				return "";
			}
		}
		/**
			 * @param value
			 * @return
			 * Format Decimal to 1 decimal places
		*/
		public static String formatDecimal_1(double value) {
			DecimalFormat format = new DecimalFormat("0.0");
			try {
				return format.format(value);
			} catch (Exception e) {
				return "";
			}
		}

		/**
			 * @param value
			 * @return
			 * Format Decimal to n decimal places
		*/
		public static String formatDecimal(double value, int precision) {
			String formatString = "0.";
			if (precision > 0) {
				for (int i = 0; i < precision; i++)
					formatString += "0";
			} else
				formatString = "0";

			DecimalFormat format = new DecimalFormat(formatString);
			try {
				return format.format(value);
			} catch (Exception e) {
				return "";
			}
		}

		public static String formatDecimalWithCommas(double value) {

			DecimalFormat format = new DecimalFormat("###,###,###");
			try {
				return format.format(value);
			} catch (Exception e) {
				return "";
			}

		}
		
		public static boolean isEqual(String str1, String str2) {
			if(!isNull(str1) && !isNull(str2) && str1.trim().equalsIgnoreCase(str2.trim()))
					return true;
			return false;
		}
		
		public static String formatPhoneFax(String str1) {
			String formatStr="";
			int i=0;
			while(str1.length()>3)
			if(i==0){
				formatStr="-"+str1.substring(str1.length()-4);
				str1=str1.substring(0,str1.length()-4);		
				i++;
			}else{
				formatStr="-"+str1.substring(str1.length()-3)+formatStr;
				str1=str1.substring(0,str1.length()-3);
			}
			return str1+formatStr;
		}
}
