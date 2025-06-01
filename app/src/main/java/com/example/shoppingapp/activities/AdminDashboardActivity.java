package com.example.shoppingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.shoppingapp.R;

public class AdminDashboardActivity extends AppCompatActivity {
    private final ActivityResultLauncher<Intent> addProductLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK) {
                // Product was added successfully
                // You can add a toast message here if you want
            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Yönetici Paneli");
        }

        // Setup click listeners for buttons
        findViewById(R.id.addProductButton).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditProductActivity.class);
            addProductLauncher.launch(intent);
        });

        findViewById(R.id.viewProductsButton).setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminProductListActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.viewOrdersButton).setOnClickListener(v -> {
            Intent intent = new Intent(this, OrdersActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.viewUsersButton).setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminUserManagementActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_product) {
            Intent intent = new Intent(this, AddEditProductActivity.class);
            addProductLauncher.launch(intent);
            return true;
        } else if (id == R.id.action_view_orders) {
            Intent intent = new Intent(this, OrdersActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_view_users) {
            Intent intent = new Intent(this, AdminUserManagementActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
} 