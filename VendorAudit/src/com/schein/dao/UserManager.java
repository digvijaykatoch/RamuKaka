/*******************************************************************************************************************
 * File : UserManager.java
 * 
 * Description : Methods to set the USER object.
 * 
 * REVISION HISTORY **************** Date Version Phase Change Description Author
 * =============================================================================================================
 * 11/21/2011 1.0 FSC Release 2011 Item Details Screen - Recreated for FSC Sriram Krishnamurthy
 * instead of linking to BO Item Details. Added attribute isIMUser.
 *******************************************************************************************************************
 */

package com.schein.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


import com.schein.bean.BuyerInfo;
import com.schein.bean.User;
import com.schein.bean.UserMainMenu;
import com.schein.bean.UserSubMenu;
import com.schein.utils.BoUtils;
import com.schein.utils.DBUtil;
import com.schein.utils.LoggerUtil;
import com.schein.utils.ScheinUtils;
import com.schein.utils.StringUtil;

/**
 * @author Dilip Nair
 *
 *         This class has all the methods to insert , update and delete records for the user class
 * 
 */
public class UserManager {

  /**
   * Method getUser.
   * 
   * @param userId
   * @param con
   * @return User This method is return the details of a user that is authorized to use the system
   */
  public User getUser(String userId, Connection con) {
    User userInfo = null;
    Statement stmt = null;
    ResultSet rset = null;
    String qryString = "SELECT A.*, B.RLUSRT FROM F560212 A, F560218 B  WHERE UPPER(A.USUSRI) = '"
        + userId.toUpperCase() + "' AND A.USNPOS=B.RLNPOS";

    try {
      stmt = con.createStatement();
      rset = stmt.executeQuery(qryString);
      if (rset.next()) {
        userInfo = new User();
        userInfo.setUserId(rset.getString(1));
        userInfo.setUserName(rset.getString(2));
        userInfo.setUserDspn(rset.getString(3));
        userInfo.setRole(rset.getString(4));
        userInfo.setBuyerId(rset.getInt(5));
        userInfo.setParentId(rset.getString(6));
        userInfo.setTeam(rset.getString(7));
        userInfo.setDivision(rset.getString(8));
        userInfo.setEmail(rset.getString(9));
        userInfo.setActive(rset.getString(10));
        userInfo.setUserLevel(rset.getInt(11));
        userInfo.setMgrLevel(rset.getInt(12));
        userInfo.setAdmUser(rset.getString(13));
        if (rset.getString(19).trim().equalsIgnoreCase("Y"))
          userInfo.setIMUser(true);
        else
          userInfo.setIMUser(false);
      }

    } catch (Exception e) {
      LoggerUtil.log("error", "Error at getUser method " + e.toString());
    } finally {
      try {
        rset.close();
        stmt.close();
      } catch (Exception ee) {
      }
    }

    return userInfo;
  }

  /**
   * Method getUser.
   * 
   * @param buyerNo
   * @param con
   * @return User
   * 
   *         Returns all the information for one buyer,for the buyerNo passes as parameter
   * 
   */
  public User getUser(int buyerNo, Connection con) {
    User userInfo = null;
    Statement stmt = null;
    ResultSet rset = null;
    String qryString = "SELECT * FROM F560212 WHERE USBUYR = " + buyerNo + " ";

    try {
      stmt = con.createStatement();
      rset = stmt.executeQuery(qryString);
      if (rset.next()) {
        userInfo = new User();
        userInfo.setUserId(rset.getString(1));
        userInfo.setUserName(rset.getString(2));
        userInfo.setUserDspn(rset.getString(3));
        userInfo.setRole(rset.getString(4));
        userInfo.setBuyerId(rset.getInt(5));
        userInfo.setParentId(rset.getString(6));
        userInfo.setTeam(rset.getString(7));
        userInfo.setDivision(rset.getString(8));
        userInfo.setEmail(rset.getString(9));
        userInfo.setActive(rset.getString(10));
        userInfo.setUserLevel(rset.getInt(11));
        userInfo.setMgrLevel(rset.getInt(12));
        userInfo.setAdmUser(rset.getString(13));

      }

    } catch (Exception e) {
      LoggerUtil.log("error", "Error at getUser method " + e.toString());
    } finally {
      try {
        rset.close();
        stmt.close();
      } catch (Exception ee) {
      }
    }

    return userInfo;
  }



