package com.xsushirollx.sushibyte.restaurantservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xsushirollx.sushibyte.restaurantservice.dto.FoodDTO;
import com.xsushirollx.sushibyte.restaurantservice.model.Food;
import com.xsushirollx.sushibyte.restaurantservice.service.FoodService;

@Controller
@RequestMapping(value = "/food")
public class FoodController {

	
	@Autowired
    private FoodService foodControllerService;


	@PreAuthorize(value = "hasAuthority('ADMINISTRATOR')")
    @GetMapping("/{id}")
    ResponseEntity<?> getOneFoodMenuItem(@PathVariable Long id) {
			return new ResponseEntity<>(foodControllerService.getOneFoodMenuItem(id), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ADMINISTRATOR')")
    @PostMapping
    ResponseEntity<?> addNewFoodMenuItem(@RequestBody FoodDTO newFood, @RequestHeader("Authorization") String authorization) {
			return new ResponseEntity<>(foodControllerService.addNewFoodMenuItem(newFood), HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasAuthority('ADMINISTRATOR')")
    @PutMapping("/{id}")
    ResponseEntity<?> updateFood(@RequestBody FoodDTO newFood, @PathVariable Long id) {
			return new ResponseEntity<>(foodControllerService.updateFood(newFood,id), HttpStatus.OK);
    }

	@PreAuthorize(value = "hasAuthority('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    ResponseEntity<Food> setFoodMenuItemToInActive(@PathVariable Long id) {
			foodControllerService.deleteFoodMenuItem(id);
			return new ResponseEntity<Food>(HttpStatus.NO_CONTENT);
    }

}
