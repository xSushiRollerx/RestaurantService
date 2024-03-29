package com.xsushirollx.sushibyte.restaurantservice.dto;

import org.springframework.stereotype.Component;

import com.xsushirollx.sushibyte.restaurantservice.model.RelevanceSearch;
import com.xsushirollx.sushibyte.restaurantservice.model.Restaurant;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import java.util.Arrays;
import java.util.List;


@Component
public class RestaurantDTO {

    private Long id;

  
    @Size(min=2, max=20)
    private String name;

    @Max(5)
    private Double averageRating;

    @Size(max=100)
    private String tags;

    @Max(1)
    private Integer isActive;

    @Min(1)
    @Max(5)
    private Integer priceCategory;

    @Size(max=50)
    private String streetAddress;
    
    private String city;

    private String state;

    private Integer zipCode;
    
    private List<FoodDTO> menu;
    
    private Double relevance = (double) 0;
    
    private Long resultSize;
    
    private Integer totalPages;

    public RestaurantDTO(){};
    
    public RestaurantDTO(Restaurant restaurant, Long resultSize, Integer totalPages) {
    	
    	this.id = restaurant.getId();
    	this.name = restaurant.getName();
		this.priceCategory = restaurant.getPriceCategory();
		this.averageRating = restaurant.getAverageRating();
		this.tags = restaurant.getTags();
		this.isActive = restaurant.getIsActive();
		this.streetAddress = restaurant.getStreetAddress();
		this.city = restaurant.getCity();
		this.state = restaurant.getState();
		this.zipCode = restaurant.getZipCode();
		this.relevance = restaurant.getRelevance();
		this.resultSize = resultSize;
		this.totalPages = totalPages;
		
		if (restaurant.getMenu() != null) {
			this.setMenu(Arrays.asList(restaurant.getMenu().parallelStream().map(m -> new FoodDTO(m)).toArray(FoodDTO[]::new)));
		}
    }
    
    public RestaurantDTO(String name, Integer priceCategory, Double averageRating, String tags, Integer isActive, String streetAddress, String city, String state, 
    		Integer zipCode){
        this();
        this.name = name;
        this.priceCategory = priceCategory;
        this.averageRating = averageRating;
        this.tags = tags;
        this.isActive = isActive;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public RestaurantDTO(RelevanceSearch restaurant , Long resultSize, Integer totalPages) {
    	this.id = restaurant.getId();
    	this.name = restaurant.getName();
		this.priceCategory = restaurant.getPriceCategory();
		this.averageRating = restaurant.getAverageRating();
		this.tags = restaurant.getTags();
		this.isActive = restaurant.getIsActive();
		this.streetAddress = restaurant.getStreetAddress();
		this.city = restaurant.getCity();
		this.state = restaurant.getState();
		this.zipCode = restaurant.getZipCode();
		this.relevance = restaurant.getRelevance();
		this.resultSize = resultSize;
		this.totalPages = totalPages;
		
		if (restaurant.getMenu() != null) {
			this.setMenu(Arrays.asList(restaurant.getMenu().parallelStream().map(m -> new FoodDTO(m)).toArray(FoodDTO[]::new)));
		}
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriceCategory() {
        return priceCategory;
    }

    public void setPriceCategory(Integer priceID) {
        this.priceCategory = priceID;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
            this.averageRating = averageRating;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        if (state.toCharArray().length == 2) {
            this.state = state;
        }
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

	public List<FoodDTO> getMenu() {
		return menu;
	}

	public void setMenu(List<FoodDTO> menu) {
		this.menu = menu;
	}

	public Double getRelevance() {
		return relevance;
	}

	public void setRelevance(Double  relevance) {
		this.relevance = relevance;
	}

	public Long getResultSize() {
		return resultSize;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	@Override
	public String toString() {
		return "RestaurantDTO [id=" + id + ", name=" + name + ", averageRating=" + averageRating + ", tags=" + tags
				+ ", isActive=" + isActive + ", relevance=" + relevance + "]";
	}

}
