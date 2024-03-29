package com.xsushirollx.sushibyte.restaurantservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsushirollx.sushibyte.restaurantservice.dao.UserDAO;
import com.xsushirollx.sushibyte.restaurantservice.dto.RestaurantDTO;
import com.xsushirollx.sushibyte.restaurantservice.exception.RestaurantCreationException;
import com.xsushirollx.sushibyte.restaurantservice.exception.RestaurantNotFoundException;
import com.xsushirollx.sushibyte.restaurantservice.model.Food;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;
import com.xsushirollx.sushibyte.restaurantservice.model.User;
import com.xsushirollx.sushibyte.restaurantservice.security.JWTUtil;
import com.xsushirollx.sushibyte.restaurantservice.service.RestaurantService;

@AutoConfigureMockMvc
@SpringBootTest
public class RestaurantControllerTest {
	
	private Logger log = Logger.getLogger("RestaurantControllerTests");
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	RestaurantService rservice;
	
	@MockBean
	UserDAO udao;

	RestaurantService realrservice = new RestaurantService();
	
	@Autowired
	JWTUtil util;
	
	@Autowired
	ObjectMapper objectMapper;
	
	RestaurantDTO r = new RestaurantDTO("Burger Bar", 3, 3.4, "american, burger, bar, milkshakes", 1, "1958 Sandy Ln", "Danny", "CA", 45678);

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
			testRestaurants.get(i).setId((long) i);
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
			testFoods.get(i).setId((long) i);
		}
	}

	
	@SuppressWarnings("unchecked")
	@Test
	public void getAllRestaurants200() {
		List<RestaurantDTO> result = new ArrayList<>();
		when(rservice.getAllRestaurants(Mockito.any(Map.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyDouble(), Mockito.any(String.class), Mockito.anyInt())).thenReturn(result);
		try {
			mockMvc.perform(get("/restaurants/all/1?sort=alphabetically").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getAllRestaurants500() {
		when(rservice.getAllRestaurants(Mockito.any(Map.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyDouble(), Mockito.any(String.class), Mockito.anyInt()))
		.thenThrow(NumberFormatException.class);
		
		try {
			mockMvc.perform(get("/restaurants/all/1?sort=alphabetically").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isInternalServerError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getAllRestaurants403() {
		String token  = "Bearer " + util.generateToken("96");
		List<RestaurantDTO> result = new ArrayList<>();
		when(rservice.getAllRestaurants(Mockito.any(Map.class), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyDouble(), Mockito.any(String.class), Mockito.anyInt())).thenReturn(result);
		when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 96, 1)));
		
		try {
			mockMvc.perform(get("/restaurants/all/1?sort=alphabetically&&active=2").contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
					.andExpect(status().isForbidden());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getRestaurant200() {
		when(rservice.findById(Mockito.anyLong())).thenReturn(new RestaurantDTO(testRestaurants.get(0), null, null));
		
		
		try {
			mockMvc.perform(get("/restaurant/1").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getRestaurant404() {
		String token  = "Bearer " + util.generateToken("98");
		when(rservice.findById(Mockito.anyLong())).thenThrow(RestaurantNotFoundException.class);
		when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
		
		
		try {
			mockMvc.perform(get("/restaurant/" + testRestaurants.get(0).getId()).contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
					.andExpect(status().isNotFound());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getRestaurant400() {
		String token  = "Bearer " + util.generateToken("98");
		when(rservice.findById(Mockito.anyLong())).thenThrow(NumberFormatException.class);
		when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
		
		
		
		try {
			mockMvc.perform(get("/restaurant/" + testRestaurants.get(0).getId()).contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
					.andExpect(status().isInternalServerError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getRestaurant403() {
		String token  = "Bearer " + util.generateToken("96");
		when(rservice.findById(Mockito.anyLong())).thenReturn(new RestaurantDTO(testRestaurants.get(1), null, null));
		when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 96, 1)));
		log.info(testRestaurants.get(1).toString());
		
		try {
			mockMvc.perform(get("/restaurant/"+ testRestaurants.get(1).getId()).contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
					.andExpect(status().isForbidden());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void addNewRestaurant201() {
		String token  = "Bearer " + util.generateToken("98");
		when(rservice.addNewRestaurant(Mockito.any(RestaurantDTO.class))).thenReturn(new RestaurantDTO());
		when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
		
		RestaurantDTO r = new RestaurantDTO("Burger Bar", 3, 3.4, "american, burger, bar, milkshakes", 1, "1958 Sandy Ln", "Danny", "CA", 45678);
		
		try {
			mockMvc.perform(post("/restaurant").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(r)).header("Authorization", token))
					.andExpect(status().isCreated());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void addNewRestaurant400() {
		String token  = "Bearer " + util.generateToken("98");
		when(rservice.addNewRestaurant(Mockito.any(RestaurantDTO.class))).thenThrow(new RestaurantCreationException());
		when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
		
		RestaurantDTO r = new RestaurantDTO("Burger Bar", 3, 3.4, "american, burger, bar, milkshakes", 1, "1958 Sandy Ln", "Danny", "CA", 45678);
		
		try {
			mockMvc.perform(post("/restaurant").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(r)).header("Authorization", token))
					.andExpect(status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void addNewRestaurant500() {
		String token  = "Bearer " + util.generateToken("98");
		when(rservice.addNewRestaurant(Mockito.any(RestaurantDTO.class))).thenThrow(NumberFormatException.class);
		when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
		
		RestaurantDTO r = new RestaurantDTO("Burger Bar", 3, 3.4, "american, burger, bar, milkshakes", 1, "1958 Sandy Ln", "Danny", "CA", 45678);
		
		try {
			mockMvc.perform(post("/restaurant").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(r)).header("Authorization", token))
					.andExpect(status().isInternalServerError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updadteRestaurant200() {
		String token  = "Bearer " + util.generateToken("98");
		when(rservice.updateRestaurant(Mockito.any(RestaurantDTO.class), Mockito.anyLong())).thenReturn(new RestaurantDTO());
		when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
		try {
			mockMvc.perform(put("/restaurant/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", token).content(objectMapper.writeValueAsString(r)))
					.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateRestaurant400() {
		String token  = "Bearer " + util.generateToken("98");
		when(rservice.updateRestaurant(Mockito.any(RestaurantDTO.class), Mockito.anyLong())).thenThrow(new RestaurantNotFoundException((long) 2));
		when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
		
		try {
			mockMvc.perform(put("/restaurant/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", token).content(objectMapper.writeValueAsString(r)))
					.andExpect(status().isNotFound());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateRestaurant500() {
		String token  = "Bearer " + util.generateToken("98");
		when(rservice.updateRestaurant(Mockito.any(RestaurantDTO.class), Mockito.anyLong())).thenThrow(NumberFormatException.class);
		when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
		
		try {
			mockMvc.perform(put("/restaurant/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", token).content(objectMapper.writeValueAsString(r)))
					.andExpect(status().isInternalServerError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateRestaurant403() {
		String token  = "Bearer " + util.generateToken("96");
		when(rservice.updateRestaurant(Mockito.any(RestaurantDTO.class), Mockito.anyLong())).thenReturn(new RestaurantDTO());
		when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 96, 1)));
		
		try {
			mockMvc.perform(put("/restaurant/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", token).content(objectMapper.writeValueAsString(r)))
					.andExpect(status().isForbidden());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteRestaurant200() {
		String token  = "Bearer " + util.generateToken("98");
		when(rservice.setRestaurantToInActive(Mockito.anyLong())).thenReturn(new RestaurantDTO());
		when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
		
		try {
			mockMvc.perform(delete("/restaurant/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", token).content(objectMapper.writeValueAsString(r)))
					.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteRestaurant400() {
		String token  = "Bearer " + util.generateToken("98");
		when(rservice.setRestaurantToInActive(Mockito.anyLong())).thenThrow(new RestaurantNotFoundException((long) 2));
		when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
		
		try {
			mockMvc.perform(delete("/restaurant/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", token).content(objectMapper.writeValueAsString(r)))
					.andExpect(status().isNotFound());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteRestaurant500() {
		String token  = "Bearer " + util.generateToken("98");
		when(rservice.setRestaurantToInActive(Mockito.anyLong())).thenThrow(NumberFormatException.class);
		when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
		
		try {
			mockMvc.perform(delete("/restaurant/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", token).content(objectMapper.writeValueAsString(r)))
					.andExpect(status().isInternalServerError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void search403() {
		String token  = "Bearer " + util.generateToken("96");
		when(rservice.search(Mockito.anyInt(), Mockito.any(Map.class), Mockito.anyDouble(), Mockito.anyInt(), Mockito.any(List.class), Mockito.anyInt())).thenReturn(new ArrayList<RestaurantDTO>());
		when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 96, 1)));
		
		try {
			mockMvc.perform(get("/restaurants/0?sort=rating&&keywords=queen,burger&&active=0").contentType(MediaType.APPLICATION_JSON).header("Authorization", token).content(objectMapper.writeValueAsString(r)))
					.andExpect(status().isForbidden());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void search200() {
		String token  = "Bearer " + util.generateToken("96");
		when(rservice.search(Mockito.anyInt(), Mockito.any(Map.class), Mockito.anyDouble(), Mockito.anyInt(), Mockito.any(List.class), Mockito.anyInt())).thenReturn(new ArrayList<RestaurantDTO>());
		when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 96, 1)));
		try {
			mockMvc.perform(get("/restaurants/0?sort=rating&&keywords=queen,burger").contentType(MediaType.APPLICATION_JSON).header("Authorization", token).content(objectMapper.writeValueAsString(r)))
					.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void search500() {
		String token  = "Bearer " + util.generateToken("96");
		when(rservice.search(Mockito.anyInt(), Mockito.any(Map.class), Mockito.anyDouble(), Mockito.anyInt(), Mockito.any(List.class), Mockito.anyInt())).thenThrow(NumberFormatException.class);
		when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 96, 1)));
		try {
			mockMvc.perform(get("/restaurants/0?sort=rating&&keywords=queen,burger").contentType(MediaType.APPLICATION_JSON).header("Authorization", token).content(objectMapper.writeValueAsString(r)))
					.andExpect(status().isInternalServerError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void searchByName200() {
		String token  = "Bearer " + util.generateToken("96");
		when(rservice.searchByNameExclusively(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt())).thenThrow(NumberFormatException.class);
		when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 96, 2)));
		try {
			mockMvc.perform(get("/restaurants/name?page=0&keywords=queen").contentType(MediaType.APPLICATION_JSON).header("Authorization", token).content(objectMapper.writeValueAsString(r)))
					.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}