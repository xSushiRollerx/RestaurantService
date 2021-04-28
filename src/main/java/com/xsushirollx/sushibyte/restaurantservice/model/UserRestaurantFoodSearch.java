package com.xsushirollx.sushibyte.restaurantservice.model;

import javax.persistence.*;

@Entity
@Table(name="food")
@SecondaryTable(name ="restaurant", pkJoinColumns = @PrimaryKeyJoinColumn(name="restaurant_id"))
public class UserRestaurantFoodSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long foodId;

    @Column(name="restaurant_id")
    private Integer restaurantID;

    @Column(name="name")
    private String name;

    @Column(name="cost")
    private Double cost;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    @Column(name="summary")
    private String summary;

    @Column(name="special")
    private Integer special;

    @Column(name="is_active")
    private Integer isActive;

    @Column(name="category")
    private Integer category;

    @Column(name="id", table = "restaurant")
    private Long id;

    @Column(name = "name", table = "restaurant")
    private String restaurantName;

    @Column(name="average_rating", table = "restaurant")
    private Double averageRating;

    @Column(name="tags", table = "restaurant")
    private String tags;

    @Column(name="is_active", table = "restaurant")
    private Integer restaurantIsActive;

    @Column(name = "price_category", table = "restaurant")
    private Integer priceCategory;

    @Column(name="street_address", table = "restaurant")
    private String streetAddress;

    @Column(name="city", table = "restaurant")
    private String city;

    @Column(name="state", table = "restaurant")
    private String state;

    @Column(name="zip_code", table = "restaurant")
    private Integer zipCode;

    public Long getFoodId() {
        return foodId;
    }

    public Integer getRestaurantID() {
        return restaurantID;
    }

    public String getName() {
        return name;
    }

    public Double getCost() {
        return cost;
    }

    public String getImage() {
        return image;
    }

    public String getSummary() {
        return summary;
    }

    public Integer getSpecial() {
        return special;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public Integer getCategory() {
        return category;
    }

    public Long getId() {
        return id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public String getTags() {
        return tags;
    }

    public Integer getRestaurantIsActive() {
        return restaurantIsActive;
    }

    public Integer getPriceCategory() {
        return priceCategory;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public Integer getZipCode() {
        return zipCode;
    }
}
