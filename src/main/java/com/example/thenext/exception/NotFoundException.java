package com.example.thenext.exception;

public class NotFoundException extends Throwable {
    NotFoundException(String message) {
        super(message);
    }
}
