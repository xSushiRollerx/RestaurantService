package com.xsushirollx.sushibyte.restaurantservice.service;

import java.io.IOException;
import java.util.Base64;


import com.xsushirollx.sushibyte.restaurantservice.dao.FoodRepository;
import com.xsushirollx.sushibyte.restaurantservice.model.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;



@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    //    public void  saveFood(
    public Food prepFood(
            Integer restaurantID, String name,
            Double cost, MultipartFile image,
            String summary, Integer special, Integer isActive,
            Integer category) {
        Food food = new Food();

        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("not a a valid file");
        }
        try {//turn image bytes into blob format
            food.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        food.setRestaurantID(restaurantID);
        food.setName(name);
        food.setCost(cost);

        food.setSummary(summary);
        food.setSpecial(special);
        food.setIsActive(isActive);
        food.setCategory(category);


//        foodRepository.save(food);
        return food;
    }
}