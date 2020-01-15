package com.schein.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.schein.bean.PoBean;
import com.schein.utils.BoUtils;
import com.schein.utils.DBUtil;
import com.schein.utils.LoggerUtil;

public class PoDao {

  @SuppressWarnings("unchecked")
  public List getPODetails(Connection con, String ponumber) {
    // List and all variables
    PoBean poBean = new PoBean();
    List poList = new ArrayList();

    // Connection variables
    Statement statement = null;
    Statement statement2 = null;
    ResultSet resultSet = null;
    ResultSet resultSet2 = null;
    StringBuffer queryString = new StringBuffer();
    StringBuffer queryString2 = new StringBuffer();

    queryString.append(
        "Select PONUMB, HOWHSE, HODIV#, SUPPLR, PQDOCO, PQDCTO, PQJINV FROM NPFPHO WHERE PONUMB = "
            + ponumber);

    LoggerUtil.log("debug", "Query generated in getPODetails: " + queryString);

    try {
      statement = con.createStatement();
      resultSet = statement.executeQuery(queryString.toString());


      while (resultSet.next()) {
        poBean = new PoBean();
        poBean.setPonumber(ponumber);
        poBean.setDivision(resultSet.getBigDecimal("HODIV#"));
        poBean.setWarehouse(resultSet.getBigDecimal("HOWHSE"));
        poBean.setSupplier(resultSet.getString("SUPPLR"));
        poBean.setJdeinvoice(resultSet.getInt("PQJINV"));
        poBean.setOrdernumber(resultSet.getInt("PQDOCO"));
        poBean.setOrdertype(resultSet.getString("PQDCTO"));
        poList.add(poBean);
      }
      // Check if npfpho has record. If not, check for npfphh
      if (poList.size() == 0) {

        queryString = new StringBuffer();
        queryString.append(
            "Select PONUMB, HHWHSE, HHDIV#, SUPPLR, PQDOCO, PQDCTO, PQJINV FROM NPFPHH WHERE PONUMB = "
                + ponumber);
        statement2 = con.createStatement();
        resultSet2 = statement2.executeQuery(queryString.toString());
        while (resultSet2.next()) {
          poBean = new PoBean();
          poBean.setPonumber(ponumber);
          poBean.setDivision(resultSet2.getBigDecimal("HHDIV#"));
          poBean.setWarehouse(resultSet2.getBigDecimal("HHWHSE"));
          poBean.setSupplier(resultSet2.getString("SUPPLR"));
          poBean.setJdeinvoice(resultSet2.getInt("PQJINV"));
          poBean.setOrdernumber(resultSet2.getInt("PQDOCO"));
          poBean.setOrdertype(resultSet2.getString("PQDCTO"));
          poList.add(poBean);
        }
      }

    } catch (Exception e) {
      LoggerUtil.log("error", "Error on method getPODetails " + e.toString());
      e.printStackTrace();
    } finally {
      DBUtil.close(resultSet);
      DBUtil.close(resultSet2);
      DBUtil.close(statement);
      DBUtil.close(statement2);
    }
    return poList;
  }

  public int checkTickets(Connection con, String ponumber) {
    int rowCount = 0;
    // Connection variables
    Statement statement = null;
    ResultSet resultSet = null;
    StringBuffer queryString = new StringBuffer();

    queryString =
        queryString.append("SELECT COUNT(*) FROM IM0160 WHERE PO_NUMBER='" + ponumber + "'");

    try {

      statement = con.createStatement();
      resultSet = statement.executeQuery(queryString.toString());

      rowCount = resultSet.last() ? resultSet.getRow() : 0;

    } catch (Exception e) {
      LoggerUtil.log("error", "Error on method getPODetails " + e.toString());
      e.printStackTrace();
    } finally {
      DBUtil.close(resultSet);
      DBUtil.close(statement);
    }

    return rowCount;
  }

}
