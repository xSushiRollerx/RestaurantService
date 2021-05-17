package com.xsushirollx.sushibyte.restaurantservice.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.xsushirollx.sushibyte.restaurantservice.model.RelevanceSearch;

public interface RelevanceSearchDAO extends JpaRepository<RelevanceSearch, Long>{
	List<RelevanceSearch> findByKeywordsSortByRelevance(@Param("keywords") String keywords, @Param("active")Integer active, Pageable pageRequest);

}
