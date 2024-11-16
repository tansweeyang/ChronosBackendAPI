package dev.eislyn.chronos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.eislyn.chronos.model.enums.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> implements Serializable {
    private String status;
    private int code;
    private String msg;
    private T data;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timeStamp;

    public ApiResponse(String msg, T data) {
        this.status = "SUCCESS";
        this.code = 200;
        this.msg = msg;
        this.data = data;
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(T data) {
        this.status = "SUCCESS";
        this.code = 200;
        this.msg = null;
        this.data = data;
        this.timeStamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> ok(T data){
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setStatus("SUCCESS");
        apiResponse.setCode(200);
        apiResponse.setMsg(null);
        apiResponse.setData(data);
        apiResponse.setTimeStamp(LocalDateTime.now());
        return apiResponse;
    }

    public static <T> ApiResponse<T> ok(String msg,T data){
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setStatus("SUCCESS");
        apiResponse.setCode(200);
        apiResponse.setMsg(msg);
        apiResponse.setData(data);
        apiResponse.setTimeStamp(LocalDateTime.now());
        return apiResponse;
    }

    public static <T> ApiResponse<T> fail(ErrorCode errorCode){
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setStatus("ERROR");
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMsg(errorCode.getMessage());
        apiResponse.setData(null);
        apiResponse.setTimeStamp(LocalDateTime.now());
        return apiResponse;
    }

    public static <T> ApiResponse<T> fail(ErrorCode errorCode, String msg){
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setStatus("ERROR");
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMsg(msg);
        apiResponse.setData(null);
        apiResponse.setTimeStamp(LocalDateTime.now());
        return apiResponse;
    }

    public static <T> ApiResponse<T> fail(HttpStatus errorCode){
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setStatus("ERROR");
        apiResponse.setCode(errorCode.value());
        apiResponse.setMsg(errorCode.getReasonPhrase());
        apiResponse.setData(null);
        apiResponse.setTimeStamp(LocalDateTime.now());
        return apiResponse;
    }

    public static <T> ApiResponse<T> fail(HttpStatus errorCode, String msg){
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setStatus("ERROR");
        apiResponse.setCode(errorCode.value());
        apiResponse.setMsg(msg);
        apiResponse.setData(null);
        apiResponse.setTimeStamp(LocalDateTime.now());
        return apiResponse;
    }

    public static <T> ApiResponse<T> fail(int errorCode, T data){
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setStatus("ERROR");
        apiResponse.setCode(errorCode);
        apiResponse.setMsg(null);
        apiResponse.setData(data);
        apiResponse.setTimeStamp(LocalDateTime.now());
        return apiResponse;
    }
}