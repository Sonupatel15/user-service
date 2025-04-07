package org.example.userservice.exception;

public class TravelIdNotFoundException extends RuntimeException {
  public TravelIdNotFoundException(String message) {
    super(message);
  }
}