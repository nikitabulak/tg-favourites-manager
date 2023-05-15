package com.nktbul.tgfavouritesmanager.tgagent.exception;

public class AuthorizationInterruptedException extends RuntimeException{
    public AuthorizationInterruptedException() {
        super();
    }

    public AuthorizationInterruptedException(String message) {
        super(message);
    }

    public AuthorizationInterruptedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationInterruptedException(Throwable cause) {
        super(cause);
    }
}
