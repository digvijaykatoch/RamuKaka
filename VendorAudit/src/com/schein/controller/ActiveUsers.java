/*
 * Created on Jul 16, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.schein.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dnair
 *
 * This class is used to keep track of the active users in the session.
 * Every time a user logs in to the application , a new session is created.
 * The sessionId and name of the user is stored in the Map
 */
public class ActiveUsers {
	
	private final Map map = new HashMap();  
	
	public synchronized void put(String sessionId,  String userName) {   
		map.put(sessionId, userName);
	}   
      
	public synchronized void remove(String sessionId) { 
		map.remove(sessionId);
	}
       
	public synchronized Map getMap() { 
		return new HashMap(map);
	}

}
