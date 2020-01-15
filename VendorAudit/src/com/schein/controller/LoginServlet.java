package com.schein.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.schein.controller.SessionManager;
import com.schein.bean.LoginUser;
import com.schein.controller.LoginAppCtrl;
import com.schein.dao.TicketDao;
import com.schein.dao.UserTrackDAO;
import com.schein.utils.BoUtils;
import com.schein.utils.LoggerUtil;

/**
 * @version 1.0
 * @author Dilip Nair
 */
/*
 * REVISION HISTORY **************** Date Version Phase Change Description Author
 * =================================================================================================
 * ============ 12/12/2011 FSC Release 2011 Added method call for user tracking dnair
 * 
 *******************************************************************************************************************
 */
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public void init() throws ServletException {}

  /**
   * @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doPost(req, resp);
    return;
  }

  /**
   * @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = null;
    WorkContext ctx = new WorkContext();

    ctx.setRequest(req);
    ctx.setResponse(resp);
    ctx.setSession(session);

    String userId = null;
    String password = null;
    boolean isValid = false;
    String target_page = "";

    UserTrackDAO userTrackDao = new UserTrackDAO();
    TicketDao ticketdao = new TicketDao();

    if ((req.getParameter("action") != null) && (req.getParameter("action").equals("logoff"))) {
      session = req.getSession(false);
      if (session != null) {
        // Delete the user when the user has logged off from the system
        userTrackDao.trackUser(
            ((LoginUser) session.getAttribute("loginuser")).getUserInfo().getUserId(), "logoff");
        SessionManager.removeUser(session);
        cleanSession(session);
      }
      target_page = "main.jsp";
    } else if ((session == null) || (session.getAttribute("loginuser") == null)) {
      session = req.getSession(true);
      ctx.setSession(session);
      userId = req.getParameter("userId");
      password = req.getParameter("password");

      isValid = new LoginAppCtrl().validateLogin(userId, password, ctx);
      if (isValid) {
        // add the user to the HashMap when a user has successfully logged in
        SessionManager.addUser(session);
        userTrackDao.trackUser(userId, "Login");
        List opentickets = new ArrayList();
        opentickets = ticketdao.getOpenTickets(userId);
        req.setAttribute("opentickets", opentickets);
        target_page = "main.jsp";
      } else {
        target_page = "index.jsp";
      }
    } else {
      session = req.getSession(false);
      target_page = "admin/index.jsp";
    }

    // getServletContext().getRequestDispatcher("/" + target_page).forward(req, resp);
    // getServletContext().getRequestDispatcher("/" + target_page).FORWARD_REQUEST_URI
    LoggerUtil.log("debug", "servlet context: " + getServletContext().getContextPath());
    // resp.sendRedirect(getServletContext().getContextPath() + "/" + target_page);
    getServletContext().getRequestDispatcher("/" + target_page).forward(req, resp);
    return;
  }

  /**
   * Method cleanSession.
   * 
   * @param session
   * 
   *        Clean up all the values stored in the session
   */
  private void cleanSession(HttpSession session) {
    // FSCSessionIdDao sessDao = new FSCSessionIdDao();
    // sessDao.insertSessionId(session.getAttribute("fscIMfillrate").toString());
    session.invalidate();
  }
}
