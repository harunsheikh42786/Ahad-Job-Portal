package com.ahad.exceptions;

// Unauthorized or forbidden access
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
