package com.example.shoppingapp.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapters.OrderAdapter;
import com.example.shoppingapp.models.Order;
import com.example.shoppingapp.utils.OrderManager;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        // Initialize OrderManager
        OrderManager.init(this);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Siparişlerim");
        }

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        emptyView = findViewById(R.id.emptyView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load orders
        loadOrders();
    }

    private void loadOrders() {
        List<Order> orders = OrderManager.getInstance().getOrders();
        if (orders.isEmpty()) {
            recyclerView.setVisibility(android.view.View.GONE);
            emptyView.setVisibility(android.view.View.VISIBLE);
        } else {
            recyclerView.setVisibility(android.view.View.VISIBLE);
            emptyView.setVisibility(android.view.View.GONE);
            orderAdapter = new OrderAdapter(this, orders);
            recyclerView.setAdapter(orderAdapter);
        }
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