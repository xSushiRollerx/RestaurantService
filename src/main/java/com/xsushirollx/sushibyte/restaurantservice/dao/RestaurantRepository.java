package com.xsushirollx.sushibyte.restaurantservice.dao;

import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

//    @Modifying(flushAutomatically = true)
    @Modifying(clearAutomatically = true)
//    @Modifying
    @Transactional
    @Query(value = "UPDATE Restaurant restaurant SET restaurant.isActive = 0 WHERE restaurant.id = :id")
    void setInactiveById(@Param("id") Long id);



    @Query(value = "SELECT restaurant from Restaurant restaurant WHERE restaurant.name = :name AND " +
            "restaurant.streetAddress = :streetAddress AND restaurant.city = :city AND " +
            "restaurant.state = :state AND restaurant.zipCode = :zipCode ")
    Restaurant checkForExistingRestaurantByValues(@Param("name") String name,
                                                  @Param("streetAddress") String streetAddress,
                                                  @Param("city") String city,
                                                  @Param("state") String state,
                                                  @Param("zipCode") Integer zipCode);


}
