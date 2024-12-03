package dev.eislyn.chronos_calendar.exceptions;

public class EmailSendingException extends RuntimeException{
    public EmailSendingException(String message) {
        super(message);
    }
}
