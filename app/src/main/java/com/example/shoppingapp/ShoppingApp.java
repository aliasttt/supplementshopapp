package com.example.shoppingapp;

import android.app.Application;
import com.google.firebase.FirebaseApp;
import com.example.shoppingapp.utils.CartManager;
import com.example.shoppingapp.utils.LocalAuthManager;

public class ShoppingApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        
        // Initialize managers
        LocalAuthManager.init(this);
        CartManager.getInstance(this);
    }
} 