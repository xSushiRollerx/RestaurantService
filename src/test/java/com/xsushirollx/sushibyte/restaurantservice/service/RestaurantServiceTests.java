package com.xsushirollx.sushibyte.restaurantservice.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import static org.mockito.Mockito.when;

import com.xsushirollx.sushibyte.restaurantservice.dao.RestaurantDAO;
import com.xsushirollx.sushibyte.restaurantservice.dto.RestaurantDTO;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;

@SpringBootTest
public class RestaurantServiceTests {

	@Autowired
	private RestaurantService rservice;

	private Logger log = Logger.getLogger("RestaurantServiceTests");

	@MockBean
	private RestaurantDAO rdao;

	List<Restaurant> name;
	List<Restaurant> rating;

	@BeforeEach
	public void setUp() {
		
		List<Restaurant> testRestaurants = new ArrayList<Restaurant>();
		Restaurant r1 = new Restaurant("Burger Bar", 2, 3.4, "american, burger, bar, milkshakes", 1, "1958 Sandy Ln",
				"Danny", "CA", 45678);
		testRestaurants.add(r1);
		Restaurant r2 = new Restaurant("Casa Feliz", 3, 3.7, "mexican, texmex, latin", 1, "1654 Tejas", "Primera Vista",
				"TX", 17478);
		testRestaurants.add(r2);
		
		Restaurant r3 = new Restaurant("HelloWorld", 1, 1.4, "hello, hola, ciao", 0, "Nowhere", "Primera Vista",
				"TX", 17478);
		testRestaurants.add(r3);
		
		Restaurant r4 = new Restaurant("Cafe Mocha", 4, 4.6, "cacao, fudge, chocalate", 0, "Hi There", "Primera Vista",
				"TX", 17478);
		testRestaurants.add(r4);
		
		Restaurant r5 = new Restaurant("Yummy!", 3, 2.2, "delicioso, rico, dulce", 0, "Knowhere", "Primera Vista",
				"TX", 17478);
		testRestaurants.add(r5);
		
		name = new ArrayList<>(testRestaurants);
		name.sort((r01, r02) -> r01.getName().compareTo(r02.getName()));
		
		rating = new ArrayList<>(testRestaurants);
		rating.sort((r01, r02) -> {
			if (r01.getAverageRating() - r02.getAverageRating() > 0) {
				return 1;
			} else if(r01.getAverageRating() - r02.getAverageRating() < 0) {
				return -1;
			} else {
				return 0;
			}
		});
		
		log.info(new TestPage(name) == null ? "true" : "false");
		
	}

