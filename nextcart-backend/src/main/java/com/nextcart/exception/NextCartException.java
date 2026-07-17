package com.nextcart.exception;

public class NextCartException extends RuntimeException {
    public NextCartException(String message) {
        super(message);
    }

    public NextCartException(String message, Throwable cause) {
        super(message, cause);
    }
}
