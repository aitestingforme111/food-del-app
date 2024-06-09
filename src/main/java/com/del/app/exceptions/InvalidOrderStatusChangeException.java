package com.del.app.exceptions;

public class InvalidOrderStatusChangeException  extends Exception {
    public InvalidOrderStatusChangeException(String message) {
        super(message);
    }
}
