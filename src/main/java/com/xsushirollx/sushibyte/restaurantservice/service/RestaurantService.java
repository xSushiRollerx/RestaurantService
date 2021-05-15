package com.xsushirollx.sushibyte.restaurantservice.service;

import com.xsushirollx.sushibyte.restaurantservice.dao.RestaurantDAO;
import com.xsushirollx.sushibyte.restaurantservice.dto.RestaurantDTO;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantDAO repository;

	private Logger log = Logger.getLogger("RestaurantServiceTests");

	public List<RestaurantDTO> getAllRestaurants(Integer page, String sort) {
		switch (sort) {
		case "alphabetically":
			return Arrays.asList(repository.findAll(PageRequest.of(page, 250, Sort.by("name"))).stream().map(r -> new RestaurantDTO(r))
					.toArray(RestaurantDTO[]::new));
		case "ratings":
			log.info("In Avereage Rating: ");
//			return Arrays.asList(repository.findAll(PageRequest.of(page, 250, Sort.by("averageRating").descending())).stream().map(r -> new RestaurantDTO(r))
//					.toArray(RestaurantDTO[]::new));
			return Arrays.asList(repository.findAllSortByAverageRating(PageRequest.of(page, 250)).stream().map(r -> new RestaurantDTO(r))
					.toArray(RestaurantDTO[]::new));
		default:
			return Arrays.asList(repository.findAll(PageRequest.of(page, 250)).stream().map(r -> new RestaurantDTO(r))
					.toArray(RestaurantDTO[]::new));
		}
	}

	public RestaurantDTO findById(Long id) {
		Optional<Restaurant> r = repository.findById(id);
		return r.isPresent() ? new RestaurantDTO(r.get()) : null;
	}

	public boolean addNewRestaurant(RestaurantDTO newRestaurant) {
		Restaurant restaurantToBeAdded = new Restaurant(newRestaurant);

		boolean duplicateRestaurantCheck = repository.existsByNameAndStreetAddressAndCityAndStateAndZipCode(
				restaurantToBeAdded.getName(), restaurantToBeAdded.getStreetAddress(), restaurantToBeAdded.getCity(),
				restaurantToBeAdded.getState(), restaurantToBeAdded.getZipCode());

		if (!duplicateRestaurantCheck) {
			repository.saveAndFlush(restaurantToBeAdded);
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
		repository.setInactiveById(id);
		return true;
	}

	public List<RestaurantDTO> search(Map<String, String> params, String[] keywords, String authority) {
		return dataTransfer(sort(params, keywords, Integer.parseInt(authority)));
	}

	private List<Restaurant> sort(Map<String, String> params, String[] keywords, Integer active) {
		String regex = "";
		for (String k : keywords) {
			regex += "|" + k;
		}

		switch (params.get("sort")) {
		case "alphabetically":
			return repository.findByKeywordsSortByName(regex.substring(1), active,
					PageRequest.of(Integer.parseInt(params.get("page")), 250));
		case "ratings":
			return repository.findByKeywordsSortByRating(regex.substring(1), active,
					PageRequest.of(Integer.parseInt(params.get("page")), 250));
		default:
			return repository.findByKeywords(regex.substring(1), active,
					PageRequest.of(Integer.parseInt(params.get("page")), 250));
		}
	}

	private List<RestaurantDTO> dataTransfer(List<Restaurant> restaurants) {
		log.entering("RestaurantService", "dataTransfer");
		return Arrays.asList(restaurants.parallelStream().map(r -> new RestaurantDTO(r)).toArray(RestaurantDTO[]::new));
	}

}
