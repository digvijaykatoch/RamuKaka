package com.schein.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.schein.bean.AttachmentTable;
import com.schein.bean.EmailTable;
import com.schein.bean.IssueDetailsTable;
import com.schein.bean.IssueTable;
import com.schein.bean.MasterTable;
import com.schein.bean.PoBean;
import com.schein.utils.AS400Utils;
import com.schein.utils.BoUtils;
import com.schein.utils.DBUtil;
import com.schein.utils.DateTimeUtil;
import com.schein.utils.DropDownOption;
import com.schein.utils.LoggerUtil;

public class TicketDao {
	final String UPLOAD_DIRECTORY = "E:\\VendorAudit\\upload\\";

	@SuppressWarnings("unchecked")
	public List getTicketList(Connection con, String ponumber) {

		// List and all variables
		MasterTable mbean = new MasterTable();
		List ticketList = new ArrayList();

		// Connection variables
		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();

		queryString
				.append("Select CONTROL_NO, PO_NUMBER, ANALYST, OBSERVE_DTE, STATUS, SHIP_LOC, PRO_NUMBER FROM IM0160 WHERE PO_NUMBER = '"
						+ ponumber + "'");

		LoggerUtil.log("debug", "Query generated in getTicketList: " + queryString);

		try {
			statement = con.createStatement();
			resultSet = statement.executeQuery(queryString.toString());

			while (resultSet.next()) {
				mbean = new MasterTable();
				mbean.setControlNo(resultSet.getInt("CONTROL_NO"));
				mbean.setPoNumber(resultSet.getString("PO_NUMBER"));
				mbean.setAnalyst(resultSet.getString("ANALYST"));
				mbean.setObserveDte(resultSet.getBigDecimal("OBSERVE_DTE"));
				mbean.setStatus(resultSet.getString("STATUS"));
				mbean.setShipLoc(resultSet.getString("SHIP_LOC"));
				mbean.setProNumber(resultSet.getString("PRO_NUMBER"));
				ticketList.add(mbean);
			}

		} catch (Exception e) {
			LoggerUtil.log("error", "Error on method getPODetails " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}
		return ticketList;
	}

	public int getControlNumber(Connection con) {

		// List and all variables
		int controlNo = 0;

		// Connection variables
		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();

		queryString.append("Select MAX(CONTROL_NO) FROM IM0160");

		LoggerUtil.log("debug", "Query generated in getControlNumber: " + queryString);

		try {
			statement = con.createStatement();
			resultSet = statement.executeQuery(queryString.toString());

			while (resultSet.next()) {
				controlNo = resultSet.getInt(1);
				controlNo++;
			}

			if (controlNo == 0)
				controlNo = 1;

		} catch (Exception e) {
			LoggerUtil.log("error", "Error on method getControlNumber " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}
		return controlNo;
	}

	public List getWorkingStatus(Connection con, String ponumber) {

		// List and all variables
		List list = new ArrayList();
		DropDownOption dc = null;

		LoggerUtil.log("debug", "Query generated in getWorkingStatus: no query required.");

		try {
			dc = new DropDownOption();

			dc.setKeyValue("1");
			dc.setDispValue("Open");
			list.add(dc);

			dc.setKeyValue("2");
			dc.setDispValue("Pending");
			list.add(dc);

			dc.setKeyValue("3");
			dc.setDispValue("Completed");
			list.add(dc);

		} catch (Exception e) {
			LoggerUtil.log("error", "Error on method getWorkingStatus " + e.toString());
			e.printStackTrace();
		} finally {
			// DBUtil.close(resultSet);
			// DBUtil.close(statement);
		}
		return list;
	}

	public List getLocation(Connection con) {

		// List and all variables
		List list = new ArrayList();
		DropDownOption dc = null;

		// Connection variables
		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();

		// queryString.append("SELECT DRDL01, DRDL02 FROM f0005 WHERE DRSY='04 '
		// and DRRT='ST' ");
		queryString
				.append("SELECT E6SEQLV, E6SEQSV FROM F570406 WHERE E6KEY='AT_WHSE' AND E6ACTIV = 'Y' ORDER BY E6SEQ");

		LoggerUtil.log("debug", "Query generated in getLocation: " + queryString);

		try {
			statement = con.createStatement();
			resultSet = statement.executeQuery(queryString.toString());
			String key = "";
			String val = "";

			while (resultSet.next()) {
				val = resultSet.getString(1).trim();
				key = resultSet.getString(2).trim();
				dc = new DropDownOption();

				if (val != null && key != null) {
					if (val.trim().equalsIgnoreCase(".") || key.trim().equalsIgnoreCase("."))
						continue;

					dc.setKeyValue(resultSet.getString(2));
					dc.setDispValue(resultSet.getString(1));
					list.add(dc);
				}
			}

		} catch (Exception e) {
			LoggerUtil.log("error", "Error on method getLocation " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}
		return list;
	}

	public String getChosenLocation(Connection con, String location) {

		// List and all variables
		String loc = "";

		// Connection variables
		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();

		// queryString.append("SELECT DRDL01, DRDL02 FROM f0005 WHERE DRSY='04 '
		// and DRRT='ST' ");
		queryString.append("SELECT E6SEQLV FROM F570406 WHERE E6KEY='AT_WHSE' AND E6ACTIV = 'Y'" + " AND E6SEQSV = "
				+ Integer.parseInt(location) + " ORDER BY E6SEQ");

		LoggerUtil.log("debug", "Query generated in getChosenLocation: " + queryString);

		try {
			statement = con.createStatement();
			resultSet = statement.executeQuery(queryString.toString());

			while (resultSet.next()) {
				loc = resultSet.getString(1).trim();
			}

		} catch (Exception e) {
			LoggerUtil.log("error", "Error on method getChosenLocation " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}
		return loc;
	}

	public List getState(Connection con) {

		// List and all variables
		List list = new ArrayList();
		DropDownOption dc = null;

		// Connection variables
		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();

		queryString.append("SELECT DRDL01, DRDL02 FROM f0005 WHERE DRSY='04  ' and DRRT='ST' ");
		// queryString.append("SELECT E6SEQLV, E6SEQSV FROM F570406 WHERE
		// E6KEY='AT_WHSE' AND E6ACTIV = 'Y' ORDER BY E6SEQ");

		LoggerUtil.log("debug", "Query generated in getState: " + queryString);

		try {
			statement = con.createStatement();
			resultSet = statement.executeQuery(queryString.toString());
			String key = "";
			String val = "";

			while (resultSet.next()) {
				val = resultSet.getString(1).trim();
				key = resultSet.getString(2).trim();
				dc = new DropDownOption();

				if (val != null && key != null) {
					if (val.trim().equalsIgnoreCase(".") || key.trim().equalsIgnoreCase("."))
						continue;

					dc.setKeyValue(resultSet.getString(2));
					dc.setDispValue(resultSet.getString(1));
					list.add(dc);
				}
			}

		} catch (Exception e) {
			LoggerUtil.log("error", "Error on method getState " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}
		return list;
	}

	public List getAnalysts(Connection con) {
		List list = new ArrayList();

		// Connection variables
		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();

		queryString.append("SELECT DISTINCT USNAME FROM F560212");

		LoggerUtil.log("debug", "Query generated in getAnalysts: " + queryString);

		try {
			statement = con.createStatement();
			resultSet = statement.executeQuery(queryString.toString());

			while (resultSet.next()) {
				list.add(resultSet.getString("USNAME"));
			}

		} catch (Exception e) {
			LoggerUtil.log("error", "Error on method getAnalysts: " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public MasterTable getTicketDetails(Connection con, Integer controlNo) {

		// List and all variables
		MasterTable mbean = new MasterTable();

		// Connection variables
		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();

		queryString
				.append("Select CONTROL_NO, PO_NUMBER, ANALYST, OBSERVE_DTE, STATUS, SHIP_LOC, PRO_NUMBER, FOLUP_EML_DTE, VENDOR_RES_DTE, GOOD_LOAD, CARRIER FROM IM0160 WHERE control_no = "
						+ controlNo);

		LoggerUtil.log("debug", "Query generated in getTicketDetails: " + queryString);

		try {
			statement = con.createStatement();
			resultSet = statement.executeQuery(queryString.toString());

			while (resultSet.next()) {
				mbean = new MasterTable();
				mbean.setControlNo(resultSet.getInt("CONTROL_NO"));
				mbean.setPoNumber(resultSet.getString("PO_NUMBER"));
				mbean.setAnalyst(resultSet.getString("ANALYST"));
				mbean.setObserveDte(resultSet.getBigDecimal("OBSERVE_DTE"));
				mbean.setStatus(resultSet.getString("STATUS"));
				mbean.setShipLoc(resultSet.getString("SHIP_LOC"));
				mbean.setProNumber(resultSet.getString("PRO_NUMBER"));
				mbean.setFollowupDate(resultSet.getBigDecimal("FOLUP_EML_DTE"));
				mbean.setResponseDate(resultSet.getBigDecimal("VENDOR_RES_DTE"));
				mbean.setGoodLoad(resultSet.getString("GOOD_LOAD"));
				mbean.setCarrier(resultSet.getInt("CARRIER"));
			}

		} catch (Exception e) {
			LoggerUtil.log("error", "Error on method getTicketDetails " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}
		return mbean;
	}

	public int createTicket(MasterTable mbean, Connection con) {
		PreparedStatement pstmt = null;
		int status = 0;
		try {
			String queryString = "INSERT INTO IM0160(CONTROL_NO,PO_NUMBER, ANALYST,OBSERVE_DTE,STATUS,SHIP_LOC,PRO_NUMBER,FOLUP_EML_DTE, VENDOR_RES_DTE, GOOD_LOAD, CARRIER, CRTUSER,CRTPGM, TS_CRTE, UPDUSER,UPDPGM,TS_UPD)  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(queryString);

			pstmt.setInt(1, mbean.getControlNo());
			pstmt.setString(2, mbean.getPoNumber());
			pstmt.setString(3, mbean.getAnalyst());
			pstmt.setBigDecimal(4, mbean.getObserveDte());
			pstmt.setString(5, mbean.getStatus());
			pstmt.setString(6, mbean.getShipLoc());
			pstmt.setString(7, mbean.getProNumber());
			pstmt.setBigDecimal(8,mbean.getFollowupDate());
			pstmt.setBigDecimal(9,mbean.getResponseDate());
			pstmt.setString(10,mbean.getGoodLoad());
			pstmt.setInt(11, mbean.getCarrier());
			pstmt.setString(12, mbean.getCrtUser());
			pstmt.setString(13, mbean.getCrtPgm());
			pstmt.setTimestamp(14, mbean.getTsCrte());
			pstmt.setString(15, mbean.getUpdUser());
			pstmt.setString(16, mbean.getUpdPgm());
			pstmt.setTimestamp(17, mbean.getTsUpd());

			LoggerUtil.log("debug", "SQL IS:" + queryString);
			status = pstmt.executeUpdate();
			if (status == 1) {
				LoggerUtil.log("debug",
						"INSERTED A RECORD SUCCESSFULLY IN TABLE IM0160 with id: " + mbean.getControlNo());
			}
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at createTicket in TicketDao" + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
		return status;

	}

	public int createIssue(IssueTable mbean, Connection con) {
		PreparedStatement pstmt = null;
		int status = 0;
		try {
			String queryString = "INSERT INTO IM0162(CONTROL_NO,ISSUE_TYPE,ISSUE_COMMENT,CRTUSER,CRTPGM,TS_CRTE,UPDUSER,UPDPGM,TS_UPD) VALUES (?,?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(queryString);

			pstmt.setInt(1, mbean.getControlNo());
			pstmt.setInt(2, mbean.getIssueType());
			pstmt.setString(3, mbean.getIssueComment());
			pstmt.setString(8, mbean.getCrtUser());
			pstmt.setString(9, mbean.getCrtPgm());
			pstmt.setTimestamp(10, mbean.getTsCrte());
			pstmt.setString(11, mbean.getUpdUser());
			pstmt.setString(12, mbean.getUpdPgm());
			pstmt.setTimestamp(13, mbean.getTsUpd());

			LoggerUtil.log("debug", "SQL IS:" + queryString);
			status = pstmt.executeUpdate();
			if (status == 1) {
				LoggerUtil.log("debug",
						"INSERTED A RECORD SUCCESSFULLY IN TABLE IM0162 with control no: " + mbean.getControlNo());
			}
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at createIssue in TicketDao" + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
		return status;

	}

	public String getSupplierEmail(Connection con, String suppliercode) {
		String supplierEmail = "";
		Statement stmt = null;
		ResultSet rset = null;
		String sql = "SELECT EMAIL_CONTACT AS EMAIL \n" + " FROM IM0161 WHERE SUPPLIER='" + suppliercode + "'";
		BoUtils.writeTrace(this.getClass() + " getSupplierEmail " + sql);
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(sql);
			while (rset.next()) {
				supplierEmail = rset.getString(1).trim();
			}
			rset.close();
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at TicketDao.getSupplierEmail " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(rset);
			DBUtil.close(stmt);
		}
		return supplierEmail;
	}
	
	public String getXdoc(Connection con, String suppliercode) {
		String xdock = "";
		Statement stmt = null;
		ResultSet rset = null;
		String sql = "SELECT XDOCK AS xdock \n" + " FROM IM0161 WHERE SUPPLIER='" + suppliercode + "'";
		BoUtils.writeTrace(this.getClass() + " getXdoc " + sql);
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(sql);
			while (rset.next()) {
				xdock = rset.getString(1).trim();
			}
			rset.close();
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at TicketDao.getXdoc " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(rset);
			DBUtil.close(stmt);
		}
		return xdock;
	}
	
	public String getFreightTerms(Connection con, String suppliercode) {
		String xdock = "";
		Statement stmt = null;
		ResultSet rset = null;
		String sql = "SELECT FREIGHT_TERMS AS freightterms \n" + " FROM IM0161 WHERE SUPPLIER='" + suppliercode + "'";
		BoUtils.writeTrace(this.getClass() + " getXdoc " + sql);
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(sql);
			while (rset.next()) {
				xdock = rset.getString(1).trim();
			}
			rset.close();
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at TicketDao.getFreightTerms " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(rset);
			DBUtil.close(stmt);
		}
		return xdock;
	}
	

	// Query to get all the issues
	public Map getIssues(Connection con) {
		Map map = new HashMap();
		String key = "";
		String val = "";
		Statement stmt = null;
		ResultSet rset = null;
		String sql = "SELECT E6SEQSV,E6SEQLV FROM F570406 WHERE E6KEY='AT_ISSUES' AND E6ACTIV='Y'"; // Will
																									// return
																									// 5-6
																									// menus
																									// items;
		BoUtils.writeTrace(this.getClass() + " getIssues " + sql);
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(sql);
			while (rset.next()) {
				key = rset.getString(1).trim();
				val = rset.getString(2).trim();
				map.put(key, val);
			}
			rset.close();
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at TicketDao.getIssues " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(rset);
			DBUtil.close(stmt);
		}
		return map;

	}

	/*
	 * Returns all the subissues for a specific issue
	 */
	public List getSubIssues(Connection con, String seq) {
		List list = new ArrayList();
		Statement stmt = null;
		ResultSet rset = null;
		String sql = "SELECT E6SEQLV FROM F570406 WHERE E6KEY='AT_ISSSUB' AND E6ACTIV='Y' AND E6SEQSV='" + seq + "'"; // Will
																														// return
																														// sub
																														// issues
		BoUtils.writeTrace(this.getClass() + " getSubIssues " + sql);
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(sql);
			while (rset.next()) {
				list.add(rset.getString(1).trim());
			}
			rset.close();
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at TicketDao.getSubIssues " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(rset);
			DBUtil.close(stmt);
		}
		return list;

	}

	public int getSubIssueSv(Connection con, String seq) {
		String retrn = "";
		Statement stmt = null;
		ResultSet rset = null;
		String sql = "SELECT E6SEQ FROM F570406 WHERE E6KEY='AT_ISSSUB' AND E6ACTIV='Y' AND E6SEQLV='" + seq + "'"; // Will
																													// return
																													// sub
																													// issues
		BoUtils.writeTrace(this.getClass() + " getSubIssueSv " + sql);
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(sql);
			while (rset.next()) {
				retrn = rset.getString(1).trim();
			}
			rset.close();
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at TicketDao.getSubIssueSv " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(rset);
			DBUtil.close(stmt);
		}
		return Integer.parseInt(retrn);

	}

	public int addIssueComment(Connection con, IssueTable issueTable) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int status = 0;
		try {
			String queryString = "INSERT INTO IM0162(CONTROL_NO,ISSUE_TYPE,ISSUE_COMMENT,CRTUSER,CRTPGM,TS_CRTE,UPDUSER,UPDPGM,TS_UPD) VALUES (?,?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(queryString);

			pstmt.setInt(1, issueTable.getControlNo());
			pstmt.setInt(2, issueTable.getIssueType());
			pstmt.setString(3, issueTable.getIssueComment());
			pstmt.setString(4, issueTable.getCrtUser());
			pstmt.setString(5, issueTable.getCrtPgm());
			pstmt.setTimestamp(6, issueTable.getTsCrte());
			pstmt.setString(7, issueTable.getUpdUser());
			pstmt.setString(8, issueTable.getUpdPgm());
			pstmt.setTimestamp(9, issueTable.getTsUpd());

			LoggerUtil.log("debug", "SQL IS:" + queryString);
			status = pstmt.executeUpdate();
			if (status == 1) {
				LoggerUtil.log("debug",
						"INSERTED A RECORD SUCCESSFULLY IN TABLE IM0162 with id: " + issueTable.getControlNo());
			}
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at addIssueComment in TicketDao" + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
		return status;
	}

	public int addIssueType(Connection con, IssueDetailsTable issueDetailsTable) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int status = 0;
		try {
			String queryString = "INSERT INTO IM0164(CONTROL_NO,ISSUE_TYPE,ISSUE_SUBTYPE,CRTUSER,CRTPGM,TS_CRTE,UPDUSER,UPDPGM,TS_UPD) VALUES (?,?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(queryString);

			pstmt.setInt(1, issueDetailsTable.getControlNo());
			pstmt.setInt(2, issueDetailsTable.getIssueType());
			pstmt.setInt(3, issueDetailsTable.getIssueSubType());
			pstmt.setString(4, issueDetailsTable.getCrtUser());
			pstmt.setString(5, issueDetailsTable.getCrtPgm());
			pstmt.setTimestamp(6, issueDetailsTable.getTsCrte());
			pstmt.setString(7, issueDetailsTable.getUpdUser());
			pstmt.setString(8, issueDetailsTable.getUpdPgm());
			pstmt.setTimestamp(9, issueDetailsTable.getTsUpd());

			LoggerUtil.log("debug", "SQL IS:" + queryString);
			status = pstmt.executeUpdate();
			if (status == 1) {
				LoggerUtil.log("debug",
						"INSERTED A RECORD SUCCESSFULLY IN TABLE IM0164 with id: " + issueDetailsTable.getControlNo());
			}
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at addIssueType in TicketDao" + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
		return status;

	}

	/*
	 * Returns sub issue for a certain issue
	 */
	public String getSubForIssues(Connection con, Integer controlNo, Integer issue) {
		String ret = "";
		Statement stmt = null;
		ResultSet rset = null;

		// String sql = "select E6SEQLV FROM F570406 WHERE E6KEY='AT_ISSSUB' AND
		// E6SEQ IN " +
		// "(SELECT ISSUE_SUBTYPE FROM IM0164 WHERE CONTROL_NO ="+controlNo +
		// " AND ISSUE_TYPE =" + issue + ") AND E6SEQSV = " + issue;

		String sql = "SELECT A.E6SEQLV FROM F570406 A, IM0164 B " + "WHERE B.CONTROL_NO = " + controlNo
				+ " AND B.ISSUE_TYPE = " + issue + " and a.e6seqsv =  SUBSTR(DIGITS(ISSUE_TYPE),9)"
				+ " AND E6SEQLV LIKE '%' || SUBSTR(DIGITS( ISSUE_SUBTYPE),9) || '%'";

		BoUtils.writeTrace(this.getClass() + " getSubForIssues " + sql);
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(sql);
			while (rset.next()) {
				ret = ret + ", " + rset.getString(1).trim();
			}
			ret = ret.replaceFirst("^,", "");
			rset.close();
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at TicketDao.getSubForIssues " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(rset);
			DBUtil.close(stmt);
		}
		return ret;

	}

	public String getCommentForIssues(Connection con, Integer controlNo, Integer issue) {
		String ret = "";
		Statement stmt = null;
		ResultSet rset = null;
		String sql = "select Issue_Comment from IM0162 WHERE CONTROL_NO= " + controlNo + " AND ISSUE_TYPE =" + issue;
		BoUtils.writeTrace(this.getClass() + " getCommentForIssues " + sql);
		if (issue != 0) {
			try {
				stmt = con.createStatement();
				rset = stmt.executeQuery(sql);
				while (rset.next()) {
					ret = ret + ", " + rset.getString(1).trim();
				}
				ret = ret.replaceFirst("^,", "");
				rset.close();
			} catch (Exception e) {
				LoggerUtil.log("error", "Error at TicketDao.getCommentForIssues " + e.toString());
				e.printStackTrace();
			} finally {
				DBUtil.close(rset);
				DBUtil.close(stmt);
			}
		}
		return ret;

	}

	public List getOpenTickets(String userId) {
		List ret = new ArrayList();
		Statement stmt = null;
		ResultSet rset = null;
		String sql = " SELECT CONTROL_NO, PO_NUMBER, ANALYST, TS_CRTE FROM IM0160 WHERE UPDUSER='"
				+ userId.toUpperCase() + "'"
				// "' AND CONTROL_NO NOT IN (SELECT CONTROL_NO FROM IM0165 WHERE
				// UPD_USER='"+ userId.toUpperCase() +"') "
				+ " order by CONTROL_NO DESC";

		BoUtils.writeTrace(this.getClass() + " getOpenTickets " + sql);

		Connection con = null;

		try {
			con = AS400Utils.getAS400DBConnection();
			stmt = con.createStatement();
			rset = stmt.executeQuery(sql);
			MasterTable mbean = new MasterTable();
			while (rset.next()) {
				mbean = new MasterTable();
				mbean.setAnalyst(rset.getString("ANALYST"));
				mbean.setControlNo(rset.getInt("CONTROL_NO"));
				mbean.setPoNumber(rset.getString("PO_NUMBER"));
				mbean.setTsCrte(rset.getTimestamp("TS_CRTE"));
				ret.add(mbean);
			}
			rset.close();
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at TicketDao.getOpenTickets " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(rset);
			DBUtil.close(stmt);
			DBUtil.close(con);
		}

		return ret;

	}

	@SuppressWarnings("unchecked")
	public List getEmails(Connection con, Integer controlNo) {

		// List and all variables
		EmailTable conversations = new EmailTable();
		List emailList = new ArrayList();

		// Connection variables
		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();

		queryString
				.append("Select CONTROL_NO, EMAIL_ID FROM IM0165 WHERE control_no = " + controlNo + " ORDER BY TS_UPD");

		LoggerUtil.log("debug", "Query generated in getEmails: " + queryString);

		try {
			statement = con.createStatement();
			resultSet = statement.executeQuery(queryString.toString());

			while (resultSet.next()) {
				conversations = new EmailTable();
				conversations.setControlNo(resultSet.getInt("CONTROL_NO"));
				conversations.setEmailId(resultSet.getString("EMAIL_ID"));
				emailList.add(conversations);
			}

		} catch (Exception e) {
			LoggerUtil.log("error", "Error on method getEmails " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}
		return emailList;
	}

	@SuppressWarnings("unchecked")
	public List getAttachments(Connection con, Integer controlNo) {

		// List and all variables
		AttachmentTable attachments = new AttachmentTable();
		List attachmentList = new ArrayList();

		// Connection variables
		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();

		queryString.append("Select CONTROL_NO, LINE_NO FROM IM0163 WHERE control_no = " + controlNo);

		LoggerUtil.log("debug", "Query generated in getAttachments: " + queryString);

		try {
			statement = con.createStatement();
			resultSet = statement.executeQuery(queryString.toString());

			while (resultSet.next()) {
				attachments = new AttachmentTable();
				attachments.setControlNo(resultSet.getInt("CONTROL_NO"));
				attachments.setLineNo(resultSet.getInt("LINE_NO"));
				attachmentList.add(attachments);
			}

		} catch (Exception e) {
			LoggerUtil.log("error", "Error on method getAttachments " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}
		return attachmentList;
	}

	public EmailTable getEmailDetail(Connection connection, Integer controlNo, String emailId) {
		EmailTable conversation = new EmailTable();

		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();

		queryString
				.append("SELECT CONTROL_NO, EMAIL_ID, TO_EMAIL, SUBJECT, MESSAGE, UPD_USER, UPD_PGM, TS_CRTE, TS_UPD FROM IM0165 WHERE CONTROL_NO ="
						+ controlNo + " AND EMAIL_ID=" + emailId);

		LoggerUtil.log("debug", "Query generated in getEmailDetail: " + queryString);

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(queryString.toString());

			while (resultSet.next()) {
				conversation = new EmailTable();

				conversation.setControlNo(controlNo);
				conversation.setEmailId(emailId);
				conversation.setSubject(resultSet.getString("SUBJECT"));
				conversation.setMessage(resultSet.getString("MESSAGE"));
				conversation.setEmailId(resultSet.getString("EMAIL_ID"));
				conversation.setToEmail(resultSet.getString("TO_EMAIL"));
				conversation.setTsCrte(resultSet.getTimestamp("TS_CRTE"));

			}

		} catch (Exception e) {
			LoggerUtil.log("error", "Error on method getAttachments " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}

		return conversation;
	}

	public int addEmail(Connection connection, EmailTable emailbean) {

		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int status = 0;
		try {
			String queryString = "INSERT INTO IM0165(CONTROL_NO,EMAIL_ID,TO_EMAIL,SUBJECT,MESSAGE,UPD_USER,UPD_PGM,TS_CRTE,TS_UPD) VALUES (?,?,?,?,?,?,?,?,?)";
			pstmt = connection.prepareStatement(queryString);

			pstmt.setInt(1, emailbean.getControlNo());
			pstmt.setInt(2, Integer.parseInt(emailbean.getEmailId()));
			pstmt.setString(3, emailbean.getToEmail());
			pstmt.setString(4, emailbean.getSubject());
			pstmt.setString(5, emailbean.getMessage());
			pstmt.setString(6, emailbean.getUpdUser());
			pstmt.setString(7, emailbean.getUpdPgm());
			pstmt.setTimestamp(8, emailbean.getTsCrte());
			pstmt.setTimestamp(9, emailbean.getTsUpd());

			LoggerUtil.log("debug", "SQL IS:" + queryString);
			status = pstmt.executeUpdate();
			if (status == 1) {
				LoggerUtil.log("debug",
						"INSERTED A RECORD SUCCESSFULLY IN TABLE IM0165 with id: " + emailbean.getControlNo());
			}
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at addIssueComment in TicketDao" + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
		return status;
	}

	public Integer getEmailId(Connection connection, Integer controlNo) {

		// List and all variables
		int emailId = 0;

		// Connection variables
		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();

		queryString.append("Select MAX(EMAIL_ID) FROM IM0165 WHERE CONTROL_NO=" + controlNo);

		LoggerUtil.log("debug", "Query generated in getEmailId: " + queryString);

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(queryString.toString());

			while (resultSet.next()) {
				emailId = resultSet.getInt(1);
				emailId++;
			}

			if (emailId == 0)
				emailId = 1;

		} catch (Exception e) {
			LoggerUtil.log("error", "Error on method getEmailId " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}
		return emailId;
	}

	public int updateTicket(MasterTable mbean, Connection con) {

		PreparedStatement pstmt = null;
		int status = 0;
		int flag = 0;
		try {
			String queryString = "UPDATE IM0160 SET ANALYST=?, OBSERVE_DTE=?, STATUS=?, "
					+ " SHIP_LOC=?, PRO_NUMBER=?, GOOD_LOAD=?, FOLUP_EML_DTE=?, VENDOR_RES_DTE=?, ";
					if(mbean.getCarrier()!=null && mbean.getCarrier()>0){
					queryString = queryString + "CARRIER = ?, ";
					flag=1;
					}
					queryString = queryString + "UPDUSER=?, UPDPGM=?, TS_UPD=? " + " WHERE CONTROL_NO=?";

			pstmt = con.prepareStatement(queryString);

			// pstmt.setInt(1, mbean.getControlNo());
			// pstmt.setString(2, mbean.getPoNumber());
			pstmt.setString(1, mbean.getAnalyst());
			pstmt.setBigDecimal(2, mbean.getObserveDte());
			pstmt.setString(3, mbean.getStatus());
			pstmt.setString(4, mbean.getShipLoc());
			pstmt.setString(5, mbean.getProNumber());
			pstmt.setString(6, mbean.getGoodLoad());
			pstmt.setBigDecimal(7, mbean.getFollowupDate());
			pstmt.setBigDecimal(8, mbean.getResponseDate());
			if(flag==1){
			pstmt.setInt(9, mbean.getCarrier());
			pstmt.setString(10, mbean.getUpdUser());
			pstmt.setString(11, mbean.getUpdPgm());
			pstmt.setTimestamp(12, mbean.getTsUpd());
			pstmt.setInt(13, mbean.getControlNo());
			}
			else{
				pstmt.setString(9, mbean.getUpdUser());
				pstmt.setString(10, mbean.getUpdPgm());
				pstmt.setTimestamp(11, mbean.getTsUpd());
				pstmt.setInt(12, mbean.getControlNo());
			}

			LoggerUtil.log("debug", "SQL IS:" + queryString);
			status = pstmt.executeUpdate();
			if (status == 1) {
				LoggerUtil.log("debug",
						"UPDATED A RECORD SUCCESSFULLY IN TABLE IM0160 with id: " + mbean.getControlNo());
			}
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at updateTicket in TicketDao" + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
		return status;

	}

	public int updateIssueComment(Connection con, IssueTable issueTable) {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		int status = 0;
		try {
			String deleteString = "DELETE FROM IM0162 WHERE CONTROL_NO=" + issueTable.getControlNo()
					+ " AND ISSUE_TYPE=" + issueTable.getIssueType();

			stmt = con.createStatement();
			stmt.executeUpdate(deleteString);

			status = addIssueComment(con, issueTable);
			if (status == 1) {
				LoggerUtil.log("debug",
						"UPDATED A RECORD SUCCESSFULLY IN TABLE IM0162 with id: " + issueTable.getControlNo());
			}
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at addIssueComment in TicketDao" + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
		return status;
	}

	public int updateIssueType(Connection con, IssueDetailsTable issueDetailsTable) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		Statement stmt = null;
		int status = 0;
		try {
			status = addIssueType(con, issueDetailsTable);
			if (status == 1) {
				LoggerUtil.log("debug",
						"UPDATED A RECORD SUCCESSFULLY IN TABLE IM0164 with id: " + issueDetailsTable.getControlNo());
			}
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at updateIssueType in TicketDao" + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
			DBUtil.close(stmt);
		}
		return status;

	}

	public void deleteIssueType(Connection con, IssueDetailsTable issueDetailsTable) {

		Statement stmt = null;

		String deleteString = "DELETE FROM IM0164 WHERE CONTROL_NO=" + issueDetailsTable.getControlNo()
				+ " AND ISSUE_TYPE=" + issueDetailsTable.getIssueType();

		try {
			stmt = con.createStatement();
			stmt.executeUpdate(deleteString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LoggerUtil.log("error", "Error at TicketDao.deleteIssueType " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(stmt);
		}

	}

	public int getLineNo(Connection connection, Integer controlNo) {

		// List and all variables
		int lineNo = 0;

		// Connection variables
		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();

		queryString.append("Select MAX(LINE_NO) FROM IM0163 WHERE CONTROL_NO=" + controlNo);

		LoggerUtil.log("debug", "Query generated in getLineNo: " + queryString);

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(queryString.toString());

			while (resultSet.next()) {
				lineNo = resultSet.getInt(1);
				lineNo++;
			}

			if (lineNo == 0)
				lineNo = 1;

		} catch (Exception e) {
			LoggerUtil.log("error", "Error on method getLineNo " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}
		return lineNo;
	}

	public List getFilesList(Connection connection, Integer controlNo) {

		// List and all variables
		int lineNo = 0;
		String fileName = "";
		List fileList = new ArrayList();

		// Connection variables
		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();

		queryString.append("Select LINE_NO FROM IM0163 WHERE CONTROL_NO=" + controlNo);

		LoggerUtil.log("debug", "Query generated in getLineNo: " + queryString);

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(queryString.toString());

			while (resultSet.next()) {
				lineNo = resultSet.getInt(1);
				fileName = UPLOAD_DIRECTORY + File.separator + "Image_" + controlNo + "_" + lineNo;
				fileList.add(fileName);
			}

			if (lineNo == 0)
				lineNo = 1;

		} catch (Exception e) {
			LoggerUtil.log("error", "Error on method getLineNo " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}
		return fileList;
	}

	public List getStatus(Connection con) {

		// List and all variables
		List list = new ArrayList();
		DropDownOption dc = null;

		// Connection variables
		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();

		// queryString.append("SELECT DRDL01, DRDL02 FROM f0005 WHERE DRSY='04 '
		// and DRRT='ST' ");
		queryString.append(
				"SELECT E6SEQLV, E6SEQSV FROM F570406 WHERE E6KEY='AT_STATUS' AND E6ACTIV = 'Y' ORDER BY E6SEQ");

		LoggerUtil.log("debug", "Query generated in getStatus: " + queryString);

		try {
			statement = con.createStatement();
			resultSet = statement.executeQuery(queryString.toString());
			String key = "";
			String val = "";

			while (resultSet.next()) {
				val = resultSet.getString(1).trim();
				key = resultSet.getString(2).trim();
				dc = new DropDownOption();

				if (val != null && key != null) {
					if (val.trim().equalsIgnoreCase(".") || key.trim().equalsIgnoreCase("."))
						continue;

					dc.setKeyValue(resultSet.getString(2).trim());
					dc.setDispValue(resultSet.getString(1).trim());
					list.add(dc);
				}
			}

		} catch (Exception e) {
			LoggerUtil.log("error", "Error on method getStatus " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}
		return list;
	}
	
	public String getSupplierException(Connection con, String suppliercode) {
		String supplierException = "";
		Statement stmt = null;
		ResultSet rset = null;
		String sql = "SELECT SUPPL_EXCEPT AS supplierexception \n" + " FROM IM0161 WHERE SUPPLIER='" + suppliercode + "'";
		BoUtils.writeTrace(this.getClass() + " getSupplierException " + sql);
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(sql);
			while (rset.next()) {
				supplierException = rset.getString(1).trim();
			}
			rset.close();
		} catch (Exception e) {
			LoggerUtil.log("error", "Error at TicketDao.getSupplierException " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(rset);
			DBUtil.close(stmt);
		}
		return supplierException;
	}

	public List getCarrier(Connection con) {

		// List and all variables
		List list = new ArrayList();
		DropDownOption dc = null;

		// Connection variables
		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();

		// queryString.append("SELECT DRDL01, DRDL02 FROM f0005 WHERE DRSY='04 '
		// and DRRT='ST' ");
		queryString
				.append("SELECT E6SEQLV, E6CODE FROM F570406 WHERE E6KEY='AT_CARRIER' AND E6ACTIV = 'Y' ORDER BY E6SEQ");

		LoggerUtil.log("debug", "Query generated in getCarrier: " + queryString);

		try {
			statement = con.createStatement();
			resultSet = statement.executeQuery(queryString.toString());
			String key = "";
			String val = "";

			while (resultSet.next()) {
				val = resultSet.getString(1).trim();
				key = resultSet.getString(2).trim();
				dc = new DropDownOption();

				if (val != null && key != null) {
					dc.setKeyValue(resultSet.getString(2));
					dc.setDispValue(resultSet.getString(1));
					list.add(dc);
				}
			}

		} catch (Exception e) {
			LoggerUtil.log("error", "Error on method getCarrier " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}
		return list;
	}
	
	public String getCarrierName(Connection con, Integer carrier) {

		String carriername ="";
		// Connection variables
		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();

		// queryString.append("SELECT DRDL01, DRDL02 FROM f0005 WHERE DRSY='04 '
		// and DRRT='ST' ");
		queryString
				.append("SELECT E6SEQLV FROM F570406 WHERE E6KEY='AT_CARRIER' AND E6ACTIV = 'Y' AND E6CODE="
						+ carrier
						+ " ORDER BY E6SEQ");

		LoggerUtil.log("debug", "Query generated in getCarrierName: " + queryString);

		try {
			statement = con.createStatement();
			resultSet = statement.executeQuery(queryString.toString());

			while (resultSet.next()) {
				carriername = resultSet.getString(1).trim();
			}

		} catch (Exception e) {
			LoggerUtil.log("error", "Error on method getCarrier " + e.toString());
			e.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}
		return carriername;
	}
	
	public String getFromEmail(Connection con) {

		// List and all variables
		String from = "";
		
		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("SELECT E6SEQLV FROM F570406 WHERE E6KEY='"
						+ "AT_FROMEML" + "'AND E6ACTIV='Y' ORDER BY E6SEQ");

		try {
			statement = con.createStatement();
			resultSet = statement.executeQuery(queryString.toString());

			while (resultSet.next()) {
				from = resultSet.getString("E6SEQLV");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}
				return from;
	}
	
	public String checkcc(Connection con) {

		// List and all variables
		String cc = "";
		
		Statement statement = null;
		ResultSet resultSet = null;
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("SELECT E6SEQLV FROM F570406 WHERE E6KEY='"
						+ "AT_CCANEML" + "'AND E6ACTIV='Y' ORDER BY E6SEQ");

		try {
			statement = con.createStatement();
			resultSet = statement.executeQuery(queryString.toString());

			while (resultSet.next()) {
				cc = resultSet.getString("E6SEQLV");
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			DBUtil.close(resultSet);
			DBUtil.close(statement);
		}
				return cc;
	}
	
	
}
