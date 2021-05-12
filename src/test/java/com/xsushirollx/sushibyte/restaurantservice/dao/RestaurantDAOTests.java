package com.xsushirollx.sushibyte.restaurantservice.dao;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RestaurantDAOTests {

	@Autowired
	private RestaurantDAO rdao;
	
	private Logger log = Logger.getLogger("RestaurantDAOTests");
	
	@Test
	public void findByKeywords() {
		log.info(rdao.findAll().toString());
		log.info(rdao.findByKeyword("queen").toString());
	}
}
