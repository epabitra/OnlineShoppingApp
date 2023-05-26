package com.pabitra.online_shopping.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pabitra.online_shopping.dao.ProductsDao;
import com.pabitra.online_shopping.entity.ProductsEntity;
import com.pabitra.online_shopping.utility.MyDB;

public class ProductsDaoImpl implements ProductsDao {
	
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

	// Adding Products in the Database
	@Override
	public int addProduct(ProductsEntity entity) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DriverManager.getConnection(url, uname, dbpwd);
			ps = con.prepareStatement("INSERT INTO PRODUCT VALUES(PRODUCT_ID_SEQ.NEXTVAL, ?, ?, ?, ?, 1)");
			ps.setString(1, entity.getProductName());
			ps.setInt(2, entity.getProductTypeId());
			ps.setInt(3, entity.getNoOfStocks());
			ps.setDouble(4, entity.getPrice());
			return ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		return 0;
	}

	// Update the product information
	@Override
	public int updateProduct(ProductsEntity entity) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DriverManager.getConnection(url, uname, dbpwd);
			ps = con.prepareStatement("UPDATE PRODUCT SET PRODUCT_TYPE_ID = ?, NO_OF_STOCK = ?, PRICE = ? WHERE PRODUCT_NAME = ?");
			ps.setInt(1, entity.getProductTypeId());
			ps.setInt(2, entity.getNoOfStocks());
			ps.setDouble(3, entity.getPrice());
			ps.setString(4, entity.getProductName());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		return -1;
	}

	// It Deactivate product and that product is not visible to the user
	// But it can't be deleted from the database because we don't want to 
	// loose customer data becuase of future security.
	@Override
	public int deleteProduct(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DriverManager.getConnection(url, uname, dbpwd);
			ps = con.prepareStatement("UPDATE PRODUCT SET ACTIVE = 0 WHERE ID = ?");
			ps.setInt(1, id);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		return -1;
	}

	// Retrieving all products from Database 
	@Override
	public ArrayList<ProductsEntity> getAllActiveProducts() {
		Connection con = null;
		PreparedStatement ps = null;
		ArrayList<ProductsEntity> productsArr = new ArrayList<ProductsEntity>();
		
		try {
			con = DriverManager.getConnection(url, uname, dbpwd);
			ps = con.prepareStatement("SELECT * FROM PRODUCT WHERE ACTIVE = 1");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ProductsEntity entity = new ProductsEntity();
				entity.setProductId(rs.getInt(1));
				entity.setProductName(rs.getString(2));
				entity.setProductTypeId(rs.getInt(3));
				entity.setNoOfStocks(rs.getInt(4));
				entity.setPrice(rs.getDouble(5));
				
				// Adding Products to Products ArrayList
				productsArr.add(entity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		return productsArr;
	}

	// Get all products of a specific user which are now active
	@Override
	public ProductsEntity getProduct(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ProductsEntity product = new ProductsEntity();
		
		try {
			con = DriverManager.getConnection(url, uname, dbpwd);
			ps = con.prepareStatement("SELECT * FROM PRODUCT WHERE ID=? AND ACTIVE = 1");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				product.setProductId(rs.getInt(1));
				product.setProductName(rs.getString(2));
				product.setProductTypeId(rs.getInt(3));
				product.setNoOfStocks(rs.getInt(4));
				product.setPrice(rs.getDouble(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		return product;
	}

	// Checking for duplicate Entry by Name
	@Override
	public boolean isDuplicate(String name) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DriverManager.getConnection(url, uname, dbpwd);
			ps = con.prepareStatement("SELECT * FROM PRODUCT WHERE PRODUCT_NAME = ? AND ACTIVE = 1");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		return false;
	}

	// Update the product Stocks information everytime a product added to cart
	@Override
	public int updateProductStatus(int stocks, int id) {
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = DriverManager.getConnection(url, uname, dbpwd);
			
			ps = con.prepareStatement("UPDATE PRODUCT SET NO_OF_STOCK = ?  WHERE ID = ?");
			ps.setInt(1, stocks);
			ps.setInt(2, id);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		return -1;
	}

	// This return all the products of same type
	@Override
	public ArrayList<ProductsEntity> getAllActiveProductsOfCategory(int typeId) {
		Connection con = null;
		PreparedStatement ps = null;
		ArrayList<ProductsEntity> productsArr = new ArrayList<ProductsEntity>();
		
		try {
			con = DriverManager.getConnection(url, uname, dbpwd);
			System.out.println(typeId);
			ps = con.prepareStatement("SELECT * FROM PRODUCT WHERE PRODUCT_TYPE_ID = ? AND ACTIVE = 1");
			ps.setInt(1, typeId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ProductsEntity entity = new ProductsEntity();
				entity.setProductId(rs.getInt(1));
				entity.setProductName(rs.getString(2));
				entity.setProductTypeId(rs.getInt(3));
				entity.setNoOfStocks(rs.getInt(4));
				entity.setPrice(rs.getDouble(5));
				
				// Adding Products to Products ArrayList
				productsArr.add(entity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		return productsArr;
	}
}
