package com.xsushirollx.sushibyte.restaurantservice.controller;


import com.xsushirollx.sushibyte.restaurantservice.dao.UserRestaurantFoodSearchRepository;
import com.xsushirollx.sushibyte.restaurantservice.service.UserRestaurantFoodSearchControllerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/UserRestaurantFoodSearch")
public class UserRestaurantFoodSearchController {


    private final UserRestaurantFoodSearchControllerService userRestaurantFoodSearchControllerService;

    public UserRestaurantFoodSearchController(UserRestaurantFoodSearchControllerService userRestaurantFoodSearchControllerService) {
        this.userRestaurantFoodSearchControllerService = userRestaurantFoodSearchControllerService;
    }

    @GetMapping
    ResponseEntity<?> getAllUserRestaurantFoodSearchItems() {
        return userRestaurantFoodSearchControllerService.getAllUserRestaurantFoodSearchItems();
    }

}
