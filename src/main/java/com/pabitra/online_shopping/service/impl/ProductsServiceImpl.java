package com.pabitra.online_shopping.service.impl;

import java.util.ArrayList;

import com.pabitra.online_shopping.dao.ProductsDao;
import com.pabitra.online_shopping.entity.ProductsEntity;
import com.pabitra.online_shopping.service.ProductsService;
import com.pabitra.online_shopping.utility.Application;

public class ProductsServiceImpl implements ProductsService {

	@Override
	public int updateProductStatus(int stocks, int id) {
		ProductsDao dao = Application.getProductsDao();
		return dao.updateProductStatus(stocks, id);
	}

	@Override
	public int addProduct(ProductsEntity entity) {
		ProductsDao dao = Application.getProductsDao();
		return dao.addProduct(entity);
	}

	@Override
	public int updateProduct(ProductsEntity product) {
		ProductsDao dao = Application.getProductsDao();
		return dao.updateProduct(product);
	}

	@Override
	public int deleteProduct(int id) {
		ProductsDao dao = Application.getProductsDao();
		return dao.deleteProduct(id);
	}

	@Override
	public ArrayList<ProductsEntity> getAllActiveProducts() {
		ProductsDao dao = Application.getProductsDao();
		return dao.getAllActiveProducts();
	}

	@Override
	public ProductsEntity getProduct(int id) {
		ProductsDao dao = Application.getProductsDao();
		return dao.getProduct(id);
	}
	
	@Override
	public boolean isDuplicate(String name) {
		ProductsDao dao = Application.getProductsDao();
		return dao.isDuplicate(name);
	}

	@Override
	public ArrayList<ProductsEntity> getAllActiveProductsOfCategory(int typeId) {
		ProductsDao dao = Application.getProductsDao();
		return dao.getAllActiveProductsOfCategory(typeId);
	}
	
}
