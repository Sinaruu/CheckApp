package com.egorkivilev.checkapp.model;

public class Task {
    private String name;
    private String description;
    private long date;
    private PriorityType priority;
    private boolean completed;

    public Task(String name, String description, int date, PriorityType priority, boolean completed) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.priority = priority;
        this.completed = completed;
    }

    public Task(String name, String description, PriorityType priority) {
        this.name = name;
        this.description = description;
        this.priority = priority;

        completed = false;
        date = System.currentTimeMillis();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getDate() {
        return date;
    }

    public PriorityType getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void toggleCompleted() {
        completed = !completed;
    }
}
