package com.pabitra.online_shopping.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pabitra.online_shopping.dao.ProductsTypeDao;
import com.pabitra.online_shopping.entity.ProductsTypeEntity;
import com.pabitra.online_shopping.utility.MyDB;

public class ProductsTypeDaoImpl implements ProductsTypeDao{
	
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
	
	// << Loading all products in the Manage-Product.jsp dropdown >>
	@Override
	public ArrayList<ProductsTypeEntity> getProductTypes() {
		ArrayList<ProductsTypeEntity> productTypes = new ArrayList<ProductsTypeEntity>();
		Connection con;
		PreparedStatement ps;
		try {
			con = DriverManager.getConnection(url, uname, dbpwd);
			ps = con.prepareStatement("SELECT * FROM PRODUCT_TYPE WHERE ACTIVE = 1");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ProductsTypeEntity entity = new ProductsTypeEntity();
				entity.setId(rs.getInt(1));
				entity.setName(rs.getString(2));
				productTypes.add(entity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productTypes;
	}
	// << /End of Loading all products in the Manage-Product.jsp dropdown >>
}
