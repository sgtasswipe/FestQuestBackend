package com.example.festquestbackend.exceptions.runtime;

public class NotAuthorized extends RuntimeException {

    // Constructor with a message
    public NotAuthorized(String message) {
        super(message);
    }

    // Constructor with a message and cause
    public NotAuthorized(String message, Throwable cause) {
        super(message, cause);
    }

    // No-argument constructor
    public NotAuthorized() {
        super("Not authorized to perform this action.");
    }
}
