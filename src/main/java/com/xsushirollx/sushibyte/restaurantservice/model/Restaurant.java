package com.xsushirollx.sushibyte.restaurantservice.model;

import org.springframework.stereotype.Component;

import com.xsushirollx.sushibyte.restaurantservice.dto.RestaurantDTO;

import javax.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "restaurant")
@Component
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "average_rating")
	private Double averageRating;

	@Column(name = "tags")
	private String tags;

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
	
	@OneToMany
	@JoinColumn(name = "restaurant_id")
	private List<Food> menu;

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

	@Override
	public String toString() {
		return "Restaurant{" + "id=" + id + ", name='" + name + '\'' + ", averageRating=" + averageRating + ", tags='"
				+ tags + '\'' + ", isActive=" + isActive + ", priceCategory=" + priceCategory + ", streetAddress='"
				+ streetAddress + '\'' + ", city='" + city + '\'' + ", state='" + state + '\'' + ", zipCode=" + zipCode
				+ '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Restaurant that = (Restaurant) o;
		return Objects.equals(name, that.name) && Objects.equals(streetAddress, that.streetAddress)
				&& Objects.equals(city, that.city) && Objects.equals(state, that.state)
				&& Objects.equals(zipCode, that.zipCode);
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

}
