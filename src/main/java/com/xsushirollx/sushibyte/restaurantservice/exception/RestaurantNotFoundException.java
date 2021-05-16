package com.xsushirollx.sushibyte.restaurantservice.exception;

public class RestaurantNotFoundException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RestaurantNotFoundException(Long id){
        super("Could not locate Restaurant with id " + id);
    }
}
