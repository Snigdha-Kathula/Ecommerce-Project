package com.example.userservice.exceptions;

public class RoleAlreadyExistException extends Exception{
    public RoleAlreadyExistException(String message) {
        super(message);
    }
}
