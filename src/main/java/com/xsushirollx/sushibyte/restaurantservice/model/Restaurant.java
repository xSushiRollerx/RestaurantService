package com.xsushirollx.sushibyte.restaurantservice.model;

import com.xsushirollx.sushibyte.restaurantservice.dto.RestaurantDTO;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "restaurant")
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "average_rating")
	private Double averageRating;

	@Column(name = "tags")
	private String tags;

	@Max(value = 1)
	@Min(value = 0)
	@Column(name = "is_active")
	private Integer isActive;

	@Column(name = "price_category")
	private Integer priceCategory;

	@Column(name = "street_address")
	private String streetAddress;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "zip_code")
	private Integer zipCode;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "restaurant_id", insertable = false, updatable = false)
	private List<Food> menu;

	@Transient
	private Double relevance = (double) 0;
	
	public Restaurant() {
	};

	public Restaurant(RestaurantDTO restaurant) {
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
		
		if (restaurant.getMenu() != null) {
			this.setMenu(Arrays.asList(restaurant.getMenu().parallelStream().map(m -> new Food(m)).toArray(Food[]::new)));
		}
	}

	public Restaurant(String name, Integer priceCategory, Double averageRating, String tags, Integer isActive,
			String streetAddress, String city, String state, Integer zipCode) {
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
		if ((isActive != null) && (isActive == 0 || isActive == 1)) {
			this.isActive = isActive;
		} else {
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

	public List<Food> getMenu() {
		return menu;
	}

	public void setMenu(List<Food> menu) {
		this.menu = menu;
	}

	public Double getRelevance() {
		return relevance;
	}

	public void setRelevance(Double relevance) {
		this.relevance = relevance;
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
		Restaurant other = (Restaurant) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", averageRating=" + averageRating + ", tags=" + tags
				+ ", isActive=" + isActive + ", priceCategory=" + priceCategory + ", streetAddress=" + streetAddress
				+ ", city=" + city + ", state=" + state + ", zipCode=" + zipCode + ", menu=" + menu + ", relevance="
				+ relevance + "]";
	}
	
	
	
	
}
