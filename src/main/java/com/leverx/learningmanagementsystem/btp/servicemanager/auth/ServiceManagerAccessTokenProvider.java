package com.leverx.learningmanagementsystem.btp.servicemanager.auth;

public interface ServiceManagerAccessTokenProvider {

    String getAccessToken(String clientId, String clientSecret, String tokenUrl);

    String refreshAccessToken(String clientId, String clientSecret, String tokenUrl);
}
