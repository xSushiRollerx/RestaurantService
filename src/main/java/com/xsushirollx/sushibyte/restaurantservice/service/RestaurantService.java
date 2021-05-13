package com.xsushirollx.sushibyte.restaurantservice.service;

import com.xsushirollx.sushibyte.restaurantservice.dao.RestaurantDAO;
import com.xsushirollx.sushibyte.restaurantservice.dto.RestaurantDTO;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantDAO repository;

	public List<Restaurant> getAllRestaurants() {
		return repository.findAll();
	}

	public Restaurant findById(Long id) {
		Optional<Restaurant> r = repository.findById(id);
		return r.isPresent() ? r.get() : null;
	}

	public boolean addNewRestaurant(@RequestBody RestaurantDTO newRestaurant) {
		Restaurant restaurantToBeAdded = new Restaurant(newRestaurant);

		boolean duplicateRestaurantCheck = repository.existsByNameAndStreetAddressAndCityAndStateAndZipCode(
				restaurantToBeAdded.getName(), restaurantToBeAdded.getStreetAddress(), restaurantToBeAdded.getCity(),
				restaurantToBeAdded.getState(), restaurantToBeAdded.getZipCode());

		if (duplicateRestaurantCheck) {
			repository.save(restaurantToBeAdded);
			return true;
		} else {
			return false;
		}

	}

	public boolean updateRestaurant(RestaurantDTO updatedRestaurant, Long id) {

		Restaurant r = new Restaurant(updatedRestaurant);
		r.setId(id);
		repository.save(r);
		return true;
	}

	public boolean setRestaurantToInActive(Long id) {
		Restaurant inActiveRestaurant = findById(id);
		repository.save(inActiveRestaurant);
		
		return true;
	}
	
	
	public List<Restaurant> findByKeywords(String[] keywords, String authority) {
		String regex = "";
		for (String k: keywords) {
			regex += "|" + k;
		}
		
		//for admin active status shown to it is determined on the frontend
		if (authority.contentEquals("CUSTOMER")) {
			return repository.findByKeyword(regex.substring(1), "1");
		} else if (authority.contentEquals("ADMINISTRATOR")) {
			return repository.findByKeyword(regex.substring(1), "1 or 2");
		} else {
			return null;
		}
	}

}
