package com.xsushirollx.sushibyte.restaurantservice.service;

import com.xsushirollx.sushibyte.restaurantservice.dao.RelevanceSearchDAO;
import com.xsushirollx.sushibyte.restaurantservice.dao.RestaurantDAO;
import com.xsushirollx.sushibyte.restaurantservice.dto.RestaurantDTO;
import com.xsushirollx.sushibyte.restaurantservice.model.RelevanceSearch;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantDAO repository;

	@Autowired
	private RelevanceSearchDAO relevanceRepository;

	private Logger log = Logger.getLogger("RestaurantServiceTests");

	public List<RestaurantDTO> getAllRestaurants(Map<String, String> params, Integer page, Integer pageSize, Double rating, String sort,
			Integer active) {
		Integer one = 0;
		Integer two = 0;
		Integer three = 0;
		Integer four = 0;
		
		if (params.get("priceCategory").contains("1")) {
			one = 1;
		}
		if (params.get("priceCategory").contains("2")) {
			two = 2;
		}
		if (params.get("priceCategory").contains("3")) {
			three = 3;
		}
		if (params.get("priceCategory").contains("4")) {
			four = 4;
		}

		Page<Restaurant> restaurants = null;
		switch (sort) {
		case "a-to-z":
			restaurants = repository.findAllSortByName(active, rating, one, two, three, four, PageRequest.of(page, pageSize));
			break;
		case "ratings":
			restaurants = repository.findAllSortByAverageRating(active, rating, one, two, three, four, PageRequest.of(page, pageSize));
			break;
		default:
			restaurants = repository.findAllSortByName(active, rating, one, two, three, four, PageRequest.of(page, pageSize));
			break;
		}
		Long totalElements = restaurants.getTotalElements();
		Integer totalPages = restaurants.getTotalPages();
		return restaurants.map(r -> new RestaurantDTO(r, totalElements, totalPages)).toList();
	}

	public RestaurantDTO findById(Long id) {
		Optional<Restaurant> r = repository.findById(id);
		return r.isPresent() ? new RestaurantDTO(r.get(), null, null) : null;
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

	public List<RestaurantDTO> search(Map<String, String> params, Double rating, Integer pageSize, String[] keywords,
			Integer active) {
		String regex = "";
		Integer one = 0;
		Integer two = 0;
		Integer three = 0;
		Integer four = 0;

		for (String k : keywords) {
			regex += "|" + k;
		}
		
		if (params.get("priceCategory").contains("1")) {
			one = 1;
		}
		if (params.get("priceCategory").contains("2")) {
			two = 2;
		}
		if (params.get("priceCategory").contains("3")) {
			three = 3;
		}
		if (params.get("priceCategory").contains("4")) {
			four = 4;
		}
		
		Page<Restaurant> restaurants = null;
		List<RestaurantDTO> results = null;
		
		switch (params.get("sort")) {
		case "a-to-z":
			restaurants = repository.findByKeywordsSortByName(regex.substring(1), active, rating, one, two, three, four, 
					PageRequest.of(Integer.parseInt(params.get("page")), pageSize));
			Long totalElements = restaurants.getTotalElements();
			Integer totalPages = restaurants.getTotalPages();
			results = restaurants.map(r -> new RestaurantDTO(r, totalElements, totalPages)).toList();
			break; 
		case "ratings":
			restaurants = repository.findByKeywordsSortByRating(regex.substring(1), active, rating, one, two, three, four,
					PageRequest.of(Integer.parseInt(params.get("page")), pageSize));
			
			Long totalElementsRatings = restaurants.getTotalElements();
			Integer totalPagesRatings = restaurants.getTotalPages();
			
			results = restaurants.map(r -> new RestaurantDTO(r, totalElementsRatings, totalPagesRatings)).toList();
			break;
		default:
			results = dataTransferRelevance(
					relevanceRepository.findByKeywordsSortByRelevance(regex.substring(1), rating, active, one, two,
							three, four, PageRequest.of(Integer.parseInt(params.get("page")), pageSize)));

			if (results.size() > 0 && results.get(results.size() - 1).getRelevance() == 0) {
				results = new ArrayList<>(results);
				results.removeIf(r -> r.getRelevance() == 0);
			}
			break;
		}
		return results;
	}

	
	private List<RestaurantDTO> dataTransferRelevance(List<RelevanceSearch> restaurants) {
		log.entering("RestaurantService", "dataTransfer");
		return Arrays.asList(restaurants.parallelStream().map(r -> new RestaurantDTO(r, null, null)).toArray(RestaurantDTO[]::new));
	}

}
