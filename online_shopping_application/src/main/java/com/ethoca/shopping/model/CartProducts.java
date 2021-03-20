package com.ethoca.shopping.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CartProducts extends Response {

	//select p.prodId, p.prodName, p.prodDescription, p.price, c.quantity
	
	public CartProducts(int prodId, String prodName, String prodDescription, BigDecimal price, int quantity, int userId) {
		super();
		this.prodId = prodId;
		this.prodName = prodName;
		this.prodDescription = prodDescription;
		this.price = price;
		this.quantity = quantity;
		this.userId = userId;
	}

	@Id
	private int prodId;
	
	private String prodName;
	
	private String prodDescription;
	
	private BigDecimal price;
	
	private int quantity;

	private int userId;
	
	public int getProdId() {
		return prodId;
	}

	public void setProdId(int prodId) {
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


	
}
