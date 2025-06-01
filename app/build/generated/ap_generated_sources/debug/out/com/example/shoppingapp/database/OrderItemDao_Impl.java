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
import com.example.shoppingapp.models.OrderItem;
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
public final class OrderItemDao_Impl implements OrderItemDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<OrderItem> __insertionAdapterOfOrderItem;

  private final EntityDeletionOrUpdateAdapter<OrderItem> __deletionAdapterOfOrderItem;

  private final EntityDeletionOrUpdateAdapter<OrderItem> __updateAdapterOfOrderItem;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOrderItemsForOrder;

  public OrderItemDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfOrderItem = new EntityInsertionAdapter<OrderItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `order_items` (`id`,`orderId`,`productId`,`productName`,`productPrice`,`quantity`,`productImage`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final OrderItem entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getOrderId());
        statement.bindLong(3, entity.getProductId());
        if (entity.getProductName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getProductName());
        }
        statement.bindDouble(5, entity.getProductPrice());
        statement.bindLong(6, entity.getQuantity());
        if (entity.getProductImage() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getProductImage());
        }
      }
    };
    this.__deletionAdapterOfOrderItem = new EntityDeletionOrUpdateAdapter<OrderItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `order_items` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final OrderItem entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfOrderItem = new EntityDeletionOrUpdateAdapter<OrderItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `order_items` SET `id` = ?,`orderId` = ?,`productId` = ?,`productName` = ?,`productPrice` = ?,`quantity` = ?,`productImage` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final OrderItem entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getOrderId());
        statement.bindLong(3, entity.getProductId());
        if (entity.getProductName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getProductName());
        }
        statement.bindDouble(5, entity.getProductPrice());
        statement.bindLong(6, entity.getQuantity());
        if (entity.getProductImage() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getProductImage());
        }
        statement.bindLong(8, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteOrderItemsForOrder = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM order_items WHERE orderId = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final OrderItem orderItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfOrderItem.insert(orderItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final OrderItem orderItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfOrderItem.handle(orderItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final OrderItem orderItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfOrderItem.handle(orderItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteOrderItemsForOrder(final long orderId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOrderItemsForOrder.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, orderId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteOrderItemsForOrder.release(_stmt);
    }
  }

  @Override
  public LiveData<List<OrderItem>> getOrderItemsForOrder(final long orderId) {
    final String _sql = "SELECT * FROM order_items WHERE orderId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, orderId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"order_items"}, false, new Callable<List<OrderItem>>() {
      @Override
      @Nullable
      public List<OrderItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfOrderId = CursorUtil.getColumnIndexOrThrow(_cursor, "orderId");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
          final int _cursorIndexOfProductPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "productPrice");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfProductImage = CursorUtil.getColumnIndexOrThrow(_cursor, "productImage");
          final List<OrderItem> _result = new ArrayList<OrderItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final OrderItem _item;
            final long _tmpOrderId;
            _tmpOrderId = _cursor.getLong(_cursorIndexOfOrderId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            final double _tmpProductPrice;
            _tmpProductPrice = _cursor.getDouble(_cursorIndexOfProductPrice);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final String _tmpProductImage;
            if (_cursor.isNull(_cursorIndexOfProductImage)) {
              _tmpProductImage = null;
            } else {
              _tmpProductImage = _cursor.getString(_cursorIndexOfProductImage);
            }
            _item = new OrderItem(_tmpOrderId,_tmpProductId,_tmpProductName,_tmpProductPrice,_tmpQuantity,_tmpProductImage);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
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
  public LiveData<OrderItem> getOrderItemById(final long id) {
    final String _sql = "SELECT * FROM order_items WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"order_items"}, false, new Callable<OrderItem>() {
      @Override
      @Nullable
      public OrderItem call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfOrderId = CursorUtil.getColumnIndexOrThrow(_cursor, "orderId");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
          final int _cursorIndexOfProductPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "productPrice");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfProductImage = CursorUtil.getColumnIndexOrThrow(_cursor, "productImage");
          final OrderItem _result;
          if (_cursor.moveToFirst()) {
            final long _tmpOrderId;
            _tmpOrderId = _cursor.getLong(_cursorIndexOfOrderId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            final double _tmpProductPrice;
            _tmpProductPrice = _cursor.getDouble(_cursorIndexOfProductPrice);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final String _tmpProductImage;
            if (_cursor.isNull(_cursorIndexOfProductImage)) {
              _tmpProductImage = null;
            } else {
              _tmpProductImage = _cursor.getString(_cursorIndexOfProductImage);
            }
            _result = new OrderItem(_tmpOrderId,_tmpProductId,_tmpProductName,_tmpProductPrice,_tmpQuantity,_tmpProductImage);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _result.setId(_tmpId);
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
