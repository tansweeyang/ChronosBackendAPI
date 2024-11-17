package dev.eislyn.chronos.utils;

import dev.eislyn.chronos.model.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T> ResponseEntity<ApiResponse<T>> buildResponse(ApiResponse<T> response, HttpStatus status) {
        HttpHeaders headers = HeadersUtil.createHeaders();
        return new ResponseEntity<>(response, headers, status);
    }
}