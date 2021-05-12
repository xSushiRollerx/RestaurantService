package com.xsushirollx.sushibyte.restaurantservice.model;

import org.springframework.stereotype.Component;

import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name="food")
@Component
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

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

    public Food(){};

    public Food(Integer restaurantID, String name, Double cost, String image, String summary, Integer special,
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
        Food food = (Food) o;
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
