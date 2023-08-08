package com.example.cableapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class, CableEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public  abstract CableDao cableDao();

    private static volatile AppDatabase INSTANCE;

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "cable_database"
            ).build();
        }
        return INSTANCE;
    }
}
