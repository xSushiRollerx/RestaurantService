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
		List<RestaurantDTO> results = rservice.getAllRestaurants(0, "alphabetically");

		for (int i = 1; i < results.size(); i++) {
			assert (results.get(i).getName().compareToIgnoreCase(results.get(i - 1).getName()) > 0);
		}
	}

	@Test
	public void getAllRestaurantsSortByRating() {
		List<RestaurantDTO> results = rservice.getAllRestaurants(0, "ratings");
		log.info(results.toString());
		for (int i = 1; i < results.size(); i++) {
			log.info("Round" + i + " " + (results.get(i).getAverageRating() - (results.get(i - 1).getAverageRating()) <= 0 ? "true" : "false"));
			assert (results.get(i).getAverageRating() - (results.get(i - 1).getAverageRating()) <= 0);
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
		assert (!rservice.addNewRestaurant(new RestaurantDTO(testRestaurants.get(0))));
	}

	@Test
	public void updateRestaurant() {

		testRestaurants.get(0).setTags("american, southern, burger, fries, comfort food");
		rservice.updateRestaurant(new RestaurantDTO(testRestaurants.get(0)), testRestaurants.get(0).getId());
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
		params.put("sort", "default");
		String[] keywords = { "burgers", "tacos", "burritos" };

		assertEquals(2, rservice.search(params, keywords, "0").size());

		assertEquals(1, rservice.search(params, keywords, "1").size());
	}

	@Test
	public void searchByRatings() {
		log.entering("RestaurantServiceTests", "searchSecurity");

		Map<String, String> params = new HashMap<>();
		params.put("page", "0");
		params.put("sort", "ratings");
		String[] keywords = { "burgers", "tacos", "burritos" };

		List<RestaurantDTO> results = rservice.search(params, keywords, "0");

		for (int i = 1; i < results.size(); i++) {
			assert (results.get(i).getAverageRating() - (results.get(i - 1).getAverageRating()) < 0);
		}

	}

	@Test
	public void searchByName() {
		log.entering("RestaurantServiceTests", "searchSecurity");

		Map<String, String> params = new HashMap<>();
		params.put("page", "0");
		params.put("sort", "alphabetically");
		String[] keywords = { "burgers", "tacos", "burritos" };

		List<RestaurantDTO> results = rservice.search(params, keywords, "0");

		for (int i = 1; i < results.size(); i++) {
			assert (results.get(i).getName().compareToIgnoreCase(results.get(i - 1).getName()) > 0);
		}

	}

}
