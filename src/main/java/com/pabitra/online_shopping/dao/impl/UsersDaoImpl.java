package com.pabitra.online_shopping.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pabitra.online_shopping.dao.UsersDao;
import com.pabitra.online_shopping.entity.UsersEntity;
import com.pabitra.online_shopping.utility.MyDB;

public class UsersDaoImpl implements UsersDao{

	private final String url = MyDB.url;
	private final String uname = MyDB.uname;
	private final String dbpwd = MyDB.dbpwd;
	
	static {
		try {
			Class.forName(MyDB.driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// This method used for save the user information in DB
	public int saveUser(UsersEntity entity) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DriverManager.getConnection(url, uname, dbpwd);
			String query = "INSERT INTO USERS VALUES(USERS_ID_SEQ.NEXTVAL, ?, ?, ?, ?, ?, 2, 1)";
			ps = con.prepareStatement(query);
			ps.setString(1, entity.getFirstName());
			ps.setString(2, entity.getLastName());
			ps.setString(3, entity.getEmail());
			ps.setString(4, entity.getPhone());
			ps.setString(5, entity.getPassword());
			return(ps.executeUpdate());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(con!=null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(ps!=null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return result;
	}
	
	// This method verify the DB for duplicate number
	public boolean isDuplicateUser(String phone) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DriverManager.getConnection(url, uname, dbpwd);
			ps = con.prepareStatement("SELECT * FROM USERS WHERE PHONE = ?");
			ps.setString(1, phone);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) 
				return true;
			else 
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(con!=null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(ps!=null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return true;
	}
	
	// This method used for login Validation
	public UsersEntity verifyUser(String phone, String password) {
		Connection con = null;
		PreparedStatement ps = null;
		UsersEntity user = new UsersEntity();
		try {
			con = DriverManager.getConnection(url, uname, dbpwd);
			ps = con.prepareStatement("SELECT * FROM USERS WHERE PHONE = ? AND PASSWORD = ? AND ACTIVE = 1");
			ps.setString(1, phone);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				user.setUserId(rs.getInt(1));
				user.setFirstName(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setPhone(rs.getString(5));
				user.setRole_id(rs.getInt(7));
				
			}else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(con!=null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(ps!=null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return user;
	}
}
