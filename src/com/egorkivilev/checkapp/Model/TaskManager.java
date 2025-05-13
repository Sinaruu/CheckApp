/**
 * Will manage task objects and store neatly
 */

package com.egorkivilev.checkapp.Model;

import com.egorkivilev.checkapp.controller.TaskController;

import java.util.ArrayList;

public class TaskManager {
    ArrayList<Task> taskList;

    TaskController taskController;

    public TaskManager(TaskController taskController) {
        taskList = new ArrayList<>();
        this.taskController = taskController;

        taskList.add(new Task("Make this program work", "Lalala", PriorityType.HIGH));
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

    public Task getTask(int index) {
        return taskList.get(index);
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
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
