package com.example.shoppingapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.ContentValues;
import com.example.shoppingapp.models.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private static ProductManager instance;
    private final SQLiteOpenHelper dbHelper;
    private final List<ProductUpdateListener> listeners = new ArrayList<>();

    public interface ProductUpdateListener {
        void onProductsUpdated();
    }

    private ProductManager(Context context) {
        dbHelper = new SQLiteOpenHelper(context, "shopping_app.db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(
                    "CREATE TABLE products (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "description TEXT," +
                    "price REAL NOT NULL," +
                    "image_resource_name TEXT," +
                    "stock INTEGER DEFAULT 0," +
                    "category TEXT" +
                    ")"
                );
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS products");
                onCreate(db);
            }
        };
    }

    public static synchronized ProductManager getInstance(Context context) {
        if (instance == null) {
            instance = new ProductManager(context.getApplicationContext());
        }
        return instance;
    }

    public void addProductUpdateListener(ProductUpdateListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeProductUpdateListener(ProductUpdateListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (ProductUpdateListener listener : listeners) {
            listener.onProductsUpdated();
        }
    }

    public void insertProduct(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("image_resource_name", product.getImageResourceName());
        values.put("stock", product.getStock());
        values.put("category", product.getCategory());

        long id = db.insert("products", null, values);
        product.setId(id);
        notifyListeners();
    }

    public void updateProduct(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("image_resource_name", product.getImageResourceName());
        values.put("stock", product.getStock());
        values.put("category", product.getCategory());

        db.update("products", values, "id = ?", new String[]{String.valueOf(product.getId())});
        notifyListeners();
    }

    public void deleteProduct(long productId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("products", "id = ?", new String[]{String.valueOf(productId)});
        notifyListeners();
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("products", null, null, null, null, null, "id DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Product product = new Product(
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
                    cursor.getString(cursor.getColumnIndexOrThrow("image_resource_name")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("stock")),
                    cursor.getString(cursor.getColumnIndexOrThrow("category"))
                );
                product.setId(cursor.getLong(cursor.getColumnIndexOrThrow("id")));
                products.add(product);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return products;
    }

    public Product getProductById(long productId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("products", null, "id = ?", new String[]{String.valueOf(productId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Product product = new Product(
                cursor.getString(cursor.getColumnIndexOrThrow("name")),
                cursor.getString(cursor.getColumnIndexOrThrow("description")),
                cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
                cursor.getString(cursor.getColumnIndexOrThrow("image_resource_name")),
                cursor.getInt(cursor.getColumnIndexOrThrow("stock")),
                cursor.getString(cursor.getColumnIndexOrThrow("category"))
            );
            product.setId(cursor.getLong(cursor.getColumnIndexOrThrow("id")));
            cursor.close();
            return product;
        }
        return null;
    }
} 