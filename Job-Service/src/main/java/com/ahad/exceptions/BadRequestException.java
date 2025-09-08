package com.ahad.exceptions;

// Invalid request data (e.g., bad input, validation fail)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
