package com.example.shoppingapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

@Entity(tableName = "cart_items")
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "product_id")
    private long productId;

    @ColumnInfo(name = "product_name")
    private String productName;

    @ColumnInfo(name = "product_price")
    private double productPrice;

    @ColumnInfo(name = "quantity")
    private int quantity;

    @ColumnInfo(name = "product_image")
    private String productImage;

    @ColumnInfo(name = "user_email")
    private String userEmail;

    // Default constructor for Room
    public CartItem() {}

    // Constructor for creating new cart items
    @Ignore
    public CartItem(long productId, String productName, double productPrice, int quantity, String productImage, String userEmail) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.productImage = productImage;
        this.userEmail = userEmail;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public double getTotalPrice() {
        return productPrice * quantity;
    }
} 