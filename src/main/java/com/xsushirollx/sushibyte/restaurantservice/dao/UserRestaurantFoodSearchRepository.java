package com.xsushirollx.sushibyte.restaurantservice.dao;

import com.xsushirollx.sushibyte.restaurantservice.model.Food;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;
import com.xsushirollx.sushibyte.restaurantservice.model.UserRestaurantFoodSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRestaurantFoodSearchRepository extends JpaRepository<UserRestaurantFoodSearch, Long> {

    @Query(value = "select restaurant.name, food.name,food.image, food.summary,food.cost, food.special," +
            "food.category,restaurant.averageRating , restaurant.tags,restaurant.priceCategory, " +
            "restaurant.streetAddress, restaurant.city, restaurant.state, restaurant.zipCode from " +
            "Food food join Restaurant restaurant on restaurant.id = food.restaurantID where " +
            "restaurant.isActive=1 and food.isActive =1")
    List<?> returnAllMenuItems();

}
