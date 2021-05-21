package com.xsushirollx.sushibyte.restaurantservice.dao;

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
import com.xsushirollx.sushibyte.restaurantservice.model.RelevanceSearch;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;

@SpringBootTest
public class RelevanceSearchDAOTests {

	@Autowired
	private RestaurantDAO rdao;
	
	@Autowired
	private FoodDAO fdao;
	
	@Autowired
	private RelevanceSearchDAO rsdao;
	
	private Logger log = Logger.getLogger("RelevanceSearchDAOTests");
	
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

	
	@Test
	public void findByKeywordsSortByRelevance() {
		List<RelevanceSearch> results = rsdao.findByKeywordsSortByRelevance("sushi|hello|queen", 3.0, 0, 1, 2, 0, 4, PageRequest.of(2, 10)); 
		log.info("Sort By Relevance: " + results.toString());
		for (int i = 1; i < results.size(); i++) {
			assert(results.get(i).getRelevance() - (results.get(i - 1).getRelevance()) <= 0);
			assert (results.get(i).getAverageRating() >= 3.0);
			assert(results.get(i).getPriceCategory() != 3);
		}
	}
}
