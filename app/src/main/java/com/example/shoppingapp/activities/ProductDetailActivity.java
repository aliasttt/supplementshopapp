package com.example.shoppingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.example.shoppingapp.models.CartItem;
import com.example.shoppingapp.models.Product;
import com.example.shoppingapp.utils.CartManager;
import com.example.shoppingapp.utils.LocalAuthManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {
    private CartManager cartManager;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Get product data from intent
        Intent intent = getIntent();
        if (intent == null) {
            Toast.makeText(this, "Error loading product information", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Create product from intent extras
        product = new Product(
            intent.getStringExtra("product_name"),
            intent.getStringExtra("product_description"),
            intent.getDoubleExtra("product_price", 0.0),
            intent.getStringExtra("product_image"),
            0, // stock
            "" // category
        );
        product.setId(intent.getLongExtra("product_id", -1));

        if (product.getId() == -1) {
            Toast.makeText(this, "Error loading product information", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize managers
        cartManager = CartManager.getInstance(this);
        String userEmail = LocalAuthManager.getInstance().getCurrentUserEmail();
        if (userEmail != null) {
            cartManager.setCurrentUserEmail(userEmail);
        }

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(product.getName());
        }

        // Initialize views
        ImageView productImage = findViewById(R.id.productImage);
        TextView nameTextView = findViewById(R.id.nameText);
        TextView priceTextView = findViewById(R.id.priceText);
        TextView descriptionTextView = findViewById(R.id.descriptionText);
        FloatingActionButton addToCartButton = findViewById(R.id.addToCartButton);

        // Set product details
        nameTextView.setText(product.getName());
        priceTextView.setText(String.format("Fiyat: %.2f TL", product.getPrice()));
        descriptionTextView.setText(product.getDescription());
        
        // Load product image using Glide
        if (product.getImageResourceName() != null && !product.getImageResourceName().isEmpty()) {
            File imageFile = new File(getFilesDir(), product.getImageResourceName());
            if (imageFile.exists()) {
                Glide.with(this)
                    .load(imageFile)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(productImage);
            } else {
                productImage.setImageResource(R.drawable.placeholder_image);
            }
        } else {
            productImage.setImageResource(R.drawable.placeholder_image);
        }

        // Setup add to cart button
        addToCartButton.setOnClickListener(v -> {
            if (userEmail == null) {
                Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
                return;
            }

            CartItem cartItem = new CartItem(
                product.getId(),
                product.getName(),
                product.getPrice(),
                1,
                product.getImageResourceName(),
                userEmail
            );
            cartManager.addToCart(cartItem);
            Toast.makeText(this, "Product added to cart", Toast.LENGTH_SHORT).show();
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