  /**
   * Method getAutBuyers.
   * 
   * @param userId
   * @param con
   * @return LinkedList This method returns a LinkedList that contains all the userId's of the
   *         user's that comes under the userId as per the IM hierarchy
   */
  public LinkedList getAutBuyers(String userId, Connection con) {
    LinkedList autBuyers = null;
    Statement stmt = null;
    ResultSet rset = null;
    String qryString = "SELECT USBUYR FROM F560212 WHERE USLVL >=(SELECT USLVL FROM "
        + " F560212 WHERE USUSRI = '" + userId + "')";

    LoggerUtil.log("debug", "getAutBuyers in UserManager " + qryString);

    try {
      stmt = con.createStatement();
      rset = stmt.executeQuery(qryString);
      autBuyers = new LinkedList();
      while (rset.next()) {
        autBuyers.add(rset.getString(1));
      }
    } catch (Exception e) {
      LoggerUtil.log("error", "Error at getAutBuyers method in UserManager " + e.toString());
    } finally {
      try {
        rset.close();
        stmt.close();
      } catch (Exception ee) {
      }
    }


    return autBuyers;
  }


  /**
   * Method getAutBuyers.
   * 
   * @param userId
   * @param con
   * @return LinkedList This method returns a list of user's as a String embraced with brackets to
   *         use in a SQL
   * 
   */
  public String getAutBuyersString(User loginUser, Connection con) {
    StringBuffer autUsrString = null;
    LinkedList autBuyers = null;
    Statement stmt = null;
    ResultSet rset = null;
    // String qryString = "SELECT DISTINCT BUYERID FROM USERTBL";

    String qryString = "SELECT DISTINCT(EMP.USBUYR) FROM F560212 EMP , F560212 MGR "
        + "WHERE (MGR.USUSR0 = '" + loginUser.getUserId() + "' AND (EMP.USUSR0 = MGR.USUSRI OR "
        + "EMP.USUSRI = MGR.USUSRI) OR EMP.USUSRI = '" + loginUser.getUserId() + "')";



    /*
     * String qryString = "SELECT DISTINCT(EMP.USBUYR) FROM F560212 EMP \n"+
     * "WHERE USGPN IN (SELECT USGPN FROM F560212 WHERE \n"+ "USUSRI='"+loginUser.getUserId()+"')";
     */
    if (loginUser.getUserLevel() <= 1) { // changed from 1 to 2 to change hierarchy
      qryString = "SELECT DISTINCT USBUYR FROM F560212";
    }

    LoggerUtil.log("debug", "Authorized Buyer SQL " + qryString);

    try {
      stmt = con.createStatement();
      rset = stmt.executeQuery(qryString);
      autBuyers = new LinkedList();
      while (rset.next()) {
        autBuyers.add(rset.getString(1));
      }
      autUsrString = new StringBuffer(autBuyers.toString());
      autUsrString.replace(0, 1, "(");
      autUsrString.replace((autUsrString.length() - 1), autUsrString.length(), ")");
    } catch (Exception e) {
      LoggerUtil.log("error", "Error at method getAutBuyersString in UserManager " + e.toString());
    } finally {
      try {
        rset.close();
        stmt.close();
      } catch (Exception ee) {
      }
    }


    return autUsrString.toString();
  }



  /**
   * Method getAutBuyers.
   * 
   * @param userId
   * @param con
   * @return LinkedList This method returns a list of user's as a String embraced with brackets to
   *         use in a SQL The list provides all the buyer numbers listed under a group to give group
   *         level access to all the buyers
   * 
   */
  public String getGroupBuyerString(User loginUser, Connection con) {
    StringBuffer autUsrString = null;
    LinkedList autBuyers = null;
    Statement stmt = null;
    ResultSet rset = null;


    /*
     * String qryString = "SELECT DISTINCT(EMP.USBUYR) FROM F560212 EMP , F560212 MGR " +
     * "WHERE (MGR.USUSR0 = '"+loginUser.getUserId()+"' AND (EMP.USUSR0 = MGR.USUSRI OR "+
     * "EMP.USUSRI = MGR.USUSRI) OR EMP.USUSRI = '"+loginUser.getUserId()+"')";
     */


    String qryString = "SELECT DISTINCT(EMP.USBUYR) FROM F560212 EMP \n"
        + "WHERE USGPN IN (SELECT USGPN FROM F560212 WHERE \n" + "USUSRI='" + loginUser.getUserId()
        + "')";

    /* Modified according to section 4.1 IM portal 2017 BRD */
    // if (loginUser.getUserLevel() <= 2) { //changed from 1 to 2 to change hierarchy
    qryString = "SELECT DISTINCT USBUYR FROM F560212 WHERE US$ACT='Y'";
    // }


    LoggerUtil.log("debug", "Authorized Buyer SQL " + qryString);

    try {
      stmt = con.createStatement();
      rset = stmt.executeQuery(qryString);
      autBuyers = new LinkedList();
      while (rset.next()) {
        autBuyers.add(rset.getString(1));
      }
      autUsrString = new StringBuffer(autBuyers.toString());
      autUsrString.replace(0, 1, "(");
      autUsrString.replace((autUsrString.length() - 1), autUsrString.length(), ")");
    } catch (Exception e) {
      LoggerUtil.log("error", "Error at method getAutBuyersString in UserManager " + e.toString());
    } finally {
      try {
        rset.close();
        stmt.close();
      } catch (Exception ee) {
      }
    }


    return autUsrString.toString();
  }



