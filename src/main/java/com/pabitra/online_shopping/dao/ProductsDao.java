package com.pabitra.online_shopping.dao;

import java.util.ArrayList;

import com.pabitra.online_shopping.entity.ProductsEntity;

public interface ProductsDao {
	public int addProduct(ProductsEntity entity);
	public int updateProduct(ProductsEntity entity);
	public int deleteProduct(int id);
	public ArrayList<ProductsEntity> getAllActiveProducts();
	public ProductsEntity getProduct(int id);
	public boolean isDuplicate(String name);
	public int updateProductStatus(int stocks, int id);
	public ArrayList<ProductsEntity> getAllActiveProductsOfCategory(int typeId);
}
