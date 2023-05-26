package com.pabitra.online_shopping.service.impl;

import java.util.ArrayList;

import com.pabitra.online_shopping.dao.ProductsTypeDao;
import com.pabitra.online_shopping.entity.ProductsTypeEntity;
import com.pabitra.online_shopping.service.ProductsTypeService;
import com.pabitra.online_shopping.utility.Application;

public class ProductsTypeServiceImpl implements ProductsTypeService {

	@Override
	public ArrayList<ProductsTypeEntity> getProductTypes() {
		ProductsTypeDao dao = Application.getProductsTypeDao();
		return dao.getProductTypes();
	}
}
