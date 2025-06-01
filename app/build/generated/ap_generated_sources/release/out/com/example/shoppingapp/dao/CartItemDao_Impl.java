package com.example.shoppingapp.dao;

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

  private final SharedSQLiteStatement __preparedStmtOfClearCart;

  public CartItemDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCartItem = new EntityInsertionAdapter<CartItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `cart_items` (`id`,`product_id`,`quantity`,`user_email`,`product_name`,`product_price`,`product_image_url`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final CartItem entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getProduct_id() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getProduct_id());
        }
        statement.bindLong(3, entity.getQuantity());
        if (entity.getUserEmail() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getUserEmail());
        }
        if (entity.getProductName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getProductName());
        }
        statement.bindDouble(6, entity.getProductPrice());
        if (entity.getProductImageUrl() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getProductImageUrl());
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
        return "UPDATE OR ABORT `cart_items` SET `id` = ?,`product_id` = ?,`quantity` = ?,`user_email` = ?,`product_name` = ?,`product_price` = ?,`product_image_url` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final CartItem entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getProduct_id() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getProduct_id());
        }
        statement.bindLong(3, entity.getQuantity());
        if (entity.getUserEmail() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getUserEmail());
        }
        if (entity.getProductName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getProductName());
        }
        statement.bindDouble(6, entity.getProductPrice());
        if (entity.getProductImageUrl() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getProductImageUrl());
        }
        statement.bindLong(8, entity.getId());
      }
    };
    this.__preparedStmtOfClearCart = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM cart_items WHERE user_email = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final CartItem cartItem) {
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
  public void delete(final CartItem cartItem) {
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
  public void update(final CartItem cartItem) {
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
  public void clearCart(final String userEmail) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfClearCart.acquire();
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
      __preparedStmtOfClearCart.release(_stmt);
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
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUserEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "user_email");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "product_name");
          final int _cursorIndexOfProductPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "product_price");
          final int _cursorIndexOfProductImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "product_image_url");
          final List<CartItem> _result = new ArrayList<CartItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CartItem _item;
            final String _tmpProduct_id;
            if (_cursor.isNull(_cursorIndexOfProductId)) {
              _tmpProduct_id = null;
            } else {
              _tmpProduct_id = _cursor.getString(_cursorIndexOfProductId);
            }
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            _item = new CartItem(_tmpProduct_id,_tmpQuantity);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpUserEmail;
            if (_cursor.isNull(_cursorIndexOfUserEmail)) {
              _tmpUserEmail = null;
            } else {
              _tmpUserEmail = _cursor.getString(_cursorIndexOfUserEmail);
            }
            _item.setUserEmail(_tmpUserEmail);
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
            final String _tmpProductImageUrl;
            if (_cursor.isNull(_cursorIndexOfProductImageUrl)) {
              _tmpProductImageUrl = null;
            } else {
              _tmpProductImageUrl = _cursor.getString(_cursorIndexOfProductImageUrl);
            }
            _item.setProductImageUrl(_tmpProductImageUrl);
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
  public CartItem getCartItem(final String userEmail, final String productId) {
    final String _sql = "SELECT * FROM cart_items WHERE user_email = ? AND product_id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (userEmail == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userEmail);
    }
    _argIndex = 2;
    if (productId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, productId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "product_id");
      final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
      final int _cursorIndexOfUserEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "user_email");
      final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "product_name");
      final int _cursorIndexOfProductPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "product_price");
      final int _cursorIndexOfProductImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "product_image_url");
      final CartItem _result;
      if (_cursor.moveToFirst()) {
        final String _tmpProduct_id;
        if (_cursor.isNull(_cursorIndexOfProductId)) {
          _tmpProduct_id = null;
        } else {
          _tmpProduct_id = _cursor.getString(_cursorIndexOfProductId);
        }
        final int _tmpQuantity;
        _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
        _result = new CartItem(_tmpProduct_id,_tmpQuantity);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpUserEmail;
        if (_cursor.isNull(_cursorIndexOfUserEmail)) {
          _tmpUserEmail = null;
        } else {
          _tmpUserEmail = _cursor.getString(_cursorIndexOfUserEmail);
        }
        _result.setUserEmail(_tmpUserEmail);
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
        final String _tmpProductImageUrl;
        if (_cursor.isNull(_cursorIndexOfProductImageUrl)) {
          _tmpProductImageUrl = null;
        } else {
          _tmpProductImageUrl = _cursor.getString(_cursorIndexOfProductImageUrl);
        }
        _result.setProductImageUrl(_tmpProductImageUrl);
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
  public int getItemCount(final String userEmail) {
    final String _sql = "SELECT COUNT(*) FROM cart_items WHERE user_email = ?";
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
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public double getTotalPrice(final String userEmail) {
    final String _sql = "SELECT SUM(product_price * quantity) FROM cart_items WHERE user_email = ?";
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
      final double _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getDouble(0);
      } else {
        _result = 0.0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
