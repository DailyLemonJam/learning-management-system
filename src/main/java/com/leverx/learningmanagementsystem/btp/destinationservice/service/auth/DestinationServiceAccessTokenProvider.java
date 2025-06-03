package com.leverx.learningmanagementsystem.btp.destinationservice.service.auth;

public interface DestinationServiceAccessTokenProvider {

    String getAccessToken(String clientId, String clientSecret, String tokenUrl);

    String refreshAccessToken(String clientId, String clientSecret, String tokenUrl);

}
