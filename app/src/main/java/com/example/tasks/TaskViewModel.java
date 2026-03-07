package com.example.tasks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class TaskViewModel extends ViewModel {
    private final MutableLiveData<List<Task>> tasks = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Task>> getTasks() { return tasks; }

    public void addTask(Task task) {
        List<Task> current = new ArrayList<>(tasks.getValue());
        current.add(task);
        tasks.setValue(current);
    }

    public void removeTask(long id) {
        List<Task> current = new ArrayList<>(tasks.getValue());
        current.removeIf(t -> t.getId() == id);
        tasks.setValue(current);
    }
}
