package com.xsushirollx.sushibyte.restaurantservice.exception;

public class FoodCreationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FoodCreationException() {
		super("A Food Item With This Name Already Exists");
	}
}
