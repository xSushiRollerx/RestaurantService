package com.xsushirollx.sushibyte.restaurantservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xsushirollx.sushibyte.restaurantservice.dao.FoodDAO;
import com.xsushirollx.sushibyte.restaurantservice.dao.RestaurantDAO;
import com.xsushirollx.sushibyte.restaurantservice.dto.FoodDTO;
import com.xsushirollx.sushibyte.restaurantservice.exception.FoodNotFoundException;
import com.xsushirollx.sushibyte.restaurantservice.model.Food;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;

@SpringBootTest
public class FoodServiceTests {


	@Autowired
	FoodService fservice;

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
	public void getOneFoodMenuItemHP() {
		assertEquals(new FoodDTO(testFoods.get(0)), fservice.getOneFoodMenuItem(testFoods.get(0).getId()));
	}

	@Test
	public void addNewFoodMenuItemHP() throws Exception {
		FoodDTO newFood = new FoodDTO(testRestaurants.get(0).getId(), "Gumbo", 17.89,
				"Good ole gumbo made by Princess Tiana herself", 0, 1, "Soup");
		FoodDTO savedFood = fservice.addNewFoodMenuItem(newFood);
		testFoods.add(new Food(savedFood));
	}
	
	@Test
	public void addNewFoodMenuItemSP() throws Exception {
		FoodDTO newFood = new FoodDTO(testFoods.get(0));
		assertThrows(Exception.class, () -> { fservice.addNewFoodMenuItem(newFood);});
	}
	
	@Test
	public void updateFoodMenuItemHP() throws Exception {
		FoodDTO newFood = new FoodDTO(testFoods.get(0));
		newFood.setName("New Name");
		assertEquals("New Name", fservice.updateFood(newFood, newFood.getId()).getName());
	}
	
	@Test
	public void updateFoodMenuItemSP() throws Exception {
		Food addFood = new Food(testRestaurants.get(0).getId(), "Delete", 2.99, "test food to be deleted", 0, 1, "Delete");
		fdao.saveAndFlush(addFood);
		fdao.deleteById(addFood.getId());
		
		FoodDTO newFood = new FoodDTO(addFood);
		newFood.setName("New Name");
		assertThrows(FoodNotFoundException.class, () -> {  fservice.updateFood(newFood, newFood.getId());});
	}
	
	@Test
	public void deleteFoodMenuItemHP() throws Exception {
		
		Food newFood = new Food(testRestaurants.get(0).getId(), "Delete", 2.99, "test food to be deleted", 0, 1, "Delete");
		fdao.saveAndFlush(newFood);
		
		assert(fservice.deleteFoodMenuItem(newFood.getId()));
	}
	
	@Test
	public void deleteFoodMenuItemSP() throws Exception {
		Food addFood = new Food(testRestaurants.get(0).getId(), "Delete", 2.99, "test food to be deleted", 0, 1, "Delete");
		fdao.saveAndFlush(addFood);
		fdao.deleteById(addFood.getId());
		
		assertThrows(FoodNotFoundException.class, () -> {  fservice.deleteFoodMenuItem(addFood.getId());});
	}
	

}
