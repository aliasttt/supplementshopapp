package com.example.shoppingapp.database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shoppingapp.models.User;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private final UserDao userDao;
    private final MutableLiveData<User> currentUser = new MutableLiveData<>();
    private final ExecutorService executorService;
    private final LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        userDao = db.userDao();
        executorService = Executors.newSingleThreadExecutor();
        allUsers = userDao.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public LiveData<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public CompletableFuture<User> getUserByEmailAsync(String email) {
        CompletableFuture<User> future = new CompletableFuture<>();
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                User user = userDao.getUserByEmail(email).getValue();
                future.complete(user);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        return future;
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

    public CompletableFuture<Boolean> registerUser(String email, String password, String name) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                User existingUser = userDao.getUserByEmail(email).getValue();
                if (existingUser != null) {
                    future.complete(false);
                    return;
                }

                User newUser = new User(
                    email,
                    password,
                    name
                );
                userDao.insertUser(newUser);
                future.complete(true);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    public LiveData<User> login(String email, String password) {
        MutableLiveData<User> result = new MutableLiveData<>();
        executorService.execute(() -> {
            try {
                User user = userDao.getUserByEmail(email).getValue();
                if (user != null && user.getPassword().equals(password)) {
                    result.postValue(user);
                    currentUser.postValue(user);
                } else {
                    result.postValue(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.postValue(null);
            }
        });
        return result;
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser.setValue(null);
    }

    public User getUserByEmailSync(String email) {
        return userDao.getUserByEmail(email).getValue();
    }
} 