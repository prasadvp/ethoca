package com.ethoca.shopping.service.impl;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ethoca.shopping.constants.ShoppingConstants;
import com.ethoca.shopping.helper.ShoppingUtility;
import com.ethoca.shopping.model.CartProducts;
import com.ethoca.shopping.model.CartResponse;
import com.ethoca.shopping.model.Product;
import com.ethoca.shopping.model.ProductRequest;
import com.ethoca.shopping.model.Response;
import com.ethoca.shopping.model.Status;
import com.ethoca.shopping.persistence.dao.ShoppingDAO;
import com.ethoca.shopping.service.ShoppingService;

@Service("shoppingService")
public class ShoppingServiceImpl implements ShoppingService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingServiceImpl.class);


	
	@Autowired
	@Qualifier("shoppingDAO")
	private ShoppingDAO shoppingDAO;
	
	@Override
	public Product getProduct(int productId) {
		
		Product prod = shoppingDAO.findProductById(productId);
		return (prod !=null) ? prod : new Product();
	}

	@Override
	public List<Product> getProducts() {
		
		return shoppingDAO.getProducts();
	}
	/**
	 * Add the product to user cart provided if the below conditions met
	 * Requested quantity is valid 
	 * Product is present
	 * Requested quantity is less than or equal to available quantity 
	 */

	@Override
	public Response addProduct(ProductRequest request) {

		
		Response resp = new Response();
		int productId = request.getProduct().getProdId();
		LOGGER.debug("Product id to be added :: {} ", productId);
		Product prod  = getProduct(productId);
		if(request.getRequiredQuantity() <=0) {
			LOGGER.error("Invalid Quantity :: {} ", request.getRequiredQuantity());
			resp.getMessages().add(ShoppingUtility.buildMessage(ShoppingConstants.QUANTITY_INVALID_EXCEPTION.getCode(), ShoppingConstants.QUANTITY_INVALID_EXCEPTION.getDescription()));
			resp.setStatus(Status.ERROR.name());
			return resp;
		}
		if(prod == null || prod.getProdId() <=0) {
			LOGGER.error("Product Id not exists :: {} ", productId);
			resp.getMessages().add(ShoppingUtility.buildMessage(ShoppingConstants.PRODUCT_ID_VALIDATION_EXCEPTION.getCode(), ShoppingConstants.PRODUCT_ID_VALIDATION_EXCEPTION.getDescription()));
			resp.setStatus(Status.ERROR.name());
			return resp;
		}
		
		if(request.getRequiredQuantity() > prod.getAvailbleQuantity()) {
			LOGGER.error("Requested quantity - {} is greater than available quantity = {}",request.getRequiredQuantity(), prod.getAvailbleQuantity());
			resp.getMessages().add(ShoppingUtility.buildMessage(ShoppingConstants.QUANTITY_REQUESTED_OVER_THE_LIMIT.getCode(), ShoppingConstants.QUANTITY_REQUESTED_OVER_THE_LIMIT.getDescription()));
			resp.setStatus(Status.ERROR.name());
			return resp;
		}
		try {
			//Add it to the Cart entity
			shoppingDAO.addProduct(request);
			resp.setStatus(Status.SUCCESS.toString());
			resp.getMessages().add(ShoppingUtility.buildMessage(ShoppingConstants.PRODUCT_ADDED_TO_CART.getCode(), ShoppingConstants.PRODUCT_ADDED_TO_CART.getDescription()));
			
		}catch(Exception ex){
			LOGGER.error("Exception while adding product to cart :: {} ",ex.getMessage());
			resp.getMessages().add(ShoppingUtility.buildMessage(ShoppingConstants.PRODUCT_NOT_ADDED_TO_CART.getCode(), ShoppingConstants.PRODUCT_NOT_ADDED_TO_CART.getDescription()));
			resp.setStatus(Status.ERROR.toString());
			
		}
		return resp;
	}

	/**
	 * This method will update the cart. if the quantity is set to 0, the active flag will be changed to false;
	 */
	@Override
	public Response updateCart(ProductRequest request) {
		Response resp = new Response();
		int productId = request.getProduct().getProdId();
		LOGGER.debug("Product id to be updated  :: {} ", productId);
		Product prod  = getProduct(productId);
		if(prod == null) {
			LOGGER.error("Product Id not exists :: {} ", productId);
			resp.getMessages().add(ShoppingUtility.buildMessage(ShoppingConstants.PRODUCT_ID_VALIDATION_EXCEPTION.getCode(), ShoppingConstants.PRODUCT_ID_VALIDATION_EXCEPTION.getDescription()));
			resp.setStatus(Status.ERROR.name());
			return resp;
		}
		try {
			//Add it to the Cart entity
			shoppingDAO.updateCart(request);
			resp.setStatus(Status.SUCCESS.toString());
			resp.getMessages().add(ShoppingUtility.buildMessage(ShoppingConstants.PRODUCT_UPDATE_SUCCESSFUL.getCode(), ShoppingConstants.PRODUCT_UPDATE_SUCCESSFUL.getDescription()));
			
		}catch(Exception ex){
			LOGGER.error("Exception while adding product to cart :: {} ",ex.getMessage());
			resp.getMessages().add(ShoppingUtility.buildMessage(ShoppingConstants.PRODUCT_NOT_ADDED_TO_CART.getCode(), ShoppingConstants.PRODUCT_NOT_ADDED_TO_CART.getDescription()));
			resp.setStatus(Status.ERROR.toString());
			
		}
		return resp;

	}

	@Override
	public int submitOrder(ProductRequest request) {
		shoppingDAO.submitOrder(request);
		return new Random().nextInt(9999999);

	}

	@Override
	public CartResponse viewCart(int userId) {
		CartResponse resp = new CartResponse();
		
		try {
			List<CartProducts> prodList  = shoppingDAO.viewCart(userId);
			resp.getCartList().addAll(prodList);
			resp.setStatus(Status.SUCCESS.toString());
		}catch(Exception ex) {
			LOGGER.error("Exception while retrieving cart :: {} ",ex.getMessage());
			resp.getMessages().add(ShoppingUtility.buildMessage(ShoppingConstants.CART_RETRIEVAL_ERROR.getCode(), ShoppingConstants.CART_RETRIEVAL_ERROR.getDescription()));
			resp.setStatus(Status.ERROR.toString());
		}
		
		return resp;

	}

	

	
}
