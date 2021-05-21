package com.xsushirollx.sushibyte.restaurantservice.service;

import com.xsushirollx.sushibyte.restaurantservice.dao.RelevanceSearchDAO;
import com.xsushirollx.sushibyte.restaurantservice.dao.RestaurantDAO;
import com.xsushirollx.sushibyte.restaurantservice.dto.RestaurantDTO;
import com.xsushirollx.sushibyte.restaurantservice.model.RelevanceSearch;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

	public List<RestaurantDTO> getAllRestaurants(Integer page, Integer pageSize, Double rating, String sort, Integer active) {
		
		switch (sort) {
		case "a-to-z":
			return Arrays.asList(repository
					.findByIsActiveGreaterThanEqualAndAverageRatingGreaterThanEqual(active, rating, PageRequest.of(page, pageSize, Sort.by("name")))
					.stream()
					.map(r -> new RestaurantDTO(r))
					.toArray(RestaurantDTO[]::new));
		case "ratings":
			log.info("In Average Rating: ");
			return Arrays.asList(repository.findAllSortByAverageRating(active, rating, PageRequest.of(page, pageSize)).stream().map(r -> new RestaurantDTO(r))
					.toArray(RestaurantDTO[]::new));
		default:
			return Arrays.asList(repository
					.findByIsActiveGreaterThanEqualAndAverageRatingGreaterThanEqual(active, rating, PageRequest.of(page, pageSize)).stream().map(r -> new RestaurantDTO(r))
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

	public List<RestaurantDTO> search(Map<String, String> params, Double rating, Integer pageSize, String[] keywords, Integer active) {
		String regex = "";
		for (String k : keywords) {
			regex += "|" + k;
		}

		switch (params.get("sort")) {
		case "a-to-z":
			return dataTransfer(repository.findByKeywordsSortByName(regex.substring(1), active, rating,
					PageRequest.of(Integer.parseInt(params.get("page")), pageSize)));
		case "ratings":
			return dataTransfer(repository.findByKeywordsSortByRating(regex.substring(1), active, rating,
					PageRequest.of(Integer.parseInt(params.get("page")), pageSize)));
		default:
			List<RestaurantDTO> restaurants = dataTransferRelevance( relevanceRepository.findByKeywordsSortByRelevance(regex.substring(1), rating, active,
					PageRequest.of(Integer.parseInt(params.get("page")), pageSize)));
			
			if (restaurants.size() > 0 && restaurants.get(restaurants.size() - 1).getRelevance() == 0) {
				restaurants = new ArrayList<>(restaurants);
				restaurants.removeIf(r -> r.getRelevance() == 0);
			}
			return restaurants;
		}
	}

	private List<RestaurantDTO> dataTransfer(List<Restaurant> restaurants) {
		log.entering("RestaurantService", "dataTransfer");
		return Arrays.asList(restaurants.parallelStream().map(r -> new RestaurantDTO(r)).toArray(RestaurantDTO[]::new));
	}
	
	private List<RestaurantDTO> dataTransferRelevance(List<RelevanceSearch> restaurants) {
		log.entering("RestaurantService", "dataTransfer");
		return Arrays.asList(restaurants.parallelStream().map(r -> new RestaurantDTO(r)).toArray(RestaurantDTO[]::new));
	}
	

}
