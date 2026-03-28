package com.example.tasks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private final TaskRepository repository;
    private final LiveData<List<Task>> tasks;
    private final LiveData<List<Task>> archivedTasks;
    private final LiveData<List<Subject>> subjects;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        tasks = repository.getActiveTasks();
        archivedTasks = repository.getArchivedTasks();
        subjects = repository.getSubjects();
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }

    public LiveData<List<Task>> getArchivedTasks() {
        return archivedTasks;
    }

    public LiveData<Task> getTaskById(long id) {
        return repository.getTaskById(id);
    }

    public LiveData<List<Subject>> getSubjects() {
        return subjects;
    }

    public void saveTask(Task task) {
        repository.insertTask(task);
    }

    public void addSubject(Subject subject) {
        repository.insertSubject(subject);
    }

    public void removeTask(long id) {
        repository.deleteTask(id);
    }

    public void updateTaskStatus(Task task, boolean status) {
        repository.updateTaskStatus(task, status);
    }
}
