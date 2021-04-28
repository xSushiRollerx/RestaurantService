package com.xsushirollx.sushibyte.restaurantservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestaurantNotCreatedAdvice {

    @ResponseBody
    @ExceptionHandler(RestaurantCouldNotCreateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String restaurantNotCreatedHandler(RestaurantNotFoundException ex){
        return ex.getMessage();
    }

}
