package com.xsushirollx.sushibyte.restaurantservice.exception;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Please ensure valid input in new Restaurant request.")
public class RestaurantCouldNotCreateException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RestaurantCouldNotCreateException() {
        super("Ensure you have filled out the required fields of name and price category");
    }
}
