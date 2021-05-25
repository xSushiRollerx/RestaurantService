package com.xsushirollx.sushibyte.restaurantservice.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xsushirollx.sushibyte.restaurantservice.model.RelevanceSearch;

public interface RelevanceSearchDAO extends JpaRepository<RelevanceSearch, Long>{

	@Query(value = "select restaurant.*, (sum(if(food.name regexp :keywords, 1 ,0)) + sum(if(food.summary regexp :keywords, 1.5, 0)) + "
				+ "if(restaurant.name regexp :keywords, 1, 0) + if(restaurant.tags regexp :keywords, 2, 0)) as relevance from restaurant "
				+ "left join food on restaurant.id = food.restaurant_id where (restaurant.is_active >= :active) "
				+ "and (restaurant.average_rating >= :rating) "
				+ "and (restaurant.price_category = :one or restaurant.price_category = :two or restaurant.price_category = :three or restaurant.price_category = :four)"
				+ "group by restaurant.id order by relevance desc", nativeQuery = true, 
				countQuery = "select count(total) as total from (select count(restaurant.id) as total " 
						+ "from restaurant left join food on restaurant.id = food.restaurant_id " 
						+ "where (restaurant.is_active >= :active) " 
						+ "and (food.name regexp :keywords or food.summary regexp :keywords or restaurant.name regexp :keywords or restaurant.tags regexp :keywords) " 
						+ "and (restaurant.average_rating >= :rating) " 
						+ "and (restaurant.price_category = :one or restaurant.price_category = :two or restaurant.price_category = :three or restaurant.price_category = :four) " 
						+ "group by restaurant.id)  as sub")
	Page<RelevanceSearch> findByKeywordsSortByRelevance(@Param("keywords") String keywords, @Param("rating") Double rating, @Param("active")Integer active, 
			@Param("one") Integer one, @Param("two") Integer two, @Param("three") Integer three, @Param("four") Integer four, Pageable pageRequest);
	
}
