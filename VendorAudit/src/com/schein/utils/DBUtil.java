/*
 * Created on Mar 23, 2006
 *
 * To change the template for this generated file go to Window&gt;Preferences&gt;Java&gt;Code
 * Generation&gt;Code and Comments
 */
package com.schein.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;


/**
 * @author dnair
 *
 *         This class contains utility methods on database objects
 */
public class DBUtil {

  public static DataSource ds = null;

  /**
   * getConnection This method returns a Datbase Connection from the connection pool
   * 
   * @return
   * @throws Exception returns Connection
   */
  public static Connection getConnection() throws Exception {
    Connection con = null;
    InitialContext ctx = new InitialContext();
    ds = (DataSource) ctx.lookup("jdbc/AS400DS");

    con = ds.getConnection();

    return con;
  }

  /**
   * @author dnair<br>
   *         close
   * @param rset Closes the ResultSet
   */
  public static void close(ResultSet rset) {
    try {
      if (rset != null)
        rset.close();
    } catch (SQLException ignore) {
    }
  }

  /**
   * @author dnair<br>
   *         close
   * @param pstmt Close the PreparedStatement
   */
  public static void close(Statement stmt) {
    try {
      if (stmt != null)
        stmt.close();
    } catch (SQLException ignore) {
    }
  }

  /**
   * @author dnair<br>
   *         close
   * @param pstmt Close the PreparedStatement
   */
  public static void close(PreparedStatement pstmt) {
    try {
      if (pstmt != null)
        pstmt.close();
    } catch (SQLException ignore) {
    }
  }

  /**
   * @author dnair<br>
   *         close
   * @param con Close the connection
   */
  public static void close(Connection con) {
    try {
      if (con != null)
        con.close();
    } catch (SQLException ignore) {
    }
  }


  /**
   * @author dnair<br>
   *         close
   * @param connection
   * @param stmt
   * @param results
   * 
   *        Close all resources
   */
  public static void close(Connection con, Statement stmt, ResultSet rset) {
    close(rset);
    close(stmt);
    close(con);
  }

  /**
   * Method getConnection.
   * 
   * @return Connection
   * @throws Exception This method returns a Datbase Connection from the connection pool
   */
  public static Connection getConnection(String dataSourceName) throws Exception {
    Connection con = null;
    InitialContext ctx = new InitialContext();
    ds = (DataSource) ctx.lookup("jdbc/" + dataSourceName);
    con = ds.getConnection();
    return con;
  }

}
