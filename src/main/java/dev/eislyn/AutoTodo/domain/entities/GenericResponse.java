package dev.eislyn.AutoTodo.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T> {
    private String status;      // "success" or "error"
    private String message;     // Human-readable message
    private LocalDateTime timestamp = LocalDateTime.now();  // Time of the response
    private T data;             // Generic type for additional data (can be null)
    private String errorCode;   // Error code for easier error identification (optional)

    // Constructors for success responses (no error code)
    public GenericResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.data = data;
        this.errorCode = null;  // No error code for success
    }

    // Constructors for error responses
    public GenericResponse(String status, String message, String errorCode) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.data = null;       // No data for errors
        this.errorCode = errorCode;
    }
}
