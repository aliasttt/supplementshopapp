package com.example.shoppingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapters.CartAdapter;
import com.example.shoppingapp.models.CartItem;
import com.example.shoppingapp.utils.CartManager;
import com.example.shoppingapp.utils.LocalAuthManager;
import com.example.shoppingapp.viewmodels.CartViewModel;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {
    private CartViewModel cartViewModel;
    private CartAdapter cartAdapter;
    private TextView totalPriceTextView;
    private Button checkoutButton;
    private LocalAuthManager localAuthManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize managers
        localAuthManager = LocalAuthManager.getInstance();
        CartManager cartManager = CartManager.getInstance(this);
        cartManager.setCurrentUserEmail(localAuthManager.getCurrentUserEmail());

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Sepetim");
        }

        // Initialize ViewModel
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        
        // Set current user email
        String currentUserEmail = localAuthManager.getCurrentUserEmail();
            
        if (currentUserEmail == null) {
            Toast.makeText(this, "Lütfen önce giriş yapın", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        
        cartViewModel.setCurrentUserEmail(currentUserEmail);

        // Initialize views
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        checkoutButton = findViewById(R.id.checkoutButton);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartViewModel, new CartAdapter.OnCartItemClickListener() {
            @Override
            public void onQuantityChanged(CartItem item, int newQuantity) {
                cartViewModel.updateCartItemQuantity(item.getProductId(), newQuantity);
            }

            @Override
            public void onRemoveItem(CartItem item) {
                cartViewModel.removeFromCart(item.getProductId());
            }
        });
        recyclerView.setAdapter(cartAdapter);

        // Observe cart items
        cartViewModel.getCartItems().observe(this, items -> {
            cartAdapter.setCartItems(items);
        });

        // Observe total price
        cartViewModel.getTotalPrice().observe(this, total -> {
            if (total != null) {
                totalPriceTextView.setText(String.format("Toplam: %.2f TL", total));
            }
        });

        // Setup checkout button
        checkoutButton.setOnClickListener(v -> {
            if (cartAdapter.getItemCount() > 0) {
                startActivity(new Intent(this, CheckoutActivity.class));
            } else {
                Toast.makeText(this, "Sepetiniz boş", Toast.LENGTH_SHORT).show();
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