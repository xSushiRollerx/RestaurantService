package com.xsushirollx.sushibyte.restaurantservice.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import com.xsushirollx.sushibyte.restaurantservice.model.Food;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;

@SpringBootTest
public class RestaurantDAOTests {

	@Autowired
	private RestaurantDAO rdao;
	
	@Autowired
	private FoodDAO fdao;
	
	private Logger log = Logger.getLogger("RestaurantDAOTests");
	
	private List<Restaurant> testRestaurants = new ArrayList<>();
	
	private List<Food> testFoods = new ArrayList<>();
	
	@BeforeEach
	public void setUp() {
		Restaurant r1 = new Restaurant("Burger Bar", 2, 3.4, "american, burger, bar, milkshakes", 1, "1958 Sandy Ln", "Danny", "CA", 45678);
		testRestaurants.add(r1);
		Restaurant r2 = new Restaurant("Casa Feliz", 3, 4.6, "mexican, texmex, latin", 0, "1654 Tejas", "Primera Vista", "TX", 17478);
		testRestaurants.add(r2);
		
		for (int i = 0; i < testRestaurants.size(); i++) {
			rdao.saveAndFlush(testRestaurants.get(i));
		}
		
		
		//Restaurant 1 Food
		Food f1 = new Food(r1.getId(), "Smoothstack Burger", 12.49, "One of our most popular burgers! This delicious burger is 2 lbs of juicy meat complete with the works on a butter bun.", 0, 1, "Burgers");
		testFoods.add(f1);
		Food f2 = new Food(r1.getId(), "Mac N' Cheese Burger", 13.99, "A southern delight! Combines our amazing macaroni cheese on a juicy patty drizzled off with our super secret cheesy sauce!", 0, 1, "Burger");
		testFoods.add(f2);
		
		//Restaurant 2 Food
		Food f3 = new Food(r2.getId(), "Tacos Con Camarones", 11.99, "These mouthwatering shrimp tacos are like none you have eaten before!", 0, 1, "Tacos");
		testFoods.add(f3);
		Food f4 = new Food(r2.getId(), "Lo Mejor", 14.99, "This huge stuffed burrito is the most delicious and fullfilling burrito you will ever have in your life time!", 0, 1, "Burritos");
		testFoods.add(f4);
		
		for (int i = 0; i < testFoods.size(); i++) {
			fdao.saveAndFlush(testFoods.get(i));
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
//	
//	@Test
//	public void setInactiveById() {
//		rdao.setInactiveById(testRestaurants.get(0).getId());
//		assertEquals(0, rdao.findById(testRestaurants.get(0).getId()).get().getIsActive());
//	}
//	
//	@Test
//	public void checkForExistingRestaurantByValues() {
//		assert(rdao.checkForExistingRestaurantByValues(testRestaurants.get(0).getName(), testRestaurants.get(0).getStreetAddress(), testRestaurants.get(0).getCity(), testRestaurants.get(0).getState(), testRestaurants.get(0).getZipCode()) != null);
//		
//		testRestaurants.get(0).setStreetAddress("1435 Rizzo Star");
//		assert(rdao.checkForExistingRestaurantByValues(testRestaurants.get(0).getName(), testRestaurants.get(0).getStreetAddress(), testRestaurants.get(0).getCity(), testRestaurants.get(0).getState(), testRestaurants.get(0).getZipCode()) == null);
//	}
//	
//	@Test
//	public void findByKeywordsParameterCheck() {
//		//test words exclusive to each param field to ensure all are being checked
//		
//		for (int i = 0; i < testRestaurants.size(); i++) {
//			log.info(rdao.findById(testRestaurants.get(i).getId()).toString());
//		}
//		
//		List<Restaurant> restaurantName = rdao.findByKeywords("bar", 0, PageRequest.of(0, 250));
//		log.info("Restaurant Name: " + restaurantName.toString());
//		
//		List<Restaurant> restaurantTags = rdao.findByKeywords("texmex", 0, PageRequest.of(0, 250));
//		log.info("Restaurant Tags: " + restaurantTags.toString());
//		
//		List<Restaurant> foodName = rdao.findByKeywords("camarones", 0, PageRequest.of(0, 250));
//		log.info("Food Name: " + foodName.toString());
//		
//		List<Restaurant> foodSummary = rdao.findByKeywords("drizzled", 0, PageRequest.of(0, 250));
//		log.info("Food Summary: " + foodSummary.toString());
//		
//		assert(restaurantName.get(0).getName().equals("Burger Bar") && restaurantName.size() == 1);
//		assert(restaurantTags.get(0).getTags().contains("latin") && restaurantTags.size() == 1);
//		assert(foodName.get(0).getName().equals("Casa Feliz") && foodName.size() == 1);
//		assert(foodSummary.get(0).getName().equals("Burger Bar") && foodSummary.size() == 1);
//		
//	}
	
	@Test
	public void findByKeywordsMultipleWords() {
		
		List<Restaurant> multiple = rdao.findByKeywords("american|burrito", 0, PageRequest.of(0, 250));
		assertEquals(multiple.size(), 2);
	}
	
	@Test
	public void findByKeywordsActiveStatus() {
		
		List<Restaurant> oneOnly = rdao.findByKeywords("american|burrito", 1, PageRequest.of(0, 250));
		assertEquals(oneOnly.size(), 1);
		
		List<Restaurant> both = rdao.findByKeywords("american|burrito", 0, PageRequest.of(0, 250));
		assertEquals(both.size(), 2);
		
	}
	
	@Test
	public void findByKeywordsSortByName() {
		List<Restaurant> results = rdao.findByKeywordsSortByName("american|burrito", 0, PageRequest.of(0, 250)); 
		log.info("Sort By Name: " + results.toString());
		for (int i = 1; i < results.size(); i++) {
			assert(results.get(i).getName().compareToIgnoreCase(results.get(i - 1).getName()) > 0);
		}
	}
	
	@Test
	public void findByKeywordsSortByRating() {
		List<Restaurant> results = rdao.findByKeywordsSortByRating("american|burrito", 0, PageRequest.of(0, 250)); 
		log.info("Sort By Rating: " + results.toString());
		for (int i = 1; i < results.size(); i++) {
			assert(results.get(i).getAverageRating() - (results.get(i - 1).getAverageRating()) < 0);
		}
	}
}
