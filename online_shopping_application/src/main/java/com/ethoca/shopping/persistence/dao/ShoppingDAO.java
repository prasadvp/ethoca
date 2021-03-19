package com.ethoca.shopping.persistence.dao;

import java.util.List;


import com.ethoca.shopping.model.CartProducts;
import com.ethoca.shopping.model.Product;
import com.ethoca.shopping.model.ProductRequest;

public interface ShoppingDAO {

	List<Product> getProducts();
	
	Product findProductById(long productId);

	void addProduct(ProductRequest request);

	void updateCart(ProductRequest request);

	List<CartProducts> viewCart(long userId);
}
