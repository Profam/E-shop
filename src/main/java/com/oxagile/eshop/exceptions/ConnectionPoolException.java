package com.oxagile.eshop.exceptions;

public class ConnectionPoolException extends Exception {
    public ConnectionPoolException(String message) {
        super(message);
    }
    public ConnectionPoolException(String message, Throwable e) {
        super(message, e);
    }
}
