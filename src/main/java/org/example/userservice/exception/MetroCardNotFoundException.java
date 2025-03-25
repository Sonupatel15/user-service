package org.example.userservice.exception;

public class MetroCardNotFoundException extends RuntimeException {
    public MetroCardNotFoundException(String message) {
        super(message);
    }
}