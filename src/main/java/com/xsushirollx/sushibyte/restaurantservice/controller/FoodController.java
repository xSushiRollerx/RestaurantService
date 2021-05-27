package com.xsushirollx.sushibyte.restaurantservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xsushirollx.sushibyte.restaurantservice.dto.FoodDTO;
import com.xsushirollx.sushibyte.restaurantservice.exception.FoodNotFoundException;
import com.xsushirollx.sushibyte.restaurantservice.model.Food;
import com.xsushirollx.sushibyte.restaurantservice.service.FoodService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/food")
public class FoodController {

	
	@Autowired
    private FoodService foodControllerService;


//    @GetMapping
//    ResponseEntity<List<Food>> getAllFoodMenuItems() {
//
//        return foodControllerService.getAllFoodMenuItems();
//    }


	@PreAuthorize(value = "hasAuthority('ADMINISTRATOR')")
    @GetMapping("/{id}")
    ResponseEntity<?> getOneFoodMenuItem(@PathVariable Long id) {
		try {
			return new ResponseEntity<>(foodControllerService.getOneFoodMenuItem(id), HttpStatus.OK);
		} catch(FoodNotFoundException e) {
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
		}

    }

    @PreAuthorize(value = "hasAuthority('ADMINISTRATOR')")
    @PostMapping
    ResponseEntity<?> addNewFoodMenuItem(@RequestBody FoodDTO newFood) {
    	try {
			return new ResponseEntity<>(foodControllerService.addNewFoodMenuItem(newFood), HttpStatus.CREATED);
		} catch (Exception e) {
			if(e.getMessage() != null && e.getMessage().equalsIgnoreCase("Item Could Not Be Created. Food Item Already Exists.")) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @PreAuthorize(value = "hasAuthority('ADMINISTRATOR')")
    @PutMapping("/{id}")
    ResponseEntity<?> updateFood(@RequestBody FoodDTO newFood, @PathVariable Long id) {
    	try {
			return new ResponseEntity<>(foodControllerService.updateFood(newFood,id), HttpStatus.OK);
		} catch(FoodNotFoundException e) {
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

	@PreAuthorize(value = "hasAuthority('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    ResponseEntity<Food> setFoodMenuItemToInActive(@PathVariable Long id) {
		try {
			foodControllerService.deleteFoodMenuItem(id);
			return new ResponseEntity<Food>(HttpStatus.OK);
		} catch(FoodNotFoundException e) {
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

}
