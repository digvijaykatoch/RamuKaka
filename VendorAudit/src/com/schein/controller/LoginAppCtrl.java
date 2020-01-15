package com.schein.controller;

import java.sql.*;
import java.util.LinkedList;

import javax.servlet.http.*;

import com.schein.bean.LoginUser;
import com.schein.bean.User;
import com.schein.utils.CachingManager;
import com.schein.dao.UserManager;
import com.schein.utils.*;
/**
 * @author Dilip Nair
 *
 * This is an AppController class which handles the login process., logon.jsp
 */
public class LoginAppCtrl {

/**
 * Method validateLogin.
 * @param userId
 * @param password
 * @param ctx
 * @return boolean
 * 
 * This method validates a user trying to log in, 
 * 
 * returns true if logged in right
 */
public boolean validateLogin(String userId , String password, WorkContext ctx) {
	
		HttpServletRequest request = ctx.getRequest();
	
		boolean	isValid = false;
		String 		message = "";	
		Connection con = null;
		if ((userId==null) || (password==null)) {
			isValid = false;
			message = "UserId/Password cannot be blank";
		}
		else {
					userId=	userId.toUpperCase();
				try {
					con = AS400Utils.getAS400DBConnection();
					if (((String)CachingManager.getSysProperties().get("RUN.MODE")).trim().equals("P"))
						message = AS400Utils.validateUser(userId, password,con);
					else
						message = "true";	
					if (message.equalsIgnoreCase("true")) {
						setUserPreference(userId,ctx,con);
						isValid = true;
					}
				}catch (Exception e) {
					LoggerUtil.log("error", "Error at ValidateLogin" + e.toString());
					message = "Error verifying user  ,Contact Administrator";
					e.printStackTrace();
				}finally {
					request.setAttribute("message" ,message);
					try {
						con.close();
					}catch (Exception ee) {}
				}
			}
			
	
		return isValid;
	}
	
	
	
	/**
	 * Method setUserPreference.
	 * @param userId
	 * @param ctx
	 * @param con
	 * 
	 * This method sets the logged in user information to the Session.
	 * The values stored in the session are used for later validation and authorities.
	 */
	private void setUserPreference(String userId,WorkContext ctx,Connection con){
		HttpServletRequest request	= ctx.getRequest();
		HttpSession session			= ctx.getSession();
		
		User userInfo = null;
		LinkedList autBuyers = null;
		LinkedList mainMenuList = null;
		LinkedList subMenuList = null;
		String 	autBuyerString  = null;
		UserManager uManager = new UserManager();
		LoginUser	curUser  = new LoginUser();
	
		userInfo = uManager.getUser(userId,con); 
		autBuyers = uManager.getAutBuyers(userId,con);	
		autBuyerString = uManager.getGroupBuyerString(userInfo, con);
		mainMenuList = uManager.getMainMenuList(userId,con);
		subMenuList = uManager.getSubMenuList(userId,con);
		
		if(userInfo.getRole().trim().equals("BUYER"))
			request.setAttribute("defaultPage" ,"Report?report=fillRateDetails&typeOfRecords=regular&buyerNo="+userInfo.getBuyerId());
		
		curUser.setUserInfo(userInfo);
		curUser.setAutBuyerList(autBuyers);	
		curUser.setAutBuyerString(autBuyerString);
		curUser.setNavigatorTable(new java.util.HashMap());		//after login intialize this HashTable 
		curUser.setUserMainMenuList(mainMenuList);
		curUser.setUserSubMenuList(subMenuList);
		session.setAttribute("loginuser",curUser);	
	}
}
