package com.example.shoppingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.utils.LocalAuthManager;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private EditText emailEditText;
    private EditText nameEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private TextView loginTextView;
    private LocalAuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            // Initialize LocalAuthManager
            LocalAuthManager.init(this);
            authManager = LocalAuthManager.getInstance();

            // Initialize views
            emailEditText = findViewById(R.id.emailEditText);
            nameEditText = findViewById(R.id.nameEditText);
            passwordEditText = findViewById(R.id.passwordEditText);
            registerButton = findViewById(R.id.registerButton);
            loginTextView = findViewById(R.id.loginTextView);

            if (emailEditText == null || nameEditText == null || passwordEditText == null || 
                registerButton == null || loginTextView == null) {
                Log.e(TAG, "Failed to initialize views");
                Toast.makeText(this, "خطا در بارگذاری صفحه", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            registerButton.setOnClickListener(v -> {
                try {
                    String email = emailEditText.getText().toString().trim();
                    String name = nameEditText.getText().toString().trim();
                    String password = passwordEditText.getText().toString().trim();

                    if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
                        Toast.makeText(this, "لطفا تمام فیلدها را پر کنید", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    registerButton.setEnabled(false);
                    registerButton.setText("در حال ثبت‌نام...");

                    authManager.register(email, password, name, new LocalAuthManager.OnAuthCallback() {
                        @Override
                        public void onSuccess(boolean isAdmin) {
                            runOnUiThread(() -> {
                                registerButton.setEnabled(true);
                                registerButton.setText("ثبت‌نام");
                                Log.d(TAG, "Registration successful for user: " + email);
                                Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();
                            });
                        }

                        @Override
                        public void onError(String error) {
                            runOnUiThread(() -> {
                                registerButton.setEnabled(true);
                                registerButton.setText("ثبت‌نام");
                                Log.w(TAG, "Registration failed for user: " + email + ", error: " + error);
                                Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                            });
                        }
                    });
                } catch (Exception e) {
                    Log.e(TAG, "Error in registration", e);
                    Toast.makeText(this, "خطا در ثبت‌نام", Toast.LENGTH_SHORT).show();
                    registerButton.setEnabled(true);
                    registerButton.setText("ثبت‌نام");
                }
            });

            loginTextView.setOnClickListener(v -> {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            });

        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate", e);
            Toast.makeText(this, "خطا در بارگذاری صفحه", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
} 