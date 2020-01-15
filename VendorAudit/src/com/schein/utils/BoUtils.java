package com.schein.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.DataQueue;
import com.schein.bean.*;
import com.schein.dao.CustomPropertyDao;
import com.schein.bean.CustomPropertyBean;
import com.schein.bean.GIVWhse15SupplierBean;

/**
 * @author Dilip Nair
 * 
 *         This class contains utility functions used for the BO Analysis
 *         application
 */
public class BoUtils {
	private static DecimalFormat format = new DecimalFormat("0.0");

	/**
	 * Method getBoReasonCode.
	 * 
	 * @return Collection
	 * 
	 *         Returns the backorder reasons from table F560216
	 */
	public static Collection getBoReasonCode() {
		LinkedList reasonList = new LinkedList();
		BoReasonCode boRsn = new BoReasonCode();
		Connection con = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			con = AS400Utils.getAS400DBConnection();
			stmt = con.createStatement();
			rset = stmt.executeQuery("SELECT * FROM F560216 ORDER BY RCDESC");
			while (rset.next()) {
				boRsn.setReasonCode(rset.getInt(1));
				boRsn.setReasonDesc(rset.getString(2));
				reasonList.add(boRsn);
				boRsn = new BoReasonCode();
			}
			// reasonList.add(boRsn);
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at getBoReasonCode" + e.toString());
		} finally {
			try {
				con.close();
				rset.close();
				stmt.close();
			} catch (Exception ee) {
			}
		}

