package com.xsushirollx.sushibyte.restaurantservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Please ensure valid input in new Restaurant request.")
public class RestaurantCouldNotCreateException extends RuntimeException {
    public RestaurantCouldNotCreateException() {
        super("Ensure you have filled out the required fields of name and price category");
    }
}
