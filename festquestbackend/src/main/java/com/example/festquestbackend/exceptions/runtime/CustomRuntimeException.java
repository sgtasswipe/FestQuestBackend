package com.example.festquestbackend.exceptions.runtime;

public class CustomRuntimeException extends RuntimeException {
    public CustomRuntimeException(String message) {
        super(message);
    }
}
