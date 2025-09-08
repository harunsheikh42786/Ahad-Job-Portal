package com.ahad.exceptions;

// Duplicate resource (User already exists, Education already exists, etc.)
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