		return reasonList;
	}

	/**
	 * Method getActiveWhse.
	 * 
	 * @return Collection
	 * 
	 *         Returns all the active warehouses used for BO application
	 */
	public static Collection getActiveWhse() {
		LinkedList whseList = new LinkedList();
		Whse boWhse = new Whse();
		Connection con = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			con = AS400Utils.getAS400DBConnection();
			stmt = con.createStatement();
			rset = stmt
					.executeQuery(" SELECT WH$WHS, WHLNAM,  WHCLOC FROM f560215 WHERE WH$ACT ='0'");
			while (rset.next()) {
				boWhse.setBowhse(rset.getInt(1));
				boWhse.setWhseDspn(rset.getString(2));
				boWhse.setShipLoc(rset.getString(3));
				whseList.add(boWhse);
				boWhse = new Whse();
			}
			// reasonList.add(boRsn);
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at getActiveWhse" + e.toString());
		} finally {
			try {
				con.close();
				rset.close();
				stmt.close();
			} catch (Exception ee) {
			}
		}

		return whseList;
	}

	/**
	 * Method getFSCActiveWhse.
	 * 
	 * @return Collection
	 * 
	 *         Returns all the active FSC warehouses used for BO application
	 */
	public static Collection getFSCActiveWhse() {
		LinkedList fscWhseList = new LinkedList();
		Whse boWhse = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			con = AS400Utils.getAS400DBConnection();
			stmt = con.createStatement();
			rset = stmt
					.executeQuery("SELECT FWHSNO, FWHSDSPN, FWHSSHPLN FROM IM0024 WHERE FWHSACTIVE = 'A'");
			while (rset.next()) {
				boWhse = new Whse();
				boWhse.setBowhse(rset.getInt(1));
				boWhse.setWhseDspn(rset.getString(2));

				fscWhseList.add(boWhse);
			}
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at getFSCActiveWhse" + e.toString());
		} finally {
			try {
				DBUtil.close(con, stmt, rset);
			} catch (Exception ee) {
			}
		}

		return fscWhseList;
	}

	/**
	 * Method getSalesDivCode.
	 * 
	 * @return HashMap
	 * 
	 *         Return all the sales division code from table F560214
	 */
	public static HashMap getSalesDivCode() {
		HashMap divCodes = new HashMap();
		Connection con = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			con = AS400Utils.getAS400DBConnection();
			stmt = con.createStatement();
			rset = stmt
					.executeQuery("SELECT SLDVSN,SLNAME,SLSSQN  FROM F560214 ORDER BY SLSSQN");
			while (rset.next()) {
				divCodes.put(rset.getString(1), rset.getString(2));
			}
			// reasonList.add(boRsn);
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at getSalesDivCode" + e.toString());
		} finally {
			try {
				con.close();
				rset.close();
				stmt.close();
			} catch (Exception ee) {
			}
		}

		return divCodes;
	}

	/**
	 * Method getAllProperties.
	 * 
	 * @return HashMap Return all values from the table F560225
	 */
	public static HashMap getAllProperties() {
		HashMap props = new HashMap();
		Connection con = null;
		Statement stmt = null;
		ResultSet rset = null;
		try {
			con = AS400Utils.getAS400DBConnection();
			stmt = con.createStatement();
			rset = stmt.executeQuery("SELECT PR$NME,PRDTA1 FROM F560225");
			while (rset.next()) {
				props.put(rset.getString(1).trim(), rset.getString(2).trim());
			}

			LoggerUtil.log("info", "Total number of properties loaded is "
					+ props.size());
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at getAllProperties" + e.toString());
		} finally {
			try {
				con.close();
				rset.close();
				stmt.close();
			} catch (Exception ee) {
			}
		}
		return props;
	}

	/**
	 * Method writeTrace.
	 * 
	 * @param trace
	 *            This util method will check if the VERBOSE value = Y then
	 *            println the string passed as a parameter Used to switch on/off
	 *            of printing the SQL for validation and error checking
	 */
	public static void writeTrace(String trace) {
		if (CachingManager.getProperty("VERBOSE").equals("Y"))
			LoggerUtil.log("debug", ""+ trace);
	}

	/**
	 * @param totalOrder
	 * @param totalBo
	 * @return
	 * 
	 *         Calculates the fillrate provided the total orders lines and
	 *         backorder lines
	 */
	public static double getFillRate(int totalOrder, int totalBo) {
		double fillrate = 0.0;
		if (totalOrder != 0) {
			fillrate = (((totalOrder - totalBo) / (double) totalOrder) * 100);
		}
		return fillrate;
	}

	/**
	 * Method getRoundedDiff.
	 * 
	 * @param curFillRate
	 * @param preFillRate
	 * @return double
	 * 
	 *         Returns the rounded value of the fillrate difference
	 */
	public static double getRoundedDiff(double curFillRate, double preFillRate) {
		curFillRate = Double.parseDouble(format.format(curFillRate));
		preFillRate = Double.parseDouble(format.format(preFillRate));
		return (curFillRate - preFillRate);
	}

	/**
	 * @param totalOrder
	 * @param totalBo
	 * @return
	 * 
	 *         Calculates the fillrate provided the total orders lines and
	 *         backorder lines
	 */
	public static double getFillRate(double totalOrder, double totalBo) {
		double fillrate = 0.0;
		if (totalOrder != 0) {
			fillrate = ((totalOrder - totalBo) * 100) / (double) totalOrder;
		}
		return fillrate;
	}

	// Added this new method to print the commentary report

	public static String replaceAll(String origString, String searchStr,
			String replaceStr) {
		StringBuffer sb = new StringBuffer();

		int startIndex = 0;
		int index = origString.indexOf(searchStr, startIndex);

		while (index >= 0) {
			sb.append(origString.substring(startIndex, index));
			sb.append(replaceStr);
			startIndex = index + searchStr.length();
			index = origString.indexOf(searchStr, startIndex);
		}
		sb.append(origString.substring(startIndex));

		return sb.toString();
	}

	/*
	 * returns the latest date from the summary file F560966 reportType can be
	 * "C" for currentDate or "P" for previous date
	 */

	public static int getCurOrPrevBoSummaryDate(String reportType) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rset = null;
		int currentDate = 0;
		int prevDate = 0;

		String sql = " SELECT DISTINCT BICADT FROM F560966  ORDER BY BICADT  DESC "
				+ "	FETCH FIRST 2 ROWS ONLY ";

		try {
			con = AS400Utils.getAS400DBConnection();
			stmt = con.createStatement();
			rset = stmt.executeQuery(sql);
			while (rset.next()) {
				if (currentDate == 0) {
					currentDate = rset.getInt(1);
				} else {
					prevDate = rset.getInt(1);
				}
			}

		} catch (Exception e) {
			LoggerUtil.log("error", "Error at getCurOrPrevBoSummaryDate in BOUtils.java "
							+ e.toString());
		} finally {
			try {
				stmt.close();
				rset.close();
				con.close();
			} catch (Exception ee) {
			}
		}

		if (reportType.equalsIgnoreCase("C")) {
			return currentDate;
		} else {
			return prevDate;
		}
	}

	public static String getActivityFlag(String typeOfRecords) {
		String activityFlag = "";
		if (typeOfRecords.trim().equalsIgnoreCase("regular")) {
			activityFlag = "R";
		} else if (typeOfRecords.trim().equalsIgnoreCase("promotional")) {
			activityFlag = "P";
		} else if (typeOfRecords.trim().equalsIgnoreCase("corpBranded")) {
			activityFlag = "C";
		}
		return activityFlag;
	}

	public static String getActivityNameFromFlag(String flag) {
		String activityName = "";
		if (flag.trim().equalsIgnoreCase("R")) {
			activityName = "regular";
		} else if (flag.trim().equalsIgnoreCase("P")) {
			activityName = "promotional";
		} else if (flag.trim().equalsIgnoreCase("C")) {
			activityName = "corpBranded";
		}
		return activityName;
	}

	public static String getActivityName(String typeOfRecords) {
		String activityName = "";
		if (typeOfRecords == null
				|| typeOfRecords.trim().equalsIgnoreCase("regular")) {
			activityName = "Corporate";
		} else if (typeOfRecords.trim().equalsIgnoreCase("promotional")) {
			activityName = "Promotional";
		} else if (typeOfRecords.trim().equalsIgnoreCase("corpBranded")) {
			activityName = "Corporate Brand";
		}
		return activityName;
	}

	public static String getFillRateName(String fillRateType) {
		String fillRateName = "";
		if (fillRateType == null || fillRateType.trim().equalsIgnoreCase("R")) {
			fillRateName = "Corporate Fill Rate";
		} else if (fillRateType.trim().equalsIgnoreCase("P")) {
			fillRateName = "Promotional Fill Rate";
		} else if (fillRateType.trim().equalsIgnoreCase("C")) {
			fillRateName = "Corporate Brand Fill Rate";
		}
		return fillRateName;
	}

	/**
	 * Method getSalesDivCode.
	 * 
	 * @return HashMap
	 * 
	 *         Return all the sales division code from table F560214
	 */
	public static LinkedList getSalesDivList() {
		LinkedList divisionList = new LinkedList();
		Connection con = null;
		Statement stmt = null;
		ResultSet rset = null;
		BoDivision boDivision = null;

		try {
			con = AS400Utils.getAS400DBConnection();
			stmt = con.createStatement();
			rset = stmt
					.executeQuery("SELECT SLDVSN,SLNAME,SLSSQN  FROM F560214 ORDER BY SLSSQN");
			while (rset.next()) {
				boDivision = new BoDivision();
				boDivision.setDivisionCode(rset.getString(1));
				boDivision.setDivisionName(rset.getString(2));
				boDivision.setSeqNo(rset.getInt(3));
				divisionList.add(boDivision);
			}

		} catch (Exception e) {
			LoggerUtil.log("error", "Error at getSalesDivList()" + e.toString());
		} finally {
			try {
				con.close();
				rset.close();
				stmt.close();
			} catch (Exception ee) {
			}
		}

		return divisionList;
	}

	/**
	 * @throws Exception
	 */
	public static void clearBoBuildFiles() throws Exception {
		Connection con = null;
		Statement stmt = null;
		String strDel1 = "DELETE FROM F560220";
		String strDel2 = "DELETE FROM F560224";
		String strDel3 = "DELETE FROM F560226";
		try {
			con = AS400Utils.getAS400DBConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(strDel1);
			stmt.executeUpdate(strDel2);
			stmt.executeUpdate(strDel3);
		} catch (Exception e) {
			EmailSender.sendOperEmail("Error at clearBoBuildFiles in BoUtils "
					+ e.toString());
			throw (e);
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(con);
		}
	}

	/**
	 * @throws Exception
	 */
	public static void createDQEntry() throws Exception {
		String system = "";
		String dqLib = "";
		String dqName = "";
		AS400 sys = null;
		DataQueue dq = null;
		try {
			system = ((String) CachingManager.getSysProperties().get("SYSTEM"))
					.trim();
			dqLib = ((String) CachingManager.getSysProperties().get("DQLIB"))
					.trim();
			dqName = ((String) CachingManager.getSysProperties().get("DQNAME"))
					.trim();

			sys = new AS400(system, "WEBAPP", "WEB");
			dq = new DataQueue(sys, "/QSYS.LIB/" + dqLib + ".LIB/" + dqName
					+ ".DTAQ");
			writeTrace("Entered Listener and listening to " + dqName + " in "
					+ system);
			dq.write("C");
		} catch (Exception e) {
			EmailSender.sendOperEmail("Error at createDQEntry in BoUtils "
					+ e.toString());
			throw (e);
		} finally {

		}
	}

	/**
	 * Method getActiveWhse.
	 * 
	 * @return Collection
	 * 
	 *         Returns all the active warehouses used for BO application
	 */
	public static Collection getVendorScoreCardPeriodList(String flag) {
		LinkedList periodList = new LinkedList();
		Connection con = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			con = AS400Utils.getAS400DBConnection();
			stmt = con.createStatement();
			rset = stmt
					.executeQuery("SELECT DISTINCT FRCURM FROM f560965 WHERE FR$FG1='"
							+ flag + "' ORDER BY FRCURM ");
			while (rset.next()) {
				periodList.add(rset.getString(1));
			}

		} catch (Exception e) {
			LoggerUtil.log("error", "Error at getVendorScoreCardPeriodList"
					+ e.toString());
		} finally {
			try {
				con.close();
				rset.close();
				stmt.close();
			} catch (Exception ee) {
			}
		}

		return periodList;
	}

	public static String getFormatedFillRate(double fillRate) {
		try {
			return format.format(fillRate);
		} catch (Exception e) {
			return "0.0";
		}
	}

	public static String getFscRankName(int fscRank) {
		String rankName = "";
		switch (fscRank) {
		case 1:
			rankName = "Total Net Sales";
			break;
		case 2:
			rankName = "Goods Net Sales";
			break;
		case 3:
			rankName = "Total Order Value ($)";
			break;
		case 4:
			rankName = "Goods Ordered ($)";
			break;
		case 5:
			rankName = "Total Lines Ordered";
			break;
		case 6:
			rankName = "Goods Lines Ordered";
			break;
		}
		return rankName;
	}

	public static String getBuyerName(int buyerNumber) {
		if (buyerNumber == -1)
			return "";
		String buyerName = "";
		Connection con = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			con = AS400Utils.getAS400DBConnection();
			stmt = con.createStatement();
			rset = stmt
					.executeQuery("SELECT USCEGN FROM f560212 WHERE USBUYR ="
							+ buyerNumber);
			if (rset.next())
				buyerName = rset.getString(1);
			else
				buyerName = buyerNumber + "";

		} catch (Exception e) {
			LoggerUtil.log("error", "Error at BoUtils.getBuyerName" + e.toString());
		} finally {
			DBUtil.close(rset);
			DBUtil.close(stmt);
			DBUtil.close(con);
		}

		return buyerName;
	}

	public static String getPropertyName(int keycode, String divisoncode) {
		String propertyName = "";
		Connection con = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			con = AS400Utils.getAS400DBConnection();
			stmt = con.createStatement();
			if (keycode > 0 && divisoncode == null)
				rset = stmt
						.executeQuery("SELECT E6SEQSV FROM F570406 WHERE E6CODE ="
								+ keycode);
			else if (divisoncode != null && !"".equals(divisoncode))
				rset = stmt
						.executeQuery("SELECT DISTINCT DRDL01 FROM F0005 WHERE DRSY='01' and DRRT='10' and trim(DRKY) ='"
								+ divisoncode + "'");
			if (rset.next())
				propertyName = rset.getString(1);

		} catch (Exception e) {
			LoggerUtil.log("error", "Error at getPropertyName" + e.toString());
		} finally {
			DBUtil.close(rset);
			DBUtil.close(stmt);
			DBUtil.close(con);
		}

		return propertyName;
	}

	public static String getTeamName(int buyerNumber) {
		return getBuyerName(buyerNumber);
	}

	public static String getUnitTypeSelHTML(int selectedIndex, String attributes) {
		StringBuffer unitTypeSelHTML = new StringBuffer("");
		Connection con = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			con = AS400Utils.getAS400DBConnection();
			stmt = con.createStatement();
			rset = stmt
					.executeQuery("select FLDCDE, FLDCTX from HLPVDL where FLDNME='DCMINT' and FLENME='NPFVDC'");

			unitTypeSelHTML.append("<select " + attributes + " >");
			unitTypeSelHTML.append("<option value='0'>Select a value</option>");
			while (rset.next()) {
				unitTypeSelHTML
						.append("<option value='" + rset.getInt(1) + "'");
				if (selectedIndex == rset.getInt(1))
					unitTypeSelHTML.append(" selected");
				unitTypeSelHTML.append(" >" + rset.getString(2).trim()
						+ "</option>");
			}

			unitTypeSelHTML.append("</select>");

		} catch (Exception e) {
			LoggerUtil.log("error", "Error at getUnitTypeSelHTML" + e.toString());
		} finally {
			DBUtil.close(rset);
			DBUtil.close(stmt);
			DBUtil.close(con);
		}

		return unitTypeSelHTML.toString();
	}

	public static String getUnitType(int selectedUnit) {
		String unitType = "";
		Connection con = null;
		Statement stmt = null;
		ResultSet rset = null;
		Integer selected = new Integer(selectedUnit);

		try {
			con = AS400Utils.getAS400DBConnection();
			stmt = con.createStatement();
			rset = stmt
					.executeQuery("select FLDCTX from HLPVDL where FLDNME='DCMINT' and FLENME='NPFVDC' and FLDCDE='"
							+ selected.toString() + "'");
			while (rset.next()) {
				unitType = rset.getString(1) == null ? "" : rset.getString(1);
			}
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at getUnitType" + e.toString());
		} finally {
			DBUtil.close(rset);
			DBUtil.close(stmt);
			DBUtil.close(con);
		}
		return unitType;
	}

	public static String getCustomPropSelHTML(int selectedIndex, String key,
			String attributes) {
		StringBuffer customPropSelHTML = new StringBuffer("");
		Connection con = null;
		CustomPropertyDao customPropDao = new CustomPropertyDao();
		CustomPropertyBean custPropBean = null;
		LinkedList customProps = null;

		try {
			con = AS400Utils.getAS400DBConnection();
			customProps = customPropDao.getPropertyList(con, key);

			for (int i = 0; i < customProps.size(); i++) {
				if (i == 0) {
					customPropSelHTML.append("<select " + attributes + " >");
					customPropSelHTML
							.append("<option value='0'>Select a value</option>");
				}

				custPropBean = (CustomPropertyBean) customProps.get(i);

				customPropSelHTML.append("<option value='"
						+ custPropBean.getKeyCode() + "' ");
				if (custPropBean.getKeyCode() == selectedIndex)
					customPropSelHTML.append("selected ");
				customPropSelHTML.append(">" + custPropBean.getLongValue()
						+ "</option>");

				if (i == customProps.size() - 1)
					customPropSelHTML.append("</select>");
			}

		} catch (Exception e) {
			LoggerUtil.log("error", "Error at getCustomPropSelHTML" + e.toString());
		} finally {
			DBUtil.close(con);
		}

		return customPropSelHTML.toString();
	}

	public static String formatedPhoneNumber(String rawPhoneNumber) {
		String formatedPhoneNumber = null;
		rawPhoneNumber = rawPhoneNumber.trim();
		int index = rawPhoneNumber.length();
		if (index == 10)
			formatedPhoneNumber = "(" + rawPhoneNumber.substring(0, 3) + ")"
					+ rawPhoneNumber.substring(3, 6) + "-"
					+ rawPhoneNumber.substring(6);
		else if (index >= 11 && rawPhoneNumber.startsWith("1"))
			formatedPhoneNumber = rawPhoneNumber.substring(0, 1) + "("
					+ rawPhoneNumber.substring(1, 4) + ")"
					+ rawPhoneNumber.substring(4, 7) + "-"
					+ rawPhoneNumber.substring(7);
		else if (index > 10 && !rawPhoneNumber.startsWith("1"))
			formatedPhoneNumber = "(" + rawPhoneNumber.substring(0, 3) + ")"
					+ rawPhoneNumber.substring(3, 6) + "-"
					+ rawPhoneNumber.substring(6, 10) + " X "
					+ rawPhoneNumber.substring(10);
		else if (index < 10 && index > 6)
			formatedPhoneNumber = "(" + rawPhoneNumber.substring(0, 3) + ")"
					+ rawPhoneNumber.substring(3, 6) + "-"
					+ rawPhoneNumber.substring(6);
		else if (index == 6)
			formatedPhoneNumber = "(" + rawPhoneNumber.substring(0, 3) + ")"
					+ rawPhoneNumber.substring(3);
		else
			formatedPhoneNumber = rawPhoneNumber;

		return formatedPhoneNumber;
	}

	public static LinkedList getGIVWhseList() {
		LinkedList whseList = new LinkedList();
		Whse givWhse = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rset = null;
		try {
			con = AS400Utils.getAS400DBConnection();
			stmt = con.createStatement();
			rset = stmt
					.executeQuery("SELECT E6SEQSV, E6SEQLV FROM F570406 WHERE E6KEY = 'GIV_WHSE'");
			while (rset.next()) {
				givWhse = new Whse();
				givWhse.setBowhse(rset.getInt(1));
				givWhse.setWhseDspn(rset.getString(2));
				whseList.add(givWhse);
			}
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at getGIVWhseList " + e.toString());
		} finally {
			try {
				con.close();
				rset.close();
				stmt.close();
			} catch (Exception ee) {
			}
		}
		return whseList;
	}

	public static LinkedList getWhse15SuppList() {
		LinkedList suppList = new LinkedList();
		GIVWhse15SupplierBean suppBean = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rset = null;
		try {
			con = AS400Utils.getAS400DBConnection();
			stmt = con.createStatement();
			rset = stmt
					.executeQuery("SELECT DRKY, DRDL01 FROM F0005 WHERE DRSY = '56' AND DRRT = 'AB'");
			while (rset.next()) {
				suppBean = new GIVWhse15SupplierBean();
				suppBean.setSupplierCode(rset.getString(1).trim());
				suppBean.setSupplierDesc(rset.getString(2).trim());
				suppList.add(suppBean);
			}
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at getWhse15SuppList " + e.toString());
		} finally {
			try {
				con.close();
				rset.close();
				stmt.close();
			} catch (Exception ee) {
			}
		}
		return suppList;
	}

	public static Map getSearchMap(Connection connection, String searchKey) {

		Map searchMap = new LinkedHashMap();

		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("SELECT E6SEQSV,E6SEQLV,E6SEQ FROM F570406 WHERE E6KEY='"
						+ searchKey + "'AND E6ACTIV='Y' ORDER BY E6SEQ");

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(queryString.toString());

			while (resultSet.next()) {
				String longValue = resultSet.getString("E6SEQLV");
				if (longValue == null || "".equalsIgnoreCase(longValue.trim()))
					searchMap.put(resultSet.getString("E6SEQ").trim(),
							resultSet.getString("E6SEQSV").trim());
				else
					searchMap.put(resultSet.getString("E6SEQSV").trim(),
							resultSet.getString("E6SEQLV"));
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}
		return searchMap;

	}

	public static List getPropertyList(Connection connection, String key) {

		List propertyList = new ArrayList();

		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT E6SEQSV FROM F570406 WHERE E6KEY='" + key
				+ "'AND E6ACTIV='Y'");

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(queryString.toString());

			while (resultSet.next()) {
				propertyList.add(StringUtil.checkNull(resultSet
						.getString("E6SEQSV")));
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}

		return propertyList;

	}

	public static Map getScacCodeMap() {

		Map scacCodeMap = new LinkedHashMap();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		String queryString = "SELECT SCAC_GROUP_CD,GROUP_DESCRIPTION FROM POF220H ORDER BY GROUP_DESCRIPTION";

		try {
			connection = AS400Utils.getAS400DBConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(queryString);

			while (resultSet.next()) {
				scacCodeMap.put(resultSet.getString("SCAC_GROUP_CD").trim(),
						resultSet.getString("GROUP_DESCRIPTION").trim());
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
			DBUtil.close(connection);
		}

		return scacCodeMap;
	}

	/**
	 * 
	 * @return Warehouse number with names map
	 */
	public static Map getWarehouseMap() {
		HashMap<Integer, String> dcMap = new HashMap<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String queryString = "SELECT FWHSNO,FWHSSHPLN FROM IM0024";
		int dcNo;
		String dcName = "";
		try {
			connection = AS400Utils.getAS400DBConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(queryString);
			while (resultSet.next()) {
				dcNo = resultSet.getInt("FWHSNO");
				dcName = resultSet.getString("FWHSSHPLN");
				dcMap.put(dcNo, dcName);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
			DBUtil.close(connection);
		}

		return dcMap;
	}
}
