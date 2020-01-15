/*******************************************************************************************************************
 * File			: CustomPropertyDao.java
 * 
 * Description	: This Dao contains methods for insert,update and delete the properties from F570406 table.
 * REVISION HISTORY
 * ****************
 * Date			Version		Phase					Change Description
 * =============================================================================================================
 * 11/26/2008	1.0		Expired DB Release-I	Initial Release
 * 
 *******************************************************************************************************************
 */
package com.schein.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import com.schein.utils.BoUtils;
import com.schein.utils.DBUtil;
import com.schein.utils.LoggerUtil;
import com.schein.utils.ScheinUtils;
import com.schein.utils.StringUtil;
import com.schein.bean.CustomPropertyBean;

/**
 * @author Sridhar.Muppa
 * 
 */
public class CustomPropertyDao {

	/**
	 * @param con
	 * @param key
	 * @return
	 * @throws Exception
	 *             This method will return the list of properties based on key
	 *             value
	 */
	public LinkedList getPropertyList(Connection con, String key)
			throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		CustomPropertyBean dispositionBean = null;
		LinkedList ltDisposition = new LinkedList();
		String sqlString = "SELECT E6CODE,E6KEY, E6SEQ, E6SEQSV, E6SEQLV,E6ACTIV,E6CADT,E6USER"
				+ " FROM F570406 WHERE E6KEY=? AND E6ACTIV = ? ORDER BY E6SEQ";

		try {
			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, key);
			pstmt.setString(2, "Y");
			rset = pstmt.executeQuery();
			while (rset.next()) {
				dispositionBean = new CustomPropertyBean();
				dispositionBean.setKeyCode(rset.getInt(1));
				dispositionBean.setKey(StringUtil.checkNull(rset.getString(2)));
				dispositionBean.setSequence(rset.getInt(3));
				dispositionBean.setShortValue(StringUtil.checkNull(rset
						.getString(4)));
				dispositionBean.setLongValue(StringUtil.checkNull(rset
						.getString(5)));
				dispositionBean.setActiveFlag(StringUtil.checkNull(rset
						.getString(6)));
				dispositionBean.setCrtDate(ScheinUtils.getYMDSeparated(
						rset.getString(7), '/'));
				dispositionBean.setCrtUser(StringUtil.checkNull(rset
						.getString(8)));
				ltDisposition.add(dispositionBean);
			}
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at " + this.getClass()
					+ "on method getDispositionList" + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(rset);
			DBUtil.close(pstmt);
		}
		return ltDisposition;
	}


	/**
	 * @param con
	 * @param propertyBean
	 * @return
	 * @throws Exception
	 *             This method will insert new property to F570406 table
	 */
	public int insertCustProperty(Connection con,
			CustomPropertyBean propertyBean) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		String sqlInsert = "INSERT INTO F570406(E6CODE,E6KEY,E6SEQ,E6SEQSV,E6SEQLV,E6ACTIV,E6CADT,E6CRTM,E6USER,E6PID,"
				+ "E6JOBN) VALUES((SELECT IFNULL(MAX(E6CODE),0)+1 FROM F570406),?,"
				+ "(SELECT IFNULL(MAX(E6SEQ),0)+1 FROM F570406 WHERE E6KEY=?),?,?,?,?,?,?,?,?)";
		try {
			pstmt = con.prepareStatement(sqlInsert);
			pstmt.setString(1, propertyBean.getKey());
			pstmt.setString(2, propertyBean.getKey());
			pstmt.setString(3, propertyBean.getShortValue());
			pstmt.setString(4, propertyBean.getLongValue());
			pstmt.setString(5, propertyBean.getActiveFlag());
			pstmt.setString(6, propertyBean.getCrtDate());
			pstmt.setString(7, propertyBean.getCrtTime());
			pstmt.setString(8, propertyBean.getCrtUser());
			pstmt.setString(9, propertyBean.getProgramId());
			pstmt.setString(10, propertyBean.getWorkID());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at " + this.getClass()
					+ " on method insertCustProperty" + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
		return result;
	}

	/**
	 * @param con
	 * @param propertyBean
	 * @return
	 * @throws Exception
	 *             This method will update the list of properties
	 */
	public int updateCustProperty(Connection con, LinkedList ltData)
			throws Exception {
		PreparedStatement pstmt = null;
		CustomPropertyBean propertyBean = null;
		int result = 0;
		String sqlUpdate = "UPDATE F570406 SET E6SEQSV=?,E6SEQLV=?,E6ACTIV=?,E6CADT=?,E6CRTM=?,E6USER=?,"
				+ "E6PID=?,E6JOBN=? WHERE E6CODE=?";
		try {
			pstmt = con.prepareStatement(sqlUpdate);
			for (int i = 0; i < ltData.size(); i++) {
				propertyBean = (CustomPropertyBean) ltData.get(i);
				pstmt.setString(1, propertyBean.getShortValue());
				pstmt.setString(2, propertyBean.getLongValue());
				pstmt.setString(3, propertyBean.getActiveFlag());
				pstmt.setString(4, propertyBean.getCrtDate());
				pstmt.setString(5, propertyBean.getCrtTime());
				pstmt.setString(6, propertyBean.getCrtUser());
				pstmt.setString(7, propertyBean.getProgramId());
				pstmt.setString(8, propertyBean.getWorkID());
				pstmt.setInt(9, propertyBean.getKeyCode());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at " + this.getClass()
					+ " on method updateCustProperty" + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
		return result;
	}

	/**
	 * @param con
	 * @param propertyBean
	 * @return
	 * @throws Exception
	 *             This method will delete the list of properties by updating
	 *             the active flag to "N"
	 */
	public void deleteCustProperty(Connection con, LinkedList ltData)
			throws Exception {
		PreparedStatement pstmt = null;
		CustomPropertyBean propertyBean = null;
		String sqlUpdate = "UPDATE F570406 SET E6ACTIV=?,E6CADT=?,E6CRTM=?,E6USER=?,"
				+ "E6PID=?,E6JOBN=? WHERE E6CODE=?";
		try {
			pstmt = con.prepareStatement(sqlUpdate);
			for (int i = 0; i < ltData.size(); i++) {
				propertyBean = (CustomPropertyBean) ltData.get(i);
				pstmt.setString(1, propertyBean.getActiveFlag());
				pstmt.setString(2, propertyBean.getCrtDate());
				pstmt.setString(3, propertyBean.getCrtTime());
				pstmt.setString(4, propertyBean.getCrtUser());
				pstmt.setString(5, propertyBean.getProgramId());
				pstmt.setString(6, propertyBean.getWorkID());
				pstmt.setInt(7, propertyBean.getKeyCode());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at " + this.getClass()
					+ " on method deleteCustProperty" + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
	}

	/**
	 * @param con
	 * @param subMenuCode
	 * @return String
	 * @throws Exception
	 *             This method will return sub Menu Description
	 */

	public String getSubMenuDesc(String subMenuCode, Connection con) {

		Statement stmt = null;
		ResultSet rset = null;
		String subMenuDesc = "";
		String qryString = "SELECT DESCRIPTION FROM IM0002  WHERE  CD_SUBMENU='"
				+ subMenuCode.toUpperCase().trim() + "'";

		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(qryString);
			while (rset.next()) {
				subMenuDesc = rset.getString(1).trim();

			}

		} catch (Exception e) {
			LoggerUtil.log("error", "Error at MenuDAO.getSubMenuDesc method "
					+ e.toString());
		} finally {
			DBUtil.close(rset);
			DBUtil.close(stmt);
		}

		return subMenuDesc;
	}
}
