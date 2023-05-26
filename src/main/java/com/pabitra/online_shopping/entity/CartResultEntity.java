package com.pabitra.online_shopping.entity;

public class CartResultEntity {
	private int id;
	private String name;
	private String type;
	private int quantity;
	private double price;
	private double totalAmount;
	
	@Override
	public String toString() {
		return "CartResultEntity [name=" + name + ", type=" + type + ", quantity=" + quantity + ", price=" + price
				+ ", totalAmount=" + totalAmount + "]";
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
}
