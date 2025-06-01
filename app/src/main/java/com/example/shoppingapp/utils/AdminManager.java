package com.example.shoppingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AdminManager {
    private static final String PREF_NAME = "admin_prefs";
    private static final String KEY_ADMIN_USERNAME = "admin_username";
    private static final String KEY_ADMIN_PASSWORD = "admin_password";
    private static final String DEFAULT_ADMIN_USERNAME = "admin";
    private static final String DEFAULT_ADMIN_PASSWORD = "admin123";
    
    private static AdminManager instance;
    private final SharedPreferences preferences;

    private AdminManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        // Set default admin credentials if not set
        if (!preferences.contains(KEY_ADMIN_USERNAME)) {
            preferences.edit()
                .putString(KEY_ADMIN_USERNAME, DEFAULT_ADMIN_USERNAME)
                .putString(KEY_ADMIN_PASSWORD, DEFAULT_ADMIN_PASSWORD)
                .apply();
        }
    }

    public static synchronized AdminManager getInstance(Context context) {
        if (instance == null) {
            instance = new AdminManager(context.getApplicationContext());
        }
        return instance;
    }

    public boolean validateAdmin(String username, String password) {
        String storedUsername = preferences.getString(KEY_ADMIN_USERNAME, DEFAULT_ADMIN_USERNAME);
        String storedPassword = preferences.getString(KEY_ADMIN_PASSWORD, DEFAULT_ADMIN_PASSWORD);
        return username.equals(storedUsername) && password.equals(storedPassword);
    }

    public void changeAdminCredentials(String newUsername, String newPassword) {
        preferences.edit()
            .putString(KEY_ADMIN_USERNAME, newUsername)
            .putString(KEY_ADMIN_PASSWORD, newPassword)
            .apply();
    }
} 