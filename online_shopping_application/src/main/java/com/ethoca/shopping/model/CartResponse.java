package com.ethoca.shopping.model;

import java.util.ArrayList;
import java.util.List;

public class CartResponse extends Response {

	private List<CartProducts> cartList;

	public List<CartProducts> getCartList() {
		if(cartList == null){
			cartList = new ArrayList<>();
		}
		return cartList;
	}

	public void setCartList(List<CartProducts> cartList) {
		this.cartList = cartList;
	}
	
	
}
