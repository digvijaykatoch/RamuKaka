/*
 * Created on Jul 21, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.schein.utils;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;



public class StringUtil {
	
	public static boolean isFullString(String str){
		if (str != null && str.trim().length() != 0) return true;
		else 
			return false;
	}
	
	public static String checkNull(String data){
			if(data==null) return "";
			else return data.trim();
		
		}	
		
	public static Timestamp checkNull(Timestamp data){
			if(data==null) return new Timestamp(0);
			else return data;
		
		}	
	public static String generateStringfromStringArray(String[] codes) {
	  /*generate the string for using with the IN Clause in the DAO
	  takes in the String Array of Numbers
	  output is '12345',4567','5678'
	  */
	  String generatedStr = "";
	  if(codes != null) {
		  for (int i = 0; i < codes.length; i++) {
	
			  generatedStr += "'" + codes[i] + "'";
			  if (i < (codes.length - 1)) {
				  generatedStr += ",";
			  }
		  }
	  }
	  return generatedStr;

  }		
  
  /**
   * Method replaceAll.
   * @param origString
   * @param s1
   * @param s2
   * @return String
   * This method will replace every occurence of String s1 with s2 in String origString
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
	  
  public static String addSpaceToString(String str,int count) {
		String generatedStr = str;
		if(str.length()<count){
			int len=count-str.length(); 
			for(int i=0;i<len;i++){
				generatedStr = generatedStr+" ";
			}
		}
		
		return generatedStr;
  }
  public static String addSpaceToStringLeft(String str,int count) {
		String generatedStr = str;
		if(str.length()<count){
			int len=count-str.length(); 
			for(int i=0;i<len;i++){
				generatedStr = " "+generatedStr;
			}
		}
		
		return generatedStr;
  }  
  
  public static String getRequestAttribute(HttpServletRequest request,String name){
		if(request.getAttribute(name)!=null)
			return((String)request.getAttribute(name));
		else
			return "";	
	}
	
	public static String getRequestParameter(HttpServletRequest request,String name){
		if(request.getParameter(name)!=null)
			return(((String)request.getParameter(name)).trim());
		else
			return "";	
	}
	
	public static String replaceQuotes(String origString){
			return StringUtil.replaceAll(origString,"\"","&quot;");
	}

	public static String getShortString(String reason){
		int	index = 0 ;
		if(reason!=null){
			if ((index=reason.indexOf(" "))>0)
				return (reason.charAt(0) + reason.substring(index+1,index+2)).toUpperCase();
			else
				return reason.substring(0,2).toUpperCase();
		}
		return "";
	}
	
	/**
	 * Method formatZeroToBlanks.
	 * @author Sriram.Krishnamurthy 
	 * @param data
	 * @return String
	 * Returns the blank string if input is zero else returns string value of input number. 
	 * */
	public static String formatZeroToBlanks(int data){
		if(data==0) return "";
		else return String.valueOf(data);	
	}	
	
	public static String getHtmlString(String str){
		StringBuffer sb = new StringBuffer();
		   int n = str.length();
		   for (int i = 0; i < n; i++) {
		      char c = str.charAt(i);
		      switch (c) {
		         case '<': sb.append("&lt;"); break;
		         case '>': sb.append("&gt;"); break;
		         case '&': sb.append("&amp;"); break;
		         case '"': sb.append("&quot;"); break;
		         default:  sb.append(c); break;
		      }
		   }
		   return sb.toString();
	}
	
}
