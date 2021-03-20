package com.ethoca.shopping.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="product")
public class Product {
	@Id
	@Column(name="prodId")
	private int prodId;
	
	@Column(name="name")
	private String prodName;
	
	@Column(name="description")
	private String prodDescription;
	
	@Column(name="price")
	private BigDecimal price;
	
	@Column(name="availableQty")
	private int availbleQuantity;

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

	public int getAvailbleQuantity() {
		return availbleQuantity;
	}

	public void setAvailbleQuantity(int availbleQuantity) {
		this.availbleQuantity = availbleQuantity;
	}

}
