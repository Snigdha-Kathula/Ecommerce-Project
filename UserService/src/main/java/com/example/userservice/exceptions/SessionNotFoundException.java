package com.example.userservice.exceptions;

public class SessionNotFoundException extends Exception{
    public SessionNotFoundException(String message) {
        super(message);
    }
}
