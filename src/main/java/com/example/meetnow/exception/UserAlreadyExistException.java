package com.example.meetnow.exception;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(final String msg) {
        super(msg);
    }

}
