package com.schein.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.ibm.ws.batch.xJCL.beans.listener;
import com.schein.bean.User;
import com.schein.bean.Whse;

public class FileUtils {
	final static String UPLOAD_DIRECTORY = "E:\\VendorAudit\\upload\\";

	@SuppressWarnings("unchecked")
	public static List getAllPhotos(Connection con, Integer controlNo) {
		try {
			String osName = System.getProperty("os.name");
			String photoFormat;
			String fileName="";
			String description;
			ResultSet rs = null;

			//Get all file names from IM0163 for control no
			Statement stmt = null;
			ResultSet rset = null;
			String temp = "";
			List list = new ArrayList();

			try {
				con = AS400Utils.getAS400DBConnection();
				stmt = con.createStatement();
				rset = stmt
						.executeQuery("SELECT LINE_NO FROM IM0163 WHERE CONTROL_NO = "+controlNo);
				while (rset.next()) {
					temp = "";
					int val = rset.getInt("LINE_NO");
					temp = temp + String.valueOf(val);
					temp =  UPLOAD_DIRECTORY + File.separator + "Image_"+controlNo + "_" + temp;
					list.add(temp);
				}
			} catch (Exception e) {
				LoggerUtil.log("debug", "Error at getAllPhotos" + e.toString());
			} finally {
				try {
					DBUtil.close(con, stmt, rset);
				} catch (Exception ee) {
				}
			}
			
			LoggerUtil.log("debug", "  READ FROM BLOB FILE SUCCESSFULLY!");
			return list;
		} catch (Exception e) {
			LoggerUtil.log("debug", "ex " + e);
			return new ArrayList();
		}
	}
	
	public static String getPhoto(Connection con, Integer controlNo, Integer lineNo) {
		try {
			String osName = System.getProperty("os.name");
			String photoFormat;
			String fileName="";

			//Generate filepath	
			fileName = controlNo + String.valueOf(lineNo);
			fileName = UPLOAD_DIRECTORY + fileName;
			return fileName;
		} catch (Exception e) {
			LoggerUtil.log("debug", "ex " + e);
			return "Not Found";
		}
	}

	public static int uploadPhoto(Connection con, Integer controlNo, Integer lineNo, User curUser) throws SQLException, IOException {
		PreparedStatement pstmt = null;
	    int status = 0;
	    try {
	      String queryString = "INSERT INTO IM0163(CONTROL_NO,LINE_NO,CRTUSER,CRTPGM,TS_CRTE,UPDUSER,UPDPGM,TS_UPD) VALUES (?,?,?,?,?,?,?,?)";
	      pstmt = con.prepareStatement(queryString);

	      pstmt.setInt(1, controlNo);
	      pstmt.setInt(2, lineNo);
	      pstmt.setString(3, curUser.getUserId());
	      pstmt.setString(4, "Audittool");
	      pstmt.setTimestamp(5, DateTimeUtil.getCurrentTimeStamp());
	      pstmt.setString(6, curUser.getUserId());
	      pstmt.setString(7, "Audittool");
	      pstmt.setTimestamp(8, DateTimeUtil.getCurrentTimeStamp());

	      LoggerUtil.log("debug", "SQL IS:" + queryString);
	      status = pstmt.executeUpdate();
	      if (status == 1) {
	        LoggerUtil.log("debug", "INSERTED A RECORD SUCCESSFULLY IN TABLE IM0162 with control no: "
	            + controlNo + " and line no: " + lineNo);
	      }
	    } catch (Exception e) {
	      LoggerUtil.log("debug", "Error at uploadPhoto in FileUtils" + e.toString());
	      e.printStackTrace();
	    } finally {
	      DBUtil.close(pstmt);
	    }
	    return status;
	}
	
	public static String removePhoto(Connection con, Integer controlNo, Integer lineNo)  {
		ResultSet rs = null;
		PreparedStatement pstmt2 = null;
		String ret="";
		long key = -1L;
		try {
			LoggerUtil.log("debug", "  update BLOB FILE TO DB SUCCESSFULLY!");
		} finally {
			try {
				pstmt2.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// pstmt.close();
			
		}
		return "Success";
	}
	
	public static String removeAllPhotos(Connection con, Integer controlNo)  {
		ResultSet rs = null;
		PreparedStatement pstmt2 = null;
		String ret="";
		long key = -1L;
		try {

			LoggerUtil.log("debug", "  update BLOB FILE TO DB SUCCESSFULLY!");
		} finally {
			try {
				pstmt2.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// pstmt.close();
			
		}
		return "Success";
	}

}
