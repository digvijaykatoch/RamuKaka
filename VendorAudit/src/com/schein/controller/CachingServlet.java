package com.schein.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.schein.utils.*;
import java.net.InetAddress;
import java.util.Properties;

/**
 * @version 1.0
 * @author
 * 
 *         This servlet is initialized when the server is started Initializes the variables to be
 *         cached.
 * 
 *         Start the dataqueue listener which listens to the AS/400 dataqueue
 * 
 */
public class CachingServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  // private BoDataQueueReader dqr;

  /**
   * @see javax.servlet.GenericServlet#init()
   */
  public void init() throws ServletException {
    super.init();

    CacheIt();
    startQlistener();

  }

  /**
   * @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse) There are 3 options for an admin to use this
   *      servlet runtime. C-refesh the cached values. S-start the dataqueue listener T-will
   *      terminate the dataqueue listener
   */
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String message = "";
    String option = req.getParameter("option");
    char opt = ' ';
    if (option == null)
      option = "C";
    opt = option.charAt(0);

    switch (opt) {
      case 'C':
        CacheIt();
        message = "All the properties cached successfully";
        break;
      // case 'S':
      // if (dqr == null)
      // startQlistener();
      // break;
      // case 'T':
      // dqr.interrupt();
      // dqr = null;
      // break;
      // case 'R':
      // if (dqr != null) {
      // dqr.interrupt();
      // dqr = null;
      // }
      // Thread.sleep(30);
      // startQlistener();
      // message = "Data Queue listener is restarted";
      // break;
    }
    req.setAttribute("message", message);
    getServletContext().getRequestDispatcher("/console/" + "adminOutput.jsp").forward(req, resp);
  }

  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doGet(req, resp);
    return;
  }



  private void CacheIt() {
    LoggerUtil.log("debug", "Inside Caching Servlet");

    try {
      InputStream is =
          Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties");
      Properties props = new Properties();
      props.load(is);
      CachingManager.setCompany(props.getProperty("companyname"));
      CachingManager.setDataSourceName(props.getProperty("dataSourceName"));
      CachingManager.setDataSourceNameForE(props.getProperty("dataSourceNameForE"));
      LoggerUtil.log("debug", "E: " + props.getProperty("dataSourceNameForE").toString());
    } catch (Exception e) {
      e.printStackTrace();
      CachingManager.setCompany("HenrySchein");
      CachingManager.setDataSourceName("jdbc/AS400DS");
      CachingManager.setDataSourceNameForE("jdbc/AS400DS_E");
    }

    String serverIp = "";

    // CachingManager.setBoReasonList((LinkedList) BoUtils.getBoReasonCode());
    // CachingManager.setWhseList((LinkedList) BoUtils.getActiveWhse());
    // CachingManager.setFscWhseList((LinkedList) BoUtils.getFSCActiveWhse());
    // CachingManager.setBoSalesDiv((HashMap) BoUtils.getSalesDivCode());
    // CachingManager.setSysProperties(BoUtils.getAllProperties());
    // CachingManager.setBoDivisionList((LinkedList) BoUtils.getSalesDivList());


    try {
      serverIp = InetAddress.getLocalHost().getHostName() + " (Ip Address : "
          + InetAddress.getLocalHost().getHostAddress() + ")";
    } catch (Exception e) {
      serverIp = "Ip not found ";
    }

    CachingManager.setServerIpAddr(serverIp);

  }



  /**
   * @see javax.servlet.Servlet#destroy()
   */
  public void destroy() {
    super.destroy();
    if (CachingManager.getProperty("RUN.MODE").equals("P")
        || CachingManager.getServerIpAddr().equals(CachingManager.getProperty("listenerIP")))
      EmailSender.sendOperEmail(
          "Listener to the dataqueue was disconnected/Interrupted  from the caching servlet-destroy method ");
    LoggerUtil.log("debug", "Entered destroy method in Caching Servlet  ");
    // dqr.interrupt();
    // dqr = null;
  }


  private void startQlistener() {
    // dqr = new BoDataQueueReader("listener");
    // dqr.start();

  }

}
