package com.example.shoppingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import com.example.shoppingapp.R;
import com.example.shoppingapp.database.AppDatabase;
import com.example.shoppingapp.models.CartItem;
import com.example.shoppingapp.models.OrderItem;
import com.example.shoppingapp.utils.CartManager;
import com.example.shoppingapp.utils.LocalAuthManager;
import com.example.shoppingapp.utils.OrderManager;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class CheckoutActivity extends AppCompatActivity {
    private EditText nameInput, phoneInput, addressInput;
    private EditText cardNumberInput, cardHolderInput, expiryInput, cvvInput;
    private TextView totalPriceText;
    private Button nextButton, confirmButton;
    private CartManager cartManager;
    private OrderManager orderManager;
    private double totalPrice;
    private boolean isPaymentStep = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Initialize managers
        cartManager = CartManager.getInstance(this);
        cartManager.setCurrentUserEmail(LocalAuthManager.getInstance().getCurrentUserEmail());
        LocalAuthManager.init(this);
        OrderManager.init(this);

        orderManager = OrderManager.getInstance();

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Alışverişi Tamamla");
        }

        // Initialize views
        initializeViews();
        setupListeners();
        loadUserInfo();
        loadCartItems();

        // Get cart items from intent
        String cartItemsJson = getIntent().getStringExtra("cart_items");
        totalPrice = getIntent().getDoubleExtra("total_price", 0.0);

        // Parse cart items
        Type listType = new TypeToken<List<CartItem>>(){}.getType();
        List<CartItem> cartItems = new Gson().fromJson(cartItemsJson, listType);

        // Observe total price
        cartManager.getTotalPrice().observe(this, total -> {
            if (total != null) {
                totalPriceText.setText(String.format("Toplam: %.2f TL", total));
            }
        });
    }

    private void initializeViews() {
        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        addressInput = findViewById(R.id.addressInput);
        cardNumberInput = findViewById(R.id.cardNumberInput);
        cardHolderInput = findViewById(R.id.cardHolderInput);
        expiryInput = findViewById(R.id.expiryInput);
        cvvInput = findViewById(R.id.cvvInput);
        totalPriceText = findViewById(R.id.totalPriceText);
        nextButton = findViewById(R.id.nextButton);
        confirmButton = findViewById(R.id.confirmButton);

        // Initially hide payment fields
        findViewById(R.id.paymentFields).setVisibility(View.GONE);
        confirmButton.setVisibility(View.GONE);
    }

    private void setupListeners() {
        nextButton.setOnClickListener(v -> {
            if (validateContactInfo()) {
                showPaymentFields();
            }
        });

        confirmButton.setOnClickListener(v -> {
            if (validatePaymentInfo()) {
                processPayment();
            }
        });
    }

    private void loadUserInfo() {
        String currentUserEmail = LocalAuthManager.getInstance().getCurrentUserEmail();
        if (currentUserEmail != null) {
            // Load user info from LocalAuthManager
            String name = LocalAuthManager.getInstance().getCurrentUserName();
            String phone = LocalAuthManager.getInstance().getUserPhone();
            String address = LocalAuthManager.getInstance().getUserAddress();

            if (name != null) nameInput.setText(name);
            if (phone != null) phoneInput.setText(phone);
            if (address != null) addressInput.setText(address);
        }
    }

    private boolean validateContactInfo() {
        boolean isValid = true;

        if (nameInput.getText().toString().trim().isEmpty()) {
            nameInput.setError("لطفا نام خود را وارد کنید");
            isValid = false;
        }
        if (phoneInput.getText().toString().trim().isEmpty()) {
            phoneInput.setError("لطفا شماره تماس خود را وارد کنید");
            isValid = false;
        }
        if (addressInput.getText().toString().trim().isEmpty()) {
            addressInput.setError("لطفا آدرس خود را وارد کنید");
            isValid = false;
        }

        return isValid;
    }

    private boolean validatePaymentInfo() {
        boolean isValid = true;

        if (cardNumberInput.getText().toString().trim().isEmpty()) {
            cardNumberInput.setError("لطفا شماره کارت را وارد کنید");
            isValid = false;
        }
        if (cardHolderInput.getText().toString().trim().isEmpty()) {
            cardHolderInput.setError("لطفا نام صاحب کارت را وارد کنید");
            isValid = false;
        }
        if (expiryInput.getText().toString().trim().isEmpty()) {
            expiryInput.setError("لطفا تاریخ انقضا را وارد کنید");
            isValid = false;
        }
        if (cvvInput.getText().toString().trim().isEmpty()) {
            cvvInput.setError("لطفا کد CVV را وارد کنید");
            isValid = false;
        }

        return isValid;
    }

    private void showPaymentFields() {
        isPaymentStep = true;
        findViewById(R.id.contactFields).setVisibility(View.GONE);
        findViewById(R.id.paymentFields).setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.GONE);
        confirmButton.setVisibility(View.VISIBLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Alışverişi Tamamla");
        }
    }

    private void processPayment() {
        // Show loading
        confirmButton.setEnabled(false);
        confirmButton.setText("در حال پردازش...");

        // Simulate payment processing with fake bank gateway
        new android.os.Handler().postDelayed(() -> {
            // Simulate bank gateway response
            boolean paymentSuccess = simulateBankGateway();

            if (paymentSuccess) {
                // Save order
                String orderId = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
                
                // Get cart items
                List<CartItem> cartItems = cartManager.getCartItems().getValue();
                if (cartItems != null) {
                    List<OrderItem> orderItems = new ArrayList<>();
                    for (CartItem item : cartItems) {
                        try {
                            long productId = item.getProductId();
                            String productName = item.getProductName();
                            double price = item.getProductPrice();
                            int quantity = item.getQuantity();
                            orderItems.add(new OrderItem(
                                0, // orderId will be set later
                                productId,
                                productName,
                                price,
                                quantity,
                                item.getProductImage()
                            ));
                        } catch (NumberFormatException e) {
                            // If product ID is not a number, use a default value
                            orderItems.add(new OrderItem(
                                0, // orderId will be set later
                                0, // default productId
                                item.getProductName(),
                                item.getProductPrice(),
                                item.getQuantity(),
                                item.getProductImage()
                            ));
                        }
                    }

                    orderManager.saveOrder(
                        1, // Default user ID for now
                        totalPrice,
                        addressInput.getText().toString(),
                        orderItems
                    );

                    // Clear cart
                    cartManager.clearCart();

                    // Return to MainActivity with success flag
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("payment_success", true);
                    startActivity(intent);
                    finish();
                } else {
                    // Handle case where cart items are null
                    if (!isFinishing()) {
                        confirmButton.setEnabled(true);
                        confirmButton.setText("پرداخت");
                        showErrorDialog("خطا در دریافت اطلاعات سبد خرید");
                    }
                }
            } else {
                // Show error message
                if (!isFinishing()) {
                    confirmButton.setEnabled(true);
                    confirmButton.setText("پرداخت");
                    showErrorDialog("پرداخت ناموفق بود. لطفا دوباره تلاش کنید");
                }
            }
        }, 2000); // Simulate 2 second delay
    }

    private boolean simulateBankGateway() {
        // Simulate bank gateway validation
        String cardNumber = cardNumberInput.getText().toString().trim();
        String expiry = expiryInput.getText().toString().trim();
        String cvv = cvvInput.getText().toString().trim();

        // Simple validation rules
        if (cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
            return false;
        }
        if (!expiry.matches("\\d{2}/\\d{2}")) {
            return false;
        }
        if (cvv.length() != 3 || !cvv.matches("\\d+")) {
            return false;
        }

        // Simulate random success/failure (80% success rate)
        return Math.random() < 0.8;
    }

    private void showErrorDialog(String message) {
        if (isFinishing()) return;
        
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setMessage(message)
               .setPositiveButton("باشه", (dialog, id) -> dialog.dismiss())
               .setCancelable(false);
        builder.create().show();
    }

    private void loadCartItems() {
        cartManager.getCartItems().observe(this, items -> {
            if (items != null) {
                double total = calculateTotal(items);
                totalPriceText.setText(String.format("Toplam: %.2f TL", total));
            }
        });
    }

    private double calculateTotal(List<CartItem> items) {
        double total = 0;
        for (CartItem item : items) {
            total += item.getProductPrice() * item.getQuantity();
        }
        return total;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (isPaymentStep) {
                // Go back to contact info
                isPaymentStep = false;
                findViewById(R.id.contactFields).setVisibility(View.VISIBLE);
                findViewById(R.id.paymentFields).setVisibility(View.GONE);
                nextButton.setVisibility(View.VISIBLE);
                confirmButton.setVisibility(View.GONE);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Alışverişi Tamamla");
                }
                return true;
            }
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 