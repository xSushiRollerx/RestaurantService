package com.xsushirollx.sushibyte.restaurantservice.dao;

import com.xsushirollx.sushibyte.restaurantservice.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE Food food SET food.isActive = 0 WHERE food.id = :id")
    void setInactiveById(@Param("id") Long id);

    @Query(value = "select food from Food food where food.name = :name and food.restaurantID = :restaurantID")
    Food checkForExistingFoodByValues(@Param("restaurantID") Integer restaurantID, @Param("name") String name);

}
