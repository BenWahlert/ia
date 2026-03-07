package com.example.tasks;

public class Task {
    private long id;
    private String title;
    private Subject subject;
    private long deadline;
    private int duration;
    private boolean status;

    public Task(String title, Subject subject, long deadline, int duration) {
        this.id = System.currentTimeMillis();
        this.title = title;
        this.subject = subject;
        this.deadline = deadline;
        this.duration = duration;
        this.status = false;
    }
    public long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title=title;
    }
    public Subject getSubject() {
        return subject;
    }
    public void setSubject(Subject subject) {
        this.subject=subject;
    }
    public long getDeadline() {
        return deadline;
    }
    public void setDeadline(long deadline) {
        this.deadline=deadline;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration=duration;
    }
    public boolean getStatus() {
        return status;
    }
    public void setStatus() {
        status = !status;
    }
}
