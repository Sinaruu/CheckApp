# CheckApp

**CheckApp** is a simple desktop task management application built in Java using Swing. It allows users to create, view, and manage tasks with priority levels and timestamps in a clean and minimal interface.

## Features

- Add new tasks with a name, description, and priority (High, Medium, Low)
- Automatically timestamps tasks on creation
- Option to edit the task at anytime
- Ability to markt the task as complete
- View task details including priority, description, and created date
- Responsive interface built with Java Swing and FlatLaf for modern UI styling
- MVC architecture for separation of concerns and maintainability
- Automatically saves all tasks locally so they can be accessed later

## Technologies

- Java 17
- Swing (UI)
- FlatLaf

## Getting Started

Download the latest release .jar file and run it in a discrete folder.

Alternatively, compile and run with:

```bash
javac -d bin src/com/egorkivilev/checkapp/**/*.java
java -cp bin com.egorkivilev.checkapp.Main
