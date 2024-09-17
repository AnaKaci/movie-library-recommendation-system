package com.example.diplomeBackend.exceptions;

public class InvalidInputException extends RuntimeException {

    // Default constructor
    public InvalidInputException() {
        super();
    }

    // Constructor that accepts a message
    public InvalidInputException(String message) {
        super(message);
    }

    // Constructor that accepts a message and a cause
    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor that accepts a cause
    public InvalidInputException(Throwable cause) {
        super(cause);
    }
}
