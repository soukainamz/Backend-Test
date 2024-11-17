# Spring Boot API

## Overview

This is a simple RESTful API built with **Spring Boot** that uses an **H2 database** for storage. It's a lightweight project that demonstrates basic CRUD operations (Create, Read, Update, Delete) on a simple database model.

## Features

- **RESTful API**: Exposes HTTP endpoints using standard HTTP methods (GET, POST, PUT, DELETE).
- **Spring Boot**: Utilizes Spring Boot's auto-configuration for rapid development.
- **H2 Database**: Uses H2 as an in-memory database for easy testing and development.
- **Swagger UI**: Integrated with **Swagger** to automatically generate API documentation.

## Getting Started

### Prerequisites

- Java 17 or later
- Maven or Gradle (depending on your project setup)
- Your preferred IDE (e.g., IntelliJ IDEA, Eclipse)

### Installation

1. Clone the repository:
2. Build the application:
3. Run the application:
  

    The application will be running on `http://localhost:8080` by default.

### H2 Database

Since this project uses an **H2 in-memory database**, there is no need for external database setup. The H2 database is automatically configured and will store data temporarily during the application's runtime.

To access the H2 console and view or interact with the database:

1. Open your browser and navigate to:

## Endpoints

- **GET** `/api/tasks/{userId}`  
  - Retrieves a list of tasks for the user with the specified `userId`.
  - Example: `GET /api/tasks/1` will fetch tasks for the user with ID 1.

- **GET** `/api/tasks/{userId}/edit/{id}`  
  - Displays the form to edit a task with the specified `id` for the user with the specified `userId`.
  - Example: `GET /api/tasks/1/edit/5` will show the edit form for task with ID 5 for user ID 1.

- **POST** `/api/tasks/{userId}/edit/{id}`  
  - Saves the edited task with the specified `id` for the user with the specified `userId`.
  - Example: `POST /api/tasks/1/edit/5` saves the changes to task with ID 5 for user ID 1.

- **POST** `/api/tasks/{userId}/delete/{id}`  
  - Deletes a task with the specified `id` for the user with the specified `userId`.
  - Example: `POST /api/tasks/1/delete/5` deletes the task with ID 5 for user ID 1.

- **GET** `/api/tasks/{userId}/new`  
  - Displays the form to create a new task for the user with the specified `userId`.
  - Example: `GET /api/tasks/1/new` shows the form to create a task for user ID 1.

- **POST** `/api/tasks/{userId}/new`  
  - Creates a new task for the user with the specified `userId`.
  - Example: `POST /api/tasks/1/new?username=2&title=New%20Task&description=Description&status=PENDING` .


## Testing

All methods in the `TaskController` and `TaskService` classes have been tested using **JUnit 5** to ensure proper functionality. The tests cover all major operations such as creating, retrieving, updating, and deleting tasks.

Additionally, there is a **DB initializer** included in the project. This initializer will populate the H2 database with sample data when you run the application, allowing you to quickly test the application without needing to manually input data.


## improvement

- Uploading Deleted Tasks: Deleted tasks can be stored and uploaded for future reference or as part of user achievements. This feature allows you to track and manage completed tasks, even if they have been removed from the main task list.
- Index Page: The current index page is empty by default, providing you with the flexibility to design and implement a custom landing page.
