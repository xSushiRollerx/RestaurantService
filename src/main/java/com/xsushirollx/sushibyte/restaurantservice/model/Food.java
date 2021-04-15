package com.xsushirollx.sushibyte.restaurantservice.model;



import com.xsushirollx.sushibyte.restaurantservice.service.Helper;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
//import java.util.Vector;

@Entity
@Table(name="food")
@Component
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="restaurant_id")
    @NotNull
    private Integer restaurantID;

    @NotNull
    @Column(name="name")
    @Size(max=50)
    private String name;

    @NotNull
    @Column(name="cost")
    private Double cost;

//    @Column(name="image")
//    private SerialBlob image;

    @Lob
//    @Column
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    @Column(name="summary")
    @Size(max=100)
    private String summary;

    @Column(name="special")
    private Integer special;

    @Column(name="is_active")
    @Min(1)
    @Max(2)
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

 /*   //Relationships
    private Vector<OrderItem> orderitem = new Vector<>();*/


    public void setCategory(Integer category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equals(id, food.id);
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
        if(cost !=null) {
            Helper help = new Helper();
            this.cost = help.roundTwoPlaces(cost, 2d);
        }
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

/*    public Vector<OrderItem> getOrderitem() {
        return orderitem;
    }

    public void setOrderitem(Vector<OrderItem> orderitem) {
        this.orderitem = orderitem;
    }*/


}
