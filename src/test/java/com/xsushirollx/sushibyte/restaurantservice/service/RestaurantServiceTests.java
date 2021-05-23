package com.xsushirollx.sushibyte.restaurantservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xsushirollx.sushibyte.restaurantservice.dao.FoodDAO;
import com.xsushirollx.sushibyte.restaurantservice.dao.RestaurantDAO;
import com.xsushirollx.sushibyte.restaurantservice.dto.RestaurantDTO;
import com.xsushirollx.sushibyte.restaurantservice.model.Food;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;

@SpringBootTest
public class RestaurantServiceTests {

	@Autowired
	private RestaurantService rservice;

	private Logger log = Logger.getLogger("RestaurantServiceTests");

	@Autowired
	private RestaurantDAO rdao;

	@Autowired
	private FoodDAO fdao;

	private List<Restaurant> testRestaurants = new ArrayList<>();

	private List<Food> testFoods = new ArrayList<>();

	@BeforeEach
	public void setUp() {
		Restaurant r1 = new Restaurant("Burger Bar", 2, 3.4, "american, burger, bar, milkshakes", 1, "1958 Sandy Ln",
				"Danny", "CA", 45678);
		testRestaurants.add(r1);
		Restaurant r2 = new Restaurant("Casa Feliz", 3, 4.6, "mexican, texmex, latin", 0, "1654 Tejas", "Primera Vista",
				"TX", 17478);
		testRestaurants.add(r2);

		for (int i = 0; i < testRestaurants.size(); i++) {
			rdao.saveAndFlush(testRestaurants.get(i));
		}

		// Restaurant 1 Food
		Food f1 = new Food(r1.getId(), "Smoothstack Burger", 12.49,
				"One of our most popular burgers! This delicious burger is 2 lbs of juicy meat complete with the works on a butter bun.",
				0, 1, "Burgers");
		testFoods.add(f1);
		Food f2 = new Food(r1.getId(), "Mac N' Cheese Burger", 13.99,
				"A southern delight! Combines our amazing macaroni cheese on a juicy patty drizzled off with our super secret cheesy sauce!",
				0, 1, "Burger");
		testFoods.add(f2);

		// Restaurant 2 Food
		Food f3 = new Food(r2.getId(), "Tacos Con Camarones", 11.99,
				"These mouthwatering shrimp tacos are like none you have eaten before!", 0, 1, "Tacos");
		testFoods.add(f3);
		Food f4 = new Food(r2.getId(), "Lo Mejor Burger Burrito", 14.99,
				"This huge stuffed burrito is the most delicious and fullfilling burger burrito you will ever have in your life time!",
				0, 1, "Burritos");
		testFoods.add(f4);

		for (int i = 0; i < testFoods.size(); i++) {
			fdao.saveAndFlush(testFoods.get(i));
		}

		for (int i = 0; i < testRestaurants.size(); i++) {
			testRestaurants.set(i, rdao.findById(testRestaurants.get(i).getId()).get());
		}
	}

	@AfterEach
	public void tearDown() {
		for (int i = 0; i < testRestaurants.size(); i++) {
			rdao.deleteById(testRestaurants.get(i).getId());
		}

		for (int i = 0; i < testFoods.size(); i++) {
			fdao.deleteById(testFoods.get(i).getId());
		}

	}

	@Test
	public void getAllRestaurantsSortByName() {
		Map<String, String> params = new HashMap<>();
        params.put("priceCategories", "1, 3, 4");
        
		List<RestaurantDTO> results = rservice.getAllRestaurants(params, 0, 5, 1.0, "a-to-z", 0);
		log.info("Sort By Name: " + results.toString());
		for (int i = 1; i < results.size(); i++) {
			assert (results.get(i).getName().compareToIgnoreCase(results.get(i - 1).getName()) >= 0);
			assert (results.get(i).getAverageRating() >= 1);
			assert(results.get(i).getPriceCategory() != 2);
		}
	}

	@Test
	public void getAllRestaurantsSortByRating() {
		Map<String, String> params = new HashMap<>();
        params.put("priceCategories", "1, 2, 4");
		List<RestaurantDTO> results = rservice.getAllRestaurants(params, 0, 5, 4.0, "ratings", 0);
		log.info("Sort By Rating: " + results.toString());
		for (int i = 1; i < results.size(); i++) {
			log.info("Round" + i + " " + (results.get(i).getAverageRating() - (results.get(i - 1).getAverageRating()) <= 0 ? "true" : "false"));
			assert (results.get(i).getAverageRating() - (results.get(i - 1).getAverageRating()) <= 0);
			assert (results.get(i).getAverageRating() >= 3);
			assert(results.get(i).getPriceCategory() != 3);
		}
	}

