/**
 * A bridge between ui and data logic
 * Handles button events
 * Coordinates between TaskManager and TaskUI
 * Updates the list for the user
 */

package com.egorkivilev.checkapp.controller;

import com.egorkivilev.checkapp.model.PriorityType;
import com.egorkivilev.checkapp.model.Task;
import com.egorkivilev.checkapp.model.TaskManager;
import com.egorkivilev.checkapp.view.TaskDialog;
import com.egorkivilev.checkapp.view.TaskUI;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class TaskController {
    TaskManager taskManager;
    TaskUI taskUI;
    TaskDialog taskDialog;
    boolean taskOpen = false;

    public TaskController() {
        taskManager = new TaskManager(this);
        taskUI = new TaskUI(this);
    }

    public void buttonEvent(ActionEvent e) {
        if(e.getActionCommand().equals("Add New Task")) {
            if(!taskOpen) {
                taskDialog = new TaskDialog(this);
                taskOpen = true;
            }
        }
        else if(e.getActionCommand().equals("Add")) {
            ArrayList<String> data = taskDialog.getData();
            if(!data.get(0).isEmpty() && !data.get(1).isEmpty()) {
                taskDialog.close();

                addTask(data);
            }
        }

        else if(e.getActionCommand().equals("Remove Selected Task")) {
            Task selectedTask = taskUI.getOpenedTask();
            taskManager.removeTask(selectedTask);
            taskUI.cleanDetails();
        }
        else if(e.getActionCommand().equals("Edit Task")) {
            Task selectedTask = taskUI.getOpenedTask();
            taskDialog = new TaskDialog(this, selectedTask);
        }
        else {
            System.out.println("Task Controller - Invalid Action");
        }
    }

    public void buttonEvent(ActionEvent e, Task task) {
        if(e.getActionCommand().equals("Edit")) {
            ArrayList<String> data = taskDialog.getData();
            if(!data.get(0).isEmpty() && !data.get(1).isEmpty()) {
                taskDialog.close();
                editTask(task, data);
            }
        }
    }

    public void onDialogClosed() {
        taskOpen = false;
        taskDialog = null;
    }

    public ArrayList<Task> getTasks() {
        return taskManager.getTaskList();
    }

    public void addTask(ArrayList<String> data) {
        String name = data.get(0);
        PriorityType priority = PriorityType.fromString(data.get(1));
        String description = data.get(2);

        Task newTask = new Task(name, description, priority);
        taskManager.addTask(newTask);
        taskSelectEvent(newTask);
    }

    public void editTask(Task task, ArrayList<String> data) {
        int index = taskManager.getTaskList().indexOf(task);

        String newName = data.get(0);
        PriorityType priority = PriorityType.fromString(data.get(1));
        String description = data.get(2);

        Task newTask = new Task(newName, description, priority);
        taskManager.setTask(index, newTask);
        taskSelectEvent(newTask);
    }

    public void taskSelectEvent(String selectedTask) {
        Task task = taskManager.findTask(selectedTask);
        if(task != null) {
            taskUI.openTask(task);
        }
    }

    public void taskSelectEvent(Task selectedTask) {
        taskUI.openTask(selectedTask);
    }

    public void updateList() {
        taskUI.refreshTaskList();
    }

    public Task findTask(String line) {
        return taskManager.findTask(line);
    }

    public void setTask(int index, Task task) {
        taskManager.setTask(index, task);
    }
}
