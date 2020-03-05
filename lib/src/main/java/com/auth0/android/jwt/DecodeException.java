package com.auth0.android.jwt;

@SuppressWarnings("WeakerAccess")
public class DecodeException extends RuntimeException {

    DecodeException(String message) {
        super(message);
    }

    DecodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
