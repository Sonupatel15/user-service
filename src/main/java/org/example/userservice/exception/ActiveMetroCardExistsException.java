package org.example.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ActiveMetroCardExistsException extends RuntimeException {
    public ActiveMetroCardExistsException(String message) {
        super(message);
    }
}