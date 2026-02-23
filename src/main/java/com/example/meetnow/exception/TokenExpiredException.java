package com.example.meetnow.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(final String msg) {
        super(msg);
    }
}

