package com.xsushirollx.sushibyte.restaurantservice.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import static org.mockito.Mockito.when;

import com.xsushirollx.sushibyte.restaurantservice.dao.RelevanceSearchDAO;
import com.xsushirollx.sushibyte.restaurantservice.dao.RestaurantDAO;
import com.xsushirollx.sushibyte.restaurantservice.dto.RestaurantDTO;
import com.xsushirollx.sushibyte.restaurantservice.model.RelevanceSearch;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;

@SpringBootTest
public class RestaurantServiceTests {

	@Autowired
	private RestaurantService rservice;

	private Logger log = Logger.getLogger("RestaurantServiceTests");

	@MockBean
	private RestaurantDAO rdao;
	
	@MockBean
	private RelevanceSearchDAO reldao;

	@Test
	public void getAllRestaurantsSortByName() {
		Map<String, String> params = new HashMap<>();
        params.put("priceCategories", "1, 3, 4");
        when(rdao.findAllSortByName(Mockito.anyInt(), Mockito.anyDouble(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(Pageable.class) )).thenReturn(new TestPage<Restaurant>());
        
		List<RestaurantDTO> results = rservice.getAllRestaurants(params, 0, 5, 1.0, "a-to-z", 0);
		log.info("Sort By Name: " + results.toString());
	}

	@Test
	public void getAllRestaurantsSortByRating() {
		Map<String, String> params = new HashMap<>();
        params.put("priceCategories", "1, 2, 4");
        when(rdao.findAllSortByAverageRating(Mockito.anyInt(), Mockito.anyDouble(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(Pageable.class) )).thenReturn(new TestPage<Restaurant>());
		List<RestaurantDTO> results = rservice.getAllRestaurants(params, 0, 5, 4.0, "ratings", 0);
		log.info("Sort By Rating: " + results.toString());
		
	}

	@Test
	public void findById() {
		when(rdao.findById(Mockito.anyLong())).thenReturn(Optional.of(new Restaurant()));
	}

	@Test
	public void addNewRestaurant() {
		when(rdao.existsByNameAndStreetAddressAndCityAndStateAndZipCode(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(true);
		assert (!rservice.addNewRestaurant(new RestaurantDTO(new Restaurant("Burger Bar", 2, 3.4, "american, burger, bar, milkshakes", 1, "1958 Sandy Ln",
				"Danny", "CA", 45678), null, null)));
	}

	@Test
	public void updateRestaurant() {
		when(rdao.save(Mockito.any(Restaurant.class))).thenReturn(new Restaurant());

	}

	@Test
	public void setRestaurantToInActive() {
		when(rdao.save(Mockito.any(Restaurant.class))).thenReturn(new Restaurant());
	}

	@Test
	public void searchByRatings() {
		log.entering("RestaurantServiceTests", "searchSecurity");

		Map<String, String> params = new HashMap<>();
		params.put("page", "0");
		params.put("sort", "ratings");
		params.put("priceCategories", "1, 3");
		List<String> keywords = new ArrayList<>();
		keywords.add("burgers");
		keywords.add("tacos");
		keywords.add("burritos");

		when(rdao.findByKeywordsSortByRating(Mockito.anyString(), Mockito.anyInt(), Mockito.anyDouble(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(Pageable.class) )).thenReturn(new TestPage<Restaurant>());
		rservice.search(0, params, 1.0, 5, keywords, 0);


	}

	@Test
	public void searchByName() {
		log.entering("RestaurantServiceTests", "searchSecurity");

		Map<String, String> params = new HashMap<>();
		params.put("page", "0");
		params.put("sort", "a-to-z");
		params.put("priceCategories", "1, 3");
		List<String> keywords = new ArrayList<>();
		keywords.add("burgers");
		keywords.add("tacos");
		keywords.add("burritos");

		when(rdao.findByKeywordsSortByName(Mockito.anyString(), Mockito.anyInt(), Mockito.anyDouble(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(Pageable.class) )).thenReturn(new TestPage<Restaurant>());
		rservice.search(0, params, 1.0, 5, keywords, 0);


	}
	
	@Test
	public void searchByRelevance() {
		log.entering("RestaurantServiceTests", "searchSecurity");

		Map<String, String> params = new HashMap<>();
		params.put("page", "0");
		params.put("sort", "relevance");
		params.put("priceCategories", "1, 3");
		List<String> keywords = new ArrayList<>();
		keywords.add("burgers");
		keywords.add("tacos");
		keywords.add("burritos");

		when(reldao.findByKeywordsSortByRelevance(Mockito.anyString(), Mockito.anyDouble(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(Pageable.class) )).thenReturn(new TestPage<RelevanceSearch>());
		rservice.search(0, params, 1.0, 5, keywords, 0);


	}

private class TestPage<U> implements Page<U> {
	private List<U> testRestaurants;
	
	
	TestPage(List<U> page) {
		testRestaurants = new ArrayList<>(page);
	}
	
	public TestPage() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return testRestaurants.size();
	}

	@Override
	public int getNumberOfElements() {
		// TODO Auto-generated method stub
		return testRestaurants.size();
	}

	@Override
	public List<U> getContent() {
		// TODO Auto-generated method stub
		return testRestaurants;
	}

	@Override
	public boolean hasContent() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Sort getSort() {
		// TODO Auto-
		return null;
	}

	@Override
	public boolean isFirst() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isLast() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPrevious() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Pageable nextPageable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pageable previousPageable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<U> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalPages() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public long getTotalElements() {
		// TODO Auto-generated method stub
		return testRestaurants == null ? 0 : testRestaurants.size();
	}

	
	public <V> Page<V> map(Function<? super U, ? extends V> converter) {
		List<V> result = new ArrayList<V>();
		if (testRestaurants == null) return new TestPage<V>(new ArrayList<V>());
		for (U object : testRestaurants) {
			result.add(converter.apply(object));
		}
		log.info(result.toString());
		return new TestPage<V>(result);
	}
	
	
}
	
}
