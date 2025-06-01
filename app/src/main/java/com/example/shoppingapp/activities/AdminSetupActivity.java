package com.example.shoppingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.utils.AdminAuthManager;

public class AdminSetupActivity extends AppCompatActivity {
    private EditText emailEditText;
    private Button saveButton;
    private AdminAuthManager adminAuthManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_setup);

        adminAuthManager = AdminAuthManager.getInstance(this);
        emailEditText = findViewById(R.id.emailEditText);
        saveButton = findViewById(R.id.saveButton);

        // Check if admin is already set
        String currentAdmin = adminAuthManager.getAdminEmail();
        if (currentAdmin != null) {
            emailEditText.setText(currentAdmin);
        }

        saveButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            if (email.isEmpty()) {
                Toast.makeText(this, "لطفا ایمیل را وارد کنید", Toast.LENGTH_SHORT).show();
                return;
            }

            adminAuthManager.setAdminEmail(email);
            Toast.makeText(this, "ایمیل ادمین با موفقیت ذخیره شد", Toast.LENGTH_SHORT).show();
            
            // Navigate to login
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
} 