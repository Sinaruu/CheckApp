/**
 * Will manage task objects and store neatly
 */

package com.egorkivilev.checkapp.model;

import com.egorkivilev.checkapp.controller.TaskController;

import java.util.ArrayList;

public class TaskManager {
    ArrayList<Task> taskList;

    TaskController taskController;

    public TaskManager(TaskController taskController) {
        taskList = new ArrayList<>();
        this.taskController = taskController;
    }

    public void addTask(Task task) {
        taskList.add(task);
        taskController.updateList();
    }

    public void removeTask(Task task) {
        taskList.remove(task);
        taskController.updateList();
    }

    public void setTask(int index, Task task) {
        if(index >= 0 && index < taskList.size()) {
            taskList.set(index, task);
        }

        taskController.updateList();
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public boolean isCompleted(Task task) {
        return task.isCompleted();
    }

    public void changeCompleted(Task task) {
        task.changeCompleted();
    }

    public Task findTask(String name) {
        for(Task task : taskList) {
            if(task.getName().equals(name)) {
                return task;
            }
        }

        return null;
    }
}