  /**
   * Method getUserHierarchy.
   * 
   * @param userList
   * @param con
   * @return HashMap This routine returns the users in the hierarchy list of the users of BO system
   */
  public HashMap getUserHierarchy(String userList, Connection con) {
    Statement stmt = null;
    ResultSet rset = null;
    HashMap hList = new HashMap();
    LinkedList teamList = new LinkedList();
    LinkedList groupList = new LinkedList();
    LinkedList buyerList = new LinkedList();

    User usr = new User();

    /* Modified according to section 4.1 IM portal 2017 BRD */

    // if (userList.equalsIgnoreCase("*ALL"))
    userList = " ( SELECT DISTINCT USBUYR FROM F560212)";

    String query = "SELECT USCEGN,USBUYR,USNPOS,USGPN,USMUSE,USLVL FROM F560212 WHERE USBUYR IN"
        + userList + " AND US$ACT='Y' ORDER BY USCEGN,USGPN,USMUSE,USLVL,USBUYR";

    // WHERE "
    // + "USBUYR IN "+userList + " AND US$ACT='Y' ORDER BY USCEGN,USGPN,USMUSE,USLVL,USBUYR";

    LoggerUtil.log("debug", "User hierarchy query " + query);

    try {

      stmt = con.createStatement();
      rset = stmt.executeQuery(query);
      while (rset.next()) {
        usr.setUserDspn(StringUtil.checkNull(rset.getString(1)));
        usr.setBuyerId(rset.getInt(2));
        usr.setRole(rset.getString(3));

        if ((usr.getRole().trim().equals("BUYER")) || (usr.getRole().trim().equals("SBUYER")))
          buyerList.add(usr);

        if (usr.getRole().trim().equals("SBUYER"))
          teamList.add(usr);

        if (usr.getRole().trim().equals("MANAGER"))
          groupList.add(usr);

        usr = new User();

      }
      hList.put("buyer", buyerList);
      hList.put("team", teamList);
      hList.put("group", groupList);
    } catch (Exception e) {
      LoggerUtil.log("error", "Error at method getUserHierarchy in UserManager " + e.toString());
    } finally {
      try {
        rset.close();
        stmt.close();
      } catch (Exception ee) {
      }
    }
    return hList;
  }


  public ArrayList getAllUserList(Connection con) {

    Statement stmt = null;
    ResultSet rset = null;
    ArrayList aUserList = new ArrayList();
    String qryString = "SELECT  USUSRI,USNAME,USCEGN, USNPOS , USBUYR , USUSR0 , USMUSE , "
        + "USGPN,US$EMA ,US$ACT ,USLVL,USHLVL  , US$FG1 ,USUSER , USPID ,USJOBN  , USUPMJ  , USUPMT "
        + "FROM  F560212 ORDER BY  USUSRI";
    User userInfo = new User();
    try {

      stmt = con.createStatement();
      rset = stmt.executeQuery(qryString);
      while (rset.next()) {
        userInfo.setUserId(rset.getString(1));
        userInfo.setUserName(rset.getString(2));
        userInfo.setUserDspn(rset.getString(3));
        userInfo.setRole(rset.getString(4));
        userInfo.setBuyerId(rset.getInt(5));
        userInfo.setParentId(rset.getString(6));
        userInfo.setTeam(rset.getString(7));
        userInfo.setDivision(rset.getString(8));
        userInfo.setEmail(rset.getString(9));
        userInfo.setActive(rset.getString(10));
        userInfo.setUserLevel(rset.getInt(11));
        userInfo.setMgrLevel(rset.getInt(12));
        userInfo.setAdmUser(rset.getString(13));
        aUserList.add(userInfo);
        userInfo = new User();
      }

    } catch (Exception e) {
      LoggerUtil.log("error", "Error at method getAllUserList in UserManager " + e.toString());
    } finally {
      try {
        rset.close();
        stmt.close();
      } catch (Exception ee) {
      }
    }
    return aUserList;
  }

