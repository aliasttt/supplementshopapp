package com.example.shoppingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapters.ProductAdapter;
import com.example.shoppingapp.models.Product;
import com.example.shoppingapp.utils.LocalAuthManager;
import com.example.shoppingapp.utils.ProductManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductManager.ProductUpdateListener {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ProductManager productManager;

    private final ActivityResultLauncher<Intent> addProductLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK) {
                // Refresh product list when returning from AddEditProductActivity
                refreshProductList();
            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize managers
        LocalAuthManager.init(this);
        productManager = ProductManager.getInstance(this);
        productManager.addProductUpdateListener(this);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        
        // Setup adapter
        productAdapter = new ProductAdapter(this, productManager.getProducts(), product -> {
            // Start ProductDetailActivity with the selected product
            Intent intent = new Intent(this, ProductDetailActivity.class);
            intent.putExtra("product_id", product.getId());
            intent.putExtra("product_name", product.getName());
            intent.putExtra("product_price", product.getPrice());
            intent.putExtra("product_description", product.getDescription());
            intent.putExtra("product_image", product.getImageResourceName());
            startActivity(intent);
        });
        recyclerView.setAdapter(productAdapter);

        // Check for payment success
        if (getIntent().getBooleanExtra("payment_success", false)) {
            showSuccessDialog();
        }
    }

    private void showSuccessDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setMessage("Ödeme başarıyla tamamlandı")
               .setPositiveButton("Tamam", (dialog, id) -> dialog.dismiss())
               .setCancelable(false);
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cart) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        } else if (id == R.id.action_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        } else if (id == R.id.action_admin) {
            startActivity(new Intent(this, AdminLoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshProductList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        productManager.removeProductUpdateListener(this);
    }

    @Override
    public void onProductsUpdated() {
        refreshProductList();
    }

    private void refreshProductList() {
        if (productAdapter != null) {
            productAdapter.updateProducts(productManager.getProducts());
        }
    }
} 