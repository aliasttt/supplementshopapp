package com.example.shoppingapp.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.shoppingapp.models.Product;
import com.example.shoppingapp.models.User;
import com.example.shoppingapp.models.Category;
import com.example.shoppingapp.models.Order;
import com.example.shoppingapp.models.CartItem;
import com.example.shoppingapp.models.UserProfile;
import com.example.shoppingapp.models.OrderItem;
import com.example.shoppingapp.utils.OrderItemConverter;
import com.example.shoppingapp.utils.DateConverter;

@Database(
    entities = {
        User.class,
        UserProfile.class,
        Product.class,
        CartItem.class,
        Order.class,
        OrderItem.class
    },
    version = 3,
    exportSchema = true
)
@TypeConverters({OrderItemConverter.class, DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "shopping_app_db";
    private static volatile AppDatabase instance;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public abstract UserDao userDao();
    public abstract UserProfileDao userProfileDao();
    public abstract ProductDao productDao();
    public abstract CartItemDao cartItemDao();
    public abstract OrderDao orderDao();
    public abstract OrderItemDao orderItemDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabase.class,
                DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build();
        }
        return instance;
    }
} 