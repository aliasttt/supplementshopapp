package com.example.shoppingapp.utils;

import androidx.room.TypeConverter;
import com.example.shoppingapp.models.OrderItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class OrderItemConverter {
    private static Gson gson = new Gson();

    @TypeConverter
    public static String fromOrderItemList(List<OrderItem> items) {
        return gson.toJson(items);
    }

    @TypeConverter
    public static List<OrderItem> toOrderItemList(String data) {
        Type listType = new TypeToken<List<OrderItem>>() {}.getType();
        return gson.fromJson(data, listType);
    }
} 