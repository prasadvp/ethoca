package com.ethoca.shopping.constants;

public enum ShoppingConstants {
	DEFAULT_EXCEPTION ("CERR-1000","Retail service Default Exception"),
	//JSON_DESERIALIZE_EXCEPTION("CERR-1001", "Unable to deserialize JSON object"),
	WEB_SERVICE_EXCEPTION("WERR-5000", "Web service Exception "),
	PRODUCT_ID_VALIDATION_EXCEPTION("RSVC-4000", "Invalid Product ID"),
	PRODUCTS_NOT_FOUND_EXCEPTION("RSVC-4001", "Unable to fetch Product List"),
	INVALID_INPUT_EXCEPTION("RSVC-5001", "Invalid Input"),
	QUANTITY_INVALID_EXCEPTION("RSVC-4002", "Invalid Quantity "),
	QUANTITY_REQUESTED_OVER_THE_LIMIT("RSVC-4003", "Quantity requested more than the available"),
	PRODUCT_NOT_ADDED_TO_CART("RSVC-4004", "Product not added or updated to cart"),
	PRODUCT_ADDED_TO_CART("RSVC-4004", "Product added to cart successfully"),
	PRODUCT_UPDATE_SUCCESSFUL("RSVC-2000", "Product details updated successfully"),
	CART_RETRIEVAL_ERROR("RSVC-5003", "Error retrieving Cart information"),
	PRODUCT_NOT_FOUND_IN_THE_CART("RSVC-5002", "Product does not exist in the cart");
	
	private final String code;
	private final String description;
	
	private ShoppingConstants(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

}