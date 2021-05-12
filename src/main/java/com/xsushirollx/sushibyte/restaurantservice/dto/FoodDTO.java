package com.xsushirollx.sushibyte.restaurantservice.dto;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Component
public class FoodDTO {

    private Long id;

    @NotNull
    private Integer restaurantID;

    @NotNull
    @Size(max=50)
    private String name;

    @NotNull
    private Double cost;

    private String image;

    @Size(max=100)
    private String summary;

    private Integer special;

    @Max(1)
    private Integer isActive;

    @Max(5)
    @Min(1)
    private Integer category;

    public FoodDTO(){};

    public FoodDTO(Integer restaurantID, String name, Double cost, String image, String summary, Integer special,
                   Integer isActive, Integer category){
        this();
        this.restaurantID = restaurantID;
        this.name = name;
        this.cost = cost;
        this.image = image;
        this.summary = summary;
        this.special = special;
        this.isActive = isActive;
        this.category = category;
    }

    public Integer getCategory() {
        return category;
    }



    public void setCategory(Integer category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodDTO food = (FoodDTO) o;
        return Objects.equals(restaurantID, food.restaurantID) && Objects.equals(name, food.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(Integer restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
            this.cost = cost;
    }

//    public SerialBlob getImage() {
//        return image;
//    }
//
//    public void setImage(SerialBlob image) {
//        this.image = image;
//    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getSpecial() {
        return special;
    }

    public void setSpecial(Integer special) {
        this.special = special;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        if ((isActive != null) && (isActive == 0 || isActive == 1))
        {
            this.isActive = isActive;
        }
        else {
            this.isActive = 1;
        }
    }




}
