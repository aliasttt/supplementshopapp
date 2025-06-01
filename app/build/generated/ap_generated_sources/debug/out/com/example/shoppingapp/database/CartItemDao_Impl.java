package com.example.shoppingapp.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.shoppingapp.models.CartItem;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CartItemDao_Impl implements CartItemDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CartItem> __insertionAdapterOfCartItem;

  private final EntityDeletionOrUpdateAdapter<CartItem> __deletionAdapterOfCartItem;

  private final EntityDeletionOrUpdateAdapter<CartItem> __updateAdapterOfCartItem;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllCartItemsForUser;

  public CartItemDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCartItem = new EntityInsertionAdapter<CartItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `cart_items` (`id`,`product_id`,`product_name`,`product_price`,`quantity`,`product_image`,`user_email`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final CartItem entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProductId());
        if (entity.getProductName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getProductName());
        }
        statement.bindDouble(4, entity.getProductPrice());
        statement.bindLong(5, entity.getQuantity());
        if (entity.getProductImage() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getProductImage());
        }
        if (entity.getUserEmail() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getUserEmail());
        }
      }
    };
    this.__deletionAdapterOfCartItem = new EntityDeletionOrUpdateAdapter<CartItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `cart_items` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final CartItem entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfCartItem = new EntityDeletionOrUpdateAdapter<CartItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `cart_items` SET `id` = ?,`product_id` = ?,`product_name` = ?,`product_price` = ?,`quantity` = ?,`product_image` = ?,`user_email` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final CartItem entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProductId());
        if (entity.getProductName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getProductName());
        }
        statement.bindDouble(4, entity.getProductPrice());
        statement.bindLong(5, entity.getQuantity());
        if (entity.getProductImage() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getProductImage());
        }
        if (entity.getUserEmail() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getUserEmail());
        }
        statement.bindLong(8, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAllCartItemsForUser = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM cart_items WHERE user_email = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertCartItem(final CartItem cartItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCartItem.insert(cartItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteCartItem(final CartItem cartItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCartItem.handle(cartItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateCartItem(final CartItem cartItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCartItem.handle(cartItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllCartItemsForUser(final String userEmail) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllCartItemsForUser.acquire();
    int _argIndex = 1;
    if (userEmail == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, userEmail);
    }
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAllCartItemsForUser.release(_stmt);
    }
  }

  @Override
  public LiveData<List<CartItem>> getCartItemsForUser(final String userEmail) {
    final String _sql = "SELECT * FROM cart_items WHERE user_email = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userEmail == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userEmail);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"cart_items"}, false, new Callable<List<CartItem>>() {
      @Override
      @Nullable
      public List<CartItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "product_id");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "product_name");
          final int _cursorIndexOfProductPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "product_price");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfProductImage = CursorUtil.getColumnIndexOrThrow(_cursor, "product_image");
          final int _cursorIndexOfUserEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "user_email");
          final List<CartItem> _result = new ArrayList<CartItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CartItem _item;
            _item = new CartItem();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            _item.setProductId(_tmpProductId);
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            _item.setProductName(_tmpProductName);
            final double _tmpProductPrice;
            _tmpProductPrice = _cursor.getDouble(_cursorIndexOfProductPrice);
            _item.setProductPrice(_tmpProductPrice);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            _item.setQuantity(_tmpQuantity);
            final String _tmpProductImage;
            if (_cursor.isNull(_cursorIndexOfProductImage)) {
              _tmpProductImage = null;
            } else {
              _tmpProductImage = _cursor.getString(_cursorIndexOfProductImage);
            }
            _item.setProductImage(_tmpProductImage);
            final String _tmpUserEmail;
            if (_cursor.isNull(_cursorIndexOfUserEmail)) {
              _tmpUserEmail = null;
            } else {
              _tmpUserEmail = _cursor.getString(_cursorIndexOfUserEmail);
            }
            _item.setUserEmail(_tmpUserEmail);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<CartItem> getCartItemsForUserSync(final String userEmail) {
    final String _sql = "SELECT * FROM cart_items WHERE user_email = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userEmail == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userEmail);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "product_id");
      final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "product_name");
      final int _cursorIndexOfProductPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "product_price");
      final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
      final int _cursorIndexOfProductImage = CursorUtil.getColumnIndexOrThrow(_cursor, "product_image");
      final int _cursorIndexOfUserEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "user_email");
      final List<CartItem> _result = new ArrayList<CartItem>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final CartItem _item;
        _item = new CartItem();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final long _tmpProductId;
        _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
        _item.setProductId(_tmpProductId);
        final String _tmpProductName;
        if (_cursor.isNull(_cursorIndexOfProductName)) {
          _tmpProductName = null;
        } else {
          _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
        }
        _item.setProductName(_tmpProductName);
        final double _tmpProductPrice;
        _tmpProductPrice = _cursor.getDouble(_cursorIndexOfProductPrice);
        _item.setProductPrice(_tmpProductPrice);
        final int _tmpQuantity;
        _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
        _item.setQuantity(_tmpQuantity);
        final String _tmpProductImage;
        if (_cursor.isNull(_cursorIndexOfProductImage)) {
          _tmpProductImage = null;
        } else {
          _tmpProductImage = _cursor.getString(_cursorIndexOfProductImage);
        }
        _item.setProductImage(_tmpProductImage);
        final String _tmpUserEmail;
        if (_cursor.isNull(_cursorIndexOfUserEmail)) {
          _tmpUserEmail = null;
        } else {
          _tmpUserEmail = _cursor.getString(_cursorIndexOfUserEmail);
        }
        _item.setUserEmail(_tmpUserEmail);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public CartItem getCartItemByProductId(final String productId, final String userEmail) {
    final String _sql = "SELECT * FROM cart_items WHERE product_id = ? AND user_email = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (productId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, productId);
    }
    _argIndex = 2;
    if (userEmail == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userEmail);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "product_id");
      final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "product_name");
      final int _cursorIndexOfProductPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "product_price");
      final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
      final int _cursorIndexOfProductImage = CursorUtil.getColumnIndexOrThrow(_cursor, "product_image");
      final int _cursorIndexOfUserEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "user_email");
      final CartItem _result;
      if (_cursor.moveToFirst()) {
        _result = new CartItem();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final long _tmpProductId;
        _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
        _result.setProductId(_tmpProductId);
        final String _tmpProductName;
        if (_cursor.isNull(_cursorIndexOfProductName)) {
          _tmpProductName = null;
        } else {
          _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
        }
        _result.setProductName(_tmpProductName);
        final double _tmpProductPrice;
        _tmpProductPrice = _cursor.getDouble(_cursorIndexOfProductPrice);
        _result.setProductPrice(_tmpProductPrice);
        final int _tmpQuantity;
        _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
        _result.setQuantity(_tmpQuantity);
        final String _tmpProductImage;
        if (_cursor.isNull(_cursorIndexOfProductImage)) {
          _tmpProductImage = null;
        } else {
          _tmpProductImage = _cursor.getString(_cursorIndexOfProductImage);
        }
        _result.setProductImage(_tmpProductImage);
        final String _tmpUserEmail;
        if (_cursor.isNull(_cursorIndexOfUserEmail)) {
          _tmpUserEmail = null;
        } else {
          _tmpUserEmail = _cursor.getString(_cursorIndexOfUserEmail);
        }
        _result.setUserEmail(_tmpUserEmail);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Double> getTotalPriceLive(final String userEmail) {
    final String _sql = "SELECT SUM(product_price * quantity) FROM cart_items WHERE user_email = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userEmail == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userEmail);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"cart_items"}, false, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
