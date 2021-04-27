package com.xsushirollx.sushibyte.restaurantservice.dto;

import com.xsushirollx.sushibyte.restaurantservice.service.Helper;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;


@Component
public class RestaurantDTO {

    private Long id;

    @NotNull
    @Size(min=2, max=20)
    private String name;

    @Max(5)
    private Double averageRating;

    @Size(max=100)
    private String tags;

    @Max(1)
    private Integer isActive;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer priceCategory;

    @Size(max=50)
    private String streetAddress;

    @Size(max=45)
    private String city;

    @Size(max=2)
    private String state;

    private Integer zipCode;

    public RestaurantDTO(){};

    public RestaurantDTO(String name, Integer priceCategory, Double averageRating, String tags, Integer isActive,
                         String streetAddress, String city, String state, Integer zipCode){
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
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", averageRating=" + averageRating +
                ", tags='" + tags + '\'' +
                ", isActive=" + isActive +
                ", priceCategory=" + priceCategory +
                ", streetAddress='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantDTO that = (RestaurantDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(streetAddress,
                that.streetAddress) && Objects.equals(city, that.city) &&
                Objects.equals(state, that.state) && Objects.equals(zipCode, that.zipCode);
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
        if (averageRating != null) {
            Helper help = new Helper();
            this.averageRating = help.roundTwoPlaces(averageRating, 2d);
        }
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

}
