package com.nktbul.tgfavouritesmanager.tgagent.exception;

public class AuthorizationRequiredException extends RuntimeException{
    public AuthorizationRequiredException() {
        super();
    }

    public AuthorizationRequiredException(String message) {
        super(message);
    }

    public AuthorizationRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationRequiredException(Throwable cause) {
        super(cause);
    }
}
