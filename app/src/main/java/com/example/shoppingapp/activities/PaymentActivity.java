package com.example.shoppingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import com.example.shoppingapp.R;
import com.example.shoppingapp.models.CartItem;
import com.example.shoppingapp.utils.CartManager;
import com.example.shoppingapp.utils.LocalAuthManager;
import java.util.List;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {
    private CartManager cartManager;
    private TextView totalPriceTextView;
    private Button payButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Initialize managers
        cartManager = CartManager.getInstance(this);
        cartManager.setCurrentUserEmail(LocalAuthManager.getInstance().getCurrentUserEmail());

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("پرداخت");
        }

        // Initialize views
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        payButton = findViewById(R.id.payButton);

        // Load total price
        cartManager.getCartItems().observe(this, items -> {
            if (items != null) {
                double total = 0;
                for (CartItem item : items) {
                    total += item.getProductPrice() * item.getQuantity();
                }
                totalPriceTextView.setText(String.format("Ödenecek Tutar: %.2f TL", total));
            }
        });

        // Setup pay button
        payButton.setOnClickListener(v -> {
            // Simulate payment process
            Toast.makeText(this, "پرداخت با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
            
            // Clear cart and return to main activity
            cartManager.clearCart();
            
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("payment_success", true);
            startActivity(intent);
            finish();
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