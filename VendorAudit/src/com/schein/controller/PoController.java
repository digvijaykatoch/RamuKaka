package com.schein.controller;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.schein.bean.LoginUser;
import com.schein.bean.User;
import com.schein.dao.PoDao;
import com.schein.dao.UserManager;
import com.schein.utils.AS400Utils;
import com.schein.utils.LoggerUtil;
import com.schein.utils.WebUtils;


public class PoController {

  public void execute(WorkContext ctx) {

    HttpServletRequest httpServletRequest = ctx.getRequest();
    HttpSession httpSession = ctx.getSession();
    LoginUser loginUser = (LoginUser) httpSession.getAttribute("loginuser");
    User curUser = loginUser.getUserInfo();
    UserManager userManager = new UserManager();

    Connection connection = null;
    Map dcMap = null;
    Map labelMap = null;
    Map hierarchyMap = null;
    Map statusMap = null;
    Map poTypeMap = null;
    List buyerList = null;
    List teamList = null;

    Map dashboardMap = null;
    List poList = null;

    String ponumber = WebUtils.getRequestParameter(httpServletRequest, "ponumber");
    int ticketExists = 0;

    // Search Begins

    try {
      connection = AS400Utils.getAS400DBConnection();
      PoDao podao = new PoDao();
      poList = podao.getPODetails(connection, ponumber);
      ticketExists = podao.checkTickets(connection, ponumber);

    } catch (Exception e) {
    	LoggerUtil.log("error", "Error at PoController" + e.toString());	
    	e.printStackTrace();
    }

    // Search Ends

    httpServletRequest.setAttribute("ponumber", ponumber);
    httpServletRequest.setAttribute("poList", poList);
    httpServletRequest.setAttribute("ticketExists", ticketExists);

  }

}
