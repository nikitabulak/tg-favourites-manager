package com.nktbul.tgfavouritesmanager.tgagent.exception;

public class PhoneNumberRequiredException extends RuntimeException{
    public PhoneNumberRequiredException() {
        super();
    }

    public PhoneNumberRequiredException(String message) {
        super(message);
    }

    public PhoneNumberRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneNumberRequiredException(Throwable cause) {
        super(cause);
    }
}
