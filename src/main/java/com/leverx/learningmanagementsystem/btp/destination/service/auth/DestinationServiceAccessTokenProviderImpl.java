package com.leverx.learningmanagementsystem.btp.destination.service.auth;

import com.leverx.learningmanagementsystem.oauth2.client.OAuth2TokenClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("cloud")
@RequiredArgsConstructor
public class DestinationServiceAccessTokenProviderImpl implements DestinationServiceAccessTokenProvider {

    private final OAuth2TokenClient oAuth2TokenClient;

    @Cacheable(cacheNames = "destinationServiceAccessToken", key = "#clientId")
    @Override
    public String getAccessToken(String clientId, String clientSecret, String tokenUrl) {
        return oAuth2TokenClient.getToken(clientId, clientSecret, tokenUrl);
    }

    @CacheEvict(cacheNames = "destinationServiceAccessToken", key = "#key")
    public void evictCache(String key) {
    }

}
