package com.example.tasks;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SubjectDao {
    @Query("SELECT * FROM subjects ORDER BY name ASC")
    LiveData<List<SubjectEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SubjectEntity subject);
}
