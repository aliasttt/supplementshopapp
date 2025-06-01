package com.example.shoppingapp.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapters.UserAdapter;
import com.example.shoppingapp.models.User;
import com.example.shoppingapp.viewmodels.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminUserManagementActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_management);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Kullanıcı Yönetimi");
        }

        // Initialize ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(this, user -> {
            // Handle user click - show user details dialog
            showUserDetailsDialog(user);
        });
        recyclerView.setAdapter(adapter);

        // Observe users
        userViewModel.getAllUsers().observe(this, users -> {
            adapter.submitList(users);
        });
    }

    private void showUserDetailsDialog(User user) {
        // Create and show dialog with user details
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Kullanıcı Bilgileri")
               .setMessage(String.format(
                   "Ad Soyad: %s\n" +
                   "E-posta: %s\n" +
                   "Telefon: %s\n" +
                   "Adres: %s\n" +
                   "Şehir: %s\n" +
                   "Posta Kodu: %s\n" +
                   "Üyelik Tarihi: %s",
                   user.getName(),
                   user.getEmail(),
                   user.getPhone() != null ? user.getPhone() : "Belirtilmemiş",
                   user.getAddress() != null ? user.getAddress() : "Belirtilmemiş",
                   user.getCity() != null ? user.getCity() : "Belirtilmemiş",
                   user.getPostalCode() != null ? user.getPostalCode() : "Belirtilmemiş",
                   user.getRegistrationDate() != null ? user.getRegistrationDate() : "Belirtilmemiş"
               ))
               .setPositiveButton("Düzenle", (dialog, which) -> {
                   // TODO: Implement edit user functionality
                   Toast.makeText(this, "Düzenleme özelliği yakında eklenecek", Toast.LENGTH_SHORT).show();
               })
               .setNegativeButton("Kapat", null)
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