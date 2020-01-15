/*
 * Created on Jul 15, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.schein.controller;

import java.util.Map;

import com.schein.bean.*;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author dnair
 *
 * Class that implements the HttpSessionListener used to track the active users in the application
 */
public class SessionManager implements HttpSessionListener {
	
	private static int activeSessions = 0;
//	private static HashMap	activeUsers= new HashMap();
	private static final ActiveUsers activeUsers = new ActiveUsers();


	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent arg0) {
		activeSessions++;
		System.err.println("session created for "+arg0.getSession().getId()+"   "+new java.util.Date());

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent arg0) {
		if(activeSessions > 0) {
			activeSessions--;
		//remove the user from the Map	
	//	arg0.getSession().getAttribute("loginuser");
	//		clearFSCRecords(arg0.getSession());
			removeUser(arg0.getSession());
			//deleteFSCSummaryTable(arg0.getSession());
		}		
	}

	
	/**
	 * @author dnair<br>
	 * addUser
	 * @param session 
	 * 
	 * Adds the user to the Map with the session ID as the key
	 */
	public static void addUser(HttpSession session){
			
		if (session.getAttribute("loginuser")!=null) {
			LoginUser user = (LoginUser)session.getAttribute("loginuser");
			
			activeUsers.put(session.getId(),user.getUserInfo().getUserName());  
//			System.err.println("session created for "+session.getId()+" "+user.getUserInfo().getUserDspn()+"   "+new java.util.Date());
		}
		
	}
	
	/**
	 * @author dnair<br>
	 * removeUser
	 * @param session 
	 * 
	 * Removes an active sessionId from the Map.
	 */
	public static void removeUser(HttpSession session){
		
		String sessionId  = session.getId();
		
		activeUsers.remove(sessionId);
		
	}
	
	/**
	 * @author dnair<br>
	 * getAllUsers
	 * @return 
	 * 
	 * Returns all the active users logged in to the application
	 */
	public static Map getAllUsers(){
		return activeUsers.getMap();
	}
	
	/**
	 * @author dnair<br>
	 * getActiveSessionCount
	 * @return 
	 * 
	 * Returns the number of active sessions in the application
	 */
	public static int getActiveSessionCount(){
		return activeSessions;
	}

}
