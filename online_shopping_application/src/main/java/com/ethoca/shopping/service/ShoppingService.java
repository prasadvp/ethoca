package com.ethoca.shopping.service;

import java.util.List;

import com.ethoca.shopping.model.CartProducts;
import com.ethoca.shopping.model.CartResponse;
import com.ethoca.shopping.model.Product;
import com.ethoca.shopping.model.ProductRequest;
import com.ethoca.shopping.model.ProductResponse;
import com.ethoca.shopping.model.Response;

public interface ShoppingService {
	
	Product getProduct(long productId);
	
	
	List<Product> getProducts();
	
	Response addProduct(ProductRequest request);
	
	Response updateCart(ProductRequest request);
	
	void submitOrder();
	
	CartResponse viewCart(long userId);
}
