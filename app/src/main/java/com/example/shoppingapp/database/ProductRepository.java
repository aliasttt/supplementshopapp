package com.example.shoppingapp.database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.shoppingapp.models.Product;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepository {
    private final ProductDao productDao;
    private final LiveData<List<Product>> allProducts;
    private final ExecutorService executorService;

    public ProductRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        productDao = db.productDao();
        allProducts = productDao.getAllProducts();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public LiveData<Product> getProductById(String id) {
        return productDao.getProductById(id);
    }

    public void insertProduct(Product product) {
        executorService.execute(() -> productDao.insertProduct(product));
    }

    public void updateProduct(Product product) {
        executorService.execute(() -> productDao.updateProduct(product));
    }

    public void deleteProduct(Product product) {
        executorService.execute(() -> productDao.deleteProduct(product));
    }
} 