package com.example.tasks;

public class Task {
    private long id;
    private String title;
    private Subject subject;
    private long deadline;
    private int duration;
    private boolean status;
    private int importance;
    private int difficulty;
    private double priority;
    private double daysRemaining;
    private double urgencyScore;
    private double importanceScore;
    private double durationScore;
    private double difficultyScore;

    public Task(String title, Subject subject, long deadline, int duration, int importance, int difficulty) {
        this(System.currentTimeMillis(), title, subject, deadline, duration, false, importance, difficulty);
    }

    public Task(long id, String title, Subject subject, long deadline, int duration, boolean status, int importance, int difficulty) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.deadline = deadline;
        this.duration = duration;
        this.status = status;
        this.importance = importance;
        this.difficulty = difficulty;
        calculatePriority();
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
    public void setStatus(boolean status) {
        this.status = status;
    }
    public void calculatePriority() {
        daysRemaining = (deadline - System.currentTimeMillis()) / 86400000;
        urgencyScore = Math.max(0, 100 - (daysRemaining / 30) * 100);
        importanceScore = (importance / 5.0) * 100;
        durationScore = Math.min(100, (duration / 480.0) * 100);
        difficultyScore = (difficulty / 5.0) * 100;
        priority = (urgencyScore * 0.5) + (importanceScore * 0.15) + (difficultyScore * 0.15) + (durationScore * 0.2);
    }
    public int getImportance() {
        return importance;
    }
    public int getDifficulty() {
        return difficulty;
    }
    public double getPriority() {
        return priority;
    }
}
