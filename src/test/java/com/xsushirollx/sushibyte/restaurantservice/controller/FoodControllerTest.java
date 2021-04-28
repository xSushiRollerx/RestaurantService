package com.xsushirollx.sushibyte.restaurantservice.controller;

import com.xsushirollx.sushibyte.restaurantservice.dao.FoodRepository;
import com.xsushirollx.sushibyte.restaurantservice.dao.RestaurantRepository;
import com.xsushirollx.sushibyte.restaurantservice.model.Food;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;
import com.xsushirollx.sushibyte.restaurantservice.service.FoodControllerService;
import com.xsushirollx.sushibyte.restaurantservice.service.RestaurantControllerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = FoodController.class)
public class FoodControllerTest {

    @MockBean
    private FoodRepository repository;
    @MockBean
    private RestaurantControllerService restaurantControllerService;
    @MockBean
    private RestaurantRepository restaurantRepository;
    @MockBean
    private FoodControllerService foodControllerService;
    @MockBean
    private FoodController foodController;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("Should return a List of Food Menu Items")
    void getAllFoodMenuItems() throws Exception {

        Food food1 = new Food(1,"Pizza slice",
                1.22,"Pic goes here","chesse slice", 2,
                1, 1);
        food1.setId(1L);

        Food food2 = new Food(2,"Pizza slice",
                1.42,"Pic goes here","pep slice", 3,
                1, 1);
        food2.setId(2L);

        Mockito.when(repository.findAll()).
                thenReturn(Arrays.asList(food1,food2));


        mockMvc.perform(MockMvcRequestBuilders.get("/food"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.greaterThan(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)));


    }

    @Test
    @DisplayName("Should return a single Food Menu Item")
    void getOneFoodMenuItem() throws Exception {

        Food food1 = new Food(1,"Pizza slice",
                1.22,"Pic goes here","chesse slice", 2,
                1, 1);
        food1.setId(1L);


        Mockito.when(repository.findById(1L)).thenReturn(java.util.Optional.of((food1)));

        mockMvc.perform(MockMvcRequestBuilders.get("/food/1"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.greaterThan(0)));
    }

    @Test
    @DisplayName("Should add a new Food Menu Item")
    void addNewFoodMenuItem() throws Exception {

        String mockFoodJson = "{\"id\": 7, \"restaurantID\": 5, \"name\": \"Fries\", \"cost\": 91.18, \"image\": \"pic here\",    \"summary\": \"tasty food\", \"special\": 3, \"isActive\": 0, \"category\": 2}";

        Food food1 = new Food(1,"Pizza slice",
                1.22,"Pic goes here","chesse slice", 2,
                1, 1);
        food1.setId(1L);

        Mockito.when(repository.save(food1)).thenReturn(food1);

        /*MvcResult result =*/
        mockMvc.perform(MockMvcRequestBuilders.post("/food")

                // Send mockRestaurantJson as RequestBody to post /restaurant
                .accept(MediaType.APPLICATION_JSON)
                .content(mockFoodJson)

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andReturn();


    }

    @Test
    @DisplayName("Should Update a current Food Menu Item")
    void updateFood() throws Exception {

        String mockFoodJson = "{\"id\": 7, \"restaurantID\": 5, \"name\": \"Fries\", \"cost\": 91.18, \"image\": \"pic here\",    \"summary\": \"tasty food\", \"special\": 3, \"isActive\": 0, \"category\": 2}";


        Food food1 = new Food(5,"Fries",
                1.22,"Pic goes here","chesse slice", 2,
                1, 1);
        food1.setId(77L);
        Restaurant restaurant1 = new Restaurant("Pizza town",
                1, 2.3,
                "Pizza and salads", 1, "123 st",
                "Hialeah", "FL", 12345);
        restaurant1.setId(5L);

        Mockito.when(repository.findById(8L)).thenReturn(java.util.Optional.of(food1));

        Mockito.when(repository.save(food1)).thenReturn(food1);

        Mockito.when(restaurantRepository.findById(food1.getRestaurantID().longValue()))
                .thenReturn(java.util.Optional.of(restaurant1));

        /*MvcResult result =*/
        mockMvc.perform(MockMvcRequestBuilders.put("/food/8")

                // Send mockRestaurantJson as RequestBody to post /restaurant
                .accept(MediaType.APPLICATION_JSON)
                .content(mockFoodJson)

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

    }

    @Test
    @DisplayName("Should change the FoodMenuItem isActive Status to zero")
    void setFoodMenuItemToInActive() throws Exception {

        Food food1 = new Food(1,"Pizza slice",
                1.22,"Pic goes here","chesse slice", 2,
                1, 1);
        food1.setId(1L);


        Mockito.when(repository.findById(1L)).thenReturn(java.util.Optional.of((food1)));

        mockMvc.perform(MockMvcRequestBuilders.delete("/food/1"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.greaterThan(0)));

    }
}