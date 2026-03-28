package com.example.tasks;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private final TaskDao taskDao;
    private final SubjectDao subjectDao;
    private final LiveData<List<Task>> activeTasks;
    private final LiveData<List<Task>> archivedTasks;
    private final LiveData<List<Subject>> subjects;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    public TaskRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        taskDao = db.taskDao();
        subjectDao = db.subjectDao();

        activeTasks = Transformations.map(taskDao.getActive(), this::mapTasks);
        archivedTasks = Transformations.map(taskDao.getArchived(), this::mapTasks);
        subjects = Transformations.map(subjectDao.getAll(), this::mapSubjects);
    }

    public LiveData<List<Task>> getActiveTasks() {
        return activeTasks;
    }

    public LiveData<List<Task>> getArchivedTasks() {
        return archivedTasks;
    }

    public LiveData<Task> getTaskById(long id) {
        return Transformations.map(taskDao.getById(id), this::mapTask);
    }

    public LiveData<List<Subject>> getSubjects() {
        return subjects;
    }

    public void insertTask(Task task) {
        executor.execute(() -> {
            taskDao.insert(toEntity(task));
        });
    }

    public void deleteTask(long id) {
        executor.execute(() -> {
            taskDao.deleteById(id);
        });
    }

    public void updateTaskStatus(Task task, boolean status) {
        executor.execute(() -> {
            taskDao.updateStatus(task.getId(), status);
        });
    }

    public void insertSubject(Subject subject) {
        executor.execute(() -> subjectDao.insert(new SubjectEntity(0, subject.getSubject())));
    }

    private TaskEntity toEntity(Task task) {
        return new TaskEntity(
                task.getId(),
                task.getTitle(),
                task.getSubject().getSubject(),
                task.getDeadline(),
                task.getDuration(),
                task.getStatus(),
                task.getImportance(),
                task.getDifficulty()
        );
    }

    private Task mapTask(TaskEntity entity) {
        if (entity == null) {
            return null;
        }

        Task task = new Task(
                entity.id,
                entity.title,
                new Subject(entity.subjectName),
                entity.deadline,
                entity.duration,
                entity.status,
                entity.importance,
                entity.difficulty
        );
        return task;
    }

    private List<Task> mapTasks(List<TaskEntity> entities) {
        List<Task> mapped = new ArrayList<>();
        if (entities == null) {
            return mapped;
        }

        for (TaskEntity entity : entities) {
            Task task = mapTask(entity);
            if (task != null) {
                mapped.add(task);
            }
        }

        mapped.sort((left, right) -> Double.compare(right.getPriority(), left.getPriority()));
        return mapped;
    }

    private List<Subject> mapSubjects(List<SubjectEntity> entities) {
        List<Subject> mapped = new ArrayList<>();
        if (entities == null) {
            return mapped;
        }

        for (SubjectEntity entity : entities) {
            mapped.add(new Subject(entity.name));
        }

        return mapped;
    }
}
