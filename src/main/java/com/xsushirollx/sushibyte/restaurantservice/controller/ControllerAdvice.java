package com.xsushirollx.sushibyte.restaurantservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.xsushirollx.sushibyte.restaurantservice.exception.FoodCreationException;
import com.xsushirollx.sushibyte.restaurantservice.exception.FoodNotFoundException;
import com.xsushirollx.sushibyte.restaurantservice.exception.RestaurantCreationException;
import com.xsushirollx.sushibyte.restaurantservice.exception.RestaurantNotFoundException;

@RestControllerAdvice
public class ControllerAdvice {
	
	@ExceptionHandler(value = {RestaurantCreationException.class, FoodCreationException.class})
	public ResponseEntity<?> restaurantExceptions(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {RestaurantNotFoundException.class, FoodNotFoundException.class})
	public ResponseEntity<?> restaurantNotFoundExceptions(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<?> exceptions(Exception e) {
		if (e.getMessage().equalsIgnoreCase("Access is denied")) {
			return new ResponseEntity<>("Status 403: Access Denied", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>("Status 500: Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	

}
