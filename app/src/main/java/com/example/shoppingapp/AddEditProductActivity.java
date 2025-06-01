package com.example.shoppingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import com.example.shoppingapp.models.Product;
import com.example.shoppingapp.viewmodels.ProductViewModel;
import java.util.UUID;
import com.bumptech.glide.Glide;

public class AddEditProductActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    
    private ImageView productImage;
    private TextInputEditText nameEditText;
    private TextInputEditText descriptionEditText;
    private TextInputEditText priceEditText;
    private TextInputEditText stockEditText;
    private TextInputEditText categoryEditText;
    private Button selectImageButton;
    private Button saveButton;
    
    private ProductViewModel productViewModel;
    private Uri selectedImageUri;
    private String productId;
    private boolean isEditMode = false;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                selectedImageUri = result.getData().getData();
                productImage.setImageURI(selectedImageUri);
            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_product);

        // Initialize ViewModel
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        // Initialize views
        initializeViews();

        // Check if we're in edit mode
        productId = getIntent().getStringExtra("product_id");
        if (productId != null) {
            isEditMode = true;
            loadProductDetails();
        }

        // Setup click listeners
        setupClickListeners();
    }

    private void initializeViews() {
        productImage = findViewById(R.id.productImage);
        nameEditText = findViewById(R.id.nameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        priceEditText = findViewById(R.id.priceEditText);
        stockEditText = findViewById(R.id.stockEditText);
        categoryEditText = findViewById(R.id.categoryEditText);
        selectImageButton = findViewById(R.id.selectImageButton);
        saveButton = findViewById(R.id.saveButton);
    }

    private void loadProductDetails() {
        productViewModel.getProductById(productId).observe(this, product -> {
            if (product != null) {
                nameEditText.setText(product.getName());
                descriptionEditText.setText(product.getDescription());
                priceEditText.setText(String.valueOf(product.getPrice()));
                stockEditText.setText(String.valueOf(product.getStock()));
                categoryEditText.setText(product.getCategory());
                
                // Load existing image if available
                if (product.getImageResourceName() != null && !product.getImageResourceName().isEmpty()) {
                    Glide.with(this)
                        .load(getResources().getIdentifier(product.getImageResourceName(), "drawable", getPackageName()))
                        .into(productImage);
                }
            }
        });
    }

    private void setupClickListeners() {
        selectImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(intent);
        });

        saveButton.setOnClickListener(v -> saveProduct());
    }

    private void saveProduct() {
        String name = nameEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String priceStr = priceEditText.getText().toString().trim();
        String stockStr = stockEditText.getText().toString().trim();
        String category = categoryEditText.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() || priceStr.isEmpty() || 
            stockStr.isEmpty() || category.isEmpty() || selectedImageUri == null) {
            Toast.makeText(this, "لطفا تمام فیلدها را پر کنید", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        int stock = Integer.parseInt(stockStr);

        Product product = new Product(
            name,
            description,
            price,
            selectedImageUri.toString(),
            stock,
            category
        );
        
        // Set the ID if in edit mode
        if (isEditMode) {
            product.setId(Long.parseLong(productId));
        }

        if (isEditMode) {
            productViewModel.update(product);
            Toast.makeText(this, "محصول با موفقیت ویرایش شد", Toast.LENGTH_SHORT).show();
        } else {
            productViewModel.insert(product);
            Toast.makeText(this, "محصول با موفقیت اضافه شد", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
} 