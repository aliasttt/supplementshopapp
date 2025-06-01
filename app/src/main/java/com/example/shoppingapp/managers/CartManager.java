package com.example.shoppingapp.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import androidx.room.Room;
import com.example.shoppingapp.database.AppDatabase;
import com.example.shoppingapp.database.CartItemDao;
import com.example.shoppingapp.models.CartItem;
import com.example.shoppingapp.models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CartManager {
    private static final String PREF_NAME = "cart_prefs";
    private static final String KEY_CART_ITEMS = "cart_items";
    private static CartManager instance;
    private final SharedPreferences preferences;
    private final Gson gson;
    private List<CartItem> cartItems;
    private final Context context;
    private final CartItemDao cartItemDao;
    private final ExecutorService executor;
    private String currentUserEmail;

    private CartManager(Context context) {
        this.context = context.getApplicationContext();
        preferences = this.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        AppDatabase db = Room.databaseBuilder(this.context, AppDatabase.class, "app_database").build();
        cartItemDao = db.cartItemDao();
        executor = Executors.newSingleThreadExecutor();
        loadCartItems();
    }

    public static synchronized CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context.getApplicationContext());
        }
        return instance;
    }

    public void setCurrentUserEmail(String email) {
        this.currentUserEmail = email;
    }

    private void loadCartItems() {
        String json = preferences.getString(KEY_CART_ITEMS, null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<CartItem>>() {}.getType();
            cartItems = gson.fromJson(json, type);
        } else {
            cartItems = new ArrayList<>();
        }
    }

    private void saveCartItems() {
        String json = gson.toJson(cartItems);
        preferences.edit().putString(KEY_CART_ITEMS, json).apply();
    }

    public void addToCart(Context context, Product product) {
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        // Check if product already exists in cart
        for (CartItem item : cartItems) {
            if (item.getProductId() == product.getId()) {
                item.setQuantity(item.getQuantity() + 1);
                saveCartItems();
                Toast.makeText(context, "Ürün sepete eklendi", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Add new item to cart
        CartItem newItem = new CartItem(
            product.getId(),
            product.getName(),
            product.getPrice(),
            1,
            product.getImageResourceName(),
            currentUserEmail
        );
        cartItems.add(newItem);
        saveCartItems();
        Toast.makeText(context, "Ürün sepete eklendi", Toast.LENGTH_SHORT).show();
    }

    public void removeFromCart(CartItem item) {
        if (cartItems != null) {
            cartItems.remove(item);
            saveCartItems();
        }
    }

    public void updateQuantity(CartItem item, int newQuantity) {
        if (cartItems != null) {
            for (CartItem cartItem : cartItems) {
                if (cartItem.getProductId() == item.getProductId()) {
                    cartItem.setQuantity(newQuantity);
                    saveCartItems();
                    break;
                }
            }
        }
    }

    public List<CartItem> getCartItems() {
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        return cartItems;
    }

    public double getTotalPrice() {
        if (cartItems == null) {
            return 0.0;
        }
        double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void clearCart() {
        if (cartItems != null) {
            cartItems.clear();
            saveCartItems();
        }
    }

    public void addToCart(Product product, int quantity) {
        if (currentUserEmail == null) {
            Toast.makeText(context, "Lütfen önce giriş yapın", Toast.LENGTH_SHORT).show();
            return;
        }

        executor.execute(() -> {
            CartItem existingItem = cartItemDao.getCartItemByProductId(String.valueOf(product.getId()), currentUserEmail);
            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                cartItemDao.updateCartItem(existingItem);
            } else {
                CartItem newItem = new CartItem(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    quantity,
                    product.getImageResourceName(),
                    currentUserEmail
                );
                cartItemDao.insertCartItem(newItem);
            }
        });
    }

    public void addToCart(CartItem item) {
        if (currentUserEmail == null) {
            Toast.makeText(context, "Lütfen önce giriş yapın", Toast.LENGTH_SHORT).show();
            return;
        }

        executor.execute(() -> {
            CartItem existingItem = cartItemDao.getCartItemByProductId(String.valueOf(item.getProductId()), currentUserEmail);
            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + 1);
                cartItemDao.updateCartItem(existingItem);
            } else {
                item.setUserEmail(currentUserEmail);
                cartItemDao.insertCartItem(item);
            }
        });
    }
} 