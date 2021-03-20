package com.ethoca.shopping.helper;

import com.ethoca.shopping.model.Message;

public class ShoppingUtility {
	
	private ShoppingUtility() {
		
	}
	/**
	 * This method to build the Message object for the given code and description
	 * @param code
	 * @param description
	 * @return
	 */
		public static Message buildMessage(String code, String description) {
			return new Message(code, description);
		}

}
