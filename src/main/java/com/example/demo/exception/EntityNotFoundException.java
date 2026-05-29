package com.example.demo.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String ms) {
        super(ms);
    }
}
