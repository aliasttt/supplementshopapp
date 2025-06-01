package com.example.shoppingapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.models.Product;
import com.example.shoppingapp.utils.ProductManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AdminPanelActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private EditText nameInput, descriptionInput, priceInput, stockInput, categoryInput;
    private ImageView productImage;
    private Button addButton, selectImageButton;
    private String selectedImageName;
    private ProductManager productManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        productManager = ProductManager.getInstance(this);
        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        nameInput = findViewById(R.id.nameInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        priceInput = findViewById(R.id.priceInput);
        stockInput = findViewById(R.id.stockInput);
        categoryInput = findViewById(R.id.categoryInput);
        productImage = findViewById(R.id.productImage);
        addButton = findViewById(R.id.addButton);
        selectImageButton = findViewById(R.id.selectImageButton);
    }

    private void setupListeners() {
        selectImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE);
        });

        addButton.setOnClickListener(v -> addProduct());
    }

    private void addProduct() {
        String name = nameInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();
        String priceStr = priceInput.getText().toString().trim();
        String stockStr = stockInput.getText().toString().trim();
        String category = categoryInput.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        int stock;
        try {
            price = Double.parseDouble(priceStr);
            stock = Integer.parseInt(stockStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers for price and stock", Toast.LENGTH_SHORT).show();
            return;
        }

        Product product = new Product(name, description, price, null, stock, category);
        productManager.insertProduct(product);
        Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();
        clearInputs();
    }

    private void clearInputs() {
        nameInput.setText("");
        descriptionInput.setText("");
        priceInput.setText("");
        stockInput.setText("");
        categoryInput.setText("");
        productImage.setImageResource(0);
        selectedImageName = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            try {
                Uri selectedImage = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                
                // Generate unique filename
                selectedImageName = "product_" + System.currentTimeMillis() + ".jpg";
                File outputFile = new File(getFilesDir(), selectedImageName);
                
                FileOutputStream outputStream = new FileOutputStream(outputFile);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();

                // Display the selected image
                productImage.setImageURI(selectedImage);
            } catch (Exception e) {
                Toast.makeText(this, "خطا در بارگذاری تصویر", Toast.LENGTH_SHORT).show();
            }
        }
    }
} 