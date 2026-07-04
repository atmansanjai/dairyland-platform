package com.atman.server.UserModule.Exception;

public class UserPasswordMismatchException extends RuntimeException {
    public UserPasswordMismatchException(String message) {
        super(message);
    }
}
