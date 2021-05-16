package com.xsushirollx.sushibyte.restaurantservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsushirollx.sushibyte.restaurantservice.dto.RestaurantDTO;
import com.xsushirollx.sushibyte.restaurantservice.service.RestaurantService;

@AutoConfigureMockMvc
@SpringBootTest
public class RestaurantControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	RestaurantService rservice;
	
	@Autowired
	ObjectMapper objectMapper;
	
	RestaurantDTO r = new RestaurantDTO("Burger Bar", 3, 3.4, "american, burger, bar, milkshakes", 1, "1958 Sandy Ln", "Danny", "CA", 45678);
	
	@Test
	public void getAllRestaurants200() {
		when(rservice.getAllRestaurants(Mockito.anyInt(), Mockito.any(String.class))).thenReturn(new ArrayList<RestaurantDTO>());
		
		try {
			mockMvc.perform(get("/restaurants/all/1?sort=alphabetically").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getRestaurant200() {
		when(rservice.findById(Mockito.anyLong())).thenReturn(new RestaurantDTO());
		
		
		try {
			mockMvc.perform(get("/restaurant/1").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getRestaurant400() {
		when(rservice.findById(Mockito.anyLong())).thenReturn(null);
		
		
		try {
			mockMvc.perform(get("/restaurant/1").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void addNewRestaurant201() {
		when(rservice.addNewRestaurant(Mockito.any(RestaurantDTO.class))).thenReturn(true);
		
		RestaurantDTO r = new RestaurantDTO("Burger Bar", 3, 3.4, "american, burger, bar, milkshakes", 1, "1958 Sandy Ln", "Danny", "CA", 45678);
		
		try {
			mockMvc.perform(post("/restaurant").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(r)))
					.andExpect(status().isCreated());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void addNewRestaurant400() {
		when(rservice.addNewRestaurant(Mockito.any(RestaurantDTO.class))).thenReturn(false);
		
		try {
			mockMvc.perform(post("/restaurant").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new RestaurantDTO())))
					.andExpect(status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updadteRestaurant201() {
		when(rservice.updateRestaurant(Mockito.any(RestaurantDTO.class), Mockito.anyLong())).thenReturn(true);
		
		try {
			mockMvc.perform(put("/restaurant/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(r)))
					.andExpect(status().isNoContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updadteRestaurant400() {
		when(rservice.updateRestaurant(Mockito.any(RestaurantDTO.class), Mockito.anyLong())).thenReturn(false);
		
		try {
			mockMvc.perform(put("/restaurant/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(r)))
					.andExpect(status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteRestaurant201() {
		when(rservice.setRestaurantToInActive(Mockito.anyLong())).thenReturn(true);
		
		try {
			mockMvc.perform(delete("/restaurant/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(r)))
					.andExpect(status().isNoContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteRestaurant400() {
		when(rservice.setRestaurantToInActive(Mockito.anyLong())).thenReturn(false);
		
		try {
			mockMvc.perform(delete("/restaurant/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(r)))
					.andExpect(status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void search200() {
		when(rservice.search(Mockito.any(Map.class), Mockito.any(String[].class), Mockito.any(String.class))).thenReturn(new ArrayList<RestaurantDTO>());
		
		try {
			mockMvc.perform(get("/restaurants/?sort=rating&&keywords=queen,burger").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(r)))
					.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}