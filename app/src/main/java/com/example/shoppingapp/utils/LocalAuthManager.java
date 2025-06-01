package com.example.shoppingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class LocalAuthManager {
    private static LocalAuthManager instance;
    private SharedPreferences preferences;
    private static final String PREF_NAME = "auth_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_PHONE = "user_phone";
    private static final String KEY_USER_ADDRESS = "user_address";
    private static final String KEY_IS_ADMIN = "is_admin";
    private static final String KEY_USERS = "users";
    private Map<String, UserData> users; // email -> UserData

    private static class UserData {
        String password;
        String name;

        UserData(String password, String name) {
            this.password = password;
            this.name = name;
        }
    }

    private LocalAuthManager(Context context) {
        preferences = context.getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        loadUsers();
    }

    public static synchronized LocalAuthManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("LocalAuthManager must be initialized with context first");
        }
        return instance;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new LocalAuthManager(context.getApplicationContext());
        }
    }

    private void loadUsers() {
        String json = preferences.getString(KEY_USERS, null);
        if (json != null) {
            Type type = new TypeToken<HashMap<String, UserData>>() {}.getType();
            users = new Gson().fromJson(json, type);
        } else {
            users = new HashMap<>();
            // Add default admin user
            users.put("admin@shoppingapp.com", new UserData("admin123", "Admin"));
        }
    }

    private void saveUsers() {
        String json = new Gson().toJson(users);
        preferences.edit().putString(KEY_USERS, json).apply();
    }

    public void login(String email, String password, OnAuthCallback callback) {
        if (email.equals("admin@shoppingapp.com") && password.equals("admin123")) {
            saveLoginState(email, "Admin", true);
            callback.onSuccess(true);
        } else if (users.containsKey(email) && users.get(email).password.equals(password)) {
            saveLoginState(email, users.get(email).name, false);
            callback.onSuccess(false);
        } else {
            callback.onError("Invalid email or password");
        }
    }

    public void register(String email, String password, String name, OnAuthCallback callback) {
        if (users.containsKey(email)) {
            callback.onError("Email already registered");
            return;
        }
        
        users.put(email, new UserData(password, name));
        saveUsers();
        saveLoginState(email, name, false);
        callback.onSuccess(false);
    }

    public void logout() {
        preferences.edit()
                .remove(KEY_IS_LOGGED_IN)
                .remove(KEY_USER_EMAIL)
                .remove(KEY_USER_NAME)
                .remove(KEY_USER_PHONE)
                .remove(KEY_USER_ADDRESS)
                .remove(KEY_IS_ADMIN)
                .apply();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getCurrentUserEmail() {
        return preferences.getString(KEY_USER_EMAIL, null);
    }

    public String getCurrentUserName() {
        return preferences.getString(KEY_USER_NAME, "");
    }

    public void setCurrentUserEmail(String email) {
        preferences.edit().putString(KEY_USER_EMAIL, email).apply();
    }

    public void setUserName(String name) {
        preferences.edit().putString(KEY_USER_NAME, name).apply();
    }

    public String getUserPhone() {
        return preferences.getString(KEY_USER_PHONE, null);
    }

    public void setUserPhone(String phone) {
        preferences.edit().putString(KEY_USER_PHONE, phone).apply();
    }

    public String getUserAddress() {
        return preferences.getString(KEY_USER_ADDRESS, null);
    }

    public void setUserAddress(String address) {
        preferences.edit().putString(KEY_USER_ADDRESS, address).apply();
    }

    public void clearUserData() {
        preferences.edit().clear().apply();
    }

    private void saveLoginState(String email, String name, boolean isAdmin) {
        preferences.edit()
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .putString(KEY_USER_EMAIL, email)
                .putString(KEY_USER_NAME, name)
                .putBoolean(KEY_IS_ADMIN, isAdmin)
                .apply();
    }

    public interface OnAuthCallback {
        void onSuccess(boolean isAdmin);
        void onError(String error);
    }
} 