# AutoTodo REST API

## Overview

Welcome to the MyDay Database REST API! This API provides endpoints for managing tasks for the MyDay App. The API supports operations such as creating, retrieving, updating, and deleting tasks. This README will guide you through the available endpoints and how to use them.

## Endpoints

### Create a Task

**Endpoint:** `POST /tasks`

**Description:** Creates a new task.

**Request Body:**
`{
  "taskId": "string",
  "taskName": "string",
  "description": "string",
  "status": "string"
}` 

**Response:**

-   **201 Created:** The task was created successfully.
    `{
      "taskId": "string",
      "taskName": "string",
      "description": "string",
      "status": "string"
    }` 
    

### List All Tasks

**Endpoint:** `GET /tasks`

**Description:** Retrieves a list of all tasks.

**Response:**

-   **200 OK:** A list of tasks.
    `[
      {
        "taskId": "string",
        "taskName": "string",
        "description": "string",
        "status": "string"
      }
    ]` 
    

### Get a Task

**Endpoint:** `GET /tasks/{taskId}`

**Description:** Retrieves a specific task by its ID.

**Path Parameter:**

-   `taskId` (string): The ID of the task to retrieve.

**Response:**

-   **200 OK:** The task was found.
    `{
      "taskId": "string",
      "taskName": "string",
      "description": "string",
      "status": "string"
    }` 
    
-   **404 Not Found:** The task was not found.

### Update a Task

**Endpoint:** `PUT /tasks/{taskId}`

**Description:** Updates an existing task.

**Path Parameter:**

-   `taskId` (string): The ID of the task to update.

**Request Body:**
`{
  "taskId": "string",
  "taskName": "string",
  "description": "string",
  "status": "string"
}` 

**Response:**

-   **200 OK:** The task was updated successfully.
    `{
      "taskId": "string",
      "taskName": "string",
      "description": "string",
      "status": "string"
    }` 
    
-   **404 Not Found:** The task was not found.

### Delete a Task

**Endpoint:** `DELETE /tasks/{taskId}`

**Description:** Deletes a specific task by its ID.

**Path Parameter:**

-   `taskId` (string): The ID of the task to delete.

**Response:**

-   **204 No Content:** The task was deleted successfully.

## Error Handling

The API returns appropriate HTTP status codes for each response to indicate success or failure. Common status codes include:

-   **200 OK:** The request was successful.
-   **201 Created:** A new resource was created successfully.
-   **204 No Content:** The resource was deleted successfully.
-   **404 Not Found:** The requested resource was not found.
-   **500 Internal Server Error:** An unexpected error occurred on the server.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.

## Contributing

Contributions are welcome! Please read the [CONTRIBUTING](CONTRIBUTING.md) file for more information on how to contribute to this project.

## Contact

If you have any questions or feedback, please open an issue or contact the maintainer at tansweeyang.softwareengineer@gmail.com.