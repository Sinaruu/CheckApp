/**
 * Will manage task objects and store neatly
 */

package com.egorkivilev.checkapp.model;

import com.egorkivilev.checkapp.controller.TaskController;

import java.util.ArrayList;

public class TaskManager {
    ArrayList<Task> taskList;

    TaskController taskController;

    /**
     * Constructor
     * @param taskController
     * @param taskList
     */
    public TaskManager(TaskController taskController, ArrayList<Task> taskList) {
        this.taskController = taskController;
        this.taskList = taskList;
    }

    /**
     * Adds a task
     * @param task
     */
    public void addTask(Task task) {
        taskList.add(task);
        taskController.updateList();
    }

    /**
     * Removes a task
     * @param task
     */
    public void removeTask(Task task) {
        taskList.remove(task);
        taskController.updateList();
    }

    /**
     * Replaces a task with new details
     * @param index
     * @param task
     */
    public void setTask(int index, Task task) {
        if(index >= 0 && index < taskList.size()) {
            taskList.set(index, task);
        }

        taskController.updateList();
    }

    /**
     * Returns an array of tasks
     * @return
     */
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    /**
     * Changes the status of the task
     * @param task
     */
    public void toggleCompleted(Task task) {
        task.toggleCompleted();
        taskController.updateList();
    }

    /**
     * Searches for the task in array
     * @param name
     * @return
     */
    public Task findTask(String name) {
        for(Task task : taskList) {
            if(task.getName().equals(name)) {
                return task;
            }
        }

        return null;
    }
}
