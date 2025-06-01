package com.example.shoppingapp.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.shoppingapp.database.AppDatabase;
import com.example.shoppingapp.database.UserDao;
import com.example.shoppingapp.models.User;
import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private final UserDao userDao;
    private final LiveData<List<User>> allUsers;

    public UserViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(application);
        userDao = database.userDao();
        allUsers = userDao.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public LiveData<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public void insert(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.insertUser(user));
    }

    public void update(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.updateUser(user));
    }

    public void delete(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.deleteUser(user));
    }

    public void deleteUserByEmail(String email) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.deleteUserByEmail(email));
    }
} 