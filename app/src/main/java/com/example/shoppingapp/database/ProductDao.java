package com.example.shoppingapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shoppingapp.models.Product;
import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM products")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM products WHERE id = :id")
    LiveData<Product> getProductById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Query("SELECT * FROM products WHERE category = :category")
    LiveData<List<Product>> getProductsByCategory(String category);

    @Query("SELECT * FROM products WHERE name LIKE '%' || :searchQuery || '%'")
    LiveData<List<Product>> searchProducts(String searchQuery);
} 