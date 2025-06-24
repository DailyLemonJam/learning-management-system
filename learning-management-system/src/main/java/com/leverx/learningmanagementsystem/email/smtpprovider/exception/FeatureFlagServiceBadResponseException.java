package com.leverx.learningmanagementsystem.email.smtpprovider.exception;

public class FeatureFlagServiceBadResponseException extends RuntimeException {

    public FeatureFlagServiceBadResponseException(String message) {
        super(message);
    }
}