	@Test
	public void findById() {
		for (int i = 0; i < testRestaurants.size(); i++) {
			assertEquals(rservice.findById(testRestaurants.get(i).getId()).getId(), testRestaurants.get(i).getId());
		}
	}

	@Test
	public void addNewRestaurant() {
		assert (!rservice.addNewRestaurant(new RestaurantDTO(testRestaurants.get(0), null, null)));
	}

	@Test
	public void updateRestaurant() {

		testRestaurants.get(0).setTags("american, southern, burger, fries, comfort food");
		rservice.updateRestaurant(new RestaurantDTO(testRestaurants.get(0), null, null), testRestaurants.get(0).getId());
		assertEquals(rdao.findById(testRestaurants.get(0).getId()).get().getTags(),
				"american, southern, burger, fries, comfort food");

	}

	@Test
	public void setRestaurantToInActive() {
		rservice.setRestaurantToInActive(testRestaurants.get(0).getId());
		assertEquals(rdao.findById(testRestaurants.get(0).getId()).get().getIsActive(), 0);
	}

	@Test
	public void searchSecurity() {
		log.entering("RestaurantServiceTests", "searchSecurity");

		Map<String, String> params = new HashMap<>();
		params.put("page", "0");
		params.put("sort", "a-to-z");
		params.put("priceCategories", "1, 4");
		List<String> keywords = new ArrayList<>();
		keywords.add("burgers");
		keywords.add("american");
		
		List<RestaurantDTO> result = rservice.search(0, params, 2.0, 5, keywords, 1);
		
		log.info("Search Security" + result);
		
		
		for (int i = 0; i < result.size(); i++) {
			assertEquals(1, result.get(i).getIsActive());
			assert (result.get(i).getAverageRating() >= 2);
			assert(result.get(i).getPriceCategory() != 3);
			assert(result.get(i).getPriceCategory() != 2);
			assert(result.get(i).getPriceCategory() != 3);
		}
	}

	@Test
	public void searchByRatings() {
		log.entering("RestaurantServiceTests", "searchSecurity");

		Map<String, String> params = new HashMap<>();
		params.put("page", "0");
		params.put("sort", "ratings");
		params.put("priceCategories", "1, 3");
		List<String> keywords = new ArrayList<>();
		keywords.add("burgers");
		keywords.add("tacos");
		keywords.add("burritos");

		List<RestaurantDTO> results = rservice.search(0, params, 1.0, 5, keywords, 0);

		for (int i = 1; i < results.size(); i++) {
			assert (results.get(i).getAverageRating() - (results.get(i - 1).getAverageRating()) < 0);
			assert (results.get(i).getAverageRating() >= 1);
			assert(results.get(i).getPriceCategory() != 2);
			assert(results.get(i).getPriceCategory() != 4);
		}

	}

	@Test
	public void searchByName() {
		log.entering("RestaurantServiceTests", "searchSecurity");

		Map<String, String> params = new HashMap<>();
		params.put("page", "0");
		params.put("priceCategories", "2");
		params.put("sort", "a-to-z");
		List<String> keywords = new ArrayList<>();
		keywords.add("burgers");
		keywords.add("tacos");
		keywords.add("burritos");
		
		
		List<RestaurantDTO> results = rservice.search(0, params, 1.0, 5, keywords, 0);
		for (int i = 1; i < results.size(); i++) {
			assert (results.get(i).getName().compareToIgnoreCase(results.get(i - 1).getName()) > 0);
			assert (results.get(i).getAverageRating() >= 1);
			assert(results.get(i).getPriceCategory() == 2);
		}
		

	}
	
	@Test
	public void searchByRelevance() {
		log.entering("RestaurantServiceTests", "searchSecurity");

		Map<String, String> params = new HashMap<>();
		params.put("page", "0");
		params.put("sort", "relevance");
		params.put("priceCategories", "2, 3, 4");
		List<String> keywords = new ArrayList<>();
		keywords.add("burgers");
		keywords.add("tacos");
		keywords.add("burritos");

		List<RestaurantDTO> results = rservice.search(0, params, 2.0, 5, keywords, 0);

		log.info("Search Relevance: " + results);
		for (int i = 1; i < results.size(); i++) {
			assert (results.get(i).getRelevance() - (results.get(i - 1).getRelevance()) <= 0);
			assert(results.get(i).getRelevance() != 0);
			assert (results.get(i).getAverageRating() >= 2);
			assert(results.get(i).getPriceCategory() != 1);
		}

	}

}
