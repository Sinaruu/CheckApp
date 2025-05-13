package com.egorkivilev.checkapp.view;

import com.egorkivilev.checkapp.Model.Task;
import com.egorkivilev.checkapp.controller.TaskController;
import com.egorkivilev.checkapp.util.TimeFormatter;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TaskUI {
    private JFrame frame;
    private JList<String> taskList;
    private JLabel taskName, taskDate, taskPriority;
    private JTextArea messageArea;
    private JButton addTaskButton, removeTaskButton;
    private JPanel buttonPanel, rightPanel, headerPanel, root;
    private JScrollPane messageScroll, leftScroll;

    private DefaultListModel<String> listModel;
    private TaskController taskController;

    private Task openedTask;

    public TaskUI(TaskController taskController) {
        this.taskController = taskController;
        init();
    }

    private void init() {
        FlatLightLaf.setup();

        frame = new JFrame("CheckApp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);

        root = new JPanel(new BorderLayout());
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setContentPane(root);

        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        leftScroll = new JScrollPane(taskList);
        leftScroll.setPreferredSize(new Dimension(250, 0));
        root.add(leftScroll, BorderLayout.WEST);

        rightPanel = new JPanel(new BorderLayout());

        headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageScroll = new JScrollPane(messageArea);

        buttonPanel = new JPanel();
        addTaskButton = new JButton("Add New Task");
        addTaskButton.addActionListener(e -> taskController.buttonEvent(e));

        removeTaskButton = new JButton("Remove Selected Task");
        removeTaskButton.addActionListener(e -> taskController.buttonEvent(e));
        removeTaskButton.setEnabled(false);

        buttonPanel.add(addTaskButton);
        buttonPanel.add(removeTaskButton);

        rightPanel.add(headerPanel, BorderLayout.NORTH);
        rightPanel.add(messageScroll, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        root.add(rightPanel, BorderLayout.CENTER);

        frame.setMinimumSize(new Dimension(800, 500));
        frame.setVisible(true);

        taskList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedTask = taskList.getSelectedValue();
                if (selectedTask != null) {
                    taskController.taskSelectEvent(selectedTask.substring(6));
                    removeTaskButton.setEnabled(true);
                }
            }
        });

        refreshTaskList();
    }

    public void refreshTaskList() {
        ArrayList<Task> tasks = taskController.getTasks();
        listModel = new DefaultListModel<>();
        for (Task task : tasks) {
            switch (task.getPriority()) {
                case LOW -> listModel.addElement("[ ! ] " + task.getName());
                case MEDIUM -> listModel.addElement("[! !] " + task.getName());
                case HIGH -> listModel.addElement("[!!!] " + task.getName());
                case VERY_HIGH -> listModel.addElement("[***] " + task.getName());
                default -> listModel.addElement("[   ] " + task.getName());
            }
        }
        taskList.setModel(listModel);
    }

    public Task getOpenedTask() {
        return openedTask;
    }

    public void openTask(Task task) {
        headerPanel.removeAll();

        openedTask = task;
        taskName = new JLabel("Name: " + task.getName());
        taskDate = new JLabel("Created on: " + TimeFormatter.formatUnixTime(task.getDate()));
        taskPriority = new JLabel("Priority: " + task.getPriority().toString());
        headerPanel.add(taskName);
        headerPanel.add(taskDate);
        headerPanel.add(taskPriority);
        messageArea.setText(task.getDescription());
        headerPanel.revalidate();
        headerPanel.repaint();
    }

    public void cleanDetails() {
        taskName.setText("");
        taskDate.setText("");
        taskPriority.setText("");
        messageArea.setText("");

        removeTaskButton.setEnabled(false);
    }

    public void enableRemoveButton(boolean enable) {
        removeTaskButton.setEnabled(enable);
    }
}
