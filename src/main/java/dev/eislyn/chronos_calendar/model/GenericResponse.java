package dev.eislyn.chronos_calendar.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GenericResponse<T> {
    private String status;      // "success" or "error"
    private int statusCode;
    private String message;     // Human-readable message
    private T data; // Generic type for additional data (can be null)
    private LocalDateTime timestamp = LocalDateTime.now();  // Time of the response

    // Constructor for success with data
    public GenericResponse(String status, int statusCode, String message, T data) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}
