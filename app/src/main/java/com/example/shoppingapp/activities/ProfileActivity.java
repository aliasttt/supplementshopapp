package com.example.shoppingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.shoppingapp.R;
import com.example.shoppingapp.models.UserProfile;
import com.example.shoppingapp.database.AppDatabase;
import com.example.shoppingapp.database.UserProfileDao;
import com.example.shoppingapp.utils.LocalAuthManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity {
    private EditText nameInput, phoneInput, addressInput, cityInput, postalCodeInput;
    private Button saveButton, viewOrdersButton, logoutButton;
    private UserProfileDao userProfileDao;
    private LocalAuthManager authManager;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        try {
            // Initialize database
            AppDatabase db = AppDatabase.getInstance(this);
            userProfileDao = db.userProfileDao();
            authManager = LocalAuthManager.getInstance();

            // Initialize views
            initializeViews();
            setupToolbar();
            loadUserProfile();
            setupClickListeners();
        } catch (Exception e) {
            showError("Uygulama başlatılırken bir hata oluştu");
            finish();
        }
    }

    private void initializeViews() {
        nameInput = findViewById(R.id.nameEditText);
        phoneInput = findViewById(R.id.phoneEditText);
        addressInput = findViewById(R.id.addressEditText);
        cityInput = findViewById(R.id.cityEditText);
        postalCodeInput = findViewById(R.id.postalCodeEditText);
        saveButton = findViewById(R.id.saveButton);
        viewOrdersButton = findViewById(R.id.viewOrdersButton);
        logoutButton = findViewById(R.id.logoutButton);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Profil");
        }
    }

    private void loadUserProfile() {
        String userEmail = authManager.getCurrentUserEmail();
        if (userEmail != null) {
            userProfileDao.getUserProfile(userEmail).observe(this, profile -> {
                if (profile != null) {
                    nameInput.setText(profile.getName());
                    phoneInput.setText(profile.getPhoneNumber());
                    addressInput.setText(profile.getAddress());
                    cityInput.setText(profile.getCity());
                    postalCodeInput.setText(profile.getPostalCode());
                }
            });
        }
    }

    private void setupClickListeners() {
        saveButton.setOnClickListener(v -> saveProfile());
        viewOrdersButton.setOnClickListener(v -> {
            startActivity(new Intent(this, OrdersActivity.class));
        });
        logoutButton.setOnClickListener(v -> {
            authManager.logout();
            finish();
        });
    }

    private void saveProfile() {
        try {
            String userEmail = authManager.getCurrentUserEmail();
            if (userEmail == null) {
                showError("Kullanıcı oturumu bulunamadı");
                return;
            }

            String name = nameInput.getText().toString().trim();
            String phone = phoneInput.getText().toString().trim();
            String address = addressInput.getText().toString().trim();
            String city = cityInput.getText().toString().trim();
            String postalCode = postalCodeInput.getText().toString().trim();

            if (name.isEmpty()) {
                nameInput.setError("İsim alanı boş bırakılamaz");
                return;
            }

            if (city.isEmpty()) {
                cityInput.setError("Şehir alanı boş bırakılamaz");
                return;
            }

            if (postalCode.isEmpty()) {
                postalCodeInput.setError("Posta kodu alanı boş bırakılamaz");
                return;
            }

            UserProfile userProfile = new UserProfile(userEmail, name, phone, address, city, postalCode);
            
            executor.execute(() -> {
                try {
                    userProfileDao.insertUserProfile(userProfile);
                    runOnUiThread(() -> {
                        showMessage("Profil başarıyla kaydedildi");
                        authManager.setUserName(name);
                    });
                } catch (Exception e) {
                    runOnUiThread(() -> showError("Profil kaydedilirken bir hata oluştu"));
                }
            });
        } catch (Exception e) {
            showError("Profil kaydedilirken bir hata oluştu");
        }
    }

    private void showMessage(String message) {
        try {
            runOnUiThread(() -> {
                try {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    // Ignore toast errors
                }
            });
        } catch (Exception e) {
            // Ignore thread errors
        }
    }

    private void showError(String message) {
        showMessage(message);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
} 