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
import com.egorkivilev.checkapp.util.FileHandler;
import com.egorkivilev.checkapp.view.TaskDialog;
import com.egorkivilev.checkapp.view.TaskUI;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class TaskController {
    TaskManager taskManager;
    TaskUI taskUI;
    TaskDialog taskDialog;
    boolean taskOpen = false;

    public TaskController() throws IOException {
        ArrayList<Task> fileTasks = loadTasks();

        taskManager = new TaskManager(this, fileTasks);
        taskUI = new TaskUI(this);
    }

    /**
     * Catches events from all buttons used on the app
     * @param e button
     * @throws IOException FileHandler
     */
    public void buttonEvent(ActionEvent e) throws IOException {
        // Opens a dialog to create a new task
        if(e.getActionCommand().equals("Add New Task")) {
            if(!taskOpen) {
                taskDialog = new TaskDialog(this);
                taskOpen = true;
            }
        }

        // Inside of dialog to send a task creation request
        else if(e.getActionCommand().equals("Add")) {
            ArrayList<String> data = taskDialog.getData();
            if(!data.get(0).isEmpty() && !data.get(1).isEmpty()) {
                taskDialog.close();

                addTask(data);
            }
        }

        // The following events are located inside of main window
        else if(e.getActionCommand().equals("Remove Selected Task")) {
            Task selectedTask = taskUI.getOpenedTask();
            taskManager.removeTask(selectedTask);
            taskUI.cleanDetails();
        }

        else if(e.getActionCommand().equals("Edit Task")) {
            Task selectedTask = taskUI.getOpenedTask();
            taskDialog = new TaskDialog(this, selectedTask); // Will autofill the data in the dialog
        }

        else if(e.getActionCommand().equals("Mark Complete")) {
            Task selectedTask = taskUI.getOpenedTask();
            taskManager.toggleCompleted(selectedTask);
            taskUI.openTask(selectedTask);

            saveTasks();
        }

        else {
            System.out.println("Task Controller - Invalid Action");
        }
    }

    /**
     * Same as the one above, but with task data
     * @param e button
     * @param task details
     * @throws IOException
     */
    public void buttonEvent(ActionEvent e, Task task) throws IOException {
        // Similar to Add Task request, but replaces a task instead of adding
        if(e.getActionCommand().equals("Edit")) {
            ArrayList<String> data = taskDialog.getData();
            if(!data.get(0).isEmpty() && !data.get(1).isEmpty()) {
                taskDialog.close();
                editTask(task, data);
            }
        }
    }

    /**
     * Allow dialog to be reopened
     */
    public void onDialogClosed() {
        taskOpen = false;
        taskDialog = null;
    }

    public ArrayList<Task> getTasks() {
        return taskManager.getTaskList();
    }

    /**
     * Adds a task to the manager object
     * @param data of the new task
     * @throws IOException
     */
    public void addTask(ArrayList<String> data) throws IOException {
        String name = data.get(0);
        PriorityType priority = PriorityType.fromString(data.get(1));
        String description = data.get(2);

        Task newTask = new Task(name, description, priority);
        taskManager.addTask(newTask);
        taskSelectEvent(newTask);

        saveTasks();
    }

    /**
     * Try to parse the data and edit the task with new data
     * @param task data
     * @param data of the new fields
     * @throws IOException
     */
    public void editTask(Task task, ArrayList<String> data) throws IOException {
        int index = taskManager.getTaskList().indexOf(task);

        String newName = data.get(0);
        PriorityType priority = PriorityType.fromString(data.get(1));
        String description = data.get(2);

        Task newTask = new Task(newName, description, priority);
        taskManager.setTask(index, newTask);
        taskSelectEvent(newTask);

        saveTasks();
    }

    /**
     * When task is selected in the list
     * @param selectedTask in the users UI
     */
    public void taskSelectEvent(String selectedTask) {
        Task task = taskManager.findTask(selectedTask);
        if(task != null) {
            taskUI.openTask(task);
        }
    }

    /**
     * When task is created this will be used
     * @param selectedTask in the users UI
     */
    public void taskSelectEvent(Task selectedTask) {
        taskUI.openTask(selectedTask);
    }

    /**
     * Refreshes the list for user
     */
    public void updateList() {
        taskUI.refreshTaskList();
    }

    /**
     * Search for task by name (case-sensitive)
     * @param line name of the task to search
     * @return task object
     */
    public Task findTask(String line) {
        return taskManager.findTask(line);
    }

    /**
     * Replaces the task with new one
     * @param index of the current task
     * @param task new task
     * @throws IOException
     */
    public void setTask(int index, Task task) throws IOException {
        taskManager.setTask(index, task);
        saveTasks();
    }

    /**
     * Loads tasks from data file
     * @return tasks
     * @throws IOException
     */
    public ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> tasks = FileHandler.loadTasks();

        if(tasks != null) {
            return tasks;
        }

        return new ArrayList<>();
    }

    /**
     * Saves and overwrites (if needed) to the file
     * @throws IOException
     */
    public void saveTasks() throws IOException {
        FileHandler.saveTasks(taskManager.getTaskList());
    }
}
