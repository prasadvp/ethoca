package com.ethoca.shopping.model;

import java.util.ArrayList;
import java.util.List;

public class ProductResponse extends Response {
	
	private List<Product> products;

	public List<Product> getProducts() {
		if(products == null) {
			products = new ArrayList<>();
		}
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
