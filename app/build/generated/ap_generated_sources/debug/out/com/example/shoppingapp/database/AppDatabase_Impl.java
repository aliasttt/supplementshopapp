package com.example.shoppingapp.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile UserDao _userDao;

  private volatile UserProfileDao _userProfileDao;

  private volatile ProductDao _productDao;

  private volatile CartItemDao _cartItemDao;

  private volatile OrderDao _orderDao;

  private volatile OrderItemDao _orderItemDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`email` TEXT NOT NULL, `name` TEXT, `password` TEXT, `phone` TEXT, `address` TEXT, `city` TEXT, `postalCode` TEXT, `registrationDate` INTEGER, PRIMARY KEY(`email`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `user_profiles` (`email` TEXT NOT NULL, `name` TEXT, `phoneNumber` TEXT, `address` TEXT, `city` TEXT, `postalCode` TEXT, PRIMARY KEY(`email`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `products` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `description` TEXT, `price` REAL NOT NULL, `imageResourceName` TEXT, `stock` INTEGER NOT NULL, `category` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `cart_items` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `product_id` INTEGER NOT NULL, `product_name` TEXT, `product_price` REAL NOT NULL, `quantity` INTEGER NOT NULL, `product_image` TEXT, `user_email` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `orders` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `totalAmount` REAL NOT NULL, `status` TEXT, `shippingAddress` TEXT, `orderDate` INTEGER, `items` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `order_items` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `orderId` INTEGER NOT NULL, `productId` INTEGER NOT NULL, `productName` TEXT, `productPrice` REAL NOT NULL, `quantity` INTEGER NOT NULL, `productImage` TEXT, FOREIGN KEY(`orderId`) REFERENCES `orders`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`productId`) REFERENCES `products`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_order_items_orderId` ON `order_items` (`orderId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_order_items_productId` ON `order_items` (`productId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'b0f516b485b4ab392409753da77561bc')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `user_profiles`");
        db.execSQL("DROP TABLE IF EXISTS `products`");
        db.execSQL("DROP TABLE IF EXISTS `cart_items`");
        db.execSQL("DROP TABLE IF EXISTS `orders`");
        db.execSQL("DROP TABLE IF EXISTS `order_items`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(8);
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("password", new TableInfo.Column("password", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("phone", new TableInfo.Column("phone", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("address", new TableInfo.Column("address", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("city", new TableInfo.Column("city", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("postalCode", new TableInfo.Column("postalCode", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("registrationDate", new TableInfo.Column("registrationDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.example.shoppingapp.models.User).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsUserProfiles = new HashMap<String, TableInfo.Column>(6);
        _columnsUserProfiles.put("email", new TableInfo.Column("email", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("phoneNumber", new TableInfo.Column("phoneNumber", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("address", new TableInfo.Column("address", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("city", new TableInfo.Column("city", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("postalCode", new TableInfo.Column("postalCode", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserProfiles = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserProfiles = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserProfiles = new TableInfo("user_profiles", _columnsUserProfiles, _foreignKeysUserProfiles, _indicesUserProfiles);
        final TableInfo _existingUserProfiles = TableInfo.read(db, "user_profiles");
        if (!_infoUserProfiles.equals(_existingUserProfiles)) {
          return new RoomOpenHelper.ValidationResult(false, "user_profiles(com.example.shoppingapp.models.UserProfile).\n"
                  + " Expected:\n" + _infoUserProfiles + "\n"
                  + " Found:\n" + _existingUserProfiles);
        }
        final HashMap<String, TableInfo.Column> _columnsProducts = new HashMap<String, TableInfo.Column>(7);
        _columnsProducts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("price", new TableInfo.Column("price", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("imageResourceName", new TableInfo.Column("imageResourceName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("stock", new TableInfo.Column("stock", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("category", new TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProducts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProducts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProducts = new TableInfo("products", _columnsProducts, _foreignKeysProducts, _indicesProducts);
        final TableInfo _existingProducts = TableInfo.read(db, "products");
        if (!_infoProducts.equals(_existingProducts)) {
          return new RoomOpenHelper.ValidationResult(false, "products(com.example.shoppingapp.models.Product).\n"
                  + " Expected:\n" + _infoProducts + "\n"
                  + " Found:\n" + _existingProducts);
        }
        final HashMap<String, TableInfo.Column> _columnsCartItems = new HashMap<String, TableInfo.Column>(7);
        _columnsCartItems.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCartItems.put("product_id", new TableInfo.Column("product_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCartItems.put("product_name", new TableInfo.Column("product_name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCartItems.put("product_price", new TableInfo.Column("product_price", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCartItems.put("quantity", new TableInfo.Column("quantity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCartItems.put("product_image", new TableInfo.Column("product_image", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCartItems.put("user_email", new TableInfo.Column("user_email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCartItems = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCartItems = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCartItems = new TableInfo("cart_items", _columnsCartItems, _foreignKeysCartItems, _indicesCartItems);
        final TableInfo _existingCartItems = TableInfo.read(db, "cart_items");
        if (!_infoCartItems.equals(_existingCartItems)) {
          return new RoomOpenHelper.ValidationResult(false, "cart_items(com.example.shoppingapp.models.CartItem).\n"
                  + " Expected:\n" + _infoCartItems + "\n"
                  + " Found:\n" + _existingCartItems);
        }
        final HashMap<String, TableInfo.Column> _columnsOrders = new HashMap<String, TableInfo.Column>(7);
        _columnsOrders.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("totalAmount", new TableInfo.Column("totalAmount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("status", new TableInfo.Column("status", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("shippingAddress", new TableInfo.Column("shippingAddress", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("orderDate", new TableInfo.Column("orderDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("items", new TableInfo.Column("items", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysOrders = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesOrders = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoOrders = new TableInfo("orders", _columnsOrders, _foreignKeysOrders, _indicesOrders);
        final TableInfo _existingOrders = TableInfo.read(db, "orders");
        if (!_infoOrders.equals(_existingOrders)) {
          return new RoomOpenHelper.ValidationResult(false, "orders(com.example.shoppingapp.models.Order).\n"
                  + " Expected:\n" + _infoOrders + "\n"
                  + " Found:\n" + _existingOrders);
        }
        final HashMap<String, TableInfo.Column> _columnsOrderItems = new HashMap<String, TableInfo.Column>(7);
        _columnsOrderItems.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderItems.put("orderId", new TableInfo.Column("orderId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderItems.put("productId", new TableInfo.Column("productId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderItems.put("productName", new TableInfo.Column("productName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderItems.put("productPrice", new TableInfo.Column("productPrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderItems.put("quantity", new TableInfo.Column("quantity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderItems.put("productImage", new TableInfo.Column("productImage", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysOrderItems = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysOrderItems.add(new TableInfo.ForeignKey("orders", "CASCADE", "NO ACTION", Arrays.asList("orderId"), Arrays.asList("id")));
        _foreignKeysOrderItems.add(new TableInfo.ForeignKey("products", "CASCADE", "NO ACTION", Arrays.asList("productId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesOrderItems = new HashSet<TableInfo.Index>(2);
        _indicesOrderItems.add(new TableInfo.Index("index_order_items_orderId", false, Arrays.asList("orderId"), Arrays.asList("ASC")));
        _indicesOrderItems.add(new TableInfo.Index("index_order_items_productId", false, Arrays.asList("productId"), Arrays.asList("ASC")));
        final TableInfo _infoOrderItems = new TableInfo("order_items", _columnsOrderItems, _foreignKeysOrderItems, _indicesOrderItems);
        final TableInfo _existingOrderItems = TableInfo.read(db, "order_items");
        if (!_infoOrderItems.equals(_existingOrderItems)) {
          return new RoomOpenHelper.ValidationResult(false, "order_items(com.example.shoppingapp.models.OrderItem).\n"
                  + " Expected:\n" + _infoOrderItems + "\n"
                  + " Found:\n" + _existingOrderItems);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "b0f516b485b4ab392409753da77561bc", "cda85d684ee667d18a9585e4fd7c7d7e");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users","user_profiles","products","cart_items","orders","order_items");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `user_profiles`");
      _db.execSQL("DELETE FROM `products`");
      _db.execSQL("DELETE FROM `cart_items`");
      _db.execSQL("DELETE FROM `orders`");
      _db.execSQL("DELETE FROM `order_items`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserProfileDao.class, UserProfileDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProductDao.class, ProductDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CartItemDao.class, CartItemDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(OrderDao.class, OrderDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(OrderItemDao.class, OrderItemDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public UserProfileDao userProfileDao() {
    if (_userProfileDao != null) {
      return _userProfileDao;
    } else {
      synchronized(this) {
        if(_userProfileDao == null) {
          _userProfileDao = new UserProfileDao_Impl(this);
        }
        return _userProfileDao;
      }
    }
  }

  @Override
  public ProductDao productDao() {
    if (_productDao != null) {
      return _productDao;
    } else {
      synchronized(this) {
        if(_productDao == null) {
          _productDao = new ProductDao_Impl(this);
        }
        return _productDao;
      }
    }
  }

  @Override
  public CartItemDao cartItemDao() {
    if (_cartItemDao != null) {
      return _cartItemDao;
    } else {
      synchronized(this) {
        if(_cartItemDao == null) {
          _cartItemDao = new CartItemDao_Impl(this);
        }
        return _cartItemDao;
      }
    }
  }

  @Override
  public OrderDao orderDao() {
    if (_orderDao != null) {
      return _orderDao;
    } else {
      synchronized(this) {
        if(_orderDao == null) {
          _orderDao = new OrderDao_Impl(this);
        }
        return _orderDao;
      }
    }
  }

  @Override
  public OrderItemDao orderItemDao() {
    if (_orderItemDao != null) {
      return _orderItemDao;
    } else {
      synchronized(this) {
        if(_orderItemDao == null) {
          _orderItemDao = new OrderItemDao_Impl(this);
        }
        return _orderItemDao;
      }
    }
  }
}
