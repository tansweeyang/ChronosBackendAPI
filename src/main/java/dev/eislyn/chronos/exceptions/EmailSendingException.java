package dev.eislyn.chronos.exceptions;

public class EmailSendingException extends RuntimeException{
    public EmailSendingException(String message) {
        super(message);
    }
}
