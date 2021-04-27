package com.xsushirollx.sushibyte.restaurantservice.controller;

import com.xsushirollx.sushibyte.restaurantservice.dao.RestaurantRepository;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = RestaurantController.class)
public class RestaurantControllerTest {

    @MockBean
    private RestaurantRepository repository;

    @Autowired
    private MockMvc mockMvc;

/*//Will need to provide @MockBean 's in the future for security and json tokens
    @MockBean
    futureSecurityService

    @MockBean
    futureJwtProvider*/

    @Test
    @DisplayName("Should return a List of Restaurants")
    public void getAllRestaurants() throws Exception {

        Restaurant restaurant1 = new Restaurant("Pizza town",
                1, 2.3,
                "Pizza and salads", 1, "123 st",
                "Hialeah", "FL", 12345);
        restaurant1.setId(1L);

        Restaurant restaurant2 = new Restaurant("Burger town",
                2, 3.2,
                "Burgers and fries", 1, "123 st",
                "Hialeah", "FL", 12345);
        restaurant2.setId(2L);

        Mockito.when(repository.findAll()).
                thenReturn(Arrays.asList(restaurant1,restaurant2));


        mockMvc.perform(MockMvcRequestBuilders.get("/restaurant"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.greaterThan(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)));

    }

    @Test
    @DisplayName("Should return a single Restaurant")
    public void getOneRestaurant() throws Exception {

        Restaurant restaurant1 = new Restaurant("Pizza town",
                1, 2.3,
                "Pizza and salads", 1, "123 st",
                "Hialeah", "FL", 12345);
        restaurant1.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(java.util.Optional.of((restaurant1)));

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurant/1"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.greaterThan(0)));
    }

    @Test
    @DisplayName("Should add a new Restaurant")
    void addNewRestaurant() throws Exception {

        String mockRestaurantJson = "    {\n" +
                "        \"id\": 8,\n" +
                "        \"name\": \"Pizza Hut\",\n" +
                "        \"averageRating\": 3.1,\n" +
                "        \"tags\": \"pepperoni\",\n" +
                "        \"isActive\": 1,\n" +
                "        \"priceCategory\": 2,\n" +
                "        \"streetAddress\": \"123 Pizza st\",\n" +
                "        \"city\": \"Agusta\",\n" +
                "        \"state\": \"GA\",\n" +
                "        \"zipCode\": 33172\n" +
                "    },";

        Restaurant restaurant1 = new Restaurant("Pizza town",
                1, 2.3,
                "Pizza and salads", 1, "123 st",
                "Hialeah", "FL", 12345);
       restaurant1.setId(1L);

        Mockito.when(repository.save(restaurant1)).thenReturn(restaurant1);

        /*MvcResult result =*/
        mockMvc.perform(MockMvcRequestBuilders.post("/restaurant")

                // Send mockRestaurantJson as RequestBody to post /restaurant
                .accept(MediaType.APPLICATION_JSON)
                .content(mockRestaurantJson)

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andReturn();
/*        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/restaurant")
                .accept(MediaType.APPLICATION_JSON)
                .content(mockRestaurantJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());*/

    }

    @Test
    @DisplayName("Should Update a current Restaurant")
    void updateRestaurant() throws Exception {

        String mockRestaurantJson = "    {\n" +
                "        \"id\": 8,\n" +
                "        \"name\": \"Pizza Hut\",\n" +
                "        \"averageRating\": 3.1,\n" +
                "        \"tags\": \"pepperoni\",\n" +
                "        \"isActive\": 1,\n" +
                "        \"priceCategory\": 2,\n" +
                "        \"streetAddress\": \"123 Pizza st\",\n" +
                "        \"city\": \"Agusta\",\n" +
                "        \"state\": \"GA\",\n" +
                "        \"zipCode\": 33172\n" +
                "    },";

        Restaurant restaurant1 = new Restaurant("Pizza town",
                1, 2.3,
                "Pizza and salads", 1, "123 st",
                "Hialeah", "FL", 12345);
        restaurant1.setId(8L);

        Mockito.when(repository.findById(8L)).thenReturn(java.util.Optional.of(restaurant1));

        Mockito.when(repository.save(restaurant1)).thenReturn(restaurant1);

        /*MvcResult result =*/
        mockMvc.perform(MockMvcRequestBuilders.put("/restaurant/8")

                // Send mockRestaurantJson as RequestBody to post /restaurant
                .accept(MediaType.APPLICATION_JSON)
                .content(mockRestaurantJson)

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

    }

    @Test
    @DisplayName("Should change the Restaurant isActive Status to zero")
    void setRestaurantToInActive() throws Exception {

        Restaurant restaurant1 = new Restaurant("Pizza town",
                1, 2.3,
                "Pizza and salads", 0, "123 st",
                "Hialeah", "FL", 12345);
        restaurant1.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(java.util.Optional.of((restaurant1)));

        mockMvc.perform(MockMvcRequestBuilders.delete("/restaurant/1"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.greaterThan(0)));

    }
}