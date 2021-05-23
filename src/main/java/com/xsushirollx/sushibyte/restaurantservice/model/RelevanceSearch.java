package com.xsushirollx.sushibyte.restaurantservice.model;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.xsushirollx.sushibyte.restaurantservice.dto.RestaurantDTO;

@Entity
@SqlResultSetMapping(name = "RelevanceSort", entities = {
		@EntityResult(entityClass = RelevanceSearch.class, fields = {
				@FieldResult(column = "id", name = "id"),
				@FieldResult(column = "name", name = "name"),
				@FieldResult(column = "average_rating", name = "averageRating"),
				@FieldResult(column = "tags", name = "tags"),
				@FieldResult(column = "is_active", name = "isActive"),
				@FieldResult(column = "price_category", name = "priceCategory"),
				@FieldResult(column = "street_address", name = "streetAddress"),
				@FieldResult(column = "city", name = "city"),
				@FieldResult(column = "state", name = "state"),
				@FieldResult(column = "zip_code", name = "zipCode"),
				@FieldResult(column = "relevance", name = "relevance")
				})})
public class RelevanceSearch {

	@Id
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
    
    private Double relevance = (double) 0;
    
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    List<Food> menu;

    public RelevanceSearch(){};
    
    public RelevanceSearch(RestaurantDTO restaurant) {
    	
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
		
    }

    public RelevanceSearch(String name, Integer priceCategory, Double averageRating, String tags, Integer isActive, String streetAddress, String city, String state, 
    		Integer zipCode, Double relevance){
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
        this.relevance = relevance;
    }
    
    public RelevanceSearch(String name, Integer priceCategory, Double averageRating, String tags, Integer isActive, String streetAddress, String city, String state, 
    		Integer zipCode) {
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


    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelevanceSearch other = (RelevanceSearch) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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

	public Double getRelevance() {
		return relevance;
	}

	public void setRelevance(Double  relevance) {
		this.relevance = relevance;
	}

	public List<Food> getMenu() {
		return menu;
	}

	public void setMenu(List<Food> menu) {
		this.menu = menu;
	}

	@Override
	public String toString() {
		return "RelevanceSearch [id=" + id + ", name=" + name + ", averageRating=" + averageRating + ", tags=" + tags
				+ ", isActive=" + isActive + ", menu=" + menu + "]";
	}
	

}
