package dev.eislyn.chronos_calendar.model.enums;

public enum ErrorCode {
    NO_DATA_FOUND(10001, "No data found"),


    // HTTP-specific errors
    HTTPS_BAD_REQUEST(400, "Bad Request"),
    HTTPS_UNAUTHORIZED(401, "Unauthorized"),
    HTTPS_FORBIDDEN(403, "Forbidden"),
    HTTPS_NOT_FOUND(404, "Not Found"),
    HTTPS_INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;

    }
}