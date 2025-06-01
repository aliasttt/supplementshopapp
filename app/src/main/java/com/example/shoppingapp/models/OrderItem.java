package com.example.shoppingapp.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "order_items",
    foreignKeys = {
        @ForeignKey(
            entity = Order.class,
            parentColumns = "id",
            childColumns = "orderId",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Product.class,
            parentColumns = "id",
            childColumns = "productId",
            onDelete = ForeignKey.CASCADE
        )
    },
    indices = {
        @Index("orderId"),
        @Index("productId")
    }
)
public class OrderItem {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long orderId;
    private long productId;
    private String productName;
    private double productPrice;
    private int quantity;
    private String productImage;

    public OrderItem(long orderId, long productId, String productName, double productPrice, int quantity, String productImage) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.productImage = productImage;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
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

    public double getTotalPrice() {
        return productPrice * quantity;
    }
} 