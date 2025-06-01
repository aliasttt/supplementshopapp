package com.example.shoppingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapters.AdminProductAdapter;
import com.example.shoppingapp.models.Product;
import com.example.shoppingapp.utils.ProductManager;
import java.util.List;

public class AdminProductListActivity extends AppCompatActivity implements AdminProductAdapter.ProductActionListener {
    private RecyclerView recyclerView;
    private AdminProductAdapter adapter;
    private ProductManager productManager;

    private final ActivityResultLauncher<Intent> editProductLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK) {
                refreshProductList();
            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_list);

        // Initialize managers
        productManager = ProductManager.getInstance(this);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Ürün Yönetimi");
        }

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Setup adapter
        adapter = new AdminProductAdapter(this, this);
        recyclerView.setAdapter(adapter);

        // Load products
        refreshProductList();
    }

    private void refreshProductList() {
        List<Product> products = productManager.getProducts();
        adapter.submitList(products);
    }

    @Override
    public void onEditClick(int position) {
        Product product = adapter.getProductAt(position);
        Intent intent = new Intent(this, AddEditProductActivity.class);
        intent.putExtra("product_id", product.getId());
        editProductLauncher.launch(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        Product product = adapter.getProductAt(position);
        new AlertDialog.Builder(this)
            .setTitle("Ürünü Sil")
            .setMessage("Bu ürünü silmek istediğinizden emin misiniz?")
            .setPositiveButton("Evet", (dialog, which) -> {
                productManager.deleteProduct(product.getId());
                refreshProductList();
                Toast.makeText(this, "Ürün başarıyla silindi", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Hayır", null)
            .show();
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