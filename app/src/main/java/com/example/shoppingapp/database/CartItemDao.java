package com.example.shoppingapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.shoppingapp.models.CartItem;
import java.util.List;

@Dao
public interface CartItemDao {
    @Query("SELECT * FROM cart_items WHERE user_email = :userEmail")
    LiveData<List<CartItem>> getCartItemsForUser(String userEmail);

    @Query("SELECT * FROM cart_items WHERE user_email = :userEmail")
    List<CartItem> getCartItemsForUserSync(String userEmail);

    @Query("SELECT * FROM cart_items WHERE product_id = :productId AND user_email = :userEmail")
    CartItem getCartItemByProductId(String productId, String userEmail);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCartItem(CartItem cartItem);

    @Update
    void updateCartItem(CartItem cartItem);

    @Delete
    void deleteCartItem(CartItem cartItem);

    @Query("DELETE FROM cart_items WHERE user_email = :userEmail")
    void deleteAllCartItemsForUser(String userEmail);

    @Query("SELECT SUM(product_price * quantity) FROM cart_items WHERE user_email = :userEmail")
    LiveData<Double> getTotalPriceLive(String userEmail);
} 