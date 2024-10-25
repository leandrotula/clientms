package com.demo.clients.exception;

public class ClientAlreadyExistsException extends RuntimeException {
    public ClientAlreadyExistsException() {
    }

    public ClientAlreadyExistsException(String message) {
        super(message);
    }

    public ClientAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
