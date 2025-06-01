package com.example.shoppingapp.viewmodels;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shoppingapp.database.CartItemDao;
import com.example.shoppingapp.database.AppDatabase;
import com.example.shoppingapp.models.CartItem;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class CartViewModel extends AndroidViewModel {
    private static final String TAG = "CartViewModel";
    private final CartItemDao cartItemDao;
    private final MutableLiveData<List<CartItem>> cartItems;
    private final MutableLiveData<Double> totalPrice;
    private final ExecutorService executorService;
    private final Handler mainHandler;
    private String currentUserEmail;
    private boolean isExecutorShutdown = false;

    public CartViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        cartItemDao = db.cartItemDao();
        cartItems = new MutableLiveData<>();
        totalPrice = new MutableLiveData<>();
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public void setCurrentUserEmail(String email) {
        this.currentUserEmail = email;
        loadCartItems();
    }

    private void loadCartItems() {
        if (currentUserEmail == null) {
            cartItems.postValue(null);
            totalPrice.postValue(0.0);
            return;
        }

        try {
            executorService.execute(() -> {
                try {
                    List<CartItem> items = cartItemDao.getCartItemsForUserSync(currentUserEmail);
                    mainHandler.post(() -> {
                        cartItems.postValue(items);
                        updateTotalPrice(items);
                    });
                } catch (Exception e) {
                    Log.e(TAG, "Error loading cart items", e);
                    mainHandler.post(() -> {
                        cartItems.postValue(null);
                        totalPrice.postValue(0.0);
                    });
                }
            });
        } catch (RejectedExecutionException e) {
            Log.e(TAG, "Executor service rejected task", e);
            mainHandler.post(() -> {
                cartItems.postValue(null);
                totalPrice.postValue(0.0);
            });
        }
    }

    public LiveData<List<CartItem>> getCartItems() {
        return cartItems;
    }

    public LiveData<Double> getTotalPrice() {
        return totalPrice;
    }

    private void updateTotalPrice(List<CartItem> items) {
        if (items == null) {
            totalPrice.postValue(0.0);
            return;
        }
        double total = 0.0;
        for (CartItem item : items) {
            total += item.getProductPrice() * item.getQuantity();
        }
        totalPrice.postValue(total);
    }

    public void addToCart(CartItem item) {
        if (currentUserEmail == null) return;

        try {
            executorService.execute(() -> {
                try {
                    CartItem existingItem = cartItemDao.getCartItemByProductId(String.valueOf(item.getProductId()), currentUserEmail);
                    if (existingItem != null) {
                        existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                        cartItemDao.updateCartItem(existingItem);
                    } else {
                        item.setUserEmail(currentUserEmail);
                        cartItemDao.insertCartItem(item);
                    }
                    loadCartItems();
                } catch (Exception e) {
                    Log.e(TAG, "Error adding to cart", e);
                }
            });
        } catch (RejectedExecutionException e) {
            Log.e(TAG, "Executor service rejected task", e);
        }
    }

    public void updateCartItemQuantity(long productId, int newQuantity) {
        if (currentUserEmail == null) return;

        try {
            executorService.execute(() -> {
                try {
                    CartItem item = cartItemDao.getCartItemByProductId(String.valueOf(productId), currentUserEmail);
                    if (item != null) {
                        item.setQuantity(newQuantity);
                        cartItemDao.updateCartItem(item);
                        loadCartItems();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error updating cart item quantity", e);
                }
            });
        } catch (RejectedExecutionException e) {
            Log.e(TAG, "Executor service rejected task", e);
        }
    }

    public void removeFromCart(long productId) {
        if (currentUserEmail == null) return;

        try {
            executorService.execute(() -> {
                try {
                    CartItem item = cartItemDao.getCartItemByProductId(String.valueOf(productId), currentUserEmail);
                    if (item != null) {
                        cartItemDao.deleteCartItem(item);
                        loadCartItems();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error removing from cart", e);
                }
            });
        } catch (RejectedExecutionException e) {
            Log.e(TAG, "Executor service rejected task", e);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!isExecutorShutdown) {
            executorService.shutdown();
            isExecutorShutdown = true;
        }
    }
} 