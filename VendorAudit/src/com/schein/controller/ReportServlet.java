package com.schein.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.lang.reflect.*;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;

import com.schein.bean.LoginUser;
import com.schein.utils.BoUtils;
import com.schein.utils.CachingManager;
import com.schein.utils.LoggerUtil;
import com.schein.dao.UserTrackDAO;
import com.schein.utils.ServletRedirectingProps;
import com.schein.utils.TheXMLParser;

/**
 * @version 1.0
 * @author Dilip Nair
 * 
 *         This is the mainServlet which controls all the requests for the backorder system.
 *         Determines the request and pass it on to the right app controller class. The app
 *         controller class will handle the inputs and process the request.
 */
/*
 * 
 * @author dnair
 *
 * DNAIR2:04/25/2004 DN1 Changes made for the navigation between items DNAIR 12/12/11 New method
 * added to track user actvity
 */
public class ReportServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    TheXMLParser x1 = new TheXMLParser(config);
    HashMap h1 = x1.getHashMap();
    if (h1.isEmpty()) {
      System.out.print("did not get HashMap");
    } else {
      ServletContext context = config.getServletContext();
      context.setAttribute("JSPMaps", h1);

    }

  }


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
    String target_page = "";
    HttpSession session = req.getSession(true);

    String waitFlag = req.getParameter("waitFlag");

    String report = req.getParameter("report");

    /*
     * Validation added to handle null pointer exception for Direct Inventory Maintenance
     */

    if (!(report != null) || report.toString().contains("directInvItemReport")
        || report.toString().contains("directInvMaintenance")) {
      req.setAttribute("waitFlag", "N");
      waitFlag = "N";
    }

    LoggerUtil.log("debug", "report Parameter Value" + report.toString());

    char option = ' ';

    if ((report != null) && !(report.equals("")))
      option = report.charAt(0);
    else
      option = '1';
    String optionStr = option + " ";

    WorkContext ctx = new WorkContext();

    ctx.setRequest(req);
    ctx.setResponse(resp);
    ctx.setSession(session);

    /// test for case = 5
    ServletContext context = getServletContext();
    HashMap h = (HashMap) context.getAttribute("JSPMaps");



    if (session.getAttribute("loginuser") != null) {


      if ((waitFlag != null) && (waitFlag.trim().equals("Y"))) {
        setParametersForWaitPage(ctx);
        target_page = "admin/pleaseWait.jsp";
      } else {
        if (optionStr.equals(null)) {
          LoggerUtil.log("debug", "Report param is null");
          target_page = "error.jsp";
          LoggerUtil.log("debug", "forwarded to " + target_page);

        } else {
          // ServletRedirectingProps s = (ServletRedirectingProps)h.get(optionStr.trim());
          // //h.get(reportName)
          ServletRedirectingProps s = (ServletRedirectingProps) h.get(report.trim());
          if (s == null) {
            LoggerUtil.log("debug", "object ServletRedirectingProps is null link not correct");
            target_page = "error.jsp";
            getServletContext().getRequestDispatcher("/" + target_page).forward(req, resp);
            return;
          }
          String methodToExecute = s.getMethodName();
          String className = s.getClassName();
          String methodName = s.getMethodName();
          String jspName = s.getJspName();
          String reportName = s.getReportName();
          LoggerUtil.log("debug", "Class and Method Invoked : " + className + "." + methodName);
          LoggerUtil.log("debug", "Jsp And Jsp report name: " + jspName + " -" + reportName);

          trackUser(session, reportName);
          if (className == "" || methodName == "" || jspName == "" || reportName == "") {
            // LoggerUtil.log("debug", "class or method or jsp or report name is null");
            target_page = "error.jsp";
            getServletContext().getRequestDispatcher("/" + target_page).forward(req, resp);
          } else if (option == 'G') {
            // target_page = new VendorRunningFillRateAppCtrl().getVendorRunningFillRate(ctx);
            target_page = "error.jsp";
          } else {

            try {
              // Types of parameters being sent
              // cmnt st
              Class[] parameterClasses = {Class.forName("com.schein.controller.WorkContext")};
              // cmnt end
              // System.out.print("classname from xml is :"+s.getClassName());
              // System.out.print(", method = "+s.getMethodName());
              // System.out.print(", JspName = "+s.getJspName());
              // LoggerUtil.log("debug", ", ReportName = "+s.getReportName());
              // cmnt st
              Class c = Class.forName(className);
              // cmnt end
              // Method toExecuteMethod = getClass().getMethod( methodToExecute, parameterClasses );
              // Method[] ms = c.getDeclaredMethods();
              // for (int i=0; i<ms.length; i++)
              // LoggerUtil.log("debug", "method "+i+" is"+ms[i]);
              // cmnt st
              Method toExecuteMethod = c.getMethod(methodToExecute, parameterClasses);
              // cmnt end
              // LoggerUtil.log("debug", "method "+toExecuteMethod.toString());
              // Here the method is exceuted and what ever it returns, it is copied into a new
              // variable.
              // cmnt st
              Object lObject = c.newInstance();
              Object[] arguments = new Object[] {ctx};
              toExecuteMethod.invoke(lObject, arguments);
              // cmnt end
              // toExecuteMethod.invoke(lObject,new Object[0] );
            } catch (IllegalArgumentException e) {
              e.printStackTrace();
            } catch (ClassNotFoundException e) {
              LoggerUtil.log("error", e.toString());
            } catch (NoSuchMethodException e) {
              LoggerUtil.log("error", "Could not execute NoSuchMethodException thrown");
              e.printStackTrace();
            } catch (IllegalAccessException ie) {
              LoggerUtil.log("error", "Could not execute IllegalAccessException thrown");
              ie.printStackTrace();
            } catch (InvocationTargetException ie) {
              LoggerUtil.log("error", "Could not execute InvocationTargetException thrown");
              ie.printStackTrace();
            } catch (Exception e) {
              LoggerUtil.log("error", "Could not execute Exception thrown");
              e.printStackTrace();
            }

            target_page = jspName;
            // LoggerUtil.log("debug", "forwarded to "+target_page);
          }
        }

      }

    } else {
      target_page = "/";
    }
    getServletContext().getRequestDispatcher("/" + target_page).forward(req, resp);
  }


  private static void setParametersForWaitPage(WorkContext ctx) {
    HttpServletRequest req = ctx.getRequest();
    Enumeration enum1 = req.getParameterNames();
    String parmName = "";
    String parmValue = "";
    req.removeAttribute("report");
    while (enum1.hasMoreElements()) {
      parmName = (String) enum1.nextElement();
      parmValue = req.getParameter(parmName);

      if (!(parmName.equals("waitFlag")))
        req.setAttribute(parmName, parmValue);
    }
    req.setAttribute("testme", "test");
  }

  /*
   * This method tracks the user activity in IM Portal
   */
  private void trackUser(HttpSession session, String actionName) {
    if (CachingManager.getProperty("userTracking") == null
        || !CachingManager.getProperty("userTracking").equalsIgnoreCase(("Y")))
      return;
    LoginUser userBean = (LoginUser) session.getAttribute("loginuser");
    UserTrackDAO userTrackDAO = new UserTrackDAO();
    String lastUsedMenu = null;
    try {
      lastUsedMenu = (String) session.getAttribute("lastUsedMenu");
    } catch (Exception e) {
    	LoggerUtil.log("error", "Error in trackUser");
    }
    if (lastUsedMenu != null && !lastUsedMenu.equals(actionName))
      userTrackDAO.trackUser(userBean.getUserInfo().getUserId(), actionName);
    session.setAttribute("lastUsedMenu", actionName);
    userTrackDAO = null;
  }
}
