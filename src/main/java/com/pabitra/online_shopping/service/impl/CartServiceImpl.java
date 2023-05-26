package com.pabitra.online_shopping.service.impl;

import java.util.ArrayList;

import com.pabitra.online_shopping.dao.CartDao;
import com.pabitra.online_shopping.entity.CartEntity;
import com.pabitra.online_shopping.service.CartService;
import com.pabitra.online_shopping.utility.Application;

public class CartServiceImpl implements CartService {

	@Override
	public int addCartProduct(CartEntity entity) {
		CartDao dao = Application.getCartDao();
		return dao.addCartProduct(entity);
	}

	@Override
	public CartEntity getCartProduct(int userId, int productId) {
		CartDao dao = Application.getCartDao();
		return dao.getCartProduct(userId, productId);
	}

	@Override
	public ArrayList<CartEntity> getAllActiveCartProductsById(int id) {
		CartDao dao = Application.getCartDao();
		return dao.getAllActiveCartProductsById(id);
	}

	@Override
	public int deleteCartProduct(int id) {
		CartDao dao = Application.getCartDao();
		return dao.deleteCartProduct(id);
	}

	@Override
	public int updateCartProduct(CartEntity entity) {
		CartDao dao = Application.getCartDao();
		return dao.updateCartProduct(entity);
	}

	@Override
	public ArrayList<CartEntity> getAllActiveCartProducts() {
		CartDao dao = Application.getCartDao();
		return dao.getAllActiveCartProducts();
	}	
}
