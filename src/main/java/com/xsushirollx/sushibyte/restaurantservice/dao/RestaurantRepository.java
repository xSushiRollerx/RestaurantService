package com.xsushirollx.sushibyte.restaurantservice.dao;

import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

//    @Modifying(flushAutomatically = true)
    @Modifying(clearAutomatically = true)
//    @Modifying
    @Transactional
    @Query(value = "UPDATE Restaurant restaurant SET restaurant.isActive = 0 WHERE restaurant.id = :id")
    void setInactiveById(@Param("id") Long id);
}
