package dev.eislyn.AutoTodo.Exceptions;

public class EmailSendingException extends RuntimeException{
    public EmailSendingException(String message) {
        super(message);
    }
}