  public BuyerInfo getUpdateUserInfo(String userId, Connection con) {
    BuyerInfo buyerInfo = null;
    Statement stmt = null;
    ResultSet rset = null;
    String qryString =
        "SELECT  USUSRI,USNAME,USCEGN, USNPOS , USBUYR , USUSR0 , USMUSE ,USGPN,US$EMA ,"
            + "US$ACT ,USLVL,USHLVL  , US$FG1 ,USUSER , USPID ,USJOBN ,USUPMJ  , USUPMT,glseq# "
            + "FROM F560212 f1  left outer join F560213 f2 on f1.usbuyr =f2.glbuyr WHERE f1.USUSRI = '"
            + userId + "'";

    try {
      stmt = con.createStatement();
      rset = stmt.executeQuery(qryString);
      if (rset.next()) {
        buyerInfo = new BuyerInfo();
        buyerInfo.setUserId(rset.getString(1));
        buyerInfo.setUserName(rset.getString(2));
        buyerInfo.setUserDspn(rset.getString(3));
        buyerInfo.setRole(rset.getString(4));
        buyerInfo.setBuyerId(rset.getInt(5));
        buyerInfo.setParentId(rset.getString(6));
        buyerInfo.setTeam(rset.getString(7));
        buyerInfo.setDivision(rset.getString(8));
        buyerInfo.setEmail(rset.getString(9));
        buyerInfo.setActive(rset.getString(10));
        buyerInfo.setUserLevel(rset.getInt(11));
        buyerInfo.setMgrLevel(rset.getInt(12));
        buyerInfo.setAdmUser(rset.getString(13));
        String strGoal = rset.getString(19);
        if (strGoal == null || strGoal.trim().equals("null"))
          strGoal = "";
        else
          strGoal = strGoal.trim();
        buyerInfo.setGoals(strGoal);
      }

    } catch (Exception e) {
      LoggerUtil.log("error", "Error at getUser method " + e.toString());
    } finally {
      try {
        rset.close();
        stmt.close();
      } catch (Exception ee) {
      }
    }

    return buyerInfo;
  }

