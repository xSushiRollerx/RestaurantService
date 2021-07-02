package com.xsushirollx.sushibyte.restaurantservice.controller;

import com.xsushirollx.sushibyte.restaurantservice.dto.RestaurantDTO;
import com.xsushirollx.sushibyte.restaurantservice.service.RestaurantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class RestaurantController {
//	private Logger log = Logger.getLogger("RestaurantController");

	@Autowired
    private RestaurantService restaurantControllerService;
	
	@PreAuthorize(value = "((hasAnyAuthority('CUSTOMER','NONE') and #active == 1) or hasAuthority('ADMINISTRATOR'))")
	@GetMapping(value = "/restaurants/all/{page}")
    ResponseEntity<List<RestaurantDTO>> getAllRestaurants(@PathVariable Integer page,@RequestParam Map<String, String> params, @RequestParam(value = "sort", defaultValue = "default") String sort, 
    		@RequestParam(defaultValue = "1", name = "active") Integer active, @RequestParam(value="pageSize", defaultValue = "10") Integer pageSize,
    		@RequestParam(defaultValue = "0.0", name = "rating") Double rating, @RequestParam(name="priceCategories", defaultValue="1,2,3,4") String priceCategories) {
    		return new ResponseEntity<>(restaurantControllerService.getAllRestaurants(params, page, pageSize, rating, sort,active), HttpStatus.OK);
    }

    @GetMapping(value = "/restaurant/{id}")
    @PostAuthorize("((returnObject.body == null or returnObject.body.getIsActive() == 1) and hasAnyAuthority('CUSTOMER','NONE')) or hasAuthority('ADMINISTRATOR')")
    ResponseEntity<RestaurantDTO> getRestaurant(@PathVariable Long id) {
    			return new ResponseEntity<>(restaurantControllerService.findById(id), HttpStatus.OK);

    }

    @PreAuthorize(value = "hasAuthority('ADMINISTRATOR')")
    @PostMapping(value = "/restaurant")
    ResponseEntity<RestaurantDTO> addNewRestaurant(@RequestBody RestaurantDTO newRestaurant, @RequestHeader("Authorization") String token) {
    	return new ResponseEntity<>(restaurantControllerService.addNewRestaurant(newRestaurant), HttpStatus.CREATED);
       
    }

    @PreAuthorize(value = "hasAuthority('ADMINISTRATOR')")
    @PutMapping("/restaurant/{id}")
    ResponseEntity<RestaurantDTO> updateRestaurant(@RequestBody RestaurantDTO newRestaurant, @PathVariable Long id, @RequestHeader("Authorization") String token) {
    	return new ResponseEntity<>(restaurantControllerService.updateRestaurant(newRestaurant,id), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ADMINISTRATOR')")
    @DeleteMapping("/restaurant/{id}")
    ResponseEntity<RestaurantDTO> setRestaurantToInActive(@PathVariable Long id, @RequestHeader("Authorization") String token) {
    	return new ResponseEntity<>(restaurantControllerService.setRestaurantToInActive(id), HttpStatus.OK); 
    	
        
    }

    @PreAuthorize(value = "((hasAnyAuthority('CUSTOMER','NONE') and #active == 1) or hasAuthority('ADMINISTRATOR'))")
    @GetMapping("/restaurants/{page}")
    ResponseEntity<List<RestaurantDTO>> searchByKeyword(@RequestParam Map<String, String> params, @RequestParam("keywords") List<String> keywords,
    		@RequestParam(name = "sort", defaultValue = "default") String sort, @PathVariable("page") Integer page,
    		@RequestParam(name = "active", defaultValue = "1") Integer active, @RequestParam(value="pageSize", defaultValue = "10") Integer pageSize,
    		@RequestParam(name = "rating", defaultValue = "0.0") Double rating, @RequestParam(name="priceCategories", defaultValue="1,2,3,4") String priceCategories)  {
    		return new ResponseEntity<>(restaurantControllerService.search(page, params, rating, pageSize, keywords, active), HttpStatus.OK);
    }
    
    @PreAuthorize(value = "hasAuthority('ADMINISTRATOR')")
    @GetMapping("/restaurants/name")
    ResponseEntity<List<RestaurantDTO>> searchByNameExclusively(@RequestParam Map<String, String> params, @RequestParam("keywords") String keyword,
    		@RequestParam(name = "page", defaultValue = "0") Integer page,
    		@RequestParam(name = "active", defaultValue = "1") Integer active, @RequestParam(value="pageSize", defaultValue = "10") Integer pageSize
    		)  {
    		return new ResponseEntity<>(restaurantControllerService.searchByNameExclusively(page, pageSize, keyword, active), HttpStatus.OK);
    }
}




