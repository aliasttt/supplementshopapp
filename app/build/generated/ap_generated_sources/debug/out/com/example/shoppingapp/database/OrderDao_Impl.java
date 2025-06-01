package com.example.shoppingapp.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.shoppingapp.models.Order;
import com.example.shoppingapp.models.OrderItem;
import com.example.shoppingapp.utils.DateConverter;
import com.example.shoppingapp.utils.OrderItemConverter;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class OrderDao_Impl implements OrderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Order> __insertionAdapterOfOrder;

  private final EntityDeletionOrUpdateAdapter<Order> __updateAdapterOfOrder;

  public OrderDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfOrder = new EntityInsertionAdapter<Order>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `orders` (`id`,`userId`,`totalAmount`,`status`,`shippingAddress`,`orderDate`,`items`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Order entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindDouble(3, entity.getTotalAmount());
        if (entity.getStatus() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getStatus());
        }
        if (entity.getShippingAddress() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getShippingAddress());
        }
        final Long _tmp = DateConverter.toTimestamp(entity.getOrderDate());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp);
        }
        final String _tmp_1 = OrderItemConverter.fromOrderItemList(entity.getItems());
        if (_tmp_1 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_1);
        }
      }
    };
    this.__updateAdapterOfOrder = new EntityDeletionOrUpdateAdapter<Order>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `orders` SET `id` = ?,`userId` = ?,`totalAmount` = ?,`status` = ?,`shippingAddress` = ?,`orderDate` = ?,`items` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Order entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindDouble(3, entity.getTotalAmount());
        if (entity.getStatus() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getStatus());
        }
        if (entity.getShippingAddress() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getShippingAddress());
        }
        final Long _tmp = DateConverter.toTimestamp(entity.getOrderDate());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp);
        }
        final String _tmp_1 = OrderItemConverter.fromOrderItemList(entity.getItems());
        if (_tmp_1 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_1);
        }
        statement.bindLong(8, entity.getId());
      }
    };
  }

  @Override
  public void insert(final Order order) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfOrder.insert(order);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Order order) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfOrder.handle(order);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Order>> getOrdersByUserId(final int userId) {
    final String _sql = "SELECT * FROM orders WHERE userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"orders"}, false, new Callable<List<Order>>() {
      @Override
      @Nullable
      public List<Order> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfShippingAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "shippingAddress");
          final int _cursorIndexOfOrderDate = CursorUtil.getColumnIndexOrThrow(_cursor, "orderDate");
          final int _cursorIndexOfItems = CursorUtil.getColumnIndexOrThrow(_cursor, "items");
          final List<Order> _result = new ArrayList<Order>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Order _item;
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final String _tmpShippingAddress;
            if (_cursor.isNull(_cursorIndexOfShippingAddress)) {
              _tmpShippingAddress = null;
            } else {
              _tmpShippingAddress = _cursor.getString(_cursorIndexOfShippingAddress);
            }
            final Date _tmpOrderDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfOrderDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfOrderDate);
            }
            _tmpOrderDate = DateConverter.toDate(_tmp);
            final List<OrderItem> _tmpItems;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfItems)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfItems);
            }
            _tmpItems = OrderItemConverter.toOrderItemList(_tmp_1);
            _item = new Order(_tmpUserId,_tmpTotalAmount,_tmpStatus,_tmpShippingAddress,_tmpOrderDate,_tmpItems);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
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
  public LiveData<List<Order>> getAllOrders() {
    final String _sql = "SELECT * FROM orders";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"orders"}, false, new Callable<List<Order>>() {
      @Override
      @Nullable
      public List<Order> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfShippingAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "shippingAddress");
          final int _cursorIndexOfOrderDate = CursorUtil.getColumnIndexOrThrow(_cursor, "orderDate");
          final int _cursorIndexOfItems = CursorUtil.getColumnIndexOrThrow(_cursor, "items");
          final List<Order> _result = new ArrayList<Order>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Order _item;
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final String _tmpShippingAddress;
            if (_cursor.isNull(_cursorIndexOfShippingAddress)) {
              _tmpShippingAddress = null;
            } else {
              _tmpShippingAddress = _cursor.getString(_cursorIndexOfShippingAddress);
            }
            final Date _tmpOrderDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfOrderDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfOrderDate);
            }
            _tmpOrderDate = DateConverter.toDate(_tmp);
            final List<OrderItem> _tmpItems;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfItems)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfItems);
            }
            _tmpItems = OrderItemConverter.toOrderItemList(_tmp_1);
            _item = new Order(_tmpUserId,_tmpTotalAmount,_tmpStatus,_tmpShippingAddress,_tmpOrderDate,_tmpItems);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
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
  public LiveData<Order> getOrderById(final int orderId) {
    final String _sql = "SELECT * FROM orders WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, orderId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"orders"}, false, new Callable<Order>() {
      @Override
      @Nullable
      public Order call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfShippingAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "shippingAddress");
          final int _cursorIndexOfOrderDate = CursorUtil.getColumnIndexOrThrow(_cursor, "orderDate");
          final int _cursorIndexOfItems = CursorUtil.getColumnIndexOrThrow(_cursor, "items");
          final Order _result;
          if (_cursor.moveToFirst()) {
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final String _tmpShippingAddress;
            if (_cursor.isNull(_cursorIndexOfShippingAddress)) {
              _tmpShippingAddress = null;
            } else {
              _tmpShippingAddress = _cursor.getString(_cursorIndexOfShippingAddress);
            }
            final Date _tmpOrderDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfOrderDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfOrderDate);
            }
            _tmpOrderDate = DateConverter.toDate(_tmp);
            final List<OrderItem> _tmpItems;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfItems)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfItems);
            }
            _tmpItems = OrderItemConverter.toOrderItemList(_tmp_1);
            _result = new Order(_tmpUserId,_tmpTotalAmount,_tmpStatus,_tmpShippingAddress,_tmpOrderDate,_tmpItems);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
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
