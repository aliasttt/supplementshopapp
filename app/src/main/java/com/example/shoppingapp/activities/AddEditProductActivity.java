package com.example.shoppingapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.example.shoppingapp.models.Product;
import com.example.shoppingapp.utils.ProductManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class AddEditProductActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText nameEditText;
    private EditText priceEditText;
    private EditText descriptionEditText;
    private EditText stockEditText;
    private EditText categoryEditText;
    private ImageView productImageView;
    private Button selectImageButton;
    private Button saveButton;
    private ProductManager productManager;
    private Uri selectedImageUri;
    private boolean isEditMode = false;
    private long productId = -1;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                selectedImageUri = result.getData().getData();
                Glide.with(this)
                    .load(selectedImageUri)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(productImageView);
            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_product);

        // Initialize managers
        productManager = ProductManager.getInstance(this);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(isEditMode ? "Ürünü Düzenle" : "Yeni Ürün Ekle");
        }

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText);
        priceEditText = findViewById(R.id.priceEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        stockEditText = findViewById(R.id.stockEditText);
        categoryEditText = findViewById(R.id.categoryEditText);
        productImageView = findViewById(R.id.productImageView);
        selectImageButton = findViewById(R.id.selectImageButton);
        saveButton = findViewById(R.id.saveButton);

        // Check if we're in edit mode
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("product_id")) {
            isEditMode = true;
            productId = intent.getLongExtra("product_id", -1);
            loadProductDetails();
        }

        // Setup click listeners
        selectImageButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(intent1);
        });

        saveButton.setOnClickListener(v -> saveProduct());
    }

    private void loadProductDetails() {
        if (productId != -1) {
            Product product = productManager.getProductById(productId);
            if (product != null) {
                nameEditText.setText(product.getName());
                priceEditText.setText(String.valueOf(product.getPrice()));
                descriptionEditText.setText(product.getDescription());
                stockEditText.setText(String.valueOf(product.getStock()));
                categoryEditText.setText(product.getCategory());
                
                // Load product image
                if (product.getImageResourceName() != null && !product.getImageResourceName().isEmpty()) {
                    File imageFile = new File(getFilesDir(), product.getImageResourceName());
                    if (imageFile.exists()) {
                        Glide.with(this)
                            .load(imageFile)
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.placeholder_image)
                            .into(productImageView);
                    }
                }
            }
        }
    }

    private void saveProduct() {
        String name = nameEditText.getText().toString().trim();
        String priceStr = priceEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String stockStr = stockEditText.getText().toString().trim();
        String category = categoryEditText.getText().toString().trim();

        if (name.isEmpty() || priceStr.isEmpty() || description.isEmpty() || stockStr.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        int stock;
        try {
            price = Double.parseDouble(priceStr);
            stock = Integer.parseInt(stockStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Lütfen geçerli fiyat ve stok değerleri girin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedImageUri == null && !isEditMode) {
            Toast.makeText(this, "Lütfen bir resim seçin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save image to internal storage
        String imageFileName = null;
        if (selectedImageUri != null) {
            try {
                imageFileName = saveImageToInternalStorage(selectedImageUri);
            } catch (IOException e) {
                Toast.makeText(this, "Resim kaydedilirken hata oluştu", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Create or update product
        Product product = new Product(name, description, price, imageFileName, stock, category);
        if (isEditMode) {
            product.setId(productId);
            productManager.updateProduct(product);
            Toast.makeText(this, "Ürün başarıyla güncellendi", Toast.LENGTH_SHORT).show();
        } else {
            productManager.insertProduct(product);
            Toast.makeText(this, "Ürün başarıyla eklendi", Toast.LENGTH_SHORT).show();
        }

        // Set result and finish
        setResult(RESULT_OK);
        finish();
    }

    private String saveImageToInternalStorage(Uri imageUri) throws IOException {
        String fileName = "product_" + UUID.randomUUID().toString() + ".jpg";
        File outputFile = new File(getFilesDir(), fileName);
        
        try (InputStream inputStream = getContentResolver().openInputStream(imageUri);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            if (inputStream != null) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        }
        
        return fileName;
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