package com.egorkivilev.checkapp.util;

import com.egorkivilev.checkapp.model.PriorityType;
import com.egorkivilev.checkapp.model.Task;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {
    // Filename that is used to store tasks
    public static final String FILE_NAME = "checkapp_tasks.dat";

    /**
     * Parses the line to split the fields properly
     * @param line to parse
     * @return parsed line
     */
    static ArrayList<String> parseLine(String line) {
        ArrayList<String> fields = new ArrayList<>();
        int index = 0;
        String field = "";
        boolean inQuotes = false;
        char ch;
        for (int i = 0; i < line.length(); i++) {
            ch = line.charAt(i);
            if (ch == '"') {
                inQuotes = !inQuotes;
            } else if (ch == ',' && !inQuotes) {
                fields.add(index++, field.strip());
                field = "";
            } else {
                field = field + ch;
            }
        }
        fields.add(index++, field.strip());
        return fields;
    }

    /**
     * Attempts to load data from a file
     * @return array of tasks
     * @throws IOException
     */
    public static ArrayList<Task> loadTasks() throws IOException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            ArrayList<Task> tasks = new ArrayList<>();
            ArrayList<String> fields = new ArrayList<>();

            String line;

            while ((line = br.readLine()) != null) {
                fields = parseLine(line);

                String name = fields.get(0);
                String description = fields.get(1);
                long date = Long.parseLong(fields.get(2));
                long dueDate = Long.parseLong(fields.get(3));
                PriorityType priority = PriorityType.fromString(fields.get(4));
                boolean completed = Boolean.parseBoolean(fields.get(5));

                tasks.add(new Task(name, description, date, dueDate, priority, completed));
            }

            br.close();
            return tasks;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Attempts to save data from a file
     * @param tasks array
     * @throws IOException
     */
    public static void saveTasks(ArrayList<Task> tasks) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for(Task task : tasks) {
                writer.write(task.toCSVString());
                writer.newLine();
            }
        }
    }
}
