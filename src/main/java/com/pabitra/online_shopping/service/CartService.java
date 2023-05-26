package com.pabitra.online_shopping.service;

import java.util.ArrayList;

import com.pabitra.online_shopping.entity.CartEntity;

public interface CartService {
	public int addCartProduct(CartEntity entity);
	public CartEntity getCartProduct(int userId, int productId);
	public ArrayList<CartEntity> getAllActiveCartProductsById(int id);
	public ArrayList<CartEntity> getAllActiveCartProducts();
	public int deleteCartProduct(int id);
	public int updateCartProduct(CartEntity entity);
}