	@Test
	public void getAllRestaurantsSortByName() {
		Map<String, String> params = new HashMap<>();
        params.put("priceCategories", "1, 3, 4");
        when(rdao.findAllSortByName(Mockito.anyInt(), Mockito.anyDouble(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(Pageable.class) )).thenReturn(new TestPage(name));
        
		List<RestaurantDTO> results = rservice.getAllRestaurants(params, 0, 5, 1.0, "a-to-z", 0);
		log.info("Sort By Name: " + results.toString());
		for (int i = 1; i < results.size(); i++) {
			assert (results.get(i).getName().compareToIgnoreCase(results.get(i - 1).getName()) >= 0);
		}
	}
//
//	@Test
//	public void getAllRestaurantsSortByRating() {
//		Map<String, String> params = new HashMap<>();
//        params.put("priceCategories", "1, 2, 4");
//		List<RestaurantDTO> results = rservice.getAllRestaurants(params, 0, 5, 4.0, "ratings", 0);
//		log.info("Sort By Rating: " + results.toString());
//		for (int i = 1; i < results.size(); i++) {
//			log.info("Round" + i + " " + (results.get(i).getAverageRating() - (results.get(i - 1).getAverageRating()) <= 0 ? "true" : "false"));
//			assert (results.get(i).getAverageRating() - (results.get(i - 1).getAverageRating()) <= 0);
//			assert (results.get(i).getAverageRating() >= 3);
//			assert(results.get(i).getPriceCategory() != 3);
//		}
//	}
//
//	@Test
//	public void findById() {
//		for (int i = 0; i < testRestaurants.size(); i++) {
//			assertEquals(rservice.findById(testRestaurants.get(i).getId()).getId(), testRestaurants.get(i).getId());
//		}
//	}
//
//	@Test
//	public void addNewRestaurant() {
//		assert (!rservice.addNewRestaurant(new RestaurantDTO(testRestaurants.get(0), null, null)));
//	}
//
//	@Test
//	public void updateRestaurant() {
//
//		testRestaurants.get(0).setTags("american, southern, burger, fries, comfort food");
//		rservice.updateRestaurant(new RestaurantDTO(testRestaurants.get(0), null, null), testRestaurants.get(0).getId());
//		assertEquals(rdao.findById(testRestaurants.get(0).getId()).get().getTags(),
//				"american, southern, burger, fries, comfort food");
//
//	}
//
//	@Test
//	public void setRestaurantToInActive() {
//		rservice.setRestaurantToInActive(testRestaurants.get(0).getId());
//		assertEquals(rdao.findById(testRestaurants.get(0).getId()).get().getIsActive(), 0);
//	}
//
//	@Test
//	public void searchSecurity() {
//		log.entering("RestaurantServiceTests", "searchSecurity");
//
//		Map<String, String> params = new HashMap<>();
//		params.put("page", "0");
//		params.put("sort", "a-to-z");
//		params.put("priceCategories", "1, 4");
//		List<String> keywords = new ArrayList<>();
//		keywords.add("burgers");
//		keywords.add("american");
//		
//		List<RestaurantDTO> result = rservice.search(0, params, 2.0, 5, keywords, 1);
//		
//		log.info("Search Security" + result);
//		
//		
//		for (int i = 0; i < result.size(); i++) {
//			assertEquals(1, result.get(i).getIsActive());
//			assert (result.get(i).getAverageRating() >= 2);
//			assert(result.get(i).getPriceCategory() != 3);
//			assert(result.get(i).getPriceCategory() != 2);
//			assert(result.get(i).getPriceCategory() != 3);
//		}
//	}
//
//	@Test
//	public void searchByRatings() {
//		log.entering("RestaurantServiceTests", "searchSecurity");
//
//		Map<String, String> params = new HashMap<>();
//		params.put("page", "0");
//		params.put("sort", "ratings");
//		params.put("priceCategories", "1, 3");
//		List<String> keywords = new ArrayList<>();
//		keywords.add("burgers");
//		keywords.add("tacos");
//		keywords.add("burritos");
//
//		List<RestaurantDTO> results = rservice.search(0, params, 1.0, 5, keywords, 0);
//
//		for (int i = 1; i < results.size(); i++) {
//			assert (results.get(i).getAverageRating() - (results.get(i - 1).getAverageRating()) < 0);
//			assert (results.get(i).getAverageRating() >= 1);
//			assert(results.get(i).getPriceCategory() != 2);
//			assert(results.get(i).getPriceCategory() != 4);
//		}
//
//	}
//
//	@Test
//	public void searchByName() {
//		log.entering("RestaurantServiceTests", "searchSecurity");
//
//		Map<String, String> params = new HashMap<>();
//		params.put("page", "0");
//		params.put("priceCategories", "2");
//		params.put("sort", "a-to-z");
//		List<String> keywords = new ArrayList<>();
//		keywords.add("burgers");
//		keywords.add("tacos");
//		keywords.add("burritos");
//		
//		
//		List<RestaurantDTO> results = rservice.search(0, params, 1.0, 5, keywords, 0);
//		for (int i = 1; i < results.size(); i++) {
//			assert (results.get(i).getName().compareToIgnoreCase(results.get(i - 1).getName()) > 0);
//			assert (results.get(i).getAverageRating() >= 1);
//			assert(results.get(i).getPriceCategory() == 2);
//		}
//		
//
//	}
//	
//	@Test
//	public void searchByRelevance() {
//		log.entering("RestaurantServiceTests", "searchSecurity");
//
//		Map<String, String> params = new HashMap<>();
//		params.put("page", "0");
//		params.put("sort", "relevance");
//		params.put("priceCategories", "2, 3, 4");
//		List<String> keywords = new ArrayList<>();
//		keywords.add("burgers");
//		keywords.add("tacos");
//		keywords.add("burritos");
//
//		List<RestaurantDTO> results = rservice.search(0, params, 2.0, 5, keywords, 0);
//
//		log.info("Search Relevance: " + results);
//		for (int i = 1; i < results.size(); i++) {
//			assert (results.get(i).getRelevance() - (results.get(i - 1).getRelevance()) <= 0);
//			assert(results.get(i).getRelevance() != 0);
//			assert (results.get(i).getAverageRating() >= 2);
//			assert(results.get(i).getPriceCategory() != 1);
//		}
//
//	}

private class TestPage<U> implements Page<U> {
	private List<U> testRestaurants;
	
	
	TestPage(List<U> page) {
		testRestaurants = new ArrayList<>(page);
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
		return testRestaurants.size();
	}

	
	public <V> Page<V> map(Function<? super U, ? extends V> converter) {
		List<V> result = new ArrayList<V>();
		for (U object : testRestaurants) {
			result.add(converter.apply(object));
		}
		log.info(result.toString());
		return new TestPage<V>(result);
	}
	
	
}
	
}
