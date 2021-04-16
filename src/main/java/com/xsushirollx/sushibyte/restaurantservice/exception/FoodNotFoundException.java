package com.xsushirollx.sushibyte.restaurantservice.exception;

public class FoodNotFoundException extends RuntimeException {
    public FoodNotFoundException(Long id) {super("Could not locate Food Menu item with id " + id);
    }
}
