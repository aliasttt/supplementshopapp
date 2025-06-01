package com.example.shoppingapp.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.shoppingapp.database.AppDatabase;
import com.example.shoppingapp.database.ProductDao;
import com.example.shoppingapp.models.Product;
import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private final ProductDao productDao;
    private final LiveData<List<Product>> allProducts;

    public ProductViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        productDao = db.productDao();
        allProducts = productDao.getAllProducts();
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public LiveData<Product> getProductById(String productId) {
        return productDao.getProductById(productId);
    }

    public LiveData<List<Product>> getProductsByCategory(String category) {
        return productDao.getProductsByCategory(category);
    }

    public LiveData<List<Product>> searchProducts(String query) {
        return productDao.searchProducts(query);
    }

    public void insert(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            productDao.insertProduct(product);
        });
    }

    public void update(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            productDao.updateProduct(product);
        });
    }

    public void delete(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            productDao.deleteProduct(product);
        });
    }
} 