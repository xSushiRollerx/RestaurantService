package com.xsushirollx.sushibyte.restaurantservice.exception;

public class RestaurantNotFoundException extends RuntimeException{
    public RestaurantNotFoundException(Long id){
        super("Could not locate Restaurant with id " + id);
    }
}
