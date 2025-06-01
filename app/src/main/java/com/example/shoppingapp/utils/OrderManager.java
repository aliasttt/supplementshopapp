package com.example.shoppingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.shoppingapp.models.Order;
import com.example.shoppingapp.models.OrderItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderManager {
    private static OrderManager instance;
    private static final String PREF_NAME = "orders";
    private static final String KEY_ORDERS = "orders_list";
    private SharedPreferences preferences;
    private Gson gson;

    private OrderManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new OrderManager(context.getApplicationContext());
        }
    }

    public static OrderManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("OrderManager not initialized");
        }
        return instance;
    }

    public void saveOrder(int userId, double totalAmount, String shippingAddress, List<OrderItem> items) {
        List<Order> orders = getOrders();
        Order order = new Order(
            userId,
            totalAmount,
            "در انتظار پرداخت",
            shippingAddress,
            new Date(),
            items
        );
        orders.add(0, order); // Add new order at the beginning
        saveOrders(orders);
    }

    public List<Order> getOrders() {
        String json = preferences.getString(KEY_ORDERS, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<Order>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private void saveOrders(List<Order> orders) {
        String json = gson.toJson(orders);
        preferences.edit().putString(KEY_ORDERS, json).apply();
    }

    public void clearOrders() {
        preferences.edit().remove(KEY_ORDERS).apply();
    }

    public void updateOrderStatus(int orderId, String newStatus) {
        List<Order> orders = getOrders();
        for (Order order : orders) {
            if (order.getId() == orderId) {
                order.setStatus(newStatus);
                break;
            }
        }
        saveOrders(orders);
    }
} 