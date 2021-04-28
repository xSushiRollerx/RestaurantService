package com.xsushirollx.sushibyte.restaurantservice.service;

import com.xsushirollx.sushibyte.restaurantservice.dao.UserRestaurantFoodSearchRepository;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRestaurantFoodSearchControllerService {

    private final UserRestaurantFoodSearchRepository userRestaurantFoodSearchRepository;


    public UserRestaurantFoodSearchControllerService(UserRestaurantFoodSearchRepository userRestaurantFoodSearchRepository) {
        this.userRestaurantFoodSearchRepository = userRestaurantFoodSearchRepository;
    }



    public ResponseEntity<?> getAllUserRestaurantFoodSearchItems() {

        return new ResponseEntity<>(userRestaurantFoodSearchRepository.returnAllMenuItems(), HttpStatus.OK);
    }




}
