package com.example.shoppingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.shoppingapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrderConfirmationActivity extends AppCompatActivity {
    private TextView orderIdText, statusText, totalAmountText;
    private Button continueShoppingButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();

        // Initialize views
        orderIdText = findViewById(R.id.orderIdText);
        statusText = findViewById(R.id.statusText);
        totalAmountText = findViewById(R.id.totalAmountText);
        continueShoppingButton = findViewById(R.id.continueShoppingButton);

        // Get order ID from intent
        String orderId = getIntent().getStringExtra("order_id");
        if (orderId != null) {
            loadOrderDetails(orderId);
        }

        // Setup continue shopping button
        continueShoppingButton.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        });
    }

    private void loadOrderDetails(String orderId) {
        db.collection("orders")
            .document(orderId)
            .get()
            .addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    // Set order details
                    orderIdText.setText(String.format("Sipariş Numarası: %s", orderId));
                    statusText.setText(String.format("Durum: %s", documentSnapshot.getString("status")));
                    totalAmountText.setText(String.format("Toplam Tutar: %.2f TL", documentSnapshot.getDouble("totalAmount")));
                }
            });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 