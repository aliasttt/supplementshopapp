package com.example.shoppingapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.shoppingapp.models.UserProfile;
import com.example.shoppingapp.database.AppDatabase;
import com.example.shoppingapp.database.UserProfileDao;
import com.example.shoppingapp.utils.LocalAuthManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText addressEditText;
    private EditText cityEditText;
    private EditText postalCodeEditText;
    private Button saveButton;
    private Button viewOrdersButton;
    private Button logoutButton;
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
        try {
            nameEditText = findViewById(R.id.nameEditText);
            phoneEditText = findViewById(R.id.phoneEditText);
            addressEditText = findViewById(R.id.addressEditText);
            cityEditText = findViewById(R.id.cityEditText);
            postalCodeEditText = findViewById(R.id.postalCodeEditText);
            saveButton = findViewById(R.id.saveButton);
            viewOrdersButton = findViewById(R.id.viewOrdersButton);
            logoutButton = findViewById(R.id.logoutButton);
        } catch (Exception e) {
            showError("Arayüz bileşenleri yüklenirken bir hata oluştu");
            finish();
        }
    }

    private void setupToolbar() {
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(R.string.profile_title);
            }
        } catch (Exception e) {
            showError("Araç çubuğu yüklenirken bir hata oluştu");
        }
    }

    private void loadUserProfile() {
        try {
            String userEmail = authManager.getCurrentUserEmail();
            if (userEmail != null) {
                userProfileDao.getUserProfile(userEmail).observe(this, userProfile -> {
                    if (userProfile != null) {
                        try {
                            nameEditText.setText(userProfile.getName());
                            phoneEditText.setText(userProfile.getPhoneNumber());
                            addressEditText.setText(userProfile.getAddress());
                            cityEditText.setText(userProfile.getCity());
                            postalCodeEditText.setText(userProfile.getPostalCode());
                        } catch (Exception e) {
                            showError("Profil bilgileri yüklenirken bir hata oluştu");
                        }
                    }
                });
            }
        } catch (Exception e) {
            showError("Profil yüklenirken bir hata oluştu");
        }
    }

    private void setupClickListeners() {
        try {
            saveButton.setOnClickListener(v -> saveProfile());
            viewOrdersButton.setOnClickListener(v -> {
                showMessage("Siparişler yakında eklenecek");
            });
            logoutButton.setOnClickListener(v -> {
                try {
                    authManager.logout();
                    finish();
                } catch (Exception e) {
                    showError("Çıkış yapılırken bir hata oluştu");
                }
            });
        } catch (Exception e) {
            showError("Buton işlevleri yüklenirken bir hata oluştu");
        }
    }

    private void saveProfile() {
        try {
            String userEmail = authManager.getCurrentUserEmail();
            if (userEmail == null) {
                showError("Kullanıcı oturumu bulunamadı");
                return;
            }

            String name = nameEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();
            String address = addressEditText.getText().toString().trim();
            String city = cityEditText.getText().toString().trim();
            String postalCode = postalCodeEditText.getText().toString().trim();

            if (name.isEmpty()) {
                nameEditText.setError("İsim alanı boş bırakılamaz");
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
        try {
            runOnUiThread(() -> {
                try {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // Ignore toast errors
                }
            });
        } catch (Exception e) {
            // Ignore thread errors
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
} 