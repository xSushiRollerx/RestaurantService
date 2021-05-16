package com.xsushirollx.sushibyte.restaurantservice.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xsushirollx.sushibyte.restaurantservice.dto.FoodDTO;
import com.xsushirollx.sushibyte.restaurantservice.model.Food;
import com.xsushirollx.sushibyte.restaurantservice.service.FoodService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/food")
public class FoodController {


//    private final FoodRepository repository;
//    private final RestaurantRepository restaurantRepository;
//    private final RestaurantService restaurantControllerService;
	
	@Autowired
    private FoodService foodControllerService;


//    FoodController(/*FoodRepository foodRepository*/ /*, RestaurantRepository resRepository, */
//                   RestaurantService restaurantControllerService,
//                   FoodService foodControllerService) {
////        this.repository = foodRepository;
////        this.restaurantRepository = resRepository;
//        this.restaurantControllerService = restaurantControllerService;
//        this.foodControllerService = foodControllerService;
//    }


    @GetMapping
    ResponseEntity<List<Food>> getAllFoodMenuItems() {

//        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
        return foodControllerService.getAllFoodMenuItems();
    }


    @GetMapping("/{id}")
    ResponseEntity<Food> getOneFoodMenuItem(@PathVariable Long id) {
/*        return new ResponseEntity<Food>(repository.findById(id).
                orElseThrow(() -> new FoodNotFoundException(id)), HttpStatus.OK);*/
        return foodControllerService.getOneFoodMenuItem(id);

    }

    @PostMapping
    ResponseEntity<?> addNewFoodMenuItem(@RequestBody FoodDTO newFood) {
/*        *//*    ResponseEntity<Food> addNewFoodMenuItem(*//**//*@RequestParam("restaurantID") Integer id,*//**//*
                                            @RequestPart("name") String name,
                                            @RequestPart("cost") Double cost,
                                            @RequestPart("image") MultipartFile image,
                                            @RequestPart("summary") String summary,
                                            @RequestPart("special") Integer special,
                                            @RequestPart("isActive") Integer isActive,
                                            @RequestPart("category") Integer category) {*//*

        FoodService foodService = new FoodService();
        MultipartFile image = new ImageService(newFood.getImage());

        Food duplicateFoodcheck = repository.checkForExistingFoodByValues(
                newFood.getRestaurantID(),
                newFood.getName()
        );

        if (newFood.equals(duplicateFoodcheck)) {
            return ResponseEntity.unprocessableEntity()
                    .body("This Food Menu Item already exists");
        } else {

            try {
//            return new ResponseEntity<Food>(repository.save(foodService.prepFood(5,
//                    name, cost, image, summary, special, isActive, category)), HttpStatus.OK);

                return new ResponseEntity<Food>(repository.save(foodService.prepFood(newFood.getRestaurantID(),
                        newFood.getName(), newFood.getCost(), image, newFood.getSummary(),
                        newFood.getSpecial(), newFood.getIsActive(), newFood.getCategory())), HttpStatus.CREATED);


//            ResponseEntity<Restaurant> ((repository.save(restaurantToBeAdded)), HttpStatus.OK);
            } catch (IllegalArgumentException | ConstraintViolationException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please check " +
                        "for any missing fields which are required fora new Food Menu Items to be created.");
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }*/

        return foodControllerService.addNewFoodMenuItem(newFood);
    }

    @PutMapping("/{id}")
    ResponseEntity<Food> updateFood(@RequestBody FoodDTO newFood, @PathVariable Long id) {
/*//        RestaurantController restaurantExistsController = new RestaurantController(restaurantRepository, restaurantControllerService);
        RestaurantController restaurantExistsController = new RestaurantController(restaurantControllerService);
        FoodService foodService = new FoodService();
        //Checking to see if corresponding Restaurant exists
        if (restaurantExistsController.
                getOneRestaurant(newFood.getRestaurantID().longValue()) != null) {

            return new ResponseEntity<>(repository.findById(id).map(
                    Food -> {
                        if (newFood.getRestaurantID() != null) Food.setRestaurantID(newFood.getRestaurantID());
                        if (newFood.getName() != null) Food.setName(newFood.getName());
                        if (newFood.getCost() != null) Food.setCost(newFood.getCost());
                        if (newFood.getImage() != null) {
                            if (newFood.getImage().length() < 200) {
                                Food.setImage(foodService.prepImage(newFood.getImage()));
                            } else Food.setImage(newFood.getImage());
                        }


                        if (newFood.getSummary() != null) Food.setSummary(newFood.getSummary());
                        if (newFood.getSpecial() != null) Food.setSpecial(newFood.getSpecial());
                        if (newFood.getIsActive() != null) Food.setIsActive(newFood.getIsActive());
                        if (newFood.getCategory() != null) Food.setCategory(newFood.getCategory());
                        return repository.save(Food);
                    })
                    .orElseThrow(() ->
                            new FoodNotFoundException(id)),
                    HttpStatus.OK);
        } else throw new RestaurantNotFoundException(id);*/
        return foodControllerService.updateFood(newFood,id);
    }


    @DeleteMapping("/{id}")
    ResponseEntity<Food> setFoodMenuItemToInActive(@PathVariable Long id) {
/*        ResponseEntity<Food> inActiveFoodMenuItem = getOneFoodMenuItem(id);
        if (inActiveFoodMenuItem != null) {
            repository.setInactiveById(id);

            try {
                repository.setInactiveById(id);

            } catch (Exception e) {
                return new ResponseEntity(inActiveFoodMenuItem, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity(getOneFoodMenuItem(id), HttpStatus.OK);*/
        return foodControllerService.setFoodMenuItemToInActive(id);
    }

}