  public int updateUserInfo(BuyerInfo buyerInfo, String userid, Connection con) {
    PreparedStatement pstmtUpdate = null;
    PreparedStatement pstmtSelBuyer = null;
    PreparedStatement pstmtupdBuyer = null;
    PreparedStatement pstmtInsBuyer = null;
    ResultSet rs = null;

    String updString = "UPDATE F560212 SET USUSRI=?,USNAME=?,USCEGN=?, USNPOS=?,USBUYR=?,"
        + "USUSR0=?,USMUSE=?,USGPN=?,US$EMA=?,US$ACT=?,USLVL=?,USHLVL=?,"
        + "US$FG1=?,USUSER=?,USUPMJ=?,USUPMT=? WHERE TRIM(USUSRI)=?";

    String selQuery = "SELECT * FROM F560213 WHERE GLBUYR =?";

    String updQuery = "UPDATE F560213 SET GLSEQ# =? WHERE GLBUYR =?";

    String insQuery = "INSERT INTO F560213(GLBUYR,GLSEQ#) VALUES(?,?)";
    int intResult = 0;
    try {
      pstmtUpdate = con.prepareStatement(updString);
      pstmtUpdate.setString(1, buyerInfo.getUserId().trim());
      pstmtUpdate.setString(2, buyerInfo.getUserName().trim());
      pstmtUpdate.setString(3, buyerInfo.getUserDspn().trim());
      pstmtUpdate.setString(4, buyerInfo.getRole().trim());
      pstmtUpdate.setInt(5, buyerInfo.getBuyerId());
      pstmtUpdate.setString(6, buyerInfo.getParentId().trim());
      pstmtUpdate.setString(7, buyerInfo.getTeam().trim());
      pstmtUpdate.setString(8, buyerInfo.getDivision().trim());
      pstmtUpdate.setString(9, buyerInfo.getEmail().trim());
      pstmtUpdate.setString(10, buyerInfo.getActive().trim());
      pstmtUpdate.setInt(11, buyerInfo.getUserLevel());
      pstmtUpdate.setInt(12, buyerInfo.getMgrLevel());
      pstmtUpdate.setString(13, buyerInfo.getAdmUser().trim());
      pstmtUpdate.setString(14, "DNAIR2");
      pstmtUpdate.setInt(15, ScheinUtils.getToday_Julian());
      pstmtUpdate.setInt(16, ScheinUtils.getCurrentTime());
      pstmtUpdate.setString(17, userid.trim());

      intResult = pstmtUpdate.executeUpdate();
      if (intResult > 0) {
        if ("BUYER".equalsIgnoreCase(buyerInfo.getRole().trim())
            || "SBUYER".equalsIgnoreCase(buyerInfo.getRole().trim())) {
          pstmtSelBuyer = con.prepareStatement(selQuery);
          pstmtSelBuyer.setInt(1, buyerInfo.getBuyerId());
          rs = pstmtSelBuyer.executeQuery();
          if (rs.next()) {
            pstmtupdBuyer = con.prepareStatement(updQuery);
            pstmtupdBuyer.setString(1, buyerInfo.getGoals());
            pstmtupdBuyer.setInt(2, buyerInfo.getBuyerId());
            intResult = pstmtupdBuyer.executeUpdate();
          } else {
            pstmtInsBuyer = con.prepareStatement(insQuery);
            pstmtInsBuyer.setInt(1, buyerInfo.getBuyerId());
            pstmtInsBuyer.setString(2, buyerInfo.getGoals());
            intResult = pstmtInsBuyer.executeUpdate();
          }

        }
      }

    } catch (Exception e) {
      LoggerUtil.log("error", "Error at getUser method " + e.toString());
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (pstmtUpdate != null)
          pstmtUpdate.close();
        if (pstmtSelBuyer != null)
          pstmtSelBuyer.close();
        if (pstmtupdBuyer != null)
          pstmtupdBuyer.close();
        if (pstmtInsBuyer != null)
          pstmtInsBuyer.close();
      } catch (Exception ee) {
      }
    }
    return intResult;
  }

  public int createUserInfo(BuyerInfo buyerInfo, Connection con) {
    PreparedStatement pstmtUpdate = null;
    PreparedStatement pstmtSelBuyer = null;
    PreparedStatement pstmtupdBuyer = null;
    PreparedStatement pstmtInsBuyer = null;
    Statement stmt = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    String updString =
        "INSERT INTO F560212(USUSRI,USNAME,USCEGN, USNPOS,USBUYR,USUSR0,USMUSE,USGPN,US$EMA,"
            + "US$ACT,USLVL,USHLVL,US$FG1,USUSER,USUPMJ,USUPMT) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    String strSelQuery =
        "SELECT * FROM F560212 WHERE trim(USUSRI) = '" + buyerInfo.getUserId().trim() + "'";
    String selQuery = "SELECT * FROM F560213 WHERE GLBUYR =?";

    String updQuery = "UPDATE F560213 SET GLSEQ# =? WHERE GLBUYR =?";

    String insQuery = "INSERT INTO F560213(GLBUYR,GLSEQ#) VALUES(?,?)";
    int intResult = 0;
    try {
      stmt = con.createStatement();
      rs1 = stmt.executeQuery(strSelQuery);
      if (!rs1.next()) {
        pstmtUpdate = con.prepareStatement(updString);
        pstmtUpdate.setString(1, buyerInfo.getUserId().trim());
        pstmtUpdate.setString(2, buyerInfo.getUserName().trim());
        pstmtUpdate.setString(3, buyerInfo.getUserDspn().trim());
        pstmtUpdate.setString(4, buyerInfo.getRole().trim());
        pstmtUpdate.setInt(5, buyerInfo.getBuyerId());
        pstmtUpdate.setString(6, buyerInfo.getParentId().trim());
        pstmtUpdate.setString(7, buyerInfo.getTeam().trim());
        pstmtUpdate.setString(8, buyerInfo.getDivision().trim());
        pstmtUpdate.setString(9, buyerInfo.getEmail().trim());
        pstmtUpdate.setString(10, buyerInfo.getActive().trim());
        pstmtUpdate.setInt(11, buyerInfo.getUserLevel());
        pstmtUpdate.setInt(12, buyerInfo.getMgrLevel());
        pstmtUpdate.setString(13, buyerInfo.getAdmUser().trim());
        pstmtUpdate.setString(14, "DNAIR2");
        pstmtUpdate.setInt(15, ScheinUtils.getToday_Julian());
        pstmtUpdate.setInt(16, ScheinUtils.getCurrentTime());
        intResult = pstmtUpdate.executeUpdate();
        if (intResult > 0) {
          if ("BUYER".equalsIgnoreCase(buyerInfo.getRole().trim())
              || "SBUYER".equalsIgnoreCase(buyerInfo.getRole().trim())) {
            pstmtSelBuyer = con.prepareStatement(selQuery);
            pstmtSelBuyer.setInt(1, buyerInfo.getBuyerId());
            rs = pstmtSelBuyer.executeQuery();
            if (rs.next()) {
              pstmtupdBuyer = con.prepareStatement(updQuery);
              pstmtupdBuyer.setString(1, buyerInfo.getGoals());
              pstmtupdBuyer.setInt(2, buyerInfo.getBuyerId());
              pstmtupdBuyer.executeUpdate();
            } else {
              pstmtInsBuyer = con.prepareStatement(insQuery);
              pstmtInsBuyer.setInt(1, buyerInfo.getBuyerId());
              pstmtInsBuyer.setString(2, buyerInfo.getGoals());
              pstmtInsBuyer.executeUpdate();
            }

          }
        }
      }

    } catch (Exception e) {
      LoggerUtil.log("error", "Error at getUser method " + e.toString());
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (rs1 != null)
          rs1.close();
        if (pstmtUpdate != null)
          pstmtUpdate.close();
        if (pstmtSelBuyer != null)
          pstmtSelBuyer.close();
        if (pstmtupdBuyer != null)
          pstmtupdBuyer.close();
        if (pstmtInsBuyer != null)
          pstmtInsBuyer.close();
      } catch (Exception ee) {
      }
    }
    return intResult;
  }

  /*
   * Method to get the list of buyers belonging to a team.
   */

  public String getAutTeamBuyers(String team, Connection con) {

    StringBuffer autUsrString = null;
    LinkedList autBuyers = null;
    Statement stmt = null;
    ResultSet rset = null;
    String qryString = "SELECT DISTINCT(USBUYR) FROM F560212 WHERE USMUSE = '" + team.trim() + "'";
    try {
      stmt = con.createStatement();
      rset = stmt.executeQuery(qryString);
      autBuyers = new LinkedList();
      while (rset.next()) {
        autBuyers.add(rset.getString(1));
      }
      autUsrString = new StringBuffer(autBuyers.toString());
      autUsrString.replace(0, 1, "(");
      autUsrString.replace((autUsrString.length() - 1), autUsrString.length(), ")");
    } catch (Exception e) {
      LoggerUtil.log("error", "Error at method getAutTeamBuyers in UserManager " + e.toString());
    } finally {
      try {
        rset.close();
        stmt.close();
      } catch (Exception ee) {
      }
    }
    return autUsrString.toString();
  }

  /*
   * Method to get the list of buyers belonging to a group.
   */
  public String getAutGroupBuyers(String group, Connection con) {

    StringBuffer autUsrString = null;
    LinkedList autBuyers = null;
    Statement stmt = null;
    ResultSet rset = null;
    String qryString = "SELECT DISTINCT(USBUYR) FROM F560212 WHERE USGPN = '" + group.trim() + "'";
    try {
      stmt = con.createStatement();
      rset = stmt.executeQuery(qryString);
      autBuyers = new LinkedList();
      while (rset.next()) {
        autBuyers.add(rset.getString(1));
      }
      autUsrString = new StringBuffer(autBuyers.toString());
      autUsrString.replace(0, 1, "(");
      autUsrString.replace((autUsrString.length() - 1), autUsrString.length(), ")");
    } catch (Exception e) {
      LoggerUtil.log("error", "Error at method getAutGroupBuyers in UserManager " + e.toString());
    } finally {
      try {
        rset.close();
        stmt.close();
      } catch (Exception ee) {
      }
    }
    return autUsrString.toString();
  }

  public String getGroupName(String groupId, Connection con) {
    Statement stmt = null;
    ResultSet rset = null;
    String groupName = "";
    String qryString =
        "SELECT DISTINCT USCEGN FROM F560212 WHERE USGPN = '" + groupId + "' and USNPOS ='MANAGER'";
    try {
      stmt = con.createStatement();
      rset = stmt.executeQuery(qryString);
      if (rset.next()) {
        groupName = StringUtil.checkNull(rset.getString(1));
      }

    } catch (Exception e) {
      LoggerUtil.log("error", "Error at getGroupName method " + e.toString());
    } finally {
      try {
        rset.close();
        stmt.close();
      } catch (Exception ee) {
      }
    }

    return groupName;
  }

  /**
   * Method getBuyerList.
   * 
   * @author Sriram.Krishnamurthy
   * @param con
   * @param managerBuyerNo
   * @param flag
   * @return String Returns the list of buyer's as String in Closed Brackets for either Group or
   *         Team or Corporate. The flag can be one of the following values - ("G", "T", "C")
   */
  public String getBuyerList(Connection con, int managerBuyerNo, String flag) {

    StringBuffer autUsrString = null;
    LinkedList autBuyers = null;
    PreparedStatement pstmt = null;
    ResultSet rset = null;
    String teamQuery = "SELECT DISTINCT(USBUYR) FROM F560212 WHERE USMUSE IN "
        + "(SELECT USMUSE FROM F560212 WHERE USBUYR = ?)";

    String groupQuery = "SELECT DISTINCT(USBUYR) FROM F560212 WHERE USGPN IN "
        + "(SELECT USGPN FROM F560212 WHERE USBUYR = ?)";

    String corporateQuery = "SELECT DISTINCT(USBUYR) FROM F560212 WHERE US$ACT='Y' ";

    String qryString = "";


    if (flag.trim().equalsIgnoreCase("T"))
      qryString = teamQuery;
    else if (flag.trim().equalsIgnoreCase("G"))
      qryString = groupQuery;
    else
      qryString = corporateQuery;

    LoggerUtil.log("debug", this.getClass() + "getBuyerList " + qryString);

    try {
      pstmt = con.prepareStatement(qryString);
      if (flag.trim().equalsIgnoreCase("T") || flag.trim().equalsIgnoreCase("G"))
        pstmt.setInt(1, managerBuyerNo);
      rset = pstmt.executeQuery();
      autBuyers = new LinkedList();
      while (rset.next()) {
        autBuyers.add(rset.getString(1));
      }
      autUsrString = new StringBuffer(autBuyers.toString());
      autUsrString.replace(0, 1, "(");
      autUsrString.replace((autUsrString.length() - 1), autUsrString.length(), ")");
    } catch (Exception e) {
      LoggerUtil.log("error", "Error at " + this.getClass() + " on method getBuyerList " + e.toString());
      e.printStackTrace();
    } finally {
      DBUtil.close(pstmt);
    }
    return autUsrString.toString();
  }

  /*
   * @ buyerInfo
   * 
   * @ con
   * 
   * Following method fetches the main menu info for specified user.
   */
  public LinkedList getMainMenuList(String userId, Connection con) {
    Statement stmt = null;
    ResultSet rset = null;
    LinkedList mainMenuList = new LinkedList();
    UserMainMenu userMainMenu = null;

    String qryString = " SELECT DISTINCT B.CD_CTGRY, B.CD_MENU,B.DESCRIPTION,B.TITLE,B.URL,B.SEQNO "
        + " FROM IM0004 AS A,IM0001 AS B WHERE " + " A.CD_MENU=B.CD_MENU AND UPPER(USER_ID)='"
        + userId.toUpperCase() + "' AND ACTIVE_FLAG='Y' "
        + " ORDER BY  CD_CTGRY DESC,B.SEQNO      ";
    try {
      stmt = con.createStatement();
      rset = stmt.executeQuery(qryString);
      while (rset.next()) {
        userMainMenu = new UserMainMenu();
        userMainMenu.setCtgryCode(rset.getString(1));
        userMainMenu.setMenuCode(rset.getString(2));
        userMainMenu.setMenuDesc(rset.getString(3));
        userMainMenu.setMenuTitle(rset.getString(4));
        userMainMenu.setUrl(rset.getString(5));
        mainMenuList.add(userMainMenu);
      }

    } catch (Exception e) {
      LoggerUtil.log("error", "Error at getMainMenuList method " + e.toString());
    } finally {
      DBUtil.close(rset);
      DBUtil.close(stmt);
    }

    return mainMenuList;
  }

  /*
   * @ buyerInfo
   * 
   * @ con
   * 
   * Following method fetches the submenu info for specified user.
   */
  public LinkedList getSubMenuList(String userId, Connection con) {
    Statement stmt = null;
    ResultSet rset = null;
    LinkedList subMenuList = new LinkedList();
    UserSubMenu userSubMenu = null;

    String qryString =
        " SELECT DISTINCT B.CD_MENU,C.DESCRIPTION, B.CD_SUBMENU,B.DESCRIPTION,B.TITLE, B.URL,B.SEQNO "
            + " FROM IM0004 AS A,IM0002 AS B,IM0001 AS C WHERE  "
            + " A.CD_MENU=B.CD_MENU AND A.CD_SUBMENU=B.CD_SUBMENU AND B.CD_MENU=C.CD_MENU "
            + " AND UPPER(USER_ID)='" + userId.toUpperCase() + "' AND ACTIVE_FLAG='Y' "
            + " ORDER BY B.CD_MENU, B.SEQNO ";
    try {
      stmt = con.createStatement();
      rset = stmt.executeQuery(qryString);
      while (rset.next()) {
        userSubMenu = new UserSubMenu();
        userSubMenu.setMenuCode(rset.getString(1));
        userSubMenu.setMenuDesc(rset.getString(2));
        userSubMenu.setSubMenuCode(rset.getString(3));
        userSubMenu.setSubMenuDesc(rset.getString(4));
        userSubMenu.setSubMenuTitle(rset.getString(5));
        userSubMenu.setUrl(rset.getString(6));
        subMenuList.add(userSubMenu);
      }

    } catch (Exception e) {
      LoggerUtil.log("error", "Error at getSubMenuList method " + e.toString());
    } finally {
      DBUtil.close(rset);
      DBUtil.close(stmt);
    }

    return subMenuList;
  }

  /*
   * @ buyerInfo
   * 
   * @ con
   * 
   * Following method creates the menu info for new user depending the role.
   */
  public void createMenuInfo(BuyerInfo buyerInfo, Connection con) {
    PreparedStatement pstmtUpdate = null;
    Statement stmt = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    String strSelQuery =
        "SELECT * FROM IM0004 WHERE USER_ID='" + buyerInfo.getUserId().trim() + "'";

    String updString = "INSERT INTO IM0004 (USER_ID,CD_MENU,CD_SUBMENU,   "
        + " ACTIVE_FLAG,USERID) " + " SELECT B.USUSRI, A.CD_MENU, A.CD_SUBMENU ,'Y','PROGRAM' FROM "
        + " IM0003 AS A,F560212 AS B WHERE A.CD_ROLE= B.USNPOS AND B.USUSRI=?";

    try {
      stmt = con.createStatement();
      rs1 = stmt.executeQuery(strSelQuery);
      if (!rs1.next()) {
        pstmtUpdate = con.prepareStatement(updString);
        pstmtUpdate.setString(1, buyerInfo.getUserId().trim());
        pstmtUpdate.executeUpdate();
      }

    } catch (Exception e) {
      LoggerUtil.log("error", "Error at createMenuInfo method " + e.toString());
    } finally {
      DBUtil.close(rs);
      DBUtil.close(rs1);
      DBUtil.close(pstmtUpdate);
    }


  }

  /*
   * @ buyerInfo
   * 
   * @ strUserid
   * 
   * @ con
   * 
   * Following method fetches the availble user roles from F560212.
   */

  public String getUserRole(BuyerInfo buyerInfo, String strUserid, Connection con) {
    Statement stmt = null;
    ResultSet rset = null;
    String userRole = "";

    String qryString = " SELECT USNPOS FROM F560212 WHERE USUSRI = '" + strUserid.trim() + "'";
    try {
      stmt = con.createStatement();
      rset = stmt.executeQuery(qryString);
      while (rset.next()) {
        userRole = rset.getString(1);
      }

    } catch (Exception e) {
      LoggerUtil.log("error", "Error at getUserRole method " + e.toString());
    } finally {
      DBUtil.close(rset);
      DBUtil.close(stmt);
    }

    return userRole;
  }

  /*
   * @ strUserid
   * 
   * @ con
   * 
   * Following method deletes the existing menu option for the specified user id
   */

  public void deleteExistingMenuOptions(String strUserid, Connection con) {
    Statement stmt = null;

    String qryString = " DELETE FROM IM0004 WHERE USER_ID = '" + strUserid.trim() + "'";
    try {
      stmt = con.createStatement();
      stmt.executeUpdate(qryString);

    } catch (Exception e) {
      LoggerUtil.log("error", "Error at deleteExistingMenuOptions method " + e.toString());
    } finally {
      DBUtil.close(stmt);
    }

  }

  public LinkedList getUserRoleList(Connection con) {
    Statement stmt = null;
    ResultSet rset = null;
    LinkedList userRoleList = new LinkedList();

    String qryString = " SELECT RLNPOS FROM F560218 ";
    try {
      stmt = con.createStatement();
      rset = stmt.executeQuery(qryString);
      while (rset.next()) {
        userRoleList.add(rset.getString(1));
      }

    } catch (Exception e) {
      LoggerUtil.log("error", "Error at getUserRoleList method " + e.toString());
    } finally {
      DBUtil.close(rset);
      DBUtil.close(stmt);
    }

    return userRoleList;
  }

  public List getTeamGroup(Connection con, String typ) {

    Statement stmt = null;
    ResultSet rset = null;
    List dropDownList = new ArrayList();

    String teamString = " SELECT DISTINCT USMUSE FROM F560212 WHERE US$ACT='Y'";
    String groupString = " SELECT DISTINCT USGPN FROM F560212 WHERE US$ACT='Y'";
    String qryString = "";

    if (typ.equals("team")) {
      qryString = teamString;
    } else if (typ.equals("group")) {
      qryString = groupString;
    }

    try {
      stmt = con.createStatement();
      rset = stmt.executeQuery(qryString);
      while (rset.next()) {
        dropDownList.add(rset.getString(1).trim());
      }

    } catch (Exception e) {
      LoggerUtil.log("error", "Error at getUserRoleList method " + e.toString());
    } finally {
      DBUtil.close(rset);
      DBUtil.close(stmt);
    }

    return dropDownList;

  }


}
