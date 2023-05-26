package com.pabitra.online_shopping.utility;

import com.pabitra.online_shopping.dao.CartDao;
import com.pabitra.online_shopping.dao.ProductsDao;
import com.pabitra.online_shopping.dao.ProductsTypeDao;
import com.pabitra.online_shopping.dao.UsersDao;
import com.pabitra.online_shopping.dao.impl.CartDaoImpl;
import com.pabitra.online_shopping.dao.impl.ProductsDaoImpl;
import com.pabitra.online_shopping.dao.impl.ProductsTypeDaoImpl;
import com.pabitra.online_shopping.dao.impl.UsersDaoImpl;
import com.pabitra.online_shopping.service.CartService;
import com.pabitra.online_shopping.service.PhoneValidationService;
import com.pabitra.online_shopping.service.ProductsService;
import com.pabitra.online_shopping.service.ProductsTypeService;
import com.pabitra.online_shopping.service.UsersService;
import com.pabitra.online_shopping.service.impl.CartServiceImpl;
import com.pabitra.online_shopping.service.impl.PhoneValidationServiceImpl;
import com.pabitra.online_shopping.service.impl.ProductsServiceImpl;
import com.pabitra.online_shopping.service.impl.ProductsTypeServiceImpl;
import com.pabitra.online_shopping.service.impl.UsersServiceImpl;

/*
 * 
 * This class i defined for Manage all implemented
 * classes and hide our internal implementation 
 * classes from outsider.
 * 
 * From this class i return the implemented class object
 * to the Controllers or Services.
 * 
 */

public interface Application {
	
	public static UsersService getUsersService() {
		return new UsersServiceImpl();
	}
	public static UsersDao getUsersDao() {
		return new UsersDaoImpl();
	}
	public static PhoneValidationService getPhoneValidationService() {
		return new PhoneValidationServiceImpl();
	}
	public static ProductsService getProductsService() {
		return new ProductsServiceImpl();
	}
	public static ProductsDao getProductsDao() {
		return new ProductsDaoImpl();
	}
	public static ProductsTypeService getProductsTypeService() {
		return new ProductsTypeServiceImpl();
	}
	public static ProductsTypeDao getProductsTypeDao() {
		return new ProductsTypeDaoImpl();
	}
	public static CartService getCartService() {
		return new CartServiceImpl();
	}
	public static CartDao getCartDao() {
		return new CartDaoImpl();
	}
}
