package com.example.demo.exception;

public class DuplicationEmailException extends RuntimeException{
    public DuplicationEmailException(String ms) {
        super(ms);
    }
}
