package com.xsushirollx.sushibyte.restaurantservice.service;

import com.xsushirollx.sushibyte.restaurantservice.dao.RestaurantRepository;
import com.xsushirollx.sushibyte.restaurantservice.dto.RestaurantDTO;
import com.xsushirollx.sushibyte.restaurantservice.exception.RestaurantNotFoundException;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Component
public class RestaurantControllerService {

    private final RestaurantRepository repository;


    public RestaurantControllerService(RestaurantRepository repository) {
        this.repository = repository;
    }


   public ResponseEntity<List<Restaurant>> getAllRestaurants() {

        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }


   public ResponseEntity<Restaurant> getOneRestaurant(Long id) {
        return new ResponseEntity<>(repository.findById(id).
                orElseThrow(() -> new RestaurantNotFoundException(id)), HttpStatus.OK);
    }


   public ResponseEntity<?> addNewRestaurant(@RequestBody RestaurantDTO newRestaurant) {
        Restaurant restaurantToBeAdded = new Restaurant(newRestaurant.getName(),
                newRestaurant.getPriceCategory(), newRestaurant.getAverageRating(),
                newRestaurant.getTags(), newRestaurant.getIsActive(),
                newRestaurant.getStreetAddress(), newRestaurant.getCity(),
                newRestaurant.getState(), newRestaurant.getZipCode());

        Restaurant duplicateRestaurantCheck = repository.checkForExistingRestaurantByValues(
                restaurantToBeAdded.getName(),
                restaurantToBeAdded.getStreetAddress(),
                restaurantToBeAdded.getCity(),
                restaurantToBeAdded.getState(),
                restaurantToBeAdded.getZipCode());

        if (restaurantToBeAdded.equals(duplicateRestaurantCheck)) {
            //return conflict status code
            return ResponseEntity.unprocessableEntity()
                    .body("This Restaurant already exists");
        } else {
            try {
                return new ResponseEntity<Restaurant>((repository.save(restaurantToBeAdded)), HttpStatus.CREATED);
            } catch (IllegalArgumentException | ConstraintViolationException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please check " +
                        "for any missing fields which are required fora new restaurant to be created.");
            } catch (Exception ex) {
                //need to add an additional error message
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }



   public ResponseEntity<Restaurant> updateRestaurant(@RequestBody RestaurantDTO newRestaurant,
                                                @PathVariable Long id) {

        return new ResponseEntity<>(repository.findById(id).map(
                restaurant -> {
                    if (newRestaurant.getName() != null) restaurant.setName(newRestaurant.getName());
                    if (newRestaurant.getAverageRating() != null)
                        restaurant.setAverageRating(newRestaurant.getAverageRating());
                    if (newRestaurant.getTags() != null) restaurant.setTags(newRestaurant.getTags());
                    if (newRestaurant.getIsActive() != null) restaurant.setIsActive(newRestaurant.getIsActive());
                    if (newRestaurant.getPriceCategory() != null)
                        restaurant.setPriceCategory(newRestaurant.getPriceCategory());
                    if (newRestaurant.getStreetAddress() != null)
                        restaurant.setStreetAddress(newRestaurant.getStreetAddress());
                    if (newRestaurant.getCity() != null) restaurant.setCity(newRestaurant.getCity());
                    if (newRestaurant.getState() != null) restaurant.setState(newRestaurant.getState());
                    if (newRestaurant.getZipCode() != null) restaurant.setZipCode(newRestaurant.getZipCode());
                    return repository.save(restaurant);
                })
                .orElseThrow(() ->
                        new RestaurantNotFoundException(id)),
                HttpStatus.OK);
    }


   public ResponseEntity<Restaurant> setRestaurantToInActive(@PathVariable Long id) {
        ResponseEntity<Restaurant> inActiveRestaurant = getOneRestaurant(id);
        if (inActiveRestaurant != null) {
            repository.setInactiveById(id);

            try {
                repository.setInactiveById(id);

            } catch (Exception e) {
                return new ResponseEntity(inActiveRestaurant, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity(getOneRestaurant(id), HttpStatus.OK);
    }

}




