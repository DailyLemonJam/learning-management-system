package com.leverx.learningmanagementsystem.btp.destination.service.auth;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class DestinationAccessTokenServiceLocal implements DestinationAccessTokenService {
    @Override
    public String getDestinationAccessToken(String clientId, String clientSecret, String url) {
        return "";
    }
}
