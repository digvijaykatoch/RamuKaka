/*
 * Created on Nov 8, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.schein.utils;

import java.io.*;
import java.util.*;
import org.jdom.*;
import org.jdom.input.*;
import javax.servlet.ServletConfig;
/**
 * @author Santosh.Thakur
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TheXMLParser {
	public HashMap h = null;
	public TheXMLParser(ServletConfig config){
		String str1 =  readFile(config);
		h = loadAndCreate(str1);
	}
	public  HashMap getHashMap(){
		return h;
	}
   public  HashMap loadAndCreate(String str) {
		// Assume filename argument
			HashMap h = new HashMap();

		try {
			// Build the document with SAX and Xerces, no validation
			SAXBuilder builder = new SAXBuilder("org.apache.xerces.parsers.SAXParser",false);
			// Create the document

			StringReader sr = new StringReader(str);
			Document doc = builder.build(sr);

			// Get the root element
			Element root = doc.getRootElement();

			// Print servlet information
			List names = root.getChildren("NAME");
			Iterator i = names.iterator();
			
			while (i.hasNext()) {
			  Element sub = (Element) i.next();
			  ServletRedirectingProps s = new ServletRedirectingProps();
			  s.setReportName(sub.getChild("report-name").getText());
			  s.setClassName(sub.getChild("class-name").getText());
			  s.setJspName(sub.getChild("jsp-name").getText());
			  s.setMethodName(sub.getChild("method-name").getText());

				h.put(sub.getChild("report-name").getText(),s)	;		
				LoggerUtil.log("debug", sub.getChild("report-name").getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return h;
	}
	
	public  String readFile(ServletConfig config){
	String record = null;
	InputStream confIn = null;
	String fullString = "";
	 try { 
		// Create a new file output stream 
		confIn =
		  getClass().getResourceAsStream("prop.xml");
		BufferedReader b = new BufferedReader(new InputStreamReader(confIn));

		int recCount = 0; 

		record = new String();
		while ((record = b.readLine()) != null) {
			recCount++;
			fullString +=record+"\n";
		} 

		} catch (Exception e) 
		{ 
			System.err.println ("Error writing to file"); 
			e.printStackTrace();
		} 
		return fullString;
	 } 

}
