package com.xsushirollx.sushibyte.restaurantservice.controller;

import com.xsushirollx.sushibyte.restaurantservice.dto.RestaurantDTO;
import com.xsushirollx.sushibyte.restaurantservice.service.RestaurantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {
	private Logger log = Logger.getLogger("RestaurantController");

	@Autowired
    private RestaurantService restaurantControllerService;
	
	@GetMapping(value = "/all/page/{page}")
    ResponseEntity<?> getAllRestaurants(@PathVariable Integer page) {
    	try {
    		return new ResponseEntity<>(restaurantControllerService.getAllRestaurants(page), HttpStatus.OK);
    	} catch(Exception e) {
    		e.printStackTrace();
    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<RestaurantDTO> getRestaurant(@PathVariable Long id) {
    	try {
    		RestaurantDTO restaurant =  restaurantControllerService.findById(id);
    		if (restaurant == null) {
    			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    		} else {
    			return new ResponseEntity<>(restaurant, HttpStatus.OK);
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}

    }

    @PostMapping
    ResponseEntity<?> addNewRestaurant(@RequestBody RestaurantDTO newRestaurant) {

    	try {
    		log.entering("RestaurantController", "addNewRestaurant");
    		if (restaurantControllerService.addNewRestaurant(newRestaurant)) {
    			return new ResponseEntity<>(HttpStatus.CREATED);
    		} else {
    			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
       
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateRestaurant(@RequestBody RestaurantDTO newRestaurant, @PathVariable Long id) {
    	try {
    		if (restaurantControllerService.updateRestaurant(newRestaurant,id)) {
    			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    		} else {
    			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> setRestaurantToInActive(@PathVariable Long id) {
    	
    	try {
    		if (restaurantControllerService.setRestaurantToInActive(id)) {
    			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    		} else {
    			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	
        
    }

    ResponseEntity<List<RestaurantDTO>> searchByKeyword(@RequestParam Map<String, String> params, @RequestParam String[] keywords) {
    	try {
    		return new ResponseEntity<>(restaurantControllerService.search(params, keywords), HttpStatus.OK);
    	} catch(Exception e) {
    		e.printStackTrace();
    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
}




