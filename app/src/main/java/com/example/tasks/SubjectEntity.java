package com.example.tasks;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subjects")
public class SubjectEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;

    public SubjectEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
