package com.ethoca.shopping.model;

import java.math.BigDecimal;

public class CartProducts extends Response {

	//select p.prodId, p.prodName, p.prodDescription, p.price, c.quantity
	
	private long prodId;
	
	private String prodName;
	
	private String prodDescription;
	
	private BigDecimal price;
	
	private int quantity;

	public long getProdId() {
		return prodId;
	}

	public void setProdId(long prodId) {
		this.prodId = prodId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdDescription() {
		return prodDescription;
	}

	public void setProdDescription(String prodDescription) {
		this.prodDescription = prodDescription;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
