package com.example.tasks;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class TaskEntity {
    @PrimaryKey
    public long id;
    public String title;
    public String subjectName;
    public long deadline;
    public int duration;
    public boolean status;
    public int importance;
    public int difficulty;

    public TaskEntity(
            long id,
            String title,
            String subjectName,
            long deadline,
            int duration,
            boolean status,
            int importance,
            int difficulty
    ) {
        this.id = id;
        this.title = title;
        this.subjectName = subjectName;
        this.deadline = deadline;
        this.duration = duration;
        this.status = status;
        this.importance = importance;
        this.difficulty = difficulty;
    }
}
