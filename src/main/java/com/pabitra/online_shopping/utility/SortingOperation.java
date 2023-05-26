package com.pabitra.online_shopping.utility;

import java.util.ArrayList;
import java.util.Collections;

import com.pabitra.online_shopping.entity.ProductsEntity;

public class SortingOperation {
	public static ArrayList<ProductsEntity> sortByLowToHigh(ArrayList<ProductsEntity> entity){
		
		Collections.sort(entity, (product1,product2)->(product1.getPrice()>product2.getPrice())?1:(product1.getPrice()<product2.getPrice())?-1:0);
		
		return entity;
	}
	
	public static ArrayList<ProductsEntity> sortByHighToLow(ArrayList<ProductsEntity> entity){
		
		Collections.sort(entity, (product1,product2)->(product1.getPrice()>product2.getPrice())?-1:(product1.getPrice()<product2.getPrice())?1:0);
		
		return entity;
	}
	
	public static ArrayList<ProductsEntity> sortByAscending(ArrayList<ProductsEntity> entity){
		
		Collections.sort(entity, (product1,product2)->product1.getProductName().compareTo(product2.getProductName()));
		
		return entity;
	}

	public static ArrayList<ProductsEntity> sortByDescending(ArrayList<ProductsEntity> entity){
	
	Collections.sort(entity, (product1,product2)->product2.getProductName().compareTo(product1.getProductName()));
		
	return entity;
}
}
