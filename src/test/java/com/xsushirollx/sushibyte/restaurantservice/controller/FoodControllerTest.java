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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsushirollx.sushibyte.restaurantservice.dao.UserDAO;
import com.xsushirollx.sushibyte.restaurantservice.dto.FoodDTO;
import com.xsushirollx.sushibyte.restaurantservice.exception.FoodCreationException;
import com.xsushirollx.sushibyte.restaurantservice.exception.FoodNotFoundException;
import com.xsushirollx.sushibyte.restaurantservice.model.User;
import com.xsushirollx.sushibyte.restaurantservice.security.JWTUtil;
import com.xsushirollx.sushibyte.restaurantservice.service.FoodService;
import com.xsushirollx.sushibyte.restaurantservice.service.RestaurantService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

@AutoConfigureMockMvc
@SpringBootTest
public class FoodControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FoodService fservice;
    
    @MockBean
    UserDAO udao;
    
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
    	when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
    	when(fservice.getOneFoodMenuItem(Mockito.anyLong())).thenReturn(new FoodDTO());
    	
    	mockMvc.perform(get("/food/112").contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
		.andExpect(status().isOk());

    }
    
    @Test
    @DisplayName("Get Food Item 404")
    void getOneFoodItem404() throws Exception {
    	String token  = "Bearer " + util.generateToken("98");
    	
    	when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
    	when(fservice.getOneFoodMenuItem(Mockito.anyLong())).thenThrow(FoodNotFoundException.class);
    	
    	mockMvc.perform(get("/food/112").contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
		.andExpect(status().isNotFound());

    }
    
    @Test
    @DisplayName("Get Food Item 500")
    void getOneFoodItem500() {
    	String token  = "Bearer " + util.generateToken("98");
    	
    	when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
    	when(fservice.getOneFoodMenuItem(Mockito.anyLong())).thenThrow(NumberFormatException.class);
    	
    	try {
			mockMvc.perform(get("/food/112").contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
			.andExpect(status().isInternalServerError());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Test
    @DisplayName("Add Food Item 201")
    void addNewFoodItem201() throws Exception {
    	String token  = "Bearer " + util.generateToken("98");
    	
    	when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
    	when(fservice.addNewFoodMenuItem(Mockito.any(FoodDTO.class))).thenReturn(new FoodDTO((long) 1,"Delete", 2.99, "test food to be deleted", 0, 1, "Delete"));
    	
    	mockMvc.perform(post("/food").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FoodDTO())).header("Authorization", token))
		.andExpect(status().isCreated());

    }
    
    @Test
    @DisplayName("Add Food Item 400")
    void addNewFoodItem400() throws Exception {
    	String token  = "Bearer " + util.generateToken("98");
    	
    	when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
    	when(fservice.addNewFoodMenuItem(Mockito.any(FoodDTO.class))).thenThrow(new FoodCreationException());
    	
    	mockMvc.perform(post("/food").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FoodDTO())).header("Authorization", token))
		.andExpect(status().isBadRequest());

    }
    
    @Test
    @DisplayName("Add Food Item 500")
    void addNewFoodItem500() {
    	String token  = "Bearer " + util.generateToken("98");
    	
    	when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
    	when(fservice.addNewFoodMenuItem(Mockito.any(FoodDTO.class))).thenThrow(NullPointerException.class);
    	
    	try {
			mockMvc.perform(post("/food").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FoodDTO())).header("Authorization", token))
			.andExpect(status().isInternalServerError());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    @Test
    @DisplayName("Update Food Item 200")
    void updateFoodItem200() throws Exception {
    	String token  = "Bearer " + util.generateToken("98");
    	
    	when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
    	when(fservice.updateFood(Mockito.any(FoodDTO.class), Mockito.anyLong())).thenReturn(new FoodDTO());
    	
    	mockMvc.perform(put("/food/77").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FoodDTO())).header("Authorization", token))
		.andExpect(status().isOk());

    }
    
    @Test
    @DisplayName("Update Food Item 500")
    void updateFoodItem500() {
    	String token  = "Bearer " + util.generateToken("98");
    	
    	when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
    	when(fservice.updateFood(Mockito.any(FoodDTO.class), Mockito.anyLong())).thenThrow(NumberFormatException.class);
    	
    	try {
			mockMvc.perform(put("/food/12").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FoodDTO())).header("Authorization", token))
			.andExpect(status().isInternalServerError());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    @Test
    @DisplayName("Update Food Item 404")
    void updateFoodItem404() throws Exception {
    	String token  = "Bearer " + util.generateToken("98");
    	
    	when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
    	when(fservice.updateFood(Mockito.any(FoodDTO.class), Mockito.anyLong())).thenThrow(FoodNotFoundException.class);
    	
    	mockMvc.perform(put("/food/435").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new FoodDTO())).header("Authorization", token))
		.andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Delete Food Item 200")
    void deleteFoodItem200() throws Exception {
    	String token = "Bearer " + util.generateToken("98");
    	
    	when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
    	when(fservice.deleteFoodMenuItem(Mockito.anyLong())).thenReturn(true);
    	
    	mockMvc.perform(delete("/food/435").contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
		.andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("Delete Food Item 404")
    void deleteFoodItem404() throws Exception {
    	String token = "Bearer " + util.generateToken("98");
    	
    	when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
    	when(fservice.deleteFoodMenuItem(Mockito.anyLong())).thenThrow(FoodNotFoundException.class);
    	
    	mockMvc.perform(delete("/food/35").contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
		.andExpect(status().isNotFound());

    }
    
    @Test
    @DisplayName("Delete Food Item 500")
    void deleteFoodItem500() {
    	String token = "Bearer " + util.generateToken("98");
    	
    	when(udao.findById(Mockito.anyLong())).thenReturn(Optional.of(new User((long) 98, 2)));
    	when(fservice.deleteFoodMenuItem(Mockito.anyLong())).thenThrow(NumberFormatException.class);
    	
    	try {
			mockMvc.perform(delete("/food/24").contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
			.andExpect(status().isInternalServerError());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}