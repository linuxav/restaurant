package com.alex.restaurant.exception;

public class DuplicateRegistrationException extends RuntimeException {

    public DuplicateRegistrationException(String message) {
        super(message);
    }

    public DuplicateRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
