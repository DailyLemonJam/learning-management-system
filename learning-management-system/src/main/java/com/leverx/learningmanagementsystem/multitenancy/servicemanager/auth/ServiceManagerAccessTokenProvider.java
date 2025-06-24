package com.leverx.learningmanagementsystem.multitenancy.servicemanager.auth;

public interface ServiceManagerAccessTokenProvider {

    String getAccessToken(String clientId, String clientSecret, String tokenUrl);

    String refreshAccessToken(String clientId, String clientSecret, String tokenUrl);
}
