package com.xsushirollx.sushibyte.restaurantservice.service;

import com.xsushirollx.sushibyte.restaurantservice.dao.RelevanceSearchDAO;
import com.xsushirollx.sushibyte.restaurantservice.dao.RestaurantDAO;
import com.xsushirollx.sushibyte.restaurantservice.dto.RestaurantDTO;
import com.xsushirollx.sushibyte.restaurantservice.exception.RestaurantCreationException;
import com.xsushirollx.sushibyte.restaurantservice.exception.RestaurantNotFoundException;
import com.xsushirollx.sushibyte.restaurantservice.model.RelevanceSearch;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
		if (params.containsKey("priceCategories")) {
			if (params.get("priceCategories").contains("1")) {
				one = 1;
			}
			if (params.get("priceCategories").contains("2")) {
				two = 2;
			}
			if (params.get("priceCategories").contains("3")) {
				three = 3;
			}
			if (params.get("priceCategories").contains("4")) {
				four = 4;
			}
		} else {
			one = 1;
			two = 2;
			three = 3;
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
		return restaurants
				.map(r -> new RestaurantDTO(r, totalElements, totalPages))
				.getContent();
	}

	public RestaurantDTO findById(Long id) throws RestaurantNotFoundException {
		Optional<Restaurant> r = repository.findById(id);
		if (!r.isPresent()) throw new RestaurantNotFoundException(id);
		return new RestaurantDTO(r.get(), null, null);
	}

	public RestaurantDTO addNewRestaurant(RestaurantDTO newRestaurant) throws RestaurantCreationException {
		Restaurant restaurantToBeAdded = new Restaurant(newRestaurant);

		boolean duplicateRestaurantCheck = repository.existsByNameAndStreetAddressAndCityAndStateAndZipCode(
				restaurantToBeAdded.getName(), restaurantToBeAdded.getStreetAddress(), restaurantToBeAdded.getCity(),
				restaurantToBeAdded.getState(), restaurantToBeAdded.getZipCode());

		log.info("duplicate check " + duplicateRestaurantCheck);
		if (!duplicateRestaurantCheck) {
			return new RestaurantDTO(repository.saveAndFlush(restaurantToBeAdded), null, null);
		} else {
			throw new RestaurantCreationException();
		}

	}

	public RestaurantDTO updateRestaurant(RestaurantDTO updatedRestaurant, Long id)  throws RestaurantNotFoundException {

		if (!repository.existsById(id)) throw new RestaurantNotFoundException(id);
		Restaurant r = new Restaurant(updatedRestaurant);
		r.setId(id);
		return new RestaurantDTO(repository.save(r), null, null);
	}

	public  RestaurantDTO setRestaurantToInActive(Long id) throws RestaurantNotFoundException {
		Optional<Restaurant> r = repository.findById(id);
		if (r.isPresent()) {
			repository.setInactiveById(id);
			r.get().setIsActive(0);
		} else {
			 throw new RestaurantNotFoundException(id);
		}
		return new RestaurantDTO(r.get(), null, null);
	}

	public List<RestaurantDTO> search(Integer page, Map<String, String> params, Double rating, Integer pageSize, List<String> keywords,
			Integer active) {
		String regex = "";
		Integer one = 0;
		Integer two = 0;
		Integer three = 0;
		Integer four = 0;

		for (String k : keywords) {
			regex += "|" + k;
		}
		
		if (params.containsKey("priceCategories")) {
			if (params.get("priceCategories").contains("1")) {
				one = 1;
			}
			if (params.get("priceCategories").contains("2")) {
				two = 2;
			}
			if (params.get("priceCategories").contains("3")) {
				three = 3;
			}
			if (params.get("priceCategories").contains("4")) {
				four = 4;
			}
		} else {
			one = 1;
			two = 2;
			three = 3;
			four = 4;
		}
		
		Page<Restaurant> restaurants = null;
		List<RestaurantDTO> results = null;
		
		switch (params.get("sort")) {
		case "a-to-z":
			restaurants = repository.findByKeywordsSortByName(regex.substring(1), active, rating, one, two, three, four, 
					PageRequest.of(page, pageSize));
			Long totalElements = restaurants.getTotalElements();
			Integer totalPages = restaurants.getTotalPages();
			results = restaurants.map(r -> new RestaurantDTO(r, totalElements, totalPages)).getContent();
			break; 
		case "ratings":
			restaurants = repository.findByKeywordsSortByRating(regex.substring(1), active, rating, one, two, three, four,
					PageRequest.of(page, pageSize));
			
			Long totalElementsRatings = restaurants.getTotalElements();
			Integer totalPagesRatings = restaurants.getTotalPages();
			
			results = restaurants.map(r -> new RestaurantDTO(r, totalElementsRatings, totalPagesRatings)).getContent();
			break;
		default:
			Page<RelevanceSearch> relevantRestaurants = relevanceRepository.findByKeywordsSortByRelevance(regex.substring(1), rating, active, one, two,
					three, four, PageRequest.of(page, pageSize));
			Long totalElementsRelevance = relevantRestaurants.getTotalElements();
			Integer totalPagesRelevance = relevantRestaurants.getTotalPages();
			results = relevantRestaurants.map(r -> new RestaurantDTO(r, totalElementsRelevance, totalPagesRelevance)).getContent();

			if (totalPagesRelevance == page + 1) {
				results = new ArrayList<>(results);
				results.removeIf(r -> r.getRelevance() == 0);
			}
			break;
		}
		log.info(results.toString());
		return results;
	}
	
	public List<RestaurantDTO> searchByNameExclusively(Integer page, Integer pageSize, String keyword,Integer active) {
		
		Page<Restaurant> restaurants = repository.findByNameStartingWithAndIsActiveGreaterThanEqual(keyword, active, PageRequest.of(page, pageSize, Sort.by(Direction.ASC, "name")));
		return restaurants
				.map(r -> new RestaurantDTO(r, restaurants.getTotalElements(), restaurants.getTotalPages()))
				.getContent();
	}

}
