package com.xsushirollx.sushibyte.restaurantservice.service;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RestaurantServiceTests {
	
	@Autowired
	private RestaurantService rservice;
	
	private Logger log = Logger.getLogger("RestaurantServiceTests");

	
	@Test
	public void findByKeywords() {
		String[] keywords = {"queen", "bakery"};
		log.info(rservice.findByKeywords(keywords, "CUSTOMER", 0).toString());
	}
}
