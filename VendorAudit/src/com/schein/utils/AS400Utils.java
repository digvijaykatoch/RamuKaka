package com.schein.utils;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import com.ibm.as400.access.*;
import com.schein.utils.CachingManager;

/**
 * @author Dilip Nair
 *
 *         This is a Utils class that contains all the functions that is related to the AS400 All
 *         the methods in this class is static
 */
public class AS400Utils {

  public static DataSource ds = null;

  /**
   * Method validateUser.
   * 
   * @param userName
   * @param password
   * @param con
   * @return String
   * 
   *         Validates the user
   */
  public static String validateUser(String userName, String password, Connection con) {
    String validation = "User not authorized";

    if (isAuthorizedBOUser(userName, con)) {
      return (validateAS400User(userName, password));
      // validation = "true";
    }

    return validation;
  }

  /**
   * Method validateAS400User.
   * 
   * @param userName
   * @param password
   * @return String This method accepts user name and password as parameter and verify it with the
   *         AS/400 user profile, Returns "true" as a String for valid user , Else the error/reason
   *         will be passed as the return value;
   */

  public static String validateAS400User(String userName, String password) {
    String validation = "false";
    LoggerUtil.log("debug", "Digvijay: Username: " + userName + " Password: " + password);
    // Trace.setTraceAllOn(true);
    AS400 sys = new AS400("A", "DNAIR2");

    try {
      if (sys.validateSignon(userName, password)) {
        validation = "true";
      } else
        validation = "false";
    } catch (Exception e) {
      String err = e.toString();
      validation = err.substring(err.indexOf(":") + 1);
      LoggerUtil.log("error", err);
      // e.printStackTrace();
    } finally {
      sys.disconnectAllServices();
    }


    return validation;
  }

  /**
   * Method isAuthorizedBOUser.
   * 
   * @param userName
   * @param con
   * @return boolean This method verifies if the user is authorized to use the backorder system ,
   * 
   */
  public static boolean isAuthorizedBOUser(String userName, Connection con) {
    boolean isValid = false;

    String qry = "SELECT USUSRI FROM F560212 WHERE UPPER(USUSRI) = '" + userName.toUpperCase()
        + "' AND US$ACT='Y'";
    Statement stmt = null;
    ResultSet rset = null;

    try {
      stmt = con.createStatement();
      rset = stmt.executeQuery(qry);
      if (rset.next())
        isValid = true;
      else
        isValid = false;

    } catch (Exception e) {
      LoggerUtil.log("error", "Error at isAuthorizedUSer in AS400Utils" + e.toString());
      isValid = false;
      // e.printStackTrace();
    } finally {
      try {
        rset.close();
        stmt.close();
      } catch (Exception ee) {
      }
    }

    return isValid;
  }

  /**
   * Method getAS400DBConnection.
   * 
   * @return Connection
   * @throws Exception This method returns a Datbase Connection from the connection pool
   */
  public static Connection getAS400DBConnection() throws Exception {
    Connection con = null;
    InitialContext ctx = new InitialContext();
    if (ds == null) {
      // TODO
      // ds = (DataSource)ctx.lookup("jdbc/AS400DS");// Digvijay - temp
      LoggerUtil.log("debug", "ds:" + CachingManager.getDataSourceName());
      ds = (DataSource) ctx.lookup(CachingManager.getDataSourceName());
      // ds = (DataSource) ctx.lookup("jdbc/ARCONADS");
    }

    con = ds.getConnection();

    return con;
  }

  public static Connection getAS400DBConnection_E() throws Exception {
    Connection con = null;
    InitialContext ctx = new InitialContext();
    DataSource ds1 = null;
    if (ds1 == null) {
      // ds = (DataSource)ctx.lookup("jdbc/AS400DS");
      ds1 = (DataSource) ctx.lookup(CachingManager.getDataSourceNameForE());

    }

    con = ds1.getConnection();

    return con;
  }

}
