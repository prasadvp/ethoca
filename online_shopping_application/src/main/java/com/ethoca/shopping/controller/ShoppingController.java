package com.ethoca.shopping.controller;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ethoca.shopping.model.ProductRequest;
import com.ethoca.shopping.model.ProductResponse;
import com.ethoca.shopping.model.Response;
import com.ethoca.shopping.model.Status;
import com.ethoca.shopping.constants.ShoppingConstants;
import com.ethoca.shopping.helper.ShoppingUtility;
import com.ethoca.shopping.model.CartResponse;
import com.ethoca.shopping.model.Order;
import com.ethoca.shopping.model.Product;
import com.ethoca.shopping.service.ShoppingService;

@RestController
@RequestMapping(value = "/retail")
public class ShoppingController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingController.class);
	
	@Autowired
	@Qualifier("shoppingService")
	public ShoppingService shoppingSvc;
	
	@GetMapping("/v1/products/{productId}")
	public ProductResponse getProduct(@PathVariable Long productId, HttpServletResponse httpResponse) {
		
		LOGGER.info("ProductController:: SearchProductResponse :: Product ID from the request  {}" , productId);
		ProductResponse resp = new ProductResponse();
		Product prod = shoppingSvc.getProduct(productId);
		if(prod == null) {
			LOGGER.error("Invalid Product id :: {} ", productId);
			httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.getMessages().add(ShoppingUtility.buildMessage(ShoppingConstants.PRODUCT_ID_VALIDATION_EXCEPTION.getCode(), ShoppingConstants.PRODUCT_ID_VALIDATION_EXCEPTION.getDescription()));
			resp.setStatus(Status.ERROR.toString());
		}else {
			resp.setStatus(Status.SUCCESS.toString());
			resp.getProducts().add(prod);
		}
		return resp;
	}
	
	@GetMapping("/v1/products")
	public ProductResponse getProducts(HttpServletResponse httpResponse){
		List<Product> prodList = shoppingSvc.getProducts();
		ProductResponse resp = new ProductResponse();
		if(CollectionUtils.isEmpty(prodList)) {
			LOGGER.error("Product List could not be fetched ");
			resp.setStatus(Status.ERROR.toString());
			resp.getMessages().add(ShoppingUtility.buildMessage(ShoppingConstants.PRODUCTS_NOT_FOUND_EXCEPTION.getCode(), ShoppingConstants.PRODUCTS_NOT_FOUND_EXCEPTION.getDescription()));
		}else {
			LOGGER.debug("Product List Size :: {} ",prodList.size());
			resp.setStatus(Status.SUCCESS.toString());
			resp.getProducts().addAll(prodList);
		}
		
		return resp;
	}

	
	@PostMapping("/v1/product")
	public Response addProduct(@RequestBody ProductRequest request, HttpServletResponse httpResponse) {
		
		/**
		 * Check if the request payload has user id and product id 
		 * check if the quantity available for the product 
		 * if yes, get the user cart and add to it 
		 */
		Response resp = new Response();
		if(request!=null && isMandatoryDataPresent(request)) {
				shoppingSvc.addProduct(request);
		}else {
			resp.getMessages().add(ShoppingUtility.buildMessage(ShoppingConstants.INVALID_INPUT_EXCEPTION.getCode(), ShoppingConstants.INVALID_INPUT_EXCEPTION.getDescription()));
			resp.setStatus(Status.ERROR.toString());
			httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		return resp;
	}
	
	private boolean isMandatoryDataPresent(ProductRequest request) {
		
		return request.getProduct()!=null && request.getProduct().getProdId() > 0 && request.getUser()!=null && request.getUser().getUserId()>0;
	}

	@GetMapping("/v1/{userid}/cart")
	public CartResponse viewCart(@PathVariable Long userId, HttpServletResponse httpResponse) {
		return shoppingSvc.viewCart(userId);
		
	}
	
	@PutMapping("/v1/product")
	public Response updateCart(@RequestBody ProductRequest request, HttpServletResponse httpResponse) {
		Response resp = new Response();
		if(request!=null && isMandatoryDataPresent(request)) {
			shoppingSvc.updateCart(request);
	}else {
		resp.getMessages().add(ShoppingUtility.buildMessage(ShoppingConstants.INVALID_INPUT_EXCEPTION.getCode(), ShoppingConstants.INVALID_INPUT_EXCEPTION.getDescription()));
		resp.setStatus(Status.ERROR.toString());
		httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
	
		return resp;
		
	}

	@PostMapping(value = "/v1/order")
	public Order submitOrder(HttpServletResponse httpResponse) {
		Order order = new Order();
		
		return order;
	}
}
