package com.leverx.learningmanagementsystem.payments.exception;

public class NotEnoughCoinsException extends RuntimeException {

    public NotEnoughCoinsException(String message) {
        super(message);
    }
}
