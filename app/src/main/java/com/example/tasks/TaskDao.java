package com.example.tasks;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks WHERE status = 0")
    LiveData<List<TaskEntity>> getActive();

    @Query("SELECT * FROM tasks WHERE status = 1")
    LiveData<List<TaskEntity>> getArchived();

    @Query("SELECT * FROM tasks WHERE id = :id")
    LiveData<TaskEntity> getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TaskEntity task);

    @Query("UPDATE tasks SET status = :status WHERE id = :id")
    void updateStatus(long id, boolean status);

    @Query("DELETE FROM tasks WHERE id = :id")
    void deleteById(long id);
}
