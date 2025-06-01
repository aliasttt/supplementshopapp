package com.example.shoppingapp.database;

import androidx.room.TypeConverter;
import com.example.shoppingapp.models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Converters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static String fromProduct(Product product) {
        if (product == null) {
            return null;
        }
        return gson.toJson(product);
    }

    @TypeConverter
    public static Product toProduct(String productString) {
        if (productString == null) {
            return null;
        }
        return gson.fromJson(productString, Product.class);
    }
} 