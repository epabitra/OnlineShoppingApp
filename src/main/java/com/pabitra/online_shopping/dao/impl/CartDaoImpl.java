package com.pabitra.online_shopping.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pabitra.online_shopping.dao.CartDao;
import com.pabitra.online_shopping.entity.CartEntity;
import com.pabitra.online_shopping.utility.MyDB;

public class CartDaoImpl implements CartDao {

	private String url = MyDB.url;
	private String uname = MyDB.uname;
	private String dbpwd = MyDB.dbpwd;
	
	static {
		try {
			Class.forName(MyDB.driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	// This method add a product to the cart when ever the customer add
	// a perticular product for the 1st time...
	@Override
	public int addCartProduct(CartEntity entity) {
		Connection con;
		PreparedStatement ps;
		try {
			con = DriverManager.getConnection(url, uname, dbpwd);
			ps = con.prepareStatement("INSERT INTO CART VALUES(CART_ID_SEQ.NEXTVAL, ?, ?, ?, ?, 1)");
			ps.setInt(1, entity.getUserId());
			ps.setInt(2, entity.getProductId());
			ps.setInt(3, entity.getQuantity());
			ps.setDouble(4, entity.getTotalPrice());
			return ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	// This method returns the respective product using UserId and ProductId
	@Override
	public CartEntity getCartProduct(int userId, int productId) {
		Connection con;
		PreparedStatement ps;
		CartEntity entity = new CartEntity();
		entity.setId(0);
		try {
			con = DriverManager.getConnection(url, uname, dbpwd);
			ps = con.prepareStatement("SELECT ID, QUANTITY, TOTAL_PRICE FROM CART WHERE USER_ID = ? AND PRODUCT_ID = ? AND ACTIVE = 1");
			ps.setInt(1, userId);
			ps.setInt(2, productId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				entity.setId(rs.getInt(1));
				entity.setQuantity(rs.getInt(2));
				entity.setTotalPrice(rs.getDouble(3));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return entity;
	}

	// This method return all the active product of the given user
	@Override
	public ArrayList<CartEntity> getAllActiveCartProductsById(int id) {
		Connection con;
		PreparedStatement ps;
		ArrayList<CartEntity> products = new ArrayList<CartEntity>();
		
		try {
			con = DriverManager.getConnection(url, uname, dbpwd);
			ps = con.prepareStatement("SELECT * FROM CART WHERE USER_ID = ? AND ACTIVE = 1");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				CartEntity entity = new CartEntity();
				entity.setId(rs.getInt(1));
				entity.setUserId(rs.getInt(2));
				entity.setProductId(rs.getInt(3));
				entity.setQuantity(rs.getInt(4));
				entity.setTotalPrice(rs.getDouble(5));
				products.add(entity);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	// This method deactivete the product in cart table for the given user
	@Override
	public int deleteCartProduct(int id) {
		Connection con;
		PreparedStatement ps;
		ArrayList<CartEntity> products = new ArrayList<CartEntity>();
		
		try {
			con = DriverManager.getConnection(url, uname, dbpwd);
			ps = con.prepareStatement("UPDATE CART SET ACTIVE = 0 WHERE ID = ?");
			ps.setInt(1, id);
			return ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	// This is for updating the cart every time when a customer
	// add the same product to the cart rather than 1st request...
	@Override
	public int updateCartProduct(CartEntity entity) {
		Connection con;
		PreparedStatement ps;
		try {
			con = DriverManager.getConnection(url, uname, dbpwd);
			
			ps = con.prepareStatement("UPDATE CART SET QUANTITY = ?, TOTAL_PRICE = ? WHERE ID = ?");
			ps.setInt(1, entity.getQuantity());
			ps.setDouble(2, entity.getTotalPrice());
			ps.setInt(3, entity.getId());
			return ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	// This method returns all the active cart products of all users
	@Override
	public ArrayList<CartEntity> getAllActiveCartProducts() {
		Connection con;
		PreparedStatement ps;
		ArrayList<CartEntity> products = new ArrayList<CartEntity>();
		
		try {
			con = DriverManager.getConnection(url, uname, dbpwd);
			ps = con.prepareStatement("SELECT * FROM CART WHERE ACTIVE = 1");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				CartEntity entity = new CartEntity();
				entity.setId(rs.getInt(1));
				entity.setUserId(rs.getInt(2));
				entity.setProductId(rs.getInt(3));
				entity.setQuantity(rs.getInt(4));
				entity.setTotalPrice(rs.getDouble(5));
				products.add(entity);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return products;
	}
}
