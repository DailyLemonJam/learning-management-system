package com.leverx.learningmanagementsystem.payments.exception;

public class StudentAlreadyEnrolledException extends RuntimeException {

    public StudentAlreadyEnrolledException(String message) {
        super(message);
    }

}
