package com.example.shoppingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.utils.LocalAuthManager;

public class LoginActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private Button loginButton, registerButton;
    private LocalAuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize LocalAuthManager
        LocalAuthManager.init(this);
        authManager = LocalAuthManager.getInstance();

        // اگر کاربر قبلاً وارد شده است، به صفحه اصلی برو
        if (authManager.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(v -> login());
        registerButton.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void login() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "لطفاً ایمیل و رمز عبور را وارد کنید", Toast.LENGTH_SHORT).show();
            return;
        }

        authManager.login(email, password, new LocalAuthManager.OnAuthCallback() {
            @Override
            public void onSuccess(boolean isAdmin) {
                Intent intent;
                if (isAdmin) {
                    intent = new Intent(LoginActivity.this, AdminPanelActivity.class);
                } else {
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
} 