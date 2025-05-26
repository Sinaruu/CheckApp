package com.egorkivilev.checkapp.view;

import com.egorkivilev.checkapp.model.PriorityType;
import com.egorkivilev.checkapp.controller.TaskController;
import com.egorkivilev.checkapp.model.Task;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

public class TaskDialog {
    private JFrame frame;
    private JPanel panel;
    private JLabel taskName;
    private JTextField taskNameTextField;
    private JLabel taskDescription;
    private JTextArea taskDescriptionField;
    private JScrollPane descriptionScrollPane;
    private JLabel taskPriorityLabel;
    private JComboBox<String> taskPriorityComboBox;
    private JButton button;
    private JLabel statusLabel;
    private GroupLayout layout;

    private TaskController taskController;
    private boolean editing = false;
    Task selectedTask;

    /**
     * Constructor
     * @param taskController
     * @throws IOException
     */
    public TaskDialog(TaskController taskController) throws IOException {
        this.taskController = taskController;
        init();
    }

    /**
     * More advanced constructor, made for editing task window
     * @param taskController
     * @param task
     * @throws IOException
     */
    public TaskDialog(TaskController taskController, Task task) throws IOException {
        this.taskController = taskController;
        editing = true;
        selectedTask = task;
        init();
    }

    /**
     * Initialize the UI and open it
     * @throws IOException
     */
    private void init() throws IOException {
        FlatLightLaf.setup();

        frame = new JFrame("Add Task");
        panel = new JPanel();

        taskName = new JLabel("Name");
        taskNameTextField = new JTextField(20);
        taskNameTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateButton();
            }
        });

        taskDescription = new JLabel("Description");
        taskDescriptionField = new JTextArea(4, 20); // 4 rows, 20 columns
        taskDescriptionField.setLineWrap(true);
        taskDescriptionField.setWrapStyleWord(true);
        descriptionScrollPane = new JScrollPane(taskDescriptionField,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        taskPriorityLabel = new JLabel("Priority");
        taskPriorityComboBox = new JComboBox(PriorityType.values());

        if(editing) {
            button = new JButton("Edit");
            button.addActionListener(e -> {
                try {
                    taskController.buttonEvent(e, selectedTask);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            taskNameTextField.setText(selectedTask.getName());
            taskDescriptionField.setText(selectedTask.getDescription());
            taskPriorityComboBox.setSelectedItem(selectedTask.getPriority());

            button.setEnabled(true);
        } else {
            button = new JButton("Add");
            button.addActionListener(e -> {
                try {
                    taskController.buttonEvent(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            button.setEnabled(false);
        }

        if(editing) {
            statusLabel = new JLabel();
        } else {
            statusLabel = new JLabel("Name must be not empty");
        }

        layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(taskName)
                                .addComponent(taskPriorityLabel)
                                .addComponent(taskDescription))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(taskNameTextField)
                                .addComponent(taskPriorityComboBox)
                                .addComponent(descriptionScrollPane)
                                .addComponent(button)
                                .addComponent(statusLabel))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(taskName)
                                .addComponent(taskNameTextField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(taskPriorityLabel)
                                .addComponent(taskPriorityComboBox))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(taskDescription)
                                .addComponent(descriptionScrollPane))
                        .addComponent(button)
                        .addComponent(statusLabel)
        );

        layout.linkSize(SwingConstants.HORIZONTAL, taskNameTextField, descriptionScrollPane);

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                taskController.onDialogClosed();
            }
        });

        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * Update the state of add/edit button to check if the current inputs are valid
     */
    public void updateButton() {
        if(statusLabel != null) {
            if(taskNameTextField.getText().isEmpty()) {
                button.setEnabled(false);
                statusLabel.setText("Name must be not empty");
            } else if(taskController.findTask(taskNameTextField.getText()) != null && !editing) {
                button.setEnabled(false);
                statusLabel.setText("This name is already in use");
            } else {
                button.setEnabled(true);
                statusLabel.setText("");
            }
        }
    }

    /**
     * Gets data from the dialog and returns as array
     * @return
     */
    public ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        data.add(taskNameTextField.getText());
        data.add(taskPriorityComboBox.getSelectedItem().toString());
        data.add(taskDescriptionField.getText());

        return data;
    }

    /**
     * Well, it's probably self-explanatory...
     */
    public void close() {
        frame.dispose();
    }
}