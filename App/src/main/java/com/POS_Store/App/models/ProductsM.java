package com.POS_Store.App.models;

import jakarta.persistence.*;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Entity
@Table(name = "products")
public class ProductsM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    private String image;

    @Column(nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @Column(updatable = true)
    private ZonedDateTime updatedAt;

    public enum Category {
        Men,
        Women,
        Kid,
        Unisex,
        Sports,
        Outdoor,
        Formal,
        Casual,
        Designer,
        Orthopedic
    }

    public enum Type {

        // Men Category type
        Sneakers_m(Category.Men),
        Loafers_m(Category.Men),
        Oxford_m(Category.Men),
        Brogues_m(Category.Men),
        Derby_m(Category.Men),
        Sandals_m(Category.Men),
        Boots_m(Category.Men),
        Slipons_m(Category.Men),
        Slippers_m(Category.Men),

        //Women category type
        Heels_w(Category.Women),
        Flats_w(Category.Women),
        Sandals_w(Category.Women),
        Boots_w(Category.Women),
        Wedges_w(Category.Women),
        Sneakers_w(Category.Women),
        Slippers_w(Category.Women),
        Mules_w(Category.Women),

        //Kids
        Sneakers_k(Category.Kid),
        Sandals_k(Category.Kid),
        School_k(Category.Kid),
        Boots_k(Category.Kid),
        Slippers_k(Category.Kid),
        Running_k(Category.Kid),

        //Sport
        Running_Shoes_s(Category.Sports),
        Basketball_Shoes_s(Category.Sports),
        Football_Cleats_s(Category.Sports),
        Tennis_Shoes_s(Category.Sports),
        Training_Shoes_s(Category.Sports),
        Hiking_Shoes_s(Category.Sports),
        Golf_Shoes_s(Category.Sports),
        Skateboarding_Shoes_s(Category.Sports),

        //Outdoor
        Hiking_Boots_o(Category.Outdoor),
        Trail_Running_Shoes_o(Category.Outdoor),
        Snow_Boots_o(Category.Outdoor),
        Water_Shoes_o(Category.Outdoor),

        //Formal
        Oxford_Shoes_f(Category.Formal),
        Monk_Straps_f(Category.Formal),
        Derby_Shoes_f(Category.Formal),
        Brogues_f(Category.Formal),
        Dress_Boots_f(Category.Formal),

        //Casual
        Slip_ons_c(Category.Casual),
        Espadrilles_c(Category.Casual),
        Canvas_Shoes_c(Category.Casual),
        Moccasins_c(Category.Casual),

        //Designer
        Limited_Edition_Sneakers_d(Category.Designer),
        High_End_Loafers_d(Category.Casual),
        Luxury_Heels_d(Category.Casual),

        //Orthopedic
        Arch_Support_Sandals_ort(Category.Orthopedic),
        Diabetic_Shoes_ort(Category.Orthopedic),
        Orthotic_Sneakers_ort(Category.Orthopedic),
        Slip_resistant_Shoes_ort(Category.Orthopedic);

        private final Category parentCategory;

        Type(Category parentCategory) {
            this.parentCategory = parentCategory;
        }

        public Category getParentCategory() {
            return parentCategory;
        }
    }

    //Getters and setters


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getImage() {
        return image;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    @PrePersist
    public void prePersist() {
        // Set the createdAt field to the current UTC time before persisting the entity
        if (createdAt == null) {
            createdAt = ZonedDateTime.now(ZoneOffset.UTC);
        }
    }



}
