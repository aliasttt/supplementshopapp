package com.example.shoppingapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.shoppingapp.models.OrderItem;
import java.util.List;

@Dao
public interface OrderItemDao {
    @Insert
    void insert(OrderItem orderItem);

    @Update
    void update(OrderItem orderItem);

    @Delete
    void delete(OrderItem orderItem);

    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    LiveData<List<OrderItem>> getOrderItemsForOrder(long orderId);

    @Query("SELECT * FROM order_items WHERE id = :id")
    LiveData<OrderItem> getOrderItemById(long id);

    @Query("DELETE FROM order_items WHERE orderId = :orderId")
    void deleteOrderItemsForOrder(long orderId);
} 