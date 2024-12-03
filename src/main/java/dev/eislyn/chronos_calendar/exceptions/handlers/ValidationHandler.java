package dev.eislyn.chronos_calendar.exceptions.handlers;

import dev.eislyn.chronos_calendar.model.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationHandler{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<GenericResponse<Map<String, String>>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponse<>("error", HttpStatus.BAD_REQUEST.value(), "Invalid ", errors));
    }
}
