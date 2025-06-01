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
import com.example.shoppingapp.models.UserProfile;
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
public final class UserProfileDao_Impl implements UserProfileDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserProfile> __insertionAdapterOfUserProfile;

  private final EntityDeletionOrUpdateAdapter<UserProfile> __deletionAdapterOfUserProfile;

  private final EntityDeletionOrUpdateAdapter<UserProfile> __updateAdapterOfUserProfile;

  private final SharedSQLiteStatement __preparedStmtOfDeleteUserProfileByEmail;

  public UserProfileDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserProfile = new EntityInsertionAdapter<UserProfile>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_profiles` (`email`,`name`,`phoneNumber`,`address`,`city`,`postalCode`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final UserProfile entity) {
        if (entity.getEmail() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getEmail());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getPhoneNumber() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getPhoneNumber());
        }
        if (entity.getAddress() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getAddress());
        }
        if (entity.getCity() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCity());
        }
        if (entity.getPostalCode() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPostalCode());
        }
      }
    };
    this.__deletionAdapterOfUserProfile = new EntityDeletionOrUpdateAdapter<UserProfile>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `user_profiles` WHERE `email` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final UserProfile entity) {
        if (entity.getEmail() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getEmail());
        }
      }
    };
    this.__updateAdapterOfUserProfile = new EntityDeletionOrUpdateAdapter<UserProfile>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `user_profiles` SET `email` = ?,`name` = ?,`phoneNumber` = ?,`address` = ?,`city` = ?,`postalCode` = ? WHERE `email` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final UserProfile entity) {
        if (entity.getEmail() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getEmail());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getPhoneNumber() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getPhoneNumber());
        }
        if (entity.getAddress() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getAddress());
        }
        if (entity.getCity() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCity());
        }
        if (entity.getPostalCode() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPostalCode());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getEmail());
        }
      }
    };
    this.__preparedStmtOfDeleteUserProfileByEmail = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM user_profiles WHERE email = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertUserProfile(final UserProfile userProfile) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUserProfile.insert(userProfile);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteUserProfile(final UserProfile userProfile) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfUserProfile.handle(userProfile);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateUserProfile(final UserProfile userProfile) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfUserProfile.handle(userProfile);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteUserProfileByEmail(final String email) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteUserProfileByEmail.acquire();
    int _argIndex = 1;
    if (email == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, email);
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
      __preparedStmtOfDeleteUserProfileByEmail.release(_stmt);
    }
  }

  @Override
  public LiveData<UserProfile> getUserProfile(final String email) {
    final String _sql = "SELECT * FROM user_profiles WHERE email = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_profiles"}, false, new Callable<UserProfile>() {
      @Override
      @Nullable
      public UserProfile call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
          final int _cursorIndexOfPostalCode = CursorUtil.getColumnIndexOrThrow(_cursor, "postalCode");
          final UserProfile _result;
          if (_cursor.moveToFirst()) {
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpPhoneNumber;
            if (_cursor.isNull(_cursorIndexOfPhoneNumber)) {
              _tmpPhoneNumber = null;
            } else {
              _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            }
            final String _tmpAddress;
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _tmpAddress = null;
            } else {
              _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            }
            final String _tmpCity;
            if (_cursor.isNull(_cursorIndexOfCity)) {
              _tmpCity = null;
            } else {
              _tmpCity = _cursor.getString(_cursorIndexOfCity);
            }
            final String _tmpPostalCode;
            if (_cursor.isNull(_cursorIndexOfPostalCode)) {
              _tmpPostalCode = null;
            } else {
              _tmpPostalCode = _cursor.getString(_cursorIndexOfPostalCode);
            }
            _result = new UserProfile(_tmpEmail,_tmpName,_tmpPhoneNumber,_tmpAddress,_tmpCity,_tmpPostalCode);
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

  @Override
  public LiveData<List<UserProfile>> getAllUserProfiles() {
    final String _sql = "SELECT * FROM user_profiles";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_profiles"}, false, new Callable<List<UserProfile>>() {
      @Override
      @Nullable
      public List<UserProfile> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
          final int _cursorIndexOfPostalCode = CursorUtil.getColumnIndexOrThrow(_cursor, "postalCode");
          final List<UserProfile> _result = new ArrayList<UserProfile>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UserProfile _item;
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpPhoneNumber;
            if (_cursor.isNull(_cursorIndexOfPhoneNumber)) {
              _tmpPhoneNumber = null;
            } else {
              _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            }
            final String _tmpAddress;
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _tmpAddress = null;
            } else {
              _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            }
            final String _tmpCity;
            if (_cursor.isNull(_cursorIndexOfCity)) {
              _tmpCity = null;
            } else {
              _tmpCity = _cursor.getString(_cursorIndexOfCity);
            }
            final String _tmpPostalCode;
            if (_cursor.isNull(_cursorIndexOfPostalCode)) {
              _tmpPostalCode = null;
            } else {
              _tmpPostalCode = _cursor.getString(_cursorIndexOfPostalCode);
            }
            _item = new UserProfile(_tmpEmail,_tmpName,_tmpPhoneNumber,_tmpAddress,_tmpCity,_tmpPostalCode);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
