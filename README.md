# CheckApp

**CheckApp** is a simple desktop task management application built in Java using Swing. It allows users to create, view, and manage tasks with priority levels and timestamps in a clean and minimal interface.

## Features

- Add new tasks with a name, description, and priority (High, Medium, Low)
- Automatically timestamps tasks on creation
- View task details including priority, description, and created date
- Responsive interface built with Java Swing and FlatLaf for modern UI styling
- MVC architecture for separation of concerns and maintainability

## Technologies

- Java 17
- Swing (UI)
- FlatLaf

## Structure

- `Model` – Handles task data and logic (Task, TaskManager, PriorityType)
- `View` – User interface components (TaskUI, AddTaskDialog)
- `Controller` – Connects UI and logic (TaskController)

## Getting Started

Compile and run with:

```bash
javac -d bin src/com/egorkivilev/checkapp/**/*.java
java -cp bin com.egorkivilev.checkapp.Main
