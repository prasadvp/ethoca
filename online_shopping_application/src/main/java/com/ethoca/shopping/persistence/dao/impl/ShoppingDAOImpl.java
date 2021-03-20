package com.ethoca.shopping.persistence.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ethoca.shopping.constants.ShoppingConstants;
import com.ethoca.shopping.exception.RetailServiceException;
import com.ethoca.shopping.model.Cart;
import com.ethoca.shopping.model.CartProducts;
import com.ethoca.shopping.model.Product;
import com.ethoca.shopping.model.ProductRequest;
import com.ethoca.shopping.persistence.dao.ShoppingDAO;
import com.ethoca.shopping.persistence.repository.CartProductsRepository;
import com.ethoca.shopping.persistence.repository.CartRepository;
import com.ethoca.shopping.persistence.repository.ProductRepository;

@Repository("shoppingDAO")
public class ShoppingDAOImpl implements ShoppingDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingDAOImpl.class);
	
	@Autowired
	@Qualifier("ProductRepository")
	private ProductRepository prodRepo;
	
	@Autowired
	@Qualifier("CartRepository")
	private CartRepository cartRepo;
	
	@Autowired
	@Qualifier("CartProductsRepository")
	private CartProductsRepository cartProdRepo;

	@Override
	public List<Product> getProducts() {
		return prodRepo.findAll();
	}

	@Override
	@Cacheable(value = "products", key = "#productId")
	public Product findProductById(int productId) {
		return prodRepo.findByProdId(productId);
	}

	@Override
	public void addProduct(ProductRequest request) {
		//Check if the product is already present in the user cart. if yes, update the product quantity
		int prodId = request.getProduct().getProdId();
		int userId = request.getUser().getUserId();
		Cart obj = cartRepo.findProductByUserId(userId, prodId);
		if(obj==null) {
			obj = new Cart();
			obj.setProductId(prodId);
			obj.setUserId(userId);
			obj.setQuantity(request.getRequiredQuantity());
			obj.setActive(true);
			cartRepo.save(obj);
		}else {
			obj.setQuantity(request.getRequiredQuantity());
			obj.setActive(true);
			cartRepo.save(obj);
		}
		
	}

	@Override
	public void updateCart(ProductRequest request) {
		//Check if the product is already present in the user cart. if yes, update the product quantity
		//If not, return an error 
		int prodId = request.getProduct().getProdId();
		int userId = request.getUser().getUserId();
		Cart obj = cartRepo.findProductByUserId(userId, prodId);
		if(obj!=null) {
			obj.setQuantity(request.getRequiredQuantity());
			if(request.getRequiredQuantity() <= 0) {
				obj.setActive(false);
			}
			cartRepo.save(obj);
		}else {
			LOGGER.error("Product not found in the cart ");
			throw new RetailServiceException(ShoppingConstants.PRODUCT_NOT_FOUND_IN_THE_CART.getCode(), ShoppingConstants.PRODUCT_NOT_FOUND_IN_THE_CART.getDescription());
		}
		
	}

	
	@Override
	public List<CartProducts> viewCart(int userId) {
		return cartProdRepo.findProductsByUserId(userId);
	
	}

	@Transactional
	@Override
	public void submitOrder(ProductRequest request) {
		int userId = request.getUser().getUserId();
		cartRepo.updateCart(userId);
		
		
	}

}
