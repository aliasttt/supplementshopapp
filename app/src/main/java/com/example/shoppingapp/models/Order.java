package com.example.shoppingapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.shoppingapp.utils.DateConverter;
import com.example.shoppingapp.utils.OrderItemConverter;

import java.util.Date;
import java.util.List;

@Entity(tableName = "orders")
@TypeConverters({DateConverter.class, OrderItemConverter.class})
public class Order {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private double totalAmount;
    private String status;
    private String shippingAddress;
    private Date orderDate;
    private List<OrderItem> items;

    public Order(int userId, double totalAmount, String status, String shippingAddress, Date orderDate, List<OrderItem> items) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.orderDate = orderDate;
        this.items = items;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
} 