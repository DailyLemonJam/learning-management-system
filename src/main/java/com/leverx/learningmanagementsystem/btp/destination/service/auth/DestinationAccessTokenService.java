package com.leverx.learningmanagementsystem.btp.destination.service.auth;

public interface DestinationAccessTokenService {
    String getDestinationAccessToken(String clientId, String clientSecret, String url);
}
