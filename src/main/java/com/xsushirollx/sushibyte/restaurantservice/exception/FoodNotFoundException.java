package com.xsushirollx.sushibyte.restaurantservice.exception;

public class FoodNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FoodNotFoundException(Long id) {super("Could not locate Food Menu item with id " + id);
    }
}
