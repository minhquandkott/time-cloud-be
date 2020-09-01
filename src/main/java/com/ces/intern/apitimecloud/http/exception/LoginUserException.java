package com.ces.intern.apitimecloud.http.exception;

public class LoginUserException extends RuntimeException{

    public LoginUserException() {
        super();
    }

    public LoginUserException(String message) {
        super(message);
    }

    public LoginUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginUserException(Throwable cause) {
        super(cause);
    }

    protected LoginUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
