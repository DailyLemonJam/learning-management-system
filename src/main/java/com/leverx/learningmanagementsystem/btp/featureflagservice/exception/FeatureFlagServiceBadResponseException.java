package com.leverx.learningmanagementsystem.btp.featureflagservice.exception;

public class FeatureFlagServiceBadResponseException extends RuntimeException {

    public FeatureFlagServiceBadResponseException(String message) {
        super(message);
    }
}
