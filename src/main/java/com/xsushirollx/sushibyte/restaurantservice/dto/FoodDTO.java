package com.xsushirollx.sushibyte.restaurantservice.dto;

import org.springframework.stereotype.Component;

import com.xsushirollx.sushibyte.restaurantservice.model.Food;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Component
public class FoodDTO {

    private Long id;

    @NotNull
    private Long restaurantID;

    @NotNull
    @Size(max=50)
    private String name;

    @NotNull
    private Double cost;

    @Size(max=100)
    private String summary;

    private Integer special;

    @Max(1)
    private Integer isActive;

   
    private String category;

    public FoodDTO(){};
    
    public FoodDTO(Food food) {
    	this.id = food.getId();
    	this.restaurantID = food.getRestaurantID();
        this.name = food.getName();
        this.cost = food.getCost();
        this.summary = food.getSummary();
        this.special = food.getSpecial();
        this.isActive = food.getIsActive();
        this.category = food.getCategory();
    }

    public FoodDTO(Long restaurantID, String name, Double cost, String summary, Integer special,
                   Integer isActive, String category){
        this();
        this.restaurantID = restaurantID;
        this.name = name;
        this.cost = cost;
        this.summary = summary;
        this.special = special;
        this.isActive = isActive;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }



    public void setCategory(String category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(Long restaurantID) {
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

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FoodDTO other = (FoodDTO) obj;
		return Objects.equals(id, other.id);
	}

}
