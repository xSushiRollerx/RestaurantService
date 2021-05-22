package com.xsushirollx.sushibyte.restaurantservice.dao;

import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface RestaurantDAO extends JpaRepository<Restaurant, Long> {

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "UPDATE Restaurant restaurant SET restaurant.isActive = 0 WHERE restaurant.id = :id")
	void setInactiveById(@Param("id") Long id);

	@Query(value = "SELECT restaurant from Restaurant restaurant WHERE restaurant.name = :name AND "
			+ "restaurant.streetAddress = :streetAddress AND restaurant.city = :city AND "
			+ "restaurant.state = :state AND restaurant.zipCode = :zipCode ")
	Restaurant checkForExistingRestaurantByValues(@Param("name") String name,
			@Param("streetAddress") String streetAddress, @Param("city") String city, @Param("state") String state,
			@Param("zipCode") Integer zipCode);
	
	@Query(value = "select * from restaurant join food on restaurant.id = food.restaurant_id where "
			+ "(restaurant.name regexp :keywords or restaurant.tags regexp :keywords "
			+ "or food.name regexp :keywords or food.summary regexp :keywords) and (restaurant.is_active = 1 or restaurant.is_active = :active) group by restaurant.id "
			+ "order by restaurant.id", nativeQuery = true)
	List<Restaurant> findByKeywords(@Param("keywords") String keywords, @Param("active") Integer active, Pageable pageRequest);
	
	@Query(value = "select * from restaurant join food on restaurant.id = food.restaurant_id where "
			+ "(restaurant.name regexp :keywords or restaurant.tags regexp :keywords "
			+ "or food.name regexp :keywords or food.summary regexp :keywords) and (restaurant.is_active = 1 or restaurant.is_active = :active) "
			+ "and (restaurant.price_category = :one or restaurant.price_category = :two or restaurant.price_category = :three or restaurant.price_category = :four)"
			+ "and (restaurant.average_rating >= :rating) group by restaurant.id "
			+ "order by restaurant.name", nativeQuery = true)
	List<Restaurant> findByKeywordsSortByName(@Param("keywords") String keywords, @Param("active") Integer active, @Param("rating") Double rating, 
			@Param("one") Integer one, @Param("two") Integer two, @Param("three") Integer three, @Param("four") Integer four, Pageable pageRequest);
	
	@Query(value = "select * from restaurant join food on restaurant.id = food.restaurant_id where "
			+ "(restaurant.name regexp :keywords or restaurant.tags regexp :keywords or food.name regexp :keywords or food.summary regexp :keywords) "
			+ "and (restaurant.is_active = 1 or restaurant.is_active = :active) "
			+ "and (restaurant.average_rating >= :rating) "
			+ "and (restaurant.price_category = :one or restaurant.price_category = :two or restaurant.price_category = :three or restaurant.price_category = :four)"
			+ " group by restaurant.id order by restaurant.average_rating desc", nativeQuery = true)
	List<Restaurant> findByKeywordsSortByRating(@Param("keywords") String keywords, @Param("active") Integer active, @Param("rating") Double rating, 
			@Param("one") Integer one, @Param("two") Integer two, @Param("three") Integer three, @Param("four") Integer four, Pageable pageRequest);

	boolean existsByNameAndStreetAddressAndCityAndStateAndZipCode(String name, String streetAddress, String city,
			String state, Integer zipCode);

	@Query(value = "select * from restaurant where is_active >= :active and average_rating >= :rating  order by average_rating desc", nativeQuery = true)
	List<Restaurant> findAllSortByAverageRating(@Param("active") Integer active, @Param("rating") Double rating, Pageable pageRequest);

	List<Restaurant> findByIsActiveGreaterThanEqualAndAverageRatingGreaterThanEqual(Integer active, Double rating, Pageable pageRequest);

	
}
