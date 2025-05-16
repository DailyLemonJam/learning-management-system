package com.leverx.learningmanagementsystem.auth.client;

public interface OAuth2TokenClient {
    String getToken(String clientId, String clientSecret, String tokenUrl);
}
