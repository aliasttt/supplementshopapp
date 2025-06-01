package com.example.shoppingapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shoppingapp.models.Order;
import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    void insert(Order order);

    @Update
    void update(Order order);

    @Query("SELECT * FROM orders WHERE userId = :userId")
    LiveData<List<Order>> getOrdersByUserId(int userId);

    @Query("SELECT * FROM orders")
    LiveData<List<Order>> getAllOrders();

    @Query("SELECT * FROM orders WHERE id = :orderId")
    LiveData<Order> getOrderById(int orderId);
} 