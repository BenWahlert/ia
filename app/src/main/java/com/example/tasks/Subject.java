package com.example.tasks;

public class Subject {
    private String name;
    public Subject(String name) {
        this.name = name;
    }
    public String getSubject() {
        return name;
    }
    public void renameSubject(String name) {
       this.name = name;
    }
}
