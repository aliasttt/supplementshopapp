package com.example.shoppingapp.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "products")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @PrimaryKey(autoGenerate = true)
    private long id;
    
    @NonNull
    private String name;
    private String description;
    private double price;
    private String imageResourceName;
    private int stock;
    private String category;

    public Product(@NonNull String name, String description, double price, String imageResourceName, int stock, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResourceName = imageResourceName;
        this.stock = stock;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
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

    public String getImageResourceName() {
        return imageResourceName;
    }

    public void setImageResourceName(String imageResourceName) {
        this.imageResourceName = imageResourceName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
} 