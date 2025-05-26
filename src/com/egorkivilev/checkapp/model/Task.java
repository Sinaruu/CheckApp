package com.egorkivilev.checkapp.model;

public class Task {
    private String name;
    private String description;
    private long date;
    private long dueDate;
    private PriorityType priority;
    private boolean completed;

    /**
     * Constructor (made mostly for FileHandler)
     * @param name
     * @param description
     * @param date
     * @param dueDate
     * @param priority
     * @param completed
     */
    public Task(String name, String description, long date, long dueDate, PriorityType priority, boolean completed) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.dueDate = date;
        this.priority = priority;
        this.completed = completed;
    }

    /**
     * Constructor with less data provided (made for user creations)
     * @param name
     * @param description
     * @param priority
     */
    public Task(String name, String description, PriorityType priority) {
        this.name = name;
        this.description = description;
        this.priority = priority;

        completed = false;
        date = System.currentTimeMillis();
        dueDate = date; // Due dates arent implemented yet
    }

    /**
     * Returns a name of the task
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Returns description
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns date
     * @return
     */
    public long getDate() {
        return date;
    }

    /**
     * Returns tasks priority
     * @return
     */
    public PriorityType getPriority() {
        return priority;
    }

    /**
     * Returns the status of the task
     * @return
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Changes tasks status
     */
    public void toggleCompleted() {
        completed = !completed;
    }

    /**
     * Returns a formatted string to store in the file
     * @return
     */
    public String toCSVString() {
        String result = "\"" + name + "\",\"";
        result += description + "\",\"";
        result += date + "\",\"";
        result += dueDate + "\",\"";
        result += priority + "\",";
        result += completed;

        return result;
    }
}
