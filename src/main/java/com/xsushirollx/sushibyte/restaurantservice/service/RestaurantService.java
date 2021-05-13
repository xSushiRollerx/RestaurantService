package com.xsushirollx.sushibyte.restaurantservice.service;

import com.xsushirollx.sushibyte.restaurantservice.dao.RestaurantDAO;
import com.xsushirollx.sushibyte.restaurantservice.dto.RestaurantDTO;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantDAO repository;

	private Logger log = Logger.getLogger("RestaurantServiceTests");

	public List<Restaurant> getAllRestaurants() {
		return repository.findAll();
	}

	public RestaurantDTO findById(Long id) {
		Optional<Restaurant> r = repository.findById(id);
		return r.isPresent() ? new RestaurantDTO(r.get()) : null;
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
		try {
			Restaurant inActiveRestaurant = repository.findById(id).get();
			repository.save(inActiveRestaurant);
			return true;
		} catch (NullPointerException e) {
			return false;
		}

	}

	public List<Restaurant> findByKeywords(String[] keywords, String authority, Integer page) {
		String regex = "";
		for (String k : keywords) {
			regex += "|" + k;
		}

		// for admin active status shown to it is determined on the frontend
		repository.findAll(PageRequest.of(page, 250));

		if (authority.equals("CUSTOMER")) {

			return repository.findByKeywords(regex.substring(1), "1", PageRequest.of(page, 250));

		} else if (authority.equals("ADMINISTRATOR")) {

			return repository.findByKeywords(regex.substring(1), "1 or 2", PageRequest.of(page, 250));

		} else {
			return null;
		}
	}

	public List<Restaurant> relevance(List<Restaurant> restaurants, String[] keywords) {
		return Arrays.asList(restaurants.parallelStream().map(r -> {

			for (String k : keywords) {
				if (r.getName().contains(k)) {
					r.setRelevance(r.getRelevance() + 1);
				}

				if (r.getTags().contains(k)) {
					r.setRelevance(r.getRelevance() + 2.5);
				}

				for (int i = 0; i < r.getMenu().size(); i++) {
					if (r.getMenu().get(i).getName().contains(k)) {
						r.setRelevance(r.getRelevance() + 1);
					}

					if (r.getMenu().get(i).getSummary().contains(k)) {
						r.setRelevance(r.getRelevance() + 1.25);
					}

				}
			}
			return r;
		}).toArray(Restaurant[]::new));

	}

	public List<RestaurantDTO> dataTransfer(List<Restaurant> restaurants) {
		return Arrays.asList(restaurants.parallelStream().map(r -> new RestaurantDTO(r)).toArray(RestaurantDTO[]::new));

	}

}
