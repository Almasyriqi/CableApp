package com.example.cableapp;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.cableapp.database.AppDatabase;
import com.example.cableapp.database.CableDao;
import com.example.cableapp.database.CableEntity;
import com.example.cableapp.database.UserDao;
import com.example.cableapp.database.UserEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private UserDao userDao;
    private CableDao cableDao;
    private ExecutorService executorService;

    public Repository(Context context){
        AppDatabase database = AppDatabase.getInstance(context);
        userDao = database.userDao();
        cableDao = database.cableDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insertUser(UserEntity user) {
        executorService.execute(() -> userDao.insertUser(user));
    }

    public UserEntity login(String username, String password){
        return userDao.login(username, password);
    }

    public UserEntity getEmail(String email){
        return userDao.getEmail(email);
    }

    public void updatePassword(UserEntity user){
        executorService.execute(() -> userDao.updatePassword(user));
    }

    public LiveData<List<CableEntity>> getAllCables() {
        return cableDao.getAllCables();
    }

    public void insertCable(int length){
        executorService.execute(()->cableDao.insertCable(length));
    }

    public void deleteTable(){
        cableDao.deleteTable();
    }

    public void resetTable(){
        cableDao.resetTable();
    }
}
