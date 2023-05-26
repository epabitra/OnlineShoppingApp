package com.pabitra.online_shopping.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.pabitra.online_shopping.entity.CartEntity;
import com.pabitra.online_shopping.entity.CartResultEntity;

//import org.json.simple.JSONObject;

import com.pabitra.online_shopping.entity.ProductsEntity;
import com.pabitra.online_shopping.entity.ProductsTypeEntity;
import com.pabitra.online_shopping.service.CartService;
import com.pabitra.online_shopping.service.ProductsService;
import com.pabitra.online_shopping.service.ProductsTypeService;
import com.pabitra.online_shopping.utility.Application;
import com.pabitra.online_shopping.utility.SortingOperation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/product")
public class ProductsController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		
		// << Loading the manage-products page Types Dropdown >>
		if(action.equals("loadTypes")) {
			PrintWriter pw = resp.getWriter();
			pw.println("<option value=0>Select</option>");
			ProductsTypeService service = Application.getProductsTypeService();
			ArrayList<ProductsTypeEntity> productTypes = service.getProductTypes();
			for(ProductsTypeEntity type:productTypes) {
				pw.println("<option value="+type.getId()+">"+type.getName()+"</option>");
			}
			return;
		}
		// << /End of Loading the manage-products page types Dropdown >>
		
		// << Loading the View-products page types Dropdown >>
				if(action.equals("loadDropdown")) {
					PrintWriter pw = resp.getWriter();
					//pw.println("<li><a class=\"dropdown-item\" onclick='loadDropdown()'>Electronics</a></li>");
					
					ProductsTypeService service = Application.getProductsTypeService();
					ArrayList<ProductsTypeEntity> productTypes = service.getProductTypes();
					
					for(ProductsTypeEntity type:productTypes) {
						pw.println("<li><a class=\"dropdown-item\" onclick='loadDropdown("+type.getId()+")'>"+type.getName()+"</a></li>");
					}
					return;
				}
		// << /End of Loading the view-products page types Dropdown >>
		
		// << Adding product to the Database >>
		if(action.equals("addProduct")){
			ProductsEntity entity = new ProductsEntity();
			entity.setProductName(req.getParameter("name"));
			entity.setProductTypeId(Integer.parseInt(req.getParameter("productType")));
			entity.setPrice(Double.parseDouble(req.getParameter("price")));
			entity.setNoOfStocks(Integer.parseInt(req.getParameter("stocks")));
			
			// PrintWriter Object Creation
			PrintWriter pw = resp.getWriter();
			
			// Duplicate Name check
			ProductsService service = Application.getProductsService();
			if(service.isDuplicate(entity.getProductName())) {
				pw.println("duplicate");
				pw.close();
				return;
			}
			
			// Product Adding
			int temp = service.addProduct(entity);
			if(temp == 1) {
				pw.println("success");
				pw.close();
				return;
			}else {
				pw.println("failed");
				System.out.println("Product adding query returns :: "+temp);
				pw.close();
				return;
			}
		}
		// << /End of Adding product to the Database >>
		
		// << Rendering all products in View Product Page >>
		if(action.equals("loadProducts")) {
			String role = req.getParameter("role");
			PrintWriter pw = resp.getWriter();
			String sort = req.getParameter("sort");
			int type = Integer.parseInt(req.getParameter("type"));
			
			// Getting the products from Database
			ProductsService productsService = Application.getProductsService();
			ArrayList<ProductsEntity> products;
			if(type == 0) {
				products = productsService.getAllActiveProducts();
			}else {
				products = productsService.getAllActiveProductsOfCategory(type);
			}
			
			// Shorting the products object based on below condition
			if(sort.equals("lowtohigh"))
				SortingOperation.sortByLowToHigh(products);
			if(sort.equals("hightolow"))
				SortingOperation.sortByHighToLow(products);
			if(sort.equals("ascending"))
				SortingOperation.sortByAscending(products);
			if(sort.equals("descending"))
				SortingOperation.sortByDescending(products);
			
			// Getting product types from the Database and Storing them
			// in an Map for reduce repeatedly accessing the database.
			// I use this for render in the View-product.jsp page.
			Map<Integer,String> map = new HashMap<Integer,String>();
			ProductsTypeService productsTypeService = Application.getProductsTypeService();
			ArrayList<ProductsTypeEntity> productTypes = productsTypeService.getProductTypes();
			for(ProductsTypeEntity productType : productTypes) {
				map.put(productType.getId(), productType.getName());
			}
			
			// If the login person is admin then the data render in the below format
			if(role.equals("admin")) {
				for(ProductsEntity product : products) {
					pw.println("<tr><td>" + product.getProductName() + "</td><td>" + map.get(product.getProductTypeId()) + "</td><td>" + product.getNoOfStocks() + "</td><td>"+ product.getPrice() + "</td><td><button class=\"btn btn-outline-info\" id=editBtn onclick=editProduct(this) value=" + product.getProductId() + ">Edit</button><button class=\"btn btn-outline-danger ml-2\" id=deleteBtn onclick=deleteProduct(this) value=" + product.getProductId() +">Delete</button></td></tr>");
				}
			}
			// If the login person is user then the data render in the below format
			if(role.equals("user")) {
				for(ProductsEntity product : products) {
//					if(product.getStatus()==1){
						pw.println("<tr><td>" + product.getProductName() + "</td><td>" + map.get(product.getProductTypeId()) + "</td><td>" + product.getNoOfStocks() + "</td><td>"+ product.getPrice() + "</td><td><button class=\"btn btn-outline-info\" onclick=addCart(this) value=" + product.getProductId() + ">Add To Cart</button></td></tr>");
//					}
//					if(product.getStatus()==0) {
//						pw.println("<tr><td>" + product.getProductName() + "</td><td>" + map.get(product.getProductTypeId()) + "</td><td>" + product.getNoOfStocks() + "</td><td>"+ product.getPrice() + "</td><td><button class=\"btn btn-outline-secondary\" disabled>View Cart</button></td></tr>");
//					}
				}
			}
		}
		// << /End of Rendering all products in View Product Page >>
		
		// << Deactivating a product in the Database >>
		if(action.equals("deleteProduct")) {
			// Id we want to delete
			int id = Integer.parseInt(req.getParameter("id"));
			
			// Checking This product added to any users cart or not
			CartService cartService = Application.getCartService();
			ArrayList<CartEntity> allActiveCartProducts = cartService.getAllActiveCartProducts();
			HashMap<Integer,String> map = new HashMap<Integer,String>();
			for(CartEntity entity:allActiveCartProducts) {
				map.put(entity.getProductId(), "active");
			}

			PrintWriter pw = resp.getWriter();
			if(map.get(id)!=null && map.get(id).equals("active")) {
				pw.println("used");
				return;
			}
			ProductsService productService = Application.getProductsService();
			int result = productService.deleteProduct(id);
			if(result == 1) {
				pw.println("success");
			}else {
				System.out.println("Deleting Status :: "+result);
				pw.println("failed");
			}
		}
		// << /End of Deactivating a product in the Database >>
		
		// << Getting the product information and send it to clint-side as JSON data >>
		if(action.equals("getJSON")) {
			ProductsService service = Application.getProductsService();
			ProductsEntity product = service.getProduct(Integer.parseInt(req.getParameter("id")));
			
			// Getting product types from the Database and Storing them
			// in an Map for reduce repeatedly accessing the database.
			// I use this for render in the View-product.jsp page.
			Map<Integer,String> map = new HashMap<Integer,String>();
			ProductsTypeService productsTypeService = Application.getProductsTypeService();
			ArrayList<ProductsTypeEntity> productTypes = productsTypeService.getProductTypes();
			for(ProductsTypeEntity productType : productTypes) {
				map.put(productType.getId(), productType.getName());
			}
			
			String jsonString = "{\"productId\":\""+product.getProductId()+"\",\"productName\":\""+product.getProductName()+"\",\"productType\":\""+map.get(product.getProductTypeId())+"\",\"productTypeId\":\""+product.getProductTypeId()+"\",\"price\":\""+product.getPrice()+"\",\"stocks\":\""+product.getNoOfStocks()+"\"}";
			resp.setContentType("application/json");
            resp.setCharacterEncoding("utf-8");
			PrintWriter pw = resp.getWriter();
			pw.println(jsonString);
		}
		// << /End of Getting the product information and send it to clint-side as JSON data  >>
		
		// << Update the Product Information >>
		if(action.equals("updateProduct")) {
			ProductsEntity entity = new ProductsEntity();
			entity.setProductName(req.getParameter("name"));
			entity.setProductTypeId(Integer.parseInt(req.getParameter("productType")));
			entity.setPrice(Double.parseDouble(req.getParameter("price")));
			entity.setNoOfStocks(Integer.parseInt(req.getParameter("stocks")));
			
			ProductsService service = Application.getProductsService();
			int result = service.updateProduct(entity);
			if(result == 1) {
				PrintWriter pw = resp.getWriter();
				pw.println("success");
			}
		}
		// << /End of Update the Product Information >>
		
		// << Adding Product into Cart >>
		if(action.equals("addCart")) {
			CartEntity entity = new CartEntity();
			
			// how many products the seller have
			int stocks = Integer.parseInt(req.getParameter("stocks"));
			
			// how many the customer planed to buy
			int quantity = Integer.parseInt(req.getParameter("quantity"));

			int id = Integer.parseInt(req.getParameter("productId"));
			int userId = Integer.parseInt(req.getParameter("userId"));
			double totalPrice = Double.parseDouble(req.getParameter("totalPrice"));
			
			// Now it is the current available stocks in product table we update this quantity
			stocks -= quantity;
			
			// (CartEntity)entity fields initialized
			// But if product already exit in cart then totalPrice and
			// quantity going to update again in the update part below...
			entity.setProductId(id);
			entity.setUserId(userId);
			entity.setQuantity(quantity);
			entity.setTotalPrice(totalPrice);
			
			CartService cartService = Application.getCartService();
			ProductsService productService = Application.getProductsService();
			
			// It extract the already added product from cart table
			// Because when we try to add cart 2nd time then it have 
			// to update the same row instead of creating new row...
			// <! (imp) If the cart table not have the current product
			//          then it return a empty CartEntity object with 
			//			only by setting userId as 0 for recognize easily /> 
			CartEntity cartEntity = cartService.getCartProduct(userId, id);
			
			int result=0;
			int cartResult=-1;
			int productResult = -1;
			
			// Updating the existing product in cart
			if(cartEntity.getId() != 0) {
				// Here i updated the TotalPrice and Quantity fields of 
				// above initialized variable (CartEntity)entity and
				// setting the current exising cart row id
				entity.setQuantity(cartEntity.getQuantity()+quantity);
				entity.setTotalPrice(cartEntity.getTotalPrice()+totalPrice);
				entity.setId(cartEntity.getId());
				cartResult = cartService.updateCartProduct(entity);
				productResult = productService.updateProductStatus(stocks, id);
			}else {
			// Adding a new Product to the Cart
				cartResult = cartService.addCartProduct(entity);
				productResult = productService.updateProductStatus(stocks, id);
			}
			if(cartResult==1 && productResult==1) {
				result = 1;
			}else {
				// TODO Rollback code have to write if anyone
				// got failed but not necessary for now.
			}
			
			PrintWriter pw = resp.getWriter();
			
			if(result == 1) {
				pw.println("success");
			}
			if(result == 0) {
				pw.println("failed");
			}
		}
		// << /End of Adding Product into Cart >>
		
		// << Rendering all products in the Cart Page >>
		if(action.equals("loadCart")) {
			PrintWriter pw = resp.getWriter();
			int id = Integer.parseInt(req.getParameter("id"));
			
			// Getting all the Products from Cart table
			CartService cartService = Application.getCartService();
			ArrayList<CartEntity> cartEntity = cartService.getAllActiveCartProductsById(id);
			
			// Getting the products from Database
			ProductsService productsService = Application.getProductsService();
			ArrayList<ProductsEntity> productsEntity = productsService.getAllActiveProducts();
			
			// Getting products from the Database and Storing them
			// in an Map for reduce repeatedly accessing the database.
			// I use this for render in the my-cart page.
			Map<Integer, ProductsEntity> productMap = new HashMap<Integer, ProductsEntity>();
			for(ProductsEntity entity:productsEntity) {
				productMap.put(entity.getProductId(), entity);
			}
			
			Map<Integer,String> productTypeMap = new HashMap<Integer,String>();
			ProductsTypeService productsTypeService = Application.getProductsTypeService();
			ArrayList<ProductsTypeEntity> productTypes = productsTypeService.getProductTypes();
			for(ProductsTypeEntity productType : productTypes) {
				productTypeMap.put(productType.getId(), productType.getName());
			}
			
			// The cartResultEntity store the all final values which i want to render in my-cart page
			// It extract data from product table and product type table and stored combinely in this obj...
			ArrayList<CartResultEntity> cartResultEntity = new ArrayList<CartResultEntity>();
			for(CartEntity entity:cartEntity) {
				ProductsEntity product = productMap.get(entity.getProductId());
				CartResultEntity temp = new CartResultEntity();
				// This is the cart table row id
				// below are extracted from cart table
				temp.setId(entity.getId());
				temp.setTotalAmount(entity.getTotalPrice());
				temp.setQuantity(entity.getQuantity());
				// below are extracted from product table
				temp.setName(product.getProductName());
				temp.setPrice(product.getPrice());
				// below is extracted from productType table
				temp.setType(productTypeMap.get(product.getProductTypeId()));
				cartResultEntity.add(temp);
			}
			
			for(CartResultEntity entity:cartResultEntity) {
				pw.println("<tr><td>" + entity.getName() + "</td><td>" + entity.getType() + "</td><td>" + entity.getPrice() + "</td><td>"+ entity.getQuantity() + "</td><td>"+ entity.getTotalAmount() + "</td><td><button class=\"btn btn-outline-danger\" onclick=removeProduct(this) value=" + entity.getId() + ">Remove Product</button></td></tr>");
			}
		}
		// << /End of Rendering all products in the Cart Page >>
		
		// << Remove Product From the Cart >>
		if(action.equals("removeProduct")) {
			int id = Integer.parseInt(req.getParameter("id"));
			CartService service = Application.getCartService();
			int result = service.deleteCartProduct(id);
			
			// TODO I have to reduce the stocks from  products table but
			// actually they assign a new work to me so i have to stop this
			// for now... but in the future i have to work on this...
			
			PrintWriter pw = resp.getWriter();
			if(result == 1) {
				pw.println("success");
				pw.close();
			}else {
				pw.println("failed");
				pw.close();
			}
		}
		// << /End of Remove Product From the Cart >>
	}
}
