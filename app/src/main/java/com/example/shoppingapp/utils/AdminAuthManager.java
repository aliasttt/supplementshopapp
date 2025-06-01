package com.example.shoppingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AdminAuthManager {
    private static final String PREF_NAME = "admin_prefs";
    private static final String KEY_IS_ADMIN = "is_admin";
    private static final String KEY_ADMIN_EMAIL = "admin_email";
    
    private static AdminAuthManager instance;
    private final SharedPreferences preferences;
    
    private AdminAuthManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    
    public static synchronized AdminAuthManager getInstance(Context context) {
        if (instance == null) {
            instance = new AdminAuthManager(context.getApplicationContext());
        }
        return instance;
    }
    
    public void setAdminEmail(String email) {
        preferences.edit().putString(KEY_ADMIN_EMAIL, email).apply();
    }
    
    public String getAdminEmail() {
        return preferences.getString(KEY_ADMIN_EMAIL, "");
    }
    
    public boolean isAdmin(String email) {
        return email.equals(getAdminEmail());
    }
    
    public void logout() {
        preferences.edit().clear().apply();
    }
} 