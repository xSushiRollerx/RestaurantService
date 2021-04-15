package com.xsushirollx.sushibyte.restaurantservice.model;

import com.xsushirollx.sushibyte.restaurantservice.service.Helper;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;


@Entity
@Table(name="restaurant")
@Component
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotNull
    @Size(min=2, max=20)
    @Column(name = "name")
    private String name;

    @Max(5)
    @Column(name="average_rating")
    private Double averageRating;

    @Size(max=100)
    @Column(name="tags")
    private String tags;

    @Max(1)
    @Column(name="is_active")
    private Integer isActive;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(name = "price_category")
    private Integer priceCategory;

    @Size(max=50)
    @Column(name="street_address")
    private String streetAddress;

    @Size(max=45)
    @Column(name="city")
    private String city;

    @Column(name="state")
    @Size(max=2)
    private String state;

    @Column(name="zip_code")
    private Integer zipCode;

    public Restaurant(){};

    public Restaurant(String name, Integer priceCategory, Double averageRating, String tags, Integer isActive,
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

    public Restaurant(String name, Integer priceCategory, Double averageRating, String tags, Integer isActive){
        this();
        this.name = name;
        this.priceCategory = priceCategory;
        this.averageRating = averageRating;
        this.tags = tags;
        this.isActive = isActive;
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
        Restaurant that = (Restaurant) o;
        return Objects.equals(id, that.id);
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
