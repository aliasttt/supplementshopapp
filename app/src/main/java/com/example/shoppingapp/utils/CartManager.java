package com.example.shoppingapp.utils;

import android.content.Context;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import com.example.shoppingapp.database.AppDatabase;
import com.example.shoppingapp.database.CartItemDao;
import com.example.shoppingapp.models.CartItem;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class CartManager {
    private static CartManager instance;
    private final CartItemDao cartItemDao;
    private final ExecutorService executorService;
    private String currentUserEmail;
    private final Context context;

    private CartManager(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        cartItemDao = db.cartItemDao();
        executorService = AppDatabase.databaseWriteExecutor;
        this.context = context;
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

    public LiveData<List<CartItem>> getCartItems() {
        if (currentUserEmail == null) {
            return null;
        }
        return cartItemDao.getCartItemsForUser(currentUserEmail);
    }

    public void addToCart(CartItem item) {
        if (currentUserEmail == null) {
            Toast.makeText(context, "Lütfen önce giriş yapın", Toast.LENGTH_SHORT).show();
            return;
        }

        executorService.execute(() -> {
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

    public void updateCartItemQuantity(String productId, int newQuantity) {
        if (currentUserEmail == null) return;

        executorService.execute(() -> {
            CartItem item = cartItemDao.getCartItemByProductId(productId, currentUserEmail);
            if (item != null) {
                item.setQuantity(newQuantity);
                cartItemDao.updateCartItem(item);
            }
        });
    }

    public void removeFromCart(String productId) {
        if (currentUserEmail == null) return;

        executorService.execute(() -> {
            CartItem item = cartItemDao.getCartItemByProductId(productId, currentUserEmail);
            if (item != null) {
                cartItemDao.deleteCartItem(item);
            }
        });
    }

    public void clearCart() {
        if (currentUserEmail == null) return;

        executorService.execute(() -> {
            cartItemDao.deleteAllCartItemsForUser(currentUserEmail);
        });
    }

    public LiveData<Double> getTotalPrice() {
        if (currentUserEmail == null) {
            return null;
        }
        return cartItemDao.getTotalPriceLive(currentUserEmail);
    }
} 