package com.leverx.learningmanagementsystem.oauth2.client;

public interface OAuth2TokenClient {
    String getToken(String clientId, String clientSecret, String tokenUrl);
}
