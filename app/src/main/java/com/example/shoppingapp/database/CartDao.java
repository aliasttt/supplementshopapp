package com.example.shoppingapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.shoppingapp.models.Product;
import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * FROM products WHERE id IN (SELECT product_id FROM cart_items)")
    LiveData<List<Product>> getCartItems();

    @Insert
    void addToCart(Product product);

    @Delete
    void removeFromCart(Product product);

    @Update
    void updateCartItem(Product product);

    @Query("DELETE FROM cart_items")
    void clearCart();
} 