package com.example.shoppingapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.shoppingapp.models.UserProfile;
import java.util.List;

@Dao
public interface UserProfileDao {
    @Query("SELECT * FROM user_profiles WHERE email = :email")
    LiveData<UserProfile> getUserProfile(String email);

    @Query("SELECT * FROM user_profiles")
    LiveData<List<UserProfile>> getAllUserProfiles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserProfile(UserProfile userProfile);

    @Update
    void updateUserProfile(UserProfile userProfile);

    @Delete
    void deleteUserProfile(UserProfile userProfile);

    @Query("DELETE FROM user_profiles WHERE email = :email")
    void deleteUserProfileByEmail(String email);
} 