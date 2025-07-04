package com.leverx.learningmanagementsystem.btp.destinationservice.auth;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class LocalDestinationServiceAccessTokenProvider implements DestinationServiceAccessTokenProvider {

    @Override
    public String getAccessToken(String clientId, String clientSecret, String url) {
        return "";
    }

    @Override
    public String refreshAccessToken(String clientId, String clientSecret, String tokenUrl) {
        return "";
    }
}
