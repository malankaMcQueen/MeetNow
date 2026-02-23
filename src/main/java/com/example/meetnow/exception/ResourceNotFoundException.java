package com.example.meetnow.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(final String msg) {
        super(msg);
    }
}
