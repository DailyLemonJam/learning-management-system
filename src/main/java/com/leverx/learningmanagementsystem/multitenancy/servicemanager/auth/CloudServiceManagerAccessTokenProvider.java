package com.leverx.learningmanagementsystem.multitenancy.servicemanager.auth;

import com.leverx.learningmanagementsystem.web.oauth2.client.OAuth2TokenClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("cloud")
@Component
@RequiredArgsConstructor
public class CloudServiceManagerAccessTokenProvider implements ServiceManagerAccessTokenProvider {

    private final OAuth2TokenClient oAuth2TokenClient;

    @Cacheable(cacheNames = "serviceManagerAccessToken", key = "#clientId")
    @Override
    public String getAccessToken(String clientId, String clientSecret, String tokenUrl) {
        return oAuth2TokenClient.getToken(clientId, clientSecret, tokenUrl);
    }

    @CachePut(cacheNames = "serviceManagerAccessToken", key = "#clientId")
    @Override
    public String refreshAccessToken(String clientId, String clientSecret, String tokenUrl) {
        return oAuth2TokenClient.getToken(clientId, clientSecret, tokenUrl);
    }
}
