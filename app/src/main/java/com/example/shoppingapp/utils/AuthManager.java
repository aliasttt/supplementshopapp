package com.example.shoppingapp.utils;

import android.content.Context;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.FirebaseApp;

public class AuthManager {
    private static AuthManager instance;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private static final String ADMIN_EMAIL = "admin@shoppingapp.com"; // ایمیل پیش‌فرض ادمین

    private AuthManager(Context context) {
        FirebaseApp.initializeApp(context);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public static synchronized AuthManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("AuthManager must be initialized with context first");
        }
        return instance;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new AuthManager(context.getApplicationContext());
        }
    }

    public void login(String email, String password, OnAuthCallback callback) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        checkAdminStatus(user.getEmail(), callback);
                    }
                } else {
                    callback.onError(task.getException().getMessage());
                }
            });
    }

    private void checkAdminStatus(String email, OnAuthCallback callback) {
        if (email.equals(ADMIN_EMAIL)) {
            callback.onSuccess(true); // ادمین
        } else {
            callback.onSuccess(false); // کاربر عادی
        }
    }

    public void register(String email, String password, OnAuthCallback callback) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    callback.onSuccess(false); // کاربر جدید همیشه عادی است
                } else {
                    callback.onError(task.getException().getMessage());
                }
            });
    }

    public void logout() {
        auth.signOut();
    }

    public boolean isLoggedIn() {
        return auth.getCurrentUser() != null;
    }

    public String getCurrentUserEmail() {
        FirebaseUser user = auth.getCurrentUser();
        return user != null ? user.getEmail() : null;
    }

    public interface OnAuthCallback {
        void onSuccess(boolean isAdmin);
        void onError(String error);
    }
} 