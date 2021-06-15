package com.xsushirollx.sushibyte.restaurantservice.exception;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Please ensure valid input in new Restaurant request.")
public class RestaurantCreationException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RestaurantCreationException() {
        super("Ensure you have filled out all the required fields");
    }
}
