package com.example.cableapp.database;

import androidx.room.*;

@Dao
public interface UserDao {
    @Insert
    void insertUser(UserEntity user);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    UserEntity login(String email, String password);

    @Query("SELECT * FROM users WHERE email LIKE :email")
    UserEntity getEmail(String email);

    @Update
    void updatePassword(UserEntity user);
}
