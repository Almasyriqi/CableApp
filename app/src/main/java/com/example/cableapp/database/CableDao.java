package com.example.cableapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import java.util.List;

@Dao
public interface CableDao {

    @Query("SELECT * FROM cables ORDER BY id ASC")
    LiveData<List<CableEntity>> getAllCables();

    @Query("INSERT INTO cables (length) VALUES(:length) ")
    void insertCable(int length);

    @Query("DELETE FROM cables")
    void deleteTable();

    @Query("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='cables'")
    void resetTable();
}
