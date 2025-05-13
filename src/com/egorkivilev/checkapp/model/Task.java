package com.egorkivilev.checkapp.model;

public class Task {
    private String name;
    private String description;
    private long date;
    private PriorityType priority;

    public Task(String name, String description, int date, PriorityType priority) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.priority = priority;
    }

    public Task(String name, String description, PriorityType priority) {
        this.name = name;
        this.description = description;
        this.priority = priority;

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
}
