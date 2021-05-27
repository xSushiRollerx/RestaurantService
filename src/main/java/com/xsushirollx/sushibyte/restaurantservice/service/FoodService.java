package com.xsushirollx.sushibyte.restaurantservice.service;

import com.xsushirollx.sushibyte.restaurantservice.dao.FoodDAO;
import com.xsushirollx.sushibyte.restaurantservice.dto.FoodDTO;
import com.xsushirollx.sushibyte.restaurantservice.exception.FoodNotFoundException;
import com.xsushirollx.sushibyte.restaurantservice.model.Food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class FoodService {

	@Autowired
	private FoodDAO repository;

//	public List<FoodDTO> getAllFoodMenuItems() {
//		List<Food> food = repository.findAll();
//		List<FoodDTO> foodDTO = new ArrayList<>();
//
//		for (Food f : food) {
//			foodDTO.add(new FoodDTO(f));
//		}
//		return foodDTO;
//	}

	public FoodDTO getOneFoodMenuItem(Long id) {
		Optional<Food> food = repository.findById(id);
		if (!food.isPresent()) {
			throw new FoodNotFoundException(id);
		}
		return new FoodDTO(food.get());

	}

	public FoodDTO addNewFoodMenuItem(FoodDTO newFood) throws Exception {

		if (!repository.existsByRestaurantIDAndName(newFood.getRestaurantID(), newFood.getName())) {
			Food savedFood = new Food(newFood);
			repository.save(savedFood);
			return new FoodDTO(savedFood);
		} else {
			throw new Exception("Item Could Not Be Created. Food Item Already Exists.");
		}

	}

	public FoodDTO updateFood(FoodDTO newFood, Long id) {

		// Checking to see if corresponding Restaurant exists
		if (repository.existsById(id)) {
			Food food = new Food(newFood);
			repository.save(food);
			return (new FoodDTO(food));
		} else {
			throw new FoodNotFoundException(id);

		}

	}

	public boolean deleteFoodMenuItem(Long id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
			return true;
		} else {
			throw new FoodNotFoundException(id);

		}
	}

}
