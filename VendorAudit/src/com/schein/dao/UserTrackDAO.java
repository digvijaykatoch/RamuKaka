package com.schein.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import com.schein.utils.AS400Utils;
import com.schein.utils.DBUtil;
import com.schein.utils.DateTimeUtil;
import com.schein.utils.LoggerUtil;
 

/**
 * @author Dilip Nair
 * @return void
 * @param userId and Menu Description
 * This DAo is will log the menu access for every user for auditing and usaage of reports.
 */

public class UserTrackDAO {
	
	public void  trackUser(String userId,String menuId){
		String query = "INSERT INTO IM0085 (USER_ID, MENU_ID, LAST_USED, "+ 
        "  UPD_PGM, TS_CRTE) VALUES(?, ?, ?, ?, ?)  ";
										
		PreparedStatement	pstmt = null;
		Connection con = null;
		Timestamp holder = DateTimeUtil.getCurrentTimeStamp();
		try{
			con = AS400Utils.getAS400DBConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1,userId.toUpperCase());
			pstmt.setString(2,menuId);
			pstmt.setTimestamp(3,holder); 
			pstmt.setString(4,"UserTrackDAO");
	 		pstmt.setTimestamp(5,holder);
		    pstmt.executeUpdate();
			
		}catch(Exception e){
			LoggerUtil.log("error", "Error at UserTrackDAO.trackUser method " + e.toString());
			e.printStackTrace();
		}finally{
			DBUtil.close(con);
			DBUtil.close(pstmt);
		}
		
	}

}