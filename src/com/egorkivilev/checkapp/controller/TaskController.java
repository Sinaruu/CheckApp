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
import com.egorkivilev.checkapp.view.AddTaskDialog;
import com.egorkivilev.checkapp.view.TaskUI;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class TaskController {
    TaskManager taskManager;
    TaskUI taskUI;
    AddTaskDialog addTaskDialog;
    boolean taskOpen = false;

    public TaskController() {
        taskManager = new TaskManager(this);
        taskUI = new TaskUI(this);
    }

    public void buttonEvent(ActionEvent e) {
        if(e.getActionCommand().equals("Add New Task")) {
            if(!taskOpen) {
                addTaskDialog = new AddTaskDialog(this);
                taskOpen = true;
            }
        }
        else if(e.getActionCommand().equals("Remove Selected Task")) {
            Task selectedTask = taskUI.getOpenedTask();
            taskManager.removeTask(selectedTask);
            taskUI.cleanDetails();
        }
        else if(e.getActionCommand().equals("Add")) {
            ArrayList<String> data = addTaskDialog.getData();
            if(!data.get(0).isEmpty() && !data.get(1).isEmpty()) {
                addTaskDialog.close();

                addTask(data);
            }
        } else {
            System.out.println("Task Controller - Invalid Action");
        }
    }

    public void onDialogClosed() {
        taskOpen = false;
        addTaskDialog = null;
    }

    public ArrayList<Task> getTasks() {
        return taskManager.getTaskList();
    }

    public void addTask(ArrayList<String> data) {
        //public Task(String name, String description, int date, PriorityType priority)
        String name = data.get(0);
        String description = data.get(2);
        PriorityType priority = PriorityType.fromString(data.get(1));

        taskManager.addTask(new Task(name, description, priority));
    }

    public void taskSelectEvent(String selectedTask) {
        Task task = taskManager.findTask(selectedTask);
        if(task != null) {
            taskUI.openTask(task);
        }
    }

    public void updateList() {
        taskUI.refreshTaskList();
    }
}
