package com.ahad.exceptions;

// Mapping/Conversion error (DTO <-> Entity)
public class MappingFailedException extends RuntimeException {
    public MappingFailedException(String message) {
        super(message);
    }
}
