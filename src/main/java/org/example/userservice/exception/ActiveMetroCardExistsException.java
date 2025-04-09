package org.example.userservice.exception;

public class ActiveMetroCardExistsException extends RuntimeException {
    public ActiveMetroCardExistsException(String message) {
        super(message);
    }
}
