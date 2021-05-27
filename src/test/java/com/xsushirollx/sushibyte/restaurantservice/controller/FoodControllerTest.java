package com.xsushirollx.sushibyte.restaurantservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsushirollx.sushibyte.restaurantservice.dto.FoodDTO;
import com.xsushirollx.sushibyte.restaurantservice.exception.FoodNotFoundException;
import com.xsushirollx.sushibyte.restaurantservice.security.JWTUtil;
import com.xsushirollx.sushibyte.restaurantservice.service.FoodService;
import com.xsushirollx.sushibyte.restaurantservice.service.RestaurantService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class FoodControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FoodService fservice;
    
    @Autowired
	JWTUtil util;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	RestaurantService rservice;
    
    @Test
    @DisplayName("Get Food Item 200")
    void getOneFoodItem200() throws Exception {
    	String token  = "Bearer " + util.generateToken("98");
    	
    	when(fservice.getOneFoodMenuItem(Mockito.anyLong())).thenReturn(new FoodDTO());
    	
    	mockMvc.perform(get("/food/112").contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
		.andExpect(status().isOk());

    }
    
    @Test
    @DisplayName("Get Food Item 404")
    void getOneFoodItem404() throws Exception {
    	String token  = "Bearer " + util.generateToken("98");
    	
    	when(fservice.getOneFoodMenuItem(Mockito.anyLong())).thenThrow(FoodNotFoundException.class);
    	
    	mockMvc.perform(get("/food/112").contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
		.andExpect(status().isNotFound());

    }
    
    @Test
    @DisplayName("Get Food Item 500")
    void getOneFoodItem500() throws Exception {
    	String token  = "Bearer " + util.generateToken("98");
    	
    	when(fservice.getOneFoodMenuItem(Mockito.anyLong())).thenThrow(NumberFormatException.class);
    	
    	mockMvc.perform(get("/food/112").contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
		.andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Add Food Item 201")
    void addNewFoodItem201() throws Exception {
    	String token  = "Bearer " + util.generateToken("98");
    	
    	when(fservice.addNewFoodMenuItem(Mockito.any(FoodDTO.class))).thenReturn(new FoodDTO((long) 1,"Delete", 2.99, "test food to be deleted", 0, 1, "Delete"));
    	
    	mockMvc.perform(post("/food").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FoodDTO())).header("Authorization", token))
		.andExpect(status().isCreated());

    }
    
    @Test
    @DisplayName("Add Food Item 400")
    void addNewFoodItem400() throws Exception {
    	String token  = "Bearer " + util.generateToken("98");
    	
    	when(fservice.addNewFoodMenuItem(Mockito.any(FoodDTO.class))).thenThrow(new Exception("Item Could Not Be Created. Food Item Already Exists."));
    	
    	mockMvc.perform(post("/food").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FoodDTO())).header("Authorization", token))
		.andExpect(status().isBadRequest());

    }
    
    @Test
    @DisplayName("Add Food Item 500")
    void addNewFoodItem500() throws Exception {
    	String token  = "Bearer " + util.generateToken("98");
    	
    	when(fservice.addNewFoodMenuItem(Mockito.any(FoodDTO.class))).thenThrow(NumberFormatException.class);
    	
    	mockMvc.perform(post("/food").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FoodDTO())).header("Authorization", token))
		.andExpect(status().isInternalServerError());

    }
    
    @Test
    @DisplayName("Update Food Item 200")
    void updateFoodItem200() throws Exception {
    	String token  = "Bearer " + util.generateToken("98");
    	
    	when(fservice.updateFood(Mockito.any(FoodDTO.class), Mockito.anyLong())).thenReturn(new FoodDTO());
    	
    	mockMvc.perform(put("/food/77").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FoodDTO())).header("Authorization", token))
		.andExpect(status().isOk());

    }
    
    @Test
    @DisplayName("Update Food Item 500")
    void updateFoodItem500() throws Exception {
    	String token  = "Bearer " + util.generateToken("98");
    	
    	when(fservice.updateFood(Mockito.any(FoodDTO.class), Mockito.anyLong())).thenThrow(NumberFormatException.class);
    	
    	mockMvc.perform(put("/food/12").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FoodDTO())).header("Authorization", token))
		.andExpect(status().isInternalServerError());

    }
    
    @Test
    @DisplayName("Update Food Item 404")
    void updateFoodItem404() throws Exception {
    	String token  = "Bearer " + util.generateToken("98");
    	
    	when(fservice.updateFood(Mockito.any(FoodDTO.class), Mockito.anyLong())).thenThrow(FoodNotFoundException.class);
    	
    	mockMvc.perform(put("/food/435").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FoodDTO())).header("Authorization", token))
		.andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Delete Food Item 200")
    void deleteFoodItem200() throws Exception {
    	String token = "Bearer " + util.generateToken("98");
    	
    	when(fservice.deleteFoodMenuItem(Mockito.anyLong())).thenReturn(true);
    	
    	mockMvc.perform(delete("/food/435").contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
		.andExpect(status().isOk());

    }

    @Test
    @DisplayName("Delete Food Item 404")
    void deleteFoodItem404() throws Exception {
    	String token = "Bearer " + util.generateToken("98");
    	
    	when(fservice.deleteFoodMenuItem(Mockito.anyLong())).thenThrow(FoodNotFoundException.class);
    	
    	mockMvc.perform(delete("/food/35").contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
		.andExpect(status().isNotFound());

    }
    
    @Test
    @DisplayName("Delete Food Item 500")
    void deleteFoodItem500() throws Exception {
    	String token = "Bearer " + util.generateToken("98");
    	
    	when(fservice.deleteFoodMenuItem(Mockito.anyLong())).thenThrow(NumberFormatException.class);
    	
    	mockMvc.perform(delete("/food/24").contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
		.andExpect(status().isInternalServerError());

    }
}