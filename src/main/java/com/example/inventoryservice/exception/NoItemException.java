package com.example.inventoryservice.exception;

public class NoItemException extends RuntimeException {

    public NoItemException(String message) {
        super(message);
    }
}
