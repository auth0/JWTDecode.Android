package com.auth0.android.jwtdecode.exceptions;

public class InvalidJsonException extends JWTException {
    public InvalidJsonException(String message, Throwable cause) {
        super(message, cause);
    }
}
