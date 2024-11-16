package dev.eislyn.chronos.model;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Data
public class GenericResponse<T> {
    private String status;      // "success" or "error"
    private HttpStatusCode code;
    private String message;     // Human-readable message
    private T data; // Generic type for additional data (can be null)
    private LocalDateTime timestamp = LocalDateTime.now();  // Time of the response

    // Constructor for success with data
    public GenericResponse(String status, HttpStatusCode code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}
