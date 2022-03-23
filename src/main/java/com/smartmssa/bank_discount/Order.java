package com.smartmssa.bank_discount;

import java.io.Serializable;

public class Order implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String cardType;
	private int discount;
	private int price;
	
	public Order() {
		
	}
	
	public Order(String name, String cardType, int discount, int price) {
		this.name = name;
		this.cardType = cardType;
		this.discount = discount;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Order [name=" + name + ", cardType=" + cardType + ", discount=" + discount + ", price=" + price + "]";
	}

	
	
	

}
