package com.xsushirollx.sushibyte.restaurantservice.dao;

import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xsushirollx.sushibyte.restaurantservice.model.Food;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;

@SpringBootTest
public class RestaurantDAOTests {

	@Autowired
	private RestaurantDAO rdao;
	
	private Logger log = Logger.getLogger("RestaurantDAOTests");
	
//	@BeforeEach
//	public void setUp() {
//		Restaurant r1 = new Restaurant("Burger Bar", 2, 3.4, "american, burger, bar, milkshakes", 1, "1958 Sandy Ln", "Danny", "CA", 45678);
//		rdao.save(r1);
//		
//		Food f1 = new Food()
//		
//	}
//	
//	@AfterEach
//	public void tearDown() {
//		
//	}
	
	@Test
	public void findByKeywords() {
		log.info(rdao.findAll().toString());
		log.info(rdao.findByKeyword("queen", "2").toString());
	}
}
